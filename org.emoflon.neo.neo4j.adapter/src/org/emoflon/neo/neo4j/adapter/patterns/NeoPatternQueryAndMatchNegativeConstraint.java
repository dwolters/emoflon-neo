package org.emoflon.neo.neo4j.adapter.patterns;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.neo.emsl.eMSL.NegativeConstraint;
import org.emoflon.neo.emsl.eMSL.Pattern;
import org.emoflon.neo.neo4j.adapter.CypherPatternBuilder;
import org.emoflon.neo.neo4j.adapter.IBuilder;
import org.emoflon.neo.neo4j.adapter.NeoMask;
import org.emoflon.neo.neo4j.adapter.NeoMatch;
import org.emoflon.neo.neo4j.adapter.NeoNegativeConstraint;

public class NeoPatternQueryAndMatchNegativeConstraint extends NeoPattern {
	protected NeoNegativeConstraint ncond;

	public NeoPatternQueryAndMatchNegativeConstraint(Pattern p, IBuilder builder, NeoMask mask) {
		super(p, builder, mask);
		var cons = (NegativeConstraint) p.getCondition();
		ncond = new NeoNegativeConstraint(cons.getPattern(), injective, builder, helper, mask);
	}

	@Override
	public String getQuery() {
		return getQuery(ncond.getQueryString_MatchCondition(), ncond.getQueryString_WhereConditon());
	}

	@Override
	public Collection<NeoMatch> determineMatches(int limit) {
		// Condition is negative Constraint (FORBID xyz)
		logger.info("Searching matches for Pattern: " + p.getBody().getName() + " FORBID " + ncond.getName());

		// create query
		var cypherQuery = CypherPatternBuilder.constraintQuery(nodes, helper.getNodes(),
				ncond.getQueryString_MatchCondition(), ncond.getQueryString_WhereConditon(), injective, limit, mask);
		logger.debug(cypherQuery);

		// execute query
		var result = builder.executeQuery(cypherQuery);

		// analyze and return results
		var matches = new ArrayList<NeoMatch>();
		while (result.hasNext()) {
			var record = result.next();
			matches.add(new NeoMatch(this, record));
		}

		return matches;
	}

	@Override
	public boolean isStillValid(NeoMatch m) {
		// Condition is positive Constraint (ENFORCE xyz)
		logger.info("Check if match for " + p.getBody().getName() + " WHEN " + ncond.getName() + " is still valid");

		// Create Query
		var cypherQuery = CypherPatternBuilder.constraintQuery_isStillValid(nodes, helper.getNodes(),
				ncond.getQueryString_MatchCondition(), ncond.getQueryString_WhereConditon(), injective, m);

		logger.debug(cypherQuery);

		// Execute query
		var result = builder.executeQuery(cypherQuery);
		return result.hasNext();
	}

}
