/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.src.run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.classinhhier2db.API_Common;
import org.emoflon.neo.api.classinhhier2db.src.API_Transformations;
import org.emoflon.neo.api.classinhhier2db.src.tgg.API_FacebookToInstagramGrammar_BWD_OPT;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static org.emoflon.neo.api.classinhhier2db.src.run.FacebookToInstagramGrammar_GEN_Run.SRC_MODEL_NAME;
import static org.emoflon.neo.api.classinhhier2db.src.run.FacebookToInstagramGrammar_GEN_Run.TRG_MODEL_NAME;
		
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.api.classinhhier2db.src.tgg.API_FacebookToInstagramGrammar_GEN;
import org.emoflon.neo.engine.modules.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.startup.NoOpStartup;
import org.emoflon.neo.engine.modules.terminationcondition.NoMoreMatchesTerminationCondition;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;
import org.emoflon.neo.engine.modules.analysis.TripleRuleAnalyser;
import org.emoflon.neo.engine.modules.updatepolicies.BackwardTransformationOperationalStrategy;
import org.emoflon.neo.engine.modules.ruleschedulers.BWD_OPTRuleScheduler;
import org.emoflon.neo.engine.modules.matchreprocessors.BWD_OPTReprocessor;

@SuppressWarnings("unused")
public class FacebookToInstagramGrammar_BWD_OPT_Run {
	protected static SupportedILPSolver solver = SupportedILPSolver.Gurobi;
	protected BackwardTransformationOperationalStrategy backwardTransformation;
	protected static final Logger logger = Logger.getLogger(FacebookToInstagramGrammar_BWD_OPT_Run.class);
	protected String srcModelName;
	protected String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new FacebookToInstagramGrammar_BWD_OPT_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public FacebookToInstagramGrammar_BWD_OPT_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
	}
	
	public FacebookToInstagramGrammar_BWD_OPT_Run(String srcModelName, String trgModelName, SupportedILPSolver solver) {
		this(srcModelName, trgModelName);
		FacebookToInstagramGrammar_BWD_OPT_Run.solver = solver;
	}
	
	public void run() throws Exception {
		try (var builder = API_Common.createBuilder()) {
	
			var generator = createGenerator(builder);
	
			logger.info("Running generator...");
			generator.generate();
			logger.info("Generator terminated.");
		}
	}
	
	public NeoGenerator createGenerator(NeoCoreBuilder builder) {
		var api = new API_Transformations(builder);
		var genAPI = new API_FacebookToInstagramGrammar_GEN(builder);
		var bwd_optAPI = new API_FacebookToInstagramGrammar_BWD_OPT(builder);
		var genRules = genAPI.getAllRulesForFacebookToInstagramGrammar_GEN();
		var analyser = new TripleRuleAnalyser(new API_Transformations(builder).getTripleRulesOfFacebookToInstagramGrammar());
		backwardTransformation = new BackwardTransformationOperationalStrategy(//
				solver, //
				builder, //
				genRules, //
				bwd_optAPI.getAllRulesForFacebookToInstagramGrammar_BWD_OPT(), //
				api.getConstraintsOfFacebookToInstagramGrammar(), //
				srcModelName, //
				trgModelName//
		);
		
		return new NeoGenerator(//
				bwd_optAPI.getAllRulesForFacebookToInstagramGrammar_BWD_OPT(), //
				new NoOpStartup(), //
				new NoMoreMatchesTerminationCondition(), //
				new BWD_OPTRuleScheduler(analyser), //
				backwardTransformation, //
				new BWD_OPTReprocessor(analyser), //
				backwardTransformation, //
				new HeartBeatAndReportMonitor(), //
				new ModelNameValueGenerator(srcModelName, trgModelName), //
				List.of(new LoremIpsumStringValueGenerator()));
	}
	public BackwardTransformationOperationalStrategy runBackwardTransformation() throws Exception {
		run();
		return backwardTransformation;
	}
}
