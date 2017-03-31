package TestUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

public class Utils  extends TestResources{
	public static void getScreenshot(String FileName) throws IOException {
		File OutputFile = driver.getScreenshotAs(OutputType.FILE);
		//FileUtils.copyFile(OutputFile, new File(System.getProperty("user.dir")+"//src//Reports//Screenshot//"+FileName+".jpg"));
		
		FileUtils.copyFile(OutputFile, new File(System.getProperty("user.dir")+"\\src\\Reports\\Screenshot\\"+FileName+".png"));
	}

	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat  frm = new SimpleDateFormat(format);
		return frm.format(cal.getTime()); 
	}
	
	
	public static By getBy(String locator) {
		String loc[]=locator.split("_");
	
		if(loc[0].equals("linktext")) {
			return By.linkText(OR.getProperty(locator));	
		}else if(loc[0].equals("name")) {
			return By.name(OR.getProperty(locator));
		}else if(loc[0].equals("xpath")) {
			return By.xpath(OR.getProperty(locator));
		}else if(loc[0].equals("partiallinktext")) {
			return By.partialLinkText(OR.getProperty(locator));
		}else if(loc[0].equals("css")) {
			return By.cssSelector(OR.getProperty(locator));
		}else if(loc[0].equals("tag")) {
			return By.tagName(OR.getProperty(locator));
		}else if(loc[0].equals("class")) {
			return By.className(OR.getProperty(locator));
		}else if(loc[0].equals("id")) {
			return By.id(OR.getProperty(locator));
		}
		return By.xpath(OR.getProperty(locator));
	}
	
	
	public static String getElement(String locator) {
		String loc[]=locator.split("_");
	
		if(loc[0].equals("linktext")) {
			return (OR.getProperty(locator));	
		}else if(loc[0].equals("name")) {
			return (OR.getProperty(locator));
		}else if(loc[0].equals("xpath")) {
			return (OR.getProperty(locator));
		}else if(loc[0].equals("partiallinktext")) {
			return (OR.getProperty(locator));
		}else if(loc[0].equals("css")) {
			return (OR.getProperty(locator));
		}else if(loc[0].equals("tag")) {
			return (OR.getProperty(locator));
		}else if(loc[0].equals("class")) {
			return (OR.getProperty(locator));
		}else if(loc[0].equals("id")) {
			return (OR.getProperty(locator));
		}
		return (OR.getProperty(locator));
	}
}
