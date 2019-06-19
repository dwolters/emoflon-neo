package org.emoflon.neo.emsl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.eclipse.emf.common.util.EList;
import org.emoflon.neo.emsl.eMSL.AtomicPattern;
import org.emoflon.neo.emsl.eMSL.EMSLFactory;
import org.emoflon.neo.emsl.eMSL.Entity;
import org.emoflon.neo.emsl.eMSL.EnumValue;
import org.emoflon.neo.emsl.eMSL.MetamodelNodeBlock;
import org.emoflon.neo.emsl.eMSL.ModelNodeBlock;
import org.emoflon.neo.emsl.eMSL.ModelPropertyStatement;
import org.emoflon.neo.emsl.eMSL.ModelRelationStatement;
import org.emoflon.neo.emsl.eMSL.Pattern;
import org.emoflon.neo.emsl.eMSL.PrimitiveBoolean;
import org.emoflon.neo.emsl.eMSL.PrimitiveInt;
import org.emoflon.neo.emsl.eMSL.PrimitiveString;
import org.emoflon.neo.emsl.eMSL.RefinementCommand;
import org.emoflon.neo.emsl.util.EntityCloner;
import org.emoflon.neo.emsl.util.FlattenerErrorType;
import org.emoflon.neo.emsl.util.FlattenerException;

public class EMSLFlattener {

	public EMSLFlattener() {
		
	}
	
