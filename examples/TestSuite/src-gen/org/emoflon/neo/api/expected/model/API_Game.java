/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.expected.model;

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
import org.emoflon.neo.api.API_Common;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Optional;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class API_Game {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_Game(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_Game(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot){
		spec = (EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/TestSuite/resources/expected/model/Game.msl", platformResourceURIRoot, platformPluginURIRoot);
		this.builder = builder;
	}

	//:~> platform:/resource/TestSuite/resources/expected/model/Game.msl#//@entities.0
	public Metamodel getMetamodel_SheRememberedCaterpillars(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	public static final String SheRememberedCaterpillars__Game = "SheRememberedCaterpillars__Game";
	public static final String SheRememberedCaterpillars__NamedElement = "SheRememberedCaterpillars__NamedElement";
	public static final String SheRememberedCaterpillars__Character = "SheRememberedCaterpillars__Character";
	public static final String SheRememberedCaterpillars__PlatformObject = "SheRememberedCaterpillars__PlatformObject";
	public static final String SheRememberedCaterpillars__Platform = "SheRememberedCaterpillars__Platform";
	public static final String SheRememberedCaterpillars__SimplePlatform = "SheRememberedCaterpillars__SimplePlatform";
	public static final String SheRememberedCaterpillars__ExitPlatform = "SheRememberedCaterpillars__ExitPlatform";
	public static final String SheRememberedCaterpillars__PlatformConnector = "SheRememberedCaterpillars__PlatformConnector";
	public static final String SheRememberedCaterpillars__Bridge = "SheRememberedCaterpillars__Bridge";
	public static final String SheRememberedCaterpillars__Wall = "SheRememberedCaterpillars__Wall";
	public static final String SheRememberedCaterpillars__Colored = "SheRememberedCaterpillars__Colored";
	
	//:~> platform:/resource/TestSuite/resources/expected/model/Game.msl#//@entities.1
	public Model getModel_Game(){
		return (Model) spec.getEntities().get(1);
	}
}
