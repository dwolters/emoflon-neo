/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.src.rules.run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.classinhhier2db.API_Common;
import org.emoflon.neo.api.classinhhier2db.src.rules.API_SokobanTGGs;
import org.emoflon.neo.api.classinhhier2db.src.rules.tgg.API_SokobanImportExport_BWD_OPT;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static org.emoflon.neo.api.classinhhier2db.src.rules.run.SokobanImportExport_GEN_Run.SRC_MODEL_NAME;
import static org.emoflon.neo.api.classinhhier2db.src.rules.run.SokobanImportExport_GEN_Run.TRG_MODEL_NAME;
		
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.api.classinhhier2db.src.rules.tgg.API_SokobanImportExport_GEN;
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
public class SokobanImportExport_BWD_OPT_Run {
	protected static SupportedILPSolver solver = SupportedILPSolver.Gurobi;
	protected BackwardTransformationOperationalStrategy backwardTransformation;
	protected static final Logger logger = Logger.getLogger(SokobanImportExport_BWD_OPT_Run.class);
	protected String srcModelName;
	protected String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new SokobanImportExport_BWD_OPT_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public SokobanImportExport_BWD_OPT_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
	}
	
	public SokobanImportExport_BWD_OPT_Run(String srcModelName, String trgModelName, SupportedILPSolver solver) {
		this(srcModelName, trgModelName);
		SokobanImportExport_BWD_OPT_Run.solver = solver;
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
		var api = new API_SokobanTGGs(builder);
		var genAPI = new API_SokobanImportExport_GEN(builder);
		var bwd_optAPI = new API_SokobanImportExport_BWD_OPT(builder);
		var genRules = genAPI.getAllRulesForSokobanImportExport_GEN();
		var analyser = new TripleRuleAnalyser(new API_SokobanTGGs(builder).getTripleRulesOfSokobanImportExport());
		backwardTransformation = new BackwardTransformationOperationalStrategy(//
				solver, //
				builder, //
				genRules, //
				bwd_optAPI.getAllRulesForSokobanImportExport_BWD_OPT(), //
				api.getConstraintsOfSokobanImportExport(), //
				srcModelName, //
				trgModelName//
		);
		
		return new NeoGenerator(//
				bwd_optAPI.getAllRulesForSokobanImportExport_BWD_OPT(), //
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
