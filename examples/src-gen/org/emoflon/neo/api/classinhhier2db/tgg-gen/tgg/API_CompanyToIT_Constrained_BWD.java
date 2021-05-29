/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.tgg-gen.tgg;

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
public class API_CompanyToIT_Constrained_BWD {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_CompanyToIT_Constrained_BWD(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_CompanyToIT_Constrained_BWD(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_CompanyToIT_Constrained_BWD(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.0
	public Collection<NeoRule> getAllRulesForCompanyToIT_Constrained_BWD() {
		Collection<NeoRule> rules = new HashSet<>();
		
		rules.add(getRule_CompanyToITRule().rule());
		rules.add(getRule_AdminToRouterRule().rule());
		rules.add(getRule_EmployeeToPCRule().rule());
		rules.add(getRule_EmployeeToLaptopRule().rule());
		return rules;
	}
	
	public Collection<NeoConstraint> getAllConstraintsForCompanyToIT_Constrained_BWD() {
		Collection<NeoConstraint> constraints = new HashSet<>();
		return constraints;
	}
	
	public Collection<Rule> getAllEMSLRulesForCompanyToIT_Constrained_BWD(){
		var rules = new HashSet<Rule>();
		rules.add((Rule) spec.getEntities().get(1));
		rules.add((Rule) spec.getEntities().get(4));
		rules.add((Rule) spec.getEntities().get(7));
		rules.add((Rule) spec.getEntities().get(8));
		return rules;
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.1
	public CompanyToITRuleAccess getRule_CompanyToITRule() {
		return new CompanyToITRuleAccess();
	}
	
	public class CompanyToITRuleAccess extends NeoRuleCoAccess<CompanyToITRuleData, CompanyToITRuleCoData, CompanyToITRuleMask> {
		public final String _ceo = "ceo";
		public final String _company = "company";
		public final String _it = "it";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param__ceoName = "ceoName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(1);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<CompanyToITRuleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new CompanyToITRuleData(d));
		}
			
		@Override
		public Stream<CompanyToITRuleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new CompanyToITRuleCoData(d));
		}
		
		@Override
		public CompanyToITRuleMask mask() {
			return new CompanyToITRuleMask();
		}
	}
	
	public class CompanyToITRuleData extends NeoData {
		public CompanyToITRuleData(Record data) {
		
		}
	}
	
	public class CompanyToITRuleCoData extends NeoData {
		public CompanyToITRuleCoData(Record data) {
		
		}
	}
	
	public class CompanyToITRuleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.2
	public IConstraint getConstraint_CompanyToITRuleNAC() {
		var c = (Constraint) spec.getEntities().get(2);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.3
	public CompanyToITRule_companyNameIsTakenAccess getPattern_CompanyToITRule_companyNameIsTaken() {
		return new CompanyToITRule_companyNameIsTakenAccess();
	}
	
	public class CompanyToITRule_companyNameIsTakenAccess extends NeoPatternAccess<CompanyToITRule_companyNameIsTakenData, CompanyToITRule_companyNameIsTakenMask> {
		public final String _company = "company";
		public final String _it = "it";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(3);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<CompanyToITRule_companyNameIsTakenData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new CompanyToITRule_companyNameIsTakenData(d));
		}
		
		@Override
		public CompanyToITRule_companyNameIsTakenMask mask() {
			return new CompanyToITRule_companyNameIsTakenMask();
		}
	}
	
	public class CompanyToITRule_companyNameIsTakenData extends NeoData {
		public CompanyToITRule_companyNameIsTakenData(Record data) {
			
		}
	}
	
	public class CompanyToITRule_companyNameIsTakenMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.4
	public AdminToRouterRuleAccess getRule_AdminToRouterRule() {
		return new AdminToRouterRuleAccess();
	}
	
