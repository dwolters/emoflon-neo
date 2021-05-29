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
public class API_FacebookToInstagramFASE_CO {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_FacebookToInstagramFASE_CO(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_FacebookToInstagramFASE_CO(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		this((EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/ClassInhHier2DB/FacebookToInstagramFASE/bin/tgg/FacebookToInstagramFASE_CO.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI), builder);
	}

	public API_FacebookToInstagramFASE_CO(EMSL_Spec spec, NeoCoreBuilder builder) {
		this.spec = spec;
		this.builder = builder;
	}

	//:~> platform:/resource/ClassInhHier2DB/FacebookToInstagramFASE/bin/tgg/FacebookToInstagramFASE_CO.msl#//@entities.0
	public Collection<NeoRule> getAllRulesForFacebookToInstagramFASE_CO() {
		Collection<NeoRule> rules = new HashSet<>();
		
		rules.add(getRule_NetworkToNetwork().rule());
		rules.add(getRule_UserToUser().rule());
		rules.add(getRule_RequestFriendship().rule());
		rules.add(getRule_AcceptFriendship().rule());
		return rules;
	}
	
	public Collection<NeoConstraint> getAllConstraintsForFacebookToInstagramFASE_CO() {
		Collection<NeoConstraint> constraints = new HashSet<>();
		return constraints;
	}
	
	public Collection<Rule> getAllEMSLRulesForFacebookToInstagramFASE_CO(){
		var rules = new HashSet<Rule>();
		rules.add((Rule) spec.getEntities().get(1));
		rules.add((Rule) spec.getEntities().get(2));
		rules.add((Rule) spec.getEntities().get(3));
		rules.add((Rule) spec.getEntities().get(4));
		return rules;
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FacebookToInstagramFASE/bin/tgg/FacebookToInstagramFASE_CO.msl#//@entities.1
	public NetworkToNetworkAccess getRule_NetworkToNetwork() {
		return new NetworkToNetworkAccess();
	}
	
	public class NetworkToNetworkAccess extends NeoRuleCoAccess<NetworkToNetworkData, NetworkToNetworkCoData, NetworkToNetworkMask> {
		public final String _fl = "fl";
		public final String _il = "il";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(1);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<NetworkToNetworkData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new NetworkToNetworkData(d));
		}
			
		@Override
		public Stream<NetworkToNetworkCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new NetworkToNetworkCoData(d));
		}
		
		@Override
		public NetworkToNetworkMask mask() {
			return new NetworkToNetworkMask();
		}
	}
	
	public class NetworkToNetworkData extends NeoData {
		public NetworkToNetworkData(Record data) {
		
		}
	}
	
	public class NetworkToNetworkCoData extends NeoData {
		public NetworkToNetworkCoData(Record data) {
		
		}
	}
	
	public class NetworkToNetworkMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FacebookToInstagramFASE/bin/tgg/FacebookToInstagramFASE_CO.msl#//@entities.2
	public UserToUserAccess getRule_UserToUser() {
		return new UserToUserAccess();
	}
	
	public class UserToUserAccess extends NeoRuleCoAccess<UserToUserData, UserToUserCoData, UserToUserMask> {
		public final String _fu = "fu";
		public final String _fn = "fn";
		public final String _iu = "iu";
		public final String _in = "in";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(2);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<UserToUserData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new UserToUserData(d));
		}
			
		@Override
		public Stream<UserToUserCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new UserToUserCoData(d));
		}
		
		@Override
		public UserToUserMask mask() {
			return new UserToUserMask();
		}
	}
	
	public class UserToUserData extends NeoData {
		public UserToUserData(Record data) {
		
		}
	}
	
	public class UserToUserCoData extends NeoData {
		public UserToUserCoData(Record data) {
		
		}
	}
	
	public class UserToUserMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FacebookToInstagramFASE/bin/tgg/FacebookToInstagramFASE_CO.msl#//@entities.3
	public RequestFriendshipAccess getRule_RequestFriendship() {
		return new RequestFriendshipAccess();
	}
	
	public class RequestFriendshipAccess extends NeoRuleCoAccess<RequestFriendshipData, RequestFriendshipCoData, RequestFriendshipMask> {
		public final String _fu1 = "fu1";
		public final String _fu2 = "fu2";
		public final String _fn = "fn";
		public final String _in = "in";
		public final String _iu1 = "iu1";
		public final String _iu2 = "iu2";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(3);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<RequestFriendshipData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new RequestFriendshipData(d));
		}
			
		@Override
		public Stream<RequestFriendshipCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new RequestFriendshipCoData(d));
		}
		
		@Override
		public RequestFriendshipMask mask() {
			return new RequestFriendshipMask();
		}
	}
	
	public class RequestFriendshipData extends NeoData {
		public RequestFriendshipData(Record data) {
		
		}
	}
	
	public class RequestFriendshipCoData extends NeoData {
		public RequestFriendshipCoData(Record data) {
		
		}
	}
	
	public class RequestFriendshipMask extends NeoMask {
	}
	
	//:~> platform:/resource/ClassInhHier2DB/FacebookToInstagramFASE/bin/tgg/FacebookToInstagramFASE_CO.msl#//@entities.4
	public AcceptFriendshipAccess getRule_AcceptFriendship() {
		return new AcceptFriendshipAccess();
	}
	
	public class AcceptFriendshipAccess extends NeoRuleCoAccess<AcceptFriendshipData, AcceptFriendshipCoData, AcceptFriendshipMask> {
		public final String _fu1 = "fu1";
		public final String _fu2 = "fu2";
		public final String _fn = "fn";
		public final String _fri = "fri";
		public final String _iu1 = "iu1";
		public final String _iu2 = "iu2";
		public final String _in = "in";
		
		public final String _param____srcModelName = "__srcModelName";
		public final String _param____trgModelName = "__trgModelName";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(4);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<AcceptFriendshipData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new AcceptFriendshipData(d));
		}
			
		@Override
		public Stream<AcceptFriendshipCoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new AcceptFriendshipCoData(d));
		}
		
		@Override
		public AcceptFriendshipMask mask() {
			return new AcceptFriendshipMask();
		}
	}
	
	public class AcceptFriendshipData extends NeoData {
		public AcceptFriendshipData(Record data) {
		
		}
	}
	
	public class AcceptFriendshipCoData extends NeoData {
		public AcceptFriendshipCoData(Record data) {
		
		}
	}
	
	public class AcceptFriendshipMask extends NeoMask {
	}
}
