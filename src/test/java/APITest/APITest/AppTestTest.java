package APITest.APITest;

import org.testng.annotations.Test;

import Utilities.LoggerLoad;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class AppTestTest {
  @BeforeTest
  public void beforeTest() {
	  System.out.println("Before");
	  LoggerLoad.info("Info");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("After");

  }


  @Test
  public void requestBodyTest() {
    
    System.out.println("Test not implemented");
  }
}
