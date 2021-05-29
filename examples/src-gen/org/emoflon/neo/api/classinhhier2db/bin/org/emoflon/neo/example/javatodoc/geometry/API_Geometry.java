/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.bin.org.emoflon.neo.example.javatodoc.geometry;

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
public class API_Geometry {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_Geometry(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_Geometry(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_Geometry(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.0
	public Model getModel_JavaGeometry(){
		return (Model) spec.getEntities().get(0);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.1
	public Model getModel_DocGeometry(){
		return (Model) spec.getEntities().get(1);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.2
	public CreateCorrsAccess getRule_CreateCorrs() {
		return new CreateCorrsAccess();
	}
	
	public class CreateCorrsAccess extends NeoRuleCoAccess<CreateCorrsData, CreateCorrsCoData, CreateCorrsMask> {
		public final String _parallelogramJ = "parallelogramJ";
		public final String _pAJ = "pAJ";
		public final String _pBJ = "pBJ";
		public final String _rectangleJ = "rectangleJ";
		public final String _rAJ = "rAJ";
		public final String _rBJ = "rBJ";
		public final String _rAreaJ = "rAreaJ";
		public final String _diamondJ = "diamondJ";
		public final String _dExtentJ = "dExtentJ";
		public final String _squareJ = "squareJ";
		public final String _sAreaJ = "sAreaJ";
		public final String _sAJ = "sAJ";
		public final String _sExtentJ = "sExtentJ";
		public final String _parallelogramD = "parallelogramD";
		public final String _pAD = "pAD";
		public final String _pBD = "pBD";
		public final String _rectangleD = "rectangleD";
		public final String _rAD = "rAD";
		public final String _rBD = "rBD";
		public final String _rAreaD = "rAreaD";
		public final String _diamondD = "diamondD";
		public final String _dExtentD = "dExtentD";
		public final String _squareD = "squareD";
		public final String _sAreaD = "sAreaD";
		public final String _sAD = "sAD";
		public final String _sExtentD = "sExtentD";
		
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(2);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<CreateCorrsData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new CreateCorrsData(d));
		}
			
		@Override
		public Stream<CreateCorrsCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new CreateCorrsCoData(d));
		}
		
		@Override
		public CreateCorrsMask mask() {
			return new CreateCorrsMask();
		}
	}
	
	public class CreateCorrsData extends NeoData {
		public CreateCorrsData(Record data) {
		
		}
	}
	
	public class CreateCorrsCoData extends NeoData {
		public CreateCorrsCoData(Record data) {
		
		}
	}
	
	public class CreateCorrsMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.3
	public Model getModel_JavaGeometry1(){
		return (Model) spec.getEntities().get(3);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.4
	public Model getModel_DocGeometry1(){
		return (Model) spec.getEntities().get(4);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.5
	public Model getModel_JavaGeometry2(){
		return (Model) spec.getEntities().get(5);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.6
	public Model getModel_DocGeometry2(){
		return (Model) spec.getEntities().get(6);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.7
	public Model getModel_JavaGeometry3(){
		return (Model) spec.getEntities().get(7);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.8
	public Model getModel_DocGeometry3(){
		return (Model) spec.getEntities().get(8);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.9
	public Model getModel_JavaGeometry4(){
		return (Model) spec.getEntities().get(9);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.10
	public Model getModel_DocGeometry4(){
		return (Model) spec.getEntities().get(10);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.11
	public Model getModel_JavaGeometry5(){
		return (Model) spec.getEntities().get(11);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.12
	public Model getModel_DocGeometry5(){
		return (Model) spec.getEntities().get(12);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.13
	public Model getModel_JavaGeometry13(){
		return (Model) spec.getEntities().get(13);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.14
	public Model getModel_JavaGeometry14(){
		return (Model) spec.getEntities().get(14);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.15
	public Model getModel_JavaGeometry24(){
		return (Model) spec.getEntities().get(15);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.16
	public Model getModel_JavaGeometry25(){
		return (Model) spec.getEntities().get(16);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/TestSuiteTGG/bin/org/emoflon/neo/example/javatodoc/geometry/Geometry.msl#//@entities.17
	public Model getModel_JavaGeometry35(){
		return (Model) spec.getEntities().get(17);
	}
}
