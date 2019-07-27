package org.emoflon.neo.emsl.ui.visualisation

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Optional
import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider
import org.eclipse.jface.text.TextSelection
import org.eclipse.jface.viewers.ISelection
import org.eclipse.ui.IEditorPart
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.ui.editor.XtextEditor
import org.emoflon.neo.emsl.eMSL.ActionOperator
import org.emoflon.neo.emsl.eMSL.AndBody
import org.emoflon.neo.emsl.eMSL.AtomicPattern
import org.emoflon.neo.emsl.eMSL.AttributeCondition
import org.emoflon.neo.emsl.eMSL.AttributeExpression
import org.emoflon.neo.emsl.eMSL.BuiltInType
import org.emoflon.neo.emsl.eMSL.Constraint
import org.emoflon.neo.emsl.eMSL.ConstraintBody
import org.emoflon.neo.emsl.eMSL.ConstraintReference
import org.emoflon.neo.emsl.eMSL.EMSL_Spec
import org.emoflon.neo.emsl.eMSL.Entity
import org.emoflon.neo.emsl.eMSL.Enum
import org.emoflon.neo.emsl.eMSL.EnumValue
import org.emoflon.neo.emsl.eMSL.GraphGrammar
import org.emoflon.neo.emsl.eMSL.Implication
import org.emoflon.neo.emsl.eMSL.LinkAttributeExpTarget
import org.emoflon.neo.emsl.eMSL.Metamodel
import org.emoflon.neo.emsl.eMSL.MetamodelNodeBlock
import org.emoflon.neo.emsl.eMSL.MetamodelRelationStatement
import org.emoflon.neo.emsl.eMSL.Model
import org.emoflon.neo.emsl.eMSL.ModelNodeBlock
import org.emoflon.neo.emsl.eMSL.NegativeConstraint
import org.emoflon.neo.emsl.eMSL.NodeAttributeExpTarget
import org.emoflon.neo.emsl.eMSL.OrBody
import org.emoflon.neo.emsl.eMSL.Pattern
import org.emoflon.neo.emsl.eMSL.PositiveConstraint
import org.emoflon.neo.emsl.eMSL.PrimitiveBoolean
import org.emoflon.neo.emsl.eMSL.PrimitiveInt
import org.emoflon.neo.emsl.eMSL.PrimitiveString
import org.emoflon.neo.emsl.eMSL.RefinementCommand
import org.emoflon.neo.emsl.eMSL.RelationKind
import org.emoflon.neo.emsl.eMSL.Rule
import org.emoflon.neo.emsl.eMSL.SourceNAC
import org.emoflon.neo.emsl.eMSL.TripleGrammar
import org.emoflon.neo.emsl.eMSL.TripleRule
import org.emoflon.neo.emsl.eMSL.UserDefinedType
import org.emoflon.neo.emsl.eMSL.Value
import org.emoflon.neo.emsl.refinement.EMSLFlattener
import org.emoflon.neo.emsl.ui.util.ConstraintTraversalHelper
import org.emoflon.neo.emsl.util.EntityAttributeDispatcher
import org.emoflon.neo.emsl.util.FlattenerException

class EMSLDiagramTextProvider implements DiagramTextProvider {
	static final int MAX_SIZE = 500

	override String getDiagramText(IEditorPart editor, ISelection selection) {
		var Optional<String> diagram = Optional.empty()
		try {
			var String d = getDiagramBody(editor, selection)
			if(d === null)
				diagram = Optional.of(errorDiagram)
			if (d.split("\n").length > MAX_SIZE)
				diagram = Optional.of(tooBigDiagram)
			else
				diagram = Optional.of(d)
		} catch (Exception e) {
			e.printStackTrace()
		}

		return wrapInTags(diagram.orElse(errorDiagram))
	}

	def String wrapInTags(String body) {
		'''
			@startuml
			«plantUMLPreamble»
			«body»
			@enduml
		'''
	}

	def String emptyDiagram() {
		'''
			title Choose an element that can be visualised
		'''
	}

	def String errorDiagram() {
		'''
			title I'm having problems visualising the current selection (check your editor and console).
		'''
	}

	def String tooBigDiagram() {
		'''
			title This diagram would be so big, trying to render it would fry your Eclipse instance
		'''
	}

	def String getDiagramBody(IEditorPart editor, ISelection selection) {
		val EMSL_Spec root = getRoot(editor).get() as EMSL_Spec
		val Optional<Entity> selectedEntity = determineSelectedEntity(selection, root)
		val Optional<ModelNodeBlock> selectedNodeBlock = selectedEntity.flatMap([ e |
			determineSelectedNodeBlock(selection, e)
		])
		val Optional<MetamodelNodeBlock> selectedMetamodelNodeBlock = selectedEntity.flatMap([ e |
			determineSelectedMetamodelNodeBlock(selection, e)
		])

		if (selectedEntity.isPresent && selectedEntity.get instanceof Enum)
			return visualiseEnumLiterals(selectedEntity.get as Enum)

		if (selectedMetamodelNodeBlock.isPresent)
			return visualiseNodeBlockInMetamodel(selectedMetamodelNodeBlock.get, true)

		if (!selectedEntity.isPresent)
			return visualiseOverview(root)

		if (!selectedNodeBlock.isPresent)
			return visualiseEntity(selectedEntity.get, true)

		visualiseNodeBlock(selectedNodeBlock.get, true)
	}

	/**
	 * Returns the diagram text for the different cases of NodeBlocks appearing.
	 */
	def visualiseNodeBlock(ModelNodeBlock nb, boolean mainSelection) {
		if (nb.eContainer instanceof Model)
			visualiseNodeBlockInModel(nb, mainSelection)
		else if (nb.eContainer instanceof AtomicPattern)
			return visualiseNodeBlockInPattern(nb, mainSelection) +
				visualiseCondition(nb.eContainer.eContainer as Pattern)
		else if (nb.eContainer instanceof Rule)
			return visualiseNodeBlockInRule(nb, mainSelection) + visualiseCondition(nb.eContainer as Rule)
		else if (nb.eContainer instanceof TripleRule)
			visualiseNodeBlockInTripleRule(nb.eContainer as TripleRule, nb, mainSelection)
		else if (nb.eContainer instanceof GraphGrammar)
			visualiseNodeBlockInRule(nb, mainSelection)
	}

