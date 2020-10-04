package org.emoflon.neo.example.javatodoc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.emoflon.neo.api.org.emoflon.neo.example.javatodoc.API_JavaToDocTriplesForTesting;
import org.emoflon.neo.emsl.eMSL.Metamodel;
import org.emoflon.neo.emsl.eMSL.Model;
import org.emoflon.neo.example.ENeoTest;
import org.junit.jupiter.api.Test;

import JavaToDoc.run.JavaToDoc_BWD_OPT_Run;
import JavaToDoc.run.JavaToDoc_CO_Run;

public class BWD_OPT_CO_Tests extends ENeoTest {

	private API_JavaToDocTriplesForTesting api = new API_JavaToDocTriplesForTesting(builder);

	private void runTest(Model srcModel, Model trgModel, String srcName, String trgName) throws Exception {
		builder.exportEMSLEntityToNeo4j(trgModel);
		var metamodels = builder.collectDependentMetamodels(srcModel);
		for (Metamodel m : metamodels) 
			builder.exportEMSLEntityToNeo4j(m);
		new JavaToDoc_BWD_OPT_Run(srcName, trgName, solver).run();
		assertTrue(new JavaToDoc_CO_Run(srcName, trgName, solver).runCheckOnly().isConsistent());
	}

	@Test
	public void testTriple1() throws Exception {
		runTest(api.getModel_ConsistentSrc1(), api.getModel_ConsistentTrg1(), "Source1", "ConsistentTrg1");
	}

	@Test
	public void testTriple2() throws Exception {
		runTest(api.getModel_ConsistentSrc2(), api.getModel_ConsistentTrg2(), "Source2", "ConsistentTrg2");
	}

	@Test
	public void testTriple3() throws Exception {
		runTest(api.getModel_ConsistentSrc3(), api.getModel_ConsistentTrg3(), "Source3", "ConsistentTrg3");
	}

	@Test
	public void testTriple4() throws Exception {
		runTest(api.getModel_ConsistentSrc4(), api.getModel_ConsistentTrg4(), "Source4", "ConsistentTrg4");
	}
}