package TestCases;

import PageObjects.RegisterPage;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import Utilities.ExcelUtil;

import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

@Epic("Student Registration Automation")
@Feature("Data-Driven Testing")
public class RegisterTest {
    WebDriver driver;
    RegisterPage registerPage;
    XSSFWorkbook outputWorkbook;
    XSSFRow outputRow;
    int currentRow; // Track the current row for output
    // Setup method to initialize WebDriver and load the Excel file
    
    @BeforeTest
    @Step("Setup WebDriver and Load Excel")
    public void setup() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
       
       
        driver.get("https://demoqa.com/automation-practice-form"); // Update to the new URL
        registerPage = new RegisterPage(driver);
        setBrowserZoom(55); 
        // Load Excel data from file
        ExcelUtil.loadExcel("src/test/resources/TestData.xlsx");
        
        outputWorkbook = new XSSFWorkbook(); // Create output workbook for results
        outputRow = outputWorkbook.createSheet("Test Results").createRow(0); // Create header row
        setupOutputHeaders();
        currentRow = 1; // Start writing from the second row
    }
    private void setupOutputHeaders() {
        outputRow.createCell(0).setCellValue("First Name");
        outputRow.createCell(1).setCellValue("Last Name");
        outputRow.createCell(2).setCellValue("Email");
        outputRow.createCell(3).setCellValue("Gender");
        outputRow.createCell(4).setCellValue("Mobile");
        outputRow.createCell(5).setCellValue("Date of Birth");
        outputRow.createCell(6).setCellValue("Subjects");
        outputRow.createCell(7).setCellValue("Hobbies");
        outputRow.createCell(8).setCellValue("Picture");
        outputRow.createCell(9).setCellValue("Address");
        outputRow.createCell(10).setCellValue("State");
        outputRow.createCell(11).setCellValue("City");
        outputRow.createCell(12).setCellValue("Result");
    }

    // Test method using data from the DataProvider
    @Test(dataProvider = "registerData")
    @Step("Fill the form with data")
    public void registerTest(String firstName, String lastName, String email, String gender, String mobile,
                             String dob, String subjects, String hobbies, String picturePath, String address,
                             String state, String city) {
    	 String result; // To hold the result of the submission
    	try {
        registerPage.enterFirstName(firstName);
        registerPage.enterLastName(lastName);
        registerPage.enterEmail(email);
        registerPage.selectGender(gender); 
        registerPage.enterMobileNumber(mobile);
        registerPage.enterDateOfBirth(dob);
        registerPage.selectHobbies(hobbies);
       
        registerPage.enterSubjects(subjects);
        
        registerPage.enterCurrentAddress(address);
        registerPage.selectStateAndCity(state, city);
        registerPage.uploadPicture(picturePath);
        registerPage.clickSubmit();
        registerPage.closeModal();
        result = "Success"; // Set result as Success
    	}
    	catch(Exception e){
    		 result = "Failed: " + e.getMessage(); // Capture failure message
    	}
		/*;
		 * registerPage.uploadPicture(picturePath);
		 registerPage.uploadPicture(picturePath);
		 * registerPage.enterSubjects(subjects);
		 * registerPage.enterCurrentAddress(address);
		 *  registerPage.enterSubjects(subjects);
		 * registerPage.selectStateAndCity(state, city); 
		 * registerPage.clickSubmit();
		 * registerPage.closeModal();
		 */ // Close the modal after form submission
        writeTestResult(firstName, lastName, email, gender, mobile, dob, subjects, hobbies, picturePath, address, state, city, result);
    }
    
    private void writeTestResult(String firstName, String lastName, String email, String gender, String mobile,
            String dob, String subjects, String hobbies, String picturePath, String address,
            String state, String city, String result) {
        System.out.println("Writing results for: " + firstName + " " + lastName);
        XSSFRow row = outputWorkbook.getSheetAt(0).createRow(currentRow++);
        row.createCell(0).setCellValue(firstName);
        row.createCell(1).setCellValue(lastName);
        row.createCell(2).setCellValue(email);
        row.createCell(3).setCellValue(gender);
        row.createCell(4).setCellValue(mobile);
        row.createCell(5).setCellValue(dob);
        row.createCell(6).setCellValue(subjects);
        row.createCell(7).setCellValue(hobbies);
        row.createCell(8).setCellValue(picturePath);
        row.createCell(9).setCellValue(address);
        row.createCell(10).setCellValue(state);
        row.createCell(11).setCellValue(city);
        row.createCell(12).setCellValue(result); // Write the result
        System.out.println("Result written for: " + firstName + " " + lastName);
}


    // DataProvider method to get test data from the Excel file
    @DataProvider(name = "registerData")
    public Object[][] getData() throws Exception {
        int rowCount = ExcelUtil.getRowCount(0);  // Get number of rows in the first sheet
        Object[][] data = new Object[rowCount][12];  // Assuming 12 columns in the Excel file
        System.out.println(rowCount);
        // Loop through Excel rows to populate the data array
        for (int i = 0; i < rowCount; i++) {
            // Retrieve data from the Excel sheet
            // Retrieve data from the Excel sheet
			/*
			 * String firstName = ExcelUtil.getCellData(0, i, 0); // First Name (column 0)
			 * System.out.println("Row " + i + " - First Name: " + firstName);
			 * 
			 * String lastName = ExcelUtil.getCellData(0, i, 1); // Last Name (column 1)
			 * System.out.println("Row " + i + " - Last Name: " + lastName);
			 * 
			 * String email = ExcelUtil.getCellData(0, i, 2); // Email (column 2)
			 * System.out.println("Row " + i + " - Email: " + email);
			 * 
			 * String gender = ExcelUtil.getCellData(0, i, 3); // Gender (column 3)
			 * System.out.println("Row " + i + " - Gender: " + gender);
			 * 
			 * String mobileNumber = ExcelUtil.getCellData(0, i, 4); // Mobile Number
			 * (column 4) System.out.println("Row " + i + " - Mobile Number: " +
			 * mobileNumber);
			 * 
			 * String dateOfBirth = ExcelUtil.getCellData(0, i, 5); // Date of Birth (column
			 * 5) System.out.println("Row " + i + " - Date of Birth: " + dateOfBirth);
			 * 
			 * String subjects = ExcelUtil.getCellData(0, i, 6); // Subjects (column 6)
			 * System.out.println("Row " + i + " - Subjects: " + subjects);
			 * 
			 * String hobbies = ExcelUtil.getCellData(0, i, 7); // Hobbies (column 7)
			 * System.out.println("Row " + i + " - Hobbies: " + hobbies);
			 * 
			 * String picturePath = ExcelUtil.getCellData(0, i, 8); // Picture Path (column
			 * 8) System.out.println("Row " + i + " - Picture Path: " + picturePath);
			 * 
			 * String currentAddress = ExcelUtil.getCellData(0, i, 9); // Current Address
			 * (column 9) System.out.println("Row " + i + " - Current Address: " +
			 * currentAddress);
			 * 
			 * String state = ExcelUtil.getCellData(0, i, 10); // State (column 10)
			 * System.out.println("Row " + i + " - State: " + state);
			 * 
			 * String city = ExcelUtil.getCellData(0, i, 11); // City (column 11)
			 * System.out.println("Row " + i + " - City: " + city);
			 * 
			 * // Check if any critical data is null and skip if true if (firstName == null
			 * || lastName == null || email == null || gender == null || mobileNumber ==
			 * null || dateOfBirth == null || subjects == null || hobbies == null ||
			 * picturePath == null || currentAddress == null || state == null || city ==
			 * null) { System.out.println("Skipping row " + i + " due to null values.");
			 * continue; // Skip this iteration }
			 */
            
            data[i][0] = ExcelUtil.getCellData(0, i, 0); // First Name (column 0)
            data[i][1] = ExcelUtil.getCellData(0, i, 1); // Last Name (column 1)
            data[i][2] = ExcelUtil.getCellData(0, i, 2); // Email (column 2)
            data[i][3] = ExcelUtil.getCellData(0, i, 3); // Gender (column 3)
            data[i][4] = ExcelUtil.getCellData(0, i, 4); // Mobile Number (column 4)
            data[i][5] = ExcelUtil.getCellData(0, i, 5); // Date of Birth (column 5)
            data[i][6] = ExcelUtil.getCellData(0, i, 6); // Subjects (column 6)
            data[i][7] = ExcelUtil.getCellData(0, i, 7); // Hobbies (column 7)
            data[i][8] = ExcelUtil.getCellData(0, i, 8); // Picture Path (column 8)
            data[i][9] = ExcelUtil.getCellData(0, i, 9); // Current Address (column 9)
            data[i][10] = ExcelUtil.getCellData(0, i, 10); // State (column 10)
            data[i][11] = ExcelUtil.getCellData(0, i, 11); // City (column 11)
            
         // Debugging output to track what data is being read
            System.out.println("Row " + i + ": " +
                "First Name: " + data[i][0] + ", " +
                "Last Name: " + data[i][1] + ", " +
                "Email: " + data[i][2] + ", " +
                "Gender: " + data[i][3] + ", " +
                "Mobile: " + data[i][4] + ", " +
                "DOB: " + data[i][5] + ", " +
                "Subjects: " + data[i][6] + ", " +
                "Hobbies: " + data[i ][7] + ", " +
                "Picture Path: " + data[i ][8] + ", " +
                "Address: " + data[i ][9] + ", " +
                "State: " + data[i][10] + ", " +
                "City: " + data[i ][11]);
        }
        
        return data;  // Return the test data to be used in the test method
    }
    
    @AfterSuite
    @Step("Teardown and save test results")
    public void tearDown() throws IOException {
        // Save the output Excel file
        FileOutputStream fos = new FileOutputStream("src/test/resources/Output.xlsx");
        outputWorkbook.write(fos);
        fos.close();

        // Close all resources
        ExcelUtil.closeWorkbook();
        if (outputWorkbook != null) {
            outputWorkbook.close(); // Close the output workbook
        }
        if (driver != null) {
            driver.quit(); // Quit the WebDriver
        }
    }
    // Method to set browser zoom level
    private void setBrowserZoom(int percentage) {
        String zoomScript = "document.body.style.zoom = '" + percentage + "%';";
        ((JavascriptExecutor) driver).executeScript(zoomScript);
    }

}