	/**
	 * Returns the diagram text for the overview of the current file, i.e. when no entity is selected.
	 */
	def String visualiseOverview(EMSL_Spec root) {
		'''
			left to right direction
			«FOR entity : root.entities»
				«IF entity instanceof Metamodel»
					rectangle "Metamodel: «entity.name»" <<Rectangle>> «link(entity as Entity)» {
						
					}
				«ENDIF»
				«IF entity instanceof Model»
					rectangle "Model: «IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF»" «IF entity.abstract»<<Abstract>>«ENDIF» <<Rectangle>> «link(entity as Entity)» {
						
					}
					«referenceInstantiatedMetamodel(entity as Model)»
				«ENDIF»
				«IF entity instanceof Pattern»
					rectangle "Pattern: «IF entity.body.abstract»//«ENDIF»«entity.body.name»«IF entity.body.abstract»//«ENDIF»" «IF entity.body.abstract»<<Abstract>>«ENDIF» <<Rectangle>> «link(entity as Entity)» {
						
					}
				«ENDIF»
				«IF entity instanceof Rule»
					rectangle "Rule: «IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF»" «IF entity.abstract»<<Abstract>>«ENDIF» <<Rectangle>> «link(entity as Entity)» {
						
					}
				«ENDIF»
				«IF entity instanceof TripleRule»
					rectangle "TripleRule: «IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF»" «IF entity.abstract»<<Abstract>>«ENDIF» <<Rectangle>> «link(entity as Entity)» {
						
					}
				«ENDIF»
				«IF entity instanceof TripleGrammar»
					rectangle "TripleGrammar: «IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF»" «IF entity.abstract»<<Abstract>>«ENDIF» <<Rectangle>> «link(entity as Entity)» {
						
					}
				«ENDIF»
				«IF entity instanceof GraphGrammar»
					rectangle "GraphGrammar: «IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF»" «IF entity.abstract»<<Abstract>>«ENDIF» <<Rectangle>> «link(entity as Entity)» {
						
					}
				«ENDIF»
				«IF entity instanceof Constraint»
					rectangle "Constraint: «IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF»" «IF entity.abstract»<<Abstract>>«ENDIF» <<Rectangle>> «link(entity as Entity)» {
						
					}
				«ENDIF»
				«visualiseSuperTypesInEntity(entity)»
			«ENDFOR»
		'''
	}

	/*-------------------------------------------------*/
	/*------------------- Models ----------------------*/
	/*-------------------------------------------------*/
	/**
	 * Returns the diagram text for a Model.
	 */
	def dispatch String visualiseEntity(Model entity, boolean mainSelection) {
		var entityCopy = EMSLFlattener.flatten(entity)
		'''
			package "«IF entity.abstract»//«ENDIF»«(entityCopy as Model).name»«IF entity.abstract»//«ENDIF»"«IF mainSelection» <<Selection>> «ENDIF»{
			«FOR nb : entityCopy.nodeBlocks»
				«visualiseNodeBlockInModel(nb, false)»
			«ENDFOR»
			}
		'''
	}

	/**
	 * Returns the diagram text for a NodeBlock in a Model.
	 */
	def String visualiseNodeBlockInModel(ModelNodeBlock nodeBlock, boolean mainSelection) {
		var node = nodeBlock
		for (n : (new EntityAttributeDispatcher().getNodeBlocks((EMSLFlattener.flatten(nodeBlock.eContainer as Entity))))) {
			if (nodeBlock.name.equals(n.name))
				node = n
		}
		val nb = node
		
		var sizeOfTypeList = 0
		var sizeOfIncomingRefTypeList = 0
		'''
			class «labelForObject(nb)» «IF mainSelection»<<Selection>>«ENDIF»
			«FOR link : nb.relations»«{sizeOfTypeList = link.types.size - 1;""}»
				«labelForObject(nb)» --> «IF link.target !== null»«labelForObject(link.target)»«ELSE»"?"«ENDIF» : "«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
			«ENDFOR»
			«FOR attr : nb.properties»
				«labelForObject(nb)» : «IF attr.type.name !== null»«attr.type.name»«ELSE»?«ENDIF» = «IF attr.value !== null»«printValue(attr.value)»«ELSE»?«ENDIF»
			«ENDFOR»
			«FOR incoming : (nb.eContainer as Model).nodeBlocks.filter[n|n != nb]»
				«FOR incomingRef : incoming.relations»«{sizeOfIncomingRefTypeList = incomingRef.types.size - 1;""}»
					«IF incomingRef.target == nb && mainSelection»
						«labelForObject(incoming)» --> «labelForObject(nb)» : "«FOR t : incomingRef.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfIncomingRefTypeList > 0» | «ENDIF»«{sizeOfIncomingRefTypeList = sizeOfIncomingRefTypeList - 1;""}»«ENDFOR»"
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
		'''
	}

	dispatch def printValue(AttributeExpression value) {
		'''«value.node.name».«printTarget(value.target)»'''
	}

	dispatch def printTarget(NodeAttributeExpTarget target) {
		'''«IF target !== null && target.attribute !== null»«target.attribute.name»«ELSE»?«ENDIF»'''
	}

	dispatch def printTarget(LinkAttributeExpTarget target) {
		var size = target.link.types.size
		'''-"«IF target !== null»«FOR t : target.link.types»«t.type.name»«IF size > 0» | «ENDIF»«{size = size - 1;""}»«ENDFOR»"->.«target.attribute.name»«ELSE»?«ENDIF»'''
	}

	dispatch def printValue(EnumValue value) {
		'''«value.literal.name»'''
	}

	dispatch def printValue(PrimitiveInt value) {
		'''«value.literal»'''
	}

	dispatch def printValue(PrimitiveString value) {
		'''«value.literal»'''
	}

	dispatch def printValue(PrimitiveBoolean value) {
		'''«value.^true»'''
	}

	/**
	 * Returns the diagram text for the name of an object in a Model.
	 */
	private def labelForObject(ModelNodeBlock nb) {
		val entity = nb?.eContainer as Model
		'''"«IF entity !== null && entity.abstract»//«ENDIF»«IF entity?.name !== null»«entity?.name»«ELSE»?«ENDIF»«IF entity !== null && entity.abstract»//«ENDIF».«IF nb?.name !== null»«nb?.name»«ELSE»?«ENDIF»:«IF nb?.type?.name !== null»«nb?.type?.name»«ELSE»?«ENDIF»"'''
	}
	
	/**
	 * Returns the diagram text for the reference of a Model to the Metamodel it instantiates, i.e. which types the NodeBlocks of the Model use.
	 */
	private def referenceInstantiatedMetamodel(Model model) {
		var root = EcoreUtil2.getRootContainer(model)
		var allMetamodels = EcoreUtil2.getAllContentsOfType(root, Metamodel)
		if (!model.nodeBlocks.isEmpty) {
			for (nb : model.nodeBlocks) {
				for (i : allMetamodels) {
					if (i.nodeBlocks.contains(nb.type))
						return "\"Model: " + model.name + "\" --> \"Metamodel: " + i.name + "\""
				}
			}
		}
		else {
			''''''
		}
	}
	
	/*-------------------------------------------------*/
	/*----------------- Metamodels --------------------*/
	/*-------------------------------------------------*/
	/**
	 * Returns the diagram text for a Metamodel.
	 */
	def dispatch String visualiseEntity(Metamodel mm, boolean mainSelection) {
		'''
			package "«(mm as Metamodel).name»"«IF mainSelection» <<Selection>> «ENDIF»{
			«FOR nb : mm.nodeBlocks»
				«visualiseNodeBlockInMetamodel(nb, false)»
			«ENDFOR»
			«FOR e : mm.enums»
				«visualiseEnumInMetamodel(e, false)»
			«ENDFOR»
			}
		'''
	}

