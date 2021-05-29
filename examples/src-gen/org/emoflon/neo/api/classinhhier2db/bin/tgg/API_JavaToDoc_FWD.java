/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.bin.tgg;

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
public class API_JavaToDoc_FWD {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_JavaToDoc_FWD(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_JavaToDoc_FWD(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/JavaToDoc/bin/tgg/JavaToDoc_FWD.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_JavaToDoc_FWD(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/JavaToDoc/bin/tgg/JavaToDoc_FWD.msl#//@entities.0
	public Collection<NeoRule> getAllRulesForJavaToDoc_FWD() {
		Collection<NeoRule> rules = new HashSet<>();
		
		rules.add(getRule_ClazzToDocRule().rule());
		rules.add(getRule_RootToRootRule().rule());
		rules.add(getRule_SubToSubRule().rule());
		return rules;
	}
	
	public Collection<NeoConstraint> getAllConstraintsForJavaToDoc_FWD() {
		Collection<NeoConstraint> constraints = new HashSet<>();
		return constraints;
	}
	
	public Collection<Rule> getAllEMSLRulesForJavaToDoc_FWD(){
		var rules = new HashSet<Rule>();
		rules.add((Rule) spec.getEntities().get(1));
		rules.add((Rule) spec.getEntities().get(2));
		rules.add((Rule) spec.getEntities().get(3));
		return rules;
	}
	
	//:~> platform:/resource/ClassInhHier2DB/JavaToDoc/bin/tgg/JavaToDoc_FWD.msl#//@entities.1
	public ClazzToDocRuleAccess getRule_ClazzToDocRule() {
		return new ClazzToDocRuleAccess();
	}
	
	public class ClazzToDocRuleAccess extends NeoRuleCoAccess<ClazzToDocRuleData, ClazzToDocRuleCoData, ClazzToDocRuleMask> {
		public final String _p = "p";
		public final String _c = "c";
		public final String _f = "f";
		public final String _doc = "doc";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(1);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<ClazzToDocRuleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new ClazzToDocRuleData(d));
		}
			
		@Override
		public Stream<ClazzToDocRuleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new ClazzToDocRuleCoData(d));
		}
		
		@Override
		public ClazzToDocRuleMask mask() {
			return new ClazzToDocRuleMask();
		}
	}
	
	public class ClazzToDocRuleData extends NeoData {
		public ClazzToDocRuleData(Record data) {
		
		}
	}
	
	public class ClazzToDocRuleCoData extends NeoData {
		public ClazzToDocRuleCoData(Record data) {
		
		}
	}
	
	public class ClazzToDocRuleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/JavaToDoc/bin/tgg/JavaToDoc_FWD.msl#//@entities.2
	public RootToRootRuleAccess getRule_RootToRootRule() {
		return new RootToRootRuleAccess();
	}
	
	public class RootToRootRuleAccess extends NeoRuleCoAccess<RootToRootRuleData, RootToRootRuleCoData, RootToRootRuleMask> {
		public final String _p = "p";
		public final String _f = "f";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(2);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<RootToRootRuleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new RootToRootRuleData(d));
		}
			
		@Override
		public Stream<RootToRootRuleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new RootToRootRuleCoData(d));
		}
		
		@Override
		public RootToRootRuleMask mask() {
			return new RootToRootRuleMask();
		}
	}
	
	public class RootToRootRuleData extends NeoData {
		public RootToRootRuleData(Record data) {
		
		}
	}
	
	public class RootToRootRuleCoData extends NeoData {
		public RootToRootRuleCoData(Record data) {
		
		}
	}
	
	public class RootToRootRuleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/JavaToDoc/bin/tgg/JavaToDoc_FWD.msl#//@entities.3
	public SubToSubRuleAccess getRule_SubToSubRule() {
		return new SubToSubRuleAccess();
	}
	
	public class SubToSubRuleAccess extends NeoRuleCoAccess<SubToSubRuleData, SubToSubRuleCoData, SubToSubRuleMask> {
		public final String _p = "p";
		public final String _subP = "subP";
		public final String _f = "f";
		public final String _subF = "subF";
		public final String _doc = "doc";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(3);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<SubToSubRuleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new SubToSubRuleData(d));
		}
			
		@Override
		public Stream<SubToSubRuleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new SubToSubRuleCoData(d));
		}
		
		@Override
		public SubToSubRuleMask mask() {
			return new SubToSubRuleMask();
		}
	}
	
	public class SubToSubRuleData extends NeoData {
		public SubToSubRuleData(Record data) {
		
		}
	}
	
	public class SubToSubRuleCoData extends NeoData {
		public SubToSubRuleCoData(Record data) {
		
		}
	}
	
	public class SubToSubRuleMask extends NeoMask {
	}
}
