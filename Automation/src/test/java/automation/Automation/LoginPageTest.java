package automation.Automation;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import automation.CommonUtilities.DriverManager;
import automation.CommonUtilities.InitializeResources;
import automation.CommonUtilities.PropertyReader;
import automation.CommonUtilities.UtilitityFunctionsManager;
import automation.Logging.LogManager;
import automation.PageObjects.LoginPageObjects;
import automation.PageObjects.SignInPageObjects;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


/**This class contains tests for Login functionality
 * 
 * @author anil Kaushik
 * 
 * */
public class LoginPageTest {
	
	

	LoginPageObjects lpo;
	AndroidDriver<AndroidElement> driver;
	UtilitityFunctionsManager ufm;
	SignInPageObjects si;
	WebDriverWait wait;
	
	@BeforeClass
	public void testSetup()
	{
		try {
		driver=DriverManager.getDriver();
		ufm=new UtilitityFunctionsManager(driver);
		lpo=new LoginPageObjects(driver);
		si=new SignInPageObjects(driver);
		wait=new WebDriverWait(driver, 60);
	
		}
		catch(Exception e)
		{
			LogManager.logException(e);
		}
		
	}
	
	//verify login page create account lable element
	@Test(priority=0)
	public void validateCreateAccountLableTest()
	{
		String   lableText=UtilitityFunctionsManager.getText(lpo.createAccountRadioButton());

		Assert.assertEquals(InitializeResources.prop.getValue("createAccountLable"),  lableText);
		
		
	}
	
	//verify login page Login lable element
	@Test(priority=1)
	public void validateLoginAccountLableTest()
	{
		String   lableText=UtilitityFunctionsManager.getText(lpo.loginAccountRadioButton());
		Assert.assertEquals(InitializeResources.prop.getValue("loginLable"), lableText);
		
		
	}
	
	
	// verify invalid credentials test
	
	@Test(priority=2,enabled=false)
	public void validateInvalidUserDetails()
	{
		
		UtilitityFunctionsManager.setText(lpo.getAccountInfoBox(), InitializeResources.prop.getValue("username"));
		boolean isTapSuccessful=UtilitityFunctionsManager.tapElement(lpo.getContinuebuttonElement());
		Assert.assertTrue(isTapSuccessful);
		
		UtilitityFunctionsManager.setText(lpo.getPasswordFieldElement(), InitializeResources.prop.getValue("wrongPassword"));
		String  propVal=UtilitityFunctionsManager.getElementProperty(lpo.getcheckBoxElement(), "checked");
		
		// assert show password checkbox is by default checked
		Assert.assertTrue(Boolean.parseBoolean(propVal));
		isTapSuccessful=UtilitityFunctionsManager.tapElement(lpo.getLoginButtonElement());
		Assert.assertTrue(isTapSuccessful);
		
		WebElement errorMEssageElement1=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("LoginErrorMessage1"), "xpath");
		String errorMessage1=UtilitityFunctionsManager.getElementProperty(errorMEssageElement1, "text");
		Assert.assertEquals(InitializeResources.prop.getValue("expectedErrorMessage1"), errorMessage1);
		
		WebElement errorMEssageElement2=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("LoginErrorMessage2"), "xpath");
		String errorMessage2=UtilitityFunctionsManager.getElementProperty(errorMEssageElement2, "text");
		Assert.assertEquals(InitializeResources.prop.getValue("expectedErrorMessage2"), errorMessage2);
		UtilitityFunctionsManager.pressBackButton();
		//Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(si.getSignInButton()));
		UtilitityFunctionsManager.tapElement(si.getSignInButton());
		
		
		
		
	}
	
	
	//valid credentials test
	@Test(priority=3)
	public void validateSuccessfulLogin()
	{
		
		
		
		UtilitityFunctionsManager.setText(lpo.getAccountInfoBox(), InitializeResources.prop.getValue("username"));
		boolean isTapSuccessful=UtilitityFunctionsManager.tapElement(lpo.getContinuebuttonElement());
		Assert.assertTrue(isTapSuccessful);
		
		UtilitityFunctionsManager.setText(lpo.getPasswordFieldElement(), InitializeResources.prop.getValue("password"));
		String  propVal=UtilitityFunctionsManager.getElementProperty(lpo.getcheckBoxElement(), "checked");
		
		// assert show password checkbox is by default checked
		Assert.assertTrue(Boolean.parseBoolean(propVal));
		isTapSuccessful=UtilitityFunctionsManager.tapElement(lpo.getLoginButtonElement());
		Assert.assertTrue(isTapSuccessful);
		
		wait.until(ExpectedConditions.textToBePresentInElement(lpo.getHomeScreenElement(),InitializeResources.prop.getValue("amazonHomeScreenVerifier")));
		String val=UtilitityFunctionsManager.getElementProperty(lpo.getHomeScreenElement(), "text");
		Assert.assertEquals(InitializeResources.prop.getValue("amazonHomeScreenVerifier"), val);
		
		
		
		
	}
	
	
	@AfterClass
	public void tearDown()
	{
		try {
	
		}
		catch(Exception e)
		{
			LogManager.logException(e);
		}
		
	}

}
