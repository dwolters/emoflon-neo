/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.resources.expected.metamodel;

import org.emoflon.neo.cypher.common.*;
import org.emoflon.neo.cypher.constraints.*;
import org.emoflon.neo.cypher.factories.*;
import org.emoflon.neo.cypher.models.*;
import org.emoflon.neo.cypher.patterns.*;
import org.emoflon.neo.cypher.rules.*;
import org.emoflon.neo.engine.api.patterns.*;
import org.emoflon.neo.engine.api.constraints.*;
import org.emoflon.neo.engine.api.rules.*;
import org.emoflon.neo.emsl.eMSL.*;
import org.emoflon.neo.emsl.util.*;
import org.neo4j.driver.Value;
import org.neo4j.driver.Record;
import org.eclipse.emf.common.util.URI;
import org.emoflon.neo.api.classinhhier2db.API_Common;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Optional;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class API_ClassInheritanceHierarchy {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_ClassInheritanceHierarchy(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_ClassInheritanceHierarchy(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/TestSuiteGT/resources/expected/metamodel/ClassInheritanceHierarchy.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_ClassInheritanceHierarchy(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/TestSuiteGT/resources/expected/metamodel/ClassInheritanceHierarchy.msl#//@entities.0
	public Metamodel getMetamodel_ClassInheritanceHierarchy(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	public static final String ClassInheritanceHierarchy__Attribute = "ClassInheritanceHierarchy__Attribute";
	public static final String ClassInheritanceHierarchy__ClassPackage = "ClassInheritanceHierarchy__ClassPackage";
	public static final String ClassInheritanceHierarchy__Clazz = "ClassInheritanceHierarchy__Clazz";
	public static final String ClassInheritanceHierarchy__NamedElement = "ClassInheritanceHierarchy__NamedElement";
}
