/*
 * generated by Xtext 2.16.0
 */
package org.emoflon.neo.emsl.validation

import java.util.ArrayList
import java.util.HashMap
import java.util.function.Consumer
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.validation.Check
import org.emoflon.neo.emsl.eMSL.AttributeExpression
import org.emoflon.neo.emsl.eMSL.BuiltInDataTypes
import org.emoflon.neo.emsl.eMSL.BuiltInType
import org.emoflon.neo.emsl.eMSL.Condition
import org.emoflon.neo.emsl.eMSL.Constraint
import org.emoflon.neo.emsl.eMSL.ConstraintReference
import org.emoflon.neo.emsl.eMSL.DataType
import org.emoflon.neo.emsl.eMSL.EMSLPackage
import org.emoflon.neo.emsl.eMSL.EMSL_Spec
import org.emoflon.neo.emsl.eMSL.Entity
import org.emoflon.neo.emsl.eMSL.EnumValue
import org.emoflon.neo.emsl.eMSL.GraphGrammar
import org.emoflon.neo.emsl.eMSL.Implication
import org.emoflon.neo.emsl.eMSL.LinkAttributeExpTarget
import org.emoflon.neo.emsl.eMSL.Metamodel
import org.emoflon.neo.emsl.eMSL.MetamodelPropertyStatement
import org.emoflon.neo.emsl.eMSL.Model
import org.emoflon.neo.emsl.eMSL.ModelNodeBlock
import org.emoflon.neo.emsl.eMSL.ModelPropertyStatement
import org.emoflon.neo.emsl.eMSL.ModelRelationStatement
import org.emoflon.neo.emsl.eMSL.NegativeConstraint
import org.emoflon.neo.emsl.eMSL.PositiveConstraint
import org.emoflon.neo.emsl.eMSL.NodeAttributeExpTarget
import org.emoflon.neo.emsl.eMSL.Pattern
import org.emoflon.neo.emsl.eMSL.PrimitiveBoolean
import org.emoflon.neo.emsl.eMSL.PrimitiveInt
import org.emoflon.neo.emsl.eMSL.PrimitiveString
import org.emoflon.neo.emsl.eMSL.RefinementCommand
import org.emoflon.neo.emsl.eMSL.Rule
import org.emoflon.neo.emsl.eMSL.SuperType
import org.emoflon.neo.emsl.eMSL.TripleGrammar
import org.emoflon.neo.emsl.eMSL.TripleRule
import org.emoflon.neo.emsl.eMSL.UserDefinedType
import org.emoflon.neo.emsl.refinement.EMSLFlattener
import org.emoflon.neo.emsl.util.EntityAttributeDispatcher
import org.emoflon.neo.emsl.util.FlattenerErrorType
import org.emoflon.neo.emsl.util.FlattenerException
import java.util.HashSet
import org.emoflon.neo.emsl.eMSL.MetamodelNodeBlock
import org.emoflon.neo.emsl.eMSL.MetamodelRelationStatement

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class EMSLValidator extends AbstractEMSLValidator {
	
	EntityAttributeDispatcher dispatcher
	
	new() {
		dispatcher = new EntityAttributeDispatcher()
	}

	// Error and info messages
	static final String WRONG_PROPERTY_TYPE = "The value of this property must be of type "
	static final String CONDITION_IN_SUPER_ENTITY = "Entities with conditions cannot be refined."
	static final String SAME_NAMES_OF_OBJECTS_IN_ENTITY = "Using the same name multiple times in one Entity is not allowed."
	static final def String REFINEMENT_LOOP(String refinement) '''You have created an infinite loop in your refinements. "«refinement»" appears multiple times.'''
	static final def String SAME_NAMES_OF_ENTITIES(String className) '''Two «className»s with the same name are not allowed.'''
	static final String GREEN_NODE_OF_ABSTRACT_TYPES = "Green Nodes of abstract types are not allowed"
	static final String NON_RESOLVABLE_PROXY = "A proxy target you defined could not be resolved."
	static final def String NON_MERGABLE_TYPES(String typeName) '''The type «typeName»  in your refinements cannot be merged into a common subtype.'''
	static final String FORBIDDING_COMPLEX_EDGES = "Complex Edges in Models are not allowed."
	static final String ENTITY_TYPE_NOT_SUPPORTED = "Mixing entities in refinements is not allowed."
	static final String INSTANTIATING_ABSTRACT_TYPE_IN_NON_ABSTRACT_MODEL = "Instantiating an abstract type in a non-abstract model is not allowed."
	static final def String REDEFINITION_OF_CLASS_FEATURES(String feature, String superclass) '''Redefinition of features is not allowed: The feature "«feature»" is already defined in super class "«superclass»".'''
	static final def String TRIPLE_RULE_INSTANTIATION_ERROR(String section) '''The class you want to instantiate must be from a metamodel that is given in the TripleGrammar's «section» part.'''
	static final String COMPLEX_EDGE_WITH_OPERATOR = "Green/Red edges with multiple types are not allowed"
	static final String EDGE_WITH_OPERATOR_AND_PATH_LENGTH = "Green/Red edges with path lengths are not allowed"
	static final def String NO_COMMON_SUBTYPE(String name) '''The type «name» in your refinements cannot be merged into a common subtype.'''
	static final def String NON_MATCHING_ATTR_VALUES(String name) '''The value of «name» does not match your other refinements'''
	static final def String DIFFERENT_TYPES_OF_ATTRIBUTES(String type1, String type2) '''The types of the properties you are trying to refine are not compatible. The types «type1» and «type2» must be the same.'''
	static final String DIFFERENT_OPERATORS_OF_PROPERTIES = "The operators of the properties you are trying to refine are not compatible."
	static final String FORBIDDEN_ACTIONS = "Actions are not allowed here."
	static final String FORBIDDEN_NAMES_IN_EDGES = "Names in normal edges (only one type) are not allowed."
	static final String COLORED_EDGES_ADJACENT_TO_COLORED_NODES = "Edges adjacent to green/red nodes must be green/red"
	static final def String ONLY_RED_AND_GREEN_ELEMENTS(String type, String name) '''The «type»s called "«name»" in your refinements appear only red and green which is not allowed. To fix this, repeat this «type» without an operator.'''

	/**
	 * Checks if the value given in ModelPropertyStatements is of the type that was defined for it 
	 * in the metamodel.
	 */
	@Check
	def checkPropertyStatementOfNodeBlock(ModelPropertyStatement p) {
		if (p.type instanceof MetamodelPropertyStatement) {
			if (p.type.type instanceof BuiltInType) {
				var propertyType = (p.type.type as BuiltInType).reference

				if (!(p.value instanceof PrimitiveInt && propertyType == BuiltInDataTypes.EINT) &&
					!(p.value instanceof PrimitiveBoolean && propertyType == BuiltInDataTypes.EBOOLEAN) &&
					!(p.value instanceof PrimitiveString && propertyType == BuiltInDataTypes.ESTRING) &&
					!(p.value instanceof AttributeExpression &&
						isOfCorrectType(p.value as AttributeExpression, p.type.type)))
					error(WRONG_PROPERTY_TYPE + propertyType.getName,
						EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__VALUE)
			} else if (p.type.type instanceof UserDefinedType) {
				var propertyType = (p.type.type as UserDefinedType).reference
				var literals = propertyType.literals
				if (!(p.value instanceof EnumValue && literals.contains((p.value as EnumValue).literal))) {
					error(WRONG_PROPERTY_TYPE + propertyType.getName,
						EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__VALUE)
				}
			}
		}
	}

	/**
	 * Helper method for checking the values of AttributeExpressions for correctness in respect of the
	 * metamodel.
	 */
	def isOfCorrectType(AttributeExpression attrExpr, DataType type) {
		if (attrExpr.target instanceof NodeAttributeExpTarget) {
			(attrExpr.target as NodeAttributeExpTarget).attribute.type.equals(type)
		} else if (attrExpr.target instanceof LinkAttributeExpTarget) {
			return (attrExpr.target as LinkAttributeExpTarget).attribute.type.equals(type)
		}
	}

	/**
	 * Tries to flatten the given Entity to find out if there are non-mergeable objects/statements etc.
	 * If an error occurs, an appropriate error message is shown.
	 */	
	@Check(NORMAL)
	def checkFlatteningOfSuperType(SuperType entity) {
		try {
			EMSLFlattener.flatten(entity);
		} catch (FlattenerException e) {
			if (e.errorType == FlattenerErrorType.INFINITE_LOOP) {
				error(REFINEMENT_LOOP(dispatcher.getName(entity)),
						EMSLPackage.Literals.SUPER_TYPE__SUPER_REFINEMENT_TYPES)
			} else if (e.errorType == FlattenerErrorType.NO_COMMON_SUBTYPE_OF_NODES) {
				var index = 0
				for (nb : dispatcher.getNodeBlocks(entity)) {
					if (nb.type.name.equals(e.nodeBlock.type.name))
						error(NO_COMMON_SUBTYPE(e.nodeBlock.type.name), nb,
							EMSLPackage.Literals.MODEL_NODE_BLOCK__TYPE, index)
					index++
				}

			} else if (e.errorType == FlattenerErrorType.REFINE_ENTITY_WITH_CONDITION) {
				var index = 0
				for (s : entity.superRefinementTypes) {
					if (dispatcher.getName(s.referencedType).equals(dispatcher.getName(e.superEntity)))
						error(CONDITION_IN_SUPER_ENTITY, EMSLPackage.Literals.SUPER_TYPE__SUPER_REFINEMENT_TYPES, index)
					index++
				}

			} else if (e.errorType == FlattenerErrorType.NON_COMPLIANT_SUPER_ENTITY) {
				var dispatcher = dispatcher
				var index = 0
				for (s : dispatcher.getSuperRefinementTypes(entity)) {
					if (((s as RefinementCommand).referencedType.class !== entity.class) &&
							dispatcher.getSuperTypeName(e.superEntity).equals(
								dispatcher.getName((s as RefinementCommand).referencedType as SuperType))) {
						error(ENTITY_TYPE_NOT_SUPPORTED, entity,
							EMSLPackage.Literals.SUPER_TYPE__SUPER_REFINEMENT_TYPES, index)
					}
					index++
				}
			} else if (e.errorType == FlattenerErrorType.NO_COMMON_SUBTYPE_OF_PROPERTIES) {
				for (nb : dispatcher.getNodeBlocks(entity)) {
					for (propertyStatement : nb.properties) {
						if (propertyStatement.type.name.equals(e.property1.type.name))
							error(DIFFERENT_TYPES_OF_ATTRIBUTES(
									(e.property1 as ModelPropertyStatement).type.name, (e.property2 as ModelPropertyStatement).type.name),
								propertyStatement, EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE)
					}
				}
			} else if (e.errorType == FlattenerErrorType.PROPS_WITH_DIFFERENT_OPERATORS) {
				for (nb : dispatcher.getNodeBlocks(entity)) {
					for (propertyStatement : nb.properties) {
						if (propertyStatement.type.name.equals(e.property1.type.name))
							error(DIFFERENT_OPERATORS_OF_PROPERTIES,
								propertyStatement, EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE)
					}
					for (relationStatement : nb.relations) {
						for (propertyStatement : relationStatement.properties) {
							if (propertyStatement.type.name.equals(e.property1.type.name)) {
								error(DIFFERENT_OPERATORS_OF_PROPERTIES,
									propertyStatement, EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE)
							}
						}
					}
				}
			} else if (e.errorType == FlattenerErrorType.PROPS_WITH_DIFFERENT_VALUES) {
				for (nb : dispatcher.getNodeBlocks(entity)) {
					for (propertyStatement : nb.properties) {
						if (propertyStatement.type.name.equals(e.property1.type.name)) {
							error(NON_MATCHING_ATTR_VALUES(e.property1.type.name),
								propertyStatement, EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE)
						}
					}
					for (relationStatement : nb.relations) {
						for (propertyStatement : relationStatement.properties) {
							if (propertyStatement.type.name.equals(e.property1.type.name)) {
								error(NON_MATCHING_ATTR_VALUES(e.property2.type.name), propertyStatement,
									EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE)
							}
						}
					}
				}
			} else if (e.errorType == FlattenerErrorType.NON_RESOLVABLE_PROXY) {
				for (nb : dispatcher.getNodeBlocks(entity)) {
					if (nb.name.equals((e.relation.eContainer as ModelNodeBlock).name)) {
						error(
							NON_RESOLVABLE_PROXY,
							nb,
							EMSLPackage.Literals.MODEL_NODE_BLOCK__NAME
						)
					}
				}
			} else if (e.errorType == FlattenerErrorType.ONLY_RED_AND_GREEN_NODES) {
				error(ONLY_RED_AND_GREEN_ELEMENTS("node", (e.elements.get(0) as ModelNodeBlock).name), entity, EMSLPackage.Literals.SUPER_TYPE__NAME)
			} else if (e.errorType == FlattenerErrorType.ONLY_RED_AND_GREEN_EDGES) {
				error(ONLY_RED_AND_GREEN_ELEMENTS("edge", (e.elements.get(0) as ModelRelationStatement).types.get(0).type.name), 
						entity, EMSLPackage.Literals.SUPER_TYPE__NAME)
			}
		}
	}
	
	/**
	 * Helper method to handle Exceptions that are not entity specific.
	 */
	def handleCommonFlattenerExceptions(FlattenerException e, SuperType entity) {
		
	}

	@Check(NORMAL)
	def checkForEntitiesWithSameName(EMSL_Spec spec) {
		var namelistsOfEntities = new HashMap<String, ArrayList<String>>();
		namelistsOfEntities.put("Pattern", new ArrayList<String>())
		namelistsOfEntities.put("Rule", new ArrayList<String>())
		namelistsOfEntities.put("Model", new ArrayList<String>())
		namelistsOfEntities.put("Metamodel", new ArrayList<String>())
		namelistsOfEntities.put("TripleRule", new ArrayList<String>())
		namelistsOfEntities.put("TripleGrammar", new ArrayList<String>())
		namelistsOfEntities.put("GraphGrammar", new ArrayList<String>())
		namelistsOfEntities.put("Constraint", new ArrayList<String>())

		for (entity : spec.entities) {
			var dispatcher = dispatcher
			if (!namelistsOfEntities.get(entity.eClass.name).contains(dispatcher.getName(entity))) {
				namelistsOfEntities.get(entity.eClass.name).add(dispatcher.getName(entity))
			} else {
				if (entity instanceof Rule)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity, EMSLPackage.Literals.SUPER_TYPE__NAME)
				else if (entity instanceof Model)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity, EMSLPackage.Literals.SUPER_TYPE__NAME)
				else if (entity instanceof Metamodel)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity, EMSLPackage.Literals.METAMODEL__NAME)
				else if (entity instanceof TripleRule)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity, EMSLPackage.Literals.SUPER_TYPE__NAME)
				else if (entity instanceof TripleGrammar)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity, EMSLPackage.Literals.TRIPLE_GRAMMAR__NAME)
				else if (entity instanceof GraphGrammar)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity, EMSLPackage.Literals.GRAPH_GRAMMAR__NAME)
				else if (entity instanceof Constraint)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity, EMSLPackage.Literals.CONSTRAINT__NAME)
				else if (entity instanceof Pattern)
					error(SAME_NAMES_OF_ENTITIES(entity.eClass.name), entity.body, EMSLPackage.Literals.SUPER_TYPE__NAME)
			}
		}
	}

	/**
	 * We are currently able to handle if/else structures as constraints only if they are not mixed with other constraints.
	 */
	@Check(NORMAL)
	def void checkIfElseAsConstraint(Constraint c) {
		if (c.body instanceof Implication)
			return
		else
			forbidIfElseAsChild(c.body, [o |
			error('''If/else conditions such as "if «o.premise.name» then «o.conclusion.name»" cannot be referenced from other constraints.''',
				EMSLPackage.Literals.CONSTRAINT__BODY)
		])
	}

	/**
	 * We currently do not support arbitrarily deep nesting
	 */
	@Check(NORMAL)
	def void forbidNesting(NegativeConstraint np) {
		val atomicPattern = np.pattern
		val parentPattern = atomicPattern.eContainer as Pattern
		if(parentPattern !== null && parentPattern.condition !== null){
			error('''Forbidden patterns cannot have conditions as arbitrary nesting is not supported.''',
					EMSLPackage.Literals.NEGATIVE_CONSTRAINT__PATTERN)
		}
	}

	/**
	 * We currently do not support arbitrarily deep nesting
	 */
	@Check(NORMAL)
	def void forbidNesting(PositiveConstraint pp) {
		val atomicPattern = pp.pattern
		val parentPattern = atomicPattern.eContainer as Pattern
		if(parentPattern !== null && parentPattern.condition !== null){
			error('''Enforced patterns cannot have conditions as arbitrary nesting is not supported.''',
					EMSLPackage.Literals.POSITIVE_CONSTRAINT__PATTERN)
		}
	}

	/**
	 * We currently do not support arbitrarily deep nesting
	 */
	@Check(NORMAL)
	def void forbidNesting(Implication ip) {
		val premise = ip.premise
		val conclusion = ip.conclusion

		val premisePattern = premise.eContainer as Pattern
		val conclusionPattern = conclusion.eContainer as Pattern

		if (premisePattern !== null && premisePattern.condition !== null) {
			error('''Patterns used as premise cannot have conditions as arbitrary nesting is not supported.''',
				EMSLPackage.Literals.IMPLICATION__PREMISE)
		}

		if (conclusionPattern !== null && conclusionPattern.condition !== null) {
			error('''Patterns used as conclusion cannot have conditions as arbitrary nesting is not supported.''',
				EMSLPackage.Literals.IMPLICATION__CONCLUSION)
		}
	}

	/**
	 * We are currently unable to handle if/else structures as application conditions for patterns.
	 * This is related to the transformation to the underlying pattern matcher.
	 */
	@Check(NORMAL)
	def void forbidIfElseAsChild(Pattern p) {
		if (p.condition !== null) {
			forbidIfElseAsChild(p.condition, [o |
				error('''If/else conditions such as "if «o.premise.name» then «o.conclusion.name»" are currently not supported as part of application conditions''',
					EMSLPackage.Literals.PATTERN__CONDITION)
			])
		}
	}

	private def void forbidIfElseAsChild(Condition c, Consumer<Implication> error) {
		var allConditions = new ArrayList<EObject>()
		allConditions.add(c)
		allConditions.addAll(c.eAllContents.toList)
		allConditions.filter[it instanceof Implication].forEach [
			error.accept(it as Implication)
		]

		allConditions.filter[it instanceof ConstraintReference].forEach [
			var refCond = (it as ConstraintReference).reference.body
			forbidIfElseAsChild(refCond, error)
		]
	}

	/**
	 * Checks if a modelNodeBlock's action contains an operator. As this does not make sense for models
	 * an error message is shown.
	 */
	@Check
	def void forbidOperatorsInModelNodeBlocks(ModelNodeBlock m) {
		if (!(m.eContainer instanceof Rule) && !(m.eContainer instanceof TripleRule)) {
			if (m.action !== null) {
				error(FORBIDDEN_ACTIONS, m, EMSLPackage.Literals.MODEL_NODE_BLOCK__ACTION)
			}
			for (r : m.relations) {
				if (r.action !== null) {
					error(FORBIDDEN_ACTIONS, r, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__ACTION)
				}
			}
		}
	}
	
	/**
	 * Checks if multiple nodeBlocks in a metamodel have the same name. If so, an error is produced.
	 */
	@Check(NORMAL)
	def void forbidNodeBlocksWithSameNameInMetamodel(Metamodel entity) {
		var namesList = new HashSet<String>()
		for (nb : entity.nodeBlocks) {
			if (!namesList.contains(nb.name))
				namesList.add(nb.name)
			else
				error(SAME_NAMES_OF_OBJECTS_IN_ENTITY, nb, EMSLPackage.Literals.METAMODEL_NODE_BLOCK__NAME)		
		}
	}
	
	/**
	 * Checks if multiple nodeBlocks and/or edges in an entity have the same name.
	 * This is forbidden because Relabeling of nodeBlocks and/or edges would no longer work.
	 */
	@Check
	def void forbidNodeBlockAndEdgeWithSameName(Entity entity) {
		var dispatcher = dispatcher
		var namesList = new ArrayList
		
		var listOfNodeBlocks = new ArrayList;
		if(entity instanceof Pattern)
			listOfNodeBlocks.addAll(dispatcher.getPatternNodeBlocks(entity))
		else if(entity instanceof SuperType)
			listOfNodeBlocks.addAll(dispatcher.getNodeBlocks(entity))
		
		if (!(entity instanceof TripleRule)) {
			for (nb : listOfNodeBlocks) {
				if (!namesList.contains(nb.name)) {
					namesList.add(nb.name)
				} else {
					error(SAME_NAMES_OF_OBJECTS_IN_ENTITY, nb, EMSLPackage.Literals.MODEL_NODE_BLOCK__NAME)
				}
				for (rel : nb.relations) {
					if (rel.name !== null && !namesList.contains(rel.name)) {
						namesList.add(rel.name)
					} else if (rel.name !== null) {
						error(SAME_NAMES_OF_OBJECTS_IN_ENTITY, rel, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__NAME)
					}
				}
			}
		} else if (entity instanceof TripleRule) {
			for (nb : entity.srcNodeBlocks) {
				if (!namesList.contains(nb.name)) {
					namesList.add(nb.name)
				} else {
					error(SAME_NAMES_OF_OBJECTS_IN_ENTITY, nb, EMSLPackage.Literals.MODEL_NODE_BLOCK__NAME)
				}
				for (rel : nb.relations) {
					if (rel.name !== null && !namesList.contains(rel.name)) {
						namesList.add(rel.name)
					} else if (rel.name !== null) {
						error(SAME_NAMES_OF_OBJECTS_IN_ENTITY, rel, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__NAME)
					}
				}
			}
			for (nb : entity.trgNodeBlocks) {
				if (!namesList.contains(nb.name)) {
					namesList.add(nb.name)
				} else {
					error(SAME_NAMES_OF_OBJECTS_IN_ENTITY, nb, EMSLPackage.Literals.MODEL_NODE_BLOCK__NAME)
				}
				for (rel : nb.relations) {
					if (rel.name !== null && !namesList.contains(rel.name)) {
						namesList.add(rel.name)
					} else if (rel.name !== null) {
						error(SAME_NAMES_OF_OBJECTS_IN_ENTITY, rel, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__NAME)
					}
				}
			}
		}
	}
	
	/**
	 * Checks if a normal edge (edges that has only one given type) has a name. If so, an error is produced.
	 */
	@Check
	def void forbidNamesInNormalEdges(ModelRelationStatement relation) {
		if (relation.types.size == 1 && relation.name !== null) {
			error(FORBIDDEN_NAMES_IN_EDGES, relation, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__NAME)
		}
	}
	
	/**
	 * Checks if a complex edge has a name. If not, an error is produced.
	 */
	@Check
	def void enforceNamesInComplexEdges(ModelRelationStatement relation) {
		if (relation.types.size > 1 && relation.name === null) {
			error(FORBIDDEN_NAMES_IN_EDGES, relation, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TYPES)
		}
	}
	
	/**
	 * Checks if edges adjacent to green/red nodes are green/red themselves.
	 */
	@Check
	def void checkOperatorsOfEdgesAdjacentToNodes(ModelRelationStatement relation) {
		if (relation.eContainer.eContainer instanceof Rule || relation.eContainer.eContainer instanceof TripleRule) {
			if (relation.action === null && relation.target?.action !== null 
					|| (relation.action === null && (relation.eContainer as ModelNodeBlock).action !== null)) {
				error(COLORED_EDGES_ADJACENT_TO_COLORED_NODES, relation, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TYPES)
			}
		}
	}
	
	/**
	 * Checks if a green node in a Rule or TripleRule is of abstract type.
	 * Because abstract types cannot be created in a rule or TripleRule this is
	 * forbidden by the editor.
	 */
	@Check
	def void forbidGreenNodesOfAbstractTypesInRule(ModelNodeBlock nb) {
		if ((nb.eContainer instanceof Rule && !(nb.eContainer as Rule).abstract 
					|| nb.eContainer instanceof TripleRule && !(nb.eContainer as TripleRule).abstract) 
				&& nb.action !== null && nb.type.abstract) {
			error(GREEN_NODE_OF_ABSTRACT_TYPES, nb, EMSLPackage.Literals.MODEL_NODE_BLOCK__ACTION)
		}
	}
	
	/**
	 * Checks if a non-abstract model instantiates abstract classes.
	 */
	@Check
	def void forbidInstancesOfAbstractTypesInNonAbstractModels(Model model) {
		for (nb : model.nodeBlocks) {
			if (!(model.abstract) && nb.type.abstract) {
				error(INSTANTIATING_ABSTRACT_TYPE_IN_NON_ABSTRACT_MODEL, 
					nb, EMSLPackage.Literals.MODEL_NODE_BLOCK__TYPE)
			}
		}
	}
	
	/**
	 * Checks if a model contains complex edges (edges with multiple types). Since they have
	 * no meaning in models, it is forbidden.
	 */
	@Check
	def void forbidComplexEdgesInModels(ModelRelationStatement relation) {
		if (relation.types.size > 1 && relation.eContainer.eContainer instanceof Model) {
			error(FORBIDDING_COMPLEX_EDGES, relation, EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TYPES)
		}
	}
	
	/**
	 * Checks if a class of a metamodel redefines a property of its superclasses
	 */
	@Check
	def void forbidRedefinitionOfClassFeatures(Metamodel entity) {
		for (nb : entity.nodeBlocks) {
			if (!nb.superTypes.isEmpty) {
				nb.properties.forEach[p |
					nb.superTypes.forEach[st |
						findPropertyRedefinitions(p, st)
					]
				]
				nb.relations.forEach[r |
					nb.superTypes.forEach[st |
						findRelationRedefinitions(r, st)
					]
				]
			}
		}
	}
	
	/**
	 * Helper method to find redefinitions of properties recursively.
	 */
	def void findPropertyRedefinitions(MetamodelPropertyStatement property, MetamodelNodeBlock superclass) {
		if (superclass.properties.map[sp | sp.name].contains(property.name)) {
			error(REDEFINITION_OF_CLASS_FEATURES(property.name, superclass.name), property, EMSLPackage.Literals.METAMODEL_PROPERTY_STATEMENT__NAME)
		}
		superclass.superTypes.forEach[s | findPropertyRedefinitions(property, s)]
	}
	
	/**
	 * Helper method to find redefinitions of relations recursively.
	 */
	def void findRelationRedefinitions(MetamodelRelationStatement relation, MetamodelNodeBlock superclass) {
		if (superclass.relations.map[sr | sr.name].contains(relation.name)) {
			error(REDEFINITION_OF_CLASS_FEATURES(relation.name, superclass.name), relation, EMSLPackage.Literals.METAMODEL_RELATION_STATEMENT__NAME)
		}
		superclass.superTypes.forEach[s | findRelationRedefinitions(relation, s)]
	}
	
	/**
	 * Checks if the nodeblocks of a tripleRule instantiate only classes that are contained in the metamodels
	 * that are specified in the rule's grammar.
	 */
	@Check
	def void checkInstantiationOfMetamodelsInTripleRule(TripleRule tripleRule) {
		for (nb : tripleRule.srcNodeBlocks) {
			tripleRule.type.srcMetamodels.map[m | m.nodeBlocks].forEach[l | 
				if (!l.contains(nb.type))
					error(TRIPLE_RULE_INSTANTIATION_ERROR("source"), nb, EMSLPackage.Literals.MODEL_NODE_BLOCK__TYPE)
			]
		}
		for (nb : tripleRule.trgNodeBlocks) {
			tripleRule.type.trgMetamodels.map[m | m.nodeBlocks].forEach[l | 
				if (!l.contains(nb.type))
					error(TRIPLE_RULE_INSTANTIATION_ERROR("target"), nb, EMSLPackage.Literals.MODEL_NODE_BLOCK__TYPE)
			]
		}
	}
	
	/**
	 * Checks if a red/green edge (inside rule/tripleRule) is a complex edge.
	 * Since this can not be handled it is forbidden.
	 */
	@Check (NORMAL)
	def void forbidComplexEdgesInGreenRedEdges (ModelRelationStatement relation) {
		if (relation.action !== null) {
			if (relation.types.size > 1) {
				error(COMPLEX_EDGE_WITH_OPERATOR, relation.types.get(1), EMSLPackage.Literals.MODEL_RELATION_STATEMENT_TYPE__TYPE)
			} else if (relation.types.get(0)?.lower !== null) {
				error(EDGE_WITH_OPERATOR_AND_PATH_LENGTH, relation.types.get(0), EMSLPackage.Literals.MODEL_RELATION_STATEMENT_TYPE__LOWER)
			}
		}
	}
}