	/**
	 * Returns the flattened Pattern.
	 * 
	 * @param pattern that should be flattened.
	 * @param alreadyRefinedPatternNames list of names of pattern that have already appeared in the refinement path (against loops).
	 * @return the flattened pattern.
	 * @throws FlattenerException is thrown if the pattern could not be flattened.
	 */
	public Pattern flattenPattern(Pattern pattern, ArrayList<String> alreadyRefinedPatternNames) throws FlattenerException {
		if (pattern != null) {
			var atomicPattern = pattern.getBody();
			var<RefinementCommand> refinements = atomicPattern.getSuperRefinementTypes();
			
			// check for loop in refinements
	
			if (alreadyRefinedPatternNames.contains(atomicPattern.getName())) 
				throw new FlattenerException(pattern, FlattenerErrorType.INFINITE_LOOP, alreadyRefinedPatternNames);
			// if none has been found, add current name to list
			
			
			// check if anything has to be done, if not return
			if (refinements.isEmpty())
				return pattern;
			
			// 1. step: collect nodes with edges
			var<String, ArrayList<ModelNodeBlock>> collectedNodeBlocks = collectNodes(pattern, refinements, alreadyRefinedPatternNames);
			atomicPattern.getNodeBlocks().forEach(nb -> {
				if (collectedNodeBlocks.keySet().contains(nb.getName())) {
					collectedNodeBlocks.get(nb.getName()).add(nb);
				}
				else {
					var<ModelNodeBlock> tmp = new ArrayList<ModelNodeBlock>();
					tmp.add(nb);
					collectedNodeBlocks.put(nb.getName(), tmp);
				}
			});
	
			// 2. step: merge nodes and edges
			var mergedNodes = mergeNodes(pattern, refinements, collectedNodeBlocks);
			
			// 3. step: add merged nodeBlocks to pattern and return
			atomicPattern.getNodeBlocks().clear();
			atomicPattern.getNodeBlocks().addAll((mergedNodes));
			
			pattern.setBody(atomicPattern);
			
			checkForResolvedProxies(pattern);
		}
		return pattern;
	}
	
	
	/**
	 * Returns a flattened copy of the given Pattern.
	 * @param originalPattern that is to be copied and flattened.
	 * @param alreadyRefinedPatternNames list of names that of pattern that have already appeared in the refinement path (against loops). 
	 * @return flattened copy of given Pattern.
	 * @throws FlattenerException is thrown if the pattern could not be flattened.
	 */
	public Pattern flattenCopyOfPattern(Pattern originalPattern, ArrayList<String> alreadyRefinedPatternNames) throws FlattenerException {
		
		var pattern = (Pattern) new EntityCloner().cloneEntity(originalPattern);
		
		if (pattern != null) {
			var atomicPattern = pattern.getBody();
			var<RefinementCommand> refinements = atomicPattern.getSuperRefinementTypes();
			
			// check for loop in refinements
	
			if (alreadyRefinedPatternNames.contains(atomicPattern.getName())) 
				throw new FlattenerException(pattern, FlattenerErrorType.INFINITE_LOOP, alreadyRefinedPatternNames);
			// if none has been found, add current name to list
			
			
			// check if anything has to be done, if not return
			if (refinements.isEmpty())
				return pattern;
			
			// 1. step: collect nodes with edges
			var<String, ArrayList<ModelNodeBlock>> collectedNodeBlocks = collectNodes(pattern, refinements, alreadyRefinedPatternNames);
			atomicPattern.getNodeBlocks().forEach(nb -> {
				if (collectedNodeBlocks.keySet().contains(nb.getName())) {
					collectedNodeBlocks.get(nb.getName()).add(nb);
				}
				else {
					var<ModelNodeBlock> tmp = new ArrayList<ModelNodeBlock>();
					tmp.add(nb);
					collectedNodeBlocks.put(nb.getName(), tmp);
				}
			});
	
			// 2. step: merge nodes and edges
			var mergedNodes = mergeNodes(pattern, refinements, collectedNodeBlocks);
			
			// 3. step: add merged nodeBlocks to pattern and return
			atomicPattern.getNodeBlocks().clear();
			atomicPattern.getNodeBlocks().addAll((mergedNodes));
			
			pattern.setBody(atomicPattern);
			
			checkForResolvedProxies(pattern);
		}
		
		return pattern;
	}
	
	
	/**
	 * This method creates all NodeBlocks that have to be imported into the Pattern from the SuperEntities.
	 * @param refinementList List of RefinementCommands holding all pattern that should be refined.
	 * @param alreadyRefinedPatternNames List of Pattern names that have already appeared in the refinement path (against loops).
	 * @return HashMap of NodeBlocks mapped to their name that have to be added to the refining Pattern.
	 * @throws FlattenerException is thrown if an error occurs during collecting the nodes, like an infinite loop is detected
	 */
	private HashMap<String, ArrayList<ModelNodeBlock>> collectNodes(Pattern entity, EList<RefinementCommand> refinementList, ArrayList<String> alreadyRefinedPatternNames) throws FlattenerException {
		var<String, ArrayList<ModelNodeBlock>> nodeBlocks = new HashMap<String, ArrayList<ModelNodeBlock>>();
		
		for (var r : refinementList) {
			
			// add current entity to list of names
			var alreadyRefinedPatternNamesCopy = new ArrayList<String>();
			alreadyRefinedPatternNames.forEach(n -> alreadyRefinedPatternNamesCopy.add(n));
			alreadyRefinedPatternNamesCopy.add(entity.getBody().getName());
			
			// recursively flatten superEntities
			var flattenedSuperEntity = flattenPattern((Pattern) r.getReferencedType().eContainer(), alreadyRefinedPatternNamesCopy).getBody();
			var nodeBlocksOfSuperEntity = new ArrayList<ModelNodeBlock>();
			
			if (flattenedSuperEntity instanceof AtomicPattern) {				
				if (((Pattern) r.getReferencedType().eContainer()).getCondition() != null) {
					// check if a superEntity possesses a condition block
					throw new FlattenerException(entity, FlattenerErrorType.REFINE_ENTITY_WITH_CONDITION, r.getReferencedType());
				}
				else {
					for (var nb : (flattenedSuperEntity).getNodeBlocks()) {
						
						// create new NodeBlock
						var newNb = copyModelNodeBlock(nb, r);
						
						// add nodeBlock to list according to its name
						if (!nodeBlocks.containsKey(newNb.getName())) {
							var<ModelNodeBlock> newList = new ArrayList<ModelNodeBlock>();
							newList.add(newNb);
							nodeBlocks.put(newNb.getName(), newList);
						}
						else {
							nodeBlocks.get(newNb.getName()).add(newNb);
						}
						nodeBlocksOfSuperEntity.add(newNb);
					}
				}
				
				// re-set targets of edges
				for (var nb : nodeBlocksOfSuperEntity) {
					for (var rel : nb.getRelations()) {
						boolean targetSet = false;
						if (targetSet)
							break;
						for (var ref : r.getRelabeling()) {
							if (targetSet)
								break;
							if ((rel.getTarget() != null && ref.getOldLabel().equals(rel.getTarget().getName()))
									|| rel.getProxyTarget() != null && ref.getOldLabel().equals(rel.getProxyTarget())) {
								for (var node : nodeBlocksOfSuperEntity) {
									if (ref.getNewLabel().equals(node.getName())) {
										rel.setTarget(node);
										targetSet = true;
										break;
									}
								}
							} else {
								for (var node : nodeBlocksOfSuperEntity) {
									if (rel.getTarget() != null && rel.getTarget().getName().equals(node.getName()) 
											|| (rel.getProxyTarget() != null && rel.getProxyTarget().equals(node.getName()))) {
										rel.setTarget(node);
										break;
									}
								}
							}
						}
					}
				}
			} 
		}
		
		return nodeBlocks;
	}
	
