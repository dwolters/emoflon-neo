package org.emoflon.neo.neo4j.adapter.patterns;

import java.util.Collection;
import java.util.Optional;

import org.emoflon.neo.emsl.eMSL.Constraint;
import org.emoflon.neo.emsl.eMSL.ConstraintReference;
import org.emoflon.neo.emsl.eMSL.Pattern;
import org.emoflon.neo.neo4j.adapter.IBuilder;
import org.emoflon.neo.neo4j.adapter.NeoCondition;
import org.emoflon.neo.neo4j.adapter.NeoConstraint;
import org.emoflon.neo.neo4j.adapter.NeoMask;
import org.emoflon.neo.neo4j.adapter.NeoMatch;

public class NeoPatternQueryAndMatchConstraintRef extends NeoPattern {

	public NeoPatternQueryAndMatchConstraintRef(Pattern p, IBuilder builder, NeoMask mask) {
		super(p, builder, mask);
		ConstraintReference ref = (ConstraintReference) p.getCondition();
		c = ref.getReference();
	}

	@Override
	public Collection<NeoMatch> determineMatches(int limit) {
		var cond = new NeoCondition(new NeoConstraint(c, builder, helper), this, c.getName(), builder, helper);
		if (limit > 0)
			return cond.determineMatches(limit);
		else
			return cond.determineMatches();
	}

	@Override
	public boolean isStillValid(NeoMatch m) {
		var cond = new NeoCondition(new NeoConstraint(c, builder, helper), this, c.getName(), builder, helper);
		return cond.isStillValid(m);
	}

	protected Constraint c;

	@Override
	public String getQuery() {
		var cond = new NeoCondition(//
				new NeoConstraint(c, Optional.empty(), helper), //
				this, //
				c.getName(), //
				Optional.empty(), //
				helper);

		return cond.getQuery();
	}

}
