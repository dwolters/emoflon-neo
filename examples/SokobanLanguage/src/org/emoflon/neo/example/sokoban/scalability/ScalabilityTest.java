package org.emoflon.neo.example.sokoban.scalability;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.neo.emsl.EMSLStandaloneSetup;
import org.emoflon.neo.emsl.eMSL.EMSLFactory;
import org.emoflon.neo.emsl.eMSL.EMSL_Spec;
import org.emoflon.neo.emsl.eMSL.Model;
import org.emoflon.neo.emsl.eMSL.NodeBlock;
import org.emoflon.neo.emsl.eMSL.RelationStatement;
import org.emoflon.neo.emsl.eMSL.impl.EMSLPackageImpl;
import org.emoflon.neo.neo4j.adapter.NeoCoreBuilder;

import com.google.inject.Injector;

public class ScalabilityTest {

	public static void main(String[] args) throws Exception {
		ScalabilityTest t = new ScalabilityTest();
		EMSLPackageImpl.init();

		int n = 500;

		String log = "";
		for (int size = 0; size <= n; size += 50) {
			log += t.runTests(size, 10000, 10000);
		}

		System.out.println(log);
	}

	public String runTests(int modelSize, int nodes, int edges) throws Exception {
		EMSLPackageImpl.init();

		String time = "";

		NeoCoreBuilder builder = new NeoCoreBuilder("bolt://localhost:7687", "neo4j", "test");
		builder.setMaxTransactionSize(nodes, edges);
		try {
			new org.eclipse.emf.mwe.utils.StandaloneSetup().setPlatformUri("../");
			Injector injector = new EMSLStandaloneSetup().createInjectorAndDoEMFRegistration();
			XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
			resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
			Resource resource = resourceSet.getResource(URI.createURI("platform:/resource/SokobanLanguage/models/SokobanBasis.msl"),
					true);
			EMSL_Spec spec = (EMSL_Spec) resource.getContents().get(0);

			Model model = (Model) spec.getEntities().get(0);
			model.setName(model.getName() + "_" + modelSize);
			generateContents(model, modelSize);

			long tic = System.currentTimeMillis();
			builder.exportEMSLEntityToNeo4j(spec.getEntities().get(0), s -> System.out.println(s));
			long toc = System.currentTimeMillis();
			System.out.println("Export took: " + (toc - tic) / 1000.0 + "s");
			time += (toc - tic) / 1000.0 + "\n";
		} finally {
			builder.close();
		}

		return time;
	}

	private void generateContents(Model model, int size) {
		NodeBlock boardNb = model.getNodeBlocks().get(0);
		NodeBlock field0 = model.getNodeBlocks().get(1);

		List<NodeBlock> lastCol = new ArrayList<>();

		lastCol.add(field0);

		List<NodeBlock> lastRow = new ArrayList<>();

		lastRow.add(field0);

		extendBoard(lastCol, lastRow, size, boardNb, model, field0);
	}

	private void extendBoard(List<NodeBlock> lastCol, List<NodeBlock> lastRow, int size, NodeBlock board, Model model,
			NodeBlock firstField) {

		if (size == 0)
			return;

		List<NodeBlock> newLastCol = new ArrayList<>();
		List<NodeBlock> newLastRow = new ArrayList<>();

		for (NodeBlock nb : lastCol) {
			NodeBlock rightField = EMSLFactory.eINSTANCE.createNodeBlock();
			rightField.setName("rf");
			rightField.setType(firstField.getType());
			model.getNodeBlocks().add(rightField);

			newLastCol.add(rightField);

			{
				RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
				rs.setName("fields");
				rs.setValue(rightField);
				board.getRelationStatements().add(rs);
			}

			{
				RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
				rs.setName("right");
				rs.setValue(rightField);
				nb.getRelationStatements().add(rs);
			}

			int index = lastCol.indexOf(nb);

			if (index > 0) {
				var lCol = newLastCol.get(index - 1);

				RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
				rs.setName("bottom");
				rs.setValue(rightField);
				lCol.getRelationStatements().add(rs);
			}

		}

		for (NodeBlock nb1 : lastRow) {
			NodeBlock bottomField = EMSLFactory.eINSTANCE.createNodeBlock();
			bottomField.setName("bf");
			bottomField.setType(firstField.getType());
			model.getNodeBlocks().add(bottomField);

			newLastRow.add(bottomField);

			{
				RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
				rs.setName("fields");
				rs.setValue(bottomField);
				board.getRelationStatements().add(rs);
			}

			{
				RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
				rs.setName("bottom");
				rs.setValue(bottomField);
				nb1.getRelationStatements().add(rs);
			}

			int index = lastRow.indexOf(nb1);

			if (index > 0) {
				var lRow = newLastRow.get(index - 1);

				RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
				rs.setName("right");
				rs.setValue(bottomField);
				lRow.getRelationStatements().add(rs);
			}

		}

		var lc = newLastCol.get(newLastCol.size() - 1);
		var lr = newLastRow.get(newLastRow.size() - 1);

		NodeBlock cornerField = EMSLFactory.eINSTANCE.createNodeBlock();
		cornerField.setName("cf");
		cornerField.setType(firstField.getType());
		model.getNodeBlocks().add(cornerField);

		{
			RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
			rs.setName("fields");
			rs.setValue(cornerField);
			board.getRelationStatements().add(rs);
		}

		{
			RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
			rs.setName("bottom");
			rs.setValue(cornerField);
			lc.getRelationStatements().add(rs);

		}

		{
			RelationStatement rs = EMSLFactory.eINSTANCE.createRelationStatement();
			rs.setName("right");
			rs.setValue(cornerField);
			lr.getRelationStatements().add(rs);
		}

		newLastCol.add(cornerField);
		newLastRow.add(cornerField);

		extendBoard(newLastCol, newLastRow, size - 1, board, model, firstField);
	}
}
