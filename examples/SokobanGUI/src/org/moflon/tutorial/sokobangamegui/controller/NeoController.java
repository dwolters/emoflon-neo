
package org.moflon.tutorial.sokobangamegui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.api.metamodels.API_SokobanLanguage;
import org.emoflon.neo.api.models.API_SokobanSimpleTestField;
import org.emoflon.neo.api.org.moflon.tutorial.sokobangamegui.patterns.API_SokobanGUIPatterns;
import org.emoflon.neo.api.rules.API_SokobanPatternsRulesConstraints;
import org.emoflon.neo.emsl.util.FlattenerException;
import org.emoflon.neo.neo4j.adapter.models.NeoCoreBuilder;
import org.emoflon.neo.neo4j.adapter.rules.NeoCoMatch;
import org.moflon.tutorial.sokobangamegui.view.Field;
import org.moflon.tutorial.sokobangamegui.view.View;

public class NeoController implements IController {
	private View view;
	private API_SokobanGUIPatterns api1;
	private API_SokobanPatternsRulesConstraints api2;
	private NeoCoreBuilder builder;

	private int width = 0;
	private int height = 0;
	private List<Field> fields;

	public NeoController() {
		this(c -> new View(c), (c) -> c.defaultBoard());
	}
	
	public NeoController(Function<IController, View> createView, int width, int height) {
		this(createView, (c) -> c.newBoard(width, height));
	}
	
	public NeoController(int width, int height) {
		this(c -> new View(c), (c) -> c.newBoard(width, height));
	}

	public NeoController(Function<IController, View> createView, Consumer<NeoController> createBoard) {
		builder = API_Common.createBuilder();
		api1 = new API_SokobanGUIPatterns(builder, API_Common.PLATFORM_RESOURCE_URI, API_Common.PLATFORM_PLUGIN_URI);
		api2 = new API_SokobanPatternsRulesConstraints(builder, API_Common.PLATFORM_RESOURCE_URI,
				API_Common.PLATFORM_PLUGIN_URI);
		
		createBoard.accept(this);;
		
		update();
		view = createView.apply(this);
	}

