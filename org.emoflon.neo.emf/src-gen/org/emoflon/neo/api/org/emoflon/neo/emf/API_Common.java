/** 
 * EMSL-API generated by eMoflon::Neo - Do not edit as this file will be overwritten
 */
package org.emoflon.neo.api.org.emoflon.neo.emf;			
import org.emoflon.neo.cypher.models.*;

public class API_Common {
	// Default values (might have to be changed)
	public static final String PLATFORM_PLUGIN_URI = "/C:/Program Files/Eclipse/eclipse-for-java-and-dsl-developers/plugins/";
	public static final String NEOCORE_URI_INSTALLED = "/C:/Program Files/Eclipse/eclipse-for-java-and-dsl-developers/plugins/org.emoflon.neo.neocore_1.0.0.202103291557/";
	public static final String PLATFORM_RESOURCE_URI = "../";

	public static NeoCoreBuilder createBuilder() {
		return new NeoCoreBuilder("bolt://localhost:7687", "neo4j", "test");
	}
}
