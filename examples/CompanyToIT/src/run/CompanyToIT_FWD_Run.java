package run;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.api.API_CompanyToIT;
import org.emoflon.neo.api.CompanyToIT.API_CompanyToIT_FWD;
import org.emoflon.neo.cypher.rules.NeoRule;
import org.emoflon.neo.emsl.util.FlattenerException;
import org.emoflon.neo.engine.modules.NeoGenerator;
import org.emoflon.neo.engine.modules.matchreprocessors.ParanoidNeoReprocessor;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.ruleschedulers.AllRulesAllMatchesScheduler;
import org.emoflon.neo.engine.modules.terminationcondition.TimedTerminationCondition;
import org.emoflon.neo.engine.modules.updatepolicies.AnySingleMatchUpdatePolicy;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;

public class CompanyToIT_FWD_Run {
	private static final Logger logger = Logger.getLogger(CompanyToIT_FWD_Run.class);

	private static final String srcModelName = "TheSource";

	public static void main(String[] pArgs) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new CompanyToIT_FWD_Run();
		app.runGenerator();
	}

	public void runGenerator() throws FlattenerException, Exception {
		try (var builder = API_Common.createBuilder()) {
			var api = new API_CompanyToIT(builder);
			api.exportMetamodelsForCompanyToIT();

			builder.prepareModelWithTranslateAttribute(srcModelName);

			var genAPI = new API_CompanyToIT_FWD(builder);
			var generator = createGenerator(genAPI);

			logger.info("Start model generation...");
			generator.generate();
			logger.info("Generation done.");
		}
	}

	protected NeoGenerator createGenerator(API_CompanyToIT_FWD genAPI) {
		Collection<NeoRule> allRules = genAPI.getAllRulesForCompanyToIT__FWD();

		return new NeoGenerator(//
				allRules, //
				new TimedTerminationCondition(1, TimeUnit.MINUTES), //
				new AllRulesAllMatchesScheduler(), //
				new AnySingleMatchUpdatePolicy(), //
				new ParanoidNeoReprocessor(), //
				new HeartBeatAndReportMonitor(), //
				new ModelNameValueGenerator(srcModelName, "TheTarget"), //
				List.of(new LoremIpsumStringValueGenerator()));
	}
}
