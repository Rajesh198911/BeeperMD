package Demo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.beepermd.utilities.ExcelUtil;

public class ExcelDataProvider {
	static String excelPath = "Files\\testingnew.xlsx";
	static String sheetName = "test";

	@Test(dataProvider = "test")
	public void test1(String coupon, String RequestedTestingAppointmentDate, String Time, String SelectRecurring,
			String RequestedTestingAddress, String City, String State, String ZipCode, String Inbuilding,
			String FirstName, String LastName, String Gender, String DOB, String CellPhone, String Email,
			String ReceiveNotificationsFromUs, String TypeOfCovidTest, String AppointmentBooked) {
		
//		System.out.println(coupon + " " + RequestedTestingAppointmentDate);
	}

	@DataProvider(name = "test")
	public Object[][] getData() {
		Object data[][] = testData(excelPath, sheetName);
		return data;
	}

	public Object[][] testData(String excelPath, String sheetName) {
		ExcelUtil excel = new ExcelUtil(excelPath, sheetName);

		int rowCount = excel.getRowCount();
		int colCount = excel.getColCount();

		Object data[][] = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {

				String cellData = excel.getCellDataString(i, j);
				// System.out.println(cellData);
				data[i - 1][j] = cellData;
			}
		//	System.out.println();
		}
		return data;
	}
}