	/**
	 * Returns the diagram text for a NodeBlock in a Metamodel.
	 */
	def String visualiseNodeBlockInMetamodel(MetamodelNodeBlock nb, boolean mainSelection) {
		'''
			«IF nb.abstract»abstract«ENDIF» class «labelForClass(nb)» «IF mainSelection»<<Selection>>«ENDIF»
			«FOR sup : nb.superTypes»
				«labelForClass(sup)» <|-- «labelForClass(nb)»
			«ENDFOR»
			«FOR ref : nb.relations»
				«labelForClass(nb)» «IF ref.kind == RelationKind.COMPOSITION»*«ENDIF»«IF ref.kind == RelationKind.AGGREGATION»o«ENDIF»--> «IF ref.lower !== null»«visualiseMultiplicity(ref)»«ENDIF» «IF ref.target !== null»«labelForClass(ref.target)»«ELSE»"?"«ENDIF» : «IF ref.name !== null»«ref.name»«ELSE»?«ENDIF»
			«ENDFOR»
			«FOR incoming : (nb.eContainer as Metamodel).nodeBlocks.filter[n|n != nb]»
				«FOR incomingRef : incoming.relations»
					«IF incomingRef.target == nb && mainSelection»
						«labelForClass(incoming)» «IF incomingRef.kind == RelationKind.COMPOSITION»*«ENDIF»«IF incomingRef.kind == RelationKind.AGGREGATION»o«ENDIF»--> «IF incomingRef.lower !== null»«visualiseMultiplicity(incomingRef)»«ENDIF» «labelForClass(nb)» : «IF (incomingRef.name !== null)»«incomingRef.name»«ELSE»?«ENDIF»
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			«FOR attr : nb.properties»
				«labelForClass(nb)» : «attr.name» : «IF (attr.type instanceof UserDefinedType)»«((attr.type as UserDefinedType).reference).name»«ELSE»«(attr.type as BuiltInType).reference.toString»«ENDIF»
			«ENDFOR»
		'''
	}
	
	def String visualiseEnumInMetamodel(Enum e, boolean mainSelection) {
		'''
			class "«(e.eContainer as Metamodel).name».«e.name»" «IF mainSelection»<<Selection>>«ENDIF» {
				«FOR literal : e.literals»
					«literal.name»
				«ENDFOR»
			}
		'''
	}

	/**
	 * Returns the diagram text for the name of a class in a Metamodel.
	 */
	private def labelForClass(MetamodelNodeBlock nb) {
		val entity = nb?.eContainer as Metamodel
		'''"«entity?.name».«nb?.name»"'''
	}

	/**
	 * Returns the diagram text for multiplicities in a Metamodel.
	 */
	def visualiseMultiplicity(MetamodelRelationStatement link) {
		'''"«link.lower»«IF link.upper !== null»..«link.upper»«ENDIF»"'''
	}

	/**
	 * Returns the diagram text for an Enum.
	 */
	def dispatch String visualiseEntity(Enum entity, boolean mainSelection) {
		'''
			«FOR item : entity.literals»
				class "«entity.name»"
			«ENDFOR»
		'''
	}

	/**
	 * Returns the diagram text for the EnumLiterals.
	 */
	def String visualiseEnumLiterals(Enum entity) {
		'''
			«FOR literal : entity.literals»
				class "«entity.name».«literal.name»"
			«ENDFOR»
		'''
	}

	/*-------------------------------------------------*/
	/*------------------ Patterns ---------------------*/
	/*-------------------------------------------------*/
	/**
	 * Returns the diagram text for a Pattern.
	 */
	def dispatch String visualiseEntity(Pattern entity, boolean mainSelection) {
		try {
			var entityCopy = EMSLFlattener.flatten(entity)
			'''
				package «IF entity.body.abstract»//«ENDIF»«(entityCopy as Pattern).body.name»«IF entity.body.abstract»//«ENDIF» «IF mainSelection» <<Selection>> «ENDIF»{
				«FOR nb : new EntityAttributeDispatcher().getNodeBlocks(entityCopy)»
					«visualiseNodeBlockInPattern2(entityCopy as Pattern, nb, false)»
				«ENDFOR»
				}
				«IF (entityCopy as Pattern).condition !== null »
					legend bottom
						«getConditionString(entityCopy)»
					endlegend
					«visualiseCondition(entityCopy)»
				«ENDIF»
				«IF (entityCopy as Pattern).body.attributeConditions !== null && !(entityCopy as Pattern).body.attributeConditions.isEmpty»
					«visualiseAttributeConditions((entityCopy as Pattern).body.attributeConditions)»
				«ENDIF»
			'''
		} catch (AssertionError e) {
			
		} catch (FlattenerException e) {
			return ""
		}
	}

	/**
	 * Returns the diagram text for a NodeBlock in a Pattern.
	 */
	def String visualiseNodeBlockInPattern(ModelNodeBlock nodeBlock, boolean mainSelection) {
		var node = nodeBlock
		for (n : (new EntityAttributeDispatcher().getNodeBlocks((EMSLFlattener.flatten(nodeBlock.eContainer.eContainer as Entity))))) {
			if (nodeBlock.name.equals(n.name))
				node = n
		}
		val nb = node
		
		var sizeOfTypeList = 0
		var sizeOfIncomingRefTypeList = 0
		'''
			class «labelForPatternComponent(nb)» «IF mainSelection»<<Selection>>«ENDIF»
			«FOR link : nb.relations»«{sizeOfTypeList = link.types.size - 1;""}»
				«labelForPatternComponent(nb)» --> «labelForPatternComponent(link.target)» : "«IF link.name !== null»«link.name»:«ENDIF»«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
			«ENDFOR»
			«FOR attr : nb.properties»
				«labelForPatternComponent(nb)» : «attr.type.name» = «printValue(attr.value)»
			«ENDFOR»
			«FOR incoming : (nb.eContainer as AtomicPattern).nodeBlocks.filter[n|n != nb]»
				«FOR incomingRef : incoming.relations»«{sizeOfIncomingRefTypeList = incomingRef.types.size - 1;""}»
					«IF incomingRef.target == nb && mainSelection»
						«labelForPatternComponent(incoming)» --> «labelForPatternComponent(nb)» : "«FOR t : incomingRef.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfIncomingRefTypeList > 0» | «ENDIF»«{sizeOfIncomingRefTypeList = sizeOfIncomingRefTypeList - 1;""}»«ENDFOR»"
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			«IF (nb.eContainer.eContainer as Pattern).condition !== null »
				legend bottom
					«getConditionString((nb.eContainer.eContainer as Pattern))»
				endlegend
			«ENDIF»
		'''
	}
	
	/**
	 * Returns the diagram text for a NodeBlock in a Pattern.
	 */
	def String visualiseNodeBlockInPattern2(Pattern entity, ModelNodeBlock nodeBlock, boolean mainSelection) {
		var node = nodeBlock
		for (n : new EntityAttributeDispatcher().getNodeBlocks(entity)) {
			if (nodeBlock.name.equals(n.name))
				node = n
		}
		val nb = node
		
		var sizeOfTypeList = 0
		var sizeOfIncomingRefTypeList = 0
		'''
			class «labelForPatternComponent(nb)» «IF mainSelection»<<Selection>>«ENDIF»
			«FOR link : nb.relations»«{sizeOfTypeList = link.types.size - 1;""}»
				«labelForPatternComponent(nb)» --> «labelForPatternComponent(link.target)» : "«IF link.name !== null»«link.name»:«ENDIF»«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
			«ENDFOR»
			«FOR attr : nb.properties»
				«labelForPatternComponent(nb)» : «attr.type.name» = «printValue(attr.value)»
			«ENDFOR»
			«FOR incoming : (nb.eContainer as AtomicPattern).nodeBlocks.filter[n|n != nb]»
				«FOR incomingRef : incoming.relations»«{sizeOfIncomingRefTypeList = incomingRef.types.size - 1;""}»
					«IF incomingRef.target == nb && mainSelection»
						«labelForPatternComponent(incoming)» --> «labelForPatternComponent(nb)» : "«FOR t : incomingRef.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfIncomingRefTypeList > 0» | «ENDIF»«{sizeOfIncomingRefTypeList = sizeOfIncomingRefTypeList - 1;""}»«ENDFOR»"
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			«IF (nb.eContainer.eContainer as Pattern).condition !== null »
				legend bottom
					«getConditionString((nb.eContainer.eContainer as Pattern))»
				endlegend
			«ENDIF»
		'''
	}

