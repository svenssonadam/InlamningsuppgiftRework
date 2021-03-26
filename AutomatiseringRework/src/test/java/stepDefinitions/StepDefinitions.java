package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

	private WebDriver driver;
	Random randomGenerator = new Random();  
	int randomInt = randomGenerator.nextInt(10000);
	String longUsername = "username";
	String repeated = longUsername.repeat(13);
	String hundred = "Enter a value less than 100 characters long";
	String exists = "Another user with this username already exists. Maybe it's your evil twin. Spooky.";
	String value = "Please enter a value";
	
	@Before
	public void openBrowser() throws InterruptedException {
		DriveCreator creator = new DriveCreator();
		driver = creator.createBrowser("chrome");
	    driver.get("https://login.mailchimp.com/signup/");
	    driver.manage().window().maximize();
	    click(driver, By.id("onetrust-accept-btn-handler"));
	 
	}
		@Given("I have entered an {string} into the email slot")
		public void i_have_entered_an_into_the_email_slot(String email) {
		    if(email.equals("email")) {
		    	sendKeys(driver, By.id("email"), email + randomInt + "@gmail.com");
		    }else if(email.equals("")) {
		    	sendKeys(driver, By.id("email"), "");
		    }
		    
		}
		@Given("I have also entered an {string} into the username slot")
		public void i_have_also_entered_an_into_the_username_slot(String username) {
		    if(username.equals("username")) {
		    	sendKeys(driver, By.id("new_username"), username + randomInt);
		    }else if(username.equals("longUsername")) {
		    	sendKeys(driver, By.id("new_username"), repeated);
		    }else if(username.equals("Adam653")) {
		    	sendKeys(driver, By.id("new_username"), username);
		    }
		}
		@Given("I have also entered a {string} into the password slot")
		public void i_have_also_entered_a_into_the_password_slot(String password) {
			sendKeys(driver, By.id("new_password"), password);
		}
		@When("I press sign up")
		public void i_press_sign_up() {
			click(driver, By.id("create-account"));
		}
		@Then("I will {string}")
		public void i_will(String verify) {
			WebElement checkEmail = driver.findElement(By.id("signup-content"));
			WebElement error = driver.findElement(By.className("invalid-error"));
			WebElement error2 = driver.findElement(By.className("invalid-error"));
			WebElement error3 = driver.findElement(By.className("invalid-error"));
			
			if(verify.equals("Check your email")) {
				assertEquals(verify, checkEmail.getText());
				driver.quit();
			}else if(verify.equals(hundred)){
				assertEquals(verify, error.getText());
				driver.quit();
			}else if(verify.equals(exists)) {
				assertEquals(verify, error2.getText());
				driver.quit();
			}else if(verify.equals(value)){
				assertEquals(verify, error3.getText());
				driver.quit();
			
			}
		}

		public void click(WebDriver driver, By by) {
			(new WebDriverWait(driver,10)).until(ExpectedConditions.
		
					elementToBeClickable(by));
					driver.findElement(by).click();
			}
			public void sendKeys(WebDriver driver, By by, String keys) {
				(new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(by));
				
					driver.findElement(by).sendKeys(keys);

	}

}