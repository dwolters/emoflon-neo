/*
 * generated by Xtext 2.16.0
 */
package org.emoflon.neo.emsl.scoping

import java.util.HashMap
import java.util.HashSet
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.SimpleAttributeResolver
import org.emoflon.neo.emsl.eMSL.AtomicPattern
import org.emoflon.neo.emsl.eMSL.AttributeExpression
import org.emoflon.neo.emsl.eMSL.Correspondence
import org.emoflon.neo.emsl.eMSL.EMSLPackage
import org.emoflon.neo.emsl.eMSL.EMSL_Spec
import org.emoflon.neo.emsl.eMSL.EnumValue
import org.emoflon.neo.emsl.eMSL.ImportStatement
import org.emoflon.neo.emsl.eMSL.Metamodel
import org.emoflon.neo.emsl.eMSL.MetamodelNodeBlock
import org.emoflon.neo.emsl.eMSL.MetamodelRelationStatement
import org.emoflon.neo.emsl.eMSL.Model
import org.emoflon.neo.emsl.eMSL.ModelNodeBlock
import org.emoflon.neo.emsl.eMSL.ModelPropertyStatement
import org.emoflon.neo.emsl.eMSL.ModelRelationStatement
import org.emoflon.neo.emsl.eMSL.ModelRelationStatementType
import org.emoflon.neo.emsl.eMSL.NodeAttributeExpTarget
import org.emoflon.neo.emsl.eMSL.RefinementCommand
import org.emoflon.neo.emsl.eMSL.Rule
import org.emoflon.neo.emsl.eMSL.SuperType
import org.emoflon.neo.emsl.eMSL.TripleRule
import org.emoflon.neo.emsl.eMSL.UserDefinedType
import org.emoflon.neo.emsl.util.EMSLUtil
import org.emoflon.neo.emsl.eMSL.LinkAttributeExpTarget
import org.emoflon.neo.emsl.util.EntityAttributeDispatcher
import org.emoflon.neo.emsl.eMSL.Constraint

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class EMSLScopeProvider extends AbstractEMSLScopeProvider {

	override getScope(EObject context, EReference reference) {
		if (superTypeOfMetamodelNodeBlock(context, reference)) {
			return handleNodeBlockSuperTypesInMetamodel(context as MetamodelNodeBlock, reference)
		}

		if (typeOfNodeBlock(context, reference)) {
			if (isInModel(context as ModelNodeBlock))
				return handleNodeBlockTypesInModel(context as ModelNodeBlock, reference)
			else if (isInPattern(context as ModelNodeBlock))
				return handleNodeBlockTypesInPattern(context as ModelNodeBlock, reference)
			else if (isInRule(context as ModelNodeBlock))
				return handleNodeBlockTypesInRule(context as ModelNodeBlock, reference)
			else if (isInTripleRule(context as ModelNodeBlock))
				return handleNodeBlockTypesInTripleRule(context as ModelNodeBlock, reference)
		}

		if (valueOfRelationStatementInModel(context, reference))
			return handleValueOfRelationStatementInModel(context as ModelRelationStatement, reference)

		if (valueOfRelationStatementInMetamodel(context, reference))
			return handleValueOfRelationStatementInMetamodel(context as MetamodelRelationStatement, reference)

		if (valueOfRelationStatementInRule(context, reference))
			return handleValueOfRelationStatementInRule(context as ModelRelationStatement, reference)

		if (valueOfRelationStatementInPattern(context, reference))
			return handleValueOfRelationStatementInPattern(context as ModelRelationStatement, reference)

		if (valueOfRelationStatementInTripleRule(context, reference)) {
			return handleValueOfRelationStatementInTripleRule(context as ModelRelationStatement, reference)
		}

		if (typeOfRelationStatementInModelRelationStatement(context, reference)) {
			return handleTypeOfRelationStatementInModelRelationStatement(context as ModelRelationStatement,
				context.eContainer as ModelNodeBlock)
		}

		if (typeOfRelationStatementInModelRelationStatementType(context, reference)) {
			return handleTypeOfRelationStatementInModelRelationStatementType(context.eContainer as ModelRelationStatement,
				context.eContainer.eContainer as ModelNodeBlock)
		}

		if (typeOfRelationStatementInModelNodeBlock(context, reference))
			return handleTypeOfRelationStatementInModelRelationStatementType(context as ModelNodeBlock)

		if (nameOfPropertyStatement(context, reference)) {
			return handleTypeOfPropertyStatementInModelNodeBlock(context as ModelPropertyStatement,
				context.eContainer as ModelNodeBlock)
		}

		if (typeOfPropertyStatementInRelationStatement(context, reference))
			return handleTypeOfPropertyStatementInRelationStatement(context as ModelPropertyStatement, reference)

		if (valueOfEnumInPropertyStatementInModel(context, reference))
			return handleValueOfEnumInPropertyStatementInModel((context as EnumValue), reference)

		if (valueOfNodeAttributeExpression(context, reference)) {
			return handleNodeAttributeExpression(context as NodeAttributeExpTarget)
		}
		
		if (nameOfSuperRefinementTypeOfSuperType(context, reference))
			return handleNameOfSuperRefinementTypeOfSuperType(context, reference)
			
		if (nameOfSuperRefinementTypeOfInRefinementCommand(context, reference))
			return handleNameOfSuperRefinementTypeOfModelInRefinementCommand(context, reference)
			
		if (sourceOfCorrespondence(context, reference))
			return handleSourceOfCorrespondence(context, reference)
			
		if (targetOfCorrespondence(context, reference))
			return handleTargetOfCorrespondence(context as Correspondence, reference)
		
		if (typeOfCorrespondence(context, reference))
			return handleTypeOfCorrespondence(context as Correspondence, reference)
			
		if (linkAttributeExpressionTargetsLink(context, reference))
			return handleLinkAttributeExpressionTargetsLink(context, reference)
		
		if (linkAttributeExpressionTargetsAttribute(context, reference))
			return handleLinkAttributeExpressionTargetsAttribute(context, reference)
			
		if (linkAttributeExpressionTargetsTarget(context, reference))
			return handleLinkAttributeExpressionTargetsTarget(context, reference)
		
		if (patternInApplicationCondition(context, reference))
			return handlePatternInApplicationCondition(context, reference)
			
		if (constraintReferenceInApplicationCondition(context, reference))
			return handleConstraintReferenceInApplicationCondition(context, reference)

		return super.getScope(context, reference)
	}
	
	private def linkAttributeExpressionTargetsTarget(EObject context, EReference reference) {
		reference == EMSLPackage.Literals.LINK_ATTRIBUTE_EXP_TARGET__TARGET
	}

	/*---------------------------------*/
	/*----------- Metamodels ----------*/
	/*---------------------------------*/
	private def superTypeOfMetamodelNodeBlock(EObject context, EReference reference) {
		context instanceof MetamodelNodeBlock && reference == EMSLPackage.Literals.METAMODEL_NODE_BLOCK__SUPER_TYPES
	}

	private def handleNodeBlockSuperTypesInMetamodel(MetamodelNodeBlock context, EReference reference) {
		handleNodeBlockTypesInRule(context, reference)
	}
	
	
	
	/*------------------------------------------*/
	/*----------- SuperRefinementType ----------*/
	/*------------------------------------------*/
	private def nameOfSuperRefinementTypeOfSuperType(EObject context, EReference reference) {
		context instanceof SuperType &&	reference == EMSLPackage.Literals.REFINEMENT_COMMAND__REFERENCED_TYPE
	}
	
	private def nameOfSuperRefinementTypeOfInRefinementCommand(EObject context, EReference reference) {
		context instanceof RefinementCommand &&	reference == EMSLPackage.Literals.REFINEMENT_COMMAND__REFERENCED_TYPE
	}
	
	private def handleNameOfSuperRefinementTypeOfSuperType(EObject context, EReference reference) {
		val root = EcoreUtil2.getRootContainer(context)
		determineScope(allTypesInAllImportedMetamodels(root, context.class))
	}
	
	private def handleNameOfSuperRefinementTypeOfModelInRefinementCommand(EObject context, EReference reference) {
		val root = EcoreUtil2.getRootContainer(context)
		determineScope(allTypesInAllImportedMetamodels(root, context.eContainer.class))
	}

	/*----------------------------------------*/
	/*----------- PropertyStatement ----------*/
	/*----------------------------------------*/
	/**
	 * Returns whether to build a scope for the name of a PropertyStatement.
	 */
	private def nameOfPropertyStatement(EObject context, EReference reference) {
		context instanceof ModelPropertyStatement && reference == EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE &&
			!(context.eContainer instanceof ModelRelationStatement)
	}

	/**
	 * Returns the scope for the name of a PropertyStatement.
	 */
	private def handleTypeOfPropertyStatementInModelNodeBlock(ModelPropertyStatement prop, ModelNodeBlock container) {
		val nodeBlocks = thisAndAllSuperTypes(container.type)
		val possibilities = new HashMap
		for (nb : nodeBlocks) {
			(nb as MetamodelNodeBlock).properties.forEach[r|possibilities.put(r, null)]
		}

		determineScope(possibilities)
	}

	private def thisAndAllSuperTypes(MetamodelNodeBlock type) {
		var st = EMSLUtil.thisAndAllSuperTypes(type)
		val root = EcoreUtil2.getRootContainer(type)

		// Check if EObject is imported: if yes, then it is a supertype for everything
		val eObject = allNodeBlocksInAllImportedMetamodels(root).keySet.findFirst[it.name == "EObject"]
		if (eObject !== null)
			st.add(eObject)
		return st
	}

	/**
	 * Returns whether to build a scope for the name of a PropertyStatements that is nested in a RelationStatement.
	 */
	private def typeOfPropertyStatementInRelationStatement(EObject context, EReference reference) {
		context instanceof ModelPropertyStatement && reference == EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE
	}

	/**
	 * Returns the scope for the name of a PropertyStatement that is nested in a RelationStatement.
	 */
	private def handleTypeOfPropertyStatementInRelationStatement(ModelPropertyStatement context, EReference reference) {
		val properties = new HashSet()		
		(context.eContainer as ModelRelationStatement).types.forEach[t | properties.addAll(t.type.properties)]

		Scopes.scopeFor(properties)
	}

	private def handleValueOfEnumInPropertyStatementInModel(EnumValue property, EReference reference) {
		val type = (property.eContainer() as ModelPropertyStatement).type.type
		if (type instanceof UserDefinedType) {
			val enum = type.reference
			return Scopes.scopeFor(enum.literals)
		}

		return super.getScope(property, reference)
	}

	def valueOfEnumInPropertyStatementInModel(EObject context, EReference reference) {
		context instanceof EnumValue && reference == EMSLPackage.Literals.ENUM_VALUE__LITERAL
	}

	def handleNodeAttributeExpression(NodeAttributeExpTarget expression) {
		var exp = expression.eContainer as AttributeExpression
		var types = thisAndAllSuperTypes(exp.node.type)
		return Scopes.scopeFor(types.flatMap[t|t.properties])
	}

	def valueOfNodeAttributeExpression(EObject context, EReference reference) {
		context instanceof NodeAttributeExpTarget &&
			reference == EMSLPackage.Literals.ATTRIBUTE_EXP_TARGET__ATTRIBUTE
	}
	
	private def handleLinkAttributeExpressionTargetsTarget(EObject context, EReference reference) {
		val nodes = new HashMap()
		if (context instanceof LinkAttributeExpTarget) {
			(context.eContainer as AttributeExpression).node.relations.forEach[r |
				if (r.types.map[t | t.type].contains(context.link) && r.target.type == context.link.target) {
					nodes.put(r.target, null)
				}
			]
		}
		determineScope(nodes)
	}
	
	private def linkAttributeExpressionTargetsAttribute(EObject context, EReference reference) {
		context instanceof LinkAttributeExpTarget &&
		reference == EMSLPackage.Literals.ATTRIBUTE_EXP_TARGET__ATTRIBUTE
	}
	
	private def handleLinkAttributeExpressionTargetsAttribute(EObject context, EReference reference) {
		val propertyTypes = new HashSet()
		if (context instanceof LinkAttributeExpTarget) {
			(context.eContainer as AttributeExpression).node.relations.forEach[r |
				r.properties.forEach[p | propertyTypes.add(p.type)]
			]
		}
		Scopes.scopeFor(propertyTypes)
	}
	
	private def linkAttributeExpressionTargetsLink(EObject context, EReference reference) {
		reference == EMSLPackage.Literals.LINK_ATTRIBUTE_EXP_TARGET__LINK
	}
	
	private def handleLinkAttributeExpressionTargetsLink(EObject exp, EReference reference) {
		val relationTypes = new HashMap()
		if (exp instanceof ModelRelationStatement) {
			new EntityAttributeDispatcher().getNodeBlocks(exp.eContainer.eContainer as SuperType).forEach[n | n.relations.forEach[r | r.types.forEach[t | relationTypes.put(t.type, null)]]]
		} else if (exp instanceof LinkAttributeExpTarget && exp.eContainer.eContainer.eContainer instanceof ModelRelationStatement) {
			new EntityAttributeDispatcher().getNodeBlocks(exp.eContainer.eContainer.eContainer.eContainer.eContainer as SuperType).forEach[n | n.relations.forEach[r | r.types.forEach[t | relationTypes.put(t.type, null)]]]
		} else if (exp instanceof LinkAttributeExpTarget && exp.eContainer.eContainer instanceof ModelPropertyStatement) {
			new EntityAttributeDispatcher().getNodeBlocks(exp.eContainer.eContainer.eContainer.eContainer as SuperType).forEach[n | n.relations.forEach[r | r.types.forEach[t | relationTypes.put(t.type, null)]]]
		}
		determineScope(relationTypes)
	}

	/*----------------------------------------*/
	/*----------- RelationStatement ----------*/
	/*----------------------------------------*/
	/**
	 * Returns whether to build a scope for the name of a RelationStatement.
	 */
	def typeOfRelationStatementInModelRelationStatement(EObject context, EReference reference) {
		context instanceof ModelRelationStatement &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT_TYPE__TYPE
	}

	/**
	 * Returns whether to build a scope for the name of a RelationStatement.
	 */
	def typeOfRelationStatementInModelRelationStatementType(EObject context, EReference reference) {
		context instanceof ModelRelationStatementType &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT_TYPE__TYPE
	}

	/**
	 * Returns whether to build a scope for the name of a RelationStatement.
	 */
	def typeOfRelationStatementInModelNodeBlock(EObject context, EReference reference) {
		context instanceof ModelNodeBlock && reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT_TYPE__TYPE
	}

	/**
	 * Returns the scope for the name of a RelationStatement.
	 */
	def handleTypeOfRelationStatementInModelRelationStatement(ModelRelationStatement context,
		ModelNodeBlock container) {
		if (context.name !== null) {
			determineScope(allTypesInAllImportedMetamodels(EcoreUtil.getRootContainer(context), MetamodelRelationStatement))
		} else {
			val relations = new HashSet()
			thisAndAllSuperTypes(container.type).forEach[t | relations.addAll(t.relations)]
			Scopes.scopeFor(relations)
		}
	}

	/**
	 * Returns the scope for the name of a RelationStatement.
	 */
	def handleTypeOfRelationStatementInModelRelationStatementType(ModelRelationStatement relation, ModelNodeBlock container) {
		if (relation.name !== null) {
			determineScope(allTypesInAllImportedMetamodels(EcoreUtil.getRootContainer(relation), MetamodelRelationStatement))
		} else {
			val relations = new HashSet()
			thisAndAllSuperTypes(container.type).forEach[t | relations.addAll(t.relations)]
			Scopes.scopeFor(relations)
		}
	}
	
	/**
	 * Returns the scope for the name of a RelationStatement.
	 */
	def handleTypeOfRelationStatementInModelRelationStatementType(ModelNodeBlock container) {
		determineScope(allTypesInAllImportedMetamodels(EcoreUtil.getRootContainer(container), MetamodelRelationStatement))
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a Rule.
	 */
	def valueOfRelationStatementInRule(EObject context, EReference reference) {
		context instanceof ModelRelationStatement &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof Rule
	}

	/**
	 * Returns the scope for the value of a RelationStatement in a Rule.
	 */
	def handleValueOfRelationStatementInRule(ModelRelationStatement statement, EReference reference) {
		return Scopes.scopeFor(filterForCompatibleSuperTypes(
				new HashSet((statement.eContainer.eContainer as Rule).nodeBlocks), statement
		))
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a TripleRule.
	 */
	def valueOfRelationStatementInTripleRule(EObject context, EReference reference) {
		context instanceof ModelRelationStatement &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof TripleRule
	}

	/**
	 * Returns the scope for the value of a RelationStatement in a TripleRule.
	 */
	private def handleValueOfRelationStatementInTripleRule(ModelRelationStatement statement, EReference reference) {
		val tripleRule = statement.eContainer.eContainer as TripleRule
		val allNodeBlocks = new HashSet()
		if ((statement.eContainer.eContainer as TripleRule).srcNodeBlocks.contains(statement.eContainer))
			allNodeBlocks.addAll(tripleRule.srcNodeBlocks)
		else
			allNodeBlocks.addAll(tripleRule.trgNodeBlocks)

		return Scopes.scopeFor(filterForCompatibleSuperTypes(allNodeBlocks, statement))
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a Pattern.
	 */
	private def valueOfRelationStatementInPattern(EObject context, EReference reference) {
		context instanceof ModelRelationStatement &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof AtomicPattern
	}

	/**
	 * Returns a scope for the value of a RelationStatement in a Pattern.
	 */
	private def handleValueOfRelationStatementInPattern(ModelRelationStatement statement, EReference reference) {
		return Scopes.scopeFor(filterForCompatibleSuperTypes(
				new HashSet((statement.eContainer.eContainer as AtomicPattern).nodeBlocks), statement
		))
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a Model.
	 */
	private def valueOfRelationStatementInModel(EObject context, EReference reference) {
		context instanceof ModelRelationStatement &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof Model
	}

	/**
	 * Returns a scope for the value of a RelationStatement in a Model.
	 */
	private def handleValueOfRelationStatementInModel(ModelRelationStatement statement, EReference reference) {
		return Scopes.scopeFor(filterForCompatibleSuperTypes(
				new HashSet((statement.eContainer.eContainer as Model).nodeBlocks), statement
		))
	}

	private def Iterable<ModelNodeBlock> filterForCompatibleSuperTypes(HashSet<ModelNodeBlock> allNodeBlocks,
		ModelRelationStatement statement) {
		var filteredNodeBlocks = new HashSet()
		for (nb : allNodeBlocks) {
			for (t : statement.types) {
				if (thisAndAllSuperTypes(nb.type).contains((t.type as MetamodelRelationStatement).target))
					filteredNodeBlocks.add(nb)
			}
		}
		return filteredNodeBlocks
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a Model.
	 */
	private def valueOfRelationStatementInMetamodel(EObject context, EReference reference) {
		context instanceof MetamodelRelationStatement &&
			reference == EMSLPackage.Literals.METAMODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof Metamodel
	}

	/**
	 * Returns a scope for the value of a RelationStatement in a Model.
	 */
	private def handleValueOfRelationStatementInMetamodel(MetamodelRelationStatement statement, EReference reference) {
		val metaModel = statement.eContainer.eContainer as Metamodel
		val allNodeBlocks = new HashSet(metaModel.nodeBlocks)
		return Scopes.scopeFor(allNodeBlocks)
	}
	
	/*-------------------------------------*/
	/*---------- Correspondences ----------*/
	/*-------------------------------------*/
	
	/**
	 * Returns whether to create a scope for the source of a Correspondence in a TripleRule.
	 */
	private def sourceOfCorrespondence(EObject context, EReference reference) {
		reference == EMSLPackage.Literals.CORRESPONDENCE__SOURCE
	}
	
	/**
	 * Returns the scope for the source of a Correspondence in a TripleRule.
	 */
	private def handleSourceOfCorrespondence(EObject context, EReference reference) {
		var possibilities = new HashSet
		if (context instanceof TripleRule) {
			possibilities.addAll(context.srcNodeBlocks)
			possibilities.addAll(context.trgNodeBlocks)
		} else if (context instanceof Correspondence) {
			possibilities.addAll((context.eContainer as TripleRule).srcNodeBlocks)
			possibilities.addAll((context.eContainer as TripleRule).trgNodeBlocks)
		}
		Scopes.scopeFor(possibilities)
	}
	
	/**
	 * Returns whether to create a scope for the type of a Correspondence in a TripleRule.
	 */
	private def typeOfCorrespondence(EObject context, EReference reference) {
		context instanceof Correspondence && reference == EMSLPackage.Literals.CORRESPONDENCE__TYPE
	}
	
	/**
	 * Returns the scope for the type of a Correspondence in a TripleRule.
	 */
	private def handleTypeOfCorrespondence(Correspondence context, EReference reference) {
		Scopes.scopeFor((context.eContainer as TripleRule).type.correspondences)
	}
	
	/**
	 * Returns whether to create a scope for the target of a Correspondence in a TripleRule.
	 */
	private def targetOfCorrespondence(EObject context, EReference reference) {
		context instanceof Correspondence && reference == EMSLPackage.Literals.CORRESPONDENCE__TARGET
	}
	
	/**
	 * Returns the scope for the target of a Correspondence in a TripleRule.
	 */
	private def handleTargetOfCorrespondence(Correspondence context, EReference reference) {
		var possibilities = new HashSet()
		possibilities.addAll((context.eContainer as TripleRule).srcNodeBlocks.filter[n | n.type == context.type.target])
		possibilities.addAll((context.eContainer as TripleRule).trgNodeBlocks.filter[n | n.type == context.type.target])
		Scopes.scopeFor(possibilities)
	}
	
	
	/*--------------------------------------------*/
	/*---------- Application Conditions ----------*/
	/*--------------------------------------------*/
	
	
	private def constraintReferenceInApplicationCondition(EObject context, EReference reference) {
		reference == EMSLPackage.Literals.CONSTRAINT_REFERENCE__REFERENCE
	}
	
	private def handleConstraintReferenceInApplicationCondition(EObject context, EReference reference) {
		determineScope(allTypesInAllImportedMetamodels(EcoreUtil.getRootContainer(context), Constraint))
	}
	
	private def patternInApplicationCondition(EObject context, EReference reference) {
		reference == EMSLPackage.Literals.NEGATIVE_CONSTRAINT__PATTERN ||
			reference == EMSLPackage.Literals.POSITIVE_CONSTRAINT__PATTERN ||
			reference == EMSLPackage.Literals.IMPLICATION__PREMISE ||
			reference == EMSLPackage.Literals.IMPLICATION__CONCLUSION
	}
		
	private def handlePatternInApplicationCondition(EObject context, EReference reference) {
		determineScope(allTypesInAllImportedMetamodels(EcoreUtil.getRootContainer(context), AtomicPattern))
	}
	
	

	/*--------------------------------*/
	/*---------- NodeBlocks ----------*/
	/*--------------------------------*/
	/**
	 * Returns whether to build a scope for the Type of a ModelNodeBlock.
	 */
	private def typeOfNodeBlock(EObject context, EReference reference) {
		context instanceof ModelNodeBlock && reference == EMSLPackage.Literals.MODEL_NODE_BLOCK__TYPE
	}

	/**
	 * Returns the scope for a NodeBlock in a Model.
	 */
	private def handleNodeBlockTypesInModel(ModelNodeBlock context, EReference reference) {
		// For all entities other than metamodels, candidates are all node blocks of all imported metamodels
		val root = EcoreUtil2.getRootContainer(context)
		determineScope(allNodeBlocksInAllImportedMetamodels(root))
	}

	/**
	 * Returns the scope for a NodeBlock in a Pattern.
	 */
	private def handleNodeBlockTypesInPattern(ModelNodeBlock context, EReference reference) {
		val root = EcoreUtil2.getRootContainer(context)
		// For a Pattern, first check all metamodels for classes
		determineScope(allNodeBlocksInAllImportedMetamodels(root))
	}

	/**
	 * Returns the scope for a NodeBlock in a Rule.
	 */
	private def handleNodeBlockTypesInRule(EObject context, EReference reference) {
		val root = EcoreUtil2.getRootContainer(context)
		val possibilities = new HashMap<EObject, String>()

		possibilities.putAll(allNodeBlocksInAllImportedMetamodels(root))

		determineScope(possibilities)
	}
	
	/**
	 * Returns the scope for the type of a NodeBlock in a TripleRule.
	 */
	private def handleNodeBlockTypesInTripleRule(EObject context, EReference reference) {
		if ((context.eContainer as TripleRule).srcNodeBlocks.contains(context)) {
			return determineScope(allNodeBlocksInAllImportedMetamodels(EcoreUtil2.getRootContainer(context)).filter[p1, p2|
				(context.eContainer as TripleRule).type.srcMetamodels.contains(p1.eContainer)
			])
		} else if ((context.eContainer as TripleRule).trgNodeBlocks.contains(context)) {
			return determineScope(allNodeBlocksInAllImportedMetamodels(EcoreUtil2.getRootContainer(context)).filter[p1, p2|
				(context.eContainer as TripleRule).type.trgMetamodels.contains(p1.eContainer)
			])
		}
	}

	/**
	 * Returns all MetamodelNodeBlocks from all imported Metamodels.
	 */
	private def allNodeBlocksInAllImportedMetamodels(EObject root) {
		allTypesInAllImportedMetamodels(root, MetamodelNodeBlock)
	}

	/**
	 * Returns all objects of the given Type from all imported Metamodels.
	 */
	private def <T extends EObject> allTypesInAllImportedMetamodels(EObject root, Class<T> type) {
		val aliases = new HashMap<T, String>()
		if (root === null)
			return aliases

		val importStatements = EcoreUtil2.getAllContentsOfType(root, ImportStatement)
		for (st : importStatements) {
			try {
				val sp = EMSLUtil.loadEMSL_Spec(st.value, root)
				EcoreUtil2.getAllContentsOfType(sp, type).forEach [ o |
					aliases.put(o, if(st.alias == "") null else st.alias)
				]
			} catch (Exception e) {
				println(e)
			}
		}

		// Don't forget all types in the same file
		EcoreUtil2.getAllContentsOfType(root, type).forEach[o|aliases.put(o, null)]

		// If NeoCore is not already imported, make sure to add it here and search for the types too
		if (!thisSpecDefinesNeoCore(aliases) &&
			!importStatements.exists[it.value === EMSLUtil.ORG_EMOFLON_NEO_CORE_URI]) {
			try {
				val sp = EMSLUtil.loadEMSL_Spec(EMSLUtil.ORG_EMOFLON_NEO_CORE_URI, root)
				EcoreUtil2.getAllContentsOfType(sp, type).forEach[o|aliases.put(o, null)]
			} catch (Exception e) {
				println(e)
			}
		}

		aliases
	}

	private def <T extends EObject> thisSpecDefinesNeoCore(HashMap<T, String> aliases) {
		aliases.keySet.exists [
			it instanceof MetamodelNodeBlock &&
				((it as MetamodelNodeBlock).eContainer as Metamodel).name == EMSLUtil.ORG_EMOFLON_NEO_CORE
		]
	}

	/*----------------------------------------*/
	/*---------- NodeBlock Location ----------*/
	/*----------------------------------------*/
	/**
	 * Returns whether the ModelNodeBlock is part of a Model.
	 */
	private def isInModel(ModelNodeBlock context) {
		context.eContainer instanceof Model
	}

	/**
	 * Returns whether the ModelNodeBlock is part of a Pattern.
	 */
	private def isInPattern(ModelNodeBlock context) {
		context.eContainer instanceof AtomicPattern
	}

	/**
	 * Returns whether the ModelNodeBlock is part of a Rule.
	 */
	private def isInRule(ModelNodeBlock context) {
		context.eContainer instanceof Rule
	}
	
	/**
	 * Returns whether the ModelNodeBlock is part of a TripleRule.
	 */
	private def isInTripleRule(ModelNodeBlock context) {
		context.eContainer instanceof TripleRule
	}

	/*--------------------------*/
	/*---------- Misc ----------*/
	/*--------------------------*/
	private def <T extends EObject> determineScope(Map<T, String> aliases) {
		new SimpleScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(
			aliases.keySet,
			[ eob |
				// find duplicates in names of NodeBlocks
				val nameList = newArrayList
				val duplicateNames = newArrayList
				val extendedDuplicateNames = newArrayList
				val duplicateObjects = newArrayList
				aliases.keySet.forEach [ e |
					if (!nameList.contains(
						QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(e)).toString))
						nameList.add(QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(e)).toString)
					else {
						duplicateObjects.add(e)
						for (other : aliases.keySet) {
							if (e !== other && QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(e)).toString.equals(QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(other)).toString) && aliases.get(e) === null && aliases.get(other) === null) {
								duplicateObjects.add(other)
								duplicateNames.add(QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(e)).toString)
							}
						}
					}
						
				]
				for (o : duplicateObjects) {
					for (other : duplicateObjects) {
						if (o !== other && 
								QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(o)).toString.
									equals(QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(other)).toString) &&
								o.eContainer instanceof MetamodelNodeBlock && other.eContainer instanceof MetamodelNodeBlock &&
								(o.eContainer as MetamodelNodeBlock).name.equals((other.eContainer as MetamodelNodeBlock).name)
						) {
							extendedDuplicateNames.add(QualifiedName.create((o.eContainer.eContainer as Metamodel).name, SimpleAttributeResolver.NAME_RESOLVER.apply(o)).toString)
						}
					}
				}
				// create QualifiedNames for NodeBlocks
				val eobName = SimpleAttributeResolver.NAME_RESOLVER.apply(eob)
				if (eob.eContainer.eContainer instanceof Metamodel && extendedDuplicateNames.contains(QualifiedName.create((eob.eContainer.eContainer as Metamodel).name, eobName).toString)) {
					if (aliases.containsKey(eob) && aliases.get(eob) !== null)
						QualifiedName.create(aliases.get(eob), QualifiedName.create((eob.eContainer.eContainer as Metamodel).name, SimpleAttributeResolver.NAME_RESOLVER.apply(eob.eContainer), eobName).toString)
					else
						QualifiedName.create((eob.eContainer.eContainer as Metamodel).name, SimpleAttributeResolver.NAME_RESOLVER.apply(eob.eContainer), eobName)
				} else if (duplicateNames.contains(eobName)) {
					if (aliases.containsKey(eob) && aliases.get(eob) !== null && !(eob.eContainer instanceof EMSL_Spec))
						QualifiedName.create(aliases.get(eob),
							SimpleAttributeResolver.NAME_RESOLVER.apply(eob.eContainer), eobName)
					else { 
						if (eob instanceof AtomicPattern) {
							QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(eob.eContainer.eContainer), eobName)
						} else {
							QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(eob.eContainer), eobName)
						}
					}
				} else {
					if (aliases.containsKey(eob) && aliases.get(eob) !== null)
						QualifiedName.create(aliases.get(eob), eobName)
					else
						QualifiedName.create(eobName)
				}
			]
		))
	}
}
