/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api;

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
public class API_Instagram {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_Instagram(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_Instagram(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot){
		spec = (EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/FacebookToInstagramFASE/src/Instagram.msl", platformResourceURIRoot, platformPluginURIRoot);
		this.builder = builder;
	}

	//:~> platform:/resource/FacebookToInstagramFASE/src/Instagram.msl#//@entities.0
	public Metamodel getMetamodel_InstagramLanguage(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	public static final String InstagramLanguage__Network = "InstagramLanguage__Network";
	public static final String InstagramLanguage__User = "InstagramLanguage__User";
	
	//:~> platform:/resource/FacebookToInstagramFASE/src/Instagram.msl#//@entities.1
	public IConstraint getConstraint_NoDoubleInstagramUsers() {
		var c = (Constraint) spec.getEntities().get(1);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
	
	//:~> platform:/resource/FacebookToInstagramFASE/src/Instagram.msl#//@entities.2
	public DoubleInstagramUsersAccess getPattern_DoubleInstagramUsers() {
		return new DoubleInstagramUsersAccess();
	}
	
	public class DoubleInstagramUsersAccess extends NeoPatternAccess<DoubleInstagramUsersData,DoubleInstagramUsersMask> {
		public final String n = "n";
		public final String u = "u";
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(2);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<DoubleInstagramUsersData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new DoubleInstagramUsersData(d));
		}
		
		@Override
		public DoubleInstagramUsersMask mask() {
			return new DoubleInstagramUsersMask();
		}
	}
	
	public class DoubleInstagramUsersData extends NeoData {
		public final NNode n;
		public final N_users_0_uRel n_users_0_u;
		public final N_users_1_uRel n_users_1_u;
		public final UNode u;
		
		public DoubleInstagramUsersData(Record data) {
			var n = data.get("n");
			this.n = new NNode(n);
			var n_users_0_u = data.get("n_users_0_u");
			this.n_users_0_u = new N_users_0_uRel(n_users_0_u);
			var n_users_1_u = data.get("n_users_1_u");
			this.n_users_1_u = new N_users_1_uRel(n_users_1_u);
			var u = data.get("u");
			this.u = new UNode(u);
		}
		
		
		public class NNode {
			public String description;
			
			public NNode(Value n) {
				if(!n.get("description").isNull())
					this.description = n.get("description").asString();
			}
		}
		
		public class N_users_0_uRel {
		
			public N_users_0_uRel(Value n_users_0_u) {
			}
		}
		public class N_users_1_uRel {
		
			public N_users_1_uRel(Value n_users_1_u) {
			}
		}
		public class UNode {
			public String name;
			
			public UNode(Value u) {
				if(!u.get("name").isNull())
					this.name = u.get("name").asString();
			}
		}
		
	}
	
	public class DoubleInstagramUsersMask extends NeoMask {
		public DoubleInstagramUsersMask setN(Long value) {
			nodeMask.put("n", value);
			return this;
		}
		public DoubleInstagramUsersMask setNDescription(String value) {
			attributeMask.put("n.description", value);
			return this;
		}
		public DoubleInstagramUsersMask setU(Long value) {
			nodeMask.put("u", value);
			return this;
		}
		public DoubleInstagramUsersMask setUName(String value) {
			attributeMask.put("u.name", value);
			return this;
		}
	
	}
	
	//:~> platform:/resource/FacebookToInstagramFASE/src/Instagram.msl#//@entities.3
	public IConstraint getConstraint_NoDoubleFollowership() {
		var c = (Constraint) spec.getEntities().get(3);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
	
	//:~> platform:/resource/FacebookToInstagramFASE/src/Instagram.msl#//@entities.4
	public DoubleFollowershipAccess getPattern_DoubleFollowership() {
		return new DoubleFollowershipAccess();
	}
	
	public class DoubleFollowershipAccess extends NeoPatternAccess<DoubleFollowershipData,DoubleFollowershipMask> {
		public final String u1 = "u1";
		public final String u2 = "u2";
		
		@Override
		public NeoPattern pattern(){
			var p = (Pattern) spec.getEntities().get(4);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public Stream<DoubleFollowershipData> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new DoubleFollowershipData(d));
		}
		
		@Override
		public DoubleFollowershipMask mask() {
			return new DoubleFollowershipMask();
		}
	}
	
	public class DoubleFollowershipData extends NeoData {
		public final U1Node u1;
		public final U1_follows_0_u2Rel u1_follows_0_u2;
		public final U1_follows_1_u2Rel u1_follows_1_u2;
		public final U2Node u2;
		
		public DoubleFollowershipData(Record data) {
			var u1 = data.get("u1");
			this.u1 = new U1Node(u1);
			var u1_follows_0_u2 = data.get("u1_follows_0_u2");
			this.u1_follows_0_u2 = new U1_follows_0_u2Rel(u1_follows_0_u2);
			var u1_follows_1_u2 = data.get("u1_follows_1_u2");
			this.u1_follows_1_u2 = new U1_follows_1_u2Rel(u1_follows_1_u2);
			var u2 = data.get("u2");
			this.u2 = new U2Node(u2);
		}
		
		
		public class U1Node {
			public String name;
			
			public U1Node(Value u1) {
				if(!u1.get("name").isNull())
					this.name = u1.get("name").asString();
			}
		}
		
		public class U1_follows_0_u2Rel {
		
			public U1_follows_0_u2Rel(Value u1_follows_0_u2) {
			}
		}
		public class U1_follows_1_u2Rel {
		
			public U1_follows_1_u2Rel(Value u1_follows_1_u2) {
			}
		}
		public class U2Node {
			public String name;
			
			public U2Node(Value u2) {
				if(!u2.get("name").isNull())
					this.name = u2.get("name").asString();
			}
		}
		
	}
	
	public class DoubleFollowershipMask extends NeoMask {
		public DoubleFollowershipMask setU1(Long value) {
			nodeMask.put("u1", value);
			return this;
		}
		public DoubleFollowershipMask setU1Name(String value) {
			attributeMask.put("u1.name", value);
			return this;
		}
		public DoubleFollowershipMask setU2(Long value) {
			nodeMask.put("u2", value);
			return this;
		}
		public DoubleFollowershipMask setU2Name(String value) {
			attributeMask.put("u2.name", value);
			return this;
		}
	
	}
}