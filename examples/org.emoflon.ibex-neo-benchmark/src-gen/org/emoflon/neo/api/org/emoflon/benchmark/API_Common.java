/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.org.emoflon.benchmark;			
import org.emoflon.neo.cypher.models.*;

public class API_Common {
	// Default values (might have to be changed)
	public static final String PLATFORM_PLUGIN_URI = "/C:/Users/admin/eclipse-workspace-eneo/git/emoflon-neo/";
	public static final String NEOCORE_URI_INSTALLED = "/C:/Users/admin/eclipse-workspace-eneo/git/emoflon-neo/org.emoflon.neo.neocore/";
	public static final String PLATFORM_RESOURCE_URI = "../";

	public static NeoCoreBuilder createBuilder() {
		return new NeoCoreBuilder("bolt://localhost:7687", "neo4j", "test");
	}
}
