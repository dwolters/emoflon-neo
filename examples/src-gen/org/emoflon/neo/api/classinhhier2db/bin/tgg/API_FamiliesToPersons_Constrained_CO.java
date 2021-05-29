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
public class API_FamiliesToPersons_Constrained_CO {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_FamiliesToPersons_Constrained_CO(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_FamiliesToPersons_Constrained_CO(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_FamiliesToPersons_Constrained_CO(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.0
	public Collection<NeoRule> getAllRulesForFamiliesToPersons_Constrained_CO() {
		Collection<NeoRule> rules = new HashSet<>();
		
		rules.add(getRule_Families2Persons().rule());
		rules.add(getRule_MotherToFemale().rule());
		rules.add(getRule_MotherOfExistingFamilyToFemale().rule());
		rules.add(getRule_FatherToMale().rule());
		rules.add(getRule_FatherOfExistingFamilyToMale().rule());
		rules.add(getRule_DaughterToFemale().rule());
		rules.add(getRule_DaughterOfExistingFamilyToFemale().rule());
		rules.add(getRule_SonToMale().rule());
		rules.add(getRule_SonOfExistingFamilyToMale().rule());
		return rules;
	}
	
	public Collection<NeoConstraint> getAllConstraintsForFamiliesToPersons_Constrained_CO() {
		Collection<NeoConstraint> constraints = new HashSet<>();
		return constraints;
	}
	
	public Collection<Rule> getAllEMSLRulesForFamiliesToPersons_Constrained_CO(){
		var rules = new HashSet<Rule>();
		rules.add((Rule) spec.getEntities().get(1));
		rules.add((Rule) spec.getEntities().get(2));
		rules.add((Rule) spec.getEntities().get(3));
		rules.add((Rule) spec.getEntities().get(4));
		rules.add((Rule) spec.getEntities().get(5));
		rules.add((Rule) spec.getEntities().get(6));
		rules.add((Rule) spec.getEntities().get(7));
		rules.add((Rule) spec.getEntities().get(8));
		rules.add((Rule) spec.getEntities().get(9));
		return rules;
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.1
	public Families2PersonsAccess getRule_Families2Persons() {
		return new Families2PersonsAccess();
	}
	
	public class Families2PersonsAccess extends NeoRuleCoAccess<Families2PersonsData, Families2PersonsCoData, Families2PersonsMask> {
		public final String _families = "families";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(1);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<Families2PersonsData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new Families2PersonsData(d));
		}
			
		@Override
		public Stream<Families2PersonsCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new Families2PersonsCoData(d));
		}
		
		@Override
		public Families2PersonsMask mask() {
			return new Families2PersonsMask();
		}
	}
	
	public class Families2PersonsData extends NeoData {
		public Families2PersonsData(Record data) {
		
		}
	}
	
	public class Families2PersonsCoData extends NeoData {
		public Families2PersonsCoData(Record data) {
		
		}
	}
	
	public class Families2PersonsMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.2
	public MotherToFemaleAccess getRule_MotherToFemale() {
		return new MotherToFemaleAccess();
	}
	
