package org.emoflon.neo.emsl.compiler.ops;

import org.emoflon.neo.emsl.compiler.Operation;
import org.emoflon.neo.emsl.eMSL.Action;
import org.emoflon.neo.emsl.eMSL.ActionOperator;

public class BWD implements Operation {

	@Override
	public String getNameExtension() {
		return "_BWD";
	}

	@Override
	public String getAction(Action pAction, boolean pIsSrc) {
		if (!pIsSrc || pAction == null || !ActionOperator.CREATE.equals(pAction.getOp()))
			return "";
		else
			return "++";
	}

	@Override
	public String getTranslation(Action pAction, boolean pIsSrc) {
		if (!pIsSrc)
			if (pAction == null || !ActionOperator.CREATE.equals(pAction.getOp()))
				return "~_tr_ : true";
			else
				return "~_tr_ : false\n~_tr_ := true";
		else
			return "";

	}

	@Override
	public String getCorrAction(Action pAction) {
		if (pAction == null || !ActionOperator.CREATE.equals(pAction.getOp()))
			return "";
		else
			return "++";
	}

}
