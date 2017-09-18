  package com.qmetry.qaf.example.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import com.qmetry.qaf.automation.util.Reporter;

public class WebUtility extends WebDriverTestBase {
	WebDriver driver;
	public static String folderpath = "/air_irctc/resources/data.xlsx";
	public static Workbook book;
	public static Sheet sh;
	public String mainHandle = driver.getWindowHandle();

	/* parallel browser selection */
	public WebDriver openBrowser(String browsername) {
		try {
			if (browsername.equalsIgnoreCase("Firefox")) {
				driver = new FirefoxDriver();
			}
			if (browsername.equalsIgnoreCase("Chrome")) {

				System.setProperty("webdriver.chrome.driver",
						"lib/chromedriver.exe");
				driver = new ChromeDriver();
			}
			if (browsername.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						"lib/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		} catch (WebDriverException e) {

			System.out.println("Browser not found" + e.getMessage());
		}
		driver.get("/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}

	/* click method */

	public static void clickOnElement(QAFWebElement element) {
		element.isEnabled();
		element.click();

	}

	/* click using javascript executor */
	public void clickOnelementUsingJavascriptExecutor(QAFWebElement element) {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out
						.println("Clicking on element with using java script click");
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].click()", element);

			} else {
				System.out.println("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document "
					+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element was not found in DOM "
					+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to click on element "
					+ e.getStackTrace());
		}
	}

	/* verification of text using senkeys method */
	public static void sendTextToElement(QAFWebElement element, String text) {
		element.isPresent();
		element.clear();
		element.sendKeys(text);
	}

	/* check for alert present or not */

	public boolean isAlertPresent() {
		try {

			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	

	/* Handle alert option will depends on user */
	public void invokedAlert(int SelectOption) {
		if (isAlertPresent()) {
			Alert alert = driver.switchTo().alert();
			switch (SelectOption) {
			case 0:
				System.out.println("Alert text :" + alert.getText());
				alert.accept();
				break;
			case 1:
				System.out.println("Alert text :" + alert.getText());
				alert.dismiss();
				break;
			case 2:
				System.out.println("Alert text :" + alert.getText());
				alert.getText();
				break;
			default:
				System.out.println("not able to select the option");
				break;
			}

		} else {
			System.out.println("Alert is not present");
		}
	}

	public void alertAccept() {
		Alert al = driver.switchTo().alert();
		al.accept();
	}

	public void alertDismiss() {
		Alert al = driver.switchTo().alert();
		al.dismiss();
	}

	public String alertGetText() {
		Alert al = driver.switchTo().alert();
		return al.getText();
	}

	public void alertSendkeys(String text) {
		Alert al = driver.switchTo().alert();
		al.sendKeys(text);
	}

	/*
	 * checkbox selection
	 */

	public static void selectCheckbox(QAFWebElement element) {
		try {
			if (element.isSelected()) {
				System.out.println("Checkbox: " + element
						+ "is already selected");
			} else {
				element.click();
			}
		} catch (Exception e) {
			System.out.println("Unable to select the checkbox: " + element);
		}

	}

	/* select checkbix from list */

	public void selectTheCheckBoxfromList(WebElement element,
			String valueToSelect) {
		List<WebElement> allOptions = element.findElements(By.tagName("input"));
		for (WebElement option : allOptions) {
			System.out.println("Option value is " + option.getText());
			if (valueToSelect.equals(option.getText())) {
				option.click();
				break;
			}
		}
	}

	/* checkbox deselection(if chekcbox is already selected) */
	public static void DeselectCheckbox(QAFWebElement element) {
		try {
			if (element.isSelected()) {
				element.click();
			} else {
				System.out
						.println("checknox is already deselected: " + element);
			}
		} catch (Exception e) {
			System.out.println("Unable to deselect checkbox :" + element);
		}
	}

	/* dropdown selection using all three methods */
	public static void selectDropdown(QAFWebElement element, int args) {
		Select se = new Select(element);
		se.selectByIndex(args);
	}

	public static void selectDropdownByText(QAFWebElement element, String text) {
		Select se = new Select(element);
		se.selectByVisibleText(text);
	}

	public static void selectDropdownByValue(QAFWebElement element, String value) {
		Select se = new Select(element);
		se.selectByValue(value);
	}

	/* Auto-suggest dropdown */
	public void selectAutoSuggestionDropdown(QAFWebElement element, String value) {
		element.click();
		element.sendKeys(value);
		element.sendKeys(Keys.TAB);
	}

	/* mouseover action method */
	public void MouseOver(QAFWebElement element) {
		Actions action = new Actions(driver);
		// Actions actObj = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	/* handle window */
	public void handleWindow() {
		String mainHandle = driver.getWindowHandle();
		for (String childWindow : driver.getWindowHandles()) {
			driver.switchTo().window(childWindow);
		}
	}

	// For Screenshot

	public void take_screenShot(String Filename) {
		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
					+ "screenshot" + Filename + ".png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void waitforClick(By locator) {

		WebElement ele = driver.findElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, 60);

		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public void waitforElementSelected(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeSelected(locator));
	}

	public void waitforAlert(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void waitforVisible(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	// Read the value from properties file

	public String readproperty(String Filename, String property)
			throws Exception {
		Properties prop = new Properties();
		InputStream input = null;

		input = new FileInputStream(System.getProperty("user.dir")
				+ "//resource//" + Filename + ".properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		return prop.getProperty(property);

	}

	/* get the current window handle */
	public String getMainWindowHandle() {
		return driver.getWindowHandle();
	}

	// wait for given time
	public void kw_synchronize(int value) throws InterruptedException {
		Thread.sleep(value);
	}

	// close the current browser

	public void kw_close() {
		driver.close();
	}

	// maximize the window

	public void kw_maximize() {
		driver.manage().window().maximize();
	}

	// entering text in a text field

	public void kw_enter_text(QAFWebElement element, String text) {
		element.sendKeys(text);
	}

	public void kw_enter_text1(QAFWebElement element, String text) {
		element.sendKeys(text, Keys.ARROW_DOWN, Keys.ENTER);

	}

	// click button

	public void kw_click_link(QAFWebElement element) {
		element.click();
	}

	// move to an element

	public void kw_moveToElement(QAFWebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
	}

	// selecting the element from dropdown

	public void kw_dropdown(QAFWebElement ele, int index) {
		Select sel = new Select(ele);
		sel.selectByIndex(index);
	}

	// validating the string

	public void kw_validate(QAFWebElement expected, QAFWebElement actual) {
		boolean booleanvalue = expected.equals(actual);
		if (booleanvalue == false) {
			System.out.println("expected string" + expected
					+ "Not matching actual string" + actual + "");
		} else {
			System.out.println("expected string" + expected
					+ "Matching actual string" + actual + "");
		}
	}

	// validatig the page title

	public void kw_validate_title(String exp_Title) {
		String actualtitle = driver.getTitle();
		if (exp_Title.equals(actualtitle)) {
			System.out.println("pass");
		} else {
			System.out.println("false");
		}
	}

	// handling popup windows

	public void kw_alertPopup() {
		Alert al = driver.switchTo().alert();
		al.accept();
	}

	// for creating excel connection
	public void createExcelConnection(String filename, String sheetname) {
		try {
			FileInputStream fis = new FileInputStream(folderpath + filename);
			book = Workbook.getWorkbook(fis);
			sh = book.getSheet(sheetname);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// for counting no of rows
	public int rowCount() {
		return sh.getRows();
	}

	// for counting no of columns
	public int colCount() {
		return sh.getColumns();
	}

	// for fetching values from excel sheet
	public String readData(int cnum, int rnum) {
		return sh.getCell(cnum, rnum).getContents();
	}
	// for scrolling on element
	public static void scrollToElement(QAFWebElement element) {
		  JavascriptExecutor js = (JavascriptExecutor) new WebDriverTestBase().getDriver();
		  js.executeScript("arguments[0].scrollIntoView();", element);
		 }
	//  handling windows
	public static void switch_to_Frame(String mainWindowHandle) 
	 {
	  Set s = new WebDriverTestBase().getDriver().getWindowHandles();
	        Iterator ite = s.iterator();
	        while(ite.hasNext())
	        {
	             String popupHandle=ite.next().toString();
	             if(!popupHandle.contains(mainWindowHandle))
	             {
	              new WebDriverTestBase().getDriver().switchTo().window(popupHandle);
	             }
	        }
	 }
	
	// for accepting terms and condition
	private void AcceptTermAndCondition() {
		  // switchToContext(Context.NATIVE);
		  QAFExtendedWebElement element = new QAFExtendedWebElement(
		    By.xpath("//*[@resource-id='com.android.chrome:id/terms_accept']"));
		  if (element.isPresent()) {
		   element.click();
		   QAFExtendedWebElement btnNoThanks = new QAFExtendedWebElement(By
		     .xpath("//*[@resource-id='com.android.chrome:id/negative_button']"));
		   btnNoThanks.waitForPresent();
		   btnNoThanks.click();
		  }
		 }
	//
	/*protected void launchApp(String browserName) {
		  try {
		   try {
		    if (browserName.equalsIgnoreCase("ios")) {
		     closeApp("Safari", null);
		     cleanApp("Safari");
		     launchApp("Safari", null);
		     switchToContext(Context.WEBVIEW);
		    } else {
		     closeApp("Chrome", null);
		     cleanApp("Chrome");
		     // handleTabs();
		     launchApp("Chrome", null);
		     switchToContext(Context.NATIVE);
		     acceptTCChrome();
		     switchToContext(Context.WEBVIEW);

		    }

		   } catch (Exception e) {
		    if (browserName.equalsIgnoreCase("ios")) {
		     launchApp("Safari", null);
		     switchToContext(Context.WEBVIEW);
		    } else {
		     launchApp("Internet", null);
		     switchToContext(Context.WEBVIEW);
		    }
		   }
		  } catch (Exception e2) {
		   System.out.println("========== Exception Caught : 1 =============");
		   e2.printStackTrace();
		  }
		  
		  //
		  public static enum Context {
			  NATIVE,
			  WEBVIEW,
			  VISUAL;
			  
			 }*/
	
		  
			public static void closeApp(String appId, String fakeParam) {
			  String command = "mobile:application:close";
			  Map<String, Object> params = new HashMap<String, Object>();
			  params.put("name", appId);

			  try {
			   ((RemoteWebDriver) new WebDriverTestBase().getDriver()).executeScript(command,
			     params);
			  } catch (Exception e) {
			   System.err.println("Unable to Close app: " + appId);
			   Reporter.log("Unable to Close app: " + appId);
			  }
			 }

			 public static void cleanApp(String appId) {
			  String command = "mobile:application:clean";
			  Map<String, Object> params = new HashMap<String, Object>();
			  params.put("name", appId);

			  try {
			   ((RemoteWebDriver) new WebDriverTestBase().getDriver()).executeScript(command,
			     params);
			  } catch (Exception e) {
			   System.err.println("Unable to Clean app: " + appId);
			   Reporter.log("Unable to Clean app: " + appId);
			  }
			 }

			 public static void launchApp(String appId, String fakeParam) {
			  // if (StringUtils.isBlank(appId))
			  // return;
			  String command = "mobile:application:open";
			  Map<String, Object> params = new HashMap<String, Object>();
			  params.put("name", appId);
			  try {
			   ((RemoteWebDriver) new WebDriverTestBase().getDriver()).executeScript(command,
			     params);
			  } catch (Exception e) {
			   System.err.println("Unable to Open app: " + appId);
			   Reporter.log("Unable to Open app: " + appId);
			  }
			 }
			 
			 //
			 /*public static void switchToContext(Context context) {
				  RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(
				    (RemoteWebDriver) new WebDriverTestBase().getDriver());
				  Map<String, String> params = new HashMap<String, String>();
				  params.put("name", context.name());
				  executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
				 }
			 
			 //
			 public void acceptTCChrome() {
				  HomePageLOC loc = new HomePageLOC();
				  loc.getHomePageChromeAcceptTC().waitForPresent();
				  loc.getHomePageChromeAcceptTC().click();

				  loc.getHomePageChromeNoThanks().waitForPresent();
				  loc.getHomePageChromeNoThanks().click();

				 }
			 */
			 //
			 public static void waitforElementToPresent(QAFWebElement element) {
				  int count = 0;
				  while (count < 3) {
				   try {
				    element.waitForPresent();
				    break;
				   } catch (Exception ex) {
				    ex.printStackTrace();
				    count++;
				   }
				  }
				 }
			 //
			 public void clearCookiesAndMaximizeWin() {
				  getDriver().manage().deleteAllCookies();
				  getDriver().manage().window().maximize();
				 }
			
			 

}
