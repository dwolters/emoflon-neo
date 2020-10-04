/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.api.API_Transformations;
import org.emoflon.neo.api.Transformations.API_FacebookToInstagramFASE_BWD_OPT;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static Transformations.run.FacebookToInstagramFASE_GEN_Run.SRC_MODEL_NAME;
import static Transformations.run.FacebookToInstagramFASE_GEN_Run.TRG_MODEL_NAME;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.api.Transformations.API_FacebookToInstagramFASE_GEN;
import org.emoflon.neo.engine.modules.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.neo.engine.modules.matchreprocessors.BWD_OPTReprocessor;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.ruleschedulers.BWD_OPTRuleScheduler;
import org.emoflon.neo.engine.modules.startup.NoOpStartup;
import org.emoflon.neo.engine.modules.terminationcondition.NoMoreMatchesTerminationCondition;
import org.emoflon.neo.engine.modules.updatepolicies.CorrCreationOperationalStrategy;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;
import org.emoflon.neo.engine.modules.analysis.TripleRuleAnalyser;

@SuppressWarnings("unused")
public class FacebookToInstagramFASE_BWD_OPT_Run {
	private static final SupportedILPSolver solver = SupportedILPSolver.Gurobi;
	private CorrCreationOperationalStrategy backwardTransformation;
	private static final Logger logger = Logger.getLogger(FacebookToInstagramFASE_BWD_OPT_Run.class);
	private String srcModelName;
	private String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new FacebookToInstagramFASE_BWD_OPT_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public FacebookToInstagramFASE_BWD_OPT_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
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
		var genAPI = new API_FacebookToInstagramFASE_GEN(builder);
		var bwd_optAPI = new API_FacebookToInstagramFASE_BWD_OPT(builder);
		var genRules = genAPI.getAllRulesForFacebookToInstagramFASE_GEN();
		var analyser = new TripleRuleAnalyser(new API_Transformations(builder).getTripleRulesOfFacebookToInstagramFASE());
		backwardTransformation = new CorrCreationOperationalStrategy(//
				solver, //
				builder, //
				genRules, //
				bwd_optAPI.getAllRulesForFacebookToInstagramFASE_BWD_OPT(), //
				getNegativeConstraints(builder), //
				srcModelName, //
				trgModelName//
		);
		
		return new NeoGenerator(//
				bwd_optAPI.getAllRulesForFacebookToInstagramFASE_BWD_OPT(), //
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
	
	public CorrCreationOperationalStrategy runBackwardTransformation() throws Exception {
		run();
		return backwardTransformation;
	}
	
	protected Collection<IConstraint> getNegativeConstraints(NeoCoreBuilder builder) {
		return Collections.emptyList();
	}
}