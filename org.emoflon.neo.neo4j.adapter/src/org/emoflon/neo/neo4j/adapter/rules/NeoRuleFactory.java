package org.emoflon.neo.neo4j.adapter.rules;

import org.emoflon.neo.emsl.eMSL.Rule;
import org.emoflon.neo.neo4j.adapter.models.IBuilder;
import org.emoflon.neo.neo4j.adapter.patterns.EmptyMask;
import org.emoflon.neo.neo4j.adapter.patterns.NeoMask;
import org.emoflon.neo.neo4j.adapter.util.NeoQueryData;

public class NeoRuleFactory {

	public static NeoRule createNeoRule(Rule r, IBuilder builder) {
		return createNeoRule(r, builder, new EmptyMask());
	}

	public static NeoRule createNeoRule(Rule r, IBuilder builder, NeoMask mask) {
		return new NeoRule(r, builder, mask, new NeoQueryData());
	}
}