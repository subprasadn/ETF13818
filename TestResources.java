package TestUtils;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import Data.Xls_Reader;

public class TestResources {
	//public WebDriver driver = new FirefoxDriver();
	//public static WebDriver dr = new InternetExplorerDriver();
	//public static WebDriver dr = new FirefoxDriver();
	//public static EventFiringWebDriver driver = new EventFiringWebDriver(dr);  
	public static WebDriver dr = null;
	public static EventFiringWebDriver driver = null;


	public TestResources(){
		
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//drivers//chromedriver.exe");
		//dr=new ChromeDriver();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		dr = new ChromeDriver(options);
		driver = new EventFiringWebDriver(dr);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
	}
	
	public static Properties OR = new Properties();
	public static Properties AppText = new Properties();
	public static Xls_Reader Suitereader = new Xls_Reader(System.getProperty("user.dir")+"\\src\\Data\\FRED.xlsx");
	public static Xls_Reader Datareader = new Xls_Reader(System.getProperty("user.dir")+"\\src\\Data\\FRED-Data.xlsx");
	public static boolean LoginFalg=false;
	
	
	// excel  variable
	public static String TestCaseName;
	public static String RunMode;
	public static String Desc;
	public static String Keyword;
	public static String Webelement;
	public static String Webelement1;
	public static String ProceedOnFail;
	public static String DataField;
	public static String DataField1;
	public static String DataField2;
	public static String DataValue;
	public static String DataValue1;
	public static String DataValue2;
	protected static int TD;
	protected static int TD1;
	protected static int TD2;
	public static int TS_;

	
	public static void init() throws IOException {
		File f = new File(System.getProperty("user.dir")+"\\src\\Config\\OR.properties");
		FileInputStream fis = new FileInputStream(f); 
		OR.load(fis);	

	/*	f = new File(System.getProperty("user.dir")+"//data//AppText.properties");
		fis = new FileInputStream(f); 
		AppText.load(fis);*/	

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
}
