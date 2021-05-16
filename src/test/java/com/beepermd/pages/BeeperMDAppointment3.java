package com.beepermd.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.beepermd.base.Base;
import com.beepermd.model.AppointmentModel;
import com.beepermd.utilities.ExcelUtil;

public class BeeperMDAppointment3 extends Base {

	static String excelPath = "C:\\Users\\rajes\\Desktop\\data.xlsx";
	static String sheetName = "test";
	int seconds = 80;
	int rowCount;

	@DataProvider(name = "test", parallel=true)
	public Object[][] getData() {
		Object data[][] = testData(excelPath, sheetName);
		return data;
	}

	public Object[][] testData(String excelPath, String sheetName) {
		ExcelUtil excel = new ExcelUtil(excelPath, sheetName);

		rowCount = excel.getRowCount();
		int colCount = excel.getColCount();

		Object data[][] = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {

				String cellData = excel.getCellDataString(i, j);
				data[i - 1][j] = cellData;

			}
		}
		return data;
	}

	@Test(dataProvider = "test")
	public void scheduleYourTestNew(String coupon, String RequestedTestingAppointmentDate, String Time,
			String SelectRecurring, String RequestedTestingAddress, String City, String State, String ZipCode,
			String Inbuilding, String FirstName, String LastName, String Gender, String DOB, String CellPhone,
			String Email, String ReceiveNotificationsFromUs, String TypeOfCovidTest, String TotalAdditionalMembers,
			String MembersInfo) throws Exception {

	

			// Click on schedule your test button
			VisibilityOfElementByXpath(
					"//p[contains(text(),'*For instant results, Antigen and Antibody tests are')]/following-sibling::a",
					10).click();

			// Validate sign-up form open
			Assert.assertTrue(VisibilityOfElementByXpath(
					"//h3[starts-with(normalize-space(text()),'Appointment Information')]", 10).isDisplayed());

			// Coupon
			if (!coupon.equalsIgnoreCase("no")) {

				// Enter coupon
				VisibilityOfElementByXpath("//input[@id='groupCode']", 10).sendKeys(coupon);

				// Click on apply coupon button
				VisibilityOfElementByXpath("//button[@id='groupCodeApplyButton']", 10).click();
				Thread.sleep(seconds);
			}

			// Enter Requesting Testing Appointment Date
			VisibilityOfElementByXpath("//input[@id='actualScheduledDate']", 10)
					.sendKeys(RequestedTestingAppointmentDate);
			Thread.sleep(1000);

			VisibilityOfElementByXpath("//input[@id='actualScheduledDate']", 10).sendKeys(Keys.ENTER);

			Thread.sleep(seconds);

			// Enter Time
			VisibilityOfElementByXpath("//input[@id='actualScheduledTime']", 10).sendKeys(Time);

			VisibilityOfElementByXpath("//input[@id='actualScheduledTime']", 10).sendKeys(Keys.ENTER);
			Thread.sleep(seconds);

			// Select Recurring
			Select select_recurring = new Select(VisibilityOfElementByXpath("//select[@id='recurringTypes']", 10));
			select_recurring.selectByVisibleText(SelectRecurring);
			Thread.sleep(seconds);

			// Enter Requested Testing Address
			VisibilityOfElementByXpath("//input[@id='address']", 10).sendKeys(RequestedTestingAddress);
			Thread.sleep(seconds);

			// Enter City
			VisibilityOfElementByXpath("//input[@id='city']", 10).sendKeys(City);
			Thread.sleep(seconds);

			// Select State
			Select select_state = new Select(VisibilityOfElementByXpath("//select[@id='state']", 10));
			select_state.selectByVisibleText(State);
			Thread.sleep(seconds);

			// Enter zip code
			VisibilityOfElementByXpath("//input[@id='zip']", 10).sendKeys(ZipCode);
			Thread.sleep(seconds);

			// Please check, if you are in a building
			if (Inbuilding.equalsIgnoreCase("yes")) {
				VisibilityOfElementByXpath("//input[@id='parkingFeeEnabled']/following-sibling::label/span", 10)
						.click();
				Thread.sleep(seconds);
			}

			// Returning Patients Click here (Pending to implement)

			// Enter First Name
			VisibilityOfElementByXpath(
					"//h6[starts-with(normalize-space(text()),'If you are already registered')]/following-sibling::div//input[@id='firstName']",
					10).sendKeys(FirstName);
			Thread.sleep(seconds);

			// Enter Last Name
			VisibilityOfElementByXpath(
					"//h6[starts-with(normalize-space(text()),'If you are already registered')]/following-sibling::div//input[@id='lastName']",
					10).sendKeys(LastName);
			Thread.sleep(seconds);

			// Select Gender
			Select select_gender = new Select(VisibilityOfElementByXpath(
					"//h6[starts-with(normalize-space(text()),'If you are already registered')]/following-sibling::div//select[@name='gender']",
					10));
			select_gender.selectByVisibleText(Gender);
			Thread.sleep(seconds);

			// DOB
			VisibilityOfElementByXpath(
					"//h6[starts-with(normalize-space(text()),'If you are already registered')]/following-sibling::div//input[@name='dob']",
					10).sendKeys(DOB);

			VisibilityOfElementByXpath(
					"//h6[starts-with(normalize-space(text()),'If you are already registered')]/following-sibling::div//input[@name='dob']",
					10).sendKeys(Keys.ENTER);
			Thread.sleep(seconds);

			// Cell Phone
			WebElement cell = VisibilityOfElementByXpath(
					"//h6[starts-with(normalize-space(text()),'If you are already registered')]/following-sibling::div//input[@name='phone']",
					10);

			TypeInFieldSlowly(cell, CellPhone);
			Thread.sleep(seconds);

			// Cell Email
			VisibilityOfElementByXpath(
					"//h6[starts-with(normalize-space(text()),'If you are already registered')]/following-sibling::div//input[@name='email']",
					10).sendKeys(Email);
			Thread.sleep(seconds);

			// Check if you wish to receive notifications from us regarding your appointment
			if (!ReceiveNotificationsFromUs.equalsIgnoreCase("yes")) {
				VisibilityOfElementByXpath("//div[4]/div/div/label/span", 10).click();
				Thread.sleep(seconds);
			}

			// NEXT
			VisibilityOfElementByXpath("//button[@id='checkPrimaryContactPatientAlreadyExistOrNotButton']", 10).click();
			Thread.sleep(seconds);

			// Validate next page email
			Assert.assertTrue(VisibilityOfElementByXpath("//div[@id='email']", 10).getText().contains(Email));
			Thread.sleep(seconds);

			// Validate text
			Assert.assertTrue(
					VisibilityOfElementByXpath("//div[contains(text(),'You need to pay')]", 10).isDisplayed());
			Thread.sleep(seconds);

//			ADD MEMEBER 
			// Convert total additional member string to integer
			int total_additional_members = 0;
			try {
				total_additional_members = Integer.parseInt(TotalAdditionalMembers);
			} catch (Exception e) {

			}

			if (total_additional_members >= 1) {

				for (int i = 0; i < total_additional_members; i++) {
					// Add Member Button
					VisibilityOfElementByXpath(
							"//i[contains(text(),'(Adding a member will remove ALL the extra fees)')]", 10).click();
					Thread.sleep(50);
				}

				// WebElement for patient information fields
				List<WebElement> first_name = VisibilityOfAllElementsByXpath(
						"//h6[starts-with(normalize-space(text()),'*Please complete the form for each')]/following-sibling::div[1]//input[@id='firstName']",
						10);
				List<WebElement> last_name = VisibilityOfAllElementsByXpath(
						"//h6[starts-with(normalize-space(text()),'*Please complete the form for each')]/following-sibling::div[1]//input[@id='lastName']",
						10);
				List<WebElement> gen = VisibilityOfAllElementsByXpath(
						"//h6[starts-with(normalize-space(text()),'*Please complete the form for each')]/following-sibling::div[1]//select[@name='gender']",
						10);
				List<WebElement> birth = VisibilityOfAllElementsByXpath(
						"//h6[starts-with(normalize-space(text()),'*Please complete the form for each')]/following-sibling::div[1]//input[@name='dob']",
						10);
				List<WebElement> ph = VisibilityOfAllElementsByXpath(
						"//h6[starts-with(normalize-space(text()),'*Please complete the form for each')]/following-sibling::div[1]//input[@name='phone']",
						10);
				List<WebElement> mail = VisibilityOfAllElementsByXpath(
						"//h6[starts-with(normalize-space(text()),'*Please complete the form for each')]/following-sibling::div[1]//input[@name='email']",
						10);

				int s = 0;

				
				String add_mem = MembersInfo;
				String[] splits = add_mem.split("[,]");
				AppointmentModel app = null;
				app = new AppointmentModel();

				for (String myStr : splits) {

					if (myStr.contains("Patient Information")) {
						

					}

					else {

						if (myStr.contains("fname")) {
							app.setFname(myStr.split("=")[1].trim());
							// System.out.println(app.getFname());
							first_name.get(s).sendKeys(app.getFname());
						}

						else if (myStr.contains("lname")) {
							app.setLname(myStr.split("=")[1].trim());
							// System.out.println(app.getLname());
							last_name.get(s).sendKeys(app.getLname());
						}

						else if (myStr.contains("gender")) {
							app.setGender(myStr.split("=")[1].trim());
							// System.out.println(app.getGender());
							gen.get(s).sendKeys(app.getGender());

						}

						else if (myStr.contains("dob")) {
							app.setDob(myStr.split("=")[1].trim());
							// System.out.println(app.getDob());
							birth.get(s).sendKeys(app.getDob());
							Thread.sleep(50);
							birth.get(s).sendKeys(Keys.ENTER);
						}

						else if (myStr.contains("cellphone")) {
							app.setCellphone(myStr.split("=")[1].trim());
							// System.out.println(app.getCellphone());
							ph.get(s).clear();
							Thread.sleep(seconds);
							TypeInFieldSlowly(ph.get(s), app.getCellphone());

						}

						else if (myStr.contains("email")) {
							app.setEmail(myStr.split("=")[1].trim());
							// System.out.println(app.getEmail());
							mail.get(s).clear();
							Thread.sleep(seconds);
							mail.get(s).sendKeys(app.getEmail());
							s = s + 1;
						}

					}

				}
			}

			// Set payment amount
			Thread.sleep(150);
			String amount = VisibilityOfElementByXpath("//div[@id='paymentAmount']", 10).getText().replace("$", "")
					.replace(".00", "");

			AppointmentModel.setPayment_amount(Integer.parseInt(amount));
			System.out.println("Customer needs to pay=" + " $" + AppointmentModel.getPayment_amount());
			// NEXT
			VisibilityOfElementByXpath(
					"//button[@class='btn bm-btn customButton  mr-2 mb-sm-3 mb-md-0']/following-sibling::button", 10)
							.click();
			Thread.sleep(seconds);

			// Validate next page title
			Assert.assertTrue(
					VisibilityOfElementByXpath("//h3[contains(text(),'Appointment Type')]", 10).isDisplayed());
			Thread.sleep(seconds);

			// Select type of Covid Test
			ArrayList<String> type_of_covid_test = new ArrayList<String>();
			String tests = TypeOfCovidTest;
			String[] split = tests.split("[,]", 0);
			for (String myStr : split) {
				type_of_covid_test.add(myStr.trim());
			}

			for (int k = 0; k < type_of_covid_test.size(); k++) {

				// Unselect the default covid test
				VisibilityOfElementByXpath("//div[contains(text(),'Covid-19 PCR Screening')]", 10).click();
				Thread.sleep(70);

				if (type_of_covid_test.get(k).contains("Covid-19 PCR Screening")) {
					VisibilityOfElementByXpath("//div[contains(text(),\"" + type_of_covid_test.get(k) + "\")]", 10)
							.click();
					Thread.sleep(200);
				}

				if (type_of_covid_test.get(k).contains("Rapid Antigen Test")) {
					VisibilityOfElementByXpath("//div[contains(text(),\"" + type_of_covid_test.get(k) + "\")]", 10)
							.click();
					Thread.sleep(200);
				}

				if (type_of_covid_test.get(k).contains("Rapid Antibody Test")) {
					VisibilityOfElementByXpath("//div[contains(text(),\"" + type_of_covid_test.get(k) + "\")]", 10)
							.click();
					Thread.sleep(200);
				}

			}

			// Submit appointment
			VisibilityOfElementByXpath("//button[@id='submitButton']", 10).click();
			
			if (AppointmentModel.getPayment_amount() > 0) {
				stripePayment();
			}

//			if (total_additional_members == 0 && RequestedTestingAppointmentDate.equalsIgnoreCase(GetCurrentDate())
//					&& !coupon.equalsIgnoreCase("automation")) {
//				System.out.println("Paid");
//				stripePayment();
//			}
//
//			else if (total_additional_members == 0
//					&& !RequestedTestingAppointmentDate.equalsIgnoreCase(GetCurrentDate())
//					&& !coupon.equalsIgnoreCase("automation") && Inbuilding.equalsIgnoreCase("yes")) {
//				stripePayment();
//			}

			// Validate success message
			Assert.assertTrue(VisibilityOfElementByXpath(
					"//span[contains(text(),'We have sent an email with a link to upload')]", 30).isDisplayed());

			// Redirect user back to home page
			getDriver().navigate().to(url);

			Thread.sleep(seconds);
		

	}

	public void stripePayment() {

		// Check stripe page displayed
		Assert.assertTrue(VisibilityOfElementByXpath("//span[contains(text(),'Fee for scheduling appointment')]", 10)
				.isDisplayed());

		// Input card number
		VisibilityOfElementByXpath("//input[@id='cardNumber']", 15).sendKeys("4242424242424242");

		// Input card expire
		VisibilityOfElementByXpath("//input[@id='cardExpiry']", 10).sendKeys("10/32");

		// Input cvc
		VisibilityOfElementByXpath("//input[@id='cardCvc']", 10).sendKeys("222");

		// Input car holder name
		VisibilityOfElementByXpath("//input[@id='billingName']", 10).sendKeys("Test");

		// Select country
		Select country = new Select(VisibilityOfElementByXpath("//select[@id='billingCountry']", 10));
		country.selectByVisibleText("India");

		// Submit
		VisibilityOfElementByXpath("//button[@class='SubmitButton SubmitButton--complete']", 10).click();

	}

}
