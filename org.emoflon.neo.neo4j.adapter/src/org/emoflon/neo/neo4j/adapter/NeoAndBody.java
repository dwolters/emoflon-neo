package org.emoflon.neo.neo4j.adapter;

import org.apache.log4j.Logger;
import org.emoflon.neo.emsl.eMSL.AndBody;
import org.emoflon.neo.emsl.eMSL.ConstraintReference;
import org.emoflon.neo.emsl.eMSL.OrBody;

public class NeoAndBody {

	private static final Logger logger = Logger.getLogger(NeoCoreBuilder.class);
	private AndBody body;
	private NeoCoreBuilder builder;

	public NeoAndBody(AndBody body, NeoCoreBuilder builder) {

		this.body = body;
		this.builder = builder;

	}

	public boolean isSatisfied() {

		for (Object b : body.getChildren()) {

			if (b instanceof ConstraintReference) {
				var consRef = new NeoConstraint(((ConstraintReference) b).getReference(), builder);

				if(((ConstraintReference) b).isNegated()) {
					logger.info("Attention: Constraint is negated!");
				}
				
				if ((!consRef.isSatisfied() && !((ConstraintReference) b).isNegated()) ||
						consRef.isSatisfied() && ((ConstraintReference) b).isNegated()) {
					
					return false;
				}

			} else if (b instanceof OrBody) {
				var orbody = new NeoOrBody((OrBody) b, builder);

				if (!orbody.isSatisfied()) {
					return false;
				}
			}
		}

		return true;

	}

}
