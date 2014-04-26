package JunitTest;

import junit.framework.TestCase;


public class SampleTest extends TestCase {
	
	public SampleTest(){
		
	}

	public void test1(){
		assertEquals(1, 1);
	}

	
	public void test2(){
		assertEquals(1, 2);
	}

}
