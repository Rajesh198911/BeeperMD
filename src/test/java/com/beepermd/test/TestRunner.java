package com.beepermd.test;

import java.util.List;
import org.testng.TestNG;
import org.testng.collections.Lists;


public class TestRunner {
static TestNG testNG;

public static void main(String[] args) {
	TestNG testng = new TestNG();
	List<String> suites = Lists.newArrayList();
	suites.add("RunTest.xml");//path to xml..
	
	testng.setTestSuites(suites);
	testng.run();
}

}
