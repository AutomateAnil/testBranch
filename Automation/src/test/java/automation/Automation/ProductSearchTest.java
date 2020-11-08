package automation.Automation;

import java.util.List;

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

import automation.CommonUtilities.UtilitityFunctionsManager;
import automation.CommonUtilities.UtilitityFunctionsManager.Direction;
import automation.Logging.LogManager;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;

/**This class contains tests for product search,add to cart and check cart functionality
 * 
 * @author anil Kaushik
 * 
 * */
public class ProductSearchTest {
	
	AndroidDriver<AndroidElement> driver;
	UtilitityFunctionsManager ufm;
	String expectedPrice,actualPrice,expectedColor,ActualColor;
	String [] arrPrice,arrColor;
	WebDriverWait wait;
	
	@BeforeClass
	public void testSetup()
	{
		try {
		driver=DriverManager.getDriver();
		ufm=new UtilitityFunctionsManager(driver);
		wait=new WebDriverWait(driver, 30);
		}
		catch(Exception e)
		{
			LogManager.logException(e);
		}
	}
	
	// product serach test by brand and then click on specific product by scrolling to specific name
	@Test(priority = 0)
	public void SearchProduct()
	{
		try {
		WebElement element=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("locatorForSearchProductBox"), "xpath");
		element.click();
		Thread.sleep(3000);
		
		// example of Android key events
		UtilitityFunctionsManager.pressAnyKey(AndroidKey.N);
		UtilitityFunctionsManager.pressAnyKey(AndroidKey.O);
		UtilitityFunctionsManager.pressAnyKey(AndroidKey.I);
		UtilitityFunctionsManager.pressAnyKey(AndroidKey.S);
		UtilitityFunctionsManager.pressAnyKey(AndroidKey.E);
		
		UtilitityFunctionsManager.pressAnyKey(AndroidKey.ENTER);
		
		//vertical scroll
		UtilitityFunctionsManager.verticalScroll(InitializeResources.prop.getValue("scrollerClassName"), InitializeResources.prop.getValue("productTosearch"));
		
		
		List<AndroidElement>products=UtilitityFunctionsManager.getElements(InitializeResources.prop.getValue("productTitles"), "xpath");
		
		Assert.assertTrue(products.size()>1);
		for(AndroidElement el:products)
		{
			
			if(el.getText().equals(InitializeResources.prop.getValue("productTosearch"))) {
				
				boolean istapped=UtilitityFunctionsManager.tapElement(el);
				
				Assert.assertTrue(istapped);
				break;
			}
			
		}
		
		//verify product info page has appeared
		//UtilitityFunctionsManager.scrollIntoView("ADD TO WISH LIST");
		
		WebElement product=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("productName"));
		Assert.assertTrue(UtilitityFunctionsManager.elementState(product, "displayed"));
			}
		catch(Exception e)
		{
		LogManager.logException(e);
		}
		
	}
	
	// add to cart and verify count of product by scrolling according to screen size
	
	@Test(priority = 1)
	public void addToCart() throws InterruptedException
	{
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(InitializeResources.prop.getValue("cartXpath"))));
		WebElement element=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("cartXpath"), "xpath");
		String carValBeforeAddingProduct=UtilitityFunctionsManager.getElementProperty(element, "text");
		Assert.assertEquals(0, Integer.parseInt(carValBeforeAddingProduct));
		//UtilitityFunctionsManager.verticalScroll("android.widget.LinearLayout","Qty:");
		
		 // scroller to scroll down according to the screen size
		UtilitityFunctionsManager.swipeDown(20,90,20,20);
		expectedPrice=driver.findElement(By.xpath("//android.widget.EditText[starts-with(@text,'rupees')]")).getText();
		 arrPrice=expectedPrice.split(" ");
		expectedColor=driver.findElement(By.xpath("//android.widget.Button[starts-with(@text,' Colour:')]")).getText();
		 arrColor=expectedColor.split(":")[1].trim().split(" "); 
	
		 // scroller to scroll down according to the screen size
		UtilitityFunctionsManager.swipeDown(20,90,20,20);
		  UtilitityFunctionsManager.getElement("Add to Cart").click();
		 element=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("cartXpath"), "xpath"); 
		 String carValAfterAddingProduct=UtilitityFunctionsManager.getElementProperty(element, "text"); 
		  Assert.assertEquals(1,Integer.parseInt(carValAfterAddingProduct));
		
	
	
	}
	
	
	//verify cart page and match added product is right one by name,color,rate
	@Test(priority = 2)
	public void cart()
	{
		WebElement  element=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("cartXpath"), "xpath");
		boolean tapSuccessfull=UtilitityFunctionsManager.tapElement(element);
		Assert.assertTrue(tapSuccessfull);
		
		//prodcut name assertion
		WebElement productElement=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("productName"));
		String inCartProductName=UtilitityFunctionsManager.getElementProperty(productElement, "text");
		Assert.assertEquals(InitializeResources.prop.getValue("productName"), inCartProductName);
		
		
		//product rate assertion
		WebElement prodcutRate=driver.findElement(By.xpath("//android.view.View[ends-with(@text,'.00')]"));
		String productRate=prodcutRate.getAttribute("text");
		Assert.assertTrue(productRate.contains(arrPrice[1]));
		
		
		//product color assertion
		WebElement colorOfProduct=driver.findElement(By.xpath("//android.widget.TextView[starts-with(@text,'Noise ColorFit')]"));
		String color=colorOfProduct.getAttribute("text");
		Assert.assertTrue(color.contains(arrColor[1]));
		
				
		
		

		//procced to buy
		WebElement  proceedToBuyElement=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("proccedToBuyButton"));
		boolean state=UtilitityFunctionsManager.elementState(proceedToBuyElement, "displayed");
		Assert.assertTrue(state);
		
		// delete product from cart
		WebElement deleteProduct=driver.findElement(By.xpath("//android.widget.Button[@text='Delete']"));
		UtilitityFunctionsManager.tapElement(deleteProduct);
		
		// assert cart is empty after product delete
		WebElement cart=UtilitityFunctionsManager.getElement(InitializeResources.prop.getValue("cartXpath"), "xpath");
		String carValBeforeAddingProduct=UtilitityFunctionsManager.getElementProperty(cart, "text");
		Assert.assertEquals(0, Integer.parseInt(carValBeforeAddingProduct));
		
		
	}
	
	//logout from app
	@AfterClass
	public void tearDown()
	{
		try {
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.ImageView[@resource-id='com.amazon.mShop.android.shopping:id/action_bar_burger_icon']")));
			
			  driver.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.amazon.mShop.android.shopping:id/action_bar_burger_icon']")).click();
			  
			  UtilitityFunctionsManager.scrollIntoView("Settings");
			  
			  WebElement settingElement= UtilitityFunctionsManager.getElement("Settings");
			  
			  UtilitityFunctionsManager.tapElement(settingElement); WebElement
			  signoutLinkElement=
			  UtilitityFunctionsManager.getElement("Not anil? Sign out");
			  
			  UtilitityFunctionsManager.tapElement(signoutLinkElement);
			  
			  WebElement signoutElement= UtilitityFunctionsManager.getElement("SIGN OUT");
			  
			  UtilitityFunctionsManager.tapElement(signoutElement);
			 
		
		}
		catch(Exception e)
		{
			LogManager.logException(e);
		}
		
	}

}
