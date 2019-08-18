package org.emoflon.neo.neo4j.adapter.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.emoflon.neo.emsl.eMSL.AtomicPattern;
import org.emoflon.neo.neo4j.adapter.common.NeoNode;
import org.emoflon.neo.neo4j.adapter.models.IBuilder;
import org.emoflon.neo.neo4j.adapter.patterns.NeoMask;
import org.emoflon.neo.neo4j.adapter.templates.CypherPatternBuilder;
import org.emoflon.neo.neo4j.adapter.util.NeoHelper;

/**
 * Class representing an FORBID constraint, storing all relevant data, creates
 * and runs the query for checking the constraint
 * 
 * @author Jannik Hinz
 *
 */
public class NeoNegativeConstraint {

	private NeoHelper helper;

	private AtomicPattern ap;
	private String name;
	private List<NeoNode> nodes;

	private boolean injective;
	private int uuid;

	private NeoMask mask;

	/**
	 * 
	 * @param ap        AtomicPattern of the FORBID constraint
	 * @param injective boolean if the pattern should be matches injective or not
	 * @param builder   for creating and running Cypher queries
	 * @param helper    for creating nodes and
	 */
	public NeoNegativeConstraint(AtomicPattern ap, boolean injective, IBuilder builder, NeoHelper helper,
			NeoMask mask) {
		this.uuid = helper.addConstraint();
		this.helper = helper;
		this.name = ap.getName();
		this.injective = injective;
		this.mask = mask;

		this.ap = NeoHelper.getFlattenedPattern(ap);

		// Extracts all necessary information data from the Atomic Pattern
		this.nodes = new ArrayList<>(this.helper.extractNodesAndRelations(ap.getNodeBlocks()));
	}

	/**
	 * Return the name of the constraint
	 * 
	 * @return name of the constraint
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the AtomicPattern of the constraint
	 * 
	 * @return AtomicPattern of the constraint
	 */
	public AtomicPattern getPattern() {
		return ap;
	}

	/**
	 * Returns a collection of the Nodes from the constraint
	 * 
	 * @return NeoNode collection of the Nodes from the constraint
	 */
	public Collection<NeoNode> getNodes() {
		return nodes;
	}

	/**
	 * Return the corresponding OPTIONAL MATCH query with injectivity block and WITH
	 * clause (for use in constraints)
	 * 
	 * @return OPTIONAL MATCH xyz WHERE injective WITH nodes (query part for this
	 *         constraint)
	 */
	public String getQueryString_MatchConstraint() {
		return CypherPatternBuilder.constraint_matchQuery(nodes, injective, uuid, mask);
	}

	/**
	 * Return the corresponding OPTIONAL MATCH query with injectivity block and WITH
	 * clause (for use in conditions)
	 * 
	 * @return OPTIONAL MATCH xyz WHERE injective WITH nodes (query part for this
	 *         constraint)
	 */
	public String getQueryString_MatchCondition() {
		return CypherPatternBuilder.condition_matchQuery(nodes, injective, mask);
	}

	/**
	 * Return the corresponding WHERE query (for use in constraints)
	 * 
	 * @return WHERE count(xyz) = 0 (query part for this constraint)
	 */
	public String getQueryString_WhereConstraint() {
		return CypherPatternBuilder.whereNegativeConstraintQuery(uuid);
	}

	/**
	 * Return the corresponding WHERE query (for use in conditions)
	 * 
	 * @return WHERE xy IS NULL OR yz IS NULL (query part for this constraint)
	 */
	public String getQueryString_WhereConditon() {
		return CypherPatternBuilder.whereNegativeConditionQuery(nodes);
	}

}