	/**
	 * Returns the diagram text for the name of an object in a Pattern.
	 */
	private def labelForPatternComponent(ModelNodeBlock nb) {
		var entityName = "?"
		var nbName = "?"
		var nbTypeName = "?"
		if (nb !== null && nb.eContainer !== null) {
			val entity = nb.eContainer as AtomicPattern
			if (entity.name !== null)
				entityName = entity.name
			if (nb.name !== null)
				nbName = nb.name
			if (nb.type !== null && nb.type.name !== null)
				nbTypeName = nb.type.name

			'''"«IF (nb.eContainer as AtomicPattern).abstract»//«ENDIF»«entityName»«IF (nb.eContainer as AtomicPattern).abstract»//«ENDIF».«nbName»:«nbTypeName»"'''
		} else
			'''"?"'''
	}	

	/*-------------------------------------------------*/
	/*------------------- Rules -----------------------*/
	/*-------------------------------------------------*/
	/**
	 * Returns the diagram text for a Rule.
	 */
	def dispatch String visualiseEntity(Rule entity, boolean mainSelection) {
		try {
			var entityCopy = EMSLFlattener.flatten(entity)
		'''
			package «IF entity.abstract»//«ENDIF»«(entityCopy as Rule).name»«IF entity.abstract»//«ENDIF»«IF mainSelection» <<Selection>> «ENDIF»{
			«FOR nb : new EntityAttributeDispatcher().getNodeBlocks(entityCopy)»
				«visualiseNodeBlockInRule2(entityCopy as Rule, nb, false)»
			«ENDFOR»
			}
			«IF (entityCopy as Rule).condition !== null»
				legend bottom
					«getConditionString(entityCopy)»
				endlegend
				«visualiseCondition(entityCopy)»
			«ENDIF»
			«IF (entityCopy as Rule).attributeConditions !== null && !(entityCopy as Rule).attributeConditions.empty»
				«visualiseAttributeConditions((entityCopy as Rule).attributeConditions)»
			«ENDIF»
		'''
		} catch (FlattenerException e) {
			e.printStackTrace
			return ""
		}
	}

	/**
	 * Returns the diagram text for a NodeBlock in a Rule.
	 */
	def String visualiseNodeBlockInRule(ModelNodeBlock nodeBlock, boolean mainSelection) {
		var node = nodeBlock
		for (n : (new EntityAttributeDispatcher().getNodeBlocks((EMSLFlattener.flatten(nodeBlock.eContainer as Entity))))) {
			if (nodeBlock.name.equals(n.name))
				node = n
		}
		val nb = node
		
		var sizeOfTypeList = 0
		var sizeOfIncomingRefTypeList = 0
		'''
			class «labelForRuleComponent(nb)» «IF nb.action !== null && nb.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF nb.action !== null && nb.action.op == ActionOperator.DELETE»<<RED>>«ENDIF» «IF mainSelection»<<Selection>>«ENDIF»
			«FOR link : nb.relations»«{sizeOfTypeList = link.types.size - 1;""}»
				«IF link.action !== null»
					«labelForRuleComponent(nb)» -«IF link.action.op === ActionOperator.CREATE»[#SpringGreen]«ELSE»[#red]«ENDIF»-> «labelForRuleComponent(link.target)» : "«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
				«ELSE»«labelForRuleComponent(nb)» --> «labelForRuleComponent(link.target)» : "«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
				«ENDIF»
				class «labelForRuleComponent(link.target)» «IF link.target.action !== null && link.target.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF link.target.action !== null && link.target.action.op == ActionOperator.DELETE»<<RED>>«ENDIF»
			«ENDFOR»
			«FOR attr : nb.properties»
				«labelForRuleComponent(nb)» : «attr.type.name» = «printValue(attr.value)»
			«ENDFOR»
			«FOR incoming : (nb.eContainer as Rule).nodeBlocks.filter[n|n != nb]»
				«FOR incomingRef : incoming.relations»«{sizeOfIncomingRefTypeList = incomingRef.types.size - 1;""}»
					«IF incomingRef.target == nb && mainSelection»
						class «labelForRuleComponent(incoming)» «IF incoming.action !== null && incoming.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF incoming.action !== null && incoming.action.op == ActionOperator.DELETE»<<RED>>«ENDIF»
						«labelForRuleComponent(incoming)» -«IF incomingRef.action !== null &&  incomingRef.action.op === ActionOperator.CREATE»[#SpringGreen]«ENDIF»«IF incomingRef.action !== null && incomingRef.action.op === ActionOperator.DELETE»[#red]«ENDIF»-> «labelForRuleComponent(nb)» : "«FOR t : incomingRef.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfIncomingRefTypeList > 0» | «ENDIF»«{sizeOfIncomingRefTypeList = sizeOfIncomingRefTypeList - 1;""}»«ENDFOR»"
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			«IF (nb.eContainer as Rule).condition !== null »
				legend bottom
					«getConditionString((nb.eContainer as Rule))»
				endlegend
			«ENDIF»
		'''
	}
	
	/**
	 * Returns the diagram text for a NodeBlock in a Rule.
	 */
	def String visualiseNodeBlockInRule2(Rule entity, ModelNodeBlock nodeBlock, boolean mainSelection) {
		var node = nodeBlock
		for (n : (new EntityAttributeDispatcher().getNodeBlocks((EMSLFlattener.flatten(entity))))) {
			if (nodeBlock.name.equals(n.name))
				node = n
		}
		val nb = node
		
		var sizeOfTypeList = 0
		var sizeOfIncomingRefTypeList = 0
		'''
			class «labelForRuleComponent(nb)» «IF nb.action !== null && nb.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF nb.action !== null && nb.action.op == ActionOperator.DELETE»<<RED>>«ENDIF» «IF mainSelection»<<Selection>>«ENDIF»
			«FOR link : nb.relations»«{sizeOfTypeList = link.types.size - 1;""}»
				«IF link.action !== null»
					«labelForRuleComponent(nb)» -«IF link.action.op === ActionOperator.CREATE»[#SpringGreen]«ELSE»[#red]«ENDIF»-> «labelForRuleComponent(link.target)» : "«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
				«ELSE»«labelForRuleComponent(nb)» --> «labelForRuleComponent(link.target)» : "«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
				«ENDIF»
				class «labelForRuleComponent(link.target)» «IF link.target.action !== null && link.target.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF link.target.action !== null && link.target.action.op == ActionOperator.DELETE»<<RED>>«ENDIF»
			«ENDFOR»
			«FOR attr : nb.properties»
				«labelForRuleComponent(nb)» : «attr.type.name» = «printValue(attr.value)»
			«ENDFOR»
			«FOR incoming : (nb.eContainer as Rule).nodeBlocks.filter[n|n != nb]»
				«FOR incomingRef : incoming.relations»«{sizeOfIncomingRefTypeList = incomingRef.types.size - 1;""}»
					«IF incomingRef.target == nb && mainSelection»
						class «labelForRuleComponent(incoming)» «IF incoming.action !== null && incoming.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF incoming.action !== null && incoming.action.op == ActionOperator.DELETE»<<RED>>«ENDIF»
						«labelForRuleComponent(incoming)» -«IF incomingRef.action !== null &&  incomingRef.action.op === ActionOperator.CREATE»[#SpringGreen]«ENDIF»«IF incomingRef.action !== null && incomingRef.action.op === ActionOperator.DELETE»[#red]«ENDIF»-> «labelForRuleComponent(nb)» : "«FOR t : incomingRef.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfIncomingRefTypeList > 0» | «ENDIF»«{sizeOfIncomingRefTypeList = sizeOfIncomingRefTypeList - 1;""}»«ENDFOR»"
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			«IF (nb.eContainer as Rule).condition !== null »
				legend bottom
					«getConditionString((nb.eContainer as Rule))»
				endlegend
			«ENDIF»
		'''
	}

