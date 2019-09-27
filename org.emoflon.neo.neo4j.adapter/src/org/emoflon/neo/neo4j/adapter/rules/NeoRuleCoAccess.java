package org.emoflon.neo.neo4j.adapter.rules;

import org.emoflon.neo.neo4j.adapter.patterns.NeoData;
import org.emoflon.neo.neo4j.adapter.patterns.NeoMask;

public abstract class NeoRuleCoAccess<Data extends NeoData, CoData extends NeoData, Mask extends NeoMask> extends NeoRuleAccess<Data, Mask> {
	
	public abstract CoData codata(NeoCoMatch m);
}
