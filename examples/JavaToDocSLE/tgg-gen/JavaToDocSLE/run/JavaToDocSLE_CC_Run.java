/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package JavaToDocSLE.run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.api.API_JavaToDocSLE;
import org.emoflon.neo.api.JavaToDocSLE.API_JavaToDocSLE_CC;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static JavaToDocSLE.run.JavaToDocSLE_GEN_Run.SRC_MODEL_NAME;
import static JavaToDocSLE.run.JavaToDocSLE_GEN_Run.TRG_MODEL_NAME;
		
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.emoflon.neo.engine.api.constraints.IConstraint;
import org.emoflon.neo.api.JavaToDocSLE.API_JavaToDocSLE_GEN;
import org.emoflon.neo.engine.modules.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.neo.engine.modules.matchreprocessors.CCReprocessor;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.ruleschedulers.CCRuleScheduler;
import org.emoflon.neo.engine.modules.startup.NoOpStartup;
import org.emoflon.neo.engine.modules.terminationcondition.NoMoreMatchesTerminationCondition;
import org.emoflon.neo.engine.modules.updatepolicies.CorrCreationOperationalStrategy;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;
import org.emoflon.neo.engine.modules.analysis.TripleRuleAnalyser;

@SuppressWarnings("unused")
public class JavaToDocSLE_CC_Run {
	private static SupportedILPSolver solver = SupportedILPSolver.Gurobi;
	private CorrCreationOperationalStrategy corrCreation;
	protected static final Logger logger = Logger.getLogger(JavaToDocSLE_CC_Run.class);
	protected String srcModelName;
	protected String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new JavaToDocSLE_CC_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public JavaToDocSLE_CC_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
	}
	
	public JavaToDocSLE_CC_Run(String srcModelName, String trgModelName, SupportedILPSolver solver) {
		this(srcModelName, trgModelName);
		this.solver = solver;
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
		var genAPI = new API_JavaToDocSLE_GEN(builder);
		var ccAPI = new API_JavaToDocSLE_CC(builder);
		var genRules = genAPI.getAllRulesForJavaToDocSLE_GEN();
		var analyser = new TripleRuleAnalyser(new API_JavaToDocSLE(builder).getTripleRulesOfJavaToDocSLE());
		corrCreation = new CorrCreationOperationalStrategy(//
				solver, //
				builder, //
				genRules, //
				ccAPI.getAllRulesForJavaToDocSLE_CC(), //
				getNegativeConstraints(builder), //
				srcModelName, //
				trgModelName//
		);
		
		return new NeoGenerator(//
				ccAPI.getAllRulesForJavaToDocSLE_CC(), //
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
	
	protected Collection<IConstraint> getNegativeConstraints(NeoCoreBuilder builder) {
		return Collections.emptyList();
	}
}
