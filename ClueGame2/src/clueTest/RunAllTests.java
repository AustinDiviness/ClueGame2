package clueTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	BoardTest.class, 
	AdjacenciesTest.class,
	CRBoardInitTests.class,
	CRBoardAdjTargetTests.class,
	GameActionTests.class
	})


public class RunAllTests {
	
}
