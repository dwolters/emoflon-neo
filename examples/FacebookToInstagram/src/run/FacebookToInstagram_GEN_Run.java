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
import org.emoflon.neo.engine.modules.ruleschedulers.TwoPhaseRuleSchedulerForGEN;
import org.emoflon.neo.engine.modules.terminationcondition.CompositeTerminationConditionForGEN;
import org.emoflon.neo.engine.modules.terminationcondition.MaximalRuleApplicationsTerminationCondition;
import org.emoflon.neo.engine.modules.updatepolicies.TwoPhaseUpdatePolicyForGEN;
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
			var generator = createGenerator(api, genAPI, builder);

			logger.info("Start model generation...");
			generator.generate();
			logger.info("Generation done.");
		}
	}

	protected NeoGenerator createGenerator(API_Transformations api, API_FacebookToInstagramGrammar_GEN genAPI, NeoCoreBuilder builder) {
		var allRules = genAPI.getAllRulesForFacebookToInstagramGrammar__GEN();

		var maxRuleApps = new MaximalRuleApplicationsTerminationCondition(allRules, 0);
		maxRuleApps.setMaxNoOfApplicationsFor(API_Transformations.FacebookToInstagramGrammar_NetworkToNetworkIslandRule, 10);
		maxRuleApps.setMaxNoOfApplicationsFor(API_Transformations.FacebookToInstagramGrammar_UserToUserIslandRule, 100000);
		maxRuleApps.setMaxNoOfApplicationsFor(API_Transformations.FacebookToInstagramGrammar_UserNetworkBridgeRule, -1);
		maxRuleApps.setMaxNoOfApplicationsFor(API_Transformations.FacebookToInstagramGrammar_HandleIntraNetworkFollowers, 500000);
		maxRuleApps.setMaxNoOfApplicationsFor(API_Transformations.FacebookToInstagramGrammar_IgnoreIntraNetworkFollowers, 500000);
		
		return new NeoGenerator(//
				allRules, //
				new CompositeTerminationConditionForGEN(builder, 5000000, maxRuleApps), //
				new TwoPhaseRuleSchedulerForGEN(50, api.getSrcMetamodelsForFacebookToInstagramGrammar()), //
				new TwoPhaseUpdatePolicyForGEN(maxRuleApps), //
				new ParanoidNeoReprocessor(), //
				new HeartBeatAndReportMonitor(), //
				List.of(new LoremIpsumStringValueGenerator()));
	}
}