	public static void main(String[] args) {
		Logger.getRootLogger().setLevel(Level.DEBUG);
		new NeoController(8, 8);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public List<String> getFigureTypes() {
		var access = api1.getPattern_FigureTypes();
		return access.matcher().determineMatches()//
				.stream().map(m -> {
					var data = access.data(m);
					return data.eclass.ename;
				}).collect(Collectors.toList());
	}

	@Override
	public Optional<Field> getSelectedField() {
		var access = api1.getPattern_SelectedFigure();
		return access.matcher().determineOneMatch().flatMap(m -> {
			var data = access.data(m);
			return fields.stream()//
					.filter(f -> f.getRow() == data.b_fields_1_f.row && f.getCol() == data.b_fields_1_f.col)//
					.findFirst();
		});
	}

	@Override
	public List<Field> getFields() {
		return fields;
	}

	@Override
	public boolean boardIsValid() {
		if (api1.getConstraint_ExactlyOneSokoban().isViolated()) {
			view.updateStatus("You must have exactly one Sokoban!");
			return false;
		}

		if (api1.getConstraint_OneEndField().isViolated()) {
			view.updateStatus("You must have exactly one end field!");
			return false;
		}

		if (api1.getPattern_Block().matcher().countMatches() != api1.getPattern_EndField().matcher().countMatches()) {
			view.updateStatus("You must have the same number of blocks as end fields!");
			return false;
		}

		if (api1.getConstraint_NoBlockedEndField().isViolated()) {
			view.updateStatus("One of your end fields is blocked by a boulder!");
			return false;
		}

		if (api2.getPattern_BlockNotOnEndFieldInCorner().matcher().determineOneMatch().isPresent()) {
			view.updateStatus("One of your blocks is in a corner (which is not an end field)!");
			return false;
		}

		view.updateStatus("Everything seems to be ok...");
		return true;
	}

	@Override
	public void setFigure(Field field, String figureType) {
		Optional<?> result;
		switch (figureType) {
		case SOKOBAN: {
			var access = api1.getRule_CreateSokoban();
			var mask = access.mask();
			mask.setB_fields_0_fCol(field.getCol());
			mask.setB_fields_0_fRow(field.getRow());
			result = access.rule(mask).apply();
			break;
		}
		case BLOCK: {
			var access = api1.getRule_CreateBlock();
			var mask = access.mask();
			mask.setB_fields_0_fCol(field.getCol());
			mask.setB_fields_0_fRow(field.getRow());
			result = access.rule(mask).apply();
			break;
		}
		case BOULDER: {
			var access = api1.getRule_CreateBoulder();
			var mask = access.mask();
			mask.setB_fields_0_fCol(field.getCol());
			mask.setB_fields_0_fRow(field.getRow());
			result = access.rule(mask).apply();
			break;
		}
		default:
			var access = api1.getRule_DeleteFigure();
			var mask = access.mask();
			mask.setB_fields_0_fCol(field.getCol());
			mask.setB_fields_0_fRow(field.getRow());
			result = access.rule(mask).apply();
			break;
		}
		
		if(!result.isPresent())
			throw new UnsupportedOperationException("Unable to create " + figureType + " on " + field);
	}

	@Override
	public void selectField(Field field) {
		Optional<NeoCoMatch> comatch = Optional.empty();

		// If the board has a selected figure, try to move figure to this field
		{
			var mask = api2.getRule_MoveSokobanDownWithCondition().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_MoveSokobanDownWithCondition().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}

		{
			var mask = api2.getRule_PushBlockDown().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_PushBlockDown().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}
		
		{
			var mask = api2.getRule_MoveSokobanUpWithCondition().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_MoveSokobanUpWithCondition().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}
		
		{
			var mask = api2.getRule_PushBlockUp().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_PushBlockUp().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}

		{
			var mask = api2.getRule_MoveSokobanRightWithCondition().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_MoveSokobanRightWithCondition().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}

		{
			var mask = api2.getRule_PushBlockRight().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_PushBlockRight().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}
		
		{
			var mask = api2.getRule_MoveSokobanLeftWithCondition().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_MoveSokobanLeftWithCondition().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}
		
		{
			var mask = api2.getRule_PushBlockLeft().mask();
			mask.setB_fields_1_toCol(field.getCol());
			mask.setB_fields_1_toRow(field.getRow());
			comatch = api2.getRule_PushBlockLeft().rule(mask).apply();

			if (comatch.isPresent())
				return;
		}


		// All movement failed, so try to select the figure on the field
		api1.getRule_SelectFigure().rule(//
				api1.getRule_SelectFigure().mask()//
						.setB_fields_0_fCol(field.getCol())//
						.setB_fields_0_fRow(field.getRow()))//
				.apply();
	}

	@Override
	public void setEndPos(Field field, boolean b) {
		if (b) {
			var mask = api1.getRule_SetEndField().mask();
			mask.setB_fields_0_fCol(field.getCol());
			mask.setB_fields_0_fRow(field.getRow());
			api1.getRule_SetEndField().rule(mask).apply();
		} else {
			var mask = api1.getRule_SetNotEndField().mask();
			mask.setB_fields_0_fCol(field.getCol());
			mask.setB_fields_0_fRow(field.getRow());
			api1.getRule_SetNotEndField().rule(mask).apply();
		}
	}

	@Override
	public void newBoard(int width, int height) {
		try {
			var language = new API_SokobanLanguage(builder, API_Common.PLATFORM_RESOURCE_URI,
					API_Common.PLATFORM_PLUGIN_URI);
			var metamodel = language.getMetamodel_SokobanLanguage();
			builder.exportEMSLEntityToNeo4j(metamodel);
		} catch (FlattenerException e) {
			e.printStackTrace();
		}
		
		var maskTopLeft = api1.getRule_CreateTopLeft().mask();
		maskTopLeft.setBWidth(width);
		maskTopLeft.setBHeight(height);
		
		// Top-left corner
		api1.getRule_CreateTopLeft().rule(maskTopLeft).apply();
		
		// First row
		for (int col = 0; col < width-1; col++) {			
			api1.getRule_CreateFirstRow().rule().apply();
		}
		
		// First column
		for (int row = 0; row < height-1; row++) {
			api1.getRule_CreateFirstCol().rule().apply();
		}
		
		// Apply as long as possible
		var rest = api1.getRule_CreateRestOfFields().rule();
		while(rest.apply().isPresent());
		
		extractFields();
	}

	private void defaultBoard() {
		try {
			var exampleBoard = new API_SokobanSimpleTestField(builder, API_Common.PLATFORM_RESOURCE_URI,
					API_Common.PLATFORM_PLUGIN_URI);
			var board = exampleBoard.getModel_SokobanSimpleTestField();
			builder.exportEMSLEntityToNeo4j(board);
			extractFields();
		} catch (FlattenerException e) {
			e.printStackTrace();
		}
	}

	private void extractFields() {
		var accessBoard = api1.getPattern_Board();
		accessBoard.matcher().determineOneMatch().ifPresent(m -> {
			var mData = accessBoard.data(m);
			this.width = mData.board.width;
			this.height = mData.board.height;

			fields = new ArrayList<Field>();
			var accessEmptyFields = api1.getPattern_EmptyFields();
			accessEmptyFields.matcher().determineMatches().forEach(f -> {
				var fData = accessEmptyFields.data(f);
				fields.add(new Field(//
						fData.board_fields_0_field.row, //
						fData.board_fields_0_field.col, //
						fData.field.endPos, //
						Optional.empty()));
			});

			var accessOccupiedFields = api1.getPattern_OccupiedFields();
			accessOccupiedFields.matcher().determineMatches().forEach(f -> {
				var data = accessOccupiedFields.data(f);
				fields.add(new Field(//
						data.board_fields_0_field.row, //
						data.board_fields_0_field.col, //
						data.field.endPos, //
						Optional.of(data.type.ename)));
			});
		});
	}

	@Override
	public void clearBoard() {
		var access = api1.getRule_DeleteFigure();

		access.rule().determineMatches().forEach(m -> {
			access.rule().apply(m);
		});
	}

	@Override
	public void loadSOKFile(String filePath) {
		// Populate database from sok file
	}

	@Override
	public void saveSOKFile(String filePath) {
		// Write out board to a sok file
	}

	@Override
	public void update() {
		var accessEmptyFields = api1.getPattern_EmptyFields();
		accessEmptyFields.matcher().determineMatches().forEach(f -> {
			var fData = accessEmptyFields.data(f);
			fields.stream().filter(fld -> fld.getCol() == fData.board_fields_0_field.col
					&& fld.getRow() == fData.board_fields_0_field.row).forEach(fld -> {
						fld.setIsEndPos(fData.field.endPos);
						fld.setFigureName(Optional.empty());
					});
		});

		var accessOccupiedFields = api1.getPattern_OccupiedFields();
		accessOccupiedFields.matcher().determineMatches().forEach(f -> {
			var fData = accessOccupiedFields.data(f);
			fields.stream().filter(fld -> fld.getCol() == fData.board_fields_0_field.col
					&& fld.getRow() == fData.board_fields_0_field.row).forEach(fld -> {
						fld.setIsEndPos(fData.field.endPos);
						fld.setFigureName(Optional.of(fData.type.ename));
					});
		});
	}
}