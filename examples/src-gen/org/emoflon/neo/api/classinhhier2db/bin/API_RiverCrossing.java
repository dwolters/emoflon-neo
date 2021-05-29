/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.bin;

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
public class API_RiverCrossing {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_RiverCrossing(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_RiverCrossing(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_RiverCrossing(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.0
	public Metamodel getMetamodel_RiverCrossing(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	public static final String RiverCrossing__Board = "RiverCrossing__Board";
	public static final String RiverCrossing__Figure = "RiverCrossing__Figure";
	public static final String RiverCrossing__RiverSide = "RiverCrossing__RiverSide";
	public static final String RiverCrossing__Farmer = "RiverCrossing__Farmer";
	public static final String RiverCrossing__Goat = "RiverCrossing__Goat";
	public static final String RiverCrossing__Wolf = "RiverCrossing__Wolf";
	public static final String RiverCrossing__Cabbage = "RiverCrossing__Cabbage";
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.1
	public Model getModel_RiverCrossingStart(){
		return (Model) spec.getEntities().get(1);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.2
	public OneEatsAnotherAccess getPattern_OneEatsAnother() {
		return new OneEatsAnotherAccess();
	}
	
	public class OneEatsAnotherAccess extends NeoPatternAccess<OneEatsAnotherData, OneEatsAnotherMask> {
		public final String _b = "b";
		public final String _r1 = "r1";
		public final String _r2 = "r2";
		public final String _f = "f";
		public final String _f2 = "f2";
		public final String _f3 = "f3";
		
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(2);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<OneEatsAnotherData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new OneEatsAnotherData(d));
		}
		
		@Override
		public OneEatsAnotherMask mask() {
			return new OneEatsAnotherMask();
		}
	}
	
	public class OneEatsAnotherData extends NeoData {
		public OneEatsAnotherData(Record data) {
			
		}
	}
	
	public class OneEatsAnotherMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.3
	public OneEatsAnotherAfterTransportAccess getPattern_OneEatsAnotherAfterTransport() {
		return new OneEatsAnotherAfterTransportAccess();
	}
	
	public class OneEatsAnotherAfterTransportAccess extends NeoPatternAccess<OneEatsAnotherAfterTransportData, OneEatsAnotherAfterTransportMask> {
		public final String _b = "b";
		public final String _r1 = "r1";
		public final String _f1 = "f1";
		public final String _f = "f";
		public final String _f2 = "f2";
		public final String _f3 = "f3";
		
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(3);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<OneEatsAnotherAfterTransportData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new OneEatsAnotherAfterTransportData(d));
		}
		
		@Override
		public OneEatsAnotherAfterTransportMask mask() {
			return new OneEatsAnotherAfterTransportMask();
		}
	}
	
	public class OneEatsAnotherAfterTransportData extends NeoData {
		public OneEatsAnotherAfterTransportData(Record data) {
			
		}
	}
	
	public class OneEatsAnotherAfterTransportMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.4
	public IConstraint getConstraint_ForbidOneEatsAnother() {
		var c = (Constraint) spec.getEntities().get(4);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.5
	public IConstraint getConstraint_EnforceOneEatsAnother() {
		var c = (Constraint) spec.getEntities().get(5);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.6
	public MoveEmptyToOtherSideAccess getRule_MoveEmptyToOtherSide() {
		return new MoveEmptyToOtherSideAccess();
	}
	
	public class MoveEmptyToOtherSideAccess extends NeoRuleCoAccess<MoveEmptyToOtherSideData, MoveEmptyToOtherSideCoData, MoveEmptyToOtherSideMask> {
		public final String _b = "b";
		public final String _r1 = "r1";
		public final String _r2 = "r2";
		public final String _f = "f";
		
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(6);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<MoveEmptyToOtherSideData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveEmptyToOtherSideData(d));
		}
			
		@Override
		public Stream<MoveEmptyToOtherSideCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveEmptyToOtherSideCoData(d));
		}
		