	/**
	 * Returns the diagram text for the name of an object in a Rule.
	 */
	private def labelForRuleComponent(ModelNodeBlock nb) {
		val entity = nb?.eContainer as Rule
		if (entity !== null) {
			if (entity.name === null)
				entity.name = "?"
			if (nb.name === null)
				nb.name = "?"
			if (nb.type.name === null)
				nb.type.name = "?"

			'''"«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«nb.name»:«nb.type.name»"'''
		} else
			'''"?"'''
	}

	/**
	 * Returns the diagram text for a GraphGrammar.
	 */
	def dispatch String visualiseEntity(GraphGrammar entity, boolean mainSelection) {
		'''
			«FOR r : entity.rules»
				class "«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«r.name»"
			«ENDFOR»
		'''
	}

	/**
	 * Returns the diagram text for the Rules in a GraphGrammar.
	 */
	def String visualiseRulesOfGraphGrammar(GraphGrammar gg, boolean mainSelection) {
		// TODO [Maximilian]
	}

	/*------------------------------------------*/
	/*-------- Constraints & Conditions --------*/
	/*------------------------------------------*/
	/**
	 * Returns the diagram text for a Constraint.
	 */
	def dispatch String visualiseEntity(Constraint entity, boolean mainSelection) {
		'''
			legend bottom
				«IF entity.abstract»//«ENDIF»«getConditionString(entity)»«IF entity.abstract»//«ENDIF»
			endlegend
			«visualiseCondition(entity)»
		'''
	}

	/**
	 * Returns the diagram text for a condition of a Rule or Pattern.
	 */
	def String visualiseCondition(Entity entity) {
		var conditionPattern = new ConstraintTraversalHelper().getConstraintPattern(entity)
		var copiesOfConditionPatterns = newArrayList
		for (p : conditionPattern) {
			copiesOfConditionPatterns.add(EMSLFlattener.flatten(p.eContainer as Pattern))
		}
		'''
			«FOR c : copiesOfConditionPatterns»
				«visualiseEntity(c as Pattern, false)»
			«ENDFOR»
			«IF entity instanceof Rule || entity instanceof Pattern»
				«FOR nb : entity.nodeBlocks»
					«FOR p : copiesOfConditionPatterns»
						«FOR otherNB : (p as Pattern).body.nodeBlocks»
							«IF otherNB.name.equals(nb.name)»«IF (entity instanceof Rule)»«labelForRuleComponent(nb)»«ELSE»«labelForPatternComponent(nb)»«ENDIF»#-[#DarkRed]-#«labelForPatternComponent(otherNB)»«ENDIF»
						«ENDFOR»
					«ENDFOR»
				«ENDFOR»
			«ENDIF»
			«IF entity instanceof Constraint»
				«createLinksForConstraintPatterns(conditionPattern)»
			«ENDIF»
		'''
	}
	
	/**
	 * Returns the diagram text for the links between objects with the same name in the patterns of a constraint.
	 */
	def String createLinksForConstraintPatterns(ArrayList<AtomicPattern> patterns) {
		var text = ""
		for (p : patterns) {
			for (AtomicPattern other : patterns) {
				if (!p.name.equals(other.name)) {
					for (ModelNodeBlock nb : p.nodeBlocks) {
						for (ModelNodeBlock otherNB : other.nodeBlocks) {
							if (otherNB.name.equals(nb.name)) {
								// create link if not already created
								if (!text.contains(labelForPatternComponent(nb) + "#-[#DarkRed]-#" + labelForPatternComponent(otherNB)) &&
									!text.contains(labelForPatternComponent(otherNB) + "#-[#DarkRed]-#" + labelForPatternComponent(nb))
								)
									text += labelForPatternComponent(nb) + "#-[#DarkRed]-#" + labelForPatternComponent(otherNB) + "\n"
							}
						}
					}
				}
			}
		}
		return text			
	}

	/**
	 * Returns the diagram text for the condition of a Rule or Pattern.
	 */
	def String getConditionString(Entity entity) {
		var builder = new StringBuilder();
		if (entity instanceof Rule) {
			// return for atomicConstraints
			if ((entity as Rule).condition instanceof NegativeConstraint)
				return '''**forbid** «((entity as Rule).condition as NegativeConstraint).pattern.name» '''
			else if ((entity as Rule).condition instanceof PositiveConstraint)
				return '''**enforce** «((entity as Rule).condition as PositiveConstraint).pattern.name» '''
			else if ((entity as Rule).condition instanceof Implication) {
				return '''**if** «((entity as Rule).condition as Implication).premise.name» **then** «((entity as Rule).condition as Implication).conclusion.name» '''
			} 	
			// return the String for ConstraintReference
			else if (entity.condition instanceof ConstraintReference) {
				if ((entity.condition as ConstraintReference).reference.body instanceof NegativeConstraint
						|| (entity.condition as ConstraintReference).reference.body instanceof PositiveConstraint
						|| (entity.condition as ConstraintReference).reference.body instanceof Implication) {
					builder.append(getAtomicConstraintString((entity.condition as ConstraintReference).reference.body))
				} else if ((entity.condition as ConstraintReference).reference.body instanceof OrBody) {
					builder.append(getOrBodyString((entity.condition as ConstraintReference).reference.body))
				}
			}
		} else if (entity instanceof Pattern) {
			// return for atomicConstraints
			if ((entity as Pattern).condition instanceof NegativeConstraint)
				return '''**forbid** «((entity as Pattern).condition as NegativeConstraint).pattern.name» '''
			else if ((entity as Pattern).condition instanceof PositiveConstraint)
				return '''**enforce** «((entity as Pattern).condition as PositiveConstraint).pattern.name» '''
			else if ((entity as Pattern).condition instanceof Implication) {
				return '''**if** «((entity as Pattern).condition as Implication).premise.name» **then** «((entity as Pattern).condition as Implication).conclusion.name» '''
			} 
			// return the String for ConstraintReference
			else if (entity.condition instanceof ConstraintReference) {
				if ((entity.condition as ConstraintReference).reference.body instanceof NegativeConstraint
						|| (entity.condition as ConstraintReference).reference.body instanceof PositiveConstraint
						|| (entity.condition as ConstraintReference).reference.body instanceof Implication) {
					builder.append(getAtomicConstraintString((entity.condition as ConstraintReference).reference.body))
				} else if ((entity.condition as ConstraintReference).reference.body instanceof OrBody) {
					builder.append(getOrBodyString((entity.condition as ConstraintReference).reference.body))
				}
			}
		} else if (entity instanceof Constraint) {
			// return for atomicConstraints
			if ((entity as Constraint).body instanceof NegativeConstraint)
				return '''**forbid** «((entity as Constraint).body as NegativeConstraint).pattern.name» '''
			else if ((entity as Constraint).body instanceof PositiveConstraint)
				return '''**enforce** «((entity as Constraint).body as PositiveConstraint).pattern.name» '''
			else if ((entity as Constraint).body instanceof Implication) {
				return '''**if** «((entity as Constraint).body as Implication).premise.name» **then** «((entity as Constraint).body as Implication).conclusion.name» '''
			} 
			// return for OrBody
			else if ((entity as Constraint).body instanceof OrBody) {
				builder.append(getOrBodyString((entity as Constraint).body))
			}
		}
		return builder.toString
	}

