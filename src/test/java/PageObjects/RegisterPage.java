package PageObjects;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {
    WebDriver driver;
    

    // Constructor to initialize the elements
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // WebElements based on the XPaths you provided
    @FindBy(xpath = "//*[@id='firstName']")
    WebElement firstName;

    @FindBy(xpath = "//*[@id='lastName']")
    WebElement lastName;

    @FindBy(xpath = "//*[@id='userEmail']")
    WebElement email;

    @FindBy(xpath = "//label[@for='gender-radio-1']")
    WebElement maleGender;

    @FindBy(xpath = "//label[@for='gender-radio-2']")
    WebElement femaleGender;

    @FindBy(xpath = "//label[@for='gender-radio-3']")
    WebElement otherGender;

    @FindBy(xpath = "//*[@id='userNumber']")
    WebElement mobileNumber;

    @FindBy(xpath = "//input[@id='dateOfBirthInput']")
    WebElement dateOfBirth;

    @FindBy(xpath = "//*[@id='subjectsContainer']/div/div[1]")
    WebElement subjectsInput;

    @FindBy(xpath = "//label[@for='hobbies-checkbox-1']")
    WebElement hobbySports;

    @FindBy(xpath = "//label[@for='hobbies-checkbox-2']")
    WebElement hobbyReading;

    @FindBy(xpath = "//label[@for='hobbies-checkbox-3']")
    WebElement hobbyMusic;

    @FindBy(xpath = "//input[@id='uploadPicture']")
    WebElement pictureUpload;

    @FindBy(xpath = "//textarea[@id='currentAddress']")
    WebElement currentAddress;

    @FindBy(xpath = "//div[@id='state']")
    WebElement stateDropdown;

    @FindBy(xpath = "//div[@id='city']")
    WebElement cityDropdown;

    @FindBy(xpath = "//button[@id='submit']")
    WebElement submitButton;

    @FindBy(xpath = "//*[@id='closeLargeModal']")
    WebElement closeButton;

    // Methods to interact with the fields
    public void enterFirstName(String fname) {
        firstName.clear();
        firstName.sendKeys(fname);
    }

    public void enterLastName(String lname) {
        lastName.clear();
        lastName.sendKeys(lname);
    }

    public void enterEmail(String mail) {
        email.clear();
        email.sendKeys(mail);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            maleGender.click();
        } else if (gender.equalsIgnoreCase("Female")) {
            femaleGender.click();
        } else {
            otherGender.click();
        }
    }

    public void enterMobileNumber(String mobile) {
        mobileNumber.clear();
        mobileNumber.sendKeys(mobile);
    }



    public void enterDateOfBirth(String dob) {
        // Check if the input date is null or empty
        if (dob == null || dob.trim().isEmpty()) {
            System.err.println("DOB is empty or null. Cannot parse date.");
            return; // Exit the method if the date is not valid
        }
        
        try {
            // Initialize the date formatter
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            
            // Parse the input date
            Date date = formatter.parse(dob);
            System.out.println("Parsed Date: " + date);  // Log the parsed Date object

            // Extract day, month, and year from the parsed date
            SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.ENGLISH);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH); // Full month name (e.g., "October")
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

            String day = dayFormat.format(date);
            String month = monthFormat.format(date);  // Full month name for the dropdown
            String year = yearFormat.format(date);

            // Log extracted values
            System.out.println("Day: " + day);         // Log the day
            System.out.println("Month: " + month);     // Log the month
            System.out.println("Year: " + year);       // Log the year

            dateOfBirth.click();  // Click the date of birth input field
            
            // Select month
            WebElement monthDropdown = driver.findElement(By.className("react-datepicker__month-select"));
            Select selectMonth = new Select(monthDropdown);
            selectMonth.selectByVisibleText(month); // e.g., "October"

            // Select year
            WebElement yearDropdown = driver.findElement(By.className("react-datepicker__year-select"));
            Select selectYear = new Select(yearDropdown);
            selectYear.selectByVisibleText(year); // e.g., "1987"

            // Select the day
            WebElement dayElement = driver.findElement(By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='" + day + "']"));
            dayElement.click(); // Click the day

        } catch (ParseException e) {
            System.err.println("Error parsing DOB: " + dob + " - " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }


	/*
	 * public void enterSubjects(String subjects) { // Clear any existing input
	 * $("#subjectsInput").setValue("Maths").pressEnter(); // This assumes pressing
	 * Enter confirms the selection
	 * 
	 * }
	 */
    public void enterSubjects(String subjects) {
    	 WebElement subjectsInput = driver.findElement(By.xpath("//input[@id='subjectsInput']"));
    	// Clear the input field before entering new subjects
    	    subjectsInput.clear();
    	    // Split the input string by commas and trim any whitespace
    	    String[] subjectsArray = subjects.split(",");

    	    for (String subject : subjectsArray) {
    	        // Trim whitespace from the subject string
    	        subject = subject.trim();
    	        
    	        // Enter the subject
    	        subjectsInput.sendKeys(subject);
    	        subjectsInput.sendKeys(Keys.ENTER);
    	        
    	        // Optional: Add a small delay to allow the input to register (if needed)
    	        // Thread.sleep(500); // Use with caution; consider WebDriverWait for better practices
    	    }
    }

    public void selectHobbies(String hobbies) {
        if (hobbies.contains("Sports")) {
            hobbySports.click();
        }
        if (hobbies.contains("Reading")) {
            hobbyReading.click();
        }
        if (hobbies.contains("Music")) {
            hobbyMusic.click();
        }
    }

    public void uploadPicture(String pictureName) {
        // Construct the full path to the picture
        String picturePath = System.getProperty("user.dir") + "/src/test/resources/" + pictureName; // Full path
        System.out.println("Picture path: " + picturePath); 

        // Check if the file exists before attempting to upload
        File file = new File(picturePath);
        if (!file.exists()) {
            System.err.println("File does not exist: " + picturePath);
            return; // Exit the method if the file doesn't exist
        }

        // Locate the upload element
        WebElement uploadElement = driver.findElement(By.xpath("//input[@id='uploadPicture']"));
        
        // Use sendKeys to upload the picture
        uploadElement.sendKeys(picturePath); // Upload picture

        // Optional: Add confirmation or logging
        System.out.println("Uploaded picture: " + pictureName);
    }


    public void enterCurrentAddress(String address) {
        currentAddress.clear();
        currentAddress.sendKeys(address);
    }

    public void selectStateAndCity(String state, String city) {
       
     
     		driver.findElement(By.xpath("//div[@id='state']")).click();
     		driver.findElement(By.xpath("//div[text()='" + state + "']")).click();
     		
     	// City selection using XPath
    		driver.findElement(By.xpath("//div[@id='city']")).click();
    		driver.findElement(By.xpath("//div[text()='" + city + "']")).click();
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void closeModal() {
        closeButton.click();
    }
}