		@Override
		public MoveEmptyToOtherSideMask mask() {
			return new MoveEmptyToOtherSideMask();
		}
	}
	
	public class MoveEmptyToOtherSideData extends NeoData {
		public MoveEmptyToOtherSideData(Record data) {
		
		}
	}
	
	public class MoveEmptyToOtherSideCoData extends NeoData {
		public MoveEmptyToOtherSideCoData(Record data) {
		
		}
	}
	
	public class MoveEmptyToOtherSideMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.7
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.8
	public MoveWolfToOtherSideAccess getRule_MoveWolfToOtherSide() {
		return new MoveWolfToOtherSideAccess();
	}
	
	public class MoveWolfToOtherSideAccess extends NeoRuleCoAccess<MoveWolfToOtherSideData, MoveWolfToOtherSideCoData, MoveWolfToOtherSideMask> {
		public final String _r2 = "r2";
		public final String _b = "b";
		public final String _f = "f";
		public final String _f1 = "f1";
		public final String _r1 = "r1";
		
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(8);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<MoveWolfToOtherSideData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveWolfToOtherSideData(d));
		}
			
		@Override
		public Stream<MoveWolfToOtherSideCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveWolfToOtherSideCoData(d));
		}
		
		@Override
		public MoveWolfToOtherSideMask mask() {
			return new MoveWolfToOtherSideMask();
		}
	}
	
	public class MoveWolfToOtherSideData extends NeoData {
		public MoveWolfToOtherSideData(Record data) {
		
		}
	}
	
	public class MoveWolfToOtherSideCoData extends NeoData {
		public MoveWolfToOtherSideCoData(Record data) {
		
		}
	}
	
	public class MoveWolfToOtherSideMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.9
	public MoveGoatToOtherSideAccess getRule_MoveGoatToOtherSide() {
		return new MoveGoatToOtherSideAccess();
	}
	
	public class MoveGoatToOtherSideAccess extends NeoRuleCoAccess<MoveGoatToOtherSideData, MoveGoatToOtherSideCoData, MoveGoatToOtherSideMask> {
		public final String _r2 = "r2";
		public final String _b = "b";
		public final String _f = "f";
		public final String _f1 = "f1";
		public final String _r1 = "r1";
		
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(9);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<MoveGoatToOtherSideData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveGoatToOtherSideData(d));
		}
			
		@Override
		public Stream<MoveGoatToOtherSideCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveGoatToOtherSideCoData(d));
		}
		
		@Override
		public MoveGoatToOtherSideMask mask() {
			return new MoveGoatToOtherSideMask();
		}
	}
	
	public class MoveGoatToOtherSideData extends NeoData {
		public MoveGoatToOtherSideData(Record data) {
		
		}
	}
	
	public class MoveGoatToOtherSideCoData extends NeoData {
		public MoveGoatToOtherSideCoData(Record data) {
		
		}
	}
	
	public class MoveGoatToOtherSideMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.10
	public MoveCabbageToOtherSideAccess getRule_MoveCabbageToOtherSide() {
		return new MoveCabbageToOtherSideAccess();
	}
	
	public class MoveCabbageToOtherSideAccess extends NeoRuleCoAccess<MoveCabbageToOtherSideData, MoveCabbageToOtherSideCoData, MoveCabbageToOtherSideMask> {
		public final String _r2 = "r2";
		public final String _b = "b";
		public final String _f = "f";
		public final String _f1 = "f1";
		public final String _r1 = "r1";
		
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(10);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<MoveCabbageToOtherSideData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveCabbageToOtherSideData(d));
		}
			
		@Override
		public Stream<MoveCabbageToOtherSideCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MoveCabbageToOtherSideCoData(d));
		}
		
		@Override
		public MoveCabbageToOtherSideMask mask() {
			return new MoveCabbageToOtherSideMask();
		}
	}
	
	public class MoveCabbageToOtherSideData extends NeoData {
		public MoveCabbageToOtherSideData(Record data) {
		
		}
	}
	
	public class MoveCabbageToOtherSideCoData extends NeoData {
		public MoveCabbageToOtherSideCoData(Record data) {
		
		}
	}
	
	public class MoveCabbageToOtherSideMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.11
	public PuzzleSolvedAccess getPattern_PuzzleSolved() {
		return new PuzzleSolvedAccess();
	}
	
	public class PuzzleSolvedAccess extends NeoPatternAccess<PuzzleSolvedData, PuzzleSolvedMask> {
		public final String _b = "b";
		public final String _f = "f";
		public final String _g = "g";
		public final String _c = "c";
		public final String _w = "w";
		public final String _r1 = "r1";
		public final String _r2 = "r2";
		
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(11);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<PuzzleSolvedData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new PuzzleSolvedData(d));
		}
		
		@Override
		public PuzzleSolvedMask mask() {
			return new PuzzleSolvedMask();
		}
	}
	
	public class PuzzleSolvedData extends NeoData {
		public PuzzleSolvedData(Record data) {
			
		}
	}
	
	public class PuzzleSolvedMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/RiverCrossing/bin/RiverCrossing.msl#//@entities.12
	public IConstraint getConstraint_GameEnded() {
		var c = (Constraint) spec.getEntities().get(12);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
}