	/**
	 * Returns the diagram text for an OrBody in a recursive Constraint definition.
	 */
	def String getOrBodyString(ConstraintBody constraintBody) {
		var builder = new StringBuilder()
		var count = constraintBody.children.size - 1
		for (c : constraintBody.children) {
			if (c instanceof AndBody) {
				builder.append(getAndBodyString(c))
				if (count > 0)
					builder.append(" **||** ")
				count--
			}
		}
		return builder.toString
	}

	/**
	 * Returns the diagram text for an AndBody in a recursive Constraint definition.
	 */
	def String getAndBodyString(ConstraintBody constraintBody) {
		var builder = new StringBuilder()
		var count = constraintBody.children.size - 1
		for (c : constraintBody.children) {
			if (c instanceof ConstraintReference) {
				if (c.negated)
					builder.append("**!**(")
				if ((c as ConstraintReference).reference.body instanceof NegativeConstraint 
						|| (c as ConstraintReference).reference.body instanceof PositiveConstraint 
						|| (c as ConstraintReference).reference.body instanceof Implication) {
					builder.append(getAtomicConstraintString(c))
				} else if ((c as ConstraintReference).reference.body instanceof OrBody) {
					builder.append(getOrBodyString(c.reference.body))
				}
				if (c.negated)
					builder.append(")")
			} else if (c instanceof OrBody) {
				builder.append(" (" + getOrBodyString(c) + ") ")
			}
			if (count > 0)
				builder.append(" **&&** ")
			count --
		}
		return builder.toString
	}

	/**
	 * Returns the diagram text for an AtomicConstraint.
	 */
	def String getAtomicConstraintString(ConstraintBody constraintBody) {
		if (constraintBody instanceof ConstraintReference) {
			if (constraintBody.reference.body instanceof NegativeConstraint)
				return '''**forbid** «(constraintBody.reference.body as NegativeConstraint).pattern.name» '''
			else if (constraintBody.reference.body instanceof PositiveConstraint)
				return '''**enforce** «(constraintBody.reference.body as PositiveConstraint).pattern.name» '''
			else if (constraintBody.reference.body instanceof Implication)
				return '''**if** «(constraintBody.reference.body as Implication).premise.name» **then** «(constraintBody.reference.body as Implication).conclusion.name» '''
		}		
	}
	
	def List<? extends ConstraintBody> getChildren(ConstraintBody body){
		return ConstraintTraversalHelper.getChildren(body)
	}
	
	def String visualiseAttributeConditions(List<AttributeCondition> conditions) {
		'''
		note "«FOR c : conditions»**«c.operator»**(\n«FOR b : c.bindings»\t«b.name» : «IF b.pre»`«ENDIF»«IF b.value instanceof Value»«printValue(b.value)»«ENDIF»«IF b.post»´«ENDIF»,\n«ENDFOR»)\n«ENDFOR» " as N #WhiteSmoke
		'''
	}

	/*-------------------------------------------------*/
	/*---------------- Triple Rules -------------------*/
	/*-------------------------------------------------*/
	/**
	 * Returns the diagram text for a TripleRule.
	 */
	def dispatch String visualiseEntity(TripleRule entity, boolean mainSelection) {
		var entityCopy = EMSLFlattener.flatten(entity) as TripleRule
		'''
			together {
				«FOR snb : entityCopy.srcNodeBlocks»
					«visualiseTripleRuleNodeBlocks(entity, snb, "SRC")»
				«ENDFOR»
				
				«FOR tnb : entityCopy.trgNodeBlocks»
					«visualiseTripleRuleNodeBlocks(entity, tnb, "TRG")»
				«ENDFOR»
			
				«FOR corr : entityCopy.correspondences»
				"«IF entityCopy.abstract»//«ENDIF»«entityCopy.name»«IF entityCopy.abstract»//«ENDIF».«corr.source.name»:«corr.source.type.name»" ...«IF corr.action !== null»[#SpringGreen]«ENDIF»"«IF entityCopy.abstract»//«ENDIF»«entityCopy.name»«IF entityCopy.abstract»//«ENDIF».«corr.target.name»:«corr.target.type.name»": :«corr.type.name»
				«ENDFOR»
			}
			«IF entityCopy.nacs.size > 0»
				«visualiseTripleRuleNACs(entity)»
			«ENDIF»
		'''
	}

	/**
	 * Returns the diagram text for the NodeBlocks of a TripleRule.
	 */
	def String visualiseTripleRuleNodeBlocks(TripleRule entity, ModelNodeBlock nb, String type) {
		var sizeOfTypeList = 0
		'''class "«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«nb.name»:«nb.type.name»" «IF nb.action !== null»<<GREEN>>«ENDIF» <<«type»>>
			«FOR link : nb.relations»«{sizeOfTypeList = link.types.size - 1;""}»
				class «labelForTripleRuleComponent(link.target)» «IF link.target.action !== null && link.target.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF link.target.action !== null && link.target.action.op == ActionOperator.DELETE»<<RED>>«ENDIF»
				"«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«nb.name»:«nb.type.name»" -«IF (link.action !== null)»[#SpringGreen]«ENDIF»-> "«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«link.target.name»:«link.target.type.name»":"«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
			«ENDFOR»
		'''
	}

