package org.emoflon.neo.neo4j.adapter.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.emoflon.neo.emsl.eMSL.ModelNodeBlock;
import org.emoflon.neo.emsl.eMSL.PositiveConstraint;
import org.emoflon.neo.emsl.util.EMSLUtil;
import org.emoflon.neo.neo4j.adapter.constraints.NeoPositiveConstraint;
import org.emoflon.neo.neo4j.adapter.models.IBuilder;
import org.emoflon.neo.neo4j.adapter.templates.CypherPatternBuilder;
import org.emoflon.neo.neo4j.adapter.util.NeoQueryData;
import org.neo4j.driver.v1.exceptions.DatabaseException;

public class NeoPatternQueryAndMatchPositiveConstraint extends NeoPattern {
	private static final Logger logger = Logger.getLogger(NeoPatternQueryAndMatchPositiveConstraint.class);
	protected NeoPositiveConstraint pcond;

	@Override
	public String getQuery() {
		return getQuery(pcond.getQueryString_MatchCondition(), pcond.getQueryString_WhereCondition());
	}

	public NeoPatternQueryAndMatchPositiveConstraint(List<ModelNodeBlock> nodeBlocks, String name,
			PositiveConstraint pconstr, IBuilder builder, NeoMask mask, NeoQueryData queryData) {
		super(nodeBlocks, name, builder, mask, queryData);
		pcond = new NeoPositiveConstraint(pconstr.getPattern(), injective, builder, queryData, mask);
	}

	@Override
	public Collection<NeoMatch> determineMatches(int limit) {
		// Condition is positive Constraint (ENFORCE xyz)
		logger.debug("Searching matches for Pattern: " + getName() + " ENFORCE " + pcond.getName());

		// Create Query
		var cypherQuery = CypherPatternBuilder.constraintQuery(nodes, queryData.getAllElements(),
				pcond.getQueryString_MatchCondition(), pcond.getQueryString_WhereCondition(), queryData.getAttributeExpressions(), injective, limit, mask);

		logger.debug(cypherQuery);

		// Execute query
		var result = builder.executeQuery(cypherQuery);

		if(result == null) {
			throw new DatabaseException("400", "Execution Error: See console log for more details.");
		} else {
			// Analyze and return results
			var matches = new ArrayList<NeoMatch>();
			while (result.hasNext()) {
				var record = result.next();
				matches.add(new NeoMatch(this, record));
			}
	
			return matches;
		}
	}
	
	@Override
	public Map<String,Boolean> isStillValid(Collection<NeoMatch> matches) {
		// Condition is positive Constraint (ENFORCE xyz)
		logger.debug("Check if match for " + getName() + " WHEN " + pcond.getName() + " is still valid");
		
		var list = new ArrayList<Map<String,Object>>();
		matches.forEach(match -> list.add(match.getParameters()));
		
		var map = new HashMap<String,Object>();
		map.put("matches",list);
		
		// Create Query
		var helperNodes = new ArrayList<String>(queryData.getAllElements());
		helperNodes.add(EMSLUtil.PARAM_NAME_FOR_MATCH);
		
		var cypherQuery = CypherPatternBuilder.constraintQuery_isStillValid(nodes, helperNodes,
				pcond.getQueryString_MatchCondition(), pcond.getQueryString_WhereCondition(), queryData.getAttributeExpressions(), injective);

		logger.debug(map.toString() + "\n" + cypherQuery);
		var result = builder.executeQueryWithParameters(cypherQuery, map);

		var results = result.list();
		var hashCode = new ArrayList<String>();
		for(var r : results) {
			hashCode.add(r.asMap().get("match_id").toString());
		}
		
		var returnMap = new HashMap<String,Boolean>();
		for(var match : matches) {
			returnMap.put(match.getHashCode(),hashCode.contains(match.getHashCode()));
		}
		
		logger.debug(returnMap.toString());
		return returnMap;
	}
}
