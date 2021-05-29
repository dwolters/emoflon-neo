/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.bin.run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.classinhhier2db.API_Common;
import org.emoflon.neo.api.classinhhier2db.bin.API_Transformations;
import org.emoflon.neo.api.classinhhier2db.bin.tgg.API_FacebookToInstagram_Constrained_CC;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static org.emoflon.neo.api.classinhhier2db.bin.run.FacebookToInstagram_Constrained_GEN_Run.SRC_MODEL_NAME;
import static org.emoflon.neo.api.classinhhier2db.bin.run.FacebookToInstagram_Constrained_GEN_Run.TRG_MODEL_NAME;
		
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.api.classinhhier2db.bin.tgg.API_FacebookToInstagram_Constrained_GEN;
import org.emoflon.neo.engine.modules.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.startup.NoOpStartup;
import org.emoflon.neo.engine.modules.terminationcondition.NoMoreMatchesTerminationCondition;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;
import org.emoflon.neo.engine.modules.analysis.TripleRuleAnalyser;
import org.emoflon.neo.engine.modules.matchreprocessors.CCReprocessor;
import org.emoflon.neo.engine.modules.ruleschedulers.CCRuleScheduler;
import org.emoflon.neo.engine.modules.updatepolicies.CorrCreationOperationalStrategy;

@SuppressWarnings("unused")
public class FacebookToInstagram_Constrained_CC_Run {
	protected static SupportedILPSolver solver = SupportedILPSolver.Gurobi;
	protected CorrCreationOperationalStrategy corrCreation;
	protected static final Logger logger = Logger.getLogger(FacebookToInstagram_Constrained_CC_Run.class);
	protected String srcModelName;
	protected String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new FacebookToInstagram_Constrained_CC_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public FacebookToInstagram_Constrained_CC_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
	}
	
	public FacebookToInstagram_Constrained_CC_Run(String srcModelName, String trgModelName, SupportedILPSolver solver) {
		this(srcModelName, trgModelName);
		FacebookToInstagram_Constrained_CC_Run.solver = solver;
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
		var genAPI = new API_FacebookToInstagram_Constrained_GEN(builder);
		var ccAPI = new API_FacebookToInstagram_Constrained_CC(builder);
		var genRules = genAPI.getAllRulesForFacebookToInstagram_Constrained_GEN();
		var analyser = new TripleRuleAnalyser(new API_Transformations(builder).getTripleRulesOfFacebookToInstagram_Constrained());
		corrCreation = new CorrCreationOperationalStrategy(//
				solver, //
				builder, //
				genRules, //
				ccAPI.getAllRulesForFacebookToInstagram_Constrained_CC(), //
				api.getConstraintsOfFacebookToInstagram_Constrained(), //
				srcModelName, //
				trgModelName//
		);
		
		return new NeoGenerator(//
				ccAPI.getAllRulesForFacebookToInstagram_Constrained_CC(), //
				new NoOpStartup(), //
				new NoMoreMatchesTerminationCondition(), //
				new CCRuleScheduler(analyser), //
				corrCreation, //
				new CCReprocessor(analyser), //
				corrCreation, //
				new HeartBeatAndReportMonitor(), //
				new ModelNameValueGenerator(srcModelName, trgModelName), //
				List.of(new LoremIpsumStringValueGenerator()));
	}
	public CorrCreationOperationalStrategy runCorrCreation() throws Exception {
		run();
		return corrCreation;
	}
}
