package com.qmetry.qaf.example.test;

import static com.qmetry.qaf.automation.step.CommonStep.get;
import static com.qmetry.qaf.automation.step.CommonStep.verifyLinkWithPartialTextPresent;
import static com.qmetry.qaf.example.steps.StepsLibrary.searchFor;

import org.testng.annotations.Test;

import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import com.qmetry.qaf.example.pages.Times;

public class SampleTest extends WebDriverTestCase {
	
	Times homePage = new Times();

	@Test
	public void testGoogleSearch() {
		homePage.launchPage(null);
	}
}