	/**
	 * This method takes a HashMap of lists of NodeBlocks mapped to the names of the contained NodeBlocks.
	 * All of those NodeBlocks with the same name (mapping) are then merged into one that is added to the model. During
	 * the merging the least common subtype of all NodeBlocks with the same name is searched for. All of the edges with the same
	 * target and same type are also merged.
	 * 
	 * @param nodeBlocks HashMap of Lists of ModelNodeBlocks that are mapped to the names of the NodeBlocks contained in such a list.
	 * @return ArrayList of ModelNodeBlocks that only contains the ModelNodeBlocks that were created during merging.
	 * @throws FlattenerException is thrown if something went wrong during the merging process.
	 */
	private ArrayList<ModelNodeBlock> mergeNodes(Entity entity, EList<RefinementCommand> refinementList, HashMap<String, ArrayList<ModelNodeBlock>> nodeBlocks) throws FlattenerException {
		var<ModelNodeBlock> mergedNodes = new ArrayList<ModelNodeBlock>();
		
		// take all nodeBlocks with the same name/key out of the HashMap and merge
		for (var name : nodeBlocks.keySet()) {
			var blocksWithKey = nodeBlocks.get(name);
			
			Comparator<MetamodelNodeBlock> comparator = new Comparator<MetamodelNodeBlock>() {
				@Override
				public int compare(MetamodelNodeBlock o1, MetamodelNodeBlock o2) {
					if (o1.getSuperTypes().contains(o2) || recursiveContainment(o1, o2, false)) {
						return -1;
					} else if (o2.getSuperTypes().contains(o1) || recursiveContainment(o2, o1, false)) {
						return 1;
					} else if (o1 == o2) {
						return 0;
					} else {
						// no common type could be found, merge not possible
						throw new AssertionError();
					}
				}
				
				private boolean recursiveContainment(MetamodelNodeBlock o1, MetamodelNodeBlock o2, boolean containment) {
					var wrapper = new Object() { boolean contains = false; };
					
					if (o1.getSuperTypes().contains(o2)) {
						return true;
					}
					
					o1.getSuperTypes().forEach(st -> {
						wrapper.contains = (recursiveContainment(st, o2, containment));
					});
					return wrapper.contains;
				}
			};
			
			// store/sort types in this PriorityQueue
			PriorityQueue<MetamodelNodeBlock> nodeBlockTypeQueue = new PriorityQueue<MetamodelNodeBlock>(comparator);
			
			// collect types
			for (var nb : blocksWithKey) {
				if (nb.getType() != null) {
					try {
						nodeBlockTypeQueue.add(nb.getType());
					} catch (AssertionError e) {
						throw new FlattenerException(entity, FlattenerErrorType.NO_COMMON_SUBTYPE_OF_NODES, nb);
					}
				}
			}
			
			// create new NodeBlock that will be added to the pattern
			var newNb = EMSLFactory.eINSTANCE.createModelNodeBlock();
			newNb.setType(nodeBlockTypeQueue.peek());
			newNb.setName(name);
			
			mergedNodes.add(newNb);
		}
		
		return mergeEdgesOfNodeBlocks(entity, nodeBlocks, mergePropertyStatementsOfNodeBlocks(entity, nodeBlocks, mergedNodes));
	}
	
