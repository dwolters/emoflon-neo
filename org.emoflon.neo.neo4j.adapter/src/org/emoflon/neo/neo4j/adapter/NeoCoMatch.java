package org.emoflon.neo.neo4j.adapter;

import org.emoflon.neo.engine.api.rules.ICoMatch;
import org.neo4j.driver.v1.Record;

public class NeoCoMatch extends NeoMatch implements ICoMatch {

	public NeoCoMatch(NeoPattern pattern, Record record) {
		super(pattern, record);
	}

}
