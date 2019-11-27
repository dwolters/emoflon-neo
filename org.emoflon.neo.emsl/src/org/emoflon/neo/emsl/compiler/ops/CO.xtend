package org.emoflon.neo.emsl.compiler.ops

import org.emoflon.neo.emsl.compiler.ILPOperation
import org.emoflon.neo.emsl.eMSL.Action
import java.util.Collection
import org.emoflon.neo.emsl.eMSL.Parameter
import org.emoflon.neo.emsl.compiler.ParameterData
import java.util.Map

class CO extends ILPOperation {

	/*
	 * --------------------------------
	 * GT rule generation methods
	 * --------------------------------
	 */

	override String getNameExtension() {
		return "_CO"
	}
	
	override getAction(Action action, boolean isSrc) {
		return ""
	}

	override requiresCorrModelCreation() {
		false
	}
	
	override selectParamGroupRepresentative(Collection<Parameter> paramGroup, Map<Parameter, ParameterData> paramsToData) {
		paramGroup.head
	}

	/*
	 * --------------------------------
	 * app generation methods
	 * --------------------------------
	 */
	
	override additionalImports(String tggName) {
		// TODO
		'''
		'''
	}
	
	override additionalFields(String tggName) {
		// TODO
		'''
		'''
	}
	
	override createGeneratorMethodBody(String tggName) {
		// TODO
		'''
		'''
	}
	
	override additionalMethods() {
		// TODO
		'''
		'''
	}
	
	override exportMetamodels() {
		false
	}
}