	/**
	 * This method takes a list of collected NodeBlocks that were collected from all the refinements, and a list of merged nodes
	 * and adds the merged RelationStatements to the mergedNodes which are then returned.
	 * @param nodeBlocks that were collected from the refinements.
	 * @param mergedNodes nodeBlocks that were except for the relationStatements already merged.
	 * @return list of ModelNodeBlocks that now have merged RelationStatements.
	 * @throws FlattenerException is thrown if something went wrong during the merging process.
	 */
	private ArrayList<ModelNodeBlock> mergeEdgesOfNodeBlocks(Entity entity, HashMap<String, ArrayList<ModelNodeBlock>> nodeBlocks, ArrayList<ModelNodeBlock> mergedNodes) throws FlattenerException {
		
		// collect all edges in hashmap; first key is type name, second is target name
		for (var name : nodeBlocks.keySet()) {
			var edges = new HashMap<String, HashMap<String, ArrayList<ModelRelationStatement>>>();
			for (var nb : nodeBlocks.get(name)) {
				for (var rel : nb.getRelations()) {
					if (rel.getType() == null) {
						continue;
					}
					if (rel.getTarget() != null) {
						if (!edges.containsKey(rel.getType().getName())) {
							edges.put(rel.getType().getName(), new HashMap<String, ArrayList<ModelRelationStatement>>());
						}
						if (!edges.get(rel.getType().getName()).containsKey(rel.getTarget().getName())) {
							edges.get(rel.getType().getName()).put(rel.getTarget().getName(), new ArrayList<ModelRelationStatement>());
						}
						edges.get(rel.getType().getName()).get(rel.getTarget().getName()).add(rel);
					} else if (rel.getProxyTarget() != null) {
						if (!edges.containsKey(rel.getType().getName())) {
							edges.put(rel.getType().getName(), new HashMap<String, ArrayList<ModelRelationStatement>>());
						}
						if (!edges.get(rel.getType().getName()).containsKey(rel.getProxyTarget())) {
							edges.get(rel.getType().getName()).put(rel.getProxyTarget(), new ArrayList<ModelRelationStatement>());
						}
						edges.get(rel.getType().getName()).get(rel.getProxyTarget()).add(rel);
					}
				}
			}
			
			// iterate over all types and targets to create new RelationStatement that is the result of the merging
			for (var typename : edges.keySet()) {
				for (var targetname : edges.get(typename).keySet()) {
					var newRel = EMSLFactory.eINSTANCE.createModelRelationStatement();
					
					// merge PropertyStatements of Edges
					var properties = new HashMap<String, ArrayList<ModelPropertyStatement>>();
					for (var e : edges.get(typename).get(targetname)) {
						// collect propertyStatements
						e.getProperties().forEach(p -> {
							if (!properties.containsKey(p.getType().getName())) {
								properties.put(p.getType().getName(), new ArrayList<ModelPropertyStatement>());
							}
							properties.get(p.getType().getName()).add(p);
						});
					}
					// merge statements	and check statements for compliance
					for (var propertyName : properties.keySet()) {
						var props = properties.get(propertyName);
						ModelPropertyStatement basis = null;
						if (properties.size() > 0) {
							basis = props.get(0);
						}
						for (var p : props) {
							if (p.getType().getType() != basis.getType().getType() || 
									basis.getOp() != p.getOp()) {
								// incompatible types/operands found
								throw new FlattenerException(entity, FlattenerErrorType.NO_COMMON_SUBTYPE_OF_PROPERTIES, basis, p);
							}
							compareValueOfModelPropertyStatement(entity, basis, p);
						}
						newRel.getProperties().add(copyModelPropertyStatement(basis));
					}
					
					newRel.setType(edges.get(typename).get(targetname).get(0).getType());
					mergedNodes.forEach(nb -> {
						if (nb.getName().equals(targetname)) {
							newRel.setTarget(nb);
						}
						if (nb.getName().equals(name)) {
							nb.getRelations().add(newRel);
						}
					});
				}
			}
		}
		
		return mergedNodes;
	}
	
