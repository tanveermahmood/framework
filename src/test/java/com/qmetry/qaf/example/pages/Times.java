package com.qmetry.qaf.example.pages;

import static com.qmetry.qaf.automation.step.CommonStep.get;

import java.util.concurrent.TimeUnit;

import com.qmetry.qaf.automation.ui.WebDriverBaseTestPage;
import com.qmetry.qaf.automation.ui.api.PageLocator;
import com.qmetry.qaf.automation.ui.api.WebDriverTestPage;

public class Times extends WebDriverBaseTestPage<WebDriverTestPage> {
	@Override
	protected void openPage(PageLocator locator, Object... args) {
		get("/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	

}
