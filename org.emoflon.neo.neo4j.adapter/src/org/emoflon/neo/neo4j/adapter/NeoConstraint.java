package org.emoflon.neo.neo4j.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.neo.emsl.eMSL.AndBody;
import org.emoflon.neo.emsl.eMSL.AtomicPattern;
import org.emoflon.neo.emsl.eMSL.Constraint;
import org.emoflon.neo.emsl.eMSL.ConstraintBody;
import org.emoflon.neo.emsl.eMSL.ConstraintReference;
import org.emoflon.neo.emsl.eMSL.Implication;
import org.emoflon.neo.emsl.eMSL.NegativeConstraint;
import org.emoflon.neo.emsl.eMSL.OrBody;
import org.emoflon.neo.emsl.eMSL.PositiveConstraint;
import org.emoflon.neo.emsl.eMSL.Primary;
import org.emoflon.neo.engine.api.constraints.IConstraint;

/**
 * TODO[Jannik] Implement constraints
 * 
 */
public class NeoConstraint implements IConstraint {

	private static final Logger logger = Logger.getLogger(NeoCoreBuilder.class);

	private NeoCoreBuilder builder;

	private Constraint c;

	public NeoConstraint(Constraint c, NeoCoreBuilder builder) {
		this.builder = builder;
		this.c = c;
		
	}

	@Override
	public boolean isSatisfied() {
		
		logger.info("Check constraint: " + c.getName());
		
		if(c.getBody() instanceof PositiveConstraint) {			
			var ap = (AtomicPattern) c.getBody().eCrossReferences().get(0);
			var co = new NeoPositiveConstraint(ap,builder);
			
			if(co.isSatisfied())
				return true;
			else
				return false;
			
		} else if(c.getBody() instanceof NegativeConstraint) {
			var ap = (AtomicPattern) c.getBody().eCrossReferences().get(0);
			var co = new NeoNegativeConstraint(ap,builder);
			
			if(co.isSatisfied())
				return true;
			else
				return false;
			
		} else if(c.getBody() instanceof Implication) {
			var apIf = (AtomicPattern) c.getBody().eCrossReferences().get(0);
			var apThen = (AtomicPattern) c.getBody().eCrossReferences().get(1);
			var co = new NeoImplication(apIf,apThen,builder);
			
			if(co.isSatisfied())
				return true;
			else
				return false;
			
		} else if(c.getBody() instanceof ConstraintReference) { 
		
			logger.info("Its a ConstraintReference!");
			throw new UnsupportedOperationException(c.getBody().toString());
		
		} else if (c.getBody() instanceof OrBody) {
			
			var co = (Constraint) c.getBody().eContainer();
			var bd = (OrBody) co.getBody();
			
			for(int i=0; i<bd.getChildren().size(); i++) {
				for(int j=0; j<bd.getChildren().get(i).getChildren().size(); j++) {
					
					var nestedConstraint = (Constraint) bd.getChildren().get(i).getChildren().get(j).eCrossReferences().get(0);
					var nestedNeoContarint = new NeoConstraint(nestedConstraint, builder);
					if(!nestedNeoContarint.isSatisfied()) {
						return false;
					}
				}
			}
			return true;
			
		} else if (c.getBody() instanceof AndBody) {
			
			var co = (Constraint) c.getBody().eContainer();
			var bd = (AndBody) co.getBody();
			
			for(int i=0; i<bd.getChildren().size(); i++) {
					
					var nestedConstraint = (Constraint) bd.getChildren().get(i).eCrossReferences().get(0);
					var nestedNeoContarint = new NeoConstraint(nestedConstraint, builder);
					if(!nestedNeoContarint.isSatisfied()) {
						return false;
					}
			}
			return true;
			
		} else if (c.getBody() instanceof Primary) {
			
			var co = (Constraint) c.getBody().eContainer();
			var bd = (Primary) co.getBody();
				
			var nestedConstraint = (Constraint) bd.eCrossReferences().get(0);
			var nestedNeoContarint = new NeoConstraint(nestedConstraint, builder);
			if(nestedNeoContarint.isSatisfied()) {
				return true;
			}

			return true;
			
		} else {
			logger.info("Its an Unkown Type!");
			throw new UnsupportedOperationException(c.getBody().toString());
		}
		
	}

}
