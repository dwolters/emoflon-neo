package org.emoflon.neo.generator.modules;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.neo.emsl.eMSL.GraphGrammar;
import org.emoflon.neo.engine.api.rules.IRule;
import org.emoflon.neo.neo4j.adapter.patterns.NeoMatch;
import org.emoflon.neo.neo4j.adapter.rules.NeoCoMatch;

/**
 * Asks for 10 matches of each rule.
 */
public class SimpleNeoRuleScheduler extends AbstractNeoRuleScheduler {

	public SimpleNeoRuleScheduler(GraphGrammar pGrammar) {
		super(pGrammar);
	}

	@Override
	public Map<IRule<NeoMatch, NeoCoMatch>, Integer> scheduleWith(
			Map<NeoMatch, IRule<NeoMatch, NeoCoMatch>> pAvailableMatches) {
		Map<IRule<NeoMatch, NeoCoMatch>, Integer> scheduleMap = new HashMap<>();
		rules.values().forEach(rule -> scheduleMap.put(rule, 10));
		return scheduleMap;
	}
}
