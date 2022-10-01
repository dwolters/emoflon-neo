package run;

import static run.ExtType2Doc_ConcSync_GEN_Run.SRC_MODEL_NAME;
import static run.ExtType2Doc_ConcSync_GEN_Run.TRG_MODEL_NAME;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.exttype2doc_concsync.API_Common;
import org.emoflon.neo.api.exttype2doc_concsync.API_ExtType2Doc_ConcSync;
import org.emoflon.neo.api.exttype2doc_concsync.tgg.API_ExtType2Doc_ConcSync_CC;
import org.emoflon.neo.api.exttype2doc_concsync.tgg.API_ExtType2Doc_ConcSync_GEN;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;
import org.emoflon.neo.engine.modules.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.neo.engine.modules.matchreprocessors.CCReprocessor;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.ruleschedulers.CCRuleScheduler;
import org.emoflon.neo.engine.modules.startup.NoOpStartup;
import org.emoflon.neo.engine.modules.terminationcondition.NoMoreMatchesTerminationCondition;
import org.emoflon.neo.engine.modules.updatepolicies.CorrCreationOperationalStrategy;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;
import org.emoflon.neo.engine.modules.analysis.*;

public class ExtType2Doc_ConcSync_CC_Run {
	private static final Logger logger = Logger.getLogger(ExtType2Doc_ConcSync_CC_Run.class);
	private static final SupportedILPSolver solver = SupportedILPSolver.Gurobi;

	private String srcModel = SRC_MODEL_NAME;
	private String trgModel = TRG_MODEL_NAME;
	private CorrCreationOperationalStrategy corrCreation;

	public static void main(String[] pArgs) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new ExtType2Doc_ConcSync_CC_Run();
		app.run();
	}

	public void run() throws Exception {
		try (var builder = API_Common.createBuilder()) {

			var generator = createGenerator(builder);

			logger.info("Start corr creation...");
			generator.generate();
		}
	}

	public NeoGenerator createGenerator(NeoCoreBuilder builder) {
		var api = new API_ExtType2Doc_ConcSync(builder);
		var genAPI = new API_ExtType2Doc_ConcSync_GEN(builder);
		var ccAPI = new API_ExtType2Doc_ConcSync_CC(builder);
		var genRules = genAPI.getAllRulesForExtType2Doc_ConcSync_GEN();
		var tripleRules = new API_ExtType2Doc_ConcSync(builder).getTripleRulesOfExtType2Doc_ConcSync();
		var analyser = new TripleRuleAnalyser(tripleRules);
		
		corrCreation = new CorrCreationOperationalStrategy(//
				solver, //
				builder, //
				genRules, //
				ccAPI.getAllRulesForExtType2Doc_ConcSync_CC(), //
				api.getConstraintsOfExtType2Doc_ConcSync(), //
				srcModel, //
				trgModel//
		);

		return new NeoGenerator(//
				ccAPI.getAllRulesForExtType2Doc_ConcSync_CC(), //
				new NoOpStartup(), //
				new NoMoreMatchesTerminationCondition(), //
				new CCRuleScheduler(analyser), //
				corrCreation, //
				new CCReprocessor(analyser), //
				corrCreation,//
				new HeartBeatAndReportMonitor(), //
				new ModelNameValueGenerator(srcModel, trgModel), //
				List.of(new LoremIpsumStringValueGenerator()));
	}

	public CorrCreationOperationalStrategy runCorrCreation(String srcModel, String trgModel) throws Exception {
		this.srcModel = srcModel;
		this.trgModel = trgModel;
		run();
		return corrCreation;
	}

//	protected Collection<IConstraint> getNegativeConstraints(NeoCoreBuilder builder) {
//		var companyAPI = new API_Company(builder);
//		var itAPI = new API_IT(builder);
//		return List.of(//
//				companyAPI.getConstraint_CEOOfMultipleCompanies(), //
//				companyAPI.getConstraint_MultipleAdmins(), //
//				itAPI.getConstraint_NoDifferentITThanRouter()//
//		);
//	}
}
