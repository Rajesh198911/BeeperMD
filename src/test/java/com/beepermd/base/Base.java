package com.beepermd.base;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import com.beepermd.utilities.Custom_Listner;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

@Listeners(Custom_Listner.class)
public class Base {
	boolean page_load;
	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public String url = "http://54.163.228.123";

	@BeforeMethod
	@Parameters({ "Browsers", "Headless" })
	public void CheckBrowsers(String Browsers, String headless) throws Exception {
		if (Browsers.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
		}

		else if (Browsers.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");

			// Disable save password chrome dialog
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			// Disable chrome is being controlled by automated software
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

			// Headless browser run without UI
			if (headless.equalsIgnoreCase("true")) {
				options.addArguments("window-size=1366,768");
				options.addArguments("headless");
				System.out.println("Test will run in Headless Browser!");
			}

			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver(options));		
			}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		getDriver().get(url);

		// Validate login page displayed
		boolean isLoginPagedLoaded = VisibilityOfElementByXpath("//h1[contains(text(),'Protect Your Family')]", 30)
				.isDisplayed();
		Assert.assertEquals(isLoginPagedLoaded, true, "Staging is not loading!");

	}

	public WebDriver getDriver() {
		return driver.get();
	}

	// QUIT BROWSER
	@AfterMethod
	public void quit() throws Exception {
		Thread.sleep(5000);
		getDriver().quit();
	}

	// WAIT
	public WebDriverWait wait(WebDriver driver, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait;
	}

	// Visibility of element by xpath
	public WebElement VisibilityOfElementByXpath(String xpath, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	// Visibility all of elements by xpath
	public List<WebElement> VisibilityOfAllElementsByXpath(String xpath, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
	}

	// Visibility of element by ID
	public WebElement VisibilityOfElementByID(String ID, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ID)));
	}

	// Visibility of element by class name
	public WebElement VisibilityOfElementByClassName(String className, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
	}

	// Visibility of element by name
	public WebElement VisibilityOfElementByName(String Name, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Name)));
	}

	// Presence of element by xpath
	public WebElement PresenceOfElementByXpath(String xpath, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	// Presence all of elements by xpath
	public List<WebElement> PresenceOfAllElementsByXpath(String xpath, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
	}

	// Presence of element
	public WebElement PresenceOfWebElement(WebElement element, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	// Presence of all elements
	public List<WebElement> PresenceOfAllWebElements(By element, int time) {
		WebDriverWait wait = new WebDriverWait(getDriver(), time);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By) element));
	}

	// CAPTURE SCREENSHOT IF THE TEST GOT FAILED
	public void FullPageScreenshot(String screenshotName) throws Exception {
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(getDriver());
		ImageIO.write(fpScreenshot.getImage(), "PNG", new File("./Images/" + screenshotName + ".png"));

	}

	// DATE FORMATE CHANGE METHOD
	public static String date_format(String sdate) throws Exception {
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat formatter3 = new SimpleDateFormat("MMM dd, yyyy");
		Date date2 = formatter2.parse(sdate);
		String d = formatter3.format(date2);
		return d;
	}

	// OPEN NEW TAB IN BROWSER
	public void new_tab() throws Exception {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_T);
		robot.keyRelease(KeyEvent.VK_T);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(4000);
	}

	// HARD REFRESH PAGE
	public void hard_refresh_page() throws Exception {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	// SCROLL UNITL THE ELEMENT NOT FOUND
	public void scroll_until_ele_not_found(List<WebElement> Element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int i = Element.size();
		WebElement ele = Element.get(i - 1);
		js.executeScript("arguments[0].scrollIntoView();", ele);
	}

	// TYPE IN INPUT FIELD SLOWLY (SENDKEYS)
	public void TypeInFieldSlowly(WebElement ele, String value) throws Exception {
		String val = value;
		for (int i = 0; i < val.length(); i++) {
			char c = val.charAt(i);
			String s = new StringBuilder().append(c).toString();
			ele.sendKeys(s);
			Thread.sleep(10);
		}
	}

	// EXCEL CREATE ROW AND CELL TO WRITE
	public void ExcelCreateRowCreateCellAndWrite(int row, int column, String input_data, String file_path)
			throws Exception {
		File file = new File(file_path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		sheet.createRow(row).createCell(column).setCellValue(input_data);
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
	}

	// EXCEL GET ROW AND CREATE CELL TO WRITE
	public void ExcelGetRowCreateCellAndWrite(int row, int column, String input_data, String file_path)
			throws Exception {
		File file = new File(file_path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		sheet.getRow(row).createCell(column).setCellValue(input_data);
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
	}

	// EXCEL READ
	public String ExcelRead(int row, int column, String file_path) throws Exception {
		File file = new File(file_path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		DataFormatter dataformatter = new DataFormatter();
		String cell_value = dataformatter.formatCellValue(sheet.getRow(row).getCell(column));
		return cell_value;
	}

	// EXCEL GET TOTAL NUMBER OF ROWS
	public int ExcelGetNumberOfRows(String file_path) throws Exception {
		File file = new File(file_path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int total_rows = sheet.getLastRowNum();
		return total_rows;
	}

	// EXCEL READ COLUMN NAME
	public String ExcelReadColumnName(int row_num, String column_name, String file_path) throws Exception {
		File file = new File(file_path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(0);
		DataFormatter dataformatter = new DataFormatter();

		int colNum = -1;
		for (int i = 0; i < row.getLastCellNum(); i++) {

			if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(column_name)) {
				colNum = i;
			}
		}

		return dataformatter.formatCellValue(sheet.getRow(row_num).getCell(colNum));
	}

	// EXCEL WRITE COLUMN NAME
	public void ExcelWriteColumnName(int row_num, String column_name, String file_path, String set_message)
			throws Exception {
		File file = new File(file_path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(0);

		int colNum = -1;
		for (int i = 0; i < row.getLastCellNum(); i++) {

			if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(column_name)) {
				colNum = i;
			}
		}

		sheet.getRow(row_num).createCell(colNum).setCellValue(set_message);
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);

	}

	// PRINT CURRENT DATE AND TIME
	public String GetCurrentDate() {
		// Create object of SimpleDateFormat class and decide the format for date and
		// time
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		// get current date time with Date()
		Date date = new Date();
		// Now format the date
		String date1 = dateFormat.format(date);
		return date1;
	}

}
