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
public class API_JavaKeywords {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_JavaKeywords(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_JavaKeywords(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_JavaKeywords(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl#//@entities.0
	public Metamodel getMetamodel_Assert(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	public static final String assert__continue = "assert__continue";
	public static final String assert__for = "assert__for";
	public static final String assert__new = "assert__new";
	public static final String assert__switch = "assert__switch";
	public static final String assert__class = "assert__class";
	public static final String assert__const = "assert__const";
	
	//:~> platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl#//@entities.1
	public Model getModel_Interface(){
		return (Model) spec.getEntities().get(1);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl#//@entities.2
	public NativeAccess getPattern_Native() {
		return new NativeAccess();
	}
	
	public class NativeAccess extends NeoPatternAccess<NativeData, NativeMask> {
		public final String _try = "try";
		public final String _interface = "interface";
		
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(2);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<NativeData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new NativeData(d));
		}
		
		@Override
		public NativeMask mask() {
			return new NativeMask();
		}
	}
	
	public class NativeData extends NeoData {
		public NativeData(Record data) {
			
		}
	}
	
	public class NativeMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl#//@entities.3
	public TransientAccess getRule_Transient() {
		return new TransientAccess();
	}
	
	public class TransientAccess extends NeoRuleCoAccess<TransientData, TransientCoData, TransientMask> {
		public final String _try = "try";
		public final String _interface = "interface";
		
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(3);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<TransientData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new TransientData(d));
		}
			
		@Override
		public Stream<TransientCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new TransientCoData(d));
		}
		
		@Override
		public TransientMask mask() {
			return new TransientMask();
		}
	}
	
	public class TransientData extends NeoData {
		public TransientData(Record data) {
		
		}
	}
	
	public class TransientCoData extends NeoData {
		public TransientCoData(Record data) {
		
		}
	}
	
	public class TransientMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl#//@entities.4
	public void exportMetamodelsForPackage() throws FlattenerException {
		{
			var api = new org.emoflon.neo.api.classinhhier2db.bin.API_JavaKeywords(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
			builder.exportEMSLEntityToNeo4j(api.getMetamodel_Assert());
		}
		{
			var api = new org.emoflon.neo.api.classinhhier2db.bin.API_JavaKeywords(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
			builder.exportEMSLEntityToNeo4j(api.getMetamodel_Assert());
		}
	}
	
	public Collection<TripleRule> getTripleRulesOfPackage(){
		var rules = new HashSet<TripleRule>();
		var rs = spec.eResource().getResourceSet();
		{
			var uri = "platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl#//@entities.5";
			rules.add((TripleRule) rs.getEObject(URI.createURI(uri), true));
		}
		return rules;
	}
	
	public Collection<IConstraint> getConstraintsOfPackage(){
		var constraints = new HashSet<IConstraint>();
		var rs = spec.eResource().getResourceSet();
		return constraints;
	}
	
	//:~> platform:/resource/ClassInhHier2DB/JavaKeywords/bin/JavaKeywords.msl#//@entities.5
	public static final String package__static = "static";
	public static final String package__static__this = "this";
	public static final String package__static__try = "try";
	public static final String package__static__interface = "interface";
}