	/**
	 * Returns the diagram text for a NodeBlock in a TripleRule.
	 */
	def String visualiseNodeBlockInTripleRule(TripleRule rule, ModelNodeBlock nodeBlock, boolean mainSelection) {
		var node = nodeBlock
		val entityCopy = EMSLFlattener.flatten(nodeBlock.eContainer as Entity)
		for (n : (entityCopy as TripleRule).srcNodeBlocks) {
			if (nodeBlock.name.equals(n.name) && (nodeBlock.eContainer as TripleRule).srcNodeBlocks.contains(nodeBlock))
				node = n
		}
		for (n : (entityCopy as TripleRule).trgNodeBlocks) {
			if (nodeBlock.name.equals(n.name) && (nodeBlock.eContainer as TripleRule).trgNodeBlocks.contains(nodeBlock))
				node = n
		}
		val nb = node
		
		var sizeOfTypeList = 0
		var sizeOfIncomingRefTypeList = 0		
		'''
			class «labelForTripleRuleComponent(nb)» «IF nb.action !== null && nb.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF nb.action !== null && nb.action.op == ActionOperator.DELETE»<<RED>>«ENDIF» «IF mainSelection»<<Selection>>«ENDIF»
			«FOR link : nb.relations»«{sizeOfTypeList = link.types.size - 1;""}»
				class «labelForTripleRuleComponent(link.target)» «IF link.target.action !== null && link.target.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF link.target.action !== null && link.target.action.op == ActionOperator.DELETE»<<RED>>«ENDIF»
				«IF link.action !== null»
					«labelForTripleRuleComponent(nb)» -«IF link.action.op.toString === '++'»[#SpringGreen]«ELSE»[#red]«ENDIF»-> «labelForTripleRuleComponent(link.target)» : "«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
				«ELSE»«labelForTripleRuleComponent(nb)» --> «labelForTripleRuleComponent(link.target)» : "«FOR t : link.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfTypeList > 0» | «ENDIF»«{sizeOfTypeList = sizeOfTypeList - 1;""}»«ENDFOR»"
				«ENDIF»
			«ENDFOR»
			«FOR attr : nb.properties»
				«labelForTripleRuleComponent(nb)» : «attr.type.name» = «printValue(attr.value)»
			«ENDFOR»
			«FOR incoming : getTripleRuleNodeBlocks(entityCopy as TripleRule).filter[n|n != nb]»
				«FOR incomingRef : incoming.relations»«{sizeOfIncomingRefTypeList = incomingRef.types.size - 1;""}»
					«IF incomingRef.target == nb && mainSelection»
						class «labelForTripleRuleComponent(incoming)» «IF incoming.action !== null && incoming.action.op == ActionOperator.CREATE»<<GREEN>>«ENDIF»«IF incoming.action !== null && incoming.action.op == ActionOperator.DELETE»<<RED>>«ENDIF»
						«labelForTripleRuleComponent(incoming)» -«IF incomingRef.action !== null && incomingRef.action.op === ActionOperator.CREATE»[#SpringGreen]«ENDIF»«IF incomingRef.action !== null && incomingRef.action.op === ActionOperator.DELETE»[#red]«ENDIF»-> «labelForTripleRuleComponent(nb)» : "«FOR t : incomingRef.types»«IF (t.type as MetamodelRelationStatement).name !== null && t.type !== null»«(t.type as MetamodelRelationStatement).name»«ELSE»?«ENDIF»«IF (t.lower !== null && t.upper !== null)»(«t.lower»..«t.upper»)«ENDIF»«IF sizeOfIncomingRefTypeList > 0» | «ENDIF»«{sizeOfIncomingRefTypeList = sizeOfIncomingRefTypeList - 1;""}»«ENDFOR»"
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
		'''
	}

	/**
	 * Returns the diagram text for the name of an object in a TripleRule.
	 */
	private def labelForTripleRuleComponent(ModelNodeBlock nb) {
		val entity = nb.eContainer as TripleRule
		'''"«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«nb.name»:«IF nb.type.name !== null »«nb.type.name»«ELSE»?«ENDIF»"'''
	}

	/**
	 * Returns the diagram text for the NACs of a given TripleRule.
	 */
	def String visualiseTripleRuleNACs(TripleRule entity) {
		var count = entity.nacs.size
		'''
			«FOR c : entity.nacs»
				«IF c.pattern.eContainer !== null»
					«visualiseEntity(c.pattern.eContainer as Pattern, false)»
				«ENDIF»
			«ENDFOR»
			
			legend bottom
				**forbid** «FOR c : entity.nacs»«IF c instanceof SourceNAC»src(«ELSE»trg(«ENDIF»«c.pattern.name»)«{count--; ""}»«IF count > 0» && «ENDIF»«ENDFOR»
			endlegend
		'''
	}

	/**
	 * Returns the diagram text for a TripleGrammar.
	 */
	def dispatch String visualiseEntity(TripleGrammar entity, boolean mainSelection) {
		'''
			together Source {
				«FOR mm : entity.srcMetamodels»
					class "«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«mm.name»"
				«ENDFOR»
			}
			
			together Target {
				«FOR mm : entity.trgMetamodels»
					class "«IF entity.abstract»//«ENDIF»«entity.name»«IF entity.abstract»//«ENDIF».«mm.name»"
				«ENDFOR»
			}
		'''
	}

	/**
	 * Returns the diagram text for the Metamodels in the source and target parts of a TripleGrammar.
	 */
	def String visualiseMetamodelsOfTripleGrammar(TripleGrammar tg, boolean mainSelection) {
		// TODO [Maximilian]
	}

	/*----------------------------------*/
	/*--------- Get NodeBlocks ---------*/
	/*----------------------------------*/
	/**
	 * Returns all NodeBlocks of a Model.
	 */
	def dispatch getNodeBlocks(Model entity) {
		entity.nodeBlocks
	}

	/**
	 * Returns all NodeBlocks of a Pattern.
	 */
	def dispatch getNodeBlocks(Pattern entity) {
		entity.body.nodeBlocks
	}

	/**
	 * Returns all NodeBlocks of a Rule.
	 */
	def dispatch getNodeBlocks(Rule entity) {
		entity.nodeBlocks
	}
	
	def getTripleRuleNodeBlocks(TripleRule entity) {
		var nodes = entity.srcNodeBlocks
		nodes.addAll(entity.trgNodeBlocks)
		return nodes
	}
	
	
	/*-----------------------------------------*/
	/*------ Get SuperRefinementTypes ---------*/
	/*-----------------------------------------*/

	def dispatch List<RefinementCommand> getSuperRefinementTypes(Model entity) {
		entity.superRefinementTypes
	}
	
	def dispatch getSuperRefinementTypes(Pattern entity) {
		entity.body.superRefinementTypes
	}
	
	def dispatch getSuperRefinementTypes(Rule entity) {
		entity.superRefinementTypes
	}
	
	def dispatch getSuperRefinementTypes(TripleRule entity) {
		entity.superRefinementTypes
	}
	
	def dispatch getSuperRefinementTypes(Constraint entity) {
		return newArrayList
	}
	
	def dispatch getSuperRefinementTypes(TripleGrammar entity) {
		return newArrayList
	}
	
	def dispatch getSuperRefinementTypes(GraphGrammar entity) {
		return newArrayList
	}
	
	/*------------------------------*/
	/*---------- Get Names ---------*/
	/*------------------------------*/

	def dispatch getName(Model entity) {
		entity.name
	}
	
	def dispatch getName(Metamodel entity) {
		entity.name
	}
	
	def dispatch getName(Pattern entity) {
		entity.body.name
	}
	
	def dispatch getName(Rule entity) {
		entity.name
	}
	
	def dispatch getName(TripleRule entity) {
		entity.name
	}
	


	/*------------------------------*/
	/*------------ Misc ------------*/
	/*------------------------------*/
	