	/**
	 * This method merges the ModelPropertyStatements of NodeBlocks. Throws an error if the operator, value or type
	 * of the statements that are to merged are not equal.
	 * @param nodeBlocks that were collected and merged into the new NodeBlocks.
	 * @param mergedNodes result of the mergeNodes function. These nodeBlocks get the PropertyStatements.
	 * @return list of mergedNodeBlocks with the new and merged ModelPropertyStatements.
	 * @throws FlattenerException is thrown if something went wrong during the merging process.
	 */
	private ArrayList<ModelNodeBlock> mergePropertyStatementsOfNodeBlocks(Entity entity, HashMap<String, ArrayList<ModelNodeBlock>> nodeBlocks, ArrayList<ModelNodeBlock> mergedNodes) throws FlattenerException {
		for (var name : nodeBlocks.keySet()) {
			var nodeBlocksWithKey = nodeBlocks.get(name);
			var newProperties = new ArrayList<ModelPropertyStatement>();
			
			// collect ModelPropertyStatements with same name
			var propertyStatementsSortedByName = new HashMap<String, ArrayList<ModelPropertyStatement>>();
			for (var nb : nodeBlocksWithKey) {
				for (var p : nb.getProperties()) {
					if (p.getType() == null) {
						continue;
					}
					if (!propertyStatementsSortedByName.containsKey(p.getType().getName())) {
						propertyStatementsSortedByName.put(p.getType().getName(), new ArrayList<ModelPropertyStatement>());
					}
					propertyStatementsSortedByName.get(p.getType().getName()).add(p);
				}
			}
			
			// check statements for compliance
			for (var propertyName : propertyStatementsSortedByName.keySet()) {
				var properties = propertyStatementsSortedByName.get(propertyName);
				ModelPropertyStatement basis = null;
				if (properties.size() > 0) {
					basis = properties.get(0);
				}
				for (var p : properties) {
					if (!(p.getType().getType() == basis.getType().getType() && 
							basis.getOp() == p.getOp())) 
					{
						throw new FlattenerException(entity, FlattenerErrorType.NO_COMMON_SUBTYPE_OF_PROPERTIES, basis, p); // incompatible types found
					}
					compareValueOfModelPropertyStatement(entity, basis, p);
				}
				newProperties.add(copyModelPropertyStatement(basis));
			}
			
			// add merged properties to the new nodeblock
			mergedNodes.forEach(nb -> {
				if (nb.getName().equals(name)) {
					nb.getProperties().addAll(newProperties);
				}
			});
		}
		
		return mergedNodes;
	}
	
	
	/**
	 * This method creates a new NodeBlock from the given NodeBlock that was referenced in the RefinementStatement. It also applies
	 * the relabeling of the input.
	 * @param nb Referenced NodeBlock that will be created.
	 * @param oldLabel Old name of the NodeBlock, must not be present if no relabeling is to be done.
	 * @param newLabel New name of the NodeBlock, must not be present if no relabeling is to be done.
	 * @return The newly created NodeBlock based on the NodeBlock passed as parameter.
	 */
	private ModelNodeBlock copyModelNodeBlock(ModelNodeBlock nb, RefinementCommand refinement) {
		var newNb = EMSLFactory.eINSTANCE.createModelNodeBlock();
		
		// apply relabeling
		if (refinement.getRelabeling() != null) {
			for (var r : refinement.getRelabeling()) {
				if (r.getOldLabel() != null  
						&& nb.getName().equals(r.getOldLabel())) {
					newNb.setName(r.getNewLabel());
					break;
				}
			}
		}
		
		if (newNb.getName() == null)
			newNb.setName(nb.getName());
		
		newNb.setType(nb.getType());
		newNb.setAction(nb.getAction());
		
		// add relations to new nodeblock
		for (var rel : nb.getRelations()) {
			var newRel = EMSLFactory.eINSTANCE.createModelRelationStatement();
			newRel.setAction(rel.getAction());
			newRel.setType(rel.getType());
			newRel.setTarget(rel.getTarget());			
			newNb.getRelations().add(newRel);
		}
		
		// add properties to new nodeblock
		for (var prop : nb.getProperties()) {
			newNb.getProperties().add(copyModelPropertyStatement(prop));
		}
		
		return newNb;
	}
	
