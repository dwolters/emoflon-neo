/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api;

import org.emoflon.neo.neo4j.adapter.NeoCoreBuilder;
import org.emoflon.neo.emsl.eMSL.EMSL_Spec;
import org.emoflon.neo.emsl.eMSL.Model;
import org.emoflon.neo.emsl.eMSL.Metamodel;
import org.emoflon.neo.emsl.util.EMSLUtil;
import org.emoflon.neo.engine.api.rules.IPattern;
import org.emoflon.neo.neo4j.adapter.NeoPattern;
import org.emoflon.neo.emsl.eMSL.Pattern;
import org.emoflon.neo.neo4j.adapter.NeoConstraint;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.emsl.eMSL.Constraint;

@SuppressWarnings("unused")
public class API_Emsl_PacMan {
	private EMSL_Spec spec;
	private NeoCoreBuilder builder;

	public API_Emsl_PacMan(NeoCoreBuilder builder, String platformURIRoot){
		spec = (EMSL_Spec) EMSLUtil.loadSpecification("platform:/resource/PacMan/emsl/PacMan.msl", platformURIRoot);
		this.builder = builder;
	}
	
	public API_Emsl_PacMan(NeoCoreBuilder builder){
		this(builder, "../");
	}

	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.0
	public Metamodel getMetamodel_PacMan(){
		return (Metamodel) spec.getEntities().get(0);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.1
	public Model getModel_SimplePacManGame(){
		return (Model) spec.getEntities().get(1);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.2
	public IPattern getPattern_OnePacMan(){
		var p = (Pattern) spec.getEntities().get(2);
		return new NeoPattern(p, builder);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.3
	public IPattern getPattern_FigureOnField(){
		var p = (Pattern) spec.getEntities().get(3);
		return new NeoPattern(p, builder);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.4
	public IPattern getPattern_OneGhost(){
		var p = (Pattern) spec.getEntities().get(4);
		return new NeoPattern(p, builder);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.5
	public IPattern getPattern_PacManOnField(){
		var p = (Pattern) spec.getEntities().get(5);
		return new NeoPattern(p, builder);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.6
	public IPattern getPattern_GhostOnField(){
		var p = (Pattern) spec.getEntities().get(6);
		return new NeoPattern(p, builder);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.7
	public IPattern getPattern_PacManAndGhostOnFields(){
		var p = (Pattern) spec.getEntities().get(7);
		return new NeoPattern(p, builder);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.8
	public IPattern getPattern_PacManAndGhostOnNeighbouringFields(){
		var p = (Pattern) spec.getEntities().get(8);
		return new NeoPattern(p, builder);
	}
	
	//:~> platform:/resource/PacMan/emsl/PacMan.msl#//@entities.9
	public IPattern getPattern_TwoPacManOnBoard(){
		var p = (Pattern) spec.getEntities().get(9);
		return new NeoPattern(p, builder);
	}
}