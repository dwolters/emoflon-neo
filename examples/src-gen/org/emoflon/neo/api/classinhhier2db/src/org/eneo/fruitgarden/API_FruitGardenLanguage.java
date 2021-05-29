/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.src.org.eneo.fruitgarden;

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
public class API_FruitGardenLanguage {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_FruitGardenLanguage(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_FruitGardenLanguage(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/FruitGardenLanguage/src/org/eneo/fruitgarden/FruitGardenLanguage.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_FruitGardenLanguage(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/FruitGardenLanguage/src/org/eneo/fruitgarden/FruitGardenLanguage.msl#//@entities.0
	public Metamodel getMetamodel_FruitGarden(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	public static final String FruitGarden__FruitGarden = "FruitGarden__FruitGarden";
	public static final String FruitGarden__FruitTree = "FruitGarden__FruitTree";
	public static final String FruitGarden__PearTree = "FruitGarden__PearTree";
	public static final String FruitGarden__AppleTree = "FruitGarden__AppleTree";
	public static final String FruitGarden__PlumTree = "FruitGarden__PlumTree";
	public static final String FruitGarden__LemonTree = "FruitGarden__LemonTree";
	public static final String FruitGarden__Crow = "FruitGarden__Crow";
	public static final String FruitGarden__Fruit = "FruitGarden__Fruit";
	public static final String FruitGarden__Pear = "FruitGarden__Pear";
	public static final String FruitGarden__Apple = "FruitGarden__Apple";
	public static final String FruitGarden__Plum = "FruitGarden__Plum";
	public static final String FruitGarden__Lemon = "FruitGarden__Lemon";
	public static final String FruitGarden__PathWay = "FruitGarden__PathWay";
	public static final String FruitGarden__PathSegment = "FruitGarden__PathSegment";
	public static final String FruitGarden__FruitBasket = "FruitGarden__FruitBasket";
	
	//:~> platform:/resource/ClassInhHier2DB/FruitGardenLanguage/src/org/eneo/fruitgarden/FruitGardenLanguage.msl#//@entities.1
	public Model getModel_SampleGarden(){
		return (Model) spec.getEntities().get(1);
	}
}
