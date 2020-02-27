/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.api.API_Transformations;
import org.emoflon.neo.api.Transformations.API_FacebookToInstagramFASE_FWD;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static Transformations.run.FacebookToInstagramGrammar_GEN_Run.SRC_MODEL_NAME;
import static Transformations.run.FacebookToInstagramGrammar_GEN_Run.TRG_MODEL_NAME;

import org.emoflon.neo.engine.modules.cleanup.RemoveTranslateAttributes;
import org.emoflon.neo.engine.modules.matchreprocessors.ParanoidNeoReprocessor;
import org.emoflon.neo.engine.modules.monitors.HeartBeatAndReportMonitor;
import org.emoflon.neo.engine.modules.ruleschedulers.AllRulesAllMatchesScheduler;
import org.emoflon.neo.engine.modules.startup.PrepareTranslateAttributes;
import org.emoflon.neo.engine.modules.terminationcondition.NoMoreMatchesTerminationCondition;
import org.emoflon.neo.engine.modules.updatepolicies.AnySingleMatchUpdatePolicy;
import org.emoflon.neo.engine.modules.valueGenerators.LoremIpsumStringValueGenerator;
import org.emoflon.neo.engine.modules.valueGenerators.ModelNameValueGenerator;
import java.util.List;

@SuppressWarnings("unused")
public class FacebookToInstagramFASE_FWD_Run {
	private static final Logger logger = Logger.getLogger(FacebookToInstagramFASE_FWD_Run.class);
	private String srcModelName;
	private String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new FacebookToInstagramFASE_FWD_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public FacebookToInstagramFASE_FWD_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
	}

	public void run() throws Exception {
		try (var builder = API_Common.createBuilder()) {
			new API_Transformations(builder).exportMetamodelsForFacebookToInstagramFASE();
	
			var generator = createGenerator(builder);
	
			logger.info("Running generator...");
			generator.generate();
			logger.info("Generator terminated.");
		}
	}
	
	public NeoGenerator createGenerator(NeoCoreBuilder builder) {
		return new NeoGenerator(//
				new API_FacebookToInstagramFASE_FWD(builder).getAllRulesForFacebookToInstagramFASE_FWD(), //
				new PrepareTranslateAttributes(builder, srcModelName), //
				new NoMoreMatchesTerminationCondition(), //
				new AllRulesAllMatchesScheduler(), //
				new AnySingleMatchUpdatePolicy(), //
				new ParanoidNeoReprocessor(), //
				new RemoveTranslateAttributes(builder, srcModelName), //
				new HeartBeatAndReportMonitor(), //
				new ModelNameValueGenerator(srcModelName, trgModelName), //
				List.of(new LoremIpsumStringValueGenerator()));
	}
}
