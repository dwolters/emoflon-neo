package run;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.api.API_Transformations;
import org.emoflon.neo.api.Transformations.API_FacebookToInstagramGrammar_GEN;
import org.emoflon.neo.emsl.util.FlattenerException;
import org.emoflon.neo.engine.modules.NeoGenerator;
import org.emoflon.neo.engine.modules.matchreprocessors.ParanoidNeoReprocessor;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.ruleschedulers.AllRulesAllMatchesScheduler;
import org.emoflon.neo.engine.modules.terminationcondition.MaxGeneratedElementsTerminationCondition;
import org.emoflon.neo.engine.modules.updatepolicies.RandomSingleMatchUpdatePolicy;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.neo4j.adapter.models.NeoCoreBuilder;

public class FacebookToInstagram_GEN_Run {
	private static final Logger logger = Logger.getLogger(FacebookToInstagram_GEN_Run.class);

	public static void main(String[] pArgs) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new FacebookToInstagram_GEN_Run();
		app.runGenerator();
	}

	public void runGenerator() throws FlattenerException, Exception {
		try (var builder = API_Common.createBuilder()) {
			var api = new API_Transformations(builder);
			api.exportMetamodelsForFacebookToInstagramGrammar();

			var genAPI = new API_FacebookToInstagramGrammar_GEN(builder);
			var generator = createGenerator(genAPI, builder);

			logger.info("Start model generation...");
			generator.generate();
			logger.info("Generation done.");
		}
	}

	protected NeoGenerator createGenerator(API_FacebookToInstagramGrammar_GEN genAPI, NeoCoreBuilder builder) {
		var allRules = genAPI.getAllRulesForFacebookToInstagramGrammar__GEN();

		return new NeoGenerator(//
				allRules, //
				new MaxGeneratedElementsTerminationCondition(10, builder), //
				new AllRulesAllMatchesScheduler(), //
				new RandomSingleMatchUpdatePolicy(), //
				new ParanoidNeoReprocessor(), //
				new HeartBeatAndReportMonitor(), //
				List.of(new LoremIpsumStringValueGenerator()));
	}
}