	/**
	 * Returns the diagram text for all SuperTypes of a Pattern with inheritance arrows.
	 */
	def String visualiseSuperTypesInEntity(Entity entity) {
		var dispatcher = new EntityAttributeDispatcher();
		var superTypeNames = new HashMap<String, ArrayList<String[]>>()
		superTypeNames.put("Pattern", new ArrayList<String[]>())
		superTypeNames.put("Rule", new ArrayList<String[]>())
		superTypeNames.put("Model", new ArrayList<String[]>())
		superTypeNames.put("Metamodel", new ArrayList<String[]>())
		superTypeNames.put("TripleRule", new ArrayList<String[]>())
		
		for (st : entity.superRefinementTypes) {
			if (st instanceof RefinementCommand) {
				if ((st as RefinementCommand).referencedType instanceof AtomicPattern && !superTypeNames.get("Pattern").contains(((st as RefinementCommand).referencedType as AtomicPattern).name)) {
					val String[] tmp = #["", ""];
					tmp.set(0, ((st as RefinementCommand).referencedType as AtomicPattern).name)
					if (((st as RefinementCommand).referencedType as AtomicPattern).abstract)
						tmp.set(1, "1")
					else
						tmp.set(1, "0")
					superTypeNames.get("Pattern").add(tmp)
				} else if ((st as RefinementCommand).referencedType instanceof Rule && !superTypeNames.get("Rule").contains(((st as RefinementCommand).referencedType as Rule).name)) {
					val String[] tmp = #["", ""];
					tmp.set(0, (((st as RefinementCommand).referencedType as Rule).name))
					if (((st as RefinementCommand).referencedType as Rule).abstract)
						tmp.set(1, "1")
					else
						tmp.set(1, "0")
					superTypeNames.get("Rule").add(tmp)
				} else if ((st as RefinementCommand).referencedType instanceof Model && !superTypeNames.get("Model").contains(((st as RefinementCommand).referencedType as Model).name)) {
					val String[] tmp = #["", ""];
					tmp.set(0, ((st as RefinementCommand).referencedType as Model).name)
					if (((st as RefinementCommand).referencedType as Model).abstract)
						tmp.set(1, "1")
					else
						tmp.set(1, "0")
					superTypeNames.get("Model").add(tmp)
				} else if ((st as RefinementCommand).referencedType instanceof TripleRule && !superTypeNames.get("TripleRule").contains(((st as RefinementCommand).referencedType as TripleRule).name)) {
					val String[] tmp = #["", ""];
					tmp.set(0, ((st as RefinementCommand).referencedType as TripleRule).name)
					if (((st as RefinementCommand).referencedType as TripleRule).abstract)
						tmp.set(1, "1")
					else
						tmp.set(1, "0")
					superTypeNames.get("TripleRule").add(tmp)
				}
			}
		}
		'''
			«FOR type : superTypeNames.keySet»
				«FOR name : superTypeNames.get(type)»
					"«entity.eClass.name»: «IF dispatcher.getAbstract(entity)»//«ENDIF»«entity.name»«IF dispatcher.getAbstract(entity)»//«ENDIF»"--|>"«type»: «IF name.get(1).equals("1")»//«ENDIF»«name.get(0)»«IF name.get(1).equals("1")»//«ENDIF»"
				«ENDFOR»
			«ENDFOR»
		'''
	}
	
	/**
	 * Returns the diagram text for a link to the given entity to make it clickable.
	 */
	private static def link(Entity pattern) {
		val resource = pattern.eResource
		val uri = resource.URI + '#' + resource.getURIFragment(pattern)
		'''[[«uri»]]'''
	}

	/**
	 * Returns the ModelNodeBlock that is currently selected in the editor.
	 */
	def Optional<ModelNodeBlock> determineSelectedNodeBlock(ISelection selection, Entity entity) {
		if (selection instanceof TextSelection) {
			// For the TextSelection documents start with line 0.
			val selectionStart = selection.getStartLine() + 1;
			val selectionEnd = selection.getEndLine() + 1;
			if (!(entity instanceof GraphGrammar || entity instanceof TripleGrammar || entity instanceof Metamodel ||
				entity instanceof Enum || entity instanceof Constraint)) {
				if (entity instanceof TripleRule) {
					for (srcNodeBlock : entity.srcNodeBlocks) {
						val srcObject = NodeModelUtils.getNode(srcNodeBlock);
						if (srcObject !== null && selectionStart >= srcObject.getStartLine() && selectionEnd <= srcObject.getEndLine()) {
							return Optional.of(srcNodeBlock)
						}
					}
					for (trgNodeBlock : entity.trgNodeBlocks) {
						val trgObject = NodeModelUtils.getNode(trgNodeBlock);
						if (trgObject !== null && selectionStart >= trgObject.getStartLine() && selectionEnd <= trgObject.getEndLine()) {
							return Optional.of(trgNodeBlock)
						}
					}
				} else {
					for (nodeBlock : entity.nodeBlocks) {
						val object = NodeModelUtils.getNode(nodeBlock);
						if (object !== null && selectionStart >= object.getStartLine() && selectionEnd <= object.getEndLine()) {
							return Optional.of(nodeBlock)
						}
					}
				}
			}
		}

		Optional.empty()
	}

	/**
	 * Returns the MetamodelNodeBlock that is currently selected in the editor.
	 */
	def Optional<MetamodelNodeBlock> determineSelectedMetamodelNodeBlock(ISelection selection, Entity entity) {
		if (selection instanceof TextSelection) {
			// For the TextSelection documents start with line 0.
			val selectionStart = selection.getStartLine() + 1;
			val selectionEnd = selection.getEndLine() + 1;
			if (entity instanceof Metamodel)
				for (nodeBlock : entity.nodeBlocks) {
					val object = NodeModelUtils.getNode(nodeBlock);
					if (selectionStart >= object.getStartLine() && selectionEnd <= object.getEndLine()) {
						return Optional.of(nodeBlock)
					}
				}
		}

		Optional.empty()
	}

	/**
	 * Returns the Entity that is currently selected in the editor.
	 */
	def Optional<Entity> determineSelectedEntity(ISelection selection, EMSL_Spec root) {
		if (selection instanceof TextSelection) {
			// For the TextSelection documents start with line 0.
			val selectionStart = selection.getStartLine() + 1;
			val selectionEnd = selection.getEndLine() + 1;

			for (entity : root.entities) {
				val object = NodeModelUtils.getNode(entity);
				if (selectionStart >= object.getStartLine() && selectionEnd <= object.getEndLine()) {
					return Optional.of(entity);
				}
			}
		}

		Optional.empty()
	}

	override boolean supportsSelection(ISelection selection) {
		return true
	}

	override boolean supportsEditor(IEditorPart editor) {
		getRoot(editor).map([it instanceof EMSL_Spec]).orElse(false)
	}

	def getRoot(IEditorPart editor) {
		if (editor instanceof XtextEditor) {
			return editor.document.readOnly([res|
				if(!res.contents.empty) 
					return Optional.of(res.contents.get(0))
				else
					return Optional.empty
			])
		} else {
			return Optional.empty
		}
	}

	def plantUMLPreamble() {
		'''
			hide empty members
			hide circle
			hide stereotypes
			
			skinparam shadowing false
			
			skinparam class {
				BorderColor Black
				BorderColor<<GREEN>> SpringGreen
				BorderColor<<RED>> Red
				BackgroundColor White
				ArrowColor Black
				BackgroundColor<<Selection>> PapayaWhip
			}
			
			skinparam package {
				BackgroundColor GhostWhite
				BorderColor LightSlateGray
				Fontcolor LightSlateGray
				BackgroundColor<<Selection>> PapayaWhip
			}
			
			skinparam object {
				BorderColor Black
				BackgroundColor White
				ArrowColor Black
			}
			
			skinparam note {
				BorderColor Black
				BackgroundColor White
				ArrowColor Black
			}			
			
			skinparam rectangle {
				BackgroundColor<<Abstract>> LavenderBlush
			}
		'''
	}
}
