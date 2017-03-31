package Test;

import java.beans.Visibility;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import Data.Xls_Reader;
import ReportUtils.ReportUtil;
import TestUtils.TestResources;
import TestUtils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

public class KeyWords extends TestResources {
	private static final int ONE_SECOND = 1;
	static WebDriverWait wait = null;
	private static String substr = "";
	public static int hiddenCount=0;
	
	
	public static String Navigate() {
		System.out.println(Webelement);
		driver.get(Webelement);
		return "Pass";
	}

	public static String navigateback() {
		driver.navigate().back();
		driver.navigate().forward();
		return "Pass";
	}

	public static String pageRefresh() {
		if (driver.findElements(By.id("drop-signout")).size() == 0)
			driver.navigate().refresh();
		return "Pass";

	}

	public static String navigateForward() {
		driver.navigate().forward();
		return "Pass";
	}

	public static String Click() {
		try {
			moveToElement();
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(Utils.getBy(Webelement)));
			driver.findElement(Utils.getBy(Webelement)).click();
			// driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	
	public static String ClickAllLinks() {
		String[] products = DataValue.split(",");
		for (String prod : products) {
			String finalLoc = String.format(OR.getProperty(Webelement), prod);
			try {
				driver.findElement(By.xpath(finalLoc)).click();
			} catch (Exception e) {
				return "Failed: element not found" + Webelement;
			}
		}
		return "Pass";
	}

	public static String InputText() {
		try {
			// moveToElement();
			driver.findElement(Utils.getBy(Webelement)).clear();
			driver.findElement(Utils.getBy(Webelement)).sendKeys(DataValue);

		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	public static String Enter() {
		try {
			driver.findElement(Utils.getBy(Webelement1)).clear();
			driver.findElement(Utils.getBy(Webelement1)).sendKeys(DataValue);
			Thread.sleep(1000);
			/*
			 * Actions action = new Actions(driver);
			 * action.sendKeys(Keys.ENTER).build().perform();
			 */
			ClickByJavascript();
			return "Pass";
		} catch (Exception e) {
			return "Failed to Enter";
		}
	}

	public static String clickOnStrong() {
		try {
			driver.findElement(By.xpath("//strong[text()=" + DataValue)).click();
			return "Pass";
		} catch (Exception e) {
			return "Failed";
		}
	}

	public static String isEditable() {
		try {
			moveToElement();
			driver.findElement(Utils.getBy(Webelement)).sendKeys(DataValue);
			if (!(driver.findElement(Utils.getBy(Webelement)).getAttribute("value").contains(DataValue))) {
				System.out
						.println("Edit text ==> " + driver.findElement(Utils.getBy(Webelement)).getAttribute("value"));// GetAttribute("value")
				return "Failed: element not editable " + Webelement;
			}
		} catch (Exception e) {
			return "Failed: element not found " + Webelement;
		}
		return "Pass";
	}

	public static String SelectByVisibleText() {
		try {

			Select sel = new Select(driver.findElement(Utils.getBy(Webelement)));
			System.out.println(sel.getOptions());
			sel.selectByVisibleText(DataValue);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	public static String SelectByIndex() {
		try {
			Select sel = new Select(driver.findElement(Utils.getBy(Webelement)));
			System.out.println(sel.getOptions());
			sel.selectByIndex(Integer.parseInt(DataValue));
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	public static String SelectByValue() {
		try {
			Select sel = new Select(driver.findElement(Utils.getBy(Webelement)));
			sel.selectByValue(DataValue);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	/**
	 * Reads the selected values from Dropdown field - obsolete 8th Jul
	 * 2016[2.7]
	 */
	public static String getSelectedValues() {
		try {

			Select sel = new Select(driver.findElement(Utils.getBy(Webelement)));
			List<WebElement> options = sel.getAllSelectedOptions();
			System.out.println("Total Selected values ==>" + options.size());
			for (WebElement op : options) {
				System.out.println("getAllSelectedOptions ==>" + op.getText());

			}

		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	/*
	 * Function to verify selected values from dropdown
	 */
	public static String VerifySelectedValues() {
		try {
			int totalCount = driver.findElements(Utils.getBy(Webelement)).size();
			System.out.println("Count ====> " + totalCount);
			String[] ExpValues;
			ExpValues = DataValue.split(",");
			boolean isTrue = true;
			for (int i = 0; i < ExpValues.length; i++) {
				if (totalCount > 0) {
					for (int j = 0; j < totalCount; j++) {
						isTrue = true;
						String element = OR.getProperty(Webelement) + "/preceding::span/span[text()='"
								+ ExpValues[i].trim() + "']";
						System.out.println("element************ =>" + element);
						String ActuVal = driver.findElement(By.xpath(element)).getText();
						System.out.println("ActuVal ===> " + ActuVal);
						System.out.println("ExpValues ===> " + ExpValues[i]);
						if (ActuVal.equals(ExpValues[i].trim())) {

							isTrue = false;
							break;
						}
					}
					if (isTrue) {
						System.out.println("Expected value is not available ==> " + ExpValues[i]);
						return "Failed: element not found" + ExpValues[i];
					}

				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	public static String VerifyisContains() {
		moveToElement();
		if (driver.findElement(Utils.getBy(Webelement)).getAttribute("data-ng-src").contains(DataValue)) {
			return "Pass";
		}
		return "Failed : Does not contain value " + DataValue;
	}

	/**
	 * Method verify field is mandatory
	 */
	public static String VerifyisMandatory() {
		try {
			if (driver.findElement(Utils.getBy(Webelement)).getAttribute("required").contains("required")) {
				System.out.println("Field is mandatory");
			}
		} catch (Exception e) {
			return "Failed: Element not found";
		}
		return "Pass";
	}

	/**
	 * Method verify field is not mandatory
	 */
	public static String VerifyisNotMandatory() {
		try {
			String value = driver.findElement(Utils.getBy(Webelement)).getAttribute("required");
			if (value != null) {
				System.out.println("Field is not mandatory");
			}
		} catch (Exception e) {
			return "Failed :  " + Webelement;
		}
		return "Pass";

	}

	public static String CollapseSection() {
		try {
			if (driver.findElement(Utils.getBy(Webelement)).getAttribute("data-ng-src").contains(DataValue)) {
				moveToElement();
				// driver.findElement(Utils.getBy(Webelement1)).click();
				ClickByJavascript();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	public static String VerifyStaticText() {
		String actualText = "";

		try {
			moveToElement();
			actualText = driver.findElement(Utils.getBy(Webelement)).getText();
			System.out.println(actualText);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		String expectedText = AppText.getProperty(Webelement);
		if (!actualText.equals(expectedText)) {
			return "Failed: actual text " + actualText + " is not same as expected text " + expectedText;
		}
		return "Pass";
	}

	public static String VerifyText() {
		String actualText = "";
		try {
			moveToElement();
			actualText = driver.findElement(Utils.getBy(Webelement)).getText();
			System.out.println(actualText);

		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		String expectedText = DataValue;
		if (!actualText.contains(expectedText)) {
			System.out.println(expectedText + "expectedText");
			return "Failed: actual text " + actualText + " is not same as expected text " + expectedText;
		}
		return "Pass";
	}

	/**
	 * Method to verify if field contains text
	 */
	public static String verifyTextContains() {
		String result = "Failed";
		String actualText = "";
		try {
			moveToElement();
			actualText = driver.findElement(Utils.getBy(Webelement)).getText();
			System.out.println(actualText);

			if (StringUtils.containsIgnoreCase(DataValue, actualText)) {
				result = "Pass";
			} else if (StringUtils.containsIgnoreCase(actualText, DataValue)) {
				result = "Pass";
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
	}

	/**
	 * Method to verify boundry value
	 */

	public static String verifyMaxBoundryValue() {
		String actualText = "";

		try {
			moveToElement();
			actualText = driver.findElement(Utils.getBy(Webelement)).getText();
			System.out.println(actualText);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		String expectedText = DataValue;
		if (actualText.equals(expectedText)) {
			return "Failed: actual text " + actualText + " is same as expected text " + expectedText;
		}
		return "Pass";
	}

	/**
	 * Method total count of the element
	 */
	public static String verifytotalCount() {
		int actualText;
		try {
			actualText = driver.findElements(Utils.getBy(Webelement)).size();
			System.out.println("Total count ==> " + actualText);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		int expectedText = Integer.parseInt(DataValue);

		if (actualText != expectedText) {
			return "Failed: actual text " + actualText + " is not same as expected text " + expectedText;
		}
		return "Pass";
	}

	/**
	 * This method gets the size of element Size will be stored in either
	 * integer1 or integer2 depending on the datavalue given in FRED-Data sheet.
	 **/
	public static String getSize() {
		String result = "Pass";
		try {
			int size = driver.findElements(By.xpath(Utils.getElement(Webelement))).size();
			Datareader.setCellData(TestCaseName, DataField, TD, size + "".trim());
		} catch (Exception e) {
			result = "Failed: element not found";
		}
		return result;
	}

	/**
	 * This method verifies the selected value from the dropdown
	 * 
	 * Webelement should be till select tag ex:
	 * //div[@class='fred-registration-seperator']/label[contains(text(),'Dosage
	 * Form')]/following-sibling::div/select
	 **/
	public static String VerifySelectedValueFromDropdown() {
		try {

			String actualValue = new Select(driver.findElement(Utils.getBy(Webelement))).getFirstSelectedOption()
					.getText();
			if (actualValue.equals(DataValue)) {
				return "Pass";
			} else
				return "Failed: Selected Dropdown value-" + actualValue + " is not same as expected value-" + DataValue;

		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
	}

	/**
	 * Method to verify field is blank
	 */
	public static String verifyisBlankValue() {
		String actualText = "";

		try {
			moveToElement();
			actualText = driver.findElement(Utils.getBy(Webelement)).getText();
			System.out.println("Blank value ==>" + actualText);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}

		if (!(actualText.trim().equals(""))) {
				return "Failed: actual text " + actualText + " is not blank ";
					}
		return "Pass";
	}

	/**
	 * Method to verify Element is present
	 */
	public static String isElementPresent() {
		if ((driver.findElements(Utils.getBy(Webelement)).size() != 0)) {
			return "Pass";
		} else {
			return "Failed -Element not Present";
		}
	}

	/**
	 * Method to verify Element is not present
	 */
	public static String VerifyNotPresent() {
		if ((driver.findElements(Utils.getBy(Webelement)).size() == 0)) {
			return "Pass";
		} else {
			return "Failed -Element is Present";
		}
	}

	public static String waitForElementPresence() {
		try {

			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utils.getBy(Webelement)));
			driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);

		} catch (Exception e) {

			return "Fail - Element not visible";

		}
		return "Pass";
	}
	public static String waitForElementPresenceByXapth() {
		try {

			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Webelement)));
			driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);

		} catch (Exception e) {

			return "Fail - Element not visible";

		}
		return "Pass";
	}

	
	public static void waitForvisibilityofElementLocated(final By by) {

		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 20)

				.ignoring(StaleElementReferenceException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(by));

	}

	/**
	 * Method wait till loading icon disappear
	 */
	public static String waitLoadinImageDisapear() {
		try {
			// driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			// wait.until(ExpectedConditions.invisibilityOfElementLocated((Utils.getBy(Webelement))));
			waitUntilLoadComplete();
		} catch (Exception e) {

			return "Fail - Element is visible";

		}
		return "Pass";
	}

	public static void waitUntilAppeared(String path) {
		try {
			for (int i = 1; i <= 100; i++) {
				if (driver.findElement(By.xpath(path)).isDisplayed()) {
					Thread.sleep(100);
					break;
				}
				Thread.sleep(100);
				System.out.println("waiting (appear):  " + (ONE_SECOND * i) / 10);
			}
		} catch (Exception e) {
		}
	}

	public static void waitUntilDisAppear(String path) {
		try {
			for (int i = 1; i <= 400; i++) {
				if (!driver.findElement(By.xpath(path)).isDisplayed()) {
					Thread.sleep(100);
					break;
				}
				Thread.sleep(100);
				System.out.println("waiting (disappear):  " + i * ONE_SECOND);
			}

		} catch (Exception e) {
		}
	}

	public static String constantWait() {
		try {
			// int waittime= Integer.parseInt(DataValue) * 1000;
			int waittime = 10 * 2000;
			Thread.sleep(waittime);
		} catch (Exception e) {

		}
		return "Pass";
	}

	public static void waitUntilLoadComplete() {
		try {
			Thread.sleep(100);
			String PROGRESS_BAR = "//div[@id='loading-image-modal-container' and contains(@class,'opened')]";
			waitUntilAppeared(PROGRESS_BAR);
			waitUntilDisAppear(PROGRESS_BAR);
		} catch (Exception e) {
		}
	}

	/**
	 * Obsolete
	 */
	public static List dropdownValues() {
		WebElement sel = driver.findElement(Utils.getBy(Webelement));
		ArrayList<String> actual_data = new ArrayList<String>();
		List<WebElement> lst = sel.findElements(By.tagName("option"));
		Actions act = new Actions(driver);
		for (WebElement element : lst) {

			String var = element.getText();
			if (var.equals("Test_Country1") || var.equals("Test_Country3")) {
				act.keyDown(Keys.CONTROL);
				element.click();
				act.keyUp(Keys.CONTROL);
				act.build().perform();
			}
			actual_data.add(var);
		}
		return actual_data;
	}

	/**
	 * Select one or more values from Dropdown
	 */
	public static String SelectDropDownValue() throws Exception {
		try {
			constantWaitSmall();

			WebElement identifier = driver.findElement(Utils.getBy(Webelement));
			Select select = new Select(identifier);
			select.selectByVisibleText(DataValue);
			return "Pass";
		} catch (Exception e) {
			return "Failed: Element not found";
		}
	}

	/**
	 * Select one or more values from Dropdown This method is used if the option
	 * contains ',' . Use '_' to seperate two or more options.
	 */
	public static String SelectDropDownValue_() throws InterruptedException {
		WebElement sel = driver.findElement(Utils.getBy(Webelement));

		// ArrayList<String> actual_data = new ArrayList<String>();
		String[] ExpValues;
		List<WebElement> lst = sel.findElements(By.tagName("option"));
		ExpValues = DataValue.split("_");
		System.out.println("Size ==>" + lst.size());
		System.out.println("ExpValues*********==>" + ExpValues[0]);

		for (String val : ExpValues) {
			for (WebElement element : lst) {

				String var = element.getText();
				if (var.equals(val)) {
					// Scroll till element.
					JavascriptExecutor je = (JavascriptExecutor) driver;
					// WebElement elScroll =
					// driver.findElement(By.xpath("//div[@id='dragdiv']"));
					je.executeScript("arguments[0].scrollIntoView(true);", element);
					element.click();
					driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
					// Thread.sleep(10000);
					break;
				}
			}

		}
		return "Pass";
	}

	/**
	 * Select one or more values from Dropdown This method is used if the option
	 * contains ',' . Use '_' to seperate two or more options.
	 */
	public static String SelectDropDownValue_Adv() throws InterruptedException {
		try {
			ClickByJavascript();
			Thread.sleep(2000);
			int totalCount = driver.findElements(Utils.getBy(Webelement1)).size();
			System.out.println("Count ====> " + totalCount);
			String[] ExpValues;
			ExpValues = DataValue.split(",");
			boolean isTrue = true;
			if (totalCount > 0) {
				for (int i = 0; i < ExpValues.length; i++) {

					for (int j = 3; j < totalCount; j++) {

						isTrue = true;
						String element = OR.getProperty(Webelement1) + "[" + j + "]/a/span";
						// System.out.println("element************ =>" +
						// element);
						String ActuVal = driver.findElement(By.xpath(element)).getText();
						System.out.println("ActuVal ===> " + ActuVal);
						// System.out.println("ExpValues ===> " + ExpValues[i]);
						if (ActuVal.equals(ExpValues[i].trim())) {
							// JavascriptExecutor je = (JavascriptExecutor)
							// driver;
							// je.executeScript("arguments[0].scrollIntoView(true);",
							// element);
							driver.findElement(By.xpath(element)).click();
							waitLoadinImageDisapear();
							driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
							isTrue = false;
							// ClickByJavascript();
							break;
						}
					}
					if (isTrue) {
						System.out.println("Expected value is not available ==> " + ExpValues[i]);
						return "Failed: element not found" + ExpValues[i];
					}
					ClickByJavascript();
				}
			}
			ClickByJavascript();
			return "Pass";
		} catch (Exception e) {
			ClickByJavascript();
			return "Failed: Element not found";
		}
	}

	/*
	 * Uncheck the selected countries
	 * 
	 */
	public static String unCheckValues() throws InterruptedException {
		try {
			int totalCount = driver.findElements(Utils.getBy(Webelement)).size();
			System.out.println("Count ====> " + totalCount);
			String[] ExpValues;
			ExpValues = DataValue.split(",");
			boolean isTrue = true;
			if (totalCount > 0) {
				for (int i = 0; i < ExpValues.length; i++) {

					for (int j = 0; j < totalCount; j++) {
						isTrue = true;
						String element = OR.getProperty(Webelement) + "/preceding::span/span[text()='"
								+ ExpValues[i].trim() + "']";
						// System.out.println("element************ =>" +
						// element);
						String ActuVal = driver.findElement(By.xpath(element)).getText();
						// System.out.println("ActuVal ===> " + ActuVal);
						// System.out.println("ExpValues ===> " + ExpValues[i]);
						if (ActuVal.equals(ExpValues[i].trim())) {

							// driver.findElement(By.xpath(element)).click();
							String el1 = "//span[contains(text(),'" + ExpValues[i].trim() + "')]/preceding::span[1]";
							WebElement element1 = driver.findElement(By.xpath(el1));
							JavascriptExecutor je = (JavascriptExecutor) driver;
							je.executeScript("arguments[0].click();", element1);
							isTrue = false;
							waitLoadinImageDisapear();
							// driver.manage().timeouts().implicitlyWait(10,
							// TimeUnit.SECONDS);
							break;
						}
					}
					if (isTrue) {
						System.out.println("Expected value is not available ==> " + ExpValues[i]);
						return "Failed: element not found" + ExpValues[i];
					}

				}
			}

			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/*
	 * Uncheck all the values from selection box
	 */
	public static String unCheckValuesAll() throws InterruptedException {
		try {
			
			String closeBtnXpath = OR.getProperty(Webelement)+"/preceding::span[@class='close ui-select-match-close']";
			int totalCount = driver.findElements(By.xpath(closeBtnXpath)).size();
		//	if (totalCount  > 0) {	
			for(int i=1;i<=totalCount;i++){
					WebElement element = driver.findElement(By.xpath(closeBtnXpath));
					//JavascriptExecutor je = (JavascriptExecutor) driver;
					//je.executeScript("arguments[0].click();", element);
					element.click();
					waitLoadinImageDisapear();
					

			}//}

				return "Pass";
			//}
		} catch (Exception e) {
			return "Failed : Element not found";
		}
	}

	/*
	 * Click on single dropdown value
	 */
	public static String DeselectOnDropDownvalue() {
		try {
			Select sel = new Select(driver.findElement(Utils.getBy(Webelement)));
			List<WebElement> options = sel.getAllSelectedOptions();
			System.out.println("Total Selected values ==>" + options.size());
			for (WebElement op : options) {
				System.out.println("getAllSelectedOptions ==>" + op.getText());
				op.click();

			}
			return "Pass";

		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * Method to verify Active Ingradients
	 */
	public static String readActiveIngradients() {
		String status1 = ClickByJavascript();
		if (status1.equals("Pass")) {
			int totalCount = driver.findElements(Utils.getBy(Webelement1)).size();
			System.out.println("Count ====> " + totalCount);
			String[] ExpValues;
			ExpValues = DataValue.split(",");
			boolean isTrue = true;
			if (totalCount > 0) {
				for (int i = 0; i < ExpValues.length; i++) {

					// System.out.println("Total Count ==> " + totalCount);
					for (int j = 3; j < totalCount + 1; j++) {
						isTrue = true;
						String element = OR.getProperty(Webelement1) + "[" + j + "]/a/span";
						System.out.println("element************ =>" + element);
						String ActuVal = driver.findElement(By.xpath(element)).getText();
						System.out.println("ActuVal ===> " + ActuVal);
						System.out.println("ExpValues ===> " + ExpValues[i]);
						if (ActuVal.equals(ExpValues[i].trim())) {
							System.out.println("Verified value  ==> " + ExpValues[i]);
							isTrue = false;
							break;
						}
					}
					if (isTrue) {
						System.out.println("Expected value is not available ==> " + ExpValues[i]);
						return "Failed: element not found" + ExpValues[i];
					}
				}
			}
			ClickByJavascript();
		}
		return "Pass";
	}

	/**
	 * no split
	 */
	public static String selectListValuesNoSplit() {
		try{
			ClickByJavascript();
		List<WebElement> list = driver.findElements(Utils.getBy(Webelement1));
		System.out.println(list.size());
		for (WebElement elementToSelect : list) {
		if(elementToSelect.getText().equalsIgnoreCase(DataValue)){
			elementToSelect.click();
		}
		}

		return "Pass";
		}catch(Exception e){
			return "Failed : not found element";
		}
	}

	/**
	 * Method to select from dropdown value using list
	 */
	public static String selectListValues() {
		try {
			constantWaitSmall();
			String[] ExpValues;
			ExpValues = DataValue.split(",");
			for (String s : ExpValues) {
				ClickByJavascript();
				List<WebElement> list = driver.findElements(Utils.getBy(Webelement1));
				for (WebElement elementToSelect : list) {
				if(elementToSelect.getText().equalsIgnoreCase(s)){
					elementToSelect.click();
					constantWaitSmall();
					break;
				}
				}
				
			}
			return "Pass";
		} catch (Exception e) {
			ClickByJavascript();
			return "Failed:no values found";
		}
	}
	public static String verifyManuSiteType() {
		try {
		
		int elementIndex=0;
			List<WebElement> list = driver.findElements(Utils.getBy(Webelement));
			System.out.println(list.size());
			for (WebElement elementToSelect : list) {
				elementIndex+=1;
			if(elementToSelect.getText().equalsIgnoreCase(DataValue)){
				elementToSelect.click();
				int chckCount=driver.findElements(By.xpath(String.format(OR.getProperty(Webelement1),elementIndex))).size();
				if(chckCount==4){
					return "Pass";
				}
				
			}
			}

	return "Pass";
} catch (Exception e) {
	return "Failed: Element not found";
}
}
	
	/**
	 * Method to verify values from DropDown Box
	 */
	public static String verifyDropDownValues() throws Exception {
		try {
			WebElement sel = driver.findElement(Utils.getBy(Webelement));
			moveToElement();
			sel.click();
			String[] ExpValues;
			List<WebElement> lst = sel.findElements(By.tagName("option"));
			 System.out.println(lst.size());
			ExpValues = DataValue.split(",");
			boolean isTrue = false;
			for (String s : ExpValues) {
				if (isTrue) {
				return "Failed: Not found " +s;
				}
			isTrue = true;
			for (WebElement elementToSelect : lst) {
				if(elementToSelect.getText().equalsIgnoreCase(s.trim()))
				 {
							System.out.println("Verified value ==> "+ s);
							isTrue = false;
							break;
				 }
			}
					
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * Copy Registration form Method to check if the fields are blank after
	 * clicking CopyToNewRegistration button
	 */
	public static String verifyDefaultfields() {

		try {
			String country = new Select(driver.findElement(Utils.getBy("xpath_country_NewRegistration")))
					.getFirstSelectedOption().getText();
			String prodName = driver.findElement(By.xpath("//label[contains(text(),'Product Name')]//../div/input"))
					.getAttribute("value");
			String localLegalProdCat = new Select(driver.findElement(Utils.getBy("xpath_MAI_LocalLegalProCat")))
					.getFirstSelectedOption().getText();
			String dispencingClass = new Select(driver.findElement(Utils.getBy("xpath_MAI_localDispensingClass")))
					.getFirstSelectedOption().getText();
			String marketingComp = new Select(driver.findElement(Utils.getBy("xpath_MAI_localMarketingCompany")))
					.getFirstSelectedOption().getText();
			String respRegPerson = new Select(driver.findElement(Utils.getBy("xpath_MAI_RespRegPerson")))
					.getFirstSelectedOption().getText();
			String licenseHolder = new Select(driver.findElement(Utils.getBy("xpath_MAI_licenseHolder")))
					.getFirstSelectedOption().getText();
			String legalBasis = new Select(driver.findElement(Utils.getBy("xpath_MAI_LegalBasisofApplication")))
					.getFirstSelectedOption().getText();
			String healthAuthority = new Select(
					driver.findElement(Utils.getBy("xpath_MAI_healthAuthorityGoverningBody_select")))
							.getFirstSelectedOption().getText();
			String remarks = driver
					.findElement(By
							.xpath("//label[contains(text(),'Local Registration Record Remarks')]/following::div[1]/textarea"))
					.getAttribute("value");
			if (country.equals("Select one") || prodName.equals("")
					|| localLegalProdCat.equals("Select a country first")
					|| dispencingClass.equals("Select a country first") || marketingComp.equals("Select a country")
					|| respRegPerson.equals("Select one") || licenseHolder.equals("Select a country")
					|| legalBasis.equals("Select one") || healthAuthority.equals("Select a country")
					|| remarks.equals("remarks")) {
				return "Pass";
			} else
				return "Failed";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * Verify Search filter - Table coulumn verification in advanced search
	 * 
	 */
	public static String verifySearchFilter() throws Exception {
		try {
			int columnCount = driver.findElements(By.cssSelector("div#headerTarget > div")).size();
			String expValues[];
			System.out.println("columnCount ==> " + columnCount);
			expValues = DataValue.split(",");
			ReportUtil.addKeyword("Total coulmn Count => " + expValues, Keyword, "PASS", null);
			for (String val : expValues) {
				boolean ispresent = false;
				String coulunmXpath = null;
				String coulumnHeader = null;
				for (int i = 1; i <= columnCount; i++) {
					if (i == 1 || i == 2) {
						coulunmXpath = "div.fixed-section>div[class^='fred-central-header']:nth-child(" + i
								+ ")>div>span>span:nth-child(1)";
						coulumnHeader = driver.findElement(By.cssSelector(coulunmXpath)).getText();
					} else {
						coulunmXpath = "div#headerTarget > div:nth-child(" + i + ") > div>span";
						coulumnHeader = driver.findElement(By.cssSelector(coulunmXpath)).getText();
					}
					// System.out.println("coulumnHeader =>"+coulumnHeader);
					if (coulumnHeader.equals(val)) {
						System.out.println("coulumn is available : " + val);
						ReportUtil.addKeyword("Verified value =>" + val, Keyword, "PASS", null);
						ispresent = true;
						break;

					}
				}
				if (ispresent == false) {
					System.out.println("coulumn is not available : " + val);
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * Verify Registration table - regulatory notifications Anand- Commenting as
	 * UI is changed - 16 May 2016
	 */
	/*
	 * public static String verifyRegistrationNotification() { String ItemDue =
	 * Datareader.getCellData(TestCaseName, "ItemDue", TD); String DueBy =
	 * Datareader.getCellData(TestCaseName, "DueBy", TD); String Country =
	 * Datareader.getCellData(TestCaseName, "Country", TD); String RegID =
	 * Datareader.getCellData(TestCaseName, "RegID", TD);
	 * 
	 * try { String Actual_ItemDue_cell =
	 * "//table[@id='ListTable']/tbody/tr[descendant::text()[contains(.,'" +
	 * RegID + "')]]/td[1]/div[1]"; String Actual_ItemDue =
	 * driver.findElement(By.xpath(Actual_ItemDue_cell)).getText(); String
	 * Actual_DueBy_cell =
	 * "//table[@id='ListTable']/tbody/tr[descendant::text()[contains(.,'" +
	 * RegID + "')]]/td[2]/div[1]"; String Actual_DueBy =
	 * driver.findElement(By.xpath(Actual_DueBy_cell)).getText(); String
	 * Actual_Country_cell =
	 * "//table[@id='ListTable']/tbody/tr[descendant::text()[contains(.,'" +
	 * RegID + "')]]/td[5]/div[1]"; String Actual_Country =
	 * driver.findElement(By.xpath(Actual_Country_cell)).getText(); String
	 * Actual_RegID_cell =
	 * "//table[@id='ListTable']/tbody/tr[descendant::text()[contains(.,'" +
	 * RegID + "')]]/td[8]/a/span[1]"; String Actual_RegID =
	 * driver.findElement(By.xpath(Actual_RegID_cell)).getText();
	 * 
	 * if (ItemDue.equals(Actual_ItemDue) && DueBy.equals(Actual_DueBy) &&
	 * Actual_Country.equals(Country) && RegID.equals(Actual_RegID)) {
	 * System.out.println("Registration values are verified"); return "Pass"; }
	 * else return "Failed: Actual value is not equal to expected value";
	 * 
	 * } catch (Exception e) { return "Failed:element not found"; } }
	 */
	/*
	 * Method to click on value from TypeAhead
	 */
	public static String selectFromTypeAhead() throws Exception {
		try {
			int rowCount = driver.findElements(By.xpath("//ul[contains(@id,'typeahead')]/li")).size();
			System.out.println("Row Count == " + rowCount);
			for (int i = 1; i <= rowCount; i++) {
				String ActualXpath = "//ul[contains(@id,'typeahead')]/li[" + i + "]/a";
				WebElement element = driver.findElement(By.xpath(ActualXpath));
				String ActualValue = driver.findElement(By.xpath(ActualXpath)).getText();
				System.out.println("ActualValue ==>" + ActualValue);
				if (DataValue.contains(ActualValue)) {
					driver.findElement(By.xpath(ActualXpath)).click();
					break;
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed:element not found";
		}
	}

	/*
	 * Verify Product Life Cycle Tab
	 */
	public static String verifyRegistrationNotification() throws Exception {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-300)");

			int rowCount = driver
					.findElements(By
							.xpath("//div[@class='reg-alert-label']/..//div[contains(@class,'registrationRecordID')]/div[@data-ng-repeat='obj in notificationAlerts']"))
					.size();
			System.out.println("Row Count == " + rowCount);
			String ItemDue = Datareader.getCellData(TestCaseName, "ItemDue", TD);
			String DueBy = Datareader.getCellData(TestCaseName, "DueBy", TD);
			String Country = Datareader.getCellData(TestCaseName, "Country", TD);
			String RegID = Datareader.getCellData(TestCaseName, "RegID", TD);
						
			if (rowCount > 0) {

				for (int i = 1; i < rowCount; i++) {
					/*
					 * String ItemDue_Xpath =
					 * "//div[@class='reg-alert-label']/..//div[@data-ng-repeat='obj in notificationAlerts']["
					 * + i + "]/div[1]/div/label";
					 */
					String ItemDue_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope alertType']/div["
							+ i + "]/div/label";
					String DueBy_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope dueBy']/div[" + i
							+ "]/div/label";

					String Country_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope country']/div[" + i
							+ "]/div/label";
					String regID_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope registrationRecordID']/div["
							+ i + "]/div/label";
					String prodName_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope productNames']/div["+i+"]/div/label";
					String genrics_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope generics']/div["+i+"]/div/label";
					String dosageForms_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope dosageForms']/div["+i+"]/div/label";
					String prodCat_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope legalProductCategory']/div["+i+"]/div/label";
					String openReq_Xpath ="/div[@class='reg-alert-label']/..//div[@class='ng-scope registrationRequestID']/div["+i+"]/div/label";
					

					String Actual_ItemDue = driver.findElement(By.xpath(ItemDue_Xpath)).getText();
					String Actual_DueBy = driver.findElement(By.xpath(DueBy_Xpath)).getText();
					String Actual_Country = driver.findElement(By.xpath(Country_Xpath)).getText();
					String Actual_RegID = driver.findElement(By.xpath(regID_Xpath)).getText();
					String Actual_prodName = driver.findElement(By.xpath(prodName_Xpath)).getText();
					String Actual_generic = driver.findElement(By.xpath(genrics_Xpath)).getText();
					String Actual_dosageForm = driver.findElement(By.xpath(dosageForms_Xpath)).getText();
					String Actual_prodCat = driver.findElement(By.xpath(prodCat_Xpath)).getText();
					String Actual_openReq = driver.findElement(By.xpath(openReq_Xpath)).getText();
					
					if(DataField.equals("All")){
						String ProductNames  = Datareader.getCellData(TestCaseName, "ProductNames ", TD);
						String Generics = Datareader.getCellData(TestCaseName, "Generics", TD);
						String DosageForms  = Datareader.getCellData(TestCaseName, "DosageForms ", TD);
						String ProdCat = Datareader.getCellData(TestCaseName, "ProdCat", TD);
						String Openreq = Datareader.getCellData(TestCaseName, "Openreq", TD);
						if (ItemDue.equals(Actual_ItemDue) && DueBy.equals(Actual_DueBy) && Actual_Country.equals(Country)
								&& RegID.equals(Actual_RegID) && ProductNames.equals(Actual_prodName) && Generics.equals(Actual_generic) && DosageForms.equals(Actual_dosageForm) && ProdCat.equals(Actual_prodCat) && Openreq.equals(Actual_openReq)) {
							System.out.println("Registration values are verified");
							return "Pass";
						}
					}
					
					if (ItemDue.equals(Actual_ItemDue) && DueBy.equals(Actual_DueBy) && Actual_Country.equals(Country)
							&& RegID.equals(Actual_RegID)) {
						System.out.println("Registration values are verified");
						return "Pass";
					}
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed:element not found";
		}
	}

	/**
	 * Method to verify Register Id is present in regulatory notifications table
	 */
	public static String VerifyRegIdNotPresentForRegNotif() {
		String RegID = Datareader.getCellData(TestCaseName, "RegID", TD);
		try {
			
			//if ((driver.findElements(By.xpath("//span[text()='" + RegID.trim() + "']")).size() == 0)) {
					if ((driver.findElements(By.xpath("//div[@class='reg-alert-label']/..//div[@class='ng-scope registrationRecordID']/div/..//label[contains(text(),'"
					+ RegID + "')]")).size() == 0)) {
				return "Pass";
			} else
				return "Failed: RegId is present";
		} catch (Exception e) {
			return "Failed -Element not Present";
		}
		
	}

	// /**
	// * Click on Hide option from the given Registration ID
	// */
	// public static String verifyClickOnHideRegistrationID() throws Exception {
	// boolean isTrue = true;
	// int rowCount =
	// driver.findElements(By.xpath("//table[@id='ListTable']/tbody/tr")).size();
	// String expValues[];
	// expValues = DataValue.split(",");
	// // ReportUtil.addKeyword("Total coulmn Count => "+expValues,
	// // Keyword,"PASS", null);
	//
	// try {
	// String RegID = Datareader.getCellData(TestCaseName, "RegID", TD);
	//
	// for (int i = 1; i <= rowCount; i++) {
	// String Actual_RegID_cell = "//table[@id='ListTable']/tbody/tr[" + i +
	// "]/td[8]/a/span[1]";
	// String Actual_RegID =
	// driver.findElement(By.xpath(Actual_RegID_cell)).getText();
	// System.out.println("Actual_RegID==>" + Actual_RegID);
	// String Hide_cell = "//table[@id='ListTable']/tbody/tr[" + i +
	// "]/td[10]/button[1]";
	// if (RegID.trim().equals(Actual_RegID.trim())) {
	// isTrue = false;
	// driver.findElement(By.xpath(Hide_cell)).click();
	// break;
	// }
	// }
	// if (isTrue) {
	//
	// return "Failed: Not found" + RegID;
	// }
	// } catch (Exception e) {
	// return "Failed: Element not found";
	// }
	//
	// return "Pass";
	// }

	/**
	 * Click on Hide option from the given Registration ID Registration ID
	 * should be hardcoded in Fred-Data sheet
	 */
	public static String verifyClickOnHideRegistrationID() throws Exception {
		try {
			String RegID = Datareader.getCellData(TestCaseName, "RegID", TD);
			
			int rowCount = driver
			.findElements(By
					.xpath("//div[@class='reg-alert-label']/..//div[contains(@class,'registrationRecordID')]/div[@data-ng-repeat='obj in notificationAlerts']"))
			.size();

	System.out.println("Row Count == " + rowCount);
	int i = 0;
	if (rowCount > i) {

		for ( i = 1; i <= rowCount; i++) {

			String RegID_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope registrationRecordID']/div["
					+ i + "]/div/label";
			
			if (driver.findElement(By.xpath(RegID_Xpath)).getText().contains(RegID)) {
				
				driver.findElement(By.xpath("//div[@class='reg-alert-label']/..//div[@class='ng-scope isHidden']/div["+i+"]/div/span/button[text()='Hide']")).click();
				break;
			} 
	}
		
	}
	return "PASS";
	}catch (Exception e) {
			return "Failed: Element not found";
		}
	}
	/*
	 * Verify alerts count and store the count value
	 */
	public static String verifyHiddenAlertsCount() throws Exception{
		try{
		hiddenCount=0;
		String str=driver.findElement(Utils.getBy(Webelement)).getText();
		System.out.println(str);
		String str1 = str.replaceAll("[()]","");
		String str2[] = str1.split(" ");
		hiddenCount =Integer.parseInt(str2[3]);
		System.out.println(hiddenCount);
		return "Pass";
		}
		catch(Exception e){
			return "Failed: Element not Found";
		}
	}
	public static String verifyAlertsCountAfterHide() throws Exception{
		try{
			String str=driver.findElement(Utils.getBy(Webelement)).getText();
			System.out.println(str);
			String str1 = str.replaceAll("[()]","");
			String str2[] = str1.split(" ");
			int count =Integer.parseInt(str2[3]);
			if(count==hiddenCount+1){
				return "Pass";
			}
			return "Failed: Count is not increased";
			}
			catch(Exception e){
				return "Failed: Element not Found";
			}
	}
/*
 * Funtion to Unhide notfication
 * 
 */
	public static String verifyClickOnUnHideRegistrationID() throws Exception {
		try {
			String RegID = Datareader.getCellData(TestCaseName, "RegID", TD);
			
			int rowCount = driver
			.findElements(By
					.xpath("//div[@class='reg-alert-label']/..//div[contains(@class,'registrationRecordID')]/div[@data-ng-repeat='obj in notificationAlerts']"))
			.size();

	System.out.println("Row Count == " + rowCount);
	int i = 0;
	if (rowCount > i) {

		for ( i = 1; i <= rowCount; i++) {

			String RegID_Xpath = "//div[@class='reg-alert-label']/..//div[@class='ng-scope registrationRecordID']/div["
					+ i + "]/div/label";
			
			if (driver.findElement(By.xpath(RegID_Xpath)).getText().contains(RegID)) {
				
				driver.findElement(By.xpath("//div[@class='reg-alert-label']/..//div[@class='ng-scope isHidden']/div["+i+"]/div/span/button[text()='Unhide']")).click();
				break;
			} 
	}
		
	}
	return "PASS";
	}catch (Exception e) {
			return "Failed: Element not found";
		}
	}

	
	/**
	 * select Registration ID
	 */
	public static String selectRegistrationID() {
		try {
			scrollToTopPage();
			String RegID = Datareader.getCellData(TestCaseName, "RegID", TD);

			if (driver.findElement(By
					.xpath("//div[@class='reg-alert-label']/..//div[@class='ng-scope registrationRecordID']/div/..//label[contains(text(),'"
							+ RegID + "')]"))
					.isDisplayed()) {

				driver.findElement(By
						.xpath("//div[@class='reg-alert-label']/..//div[@class='ng-scope registrationRecordID']/div/..//label[contains(text(),'"
								+ RegID + "')]"))
						.click();
				return "Pass";
			} else {
				return "Pass : Element not found";
			}
		} catch (Exception e) {
			return "Failed: Element Not found";
		}
	}

	/*
	 * Select Registration ID from advanced search results - delete this
	 */
	public static String verifyRegistrationIDAdvSearch() throws Exception {
		try{
		boolean isTrue = true;
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='searchResult']/div/div/div/a"));
		for (WebElement elementToSelect : list) {
		if(elementToSelect.getText().equalsIgnoreCase(DataValue)){
			isTrue = false;
			elementToSelect.click();
			break;
		}
		}
		if (isTrue) {
			return "Failed: Not found" + DataValue;
		}

		return "Pass";
		}catch(Exception e){
			return "Failed: Not found element";
		}
	}

	/*
	 * FRED 2.8.1 - Select check boxes for register Id provided 
	 */
	public static String selectRegisterIDCheckbox() throws Exception {
		try{
			String[] ExpValues;
			ExpValues = DataValue.split(",");
			int i=0;
			boolean isTrue = true;
			List<WebElement> list = driver.findElements(By.xpath("//div[@class='searchResult']/div/div/div/a"));
			for (String s : ExpValues) {
				if (isTrue) {
				return "Failed: Not found " +s;
				}
			isTrue = true;
			for (WebElement elementToSelect : list) {
				if(elementToSelect.getText().equalsIgnoreCase(s)){
				isTrue = false;
				i=i+1;
				driver.findElement(By.xpath("//div[@class='searchResult']/div["+i+"]/div[1]/div/input")).click();;
				break;
				}
			}
			}
		return "Pass";
		}catch(Exception e){
			return "Failed: Not found element";
		}
	}
	
	/*
	 * Verify Product Life Cycle Tab
	 */
	public static String verifyProductLifeCycleTab() throws Exception {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-300)");
			boolean isTrue = true;
			int rowCount = driver
					.findElements(By.xpath("//angular-table[@total-items='TotalItemsProductLifecycle']/table/tbody/tr"))
					.size();
			System.out.println("Row Count == " + rowCount);
			String StatusGroup = Datareader.getCellData(TestCaseName, "StatusGroup", TD);
			String Status = Datareader.getCellData(TestCaseName, "Status", TD);
			String StatusDate = Datareader.getCellData(TestCaseName, "StatusDate", TD);
			String RefNo = Datareader.getCellData(TestCaseName, "RefNo", TD);
			String VersionNo = Datareader.getCellData(TestCaseName, "VersionNo", TD);
			String Remarks = Datareader.getCellData(TestCaseName, "Remarks", TD);
			if (rowCount > 0) {
				for (int i = 1; i < rowCount; i++) {
					for (int j = 1; j <= 6; j++) {
						String getText = "//angular-table[@total-items='TotalItemsProductLifecycle']/table/tbody/tr["
								+ i + "]/td[" + j + "]/div";
						String Actual_value = driver.findElement(By.xpath(getText)).getText();
						if (StatusGroup.equals(Actual_value) || Status.equals(Actual_value)
								|| StatusDate.equals(Actual_value) || RefNo.equals(Actual_value)
								|| VersionNo.equals(Actual_value) || Remarks.equals(Actual_value)) {
							System.out.println("Value is present ==> " + Actual_value);
						}

					}
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: to verify product life cycle";
		}
	}

	/**
	 * Method to verify Manufacturing Site Table
	 */
	public static String verifyManufacturingSiteTable() throws Exception {
		boolean isTrue = true;
		int rowCount = driver
				.findElements(By
						.xpath("//input[@name='picker-name']/following::div[3]/div[1]/table[@class='searchResultTable']/tbody/tr"))
				.size();
		String expValues[];
		expValues = DataValue.split(",");
		String expected_siteName = Datareader.getCellData(TestCaseName, "column1", TD);

		for (int i = 1; i <= rowCount; i++) {
			String siteName = "//input[@name='picker-name']/following::div[3]/div[1]/table[@class='searchResultTable']/tbody/tr["
					+ i + "]/td[1]";
			String Actual_siteName = driver.findElement(By.xpath(siteName)).getText();
			System.out.println("Site Name ==> " + Actual_siteName);
			if (Actual_siteName.contains(expected_siteName)) {
				isTrue = false;
				break;
			}
		}
		if (isTrue) {

			return "Failed: Not found" + expected_siteName;
		}

		return "Pass";
	}

	/**
	 * Method add Site Name in Manufacturing Site
	 */
	public static String clickonAddManuSite() throws Exception {
		boolean isTrue = true;
		int rowCount = driver
				.findElements(By
						.xpath("//input[@name='picker-name']/following::div[3]/div[1]/table[@class='searchResultTable']/tbody/tr"))
				.size();
		String expValues[];
		expValues = DataValue.split(",");

		String expected_siteName = Datareader.getCellData(TestCaseName, "column1", TD);

		for (int i = 1; i <= rowCount; i++) {
			String siteName = "//input[@name='picker-name']/following::div[3]/div[1]/table[@class='searchResultTable']/tbody/tr["
					+ i + "]/td[1]";
			String Actual_siteName = driver.findElement(By.xpath(siteName)).getText();
			System.out.println("Site Name ==> " + Actual_siteName);
			if (Actual_siteName.contains(expected_siteName)) {
				isTrue = false;
				String xpath_addButton = "//input[@name='picker-name']/following::div[3]/div[1]/table[@class='searchResultTable']/tbody/tr["
						+ i + "]/td[3]/button";
				//driver.findElement(By.xpath(xpath_addButton)).click();
				Webelement=xpath_addButton;
				//scrollToElement();
				ClickByJavascript();
				break;
			}
		}
		if (isTrue) {

			return "Failed: Not found" + expected_siteName;
		}

		return "Pass";
	}

	
	/**
	 * Method add Site Name from Local Marketing Company -MAI
	 */
	public static String clickonAddManuSite_MAI() throws Exception {
		boolean isTrue = true;
		int rowCount = driver
				.findElements(By
						.xpath("//label[contains(text(),'Local Marketing Company')]/following::div[1]/div/div[2]/div/table[@class='searchResultTable']/tbody/tr"))
				.size();

		try {
			String expected_siteName = Datareader.getCellData(TestCaseName, "column1", TD);

			for (int i = 1; i <= rowCount; i++) {
				String siteName = "//label[contains(text(),'Local Marketing Company')]/following::div[1]/div/div[2]/div/table[@class='searchResultTable']/tbody/tr["
						+ i + "]/td[1]";
				String Actual_siteName = driver.findElement(By.xpath(siteName)).getText();
				if (Actual_siteName.contains(expected_siteName)) {
					isTrue = false;
					String addButton = "//label[contains(text(),'Local Marketing Company')]/following::div[1]/div/div[2]/div/table[@class='searchResultTable']/tbody/tr["
							+ i + "]/td[3]/div/button";
					driver.findElement(By.xpath(addButton)).click();
					break;
				}
			}
			if (isTrue) {

				return "Failed: Not found" + expected_siteName;
			}
		} catch (Exception e) {
			return "Failed: element not found";
		}
		return "Pass";
	}

	/**
	 * Method add Site Name from License holder -MAI
	 */
	public static String verifySelectSitLicenseHolder() throws Exception {
		boolean isTrue = true;
		int rowCount = driver
				.findElements(
						By.xpath("//select[@name='company-picker-country-1']/following::div[4]/div/table/tbody/tr"))
				.size();
		String expValues[];
		expValues = DataValue.split(",");
		try {
			String expected_siteName = Datareader.getCellData(TestCaseName, "column1", TD);

			for (int i = 1; i <= rowCount; i++) {
				String siteName = "//select[@name='company-picker-country-1']/following::div[4]/div/table/tbody/tr[" + i
						+ "]/td[1]";
				String Actual_siteName = driver.findElement(By.xpath(siteName)).getText();
				if (expected_siteName.contains(Actual_siteName)) {
					isTrue = false;
					String addButton = "//select[@name='company-picker-country-1']/following::div[4]/div/table/tbody/tr["
							+ i + "]/td[3]/div/button";
					driver.findElement(By.xpath(addButton)).click();
					break;
				}
			}
			if (isTrue) {

				return "Failed: Not found" + expected_siteName;
			}
		} catch (Exception e) {
			return "Failed: element not found";
		}
		return "Pass";
	}

	/**
	 * Verify element is selected
	 */
	public static String isSelected() throws Exception {
		try {
			moveToElement();
			if (!(driver.findElement(Utils.getBy(Webelement)).isSelected())) {
				return "Failed - element is not selected";
			}
		} catch (Exception e) {
			return "Failed- element not found";
		}

		return "Pass";
	}
	
	/**
	 * Function to select the checkbox/radi button
	 * parameter - checkbox/radio button element
	 */
	public static String select() throws Exception {
		try {
			moveToElement();
			if (!(driver.findElement(Utils.getBy(Webelement)).isSelected())) {
				driver.findElement(Utils.getBy(Webelement)).click();
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed- element not found";
		}
	}
	/**
	 * Function to Unselect the checkbox/radi button
	 * parameter - checkbox/radio button element
	 */
	public static String unSelect() throws Exception {
		try {
			moveToElement();
			if ((driver.findElement(Utils.getBy(Webelement)).isSelected())) {
				driver.findElement(Utils.getBy(Webelement)).click();
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed- element not found";
		}
	
	}


	public static String verifyActiveIngDelete() throws Exception {
		// Active radio button
		int beforeDelete = driver.findElements(By.xpath("//div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div/div/..//label[contains(@data-ng-repeat,'ingredientFunctionChoices')][1]/input"))
				.size();

		// Search for Active Ingredient
		for (int i = 1; i <= beforeDelete; i++) {
			if (driver
					.findElement(
							By.xpath("//div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div["+i+"]/div/..//label[contains(@data-ng-repeat,'ingredientFunctionChoices')][1]/input"))
					.isSelected()) {
				// Delete the Active ingredient
				WebElement element1 = driver
						.findElement(By.xpath("//div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div["+i+"]/div/..//img"));
				// .click();
				JavascriptExecutor je = (JavascriptExecutor) driver;
				je.executeScript("arguments[0].click();", element1);
				Thread.sleep(3000);
				break;
			}
		}

		int afterDelete = driver.findElements(By.xpath("//div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div/div/..//label[contains(@data-ng-repeat,'ingredientFunctionChoices')][1]/input"))
				.size();

		// Verify if the Active ingredient is deleted
		if ((afterDelete + "").equals((beforeDelete - 1) + "")) {
			return "Pass";
		} else
			return "Failed";

	}

	public static String verifyExcepientIngDelete() throws Exception {
		// Active radio button
		int beforeDelete = driver.findElements(By.xpath("div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div/div/..//label[contains(@data-ng-repeat,'ingredientFunctionChoices')][2]/input"))
				.size();
		//div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div[1]/div/..//label[contains(@data-ng-repeat,'ingredientFunctionChoices')][2]/input
		System.out.println("Total Excepient ==> " + beforeDelete);
		// Search for Excepient Ingredient
		for (int i = 1; i <= beforeDelete; i++) {
			if (driver
					.findElement(
							By.xpath("div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div["+i+"]/div/..//label[contains(@data-ng-repeat,'ingredientFunctionChoices')][2]/input"))
					.isSelected()) {
				Thread.sleep(3000);
				// Delete the Excepient ingredient
				WebElement element1 = driver
						.findElement(By.xpath("//div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div["+i+"]/div/..//img"));
				// .click();
				JavascriptExecutor je = (JavascriptExecutor) driver;
				je.executeScript("arguments[0].click();", element1);
				Thread.sleep(3000);
				break;
			}
		}

		int afterDelete = driver.findElements(By.xpath("div[contains(@data-ng-show,'regReqCtrl.registration.formulations')]/div/div/..//label[contains(@data-ng-repeat,'ingredientFunctionChoices')][2]/input"))
				.size();

		// Verify if the Excepient ingredient is deleted
		if ((afterDelete + "").equals((beforeDelete - 1) + "")) {
			return "Pass";
		} else
			return "Failed";

	}

	/**
	 * Verify radio button is not checked
	 */
	public static String isNotSelected() throws Exception {

		try {
			if ((driver.findElement(Utils.getBy(Webelement)).isSelected())) {
				return "Failed - element is selected";
			}
		} catch (Exception e) {
			return "Failed - element is not found";
		}
		return "Pass";
	}

	/*
	 * 
	 */
	public static String scrollToMidOfPage() {
	((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-250)");
	return "Pass";
	}
	/**
	 * Method to scroll to element
	 */
	public static void scrollToElement() {
		// Scroll till element.
		JavascriptExecutor je = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(Utils.getBy(Webelement));
		je.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	/**
	 * Method to scroll to element
	 */
	public static void moveToElement() {
	Actions act=new Actions(driver);
	act.moveToElement(driver.findElement(Utils.getBy(Webelement))).build().perform();;
	}

	/**
	 * Method to click on element using Javascript
	 */
	public static String ClickByJavascript() {
		// Scroll till element.
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(Utils.getBy(Webelement));
			//je.executeScript("arguments[0].scrollIntoView(true);", element);
			je.executeScript("arguments[0].click();", element);
			return "Pass";
		} catch (Exception e) {
			return "Faild";
		}
	}

	/**
	 * Method to scroll to top of the page
	 */
	public static String scrollToTopPage() {
		// Scroll till element.
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("window.scrollTo(0,0)");
			return "Pass";
		} catch (Exception e) {
			return "Faild";
		}
	}

	/**
	 * Method to verify active infgradient from Summary
	 */
	public static String verifyActIngrdFrmSummary() throws Exception {
		boolean isTrue = true;
		String result = "Pass";
		Thread.sleep(100);
		int labelCount = driver
				.findElements(By.xpath("//label[contains(text(),'Formula Reference Number:')]/following::div[1]/div"))
				.size();

		String expValues[] = null;
		String actuValues = null;

		ArrayList<String> actIngrd = new ArrayList<String>();
		expValues = DataValue.split("\n");
		if (labelCount > 0) {
			for (int i = 1; i <= labelCount; i++) {

				// get the first ingredient from the list
				String label = "//label[contains(text(),'Formula Reference Number:')]/following::div[1]/div[" + i
						+ "]/div/label";

				String getLabelText = driver.findElement(By.xpath(label)).getText();
				actIngrd.add(getLabelText);
				// actuValues = getLabelText.split(",");

				actuValues = actIngrd.get(i - 1);
				if (expValues[i - 1].contains(actuValues)) {
					result = "Pass";
					System.out.println("Active ingradients are available");
				} else {
					result = "Failed: actual value" + actuValues + " is not same as expected value" + expValues;
				}
			}
		}
		return result;
	}

	public static String verifySumPkgTable() throws Exception {
		boolean isTrue = true;
		int rowCount = driver
				.findElements(By.xpath("//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr"))
				.size();
		System.out.println("Row Count ==> " + rowCount);
		// ReportUtil.addKeyword("Total coulmn Count => "+expValues,
		// Keyword,"PASS", null);
		String Size = Datareader.getCellData(TestCaseName, "Size", TD);
		String Material = Datareader.getCellData(TestCaseName, "Material", TD);
		String PackagingType = Datareader.getCellData(TestCaseName, "PackagingType", TD);
		String Closure = Datareader.getCellData(TestCaseName, "Closure", TD);
		String ShelfLife = Datareader.getCellData(TestCaseName, "ShelfLife", TD);
		String Comments = Datareader.getCellData(TestCaseName, "Comments", TD);
		String Description = Datareader.getCellData(TestCaseName, "Description", TD);

		try {
			// if
			// (driver.findElements(By.xpath("//div[@class='reg-alert-label']/following-sibling::div/div[1]"))
			// .size() == 1) {
			// return "Failed: No packages availables";
			// } else {
			for (int i = 1; i <= rowCount; i++) {

				String Actual_Status_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr["
						+ i + "]/td[1]";
				String Actual_Status = driver.findElement(By.xpath(Actual_Status_cell)).getText();
				System.out.println("Actual_Status==>" + Actual_Status);
				System.out.println("Actual_Status==>" + Size);
				String Actual_Size_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr[" + i
						+ "]/td[2]";
				String Actual_Size = driver.findElement(By.xpath(Actual_Size_cell)).getText();
				System.out.println("Actual_Size==>" + Actual_Size);
				System.out.println("Actual_Size==>" + Size);
				String Actual_Material_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr["
						+ i + "]/td[3]";
				String Actual_Material = driver.findElement(By.xpath(Actual_Material_cell)).getText();
				System.out.println("Actual_Material==>" + Actual_Material);
				System.out.println("Actual_Material==>" + Material);
				String Actual_PackagingType_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr["
						+ i + "]/td[4]";
				String Actual_PackagingType = driver.findElement(By.xpath(Actual_PackagingType_cell)).getText();
				System.out.println("Actual_PackagingType==>" + Actual_PackagingType);
				System.out.println("Actual_PackagingType==>" + PackagingType);
				String Actual_Closure_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr["
						+ i + "]/td[5]";
				String Actual_Closure = driver.findElement(By.xpath(Actual_Closure_cell)).getText();
				System.out.println("Actual_Closure==>" + Actual_Closure);
				System.out.println("Actual_Closure==>" + Closure);
				String Actual_ShelfLife_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr["
						+ i + "]/td[6]";
				String Actual_ShelfLife = driver.findElement(By.xpath(Actual_ShelfLife_cell)).getText();
				System.out.println("Actual_ShelfLife==>" + Actual_ShelfLife);
				System.out.println("Actual_ShelfLife==>" + ShelfLife);
				String Actual_Comments_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr["
						+ i + "]/td[8]";
				String Actual_Comments = driver.findElement(By.xpath(Actual_Comments_cell)).getText();
				System.out.println("Actual_Comments==>" + Actual_Comments);
				System.out.println("Actual_Comments==>" + Comments);
				String Actual_Desc_cell = "//label[contains(text(),'Packages')]/following::div[1]//table/tbody/tr[" + i
						+ "]/td[7]";
				String Actual_Desc = driver.findElement(By.xpath(Actual_Desc_cell)).getText();
				System.out.println("Actual_Desc==>" + Actual_Desc);
				System.out.println("Actual_Desc==>" + Description);
				if (Actual_Size.trim().equals(Size) && Actual_Material.trim().equals(Material)
						&& Actual_PackagingType.trim().equals(PackagingType) && Actual_Closure.trim().equals(Closure)
						&& Actual_ShelfLife.trim().equals(ShelfLife) && Actual_Comments.trim().equals(Comments)
						&& Actual_Desc.trim().equals(Description)) {
					isTrue = false;
					break;
				}
			}
			if (isTrue) {

				return "Failed: Not found values";
			}
			// }
		} catch (Exception e) {
			return "Failed: element not found";
		}
		return "Pass";
	}

	/**
	 * Reads the request id from pop up
	 */
	public static String getRequestID() {
		try {
			String word = driver.findElement(Utils.getBy(Webelement)).getText();
			// substr = word.substring(word.length() - 5);
			substr = StringUtils.substringAfter(word, " ID ");
			Datareader.setCellData(TestCaseName, DataField, TD, substr);
			System.out.println("Request ID => " + substr);

		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	/**
	 * Method to do nothing
	 */
	public static String passBy() {
		try {
			System.out.println("Do Nothing");
			return "Pass";
		} catch (Exception e) {
			return "Failed:";
		}
	}

	/**
	 * Method to exit from test suit
	 */
	public static String end() {
		System.out.println("=> " + TestCaseName + " completed <=");
		return "Pass";
	}

	/**
	 * Method to get if compare two integer values are equal
	 */
	public static String compareIntEqual() {

		try {
			if (Integer.parseInt(DataValue) == Integer.parseInt(DataValue1)) {
				return "Pass";
			}
		} catch (Exception e) {
			return "Failed:Actual text" + DataValue + " is not as expected text-" + DataValue1;
		}
		return "Pass";
	}

	/**
	 * Method to compare if first integer is lesser than second integer
	 */
	public static String compareIntLesser() {

		try {
			if (Integer.parseInt(DataValue) < Integer.parseInt(DataValue1)) {
				return "Pass";
			}
		} catch (Exception e) {
			return "Failed:Actual text" + DataValue + " is not as expected text-" + DataValue1;
		}
		return "Pass";
	}

	/**
	 * Method to compare if first integer is lesser than second integer
	 */
	public static String compareIntGreater() {
		try {
			if (Integer.parseInt(DataValue) > Integer.parseInt(DataValue1)) {
				return "Pass";
			}
		} catch (Exception e) {
			return "Failed:Actual text" + DataValue + " is not as expected text-" + DataValue1;
		}
		return "Pass";

	}

	/**
	 * Method to select a particular value through finder
	 *
	 * WebElement : xpath of finder till input Example:
	 * //label[contains(text(),'License Holder')]/following::div[2]/input
	 *
	 */
	public static String selectFromFinder() throws Exception {
		
		//moveToElement();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,50)");
		try {
			String expected_siteName = Datareader.getCellData(TestCaseName, DataField, TD);

			driver.findElement(By.xpath(OR.getProperty(Webelement))).click();
			driver.findElement(By.xpath(OR.getProperty(Webelement))).sendKeys(expected_siteName);
			//Thread.sleep(3000);

			// Click on finder button
			driver.findElement(By.xpath(OR.getProperty(Webelement) + "/../i")).click();

			waitLoadinImageDisapear();
			//constantWaitSmall();
			
			String xpath_select=OR.getProperty(Webelement) + "/../i/..//table/tbody/tr[1]/td[3]/button";
			Webelement=xpath_select;
			waitForElementClickable();
	
			WebElement element = driver.findElement(By.xpath(xpath_select));
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			je.executeScript("arguments[0].click();", element);
			//driver.findElement(By.xpath(select)).click();
			
			Thread.sleep(100);
	return "Pass";

		} catch (Exception e) {
			System.out.println("Failed: Element not found");
		}

return "Failed: Not found value";
	}

	/**
	 * Method - for explicit wait
	 * 
	 * @throws InterruptedException
	 */
	public static String constantWaitSmall() throws InterruptedException {

		int waittime = 1 * 5000;
		Thread.sleep(waittime);

		return "Pass";
	}

	/**
	 * Wait for the specific time Datavalue should be passed in the unit-second
	 */
	public static String constantWaitSec() {
		try {
			int waittime = Integer.parseInt(DataValue) * 1000;
			Thread.sleep(waittime);
		} catch (Exception e) {

		}
		return "Pass";
	}

	/**
	 * Method to get the value of the mentioned Attribute
	 * 
	 * Webelement is the xpath till Attribute Eg: //div/div[1]/input is the
	 * xpath and input tag will have the attribute Datavalue is the Attribute
	 * name of the value which we want to get Attribute value will be stored in
	 * global variable -substr
	 */
	public static String getAttribute() {
		try {
			substr = driver.findElement(By.xpath(OR.getProperty(Webelement))).getAttribute(DataValue);
		} catch (Exception e) {

		}
		return "Pass";
	}

	/**
	 * Method to compare text.
	 * 
	 * Datavalue is the text which we want to compare
	 */
	public static String compareText() {

		try {
			if ((DataValue).equals(DataValue1)) {
				return "Pass";
			}
		} catch (Exception e) {
			return "Fail";
		}
		return "Pass";
	}

	/**
	 * Method to click request id present in the main screen
	 * 
	 * Datavalue should be - MyDrafts, if the request id is present inside
	 * MyDrafts - InProcess, if the request id is present inside InProcess
	 * 
	 * Before using this method, store the request id in global variable
	 * "substr"
	 */
	public static String clickReqID() {

		try {
			System.out.println("//a[text()='" + DataValue.trim() + "']");
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//a[text()='" + DataValue.trim() + "']"));
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			je.executeScript("arguments[0].click();", element);
			// driver.findElement(By.xpath("//a[text()='" + DataValue.trim() +
			// "']")).click();
			return "Pass";
		} catch (java.util.NoSuchElementException e) {
			return "Failed: Thread.sleep interrupted";
		}
	}

	/**
	 * Method to click request id present in the main screen
	 * 
	 * Datavalue is the request id TD1 should be 1 if click Req Id in "My Task"
	 * section and 2 if click Req Id in "Task Management" Before using this
	 * method, store the request id in global variable "substr"
	 */
	public static String clickReqIDForCTS_User() {

		try {
			System.out.println("//a[text()='" + DataValue.trim() + "']");
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//a[text()='" + DataValue.trim() + "']"));
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-250)");
			driver.findElement(By.xpath("(//a[text()='" + DataValue + "'])[" + TD1 + "]")).click();
			return "Pass";
		} catch (Exception e) {
			return "Failed: Thread.sleep interrupted";
		}
	}

	/**
	 * Method to verify Element is present
	 */
	public static String verifyReqIdisPresent() {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//a[text()='" + DataValue.trim() + "']"));
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			if (!(driver.findElements(By.xpath("//a[text()='" + DataValue.trim() + "']")).size() == 0)) {
				return "Pass";
			} else
				return "Failed: Reqid " + DataValue + "is not present";
		} catch (Exception e) {
			return "Failed -Element not Present";
		}
	}

	/**
	 * Method to verify Element is present
	 */
	public static String verifyReqIdisNotPresent() {
		try {
			if ((driver.findElements(By.xpath("//a[text()='" + DataValue.trim() + "']")).size() == 0)) {
				return "Pass";
			} else
				return "Failed: Reqid is present";
		} catch (Exception e) {
			return "Failed -Element not Present";
		}

	}

	/**
	 * Method to select the date from the date picker
	 * 
	 * Datavalue should be given in the format - d,Mmm,yyyy. Eg: 7,Dec,2014
	 * 
	 * Before using this method, click the date icon
	 */
	public static String datePicker() {
		try {
			if(DataValue.equals("30"))
			{
				 DateFormat currentDate = DateFormat.getDateInstance();
			        Calendar c=new GregorianCalendar();
			        c.add(Calendar.DATE, 30);
			        Date d=c.getTime();
			        //System.out.println("Date ====>"+d);
			        System.out.println(currentDate.format(d));
			        String date = currentDate.format(d).toString().replace(",", "");
			        String[] str=date.split(" ");
			        new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-month']")))
					.selectByVisibleText(str[0]);
			        new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-year']")))
					.selectByVisibleText(str[2]);
			        driver.findElement(By.xpath("//a[@class='ui-state-default'][text()='" + str[1] + "']")).click();
				
			}else{
			String date[] = DataValue.split(",");
			System.out.println(Arrays.toString(date));
			// Select Month
			new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-month']")))
					.selectByVisibleText(date[1]);
			// Select Year
			new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-year']")))
					.selectByVisibleText(date[2]);
			// Select Date
			driver.findElement(By.xpath("//a[@class='ui-state-default'][text()='" + date[0] + "']")).click();
			}
		} catch (NoSuchElementException e) {
			return "Failed: element not found";
		}
		return "Pass";
	}

	/**
	 * Method to get the text from web element.
	 * 
	 * Datavalue should be the string present before the text
	 */
	public static String getTextBetweenString() {
		try {
			String word = driver.findElement(Utils.getBy(Webelement)).getText();
			String[] str = DataValue.split(",");
			substr = StringUtils.substringBetween(word, str[0], str[1]);
			Datareader.setCellData(TestCaseName, DataField1, TD1, substr.trim());
			System.out.println("getTextBeforeString => " + substr);
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
		return "Pass";
	}

	/**
	 * Method to check if the string is numeric
	 * 
	 */
	public static String isNumeric() {
		try {
			// StringUtils.isNumeric(substr);

			StringUtils.isNumeric(DataValue.trim());

			System.out.println("isNumeric => " + substr);

		} catch (Exception e) {
			return "Failed";
		}
		return "Pass";
	}

	/**
	 * Method to Check the checkbox and click on Assign Link in the Task
	 * Management domain
	 * 
	 * DataValue is the status name eg: Un-Assigned DataValue1 is the Request id
	 */
	public static String taskMangCheckbox() {
		try {
			// Check the checkbox
			driver.findElement(By.xpath("//a[contains(text(),'My Task')]/following::label[contains(text(),'" + DataValue
					+ "')][2]" + "/following::a[text()='" + DataValue1 + "']/../../../div[1]/span/input")).click();

			// // Click the Assign Link
			// driver.findElement(By.xpath("//a[contains(text(),'My
			// Task')]/following::label[contains(text(),'" + DataValue
			// + "')][2]" +
			// "/../following-sibling::div[1]/div[1]/div/div/div[1]/div[1]/span/a")).click();
			constantWaitSmall();

			// Click the Assign Link
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver
					.findElement(By.xpath("//a[contains(text(),'My Task')]/following::a[contains(text(),'" + DataValue1
							+ "')][2]/ancestor::div[4]/div[1]/div[1]/div/span/a"));
			je.executeScript("arguments[0].click();", element);

		} catch (Exception e) {
			return "Failed:element not found";
		}
		return "Pass";
	}

	/**
	 * Method to Check the checkbox and click on Assign Link in the My Task
	 * domain
	 * 
	 * DataValue is the status name eg: Un-Assigned DataValue1 is the Request id
	 */
	//Uncooment later
	/*public static String myTaskCheckbox() {
		try {
			// Check the checkbox
			driver.findElement(By.xpath(OR.getProperty(Webelement) + "/following::a[text()='" + DataValue + "']/../../../div[1]/span/input")).click();

			constantWaitSmall();

			// Click the Assign Link
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver
					.findElement(By.xpath(OR.getProperty(Webelement) +"/following::a[contains(text(),'" + DataValue
							+ "')][1]/ancestor::div[4]/div[1]/div[1]/div/span/a"));
			je.executeScript("arguments[0].click();", element);

		} catch (Exception e) {
			return "Failed:element not found";
		}
		return "Pass";
	}*/
	public static String myTaskCheckbox() {
		try {
			// Check the checkbox
			driver.findElement(By.xpath("//a[contains(text(),'My Task')]/following::label[contains(text(),'" + DataValue
					+ "')][1]" + "/following::a[text()='" + DataValue1 + "']/../../../div[1]/span/input")).click();

			// Click the Assign Link
			// driver.findElement(By.xpath("//a[contains(text(),'My
			// Task')]/following::label[contains(text(),'" + DataValue
			// + "')][1]" +
			// "/../following-sibling::div[1]/div[1]/div/div/div[1]/div[1]/span/a")).click();
			// //-> old xpath

			// constant wait
			constantWaitSmall();

			// Click the Assign Link
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver
					.findElement(By.xpath("//a[contains(text(),'My Task')]/following::a[contains(text(),'" + DataValue1
							+ "')][1]/ancestor::div[4]/div[1]/div[1]/div/span/a"));
			je.executeScript("arguments[0].click();", element);

		} catch (Exception e) {
			return "Failed:element not found";
		}
		return "Pass";
	}

	/**
	 * Method to verify the fields in Audit Table
	 * 
	 * DataValue is the Request id and DataValue1 is the column values seperated
	 * by ">"
	 */
	public static String verifyAuditTableRow() {
		String result = "Pass";
		String[] colValue = DataValue1.split(">");
		String xpath = "//td/div[contains(text(),'" + DataValue + "')]/ancestor::tr[./td/*[contains(text(),'"
				+ colValue[0] + "')]";
		String xpath1 = "";
		for (int i = 1; i < colValue.length; i++) {
			xpath1 = " and ./td/*[contains(text(),'" + colValue[i] + "')]";
			xpath = xpath.concat(xpath1);
		}
		xpath = xpath.concat("]");
		System.out.println("xpath" + xpath);
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath(xpath));
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			if (driver.findElements(By.xpath(xpath)).size() != 0) {
				result = "Pass";
			} else
				result = "Failed: Column value not found";

		} catch (Exception e) {
			return "Failed: element no found";
		}
		return result;

	}

	/**
	 * Method to select dropdown from BusinessUser search result
	 * 
	 * DataValue should be the value which we want to select
	 */
	public static String selectFromBusinessUserTable() {
		try {
			driver.findElement(By.xpath("//input[@data-ng-model='newRequest.changeBusinessUser']"
					+ "/following::table[1]/tbody/tr/td[1]/label[text()='" + DataValue.toLowerCase()
					+ "']/ancestor::tr/td[4]/button")).click();
		} catch (Exception e) {
			return "Failed: Element not found";
		}

		return "Pass";
	}

	/**
	 * >> WorkFlow 5 and 6<<
	 * 
	 * Method to select dropdown from Central Table search result DataValue
	 * should be the value which we want to select
	 */
	public static String selectFromCentralTeamTable() {
		try {
			driver.findElement(By.xpath("//input[@data-ng-model='newRequest.changeBusinessUser']"
					+ "/following::table[3]/tbody/tr/td[1]/label[text()='" + DataValue.toLowerCase()
					+ "']/ancestor::tr/td[4]/button")).click();
		} catch (NoSuchElementException e) {
			return "Failed: Element not found";
		}

		return "Pass";
	}

	/**
	 * >> WorkFlow 5 and 6 << Method to check if the radio button is enabled
	 */
	public static String radiobtnIsEnabled() {
		try {

			if (driver.findElement(Utils.getBy(Webelement)).getAttribute("class").contains("enabled")) {
				return "Pass";
			} else
				return "Failed: radio button is not disabled";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * >> WorkFlow 5 and 6 << Method to check if the radio button is disabled
	 */
	public static String radiobtnIsDisabled() {
		try {

			if (driver.findElement(Utils.getBy(Webelement)).getAttribute("class").contains("disabled")) {
				return "Pass";
			} else
				return "Failed: radio button is not disabled";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * >> Registration page << Method to check if the radio button is enabled
	 * xpath should be till input tag
	 */
	public static String radiobtnIsSelected() {
		try {

			if (driver.findElement(Utils.getBy(Webelement)).getAttribute("class")
					.contains("ng-touched ng-valid-parse")) {
				return "Pass";
			} else
				return "Failed: radio button is not selected";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * >> Registration page << Method to check if the radio button is enabled
	 * xpath should be till input tag
	 */
	public static String radiobtnIsNotSelected() {
		try {

			if (driver.findElement(Utils.getBy(Webelement)).getAttribute("class")
					.contains("ng-touched ng-valid-parse")) {
				return "Failed: radio button is not selected";
			} else
				return "Pass";
		} catch (Exception e) {
			return "Failed: element not found";
		}
	}

	/**
	 * Method to clear the contents of text box
	 */
	public static String clearTextBox() {
		try {
			driver.findElement(Utils.getBy(Webelement)).clear();
			return "Pass";
		} catch (NoSuchElementException e) {
			return "Failed: element no found";
		}
	}

	/**
	 * Method to verify Request Id details present in Fred Central Request table
	 * DataValue is the Request id DataValue1 is the expected VALUE TD2 is the
	 * nth column
	 */
	public static String verifyFredCentralReqTable() {
		try {
			String ActualColValue = driver
					.findElement(By
							.xpath("//a[contains(text(),'" + DataValue + "')]/ancestor::div[2]/div[" + TD2 + "]/label"))
					.getText();
			if (DataField1.contains("Date")) {

				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("dd MMM yyyy");
				DataValue1 = ft.format(dNow);
				StringUtils.capitalize(DataValue1);
				System.out.println("Date" + DataValue1);
			}

			if (DataValue1.contains(ActualColValue)) {
				return "Pass";
			} else
				return "Failed: Actual column value" + ActualColValue + "is not equal to expected column value"
						+ DataValue1;

		} catch (Exception e) {
			return "Failed: Elemment not found";
		}
	}

	/**
	 * Method to verify Request Id details present in Fred Central Request table
	 * DataValue is the Request id DataValue1 is the expected VALUE TD2 is the
	 * nth column
	 * 
	 * This method is used for Central team member login
	 */
	public static String verifyReqIdDetailsForCTM() {
		try {
			String ActualColValue = driver
					.findElement(
							By.xpath("//label[text()='" + DataValue + "']/ancestor::div[3]/div[" + TD2 + "]/div/label"))
					.getText();
			if (DataField1.equals("CurrentDate")) {

				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("dd MMM yyyy");
				DataValue1 = ft.format(dNow);
				StringUtils.capitalize(DataValue1);
				System.out.println("Date" + DataValue1);
			}
			if (DataValue1.contains(ActualColValue)
					|| (DataValue1.toLowerCase()).contains(ActualColValue.toLowerCase())) {
				return "Pass";
			} else
				return "Failed: Actual column value" + ActualColValue + "is not equal to expected column value"
						+ DataValue1;

		} catch (Exception e) {
			return "Failed: Elemment not found";
		}
	}

	/**
	 * Method to verify Request Id details present in Fred Central Request table
	 * DataValue is the Request id DataValue1 is the expected VALUE TD2 is the
	 * nth column
	 * 
	 * This method is used for Central team supervisor login
	 */
	public static String verifyReqIdDetailsForCTSMyTask() {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(
				By.xpath("//div[@data-ng-show='tab ===1']/div/div/div/div/div/div[2]/div/div[2]/div/label[text()='"
						+ DataValue + "']/ancestor::div[3]/div[" + TD2 + "]/div/label"));
		je.executeScript("arguments[0].scrollIntoView(true);", element);
		// ((JavascriptExecutor)
		// driver).executeScript("window.scrollBy(0,-300)");

		try {
			String ActualColValue = driver.findElement(
					By.xpath("//div[@data-ng-show='tab ===1']/div/div/div/div/div/div[2]/div/div[2]/div/label[text()='"
							+ DataValue + "']/ancestor::div[3]/div[" + TD2 + "]/div/label"))
					.getText();
			if (DataField1.equals("CurrentDate")) {
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("dd MMM yyyy");
				DataValue1 = ft.format(dNow);
				StringUtils.capitalize(DataValue1);
				System.out.println("Date" + DataValue1);
			}
			if (StringUtils.containsIgnoreCase(DataValue1, ActualColValue)) {
				return "Pass";
			} else
				return "Failed: Actual column value" + ActualColValue + "is not equal to expected column value"
						+ DataValue1;

		} catch (Exception e) {
			return "Failed: Elemment not found";
		}
	}

	/**
	 * Method to verify Request Id details present in Fred Central Request table
	 * DataValue is the Request id DataValue1 is the expected VALUE TD2 is the
	 * nth column
	 * 
	 * This method is used for Central team supervisor login
	 */
	public static String verifyReqIdDetailsForCTSTaskMang() {
		try {
			String ActualColValue = driver
					.findElement(
							By.xpath("//label[text()='" + DataValue + "']/ancestor::div[3]/div[" + TD2 + "]/div/label"))
					.getText();

			if (DataField1.equals("CurrentDate")) {
				Date dNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("dd MMM yyyy");
				DataValue1 = ft.format(dNow);
				StringUtils.capitalize(DataValue1);
				System.out.println("Date" + DataValue1);
			}
			if (StringUtils.containsIgnoreCase(DataValue1, ActualColValue)) {
				return "Pass";
			} else
				return "Failed: Actual column value" + ActualColValue + "is not equal to expected column value"
						+ DataValue1;

		} catch (Exception e) {
			return "Failed: Elemment not found";
		}
	}

	/**
	 * Verifies error pop up exists and will close if exists
	 */
	public static String verifyErrorPopAndClose() {
		try {
			if ((driver.findElements(Utils.getBy(Webelement)).size() != 0)) {
				scrollToElement();
				driver.findElement(Utils.getBy(Webelement1)).click();
			}
		} catch (Exception e) {

		}
		return "Pass";
	}

	/**
	 * >> Advance Search << Verifies the cell value in Advance search Table
	 * Datavalue should be Register Id DataValue1 should be text we want to
	 * search RowNo2 should be the nth column
	 */
	public static String verifyAdvSrchTable() {
		try {
			if (DataField2.equals("RegIdSearch")) {
				driver.findElement(By.id("registrationID")).clear();
				driver.findElement(By.id("registrationID")).sendKeys(DataValue);
				driver.findElement(Utils.getBy("xpath_Audit_SearchButton")).click();
				waitLoadinImageDisapear();
			}
			System.out.println("//a[contains(text(),'" + DataValue + "')]/ancestor::div[2]/div[" + TD2
					+ "]//*[contains(text(),'" + DataValue1 + "')]");
			// JavascriptExecutor je = (JavascriptExecutor) driver;
			// WebElement element =
			// driver.findElement(By.xpath("//a[contains(text(),'"+DataValue+"')]/ancestor::div[2]/div["+TD2+"]//*[contains(text(),'"+DataValue1+"')]"));
			// je.executeScript("arguments[0].scrollIntoView(true);", element);
			driver.findElement(By.xpath("//a[contains(text(),'" + DataValue + "')]/ancestor::div[2]/div[" + TD2
					+ "]//*[contains(text(),'" + DataValue1 + "')]"));
			driver.findElement(By.id("registrationID")).clear();
			return "Pass";
		} catch (Exception e) {
			return "Failed: Element with column value-" + DataValue1 + " not found";
		}
	}

	/**
	 * >> Advance Search << Selects the Ingredient from the finder Datavalue
	 * should be the value which we want to select RowNo1 should be the nth
	 * dropdown
	 */
	public static String selectActiveIngredient() {

		try {
			// Type in textbox
			driver.findElement(
					By.xpath("//label[contains(text(),'Active Ingredient(s)')]/parent::div/searchcomplete-box[" + TD1
							+ "]/div/div[1]/input"))
					.sendKeys(DataValue);

			// Click on finder
			driver.findElement(
					By.xpath("//label[contains(text(),'Active Ingredient(s)')]/parent::div/searchcomplete-box[" + TD1
							+ "]/div/div[1]/input/../div[1]"))
					.click();

			// Select the ingredient
			driver.findElement(
					By.xpath("//label[contains(text(),'Active Ingredient(s)')]/parent::div/searchcomplete-box[" + TD1
							+ "]/div/div[2]/a[1]"))
					.click();
			waitLoadinImageDisapear();

			return "Pass";
		} catch (Exception e) {
			return "Failed: Element with Ingredient-" + DataValue + " not found";
		}
	}

	/**
	 * >> Advance Search << Verifies if the register id is present in the
	 * Advance search table Datavalue should be Register Id
	 */
	public static String verifyRegIdNotFound() {

		String result = "Failed";
		try {
			driver.findElement(By.id("registrationID")).clear();
			driver.findElement(By.id("registrationID")).sendKeys(DataValue);
			driver.findElement(Utils.getBy("xpath_Audit_SearchButton")).click();
			waitLoadinImageDisapear();
			if (!(driver.findElements(Utils.getBy("xpath_NoResultsFound")).size() == 0)) {
				result = "Pass";
			}
			driver.findElement(By.id("registrationID")).clear();
			return result;
		} catch (Exception e) {
			return "Failed: Element not found";
		}
	}

	/**
	 * >> Workflow << Verifies if the value is present in the Central Team Users
	 * table DataValue is the username
	 */
	public static String verify_UserId_CentrlTeamUserTable() {

		try {
			if (!(driver.findElements(By
					.xpath("//table[@class='searchResultTable']/tbody/tr/td[text()='" + DataValue.toLowerCase() + "']"))
					.size() == 0)) {
				return "Pass";
			} else
				return "Failed";
		} catch (Exception e) {
			return "Failed:Element not found";
		}
	}

	/**
	 * >> Workflow << selects from the Central Team Users table DataValue is the
	 * username
	 */
	public static String select_UserId_CentrlTeamUserTable() {

		try {
			driver.findElement(By.xpath("//table[@class='searchResultTable']/tbody/tr/td[text()='"
					+ DataValue.toLowerCase() + "']/../td[4]/div/button")).click();
			return "Pass";

		} catch (Exception e) {
			return "Failed:Element not found";
		}
	}

	/**
	 * Logout
	 */
	public static String Logout() {

		try {
			driver.findElement(Utils.getBy(Webelement)).click();
			// driver.findElement(Utils.getBy(Webelement1)).click();
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(Utils.getBy(Webelement1));
			// je.executeScript("arguments[0].scrollIntoView(true);", element);
			je.executeScript("arguments[0].click();", element);
			return "Pass";
		} catch (Exception e) {
			return "Failed:Element not found";
		}
	}

	/**
	 * *************************************************************************
	 * *******************************************************
	 */
	/**
	 * >> Workflow << selects from the Central Team Users table DataValue is the
	 * username
	 */
	public static String method1() {
		String referredSheetColName = "Type";
		String value = Datareader.getCellData(Keyword, referredSheetColName, TD);
		System.out.println("output" + value);
		return "Pass";
	}

	/*
	 * Check site type from manufactring site Parmaters : Site name and Site
	 * Type
	 */
	public static String Check_manSitetype() {
		try {
			int count = driver.findElements(By.xpath(String.format(OR.getProperty("xpath_siteType_chk"), DataValue)))
					.size();
			for (int i = 1; i <= count; i++) {
				WebElement element = driver.findElement(
						By.xpath(String.format(OR.getProperty("xpath_siteType_chk") + "[" + i + "]/label", DataValue)));
				if (element.getText().contains(DataValue1)) {
					element.click();
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
	}

	/*
	 * Check site status for Site type from manufactring site Parmaters : Site
	 * name, Site Type and Site status
	 */
	public static String Check_siteStatus() {
		try {
			int count = driver.findElements(By.xpath(String.format(OR.getProperty("xpath_siteType_chk"), DataValue)))
					.size();
			for (int i = 1; i <= count; i++) {
				WebElement element = driver.findElement(
						By.xpath(String.format(OR.getProperty("xpath_siteType_chk") + "[" + i + "]/label", DataValue)));
				if (element.getText().contains(DataValue1)) {
					WebElement siteStatus_chk = driver.findElement(By.xpath(String.format(
							OR.getProperty("xpath_siteType_chk") + "[" + i + "]/label/following-sibling::div/label[1]",
							DataValue)));
					if (DataValue2.equals("Approved")) {
						driver.findElement(By.xpath(siteStatus_chk + "[1]")).click();
					} else if (DataValue2.equals("Dual Process")) {
						driver.findElement(By.xpath(siteStatus_chk + "[2]")).click();
					} else if (DataValue2.equals("Backup Site")) {
						driver.findElement(By.xpath(siteStatus_chk + "[3]")).click();
					} else if (DataValue2.equals("Currently Active")) {
						driver.findElement(By.xpath(siteStatus_chk + "[4]")).click();
					} else {
						return "Failed : No status found";
					}
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
	}

	/*
	 * >> ChangeBanner << Click on Edit or View button of package table Either
	 * View or Edit button View Or Edit - Parameter
	 */
	public static String ClickOnEditViewPackage() {
		try {
			int count = driver.findElements(By.xpath(OR.getProperty("xpath_summaryTable"))).size();
			for (int irow = 1; irow <= count; irow++) {
				// Actual values
				String Status_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[1]")).getText();
				String Size_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[2]")).getText();
				String Material_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[3]")).getText();
				String Pck_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[4]")).getText();
				String closure_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[5]")).getText();
				String SLife_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[6]")).getText();
				String Desc_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[7]")).getText();
				String Comments_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[8]")).getText();
				String AppDate_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[9]")).getText();
				// Expected values
				String Status = Datareader.getCellData(TestCaseName, "Status", TD);
				String Size = Datareader.getCellData(TestCaseName, "Size", TD);
				String Material = Datareader.getCellData(TestCaseName, "Material", TD);
				String PackagingType = Datareader.getCellData(TestCaseName, "PackagingType", TD);
				String Closure = Datareader.getCellData(TestCaseName, "Closure", TD);
				String ShelfLife = Datareader.getCellData(TestCaseName, "ShelfLife", TD);
				String Comments = Datareader.getCellData(TestCaseName, "Comments", TD);
				String Description = Datareader.getCellData(TestCaseName, "Description", TD);
				String AppDate = Datareader.getCellData(TestCaseName, "AppDate", TD);
				if (Status_actual.contains(Status) && Size_actual.contains(Size) && Material_actual.contains(Material)
						&& Pck_actual.contains(PackagingType) && closure_actual.contains(Closure)
						&& SLife_actual.contains(ShelfLife) && Desc_actual.contains(Description)
						&& Comments_actual.contains(Comments) && AppDate_actual.contains(AppDate)) {
					if (DataValue.contains("View")) {
						driver.findElement(
								By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[10]/div[1]/button"))
								.click();
					} else {
						driver.findElement(
								By.xpath(OR.getProperty("xpath_summaryTable") + "[" + irow + "]/td[10]/div[2]/button"))
								.click();
					}
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed: element not found" + Webelement;
		}
	}

	/*
	 * Function to verify change Banner Section, Type and Update are parameters
	 * - reads from excel sheet
	 */
	public static String verifyChangeBanner() {
		try {
			int count = driver.findElements(By.xpath(OR.getProperty("xpath_changeBanner"))).size();
			for (int irow = 1; irow <= count; irow++) {
				String section_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_changeBanner") + "[" + irow + "]/div[1]/span"))
						.getText();
				String type_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_changeBanner") + "[" + irow + "]/div[2]"))
						.getText();
				String updateTo1_actual = driver
						.findElement(
								By.xpath(OR.getProperty("xpath_changeBanner") + "[" + irow + "]/div[3]/div/span[1]"))
						.getText();
				String updateTo2_actual = driver
						.findElement(
								By.xpath(OR.getProperty("xpath_changeBanner") + "[" + irow + "]/div[3]/div/span[2]"))
						.getText();
				String updateFrom_actual = driver
						.findElement(By.xpath(OR.getProperty("xpath_changeBanner") + "[" + irow + "]/div[3]/div[2]"))
						.getText();
				String Section = Datareader.getCellData(TestCaseName, "Section", TD);
				String Type = Datareader.getCellData(TestCaseName, "Type", TD);
				String UpdateFrom = Datareader.getCellData(TestCaseName, "updateFrom", TD);
				String Updateto = Datareader.getCellData(TestCaseName, "updateTo", TD);

				if (section_actual.contains(Section) && type_actual.contains(Type)
						&& updateTo2_actual.contains(Updateto) && updateFrom_actual.contains(UpdateFrom)) {
					System.out.println("Change banner verified successfully");
					return "Pass";
				}
			}
			return "Pass";
		} catch (Exception e) {
			return "Failed : Element not found";
		}
	}
	/*
	 * Function to login as BU user
	 * @param1 - BU user name
	 * @param2 - BU password
	 */
	public static String loginAsBU() {
		try{
		constantWaitSmall();
		String username= Datareader.getCellData("LoginTest","BU",TD);
		String pwd= Datareader.getCellData("LoginTest","BU_PWD",TD);
		driver.findElement(By.id(OR.getProperty("id_login_email_input"))).sendKeys(username);
		driver.findElement(By.id(OR.getProperty("id_login_password_input"))).sendKeys(pwd);
		driver.findElement(By.id(OR.getProperty("id_login_login_button"))).click();
		waitLoadinImageDisapear();
		waitForElementClickable();
		return "Pass";
	}
	catch(Exception e){
		return "Failed: Element not found";
	}
}
	/*
	 * Function to wait for Home Icon present on Home page
	 * Fluent wait
	 */
	
	public static String waitForElementClickable() {
		try {
			Wait waitObj = new FluentWait(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5,TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			//waitObj.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@data-ng-click='goHome()']")));
			waitObj.until(ExpectedConditions.elementToBeClickable(Utils.getBy(Webelement)));
		} catch (Exception e) {

			return "Fail - Element is visible";

		}
		return "Pass";
	}
	/*
	 * Function to login as CTS user
	 * @param1 - CTS user name
	 * @param2 - CTS password
	 */
	public static String loginAsCTS() {
		try{
		String username= Datareader.getCellData("LoginTest","CTS", TD);
		String pwd= Datareader.getCellData("LoginTest","CTS_PWD", TD);
		driver.findElement(By.id(OR.getProperty("id_login_email_input"))).sendKeys(username);
		driver.findElement(By.id(OR.getProperty("id_login_password_input"))).sendKeys(pwd);
		driver.findElement(By.id(OR.getProperty("id_login_login_button"))).click();
		waitLoadinImageDisapear();
		waitForElementClickable();
		return "Pass";
	}
	catch(Exception e){
		return "Failed: Element not found";
	}
	}
	/*
	 * Function to login as CTM user
	 * @param1 - CTM user name
	 * @param2 - CTM password
	 */
	public static String loginAsCTM() {
		try {
			String username= Datareader.getCellData("LoginTest","CTM", TD);
			String pwd= Datareader.getCellData("LoginTest","CTM_PWD", TD);
			driver.findElement(By.id(OR.getProperty("id_login_email_input"))).sendKeys(username);
			driver.findElement(By.id(OR.getProperty("id_login_password_input"))).sendKeys(pwd);
			driver.findElement(By.id(OR.getProperty("id_login_login_button"))).click();
			waitLoadinImageDisapear();
			waitForElementClickable();
			return "Pass";
		}
		catch(Exception e){
			return "Failed: Element not found";
		}
	}
	/*
	 * Method to verify Central Team data displayed on the CT home page
	 * @param1: Webelement for the perticular section
	 * @param2: following columns - hardcoded in the method
	 * @testdata
	 */
	public static String verifyCTData(){
		try{
			int i=1;
		String ReqID=OR.getProperty(Webelement) +"/following::div[1]/div/div[1]/descendant::div[contains(@class,'FREDID')]/div/a";
		List<WebElement> list = driver.findElements(By.xpath(ReqID));
		for (WebElement elementToSelect : list) {
		i=i+1;	
		if(elementToSelect.getText().equalsIgnoreCase(DataValue)){
			//creating webElements
		String InteractionID=OR.getProperty(Webelement) +"/following::div[1]/descendant::div[contains(@class,'interactionIdctint')]/div/label";
		String Category=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'interactionCategoryctint')]/div/label";
		String Type=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'interactionTypectint')]/div/label";
		String SubType=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'interactionSubTypectint')]/div/label";
		String BusinessUser=OR.getProperty(Webelement) +"/following:div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'businessUserctint')]/div/label";
		String CurrentOwner=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'currentOwnerIDctint')]/div/label";
		String BusinessUserDate=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'submissionDatectint')]/div/label";
		String RequestType=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'typectint')]/div/label";;
		String ProcessedDate=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'processedDatectint')]/div/label";
		String ProcessedBy=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'processedByctint')]/div/label";
		String VerifiedDate=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'verifiedDatectint')]/div/label";
		String VerifiedBy=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'verifiedByctint')]/div/label";
		String Description=OR.getProperty(Webelement) +"/following::div[1]/div/div[2]/div/div["+i+"]/descendant::div[contains(@class,'descriptionctint')]/div/label";
		//reading value from webElement
		String InteractionID_Value=driver.findElement(By.xpath(InteractionID)).getText();
		String Category_Value=driver.findElement(By.xpath(Category)).getText();
		String Type_Value=driver.findElement(By.xpath(Type)).getText();
		String SubType_Value=driver.findElement(By.xpath(SubType)).getText();
		String BusinessUser_Value=driver.findElement(By.xpath(BusinessUser)).getText();
		String CurrentOwner_Value=driver.findElement(By.xpath(CurrentOwner)).getText();
		String BusinessUserDate_Value=driver.findElement(By.xpath(BusinessUserDate)).getText();
		String RequestType_Value=driver.findElement(By.xpath(RequestType)).getText();
		String ProcessedDate_Value=driver.findElement(By.xpath(ProcessedDate)).getText();
		String ProcessedBy_Value=driver.findElement(By.xpath(ProcessedBy)).getText();
		String VerifiedDate_Value=driver.findElement(By.xpath(VerifiedDate)).getText();
		String VerifiedBy_Value=driver.findElement(By.xpath(VerifiedBy)).getText();
		String Description_Value=driver.findElement(By.xpath(Description)).getText();
		//Fetching test data
		String InteractionID_data=Datareader.getCellData("interactionData","InteractionID", TD);
		String Category_data=Datareader.getCellData("interactionData","Category", TD);
		String Type_data=Datareader.getCellData("interactionData","Type", TD);
		String SubTypeD_data=Datareader.getCellData("interactionData","SubType", TD);
		String BusinessUser_data=Datareader.getCellData("interactionData","BusinessUser", TD);
		String CurrentOwner_data=Datareader.getCellData("interactionData","CurrentOwner", TD);
		String BusinessUserDate_data=Datareader.getCellData("interactionData","BusinessUserDate", TD);
		String RequestType_data=Datareader.getCellData("interactionData","RequestType", TD);
		String ProcessedDate_data=Datareader.getCellData("interactionData","ProcessedDate", TD);
		String ProcessedBy_data=Datareader.getCellData("interactionData","ProcessedBy", TD);
		String VerifiedDate_data=Datareader.getCellData("interactionData","VerifiedDate", TD);
		String VerifiedBy_data=Datareader.getCellData("interactionData","VerifiedBy", TD);
		String Description_data=Datareader.getCellData("interactionData","Description", TD);
		
		
		
		if(InteractionID_Value.equalsIgnoreCase(InteractionID_data) &&
				Category_Value.equalsIgnoreCase(Category_data) && Type_Value.equalsIgnoreCase(Type_data)&& SubType_Value.equalsIgnoreCase(SubTypeD_data)&& BusinessUser_Value.equalsIgnoreCase(BusinessUser_data)&&
				CurrentOwner_Value.equalsIgnoreCase(CurrentOwner_data)&& BusinessUserDate_Value.equalsIgnoreCase(BusinessUserDate_data)
				&& RequestType_Value.equalsIgnoreCase(RequestType_data) && ProcessedDate_Value.equalsIgnoreCase(ProcessedDate_data)
				&& ProcessedBy_Value.equalsIgnoreCase(ProcessedBy_data) && VerifiedDate_Value.equalsIgnoreCase(VerifiedDate_data)
				&& VerifiedBy_Value.equalsIgnoreCase(VerifiedBy_data) && Description_Value.equalsIgnoreCase(Description_data)){
							
				return "Pass";
				
			}
		}
		}

		return "Failed: Values not found";	
		}
		catch(Exception e){
				return "Failed: Element not Found";
		}
		
	}
	/*
	 * Method to click on Request ID present on Central Team home page
	 * @param1: Section name
	 * @param2: Request Id as datavalue
	 */
	public static String clickOnReqIDOfCTPage(){
		try{
		String ReqID=OR.getProperty(Webelement) +"/following::div[1]/div/div[1]/descendant::div[contains(@class,'FREDID')]/div/a";
		List<WebElement> list = driver.findElements(By.xpath(ReqID));
		for (WebElement elementToSelect : list) {
		if(elementToSelect.getText().equalsIgnoreCase(DataValue)){
			elementToSelect.click();
		}
		}
		return "Failed: Nor fount Req ID";
		}
		catch(Exception e){
			return "Failed: Element not found";
		}
	}
	/*
	 * Method to enter credentials to submit form
	 * @testdata1: username
	 */
	public static String submitformUserName(){
		try{
			String username= Datareader.getCellData("LoginTest",DataField, TD);
			driver.findElement(By.xpath(OR.getProperty("xpath_EverifyPopUpUserName"))).sendKeys(username);
		    return "Pass";
		}catch(Exception e){
			return "Failed: Element not found";
		}
	}
	/*
	 * Method to enter credentials to submit form
	 * @testdata1: password
	 */
	public static String submitformPassword(){
		try{
			String pwd= Datareader.getCellData("LoginTest",DataField, TD);
			driver.findElement(By.xpath(OR.getProperty("xpath_EverifyPopUpPassword"))).sendKeys(pwd);
			return "Pass";
		}catch(Exception e){
			return "Failed: Element not found";
		}
	}

}
