package org.emoflon.neo.victory.adapter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.ui.debug.api.DataPackage;
import org.emoflon.ibex.tgg.ui.debug.api.Match;
import org.emoflon.ibex.tgg.ui.debug.api.RuleApplication;
import org.emoflon.neo.engine.generator.MatchContainer;
import org.emoflon.neo.neo4j.adapter.models.NeoCoreBuilder;
import org.emoflon.neo.neo4j.adapter.patterns.NeoMatch;
import org.emoflon.neo.neo4j.adapter.rules.NeoCoMatch;

public class NeoDataPackageAdapter implements DataPackage {
	private MatchContainer<NeoMatch, NeoCoMatch> matches;
	private Collection<NeoRuleAdapter> rules;
	private NeoCoreBuilder builder;
	
	public NeoDataPackageAdapter(NeoCoreBuilder builder, MatchContainer<NeoMatch, NeoCoMatch> matches, Collection<NeoRuleAdapter> rules) {
		this.matches = matches;
		this.rules = rules;
		this.builder = builder;
	}

	@Override
	public Collection<Match> getMatches() {
		return matches.stream().map(m -> new NeoMatchAdapter(builder, m, rules)).collect(Collectors.toList());
	}

	@Override
	public List<RuleApplication> getRuleApplications() {
		// TODO
		return Collections.emptyList();
	}

}