	public class MotherToFemaleAccess extends NeoRuleCoAccess<MotherToFemaleData, MotherToFemaleCoData, MotherToFemaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(2);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<MotherToFemaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MotherToFemaleData(d));
		}
			
		@Override
		public Stream<MotherToFemaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MotherToFemaleCoData(d));
		}
		
		@Override
		public MotherToFemaleMask mask() {
			return new MotherToFemaleMask();
		}
	}
	
	public class MotherToFemaleData extends NeoData {
		public MotherToFemaleData(Record data) {
		
		}
	}
	
	public class MotherToFemaleCoData extends NeoData {
		public MotherToFemaleCoData(Record data) {
		
		}
	}
	
	public class MotherToFemaleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.3
	public MotherOfExistingFamilyToFemaleAccess getRule_MotherOfExistingFamilyToFemale() {
		return new MotherOfExistingFamilyToFemaleAccess();
	}
	
	public class MotherOfExistingFamilyToFemaleAccess extends NeoRuleCoAccess<MotherOfExistingFamilyToFemaleData, MotherOfExistingFamilyToFemaleCoData, MotherOfExistingFamilyToFemaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(3);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<MotherOfExistingFamilyToFemaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MotherOfExistingFamilyToFemaleData(d));
		}
			
		@Override
		public Stream<MotherOfExistingFamilyToFemaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new MotherOfExistingFamilyToFemaleCoData(d));
		}
		
		@Override
		public MotherOfExistingFamilyToFemaleMask mask() {
			return new MotherOfExistingFamilyToFemaleMask();
		}
	}
	
	public class MotherOfExistingFamilyToFemaleData extends NeoData {
		public MotherOfExistingFamilyToFemaleData(Record data) {
		
		}
	}
	
	public class MotherOfExistingFamilyToFemaleCoData extends NeoData {
		public MotherOfExistingFamilyToFemaleCoData(Record data) {
		
		}
	}
	
	public class MotherOfExistingFamilyToFemaleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.4
	public FatherToMaleAccess getRule_FatherToMale() {
		return new FatherToMaleAccess();
	}
	
	public class FatherToMaleAccess extends NeoRuleCoAccess<FatherToMaleData, FatherToMaleCoData, FatherToMaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(4);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<FatherToMaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new FatherToMaleData(d));
		}
			
		@Override
		public Stream<FatherToMaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new FatherToMaleCoData(d));
		}
		
		@Override
		public FatherToMaleMask mask() {
			return new FatherToMaleMask();
		}
	}
	
	public class FatherToMaleData extends NeoData {
		public FatherToMaleData(Record data) {
		
		}
	}
	
	public class FatherToMaleCoData extends NeoData {
		public FatherToMaleCoData(Record data) {
		
		}
	}
	
	public class FatherToMaleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.5
	public FatherOfExistingFamilyToMaleAccess getRule_FatherOfExistingFamilyToMale() {
		return new FatherOfExistingFamilyToMaleAccess();
	}
	
	public class FatherOfExistingFamilyToMaleAccess extends NeoRuleCoAccess<FatherOfExistingFamilyToMaleData, FatherOfExistingFamilyToMaleCoData, FatherOfExistingFamilyToMaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(5);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<FatherOfExistingFamilyToMaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new FatherOfExistingFamilyToMaleData(d));
		}
			
		@Override
		public Stream<FatherOfExistingFamilyToMaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new FatherOfExistingFamilyToMaleCoData(d));
		}
		
		@Override
		public FatherOfExistingFamilyToMaleMask mask() {
			return new FatherOfExistingFamilyToMaleMask();
		}
	}
	
	public class FatherOfExistingFamilyToMaleData extends NeoData {
		public FatherOfExistingFamilyToMaleData(Record data) {
		
		}
	}
	
	public class FatherOfExistingFamilyToMaleCoData extends NeoData {
		public FatherOfExistingFamilyToMaleCoData(Record data) {
		
		}
	}
	
	public class FatherOfExistingFamilyToMaleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.6
	public DaughterToFemaleAccess getRule_DaughterToFemale() {
		return new DaughterToFemaleAccess();
	}
	
	public class DaughterToFemaleAccess extends NeoRuleCoAccess<DaughterToFemaleData, DaughterToFemaleCoData, DaughterToFemaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(6);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<DaughterToFemaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new DaughterToFemaleData(d));
		}
			
		@Override
		public Stream<DaughterToFemaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new DaughterToFemaleCoData(d));
		}
		
		@Override
		public DaughterToFemaleMask mask() {
			return new DaughterToFemaleMask();
		}
	}
	
	public class DaughterToFemaleData extends NeoData {
		public DaughterToFemaleData(Record data) {
		
		}
	}
	
	public class DaughterToFemaleCoData extends NeoData {
		public DaughterToFemaleCoData(Record data) {
		
		}
	}
	
	public class DaughterToFemaleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.7
	public DaughterOfExistingFamilyToFemaleAccess getRule_DaughterOfExistingFamilyToFemale() {
		return new DaughterOfExistingFamilyToFemaleAccess();
	}
	
	public class DaughterOfExistingFamilyToFemaleAccess extends NeoRuleCoAccess<DaughterOfExistingFamilyToFemaleData, DaughterOfExistingFamilyToFemaleCoData, DaughterOfExistingFamilyToFemaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(7);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<DaughterOfExistingFamilyToFemaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new DaughterOfExistingFamilyToFemaleData(d));
		}
			
		@Override
		public Stream<DaughterOfExistingFamilyToFemaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new DaughterOfExistingFamilyToFemaleCoData(d));
		}
		
		@Override
		public DaughterOfExistingFamilyToFemaleMask mask() {
			return new DaughterOfExistingFamilyToFemaleMask();
		}
	}
	
	public class DaughterOfExistingFamilyToFemaleData extends NeoData {
		public DaughterOfExistingFamilyToFemaleData(Record data) {
		
		}
	}
	
	public class DaughterOfExistingFamilyToFemaleCoData extends NeoData {
		public DaughterOfExistingFamilyToFemaleCoData(Record data) {
		
		}
	}
	
	public class DaughterOfExistingFamilyToFemaleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.8
	public SonToMaleAccess getRule_SonToMale() {
		return new SonToMaleAccess();
	}
	
	public class SonToMaleAccess extends NeoRuleCoAccess<SonToMaleData, SonToMaleCoData, SonToMaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(8);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<SonToMaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new SonToMaleData(d));
		}
			
		@Override
		public Stream<SonToMaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new SonToMaleCoData(d));
		}
		
		@Override
		public SonToMaleMask mask() {
			return new SonToMaleMask();
		}
	}
	
	public class SonToMaleData extends NeoData {
		public SonToMaleData(Record data) {
		
		}
	}
	
	public class SonToMaleCoData extends NeoData {
		public SonToMaleCoData(Record data) {
		
		}
	}
	
	public class SonToMaleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FamiliesToPersons_Constrained/bin/tgg/FamiliesToPersons_Constrained_CO.msl#//@entities.9
	public SonOfExistingFamilyToMaleAccess getRule_SonOfExistingFamilyToMale() {
		return new SonOfExistingFamilyToMaleAccess();
	}
	
	public class SonOfExistingFamilyToMaleAccess extends NeoRuleCoAccess<SonOfExistingFamilyToMaleData, SonOfExistingFamilyToMaleCoData, SonOfExistingFamilyToMaleMask> {
		public final String _f = "f";
		public final String _fm = "fm";
		public final String _families = "families";
		public final String _p = "p";
		public final String _persons = "persons";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(9);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<SonOfExistingFamilyToMaleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new SonOfExistingFamilyToMaleData(d));
		}
			
		@Override
		public Stream<SonOfExistingFamilyToMaleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new SonOfExistingFamilyToMaleCoData(d));
		}
		
		@Override
		public SonOfExistingFamilyToMaleMask mask() {
			return new SonOfExistingFamilyToMaleMask();
		}
	}
	
	public class SonOfExistingFamilyToMaleData extends NeoData {
		public SonOfExistingFamilyToMaleData(Record data) {
		
		}
	}
	
	public class SonOfExistingFamilyToMaleCoData extends NeoData {
		public SonOfExistingFamilyToMaleCoData(Record data) {
		
		}
	}
	
	public class SonOfExistingFamilyToMaleMask extends NeoMask {
	}
}
