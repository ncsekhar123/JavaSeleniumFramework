/**
 * 
 */
package seleniumframework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Thread;


import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.*;

public class TestBase {

	public static FileInputStream fis;
	public static Properties config = new Properties();
	public static Properties locators= new Properties();
	public static Properties testdata = new Properties();
	public static Properties log4j = new Properties();
	
	public static WebDriver driver=null;
		
	public static Logger logger = LogManager.getLogger(TestBase.class.getName());

	public static String browser;
	public static String appURL;
	
	public static String objuserName;
	public static String objpassWord;
	public static String objLogin;
	public static String objOrageHrmLogo;
	
	public static String tduserName;
	public static String tdpassWord;
	
	public static boolean testResult;
		
	
	public TestBase() {
		// TODO Auto-generated constructor stub
	}
	
	public void setup() throws IOException{
		
		logger.info("Logger Started ...");
		
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\web\\config.properties");
			        config.load(fis);
  			        logger.info("Config file is loaded !");
			        
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			logger.info("Config file - FileNotFoundException !");
			logger.debug("Config file - Debug information !");
		}
		
		// 2. Loading locators.properties file
		
		try {
		fis = new FileInputStream(
		System.getProperty("user.dir") + "\\src\\test\\resources\\web\\locators.properties");
		locators.load(fis);
		logger.info("Locators file is loaded !");

		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		
		// 3. Loading testdata.properties file
		
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\web\\testdata.properties");
					testdata.load(fis);
					logger.info("TestData file is loaded !");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			
			logger.info("Test Data File not found log Message ! " + e.getMessage() );
			logger.debug("Test Data File not found log Message ! " + e.getMessage() );
		}
		
	// Add code for reading values from properties file 

	/*public void openApp() {
		
		browser = config.getProperty("browser");
				
		if (config.getProperty("browser").equals("firefox")) {	} 
		else if (config.getProperty("browser").equals("chrome")) { 
		
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			
					driver = new ChromeDriver();
		} 
		else if (config.getProperty("browser").equals("ie")) {  }
		 
		*/
	
	// Selenium driver initiation and browser setup
	
	browser = config.getProperty("browser");
	logger.info("browserLaunch() - Browser is " + browser);
	
	/*
	if (config.getProperty("browser").equals("firefox")) {
		
		WebDriverManager.firefoxdriver().setup();
		logger.info("Browser is setup - " + browser);
   		driver = new FirefoxDriver();
   		logger.info("Selenium driver - " + browser);

	} 
	else if (config.getProperty("browser").equals("chrome")) { 
		
		WebDriverManager.chromedriver().setup();
		logger.info("Browser is setup - " + browser);
   		driver = new ChromeDriver();
   		logger.info("Selenium driver - " + browser);
	} 
	else if (config.getProperty("browser").equals("edge")) {
		
		WebDriverManager.edgedriver().setup();
		logger.info("Browser is setup - " + browser);
		driver = new EdgeDriver();
		logger.info("Selenium driver - " + browser);
	} */
		
	  // Selenium driver initiation and browser setup with equalsIngoreCase
	  // Note: browser = config.getProperty("browser");
	   
	if (browser.equalsIgnoreCase("firefox")) {
		
		WebDriverManager.firefoxdriver().setup();
		logger.info("Browser is setup - " + browser);
   		driver = new FirefoxDriver();
   		logger.info("Selenium driver - " + browser);
   		
		} 

	else if (browser.equalsIgnoreCase("chrome")) {  
		
		WebDriverManager.chromedriver().setup();
		logger.info("Browser is setup - " + browser);
   		driver = new ChromeDriver();
   		logger.info("Selenium driver - " + browser);
		
		} 
	else if (browser.equalsIgnoreCase("edge")) {  
		
		WebDriverManager.edgedriver().setup();
		logger.info("Browser is setup - " + browser);
		driver = new EdgeDriver();
		logger.info("Selenium driver - " + browser);
		
		}
	}
	
	// Method to open application URL
	public void openURL() {
	
		appURL = testdata.getProperty("appURL");
		logger.info("Application URL - " + appURL);
		
		driver.get(appURL);
		logger.info("URL Navigated - " + appURL);

	}
	
	// Login in to the application
	public void applogin() throws InterruptedException {
		
		
		objuserName = locators.getProperty("editUserName");
		logger.info("Locator Username - " + objuserName);
		
		objpassWord = locators.getProperty("editPassWord");
		logger.info("Locator Password- " + objpassWord);
		
		objLogin = locators.getProperty("btnLogin");
		logger.info("Locator Login - " + objLogin);
	
		tduserName = testdata.getProperty("userName");
		logger.info("TestData Username - " + tduserName);
		
		tdpassWord = testdata.getProperty("password");
		logger.info("TestData Password - " + tdpassWord);
		
		System.out.println(objuserName);
		System.out.println(objpassWord);
		System.out.println(tduserName);
		System.out.println(tdpassWord);
		
		Thread.sleep(1000);
			
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		driver.findElement(By.name(objuserName)).clear();
		logger.info("Username field cleared");
		driver.findElement(By.name(objuserName)).sendKeys(tduserName);
		logger.info("Username field - Data Entered - " + tduserName);
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		driver.findElement(By.name(objpassWord)).clear();
		logger.info("Password field cleared");
		driver.findElement(By.name(objpassWord)).sendKeys(tdpassWord);
		logger.info("Password field - Data Entered - " + tdpassWord);
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		driver.findElement(By.cssSelector(objLogin)).click();
		logger.info("Login Button Clicked ");
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
	}
	
	public void dashboardTest() {
		
		objOrageHrmLogo = locators.getProperty("ImgOrageHrmLogo");
		logger.info("Locator Homepage Logo" + objOrageHrmLogo);
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		
		
		testResult = driver.findElement(By.xpath(objOrageHrmLogo)).isDisplayed();
		logger.info("Test Result for is Displayed Method: " + testResult);
		
		if (testResult=true ) {
			System.out.println("Test is Pass... ");
			logger.info("Test Result is : " + testResult);
		}
		else if( testResult=false) {
			System.out.println("Test is Fail... ");
			logger.info("Test Result is : " + testResult);
		}
		else {
			System.out.println("Test Result is Error!!! ");
			logger.info("Test Result is Error: ");
		}
			
		}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		TestBase testObj=new TestBase();
		
		testObj.setup(); 		// access and load properties files

		testObj.openURL(); 		// open application url
		
		testObj.applogin();		// Application login
		
		testObj.dashboardTest();   	// Validation Test
	}

}
