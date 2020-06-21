package org.emoflon.neo.engine.modules.attributeConstraints.standardLibrary;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.emoflon.neo.engine.modules.attributeConstraints.NeoAttributeConstraint;
import org.emoflon.neo.engine.modules.attributeConstraints.NeoAttributeConstraintVariable;

public class Concat extends NeoAttributeConstraint {

	private static final Logger logger = Logger.getLogger(Concat.class);
	
	/**
	 * concat(":", a, b, c)
	 * 
	 * a . ":" . b = c
	 * 
	 */
	@Override
	public void solve() {
		if (variables.size() != 4)
			throw new RuntimeException("The CSP -CONCAT- needs exactly four variables");

		var separator = variables.get(0);
		var a = variables.get(1);
		var b = variables.get(2);
		var c = variables.get(3);

		String bindingStates = getBindingStates(separator, a, b, c);

		switch (bindingStates) {
		case "BBBB": {
			setSatisfied(checkAllValues(separator, a, b, c));
			return;
		}

		case "BBBF": {
			c.bindToValue("" + a.getValue() + separator.getValue() + b.getValue());
			setSatisfied(checkAllValues(separator, a, b, c));
			return;
		}

		case "BBFB": {
			String[] split = ("" + c.getValue()).split(Pattern.quote("" + separator.getValue()));
			if (split.length != 2) {
				logger.warn(warningMessageForNonUniqueSeparator(c.getValue().toString()));
				setSatisfied(false);
			} else {
				b.bindToValue(split[1]);
				setSatisfied(checkAllValues(separator, a, b, c));
			}
			return;
		}

		case "BFBB": {
			String[] split = c.getValue().toString().split(Pattern.quote("" + separator.getValue()));
			a.bindToValue(split[0]);
			setSatisfied(checkAllValues(separator, a, b, c));
			return;
		}

		case "BFFB": {
			String[] split = c.getValue().toString().split(Pattern.quote("" + separator.getValue()));
			if (split.length == 2) {
				a.bindToValue(split[0]);
				b.bindToValue(split[1]);
				setSatisfied(checkAllValues(separator, a, b, c));
			} else {
				logger.warn(warningMessageForNonUniqueSeparator(c.getValue().toString()));
			}
			return;
		}

		// modelgen implementations
		case "BFFF": {
			setSatisfied(true);
			String value1 = "" + generateValue(a.getType());
			String value2 = "" + generateValue(b.getType());
			a.bindToValue(value1);
			b.bindToValue(value2);
			c.bindToValue(value1 + separator.getValue() + value2);
			return;
		}

		case "BBFF": {
			setSatisfied(true);
			String value = "" + generateValue(b.getType());
			b.bindToValue(value);
			c.bindToValue("" + a.getValue() + separator.getValue() + value);
			return;
		}

		case "BFBF": {
			setSatisfied(true);
			String value1 = "" + generateValue(a.getType());
			a.bindToValue(value1);
			c.bindToValue(value1 + separator.getValue() + b.getValue());
			return;
		}

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}

	private String warningMessageForNonUniqueSeparator(String s) {
		return "The separator used for splitting [" + s + "] is not unique!"
				+ " As I don't know what to do, this means the constraint 'concat' does not hold.";
	}

	protected boolean checkAllValues(NeoAttributeConstraintVariable separator,
			NeoAttributeConstraintVariable a, NeoAttributeConstraintVariable b,
			NeoAttributeConstraintVariable c) {
		return ("" + a.getValue() + separator.getValue() + b.getValue()).equals(c.getValue());
	}
}
