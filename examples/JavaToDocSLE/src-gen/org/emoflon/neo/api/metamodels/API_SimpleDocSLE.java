/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.metamodels;

import org.emoflon.neo.cypher.common.*;
import org.emoflon.neo.cypher.factories.*;
import org.emoflon.neo.cypher.models.*;
import org.emoflon.neo.cypher.patterns.*;
import org.emoflon.neo.cypher.rules.*;
import org.emoflon.neo.engine.api.patterns.*;
import org.emoflon.neo.engine.api.constraints.*;
import org.emoflon.neo.engine.api.rules.*;
import org.emoflon.neo.emsl.eMSL.*;
import org.emoflon.neo.emsl.util.*;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Record;
import org.eclipse.emf.common.util.URI;
import org.emoflon.neo.api.API_Common;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Optional;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class API_SimpleDocSLE {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_SimpleDocSLE(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_SimpleDocSLE(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		spec = (EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/JavaToDocSLE/src/metamodels/SimpleDocSLE.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI);
		this.builder = builder;
	}

	//:~> platform:/resource/JavaToDocSLE/src/metamodels/SimpleDocSLE.msl#//@entities.0
	public Metamodel getMetamodel_SimpleDocSLE(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	public static final String SimpleDocSLE__Doc = "SimpleDocSLE__Doc";
	public static final String SimpleDocSLE__Entry = "SimpleDocSLE__Entry";
	public static final String SimpleDocSLE__GlossaryEntry = "SimpleDocSLE__GlossaryEntry";
	public static final String SimpleDocSLE__Glossary = "SimpleDocSLE__Glossary";
}
