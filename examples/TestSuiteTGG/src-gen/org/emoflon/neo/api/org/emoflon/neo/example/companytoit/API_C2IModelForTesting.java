/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.org.emoflon.neo.example.companytoit;

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
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Record;
import org.eclipse.emf.common.util.URI;
import org.emoflon.neo.api.API_Common;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Optional;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class API_C2IModelForTesting {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	/** Use this constructor for default values */
	public API_C2IModelForTesting(NeoCoreBuilder builder) {
		this(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI, API_Common.NEOCORE_URI_INSTALLED);
	}

	/** Use this constructor to configure values for loading EMSL files */
	public API_C2IModelForTesting(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot, String neocoreURI){
		spec = (EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl", platformResourceURIRoot, platformPluginURIRoot, neocoreURI);
		this.builder = builder;
	}

	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.0
	public Model getModel_InconsistentSource1(){
		return (Model) spec.getEntities().get(0);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.1
	public Model getModel_InconsistentTarget1(){
		return (Model) spec.getEntities().get(1);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.2
	public InconsistentTriple1Access getRule_InconsistentTriple1() {
		return new InconsistentTriple1Access();
	}
	
	public class InconsistentTriple1Access extends NeoRuleCoAccess<InconsistentTriple1Data, InconsistentTriple1CoData, InconsistentTriple1Mask> {
		public final String _c = "c";
		public final String _a1 = "a1";
		public final String _e1 = "e1";
		public final String _it = "it";
		public final String _router = "router";
		public final String _l1 = "l1";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(2);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<InconsistentTriple1Data> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new InconsistentTriple1Data(d));
		}
			
		@Override
		public Stream<InconsistentTriple1CoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new InconsistentTriple1CoData(d));
		}
		
		@Override
		public InconsistentTriple1Mask mask() {
			return new InconsistentTriple1Mask();
		}
	}
	
	public class InconsistentTriple1Data extends NeoData {
		public final CNode _c;
		public final A1Node _a1;
		public final E1Node _e1;
		public final ItNode _it;
		public final RouterNode _router;
		public final L1Node _l1;
		
		public InconsistentTriple1Data(Record data) {
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
		}
		
		
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
	}
	
	public class InconsistentTriple1CoData extends NeoData {
		public final CNode _c;
		public final C_corr_0_itRel _c_corr_0_it;
		public final A1Node _a1;
		public final A1_corr_0_routerRel _a1_corr_0_router;
		public final E1Node _e1;
		public final E1_corr_0_l1Rel _e1_corr_0_l1;
		public final ItNode _it;
		public final RouterNode _router;
		public final L1Node _l1;
	
		public InconsistentTriple1CoData(Record data) {
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _c_corr_0_it = data.get("c_corr_0_it");
			this._c_corr_0_it = new C_corr_0_itRel(_c_corr_0_it);
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _a1_corr_0_router = data.get("a1_corr_0_router");
			this._a1_corr_0_router = new A1_corr_0_routerRel(_a1_corr_0_router);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
			var _e1_corr_0_l1 = data.get("e1_corr_0_l1");
			this._e1_corr_0_l1 = new E1_corr_0_l1Rel(_e1_corr_0_l1);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
		}
		
	
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class C_corr_0_itRel {
			public String __type_;
		
			public C_corr_0_itRel(Value _c_corr_0_it) {
				if(!_c_corr_0_it.get("_type_").isNull())
					this.__type_ = _c_corr_0_it.get("_type_").asString();
			}
		}
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class A1_corr_0_routerRel {
			public String __type_;
		
			public A1_corr_0_routerRel(Value _a1_corr_0_router) {
				if(!_a1_corr_0_router.get("_type_").isNull())
					this.__type_ = _a1_corr_0_router.get("_type_").asString();
			}
		}
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
		public class E1_corr_0_l1Rel {
			public String __type_;
		
			public E1_corr_0_l1Rel(Value _e1_corr_0_l1) {
				if(!_e1_corr_0_l1.get("_type_").isNull())
					this.__type_ = _e1_corr_0_l1.get("_type_").asString();
			}
		}
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
	}
	
	public class InconsistentTriple1Mask extends NeoMask {
		public InconsistentTriple1Mask setC(Long value) {
			nodeMask.put("c", value);
			return this;
		}
		public InconsistentTriple1Mask setCName(String value) {
			attributeMask.put("c.name", value);
			return this;
		}
		public InconsistentTriple1Mask setC_corr_0_it_type_(String value) {
			attributeMask.put("c_corr_0_it._type_", value);
			return this;
		}
		public InconsistentTriple1Mask setA1(Long value) {
			nodeMask.put("a1", value);
			return this;
		}
		public InconsistentTriple1Mask setA1Name(String value) {
			attributeMask.put("a1.name", value);
			return this;
		}
		public InconsistentTriple1Mask setA1_corr_0_router_type_(String value) {
			attributeMask.put("a1_corr_0_router._type_", value);
			return this;
		}
		public InconsistentTriple1Mask setE1(Long value) {
			nodeMask.put("e1", value);
			return this;
		}
		public InconsistentTriple1Mask setE1Name(String value) {
			attributeMask.put("e1.name", value);
			return this;
		}
		public InconsistentTriple1Mask setE1_corr_0_l1_type_(String value) {
			attributeMask.put("e1_corr_0_l1._type_", value);
			return this;
		}
		public InconsistentTriple1Mask setIt(Long value) {
			nodeMask.put("it", value);
			return this;
		}
		public InconsistentTriple1Mask setItName(String value) {
			attributeMask.put("it.name", value);
			return this;
		}
		public InconsistentTriple1Mask setRouter(Long value) {
			nodeMask.put("router", value);
			return this;
		}
		public InconsistentTriple1Mask setRouterName(String value) {
			attributeMask.put("router.name", value);
			return this;
		}
		public InconsistentTriple1Mask setL1(Long value) {
			nodeMask.put("l1", value);
			return this;
		}
		public InconsistentTriple1Mask setL1Name(String value) {
			attributeMask.put("l1.name", value);
			return this;
		}
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.3
	public Model getModel_ConsistentSource1(){
		return (Model) spec.getEntities().get(3);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.4
	public Model getModel_ConsistentTarget1(){
		return (Model) spec.getEntities().get(4);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.5
	public ConsistentTriple1Access getRule_ConsistentTriple1() {
		return new ConsistentTriple1Access();
	}
	
	public class ConsistentTriple1Access extends NeoRuleCoAccess<ConsistentTriple1Data, ConsistentTriple1CoData, ConsistentTriple1Mask> {
		public final String _a1 = "a1";
		public final String _router = "router";
		public final String _c = "c";
		public final String _l1 = "l1";
		public final String _l2 = "l2";
		public final String _it = "it";
		public final String _e1 = "e1";
		public final String _e2 = "e2";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(5);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<ConsistentTriple1Data> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new ConsistentTriple1Data(d));
		}
			
		@Override
		public Stream<ConsistentTriple1CoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new ConsistentTriple1CoData(d));
		}
		
		@Override
		public ConsistentTriple1Mask mask() {
			return new ConsistentTriple1Mask();
		}
	}
	
	public class ConsistentTriple1Data extends NeoData {
		public final A1Node _a1;
		public final RouterNode _router;
		public final CNode _c;
		public final L1Node _l1;
		public final L2Node _l2;
		public final ItNode _it;
		public final E1Node _e1;
		public final E2Node _e2;
		
		public ConsistentTriple1Data(Record data) {
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
			var _l2 = data.get("l2");
			this._l2 = new L2Node(_l2);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
			var _e2 = data.get("e2");
			this._e2 = new E2Node(_e2);
		}
		
		
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
		public class L2Node {
			public String _name;
			
			public L2Node(Value _l2) {
				if(!_l2.get("name").isNull())
					this._name = _l2.get("name").asString();
			}
		}
		
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
		public class E2Node {
			public String _name;
			
			public E2Node(Value _e2) {
				if(!_e2.get("name").isNull())
					this._name = _e2.get("name").asString();
			}
		}
		
	}
	
	public class ConsistentTriple1CoData extends NeoData {
		public final A1Node _a1;
		public final A1_corr_0_routerRel _a1_corr_0_router;
		public final RouterNode _router;
		public final CNode _c;
		public final C_corr_0_itRel _c_corr_0_it;
		public final L1Node _l1;
		public final L2Node _l2;
		public final ItNode _it;
		public final E1Node _e1;
		public final E1_corr_0_l1Rel _e1_corr_0_l1;
		public final E2Node _e2;
		public final E2_corr_0_l2Rel _e2_corr_0_l2;
	
		public ConsistentTriple1CoData(Record data) {
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _a1_corr_0_router = data.get("a1_corr_0_router");
			this._a1_corr_0_router = new A1_corr_0_routerRel(_a1_corr_0_router);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _c_corr_0_it = data.get("c_corr_0_it");
			this._c_corr_0_it = new C_corr_0_itRel(_c_corr_0_it);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
			var _l2 = data.get("l2");
			this._l2 = new L2Node(_l2);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
			var _e1_corr_0_l1 = data.get("e1_corr_0_l1");
			this._e1_corr_0_l1 = new E1_corr_0_l1Rel(_e1_corr_0_l1);
			var _e2 = data.get("e2");
			this._e2 = new E2Node(_e2);
			var _e2_corr_0_l2 = data.get("e2_corr_0_l2");
			this._e2_corr_0_l2 = new E2_corr_0_l2Rel(_e2_corr_0_l2);
		}
		
	
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class A1_corr_0_routerRel {
			public String __type_;
		
			public A1_corr_0_routerRel(Value _a1_corr_0_router) {
				if(!_a1_corr_0_router.get("_type_").isNull())
					this.__type_ = _a1_corr_0_router.get("_type_").asString();
			}
		}
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class C_corr_0_itRel {
			public String __type_;
		
			public C_corr_0_itRel(Value _c_corr_0_it) {
				if(!_c_corr_0_it.get("_type_").isNull())
					this.__type_ = _c_corr_0_it.get("_type_").asString();
			}
		}
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
		public class L2Node {
			public String _name;
			
			public L2Node(Value _l2) {
				if(!_l2.get("name").isNull())
					this._name = _l2.get("name").asString();
			}
		}
		
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
		public class E1_corr_0_l1Rel {
			public String __type_;
		
			public E1_corr_0_l1Rel(Value _e1_corr_0_l1) {
				if(!_e1_corr_0_l1.get("_type_").isNull())
					this.__type_ = _e1_corr_0_l1.get("_type_").asString();
			}
		}
		public class E2Node {
			public String _name;
			
			public E2Node(Value _e2) {
				if(!_e2.get("name").isNull())
					this._name = _e2.get("name").asString();
			}
		}
		
		public class E2_corr_0_l2Rel {
			public String __type_;
		
			public E2_corr_0_l2Rel(Value _e2_corr_0_l2) {
				if(!_e2_corr_0_l2.get("_type_").isNull())
					this.__type_ = _e2_corr_0_l2.get("_type_").asString();
			}
		}
	}
	
	public class ConsistentTriple1Mask extends NeoMask {
		public ConsistentTriple1Mask setA1(Long value) {
			nodeMask.put("a1", value);
			return this;
		}
		public ConsistentTriple1Mask setA1Name(String value) {
			attributeMask.put("a1.name", value);
			return this;
		}
		public ConsistentTriple1Mask setA1_corr_0_router_type_(String value) {
			attributeMask.put("a1_corr_0_router._type_", value);
			return this;
		}
		public ConsistentTriple1Mask setRouter(Long value) {
			nodeMask.put("router", value);
			return this;
		}
		public ConsistentTriple1Mask setRouterName(String value) {
			attributeMask.put("router.name", value);
			return this;
		}
		public ConsistentTriple1Mask setC(Long value) {
			nodeMask.put("c", value);
			return this;
		}
		public ConsistentTriple1Mask setCName(String value) {
			attributeMask.put("c.name", value);
			return this;
		}
		public ConsistentTriple1Mask setC_corr_0_it_type_(String value) {
			attributeMask.put("c_corr_0_it._type_", value);
			return this;
		}
		public ConsistentTriple1Mask setL1(Long value) {
			nodeMask.put("l1", value);
			return this;
		}
		public ConsistentTriple1Mask setL1Name(String value) {
			attributeMask.put("l1.name", value);
			return this;
		}
		public ConsistentTriple1Mask setL2(Long value) {
			nodeMask.put("l2", value);
			return this;
		}
		public ConsistentTriple1Mask setL2Name(String value) {
			attributeMask.put("l2.name", value);
			return this;
		}
		public ConsistentTriple1Mask setIt(Long value) {
			nodeMask.put("it", value);
			return this;
		}
		public ConsistentTriple1Mask setItName(String value) {
			attributeMask.put("it.name", value);
			return this;
		}
		public ConsistentTriple1Mask setE1(Long value) {
			nodeMask.put("e1", value);
			return this;
		}
		public ConsistentTriple1Mask setE1Name(String value) {
			attributeMask.put("e1.name", value);
			return this;
		}
		public ConsistentTriple1Mask setE1_corr_0_l1_type_(String value) {
			attributeMask.put("e1_corr_0_l1._type_", value);
			return this;
		}
		public ConsistentTriple1Mask setE2(Long value) {
			nodeMask.put("e2", value);
			return this;
		}
		public ConsistentTriple1Mask setE2Name(String value) {
			attributeMask.put("e2.name", value);
			return this;
		}
		public ConsistentTriple1Mask setE2_corr_0_l2_type_(String value) {
			attributeMask.put("e2_corr_0_l2._type_", value);
			return this;
		}
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.6
	public Model getModel_InconsistentSource2(){
		return (Model) spec.getEntities().get(6);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.7
	public Model getModel_InconsistentTarget2(){
		return (Model) spec.getEntities().get(7);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.8
	public InconsistentTriple2Access getRule_InconsistentTriple2() {
		return new InconsistentTriple2Access();
	}
	
	public class InconsistentTriple2Access extends NeoRuleCoAccess<InconsistentTriple2Data, InconsistentTriple2CoData, InconsistentTriple2Mask> {
		public final String _a1 = "a1";
		public final String _router = "router";
		public final String _c = "c";
		public final String _l1 = "l1";
		public final String _it = "it";
		public final String _e1 = "e1";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(8);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<InconsistentTriple2Data> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new InconsistentTriple2Data(d));
		}
			
		@Override
		public Stream<InconsistentTriple2CoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new InconsistentTriple2CoData(d));
		}
		
		@Override
		public InconsistentTriple2Mask mask() {
			return new InconsistentTriple2Mask();
		}
	}
	
	public class InconsistentTriple2Data extends NeoData {
		public final A1Node _a1;
		public final RouterNode _router;
		public final CNode _c;
		public final L1Node _l1;
		public final ItNode _it;
		public final E1Node _e1;
		
		public InconsistentTriple2Data(Record data) {
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
		}
		
		
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
	}
	
	public class InconsistentTriple2CoData extends NeoData {
		public final A1Node _a1;
		public final A1_corr_0_routerRel _a1_corr_0_router;
		public final RouterNode _router;
		public final CNode _c;
		public final C_corr_0_itRel _c_corr_0_it;
		public final L1Node _l1;
		public final ItNode _it;
		public final E1Node _e1;
		public final E1_corr_0_l1Rel _e1_corr_0_l1;
	
		public InconsistentTriple2CoData(Record data) {
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _a1_corr_0_router = data.get("a1_corr_0_router");
			this._a1_corr_0_router = new A1_corr_0_routerRel(_a1_corr_0_router);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _c_corr_0_it = data.get("c_corr_0_it");
			this._c_corr_0_it = new C_corr_0_itRel(_c_corr_0_it);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
			var _e1_corr_0_l1 = data.get("e1_corr_0_l1");
			this._e1_corr_0_l1 = new E1_corr_0_l1Rel(_e1_corr_0_l1);
		}
		
	
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class A1_corr_0_routerRel {
			public String __type_;
		
			public A1_corr_0_routerRel(Value _a1_corr_0_router) {
				if(!_a1_corr_0_router.get("_type_").isNull())
					this.__type_ = _a1_corr_0_router.get("_type_").asString();
			}
		}
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class C_corr_0_itRel {
			public String __type_;
		
			public C_corr_0_itRel(Value _c_corr_0_it) {
				if(!_c_corr_0_it.get("_type_").isNull())
					this.__type_ = _c_corr_0_it.get("_type_").asString();
			}
		}
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
		public class E1_corr_0_l1Rel {
			public String __type_;
		
			public E1_corr_0_l1Rel(Value _e1_corr_0_l1) {
				if(!_e1_corr_0_l1.get("_type_").isNull())
					this.__type_ = _e1_corr_0_l1.get("_type_").asString();
			}
		}
	}
	
	public class InconsistentTriple2Mask extends NeoMask {
		public InconsistentTriple2Mask setA1(Long value) {
			nodeMask.put("a1", value);
			return this;
		}
		public InconsistentTriple2Mask setA1Name(String value) {
			attributeMask.put("a1.name", value);
			return this;
		}
		public InconsistentTriple2Mask setA1_corr_0_router_type_(String value) {
			attributeMask.put("a1_corr_0_router._type_", value);
			return this;
		}
		public InconsistentTriple2Mask setRouter(Long value) {
			nodeMask.put("router", value);
			return this;
		}
		public InconsistentTriple2Mask setRouterName(String value) {
			attributeMask.put("router.name", value);
			return this;
		}
		public InconsistentTriple2Mask setC(Long value) {
			nodeMask.put("c", value);
			return this;
		}
		public InconsistentTriple2Mask setCName(String value) {
			attributeMask.put("c.name", value);
			return this;
		}
		public InconsistentTriple2Mask setC_corr_0_it_type_(String value) {
			attributeMask.put("c_corr_0_it._type_", value);
			return this;
		}
		public InconsistentTriple2Mask setL1(Long value) {
			nodeMask.put("l1", value);
			return this;
		}
		public InconsistentTriple2Mask setL1Name(String value) {
			attributeMask.put("l1.name", value);
			return this;
		}
		public InconsistentTriple2Mask setIt(Long value) {
			nodeMask.put("it", value);
			return this;
		}
		public InconsistentTriple2Mask setItName(String value) {
			attributeMask.put("it.name", value);
			return this;
		}
		public InconsistentTriple2Mask setE1(Long value) {
			nodeMask.put("e1", value);
			return this;
		}
		public InconsistentTriple2Mask setE1Name(String value) {
			attributeMask.put("e1.name", value);
			return this;
		}
		public InconsistentTriple2Mask setE1_corr_0_l1_type_(String value) {
			attributeMask.put("e1_corr_0_l1._type_", value);
			return this;
		}
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.9
	public Model getModel_InconsistentSource3(){
		return (Model) spec.getEntities().get(9);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.10
	public Model getModel_InconsistentTarget3(){
		return (Model) spec.getEntities().get(10);
	}
	
	//:~> platform:/resource/TestSuiteTGG/src/org/emoflon/neo/example/companytoit/C2IModelForTesting.msl#//@entities.11
	public InconsistentTriple3Access getRule_InconsistentTriple3() {
		return new InconsistentTriple3Access();
	}
	
	public class InconsistentTriple3Access extends NeoRuleCoAccess<InconsistentTriple3Data, InconsistentTriple3CoData, InconsistentTriple3Mask> {
		public final String _a1 = "a1";
		public final String _router = "router";
		public final String _c = "c";
		public final String _l1 = "l1";
		public final String _it = "it";
		public final String _e1 = "e1";
		
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(11);
			return NeoRuleFactory.createNeoRule(r, builder);
		}
		
		@Override
		public Stream<InconsistentTriple3Data> data(Collection<NeoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new InconsistentTriple3Data(d));
		}
			
		@Override
		public Stream<InconsistentTriple3CoData> codata(Collection<NeoCoMatch> matches) {
			var data = NeoMatch.getData(matches);
			return data.stream().map(d -> new InconsistentTriple3CoData(d));
		}
		
		@Override
		public InconsistentTriple3Mask mask() {
			return new InconsistentTriple3Mask();
		}
	}
	
	public class InconsistentTriple3Data extends NeoData {
		public final A1Node _a1;
		public final RouterNode _router;
		public final CNode _c;
		public final L1Node _l1;
		public final ItNode _it;
		public final E1Node _e1;
		
		public InconsistentTriple3Data(Record data) {
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
		}
		
		
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
	}
	
	public class InconsistentTriple3CoData extends NeoData {
		public final A1Node _a1;
		public final A1_corr_0_routerRel _a1_corr_0_router;
		public final RouterNode _router;
		public final CNode _c;
		public final C_corr_0_itRel _c_corr_0_it;
		public final L1Node _l1;
		public final ItNode _it;
		public final E1Node _e1;
		public final E1_corr_0_l1Rel _e1_corr_0_l1;
	
		public InconsistentTriple3CoData(Record data) {
			var _a1 = data.get("a1");
			this._a1 = new A1Node(_a1);
			var _a1_corr_0_router = data.get("a1_corr_0_router");
			this._a1_corr_0_router = new A1_corr_0_routerRel(_a1_corr_0_router);
			var _router = data.get("router");
			this._router = new RouterNode(_router);
			var _c = data.get("c");
			this._c = new CNode(_c);
			var _c_corr_0_it = data.get("c_corr_0_it");
			this._c_corr_0_it = new C_corr_0_itRel(_c_corr_0_it);
			var _l1 = data.get("l1");
			this._l1 = new L1Node(_l1);
			var _it = data.get("it");
			this._it = new ItNode(_it);
			var _e1 = data.get("e1");
			this._e1 = new E1Node(_e1);
			var _e1_corr_0_l1 = data.get("e1_corr_0_l1");
			this._e1_corr_0_l1 = new E1_corr_0_l1Rel(_e1_corr_0_l1);
		}
		
	
		public class A1Node {
			public String _name;
			
			public A1Node(Value _a1) {
				if(!_a1.get("name").isNull())
					this._name = _a1.get("name").asString();
			}
		}
		
		public class A1_corr_0_routerRel {
			public String __type_;
		
			public A1_corr_0_routerRel(Value _a1_corr_0_router) {
				if(!_a1_corr_0_router.get("_type_").isNull())
					this.__type_ = _a1_corr_0_router.get("_type_").asString();
			}
		}
		public class RouterNode {
			public String _name;
			
			public RouterNode(Value _router) {
				if(!_router.get("name").isNull())
					this._name = _router.get("name").asString();
			}
		}
		
		public class CNode {
			public String _name;
			
			public CNode(Value _c) {
				if(!_c.get("name").isNull())
					this._name = _c.get("name").asString();
			}
		}
		
		public class C_corr_0_itRel {
			public String __type_;
		
			public C_corr_0_itRel(Value _c_corr_0_it) {
				if(!_c_corr_0_it.get("_type_").isNull())
					this.__type_ = _c_corr_0_it.get("_type_").asString();
			}
		}
		public class L1Node {
			public String _name;
			
			public L1Node(Value _l1) {
				if(!_l1.get("name").isNull())
					this._name = _l1.get("name").asString();
			}
		}
		
		public class ItNode {
			public String _name;
			
			public ItNode(Value _it) {
				if(!_it.get("name").isNull())
					this._name = _it.get("name").asString();
			}
		}
		
		public class E1Node {
			public String _name;
			
			public E1Node(Value _e1) {
				if(!_e1.get("name").isNull())
					this._name = _e1.get("name").asString();
			}
		}
		
		public class E1_corr_0_l1Rel {
			public String __type_;
		
			public E1_corr_0_l1Rel(Value _e1_corr_0_l1) {
				if(!_e1_corr_0_l1.get("_type_").isNull())
					this.__type_ = _e1_corr_0_l1.get("_type_").asString();
			}
		}
	}
	
	public class InconsistentTriple3Mask extends NeoMask {
		public InconsistentTriple3Mask setA1(Long value) {
			nodeMask.put("a1", value);
			return this;
		}
		public InconsistentTriple3Mask setA1Name(String value) {
			attributeMask.put("a1.name", value);
			return this;
		}
		public InconsistentTriple3Mask setA1_corr_0_router_type_(String value) {
			attributeMask.put("a1_corr_0_router._type_", value);
			return this;
		}
		public InconsistentTriple3Mask setRouter(Long value) {
			nodeMask.put("router", value);
			return this;
		}
		public InconsistentTriple3Mask setRouterName(String value) {
			attributeMask.put("router.name", value);
			return this;
		}
		public InconsistentTriple3Mask setC(Long value) {
			nodeMask.put("c", value);
			return this;
		}
		public InconsistentTriple3Mask setCName(String value) {
			attributeMask.put("c.name", value);
			return this;
		}
		public InconsistentTriple3Mask setC_corr_0_it_type_(String value) {
			attributeMask.put("c_corr_0_it._type_", value);
			return this;
		}
		public InconsistentTriple3Mask setL1(Long value) {
			nodeMask.put("l1", value);
			return this;
		}
		public InconsistentTriple3Mask setL1Name(String value) {
			attributeMask.put("l1.name", value);
			return this;
		}
		public InconsistentTriple3Mask setIt(Long value) {
			nodeMask.put("it", value);
			return this;
		}
		public InconsistentTriple3Mask setItName(String value) {
			attributeMask.put("it.name", value);
			return this;
		}
		public InconsistentTriple3Mask setE1(Long value) {
			nodeMask.put("e1", value);
			return this;
		}
		public InconsistentTriple3Mask setE1Name(String value) {
			attributeMask.put("e1.name", value);
			return this;
		}
		public InconsistentTriple3Mask setE1_corr_0_l1_type_(String value) {
			attributeMask.put("e1_corr_0_l1._type_", value);
			return this;
		}
	}
}
