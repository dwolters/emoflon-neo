/*
 * generated by Xtext 2.16.0
 */
package org.emoflon.neo.emsl.scoping

import java.util.HashMap
import java.util.HashSet
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.FilteringScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.SimpleAttributeResolver
import org.emoflon.neo.emsl.eMSL.AtomicPattern
import org.emoflon.neo.emsl.eMSL.EMSLPackage
import org.emoflon.neo.emsl.eMSL.ImportStatement
import org.emoflon.neo.emsl.eMSL.Metamodel
import org.emoflon.neo.emsl.eMSL.MetamodelNodeBlock
import org.emoflon.neo.emsl.eMSL.MetamodelRelationStatement
import org.emoflon.neo.emsl.eMSL.Model
import org.emoflon.neo.emsl.eMSL.ModelNodeBlock
import org.emoflon.neo.emsl.eMSL.ModelPropertyStatement
import org.emoflon.neo.emsl.eMSL.ModelRelationStatement
import org.emoflon.neo.emsl.eMSL.Pattern
import org.emoflon.neo.emsl.eMSL.RefinementCommand
import org.emoflon.neo.emsl.eMSL.Rule
import org.emoflon.neo.emsl.eMSL.TripleRule
import java.util.ArrayList
import java.util.List

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class EMSLScopeProvider extends AbstractEMSLScopeProvider {

	override getScope(EObject context, EReference reference) {
		if (typeOfNodeBlock(context, reference)) {
			if (isInModel(context as ModelNodeBlock))
				return handleNodeBlockTypesInModel(context as ModelNodeBlock, reference)
			else if (isInPattern(context as ModelNodeBlock))
				return handleNodeBlockTypesInPattern(context as ModelNodeBlock, reference)
			else if (isInRule(context as ModelNodeBlock))
				return handleNodeBlockTypesInRule(context as ModelNodeBlock, reference)
		}

		if (valueOfRelationStatementInModel(context, reference))
			return handleValueOfRelationStatementInModel(context as ModelRelationStatement, reference)

		if (valueOfRelationStatementInMetamodel(context, reference))
			return handleValueOfRelationStatementInMetamodel(context as MetamodelRelationStatement, reference)

		if (valueOfRelationStatementInRule(context, reference))
			return handleValueOfRelationStatementInRule(context as ModelRelationStatement, reference)

		if (valueOfRelationStatementInPattern(context, reference))
			return handleValueOfRelationStatementInPattern(context as ModelRelationStatement, reference)

		if (valueOfRelationStatementInTripleRule(context, reference))
			return handleValueOfRelationStatementInTripleRule(context as ModelRelationStatement, reference)

		if (typeOfRelationStatement(context, reference))
			return handleTypeOfRelationStatementInModelNodeBlock(context as ModelRelationStatement,
				context.eContainer as ModelNodeBlock)

		if (typeOfPropertyStatementInModelNodeBlock(context, reference))
			return handleTypeOfPropertyStatementInModelNodeBlock(context as ModelPropertyStatement,
				context.eContainer as ModelNodeBlock)

		if (nameOfPropertyStatementInRelationStatement(context, reference))
			return handleNameOfPropertyStatementInRelationStatement(context as ModelPropertyStatement, reference)

		if (isNodeBlockInMetamodel(context, reference))
			return handleNodeBlockTypesInMetamodel(context as MetamodelNodeBlock, reference)

		return super.getScope(context, reference)
	}

	/**
	 * Returns whether to build a scope for the Type of a ModelNodeBlock.
	 */
	def typeOfNodeBlock(EObject context, EReference reference) {
		context instanceof ModelNodeBlock && reference == EMSLPackage.Literals.MODEL_NODE_BLOCK__TYPE
	}

	/*----------------------------------------*/
	/*----------- PropertyStatement ----------*/
	/*----------------------------------------*/
	/**
	 * Returns whether to build a scope for the name of a PropertyStatement.
	 */
	def typeOfPropertyStatementInModelNodeBlock(EObject context, EReference reference) {
		context instanceof ModelPropertyStatement && reference == EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE &&
			!(context.eContainer instanceof ModelRelationStatement)
	}

	/**
	 * Returns the scope for the type of a PropertyStatement.
	 */
	def handleTypeOfPropertyStatementInModelNodeBlock(ModelPropertyStatement prop, ModelNodeBlock container) {
		val nodeBlocks = thisAndAllSuperTypes(container.type)
		val possibilities = new HashMap
		for (nb : nodeBlocks) {
			nb.properties.forEach[r|possibilities.put(r, null)]
		}

		determineScope(possibilities)
	}

	/**
	 * Returns whether to build a scope for the name of a PropertyStatements that is nested in a RelationStatement.
	 */
	def nameOfPropertyStatementInRelationStatement(EObject context, EReference reference) {
		context instanceof ModelPropertyStatement && reference == EMSLPackage.Literals.MODEL_PROPERTY_STATEMENT__TYPE
	}

	/**
	 * Returns the scope for the name of a PropertyStatement that is nested in a RelationStatement.
	 */
	def handleNameOfPropertyStatementInRelationStatement(EObject context, EReference reference) {
		val root = EcoreUtil2.getRootContainer(context)
		val nodeBlocks = allNodeBlocksInAllImportedMetamodels(root)

		val possibilities = new HashMap
		for (nb : nodeBlocks.keySet) {
			(nb as MetamodelNodeBlock).relations.forEach [ r |
				(r as MetamodelRelationStatement).properties.forEach [ p |
					possibilities.put(p, null)
				]
			]
		}

		determineScope(possibilities)
	}

	/*----------------------------------------*/
	/*----------- RelationStatement ----------*/
	/*----------------------------------------*/
	/**
	 * Returns whether to build a scope for the name of a RelationStatement.
	 */
	def typeOfRelationStatement(EObject context, EReference reference) {
		context instanceof ModelRelationStatement && reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TYPE
	}

	/**
	 * Returns the scope for the name of a RelationStatement.
	 */
	def handleTypeOfRelationStatementInModelNodeBlock(ModelRelationStatement context, ModelNodeBlock container) {
		val nodeBlocks = thisAndAllSuperTypes(container.type)
		val possibilities = new HashMap
		for (nb : nodeBlocks)
			nb.relations.forEach[r|possibilities.put(r, null)]

		determineScope(possibilities)
	}
		
	def List<MetamodelNodeBlock> thisAndAllSuperTypes(MetamodelNodeBlock block) {
		val blocks = new ArrayList
		blocks.add(block)
		block.superTypes.forEach[blocks.addAll(thisAndAllSuperTypes(it))]
		return blocks
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
		val rule = statement.eContainer.eContainer as Rule
		val allNodeBlocks = new HashSet()
		val nodeBlocksInSuperTypes = rule.superRefinementTypes.filter [ st |
			(st as RefinementCommand).referencedType instanceof Rule
		].flatMap[r|((r as RefinementCommand).referencedType as Rule).nodeBlocks]
		allNodeBlocks.addAll(nodeBlocksInSuperTypes.toList)
		allNodeBlocks.addAll(rule.nodeBlocks)
		return Scopes.scopeFor(allNodeBlocks)
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
	def handleValueOfRelationStatementInTripleRule(ModelRelationStatement statement, EReference reference) {
		val tripleRule = statement.eContainer.eContainer as TripleRule
		val allNodeBlocks = new HashSet()

		var nodeBlocksInSuperTypes = tripleRule.superRefinementTypes.filter [ st |
			(st as RefinementCommand).referencedType instanceof TripleRule
		].flatMap[r|((r as RefinementCommand).referencedType as TripleRule).trgNodeBlocks]
		allNodeBlocks.addAll(nodeBlocksInSuperTypes.toList)
		nodeBlocksInSuperTypes = tripleRule.superRefinementTypes.filter [ st |
			(st as RefinementCommand).referencedType instanceof TripleRule
		].flatMap[r|((r as RefinementCommand).referencedType as TripleRule).srcNodeBlocks]
		allNodeBlocks.addAll(nodeBlocksInSuperTypes.toList)

		allNodeBlocks.addAll(tripleRule.srcNodeBlocks)
		allNodeBlocks.addAll(tripleRule.trgNodeBlocks)

		return Scopes.scopeFor(allNodeBlocks)
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a Pattern.
	 */
	def valueOfRelationStatementInPattern(EObject context, EReference reference) {
		context instanceof ModelRelationStatement &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof AtomicPattern
	}

	/**
	 * Returns a scope for the value of a RelationStatement in a Pattern.
	 */
	def handleValueOfRelationStatementInPattern(ModelRelationStatement statement, EReference reference) {
		val pattern = statement.eContainer.eContainer.eContainer as Pattern
		val allNodeBlocks = new HashSet()
		val nodeBlocksInSuperTypes = pattern.body.superRefinementTypes.filter [ st |
			(st as RefinementCommand).referencedType instanceof AtomicPattern
		].flatMap[r|((r as RefinementCommand).referencedType as AtomicPattern).nodeBlocks]

		allNodeBlocks.addAll(nodeBlocksInSuperTypes.toList)
		allNodeBlocks.addAll(pattern.body.nodeBlocks)
		return Scopes.scopeFor(allNodeBlocks)
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a Model.
	 */
	def valueOfRelationStatementInModel(EObject context, EReference reference) {
		context instanceof ModelRelationStatement &&
			reference == EMSLPackage.Literals.MODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof Model
	}

	/**
	 * Returns a scope for the value of a RelationStatement in a Model.
	 */
	def handleValueOfRelationStatementInModel(ModelRelationStatement statement, EReference reference) {
		val model = statement.eContainer.eContainer as Model
		val allNodeBlocks = new HashSet()
		val nodeBlocksInSuperTypes = model.superRefinementTypes.filter [ st |
			(st as RefinementCommand).referencedType instanceof Model
		].flatMap[r|((r as RefinementCommand).referencedType as Model).nodeBlocks]

		allNodeBlocks.addAll(nodeBlocksInSuperTypes.toList)
		allNodeBlocks.addAll(model.nodeBlocks)
		return Scopes.scopeFor(allNodeBlocks)
	}

	/**
	 * Returns whether to build a scope for the value of a RelationStatement in a Model.
	 */
	def valueOfRelationStatementInMetamodel(EObject context, EReference reference) {
		context instanceof MetamodelRelationStatement &&
			reference == EMSLPackage.Literals.METAMODEL_RELATION_STATEMENT__TARGET &&
			context.eContainer?.eContainer instanceof Metamodel
	}

	/**
	 * Returns a scope for the value of a RelationStatement in a Model.
	 */
	def handleValueOfRelationStatementInMetamodel(MetamodelRelationStatement statement, EReference reference) {
		val metaModel = statement.eContainer.eContainer as Metamodel
		val allNodeBlocks = new HashSet()
		val nodeBlocksInSuperTypes = metaModel.superRefinementTypes.filter [ st |
			(st as RefinementCommand).referencedType instanceof Metamodel
		].flatMap[r|((r as RefinementCommand).referencedType as Metamodel).nodeBlocks]

		allNodeBlocks.addAll(nodeBlocksInSuperTypes.toList)
		allNodeBlocks.addAll(metaModel.nodeBlocks)
		return Scopes.scopeFor(allNodeBlocks)
	}

	/*--------------------------------*/
	/*---------- NodeBlocks ----------*/
	/*--------------------------------*/
	/**
	 * Returns the scope for the SuperTypes of a NodeBlock.
	 */
	def handleSuperTypesOfNodeBlock(ModelNodeBlock block, EReference reference) {
		val root = EcoreUtil2.getRootContainer(block)
		determineScope(allNodeBlocksInAllImportedMetamodels(root))
	}

	/**
	 * Returns the scope for the type of a MetamodelNodeBlock.
	 */
	def handleNodeBlockTypesInMetamodel(MetamodelNodeBlock context, EReference reference) {
		// For a metamodel, candidates are only the EClass node block in NeoCore
		val root = EcoreUtil2.getRootContainer(context)
		new FilteringScope(determineScope(allNodeBlocksInAllImportedMetamodels(root)), [ desc |
			desc.name.lastSegment == "EClass"
		])
	}

	/**
	 * Returns the scope for a NodeBlock in a Model.
	 */
	def handleNodeBlockTypesInModel(ModelNodeBlock context, EReference reference) {
		// For all entities other than metamodels, candidates are all node blocks of all imported metamodels
		val root = EcoreUtil2.getRootContainer(context)
		determineScope(allNodeBlocksInAllImportedMetamodels(root))
	}

	/**
	 * Returns the scope for a NodeBlock in a Pattern.
	 */
	def handleNodeBlockTypesInPattern(ModelNodeBlock context, EReference reference) {
		val root = EcoreUtil2.getRootContainer(context)
		// For a Pattern, first check all metamodels for classes
		determineScope(allNodeBlocksInAllImportedMetamodels(root))
	}

	/**
	 * Returns the scope for a NodeBlock in a Rule.
	 */
	def handleNodeBlockTypesInRule(ModelNodeBlock context, EReference reference) {
		val root = EcoreUtil2.getRootContainer(context)
		val possibilities = new HashMap<EObject, String>()

		possibilities.putAll(allNodeBlocksInAllImportedMetamodels(root))

		determineScope(possibilities)
	}

	/**
	 * Returns all MetamodelNodeBlocks from all imported Metamodels.
	 */
	def allNodeBlocksInAllImportedMetamodels(EObject root) {
		allTypesInAllImportedMetamodels(root, MetamodelNodeBlock)
	}

	/**
	 * Returns all objects of the given Type from all imported Metamodels.
	 */
	def <T extends EObject> allTypesInAllImportedMetamodels(EObject root, Class<T> type) {
		val aliases = new HashMap<T, String>()
		val importStatements = EcoreUtil2.getAllContentsOfType(root, ImportStatement)
		for (st : importStatements) {
			try {
				val sp = loadEMSL_Spec(st.value, root)
				EcoreUtil2.getAllContentsOfType(sp, type).forEach [ o |
					aliases.put(o, if(st.alias == "") null else st.alias)
				]
			} catch (Exception e) {
				println(e)
			}
		}

		// Don't forget all node blocks in the same file
		EcoreUtil2.getAllContentsOfType(root, type).forEach[o|aliases.put(o, null)]

		aliases
	}

	/*----------------------------------------*/
	/*---------- NodeBlock Location ----------*/
	/*----------------------------------------*/
	/**
	 * Returns whether the ModelNodeBlock is part of a Model.
	 */
	def isInModel(ModelNodeBlock context) {
		context.eContainer instanceof Model
	}

	/**
	 * Returns whether the ModelNodeBlock is part of a Pattern.
	 */
	def isInPattern(ModelNodeBlock context) {
		context.eContainer instanceof AtomicPattern
	}

	/**
	 * Returns whether the ModelNodeBlock is part of a Rule.
	 */
	def isInRule(ModelNodeBlock context) {
		context.eContainer instanceof Rule
	}

	/**
	 * Returns whether the given context is a MetamodelNodeBlock and therefore part of a Metamodel.
	 */
	def isNodeBlockInMetamodel(EObject context, EReference reference) {
		context instanceof MetamodelNodeBlock && reference == EMSLPackage.Literals.METAMODEL__NODE_BLOCKS &&
			context.eContainer instanceof Metamodel
	}

	/*--------------------------*/
	/*---------- Misc ----------*/
	/*--------------------------*/
	def loadEMSL_Spec(String uri, EObject root) {
		val rs = root.eResource.resourceSet
		val resource = rs.getResource(URI.createURI(uri), true)
		resource.contents.get(0)
	}

	def <T extends EObject> determineScope(Map<T, String> aliases) {
		new SimpleScope(IScope.NULLSCOPE, Scopes.scopedElementsFor(
			aliases.keySet,
			[ eob |
				// find duplicates in names of NodeBlocks (works)
				val nameList = newArrayList
				val duplicateNames = newArrayList
				aliases.keySet.forEach [ e |
					if (!nameList.contains(
						QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(e)).toString))
						nameList.add(QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(e)).toString)
					else
						duplicateNames.add(
							QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(e)).toString)
				]
				// create QualifiedNames for NodeBlocks
				val eobName = SimpleAttributeResolver.NAME_RESOLVER.apply(eob)
				if (duplicateNames.contains(eobName)) {
					if (aliases.containsKey(eob) && aliases.get(eob) !== null)
						QualifiedName.create(aliases.get(eob),
							SimpleAttributeResolver.NAME_RESOLVER.apply(eob.eContainer), eobName)
					else
						QualifiedName.create(SimpleAttributeResolver.NAME_RESOLVER.apply(eob.eContainer), eobName)
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