	public class AdminToRouterRuleAccess extends NeoRuleCoAccess<AdminToRouterRuleData, AdminToRouterRuleCoData, AdminToRouterRuleMask> {
		public final String _company = "company";
		public final String _ceo = "ceo";
		public final String _admin = "admin";
		public final String _router = "router";
		public final String _it = "it";
		public final String _network = "network";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(4);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<AdminToRouterRuleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new AdminToRouterRuleData(d));
		}
			
		@Override
		public Stream<AdminToRouterRuleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new AdminToRouterRuleCoData(d));
		}
		
		@Override
		public AdminToRouterRuleMask mask() {
			return new AdminToRouterRuleMask();
		}
	}
	
	public class AdminToRouterRuleData extends NeoData {
		public AdminToRouterRuleData(Record data) {
		
		}
	}
	
	public class AdminToRouterRuleCoData extends NeoData {
		public AdminToRouterRuleCoData(Record data) {
		
		}
	}
	
	public class AdminToRouterRuleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.5
	public IConstraint getConstraint_AdminToRouterRuleNAC() {
		var c = (Constraint) spec.getEntities().get(5);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.6
	public AdminToRouterRule_AlreadyHasAnAdminAccess getPattern_AdminToRouterRule_AlreadyHasAnAdmin() {
		return new AdminToRouterRule_AlreadyHasAnAdminAccess();
	}
	
	public class AdminToRouterRule_AlreadyHasAnAdminAccess extends NeoPatternAccess<AdminToRouterRule_AlreadyHasAnAdminData, AdminToRouterRule_AlreadyHasAnAdminMask> {
		public final String _company = "company";
		public final String _other = "other";
		public final String _ceo = "ceo";
		
		public final String _param____srcModelName = "__srcModelName";
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(6);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<AdminToRouterRule_AlreadyHasAnAdminData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new AdminToRouterRule_AlreadyHasAnAdminData(d));
		}
		
		@Override
		public AdminToRouterRule_AlreadyHasAnAdminMask mask() {
			return new AdminToRouterRule_AlreadyHasAnAdminMask();
		}
	}
	
	public class AdminToRouterRule_AlreadyHasAnAdminData extends NeoData {
		public AdminToRouterRule_AlreadyHasAnAdminData(Record data) {
			
		}
	}
	
	public class AdminToRouterRule_AlreadyHasAnAdminMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.7
	public EmployeeToPCRuleAccess getRule_EmployeeToPCRule() {
		return new EmployeeToPCRuleAccess();
	}
	
	public class EmployeeToPCRuleAccess extends NeoRuleCoAccess<EmployeeToPCRuleData, EmployeeToPCRuleCoData, EmployeeToPCRuleMask> {
		public final String _admin = "admin";
		public final String _company = "company";
		public final String _ceo = "ceo";
		public final String _employee = "employee";
		public final String _router = "router";
		public final String _computer = "computer";
		public final String _it = "it";
		public final String _network = "network";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param__employeeName = "employeeName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(7);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<EmployeeToPCRuleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new EmployeeToPCRuleData(d));
		}
			
		@Override
		public Stream<EmployeeToPCRuleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new EmployeeToPCRuleCoData(d));
		}
		
		@Override
		public EmployeeToPCRuleMask mask() {
			return new EmployeeToPCRuleMask();
		}
	}
	
	public class EmployeeToPCRuleData extends NeoData {
		public EmployeeToPCRuleData(Record data) {
		
		}
	}
	
	public class EmployeeToPCRuleCoData extends NeoData {
		public EmployeeToPCRuleCoData(Record data) {
		
		}
	}
	
	public class EmployeeToPCRuleMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/CompanyToIT_Constrained/tgg-gen/tgg/CompanyToIT_Constrained_BWD.msl#//@entities.8
	public EmployeeToLaptopRuleAccess getRule_EmployeeToLaptopRule() {
		return new EmployeeToLaptopRuleAccess();
	}
	
	public class EmployeeToLaptopRuleAccess extends NeoRuleCoAccess<EmployeeToLaptopRuleData, EmployeeToLaptopRuleCoData, EmployeeToLaptopRuleMask> {
		public final String _admin = "admin";
		public final String _company = "company";
		public final String _ceo = "ceo";
		public final String _employee = "employee";
		public final String _router = "router";
		public final String _computer = "computer";
		public final String _it = "it";
		public final String _network = "network";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param__employeeName = "employeeName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(8);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<EmployeeToLaptopRuleData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new EmployeeToLaptopRuleData(d));
		}
			
		@Override
		public Stream<EmployeeToLaptopRuleCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new EmployeeToLaptopRuleCoData(d));
		}
		
		@Override
		public EmployeeToLaptopRuleMask mask() {
			return new EmployeeToLaptopRuleMask();
		}
	}
	
	public class EmployeeToLaptopRuleData extends NeoData {
		public EmployeeToLaptopRuleData(Record data) {
		
		}
	}
	
	public class EmployeeToLaptopRuleCoData extends NeoData {
		public EmployeeToLaptopRuleCoData(Record data) {
		
		}
	}
	
	public class EmployeeToLaptopRuleMask extends NeoMask {
	}
}
