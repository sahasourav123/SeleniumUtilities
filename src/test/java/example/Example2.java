package example;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.SupportUtil;

public class Example2 {
	
	SupportUtil util;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		util = new SupportUtil();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		util.getTimestamp();
	}

}
