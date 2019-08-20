/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api;

import org.emoflon.neo.neo4j.adapter.models.NeoCoreBuilder;
import org.emoflon.neo.neo4j.adapter.patterns.NeoMatch;
import org.emoflon.neo.neo4j.adapter.rules.NeoCoMatch;
import org.emoflon.neo.emsl.eMSL.EMSL_Spec;
import org.emoflon.neo.emsl.eMSL.Model;
import org.emoflon.neo.emsl.eMSL.Metamodel;
import org.emoflon.neo.emsl.util.EMSLUtil;
import org.emoflon.neo.engine.api.rules.IPattern;
import org.emoflon.neo.engine.api.rules.IRule;
import org.emoflon.neo.neo4j.adapter.rules.NeoRule;
import org.emoflon.neo.neo4j.adapter.rules.NeoRuleAccess;
import org.emoflon.neo.neo4j.adapter.patterns.NeoPattern;
import org.emoflon.neo.neo4j.adapter.patterns.NeoPatternFactory;
import org.emoflon.neo.emsl.eMSL.Pattern;
import org.emoflon.neo.emsl.eMSL.Rule;
import org.emoflon.neo.neo4j.adapter.constraints.NeoConstraint;
import org.emoflon.neo.neo4j.adapter.constraints.NeoConstraintFactory;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.emsl.eMSL.Constraint;
import org.neo4j.driver.v1.Value;
import org.emoflon.neo.neo4j.adapter.patterns.NeoPatternAccess;
import org.emoflon.neo.neo4j.adapter.patterns.NeoMask;
import org.emoflon.neo.neo4j.adapter.patterns.NeoData;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class API_RiverCrossing {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	public API_RiverCrossing(NeoCoreBuilder builder, String platformResourceURIRoot, String platformPluginURIRoot){
		spec = (EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/RiverCrossing/src/RiverCrossing.msl", platformResourceURIRoot, platformPluginURIRoot);
		this.builder = builder;
	}

	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.0
	public Metamodel getMetamodel_RiverCrossing(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.1
	public Model getModel_RiverCrossingStart(){
		return (Model) spec.getEntities().get(1);
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.2
	public FarmerOnOtherSideAccess getPattern_FarmerOnOtherSide() {
		return new FarmerOnOtherSideAccess();
	}
	
	public class FarmerOnOtherSideAccess extends NeoPatternAccess<FarmerOnOtherSideData,FarmerOnOtherSideMask> {
		public final String b = "b";
		public final String r2 = "r2";
		public final String f = "f";
		
		@Override
		public NeoPattern matcher(){
			var p = (Pattern) spec.getEntities().get(2);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public NeoPattern matcher(FarmerOnOtherSideMask mask) {
			var p = (Pattern) spec.getEntities().get(2);
			return NeoPatternFactory.createNeoPattern(p, builder, mask);
		}
		
		@Override
		public FarmerOnOtherSideData data(NeoMatch m) {
			return new FarmerOnOtherSideData(m);
		}
		
		@Override
		public FarmerOnOtherSideMask mask() {
			return new FarmerOnOtherSideMask();
		}
	}
	
	public class FarmerOnOtherSideData extends NeoData {
		public final BNode b;
		public final B_figure_0_fRel b_figure_0_f;
		public final B_side_1_r2Rel b_side_1_r2;
		public final R2Node r2;
		public final FNode f;
		public final F_on_0_r2Rel f_on_0_r2;
		
		public FarmerOnOtherSideData(NeoMatch m) {
			var data = m.getData();
			var b = data.get("b");
			this.b = new BNode(b);
			var b_figure_0_f = data.get("b_figure_0_f");
			this.b_figure_0_f = new B_figure_0_fRel(b_figure_0_f);
			var b_side_1_r2 = data.get("b_side_1_r2");
			this.b_side_1_r2 = new B_side_1_r2Rel(b_side_1_r2);
			var r2 = data.get("r2");
			this.r2 = new R2Node(r2);
			var f = data.get("f");
			this.f = new FNode(f);
			var f_on_0_r2 = data.get("f_on_0_r2");
			this.f_on_0_r2 = new F_on_0_r2Rel(f_on_0_r2);
		}
		
		
		public class BNode {
			
			public BNode(Value b) {
			}
		}
		
		public class B_figure_0_fRel {
		
			public B_figure_0_fRel(Value b_figure_0_f) {
			}
		}
		public class B_side_1_r2Rel {
		
			public B_side_1_r2Rel(Value b_side_1_r2) {
			}
		}
		public class R2Node {
			public String side;
			
			public R2Node(Value r2) {
				if(!r2.get("side").isNull())
					this.side = r2.get("side").asString();
			}
		}
		
		public class FNode {
			
			public FNode(Value f) {
			}
		}
		
		public class F_on_0_r2Rel {
		
			public F_on_0_r2Rel(Value f_on_0_r2) {
			}
		}
	}
	
	public class FarmerOnOtherSideMask extends NeoMask {
	
		private HashMap<String, Long> nodeMask = new HashMap<>();
		private HashMap<String, Object> attributeMask = new HashMap<>();
		
		@Override
		public Map<String, Long> getMaskedNodes() {
			return nodeMask;
		}
		
		@Override
		public Map<String, Object> getMaskedAttributes() {
			return attributeMask;
		}
		
	
		public FarmerOnOtherSideMask setB(Long value) {
			nodeMask.put("b", value);
			return this;
		}
		public FarmerOnOtherSideMask setR2(Long value) {
			nodeMask.put("r2", value);
			return this;
		}
		public FarmerOnOtherSideMask setR2Side(String value) {
			attributeMask.put("r2.side", value);
			return this;
		}
		public FarmerOnOtherSideMask setF(Long value) {
			nodeMask.put("f", value);
			return this;
		}
	
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.3
	public WolfAndGoatOnOneSideAccess getPattern_WolfAndGoatOnOneSide() {
		return new WolfAndGoatOnOneSideAccess();
	}
	
	public class WolfAndGoatOnOneSideAccess extends NeoPatternAccess<WolfAndGoatOnOneSideData,WolfAndGoatOnOneSideMask> {
		public final String b = "b";
		public final String r1 = "r1";
		public final String w = "w";
		public final String g = "g";
		
		@Override
		public NeoPattern matcher(){
			var p = (Pattern) spec.getEntities().get(3);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public NeoPattern matcher(WolfAndGoatOnOneSideMask mask) {
			var p = (Pattern) spec.getEntities().get(3);
			return NeoPatternFactory.createNeoPattern(p, builder, mask);
		}
		
		@Override
		public WolfAndGoatOnOneSideData data(NeoMatch m) {
			return new WolfAndGoatOnOneSideData(m);
		}
		
		@Override
		public WolfAndGoatOnOneSideMask mask() {
			return new WolfAndGoatOnOneSideMask();
		}
	}
	
	public class WolfAndGoatOnOneSideData extends NeoData {
		public final BNode b;
		public final B_figure_0_wRel b_figure_0_w;
		public final B_figure_1_gRel b_figure_1_g;
		public final B_side_2_r1Rel b_side_2_r1;
		public final R1Node r1;
		public final WNode w;
		public final W_on_0_r1Rel w_on_0_r1;
		public final GNode g;
		public final G_on_0_r1Rel g_on_0_r1;
		
		public WolfAndGoatOnOneSideData(NeoMatch m) {
			var data = m.getData();
			var b = data.get("b");
			this.b = new BNode(b);
			var b_figure_0_w = data.get("b_figure_0_w");
			this.b_figure_0_w = new B_figure_0_wRel(b_figure_0_w);
			var b_figure_1_g = data.get("b_figure_1_g");
			this.b_figure_1_g = new B_figure_1_gRel(b_figure_1_g);
			var b_side_2_r1 = data.get("b_side_2_r1");
			this.b_side_2_r1 = new B_side_2_r1Rel(b_side_2_r1);
			var r1 = data.get("r1");
			this.r1 = new R1Node(r1);
			var w = data.get("w");
			this.w = new WNode(w);
			var w_on_0_r1 = data.get("w_on_0_r1");
			this.w_on_0_r1 = new W_on_0_r1Rel(w_on_0_r1);
			var g = data.get("g");
			this.g = new GNode(g);
			var g_on_0_r1 = data.get("g_on_0_r1");
			this.g_on_0_r1 = new G_on_0_r1Rel(g_on_0_r1);
		}
		
		
		public class BNode {
			
			public BNode(Value b) {
			}
		}
		
		public class B_figure_0_wRel {
		
			public B_figure_0_wRel(Value b_figure_0_w) {
			}
		}
		public class B_figure_1_gRel {
		
			public B_figure_1_gRel(Value b_figure_1_g) {
			}
		}
		public class B_side_2_r1Rel {
		
			public B_side_2_r1Rel(Value b_side_2_r1) {
			}
		}
		public class R1Node {
			public String side;
			
			public R1Node(Value r1) {
				if(!r1.get("side").isNull())
					this.side = r1.get("side").asString();
			}
		}
		
		public class WNode {
			
			public WNode(Value w) {
			}
		}
		
		public class W_on_0_r1Rel {
		
			public W_on_0_r1Rel(Value w_on_0_r1) {
			}
		}
		public class GNode {
			
			public GNode(Value g) {
			}
		}
		
		public class G_on_0_r1Rel {
		
			public G_on_0_r1Rel(Value g_on_0_r1) {
			}
		}
	}
	
	public class WolfAndGoatOnOneSideMask extends NeoMask {
	
		private HashMap<String, Long> nodeMask = new HashMap<>();
		private HashMap<String, Object> attributeMask = new HashMap<>();
		
		@Override
		public Map<String, Long> getMaskedNodes() {
			return nodeMask;
		}
		
		@Override
		public Map<String, Object> getMaskedAttributes() {
			return attributeMask;
		}
		
	
		public WolfAndGoatOnOneSideMask setB(Long value) {
			nodeMask.put("b", value);
			return this;
		}
		public WolfAndGoatOnOneSideMask setR1(Long value) {
			nodeMask.put("r1", value);
			return this;
		}
		public WolfAndGoatOnOneSideMask setR1Side(String value) {
			attributeMask.put("r1.side", value);
			return this;
		}
		public WolfAndGoatOnOneSideMask setW(Long value) {
			nodeMask.put("w", value);
			return this;
		}
		public WolfAndGoatOnOneSideMask setG(Long value) {
			nodeMask.put("g", value);
			return this;
		}
	
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.4
	public GoatAndCabbageOnOneSideAccess getPattern_GoatAndCabbageOnOneSide() {
		return new GoatAndCabbageOnOneSideAccess();
	}
	
	public class GoatAndCabbageOnOneSideAccess extends NeoPatternAccess<GoatAndCabbageOnOneSideData,GoatAndCabbageOnOneSideMask> {
		public final String b = "b";
		public final String r1 = "r1";
		public final String c = "c";
		public final String g = "g";
		
		@Override
		public NeoPattern matcher(){
			var p = (Pattern) spec.getEntities().get(4);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public NeoPattern matcher(GoatAndCabbageOnOneSideMask mask) {
			var p = (Pattern) spec.getEntities().get(4);
			return NeoPatternFactory.createNeoPattern(p, builder, mask);
		}
		
		@Override
		public GoatAndCabbageOnOneSideData data(NeoMatch m) {
			return new GoatAndCabbageOnOneSideData(m);
		}
		
		@Override
		public GoatAndCabbageOnOneSideMask mask() {
			return new GoatAndCabbageOnOneSideMask();
		}
	}
	
	public class GoatAndCabbageOnOneSideData extends NeoData {
		public final BNode b;
		public final B_figure_0_cRel b_figure_0_c;
		public final B_figure_1_gRel b_figure_1_g;
		public final B_side_2_r1Rel b_side_2_r1;
		public final R1Node r1;
		public final CNode c;
		public final C_on_0_r1Rel c_on_0_r1;
		public final GNode g;
		public final G_on_0_r1Rel g_on_0_r1;
		
		public GoatAndCabbageOnOneSideData(NeoMatch m) {
			var data = m.getData();
			var b = data.get("b");
			this.b = new BNode(b);
			var b_figure_0_c = data.get("b_figure_0_c");
			this.b_figure_0_c = new B_figure_0_cRel(b_figure_0_c);
			var b_figure_1_g = data.get("b_figure_1_g");
			this.b_figure_1_g = new B_figure_1_gRel(b_figure_1_g);
			var b_side_2_r1 = data.get("b_side_2_r1");
			this.b_side_2_r1 = new B_side_2_r1Rel(b_side_2_r1);
			var r1 = data.get("r1");
			this.r1 = new R1Node(r1);
			var c = data.get("c");
			this.c = new CNode(c);
			var c_on_0_r1 = data.get("c_on_0_r1");
			this.c_on_0_r1 = new C_on_0_r1Rel(c_on_0_r1);
			var g = data.get("g");
			this.g = new GNode(g);
			var g_on_0_r1 = data.get("g_on_0_r1");
			this.g_on_0_r1 = new G_on_0_r1Rel(g_on_0_r1);
		}
		
		
		public class BNode {
			
			public BNode(Value b) {
			}
		}
		
		public class B_figure_0_cRel {
		
			public B_figure_0_cRel(Value b_figure_0_c) {
			}
		}
		public class B_figure_1_gRel {
		
			public B_figure_1_gRel(Value b_figure_1_g) {
			}
		}
		public class B_side_2_r1Rel {
		
			public B_side_2_r1Rel(Value b_side_2_r1) {
			}
		}
		public class R1Node {
			public String side;
			
			public R1Node(Value r1) {
				if(!r1.get("side").isNull())
					this.side = r1.get("side").asString();
			}
		}
		
		public class CNode {
			
			public CNode(Value c) {
			}
		}
		
		public class C_on_0_r1Rel {
		
			public C_on_0_r1Rel(Value c_on_0_r1) {
			}
		}
		public class GNode {
			
			public GNode(Value g) {
			}
		}
		
		public class G_on_0_r1Rel {
		
			public G_on_0_r1Rel(Value g_on_0_r1) {
			}
		}
	}
	
	public class GoatAndCabbageOnOneSideMask extends NeoMask {
	
		private HashMap<String, Long> nodeMask = new HashMap<>();
		private HashMap<String, Object> attributeMask = new HashMap<>();
		
		@Override
		public Map<String, Long> getMaskedNodes() {
			return nodeMask;
		}
		
		@Override
		public Map<String, Object> getMaskedAttributes() {
			return attributeMask;
		}
		
	
		public GoatAndCabbageOnOneSideMask setB(Long value) {
			nodeMask.put("b", value);
			return this;
		}
		public GoatAndCabbageOnOneSideMask setR1(Long value) {
			nodeMask.put("r1", value);
			return this;
		}
		public GoatAndCabbageOnOneSideMask setR1Side(String value) {
			attributeMask.put("r1.side", value);
			return this;
		}
		public GoatAndCabbageOnOneSideMask setC(Long value) {
			nodeMask.put("c", value);
			return this;
		}
		public GoatAndCabbageOnOneSideMask setG(Long value) {
			nodeMask.put("g", value);
			return this;
		}
	
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.5
	public MoveEmptyToOtherSideAccess getRule_MoveEmptyToOtherSide() {
		return new MoveEmptyToOtherSideAccess();
	}
	
	public class MoveEmptyToOtherSideAccess extends NeoRuleAccess<MoveEmptyToOtherSideData,MoveEmptyToOtherSideMask> {
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(5);
			return new NeoRule(r, builder);
		}
		
		@Override
		public NeoRule rule(MoveEmptyToOtherSideMask mask) {
			var r = (Rule) spec.getEntities().get(5);
			return new NeoRule(r, builder, mask);
		}
		
		@Override
		public MoveEmptyToOtherSideData data(NeoMatch m) {
			return new MoveEmptyToOtherSideData(m);
		}
		
		@Override
		public MoveEmptyToOtherSideMask mask() {
			return new MoveEmptyToOtherSideMask();
		}
	}
	
	public class MoveEmptyToOtherSideData extends NeoData {
		public final BNode b;
		public final B_figure_0_fRel b_figure_0_f;
		public final B_side_1_r1Rel b_side_1_r1;
		public final B_side_2_r2Rel b_side_2_r2;
		public final R1Node r1;
		public final R2Node r2;
		public final FNode f;
		public final F_on_0_r1Rel f_on_0_r1;
		public final F_on_1_r2Rel f_on_1_r2;
		
		public MoveEmptyToOtherSideData(NeoMatch m) {
			var data = m.getData();
			var b = data.get("b");
			this.b = new BNode(b);
			var b_figure_0_f = data.get("b_figure_0_f");
			this.b_figure_0_f = new B_figure_0_fRel(b_figure_0_f);
			var b_side_1_r1 = data.get("b_side_1_r1");
			this.b_side_1_r1 = new B_side_1_r1Rel(b_side_1_r1);
			var b_side_2_r2 = data.get("b_side_2_r2");
			this.b_side_2_r2 = new B_side_2_r2Rel(b_side_2_r2);
			var r1 = data.get("r1");
			this.r1 = new R1Node(r1);
			var r2 = data.get("r2");
			this.r2 = new R2Node(r2);
			var f = data.get("f");
			this.f = new FNode(f);
			var f_on_0_r1 = data.get("f_on_0_r1");
			this.f_on_0_r1 = new F_on_0_r1Rel(f_on_0_r1);
			var f_on_1_r2 = data.get("f_on_1_r2");
			this.f_on_1_r2 = new F_on_1_r2Rel(f_on_1_r2);
		}
		
		
		public class BNode {
			
			public BNode(Value b) {
			}
		}
		
		public class B_figure_0_fRel {
		
			public B_figure_0_fRel(Value b_figure_0_f) {
			}
		}
		public class B_side_1_r1Rel {
		
			public B_side_1_r1Rel(Value b_side_1_r1) {
			}
		}
		public class B_side_2_r2Rel {
		
			public B_side_2_r2Rel(Value b_side_2_r2) {
			}
		}
		public class R1Node {
			public String side;
			
			public R1Node(Value r1) {
				if(!r1.get("side").isNull())
					this.side = r1.get("side").asString();
			}
		}
		
		public class R2Node {
			public String side;
			
			public R2Node(Value r2) {
				if(!r2.get("side").isNull())
					this.side = r2.get("side").asString();
			}
		}
		
		public class FNode {
			
			public FNode(Value f) {
			}
		}
		
		public class F_on_0_r1Rel {
		
			public F_on_0_r1Rel(Value f_on_0_r1) {
			}
		}
		public class F_on_1_r2Rel {
		
			public F_on_1_r2Rel(Value f_on_1_r2) {
			}
		}
	}
	
	public class MoveEmptyToOtherSideMask extends NeoMask {
	
		private HashMap<String, Long> nodeMask = new HashMap<>();
		private HashMap<String, Object> attributeMask = new HashMap<>();
		
		@Override
		public Map<String, Long> getMaskedNodes() {
			return nodeMask;
		}
		
		@Override
		public Map<String, Object> getMaskedAttributes() {
			return attributeMask;
		}
		
	
		public MoveEmptyToOtherSideMask setB(Long value) {
			nodeMask.put("b", value);
			return this;
		}
		public MoveEmptyToOtherSideMask setR1(Long value) {
			nodeMask.put("r1", value);
			return this;
		}
		public MoveEmptyToOtherSideMask setR1Side(String value) {
			attributeMask.put("r1.side", value);
			return this;
		}
		public MoveEmptyToOtherSideMask setR2(Long value) {
			nodeMask.put("r2", value);
			return this;
		}
		public MoveEmptyToOtherSideMask setR2Side(String value) {
			attributeMask.put("r2.side", value);
			return this;
		}
		public MoveEmptyToOtherSideMask setF(Long value) {
			nodeMask.put("f", value);
			return this;
		}
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.6
	public MoveOneFigureToOtherSideAccess getRule_MoveOneFigureToOtherSide() {
		return new MoveOneFigureToOtherSideAccess();
	}
	
	public class MoveOneFigureToOtherSideAccess extends NeoRuleAccess<MoveOneFigureToOtherSideData,MoveOneFigureToOtherSideMask> {
		@Override
		public NeoRule rule(){
			var r = (Rule) spec.getEntities().get(6);
			return new NeoRule(r, builder);
		}
		
		@Override
		public NeoRule rule(MoveOneFigureToOtherSideMask mask) {
			var r = (Rule) spec.getEntities().get(6);
			return new NeoRule(r, builder, mask);
		}
		
		@Override
		public MoveOneFigureToOtherSideData data(NeoMatch m) {
			return new MoveOneFigureToOtherSideData(m);
		}
		
		@Override
		public MoveOneFigureToOtherSideMask mask() {
			return new MoveOneFigureToOtherSideMask();
		}
	}
	
	public class MoveOneFigureToOtherSideData extends NeoData {
		public final BNode b;
		public final B_figure_0_fRel b_figure_0_f;
		public final B_figure_1_f1Rel b_figure_1_f1;
		public final B_side_2_r1Rel b_side_2_r1;
		public final B_side_3_r2Rel b_side_3_r2;
		public final R1Node r1;
		public final R2Node r2;
		public final FNode f;
		public final F_on_0_r1Rel f_on_0_r1;
		public final F_on_1_r2Rel f_on_1_r2;
		public final F1Node f1;
		public final F1_on_0_r1Rel f1_on_0_r1;
		public final F1_on_1_r2Rel f1_on_1_r2;
		
		public MoveOneFigureToOtherSideData(NeoMatch m) {
			var data = m.getData();
			var b = data.get("b");
			this.b = new BNode(b);
			var b_figure_0_f = data.get("b_figure_0_f");
			this.b_figure_0_f = new B_figure_0_fRel(b_figure_0_f);
			var b_figure_1_f1 = data.get("b_figure_1_f1");
			this.b_figure_1_f1 = new B_figure_1_f1Rel(b_figure_1_f1);
			var b_side_2_r1 = data.get("b_side_2_r1");
			this.b_side_2_r1 = new B_side_2_r1Rel(b_side_2_r1);
			var b_side_3_r2 = data.get("b_side_3_r2");
			this.b_side_3_r2 = new B_side_3_r2Rel(b_side_3_r2);
			var r1 = data.get("r1");
			this.r1 = new R1Node(r1);
			var r2 = data.get("r2");
			this.r2 = new R2Node(r2);
			var f = data.get("f");
			this.f = new FNode(f);
			var f_on_0_r1 = data.get("f_on_0_r1");
			this.f_on_0_r1 = new F_on_0_r1Rel(f_on_0_r1);
			var f_on_1_r2 = data.get("f_on_1_r2");
			this.f_on_1_r2 = new F_on_1_r2Rel(f_on_1_r2);
			var f1 = data.get("f1");
			this.f1 = new F1Node(f1);
			var f1_on_0_r1 = data.get("f1_on_0_r1");
			this.f1_on_0_r1 = new F1_on_0_r1Rel(f1_on_0_r1);
			var f1_on_1_r2 = data.get("f1_on_1_r2");
			this.f1_on_1_r2 = new F1_on_1_r2Rel(f1_on_1_r2);
		}
		
		
		public class BNode {
			
			public BNode(Value b) {
			}
		}
		
		public class B_figure_0_fRel {
		
			public B_figure_0_fRel(Value b_figure_0_f) {
			}
		}
		public class B_figure_1_f1Rel {
		
			public B_figure_1_f1Rel(Value b_figure_1_f1) {
			}
		}
		public class B_side_2_r1Rel {
		
			public B_side_2_r1Rel(Value b_side_2_r1) {
			}
		}
		public class B_side_3_r2Rel {
		
			public B_side_3_r2Rel(Value b_side_3_r2) {
			}
		}
		public class R1Node {
			public String side;
			
			public R1Node(Value r1) {
				if(!r1.get("side").isNull())
					this.side = r1.get("side").asString();
			}
		}
		
		public class R2Node {
			public String side;
			
			public R2Node(Value r2) {
				if(!r2.get("side").isNull())
					this.side = r2.get("side").asString();
			}
		}
		
		public class FNode {
			
			public FNode(Value f) {
			}
		}
		
		public class F_on_0_r1Rel {
		
			public F_on_0_r1Rel(Value f_on_0_r1) {
			}
		}
		public class F_on_1_r2Rel {
		
			public F_on_1_r2Rel(Value f_on_1_r2) {
			}
		}
		public class F1Node {
			
			public F1Node(Value f1) {
			}
		}
		
		public class F1_on_0_r1Rel {
		
			public F1_on_0_r1Rel(Value f1_on_0_r1) {
			}
		}
		public class F1_on_1_r2Rel {
		
			public F1_on_1_r2Rel(Value f1_on_1_r2) {
			}
		}
	}
	
	public class MoveOneFigureToOtherSideMask extends NeoMask {
	
		private HashMap<String, Long> nodeMask = new HashMap<>();
		private HashMap<String, Object> attributeMask = new HashMap<>();
		
		@Override
		public Map<String, Long> getMaskedNodes() {
			return nodeMask;
		}
		
		@Override
		public Map<String, Object> getMaskedAttributes() {
			return attributeMask;
		}
		
	
		public MoveOneFigureToOtherSideMask setB(Long value) {
			nodeMask.put("b", value);
			return this;
		}
		public MoveOneFigureToOtherSideMask setR1(Long value) {
			nodeMask.put("r1", value);
			return this;
		}
		public MoveOneFigureToOtherSideMask setR1Side(String value) {
			attributeMask.put("r1.side", value);
			return this;
		}
		public MoveOneFigureToOtherSideMask setR2(Long value) {
			nodeMask.put("r2", value);
			return this;
		}
		public MoveOneFigureToOtherSideMask setR2Side(String value) {
			attributeMask.put("r2.side", value);
			return this;
		}
		public MoveOneFigureToOtherSideMask setF(Long value) {
			nodeMask.put("f", value);
			return this;
		}
		public MoveOneFigureToOtherSideMask setF1(Long value) {
			nodeMask.put("f1", value);
			return this;
		}
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.7
	public IConstraint getConstraint_FigureCanEatsOtherFigure() {
		var c = (Constraint) spec.getEntities().get(7);
		return NeoConstraintFactory.createNeoConstraint(c, builder);
	}
	
	//:~> platform:/resource/RiverCrossing/src/RiverCrossing.msl#//@entities.8
	public FigureEatsOtherFigureAccess getPattern_FigureEatsOtherFigure() {
		return new FigureEatsOtherFigureAccess();
	}
	
	public class FigureEatsOtherFigureAccess extends NeoPatternAccess<FigureEatsOtherFigureData,FigureEatsOtherFigureMask> {
		public final String b = "b";
		public final String r1 = "r1";
		public final String f2 = "f2";
		public final String f3 = "f3";
		
		@Override
		public NeoPattern matcher(){
			var p = (Pattern) spec.getEntities().get(8);
			return NeoPatternFactory.createNeoPattern(p, builder);
		}
		
		@Override
		public NeoPattern matcher(FigureEatsOtherFigureMask mask) {
			var p = (Pattern) spec.getEntities().get(8);
			return NeoPatternFactory.createNeoPattern(p, builder, mask);
		}
		
		@Override
		public FigureEatsOtherFigureData data(NeoMatch m) {
			return new FigureEatsOtherFigureData(m);
		}
		
		@Override
		public FigureEatsOtherFigureMask mask() {
			return new FigureEatsOtherFigureMask();
		}
	}
	
	public class FigureEatsOtherFigureData extends NeoData {
		public final BNode b;
		public final B_figure_0_f2Rel b_figure_0_f2;
		public final B_figure_1_f3Rel b_figure_1_f3;
		public final B_side_2_r1Rel b_side_2_r1;
		public final R1Node r1;
		public final F2Node f2;
		public final F2_eats_0_f3Rel f2_eats_0_f3;
		public final F2_on_1_r1Rel f2_on_1_r1;
		public final F3Node f3;
		public final F3_on_0_r1Rel f3_on_0_r1;
		
		public FigureEatsOtherFigureData(NeoMatch m) {
			var data = m.getData();
			var b = data.get("b");
			this.b = new BNode(b);
			var b_figure_0_f2 = data.get("b_figure_0_f2");
			this.b_figure_0_f2 = new B_figure_0_f2Rel(b_figure_0_f2);
			var b_figure_1_f3 = data.get("b_figure_1_f3");
			this.b_figure_1_f3 = new B_figure_1_f3Rel(b_figure_1_f3);
			var b_side_2_r1 = data.get("b_side_2_r1");
			this.b_side_2_r1 = new B_side_2_r1Rel(b_side_2_r1);
			var r1 = data.get("r1");
			this.r1 = new R1Node(r1);
			var f2 = data.get("f2");
			this.f2 = new F2Node(f2);
			var f2_eats_0_f3 = data.get("f2_eats_0_f3");
			this.f2_eats_0_f3 = new F2_eats_0_f3Rel(f2_eats_0_f3);
			var f2_on_1_r1 = data.get("f2_on_1_r1");
			this.f2_on_1_r1 = new F2_on_1_r1Rel(f2_on_1_r1);
			var f3 = data.get("f3");
			this.f3 = new F3Node(f3);
			var f3_on_0_r1 = data.get("f3_on_0_r1");
			this.f3_on_0_r1 = new F3_on_0_r1Rel(f3_on_0_r1);
		}
		
		
		public class BNode {
			
			public BNode(Value b) {
			}
		}
		
		public class B_figure_0_f2Rel {
		
			public B_figure_0_f2Rel(Value b_figure_0_f2) {
			}
		}
		public class B_figure_1_f3Rel {
		
			public B_figure_1_f3Rel(Value b_figure_1_f3) {
			}
		}
		public class B_side_2_r1Rel {
		
			public B_side_2_r1Rel(Value b_side_2_r1) {
			}
		}
		public class R1Node {
			public String side;
			
			public R1Node(Value r1) {
				if(!r1.get("side").isNull())
					this.side = r1.get("side").asString();
			}
		}
		
		public class F2Node {
			
			public F2Node(Value f2) {
			}
		}
		
		public class F2_eats_0_f3Rel {
		
			public F2_eats_0_f3Rel(Value f2_eats_0_f3) {
			}
		}
		public class F2_on_1_r1Rel {
		
			public F2_on_1_r1Rel(Value f2_on_1_r1) {
			}
		}
		public class F3Node {
			
			public F3Node(Value f3) {
			}
		}
		
		public class F3_on_0_r1Rel {
		
			public F3_on_0_r1Rel(Value f3_on_0_r1) {
			}
		}
	}
	
	public class FigureEatsOtherFigureMask extends NeoMask {
	
		private HashMap<String, Long> nodeMask = new HashMap<>();
		private HashMap<String, Object> attributeMask = new HashMap<>();
		
		@Override
		public Map<String, Long> getMaskedNodes() {
			return nodeMask;
		}
		
		@Override
		public Map<String, Object> getMaskedAttributes() {
			return attributeMask;
		}
		
	
		public FigureEatsOtherFigureMask setB(Long value) {
			nodeMask.put("b", value);
			return this;
		}
		public FigureEatsOtherFigureMask setR1(Long value) {
			nodeMask.put("r1", value);
			return this;
		}
		public FigureEatsOtherFigureMask setR1Side(String value) {
			attributeMask.put("r1.side", value);
			return this;
		}
		public FigureEatsOtherFigureMask setF2(Long value) {
			nodeMask.put("f2", value);
			return this;
		}
		public FigureEatsOtherFigureMask setF3(Long value) {
			nodeMask.put("f3", value);
			return this;
		}
	
	}
}
