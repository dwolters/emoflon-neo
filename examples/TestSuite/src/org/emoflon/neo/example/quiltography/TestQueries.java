package org.emoflon.neo.example.quiltography;

import static org.junit.Assert.assertEquals;

import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.api.API_QuiltographyPages;
import org.emoflon.neo.api.API_QuiltographyQueries;
import org.emoflon.neo.example.ENeoTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestQueries extends ENeoTest {
	private API_QuiltographyQueries queries = new API_QuiltographyQueries(builder, API_Common.PLATFORM_RESOURCE_URI,
			API_Common.PLATFORM_PLUGIN_URI);
	private API_QuiltographyPages data = new API_QuiltographyPages(builder, API_Common.PLATFORM_RESOURCE_URI,
			API_Common.PLATFORM_PLUGIN_URI);

	@BeforeEach
	public void initDB() {
		initDB(data.getModel_GabisQuiltographyPages());
	}

	@Test
	public void test_BooksOfSomeAuthor() {
		var access = queries.getPattern_AllBooksOfAParticularAuthor();
		var mask = access.mask()//
				.setSomeAuthorName("Jean Ann")//
				.setSomeAuthorSurname("Wright");

		assertEquals(1, access.matcher(mask).countMatches());
		
		access.matcher(mask).determineOneMatch().ifPresent(m -> {
			var authorId = m.getIdForNode(access.someAuthor);
			var newMask = access.mask().setSomeAuthor(authorId);
			assertEquals(1, access.matcher(newMask).countMatches());
		});

		mask = access.mask()//
				.setSomeAuthorName("Hui")//
				.setSomeAuthorSurname("Boo");

		assertEquals(0, access.matcher(mask).countMatches());
	}

	@Test
	public void test_BooksWithSomeClassification() {
		var access = queries.getPattern_AllBooksWithAParticularClassification();
		var mask = access.mask().setClassificationName("Mixed");

		assertEquals(1, access.matcher(mask).countMatches());
		
		mask = access.mask().setClassificationName("Using Strips");
		
		assertEquals(1, access.matcher(mask).countMatches());
		
		mask = access.mask().setClassificationName("Rubbish");
		
		assertEquals(0, access.matcher(mask).countMatches());
		
		assertEquals(2, access.matcher().countMatches());
	}
	
	@Test
	public void test_PageOfSomeBook() {
		var access = queries.getPattern_AllPagesOfAParticularBook();
		var mask = access.mask().setBookTitle("Jambalaya Quilts");
		assertEquals(17, access.matcher(mask).countMatches());
		
		mask = access.mask().setBookTitle("Pillows");
		assertEquals(37, access.matcher(mask).countMatches());
		
		assertEquals(54, access.matcher().countMatches());
	}
	
	@Test
	public void testAllPagesOnQuiltsWithACertainPattern() {
		var access = queries.getPattern_AllPagesOnQuiltsWithACertainPattern();
		var mask = access.mask().setPatName("Weave");
		assertEquals(2, access.matcher(mask).countMatches());
	}
	
	@Test
	public void testAllPagesOnPillowsWithACertainPattern() {
		var access = queries.getPattern_AllPagesOnPillowsWithACertainPattern();
		var mask = access.mask().setPatName("Rectangle");
		assertEquals(8, access.matcher(mask).countMatches());
	}
}