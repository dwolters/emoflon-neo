/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.classinhhier2db.src.run;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.classinhhier2db.API_Common;
import org.emoflon.neo.api.classinhhier2db.src.API_JavaToDoc;
import org.emoflon.neo.api.classinhhier2db.src.tgg.API_JavaToDoc_FWD;
import org.emoflon.neo.cypher.models.NeoCoreBuilder;
import org.emoflon.neo.engine.modules.NeoGenerator;

import static org.emoflon.neo.api.classinhhier2db.src.run.JavaToDoc_GEN_Run.SRC_MODEL_NAME;
import static org.emoflon.neo.api.classinhhier2db.src.run.JavaToDoc_GEN_Run.TRG_MODEL_NAME;

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
public class JavaToDoc_FWD_Run {
	protected static final Logger logger = Logger.getLogger(JavaToDoc_FWD_Run.class);
	protected String srcModelName;
	protected String trgModelName;
	
	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.INFO);
		var app = new JavaToDoc_FWD_Run(SRC_MODEL_NAME, TRG_MODEL_NAME);
		app.run();
	}
	
	public JavaToDoc_FWD_Run(String srcModelName, String trgModelName) {
		this.srcModelName = srcModelName;
		this.trgModelName = trgModelName;
	}
	
	
	public void run() throws Exception {
		try (var builder = API_Common.createBuilder()) {
			new API_JavaToDoc(builder).exportMetamodelsForJavaToDoc();
	
			var generator = createGenerator(builder);
	
			logger.info("Running generator...");
			generator.generate();
			logger.info("Generator terminated.");
		}
	}
	
	public NeoGenerator createGenerator(NeoCoreBuilder builder) {
		return new NeoGenerator(//
				new API_JavaToDoc_FWD(builder).getAllRulesForJavaToDoc_FWD(), //
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
