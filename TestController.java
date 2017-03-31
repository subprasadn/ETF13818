package Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.hamcrest.core.IsNull;
import org.junit.AfterClass;
import org.junit.Test;
import ReportUtils.ReportUtil;
import TestUtils.TestResources;
import TestUtils.Utils;

public class TestController extends TestResources {
	public static String folderName;
	public static void main(String[] args) {
		try {
			new TestController().MainController();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void MainController() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {
		init();
		int TCRows = Suitereader.getRowCount("TestCases");
		System.out.println(TCRows);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String startTime = Utils.now("dd.MMMM.yyyy hh.mm.ss aaa");
		ReportUtil.startTesting(System.getProperty("user.dir")+"//src//Reports//index.html", startTime, "FRED", "2.7");
		ReportUtil.startSuite("FRED");
		// loop through Suite1 test cases
		for (int TC = 2; TC <= TCRows; TC++) {
			TestCaseName = Suitereader.getCellData("TestCases", "TestCase", TC);
			RunMode = Suitereader.getCellData("TestCases", "RunMode", TC);
			// skip test case
			if (RunMode.equals("N")) {
				continue;
			}
			int TDRows = Datareader.getRowCount(TestCaseName);
			if (TDRows < 2) {
				TDRows = 2;
			}
			// loop through test data
			String startTimeTD = Utils.now("dd.MMMM.yyyy hh.mm.ss aaa");
			String result = "Pass";
			// loop through the test steps
			for (int TS = 2; TS <= Suitereader.getRowCount(TestCaseName); TS++) {
				if (TestCaseName.contains("Verify_S3_UpdateForm_005") & TS == 2) {
					System.out.println("breakpoint");
				}
				Desc = Suitereader.getCellData(TestCaseName, "Desc", TS);
				Keyword = Suitereader.getCellData(TestCaseName, "Keyword", TS);
				Webelement = Suitereader.getCellData(TestCaseName, "Webelement", TS);
				Webelement1 = Suitereader.getCellData(TestCaseName, "Webelement1", TS);
				ProceedOnFail = Suitereader.getCellData(TestCaseName, "ProceedOnFail", TS);
				DataField = Suitereader.getCellData(TestCaseName, "DataField", TS);
				DataField1 = Suitereader.getCellData(TestCaseName, "DataField1", TS);
				DataField2 = Suitereader.getCellData(TestCaseName, "DataField2", TS);
				TD = 2;
				TD1 = 2;
				TD2 = 2;
				if (!(Suitereader.getCellData(TestCaseName, "RowNo", TS).equals(""))) {
					double TDRowvalue = Double.parseDouble(Suitereader.getCellData(TestCaseName, "RowNo", TS));

					TD = (int) TDRowvalue;
				}
				if (!(Suitereader.getCellData(TestCaseName, "RowNo1", TS).equals(""))) {
					double TDRowvalue1 = Double.parseDouble(Suitereader.getCellData(TestCaseName, "RowNo1", TS));
					TD1 = (int) TDRowvalue1;
				}
				if (!(Suitereader.getCellData(TestCaseName, "RowNo2", TS).equals(""))) {
					double TDRowvalue2 = Double.parseDouble(Suitereader.getCellData(TestCaseName, "RowNo2", TS));
					TD2 = (int) TDRowvalue2;
				}
				
				DataValue = Datareader.getCellData(TestCaseName, DataField, TD);
				System.out.println("DataValue == " + DataValue);
				DataValue1 = Datareader.getCellData(TestCaseName, DataField1, TD1);
				System.out.println("DataValue1 == " + DataValue1);
				DataValue2 = Datareader.getCellData(TestCaseName, DataField1, TD2);
				System.out.println("DataValue2 == " + DataValue2);
				String referredSheetColName = "Type";
				String value = Datareader.getCellData(Keyword, referredSheetColName, TD);
				System.out.println("output -> " + value);
				System.out.println(TestCaseName + " > TS" + (TS - 1) + " >" + Keyword);
				Method method = KeyWords.class.getMethod(Keyword);
				String status = (String) method.invoke(method);
				if (status.contains("Failed")) {
					if (ProceedOnFail.equals("N")) {
						break;
					}
					result = status;
					String fileName = TestCaseName + "_" + Keyword + "[" + (TD - 2) + "]" + "TS[" + (TS - 2) + "]";
					// take screen shot
					Utils.getScreenshot(fileName);
					fileName = "Screenshot\\" + fileName + ".png";
					// report error
					ReportUtil.addKeyword(Desc, Keyword, status, fileName);
				} else {
					String fileName = TestCaseName + "_" + Keyword + "[" + (TD - 2) + "]" + "TS[" + (TS - 2) + "]";
					// take screen shot
					Utils.getScreenshot(fileName);
					fileName = "Screenshot\\" + fileName + ".png";
					ReportUtil.addKeyword(Desc, Keyword, status, fileName);
				}
				if (Keyword.equals("end")) {
					break;
				}
			}
			ReportUtil.addTestCase(TestCaseName, startTimeTD, Utils.now("dd.MMMM.yyyy hh.mm.ss aaa"), result);
		}
		ReportUtil.endSuite();
		ReportUtil.updateEndTime(Utils.now("dd.MMMM.yyyy hh.mm.ss aaa"));
		
	}
	@AfterClass
	public static void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