	/**
	 * Creates a new ModelPropertyStatement that is equal to the one that is given.
	 * @param oldStatement that is to be copied.
	 * @return a new ModelPropertyStatement with equal attributes of the given one.
	 */
	private ModelPropertyStatement copyModelPropertyStatement(ModelPropertyStatement oldStatement) {
		var newProp = EMSLFactory.eINSTANCE.createModelPropertyStatement();
		newProp.setOp(oldStatement.getOp());
		newProp.setType(oldStatement.getType());
		
		// create new Value for Property
		if (oldStatement.getValue() instanceof PrimitiveBoolean) {
			newProp.setValue(EMSLFactory.eINSTANCE.createPrimitiveBoolean());
			if (((PrimitiveBoolean) oldStatement.getValue()).isTrue())
				((PrimitiveBoolean) newProp.getValue()).setTrue(true);
			else 
				((PrimitiveBoolean) newProp.getValue()).setTrue(false);
		} else if (oldStatement.getValue() instanceof PrimitiveInt) {
			newProp.setValue(EMSLFactory.eINSTANCE.createPrimitiveInt());
			((PrimitiveInt) newProp.getValue()).setLiteral(((PrimitiveInt) oldStatement.getValue()).getLiteral());
		} else if (oldStatement.getValue() instanceof PrimitiveString) {
			newProp.setValue(EMSLFactory.eINSTANCE.createPrimitiveString());
			((PrimitiveString) newProp.getValue()).setLiteral(((PrimitiveString) oldStatement.getValue()).getLiteral());
		} else if (oldStatement.getValue() instanceof EnumValue) {
			oldStatement.getValue();
			newProp.setValue(EMSLFactory.eINSTANCE.createEnumValue());
			((EnumValue) newProp.getValue()).setLiteral(((EnumValue) oldStatement.getValue()).getLiteral());
		}
		
		return newProp;
	}
	
	/**
	 * Compares the two given PropertyStatements for equal values. If the values are not equal an according exception is thrown.
	 * @param entity that contains the PropertyStatements.
	 * @param p1 first statement in the comparison.
	 * @param p2 second statement in the comparison.
	 * @throws FlattenerException is thrown if the values of the two statements are not equal.
	 */
	private void compareValueOfModelPropertyStatement(Entity entity, ModelPropertyStatement p1, ModelPropertyStatement p2) throws FlattenerException {
		
		if (p1.getValue() instanceof PrimitiveBoolean && p2.getValue() instanceof PrimitiveBoolean
				&& (((PrimitiveBoolean) p1.getValue()).isTrue() && ((PrimitiveBoolean) p2.getValue()).isTrue()
				|| !((PrimitiveBoolean) p1.getValue()).isTrue() && !((PrimitiveBoolean) p2.getValue()).isTrue())) {
			return;
		} else if (p1.getValue() instanceof PrimitiveInt && p2.getValue() instanceof PrimitiveInt
				&& ((PrimitiveInt) p1.getValue()).getLiteral() == ((PrimitiveInt) p2.getValue()).getLiteral()) {
			return;
		} else if (p1.getValue() instanceof PrimitiveString && p2.getValue() instanceof PrimitiveString
				&& ((PrimitiveString) p1.getValue()).getLiteral().equals(((PrimitiveString) p2.getValue()).getLiteral())) {
			return;
		} else if (p1.getValue() instanceof EnumValue && p2.getValue() instanceof EnumValue
				&& ((EnumValue) p1.getValue()).getLiteral() == ((EnumValue) p2.getValue()).getLiteral()) {
			return;
		}
		throw new FlattenerException(entity, FlattenerErrorType.PROPS_WITH_DIFFERENT_VALUES, p1, p2);	
	}
	
	/**
	 * Iterates over all relations in the newly flattened pattern and checks if a proxy target could not be resolved to
	 * a nodeBlock from one of the superEntities.
	 * @param pattern that is to be checked if all proxies were resolved.
	 * @throws FlattenerException is thrown if a proxy was not resolved during flattening.
	 */
	private void checkForResolvedProxies(Pattern pattern) throws FlattenerException {
		for (var nb : pattern.getBody().getNodeBlocks()) {
			for (var relation : nb.getRelations()) {
				if (!(relation.getTarget() instanceof ModelNodeBlock)) {
					throw new FlattenerException(pattern, FlattenerErrorType.NON_RESOLVABLE_PROXY, relation);
				}
			}
		}
	}
}
