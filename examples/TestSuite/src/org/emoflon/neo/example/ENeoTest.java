package org.emoflon.neo.example;

import static org.junit.Assert.fail;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.emoflon.neo.api.API_Common;
import org.emoflon.neo.example.sokoban.scalability.ScalabilityTest;
import org.emoflon.neo.neo4j.adapter.NeoCoreBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.StatementResult;

public abstract class ENeoTest {
	
	private static Scanner reader;
	protected static final Logger logger = Logger.getLogger(ScalabilityTest.class);
	protected static NeoCoreBuilder builder = API_Common.createBuilder();
	protected static Driver driver = builder.getDriver();
	
	@BeforeAll
	private static void startDBConnection() throws Exception {
		logger.info("Database Connection established.");
		StatementResult result = driver.session().run("MATCH (n) RETURN count(n)");
	
		if (result.hasNext()) {

			if (result.next().get(0).asInt() > 0) {
				logger.info(
						"Database not empty. All data will be removed! \n" + "Do you want to continue (Y=Yes / N=No)?");
				reader = new Scanner(System.in);
				String input = reader.next();

				if (input.toLowerCase().equals("n")) {
					logger.info("Tests have been canceled. No changes in the database executed.");
					closeDBConnection();
					fail();
				} else {
					driver.session().run("MATCH (n) DETACH DELETE n");
					logger.info("Database cleared.");
				}
			} else {
				logger.info("Database empty.");
			}

		} else {
			logger.info("Database empty.");
		}
	}

	@AfterAll
	public static void closeDBConnection() throws Exception {
		builder.close();
		logger.info("Database Connection closed.");
	}
	
	@AfterEach
	private void clearDB() {
		driver.session().run("MATCH (n) DETACH DELETE n");
		logger.info("Database cleared.");
	}

}
