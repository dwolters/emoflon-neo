/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.bin.run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.classinhhier2db.API_Common;
import org.emoflon.neo.api.classinhhier2db.bin.API_Transformations;
import org.emoflon.neo.api.classinhhier2db.bin.tgg.API_FacebookToInstagramFASE_CO;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static org.emoflon.neo.api.classinhhier2db.bin.run.FacebookToInstagramFASE_GEN_Run.SRC_MODEL_NAME;
import static org.emoflon.neo.api.classinhhier2db.bin.run.FacebookToInstagramFASE_GEN_Run.TRG_MODEL_NAME;
		
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.api.classinhhier2db.bin.tgg.API_FacebookToInstagramFASE_GEN;
import org.emoflon.neo.engine.modules.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.startup.NoOpStartup;
import org.emoflon.neo.engine.modules.terminationcondition.NoMoreMatchesTerminationCondition;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;
import org.emoflon.neo.engine.modules.analysis.TripleRuleAnalyser;
import org.emoflon.neo.engine.modules.updatepolicies.CheckOnlyOperationalStrategy;
import org.emoflon.neo.engine.modules.terminationcondition.OneShotTerminationCondition;
import org.emoflon.neo.engine.modules.ruleschedulers.AllRulesAllMatchesScheduler;
import org.emoflon.neo.engine.modules.matchreprocessors.COReprocessor;

@SuppressWarnings("unused")
public class FacebookToInstagramFASE_CO_Run {
	protected static SupportedILPSolver solver = SupportedILPSolver.Gurobi;
	protected CheckOnlyOperationalStrategy checkOnly;
	protected static final Logger logger = Logger.getLogger(FacebookToInstagramFASE_CO_Run.class);
	protected String srcModelName;
	protected String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new FacebookToInstagramFASE_CO_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public FacebookToInstagramFASE_CO_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
	}
	
	public FacebookToInstagramFASE_CO_Run(String srcModelName, String trgModelName, SupportedILPSolver solver) {
		this(srcModelName, trgModelName);
		FacebookToInstagramFASE_CO_Run.solver = solver;
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
		var genAPI = new API_FacebookToInstagramFASE_GEN(builder);
		var coAPI = new API_FacebookToInstagramFASE_CO(builder);
		var analyser = new TripleRuleAnalyser(new API_Transformations(builder).getTripleRulesOfFacebookToInstagramFASE());
		checkOnly = new CheckOnlyOperationalStrategy(//
				solver, //
				genAPI.getAllRulesForFacebookToInstagramFASE_GEN(), //
				coAPI.getAllRulesForFacebookToInstagramFASE_CO(), //
				api.getConstraintsOfFacebookToInstagramFASE(), //
				builder, //
				srcModelName, //
				trgModelName//
		);
		
		return new NeoGenerator(//
				coAPI.getAllRulesForFacebookToInstagramFASE_CO(), //
				new NoOpStartup(), //
				new OneShotTerminationCondition(), //
				new AllRulesAllMatchesScheduler(), //
				checkOnly, //
				new COReprocessor(analyser), //
				checkOnly, //
				new HeartBeatAndReportMonitor(), //
				new ModelNameValueGenerator(srcModelName, trgModelName), //
				List.of(new LoremIpsumStringValueGenerator()));
	}
	
	public CheckOnlyOperationalStrategy runCheckOnly() throws Exception {
		run();
		return checkOnly;
	}
}
