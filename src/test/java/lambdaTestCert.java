//This is maven project

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class lambdaTestCert {

	public RemoteWebDriver driver = null;
    String username = "ngngocthuong1987";
    String accessKey = "R97JqltUyfkMyhulP0NvSMUtamqReZomQ8peBX9q5xxFTqqYZ6";
    String url = "https://www.lambdatest.com/";
    int implicitWaitTimeout = 20;
    int explicitWaitTimeout = 15;
    JavascriptExecutor js;

    //Locators
    By seeAllIntegrationsLink = By.xpath("//a[text()='See All Integrations']");
    By codelessAutomationTitle = By.xpath("//h2[text()='Codeless Automation']");
    By learMoreTestingWhizLink = By.xpath("//h3[text()='Testing Whiz']/following-sibling::a");

	@BeforeTest
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("version", "92.0"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("resolution","1024x768");
        capabilities.setCapability("build", "First Test");
        capabilities.setCapability("name", "Sample Test");
        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true); // To capture console logs
    
//        try {
//			driver = new RemoteWebDriver(new URL("https://"+username+":"+accessKey+"@hub.lambdatest.com/wd/hub"), capabilities);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.SECONDS);
			driver.manage().window().maximize();
            js = driver;
//        } catch (MalformedURLException e) {
//            System.out.println("Invalid grid URL");
//        }
    }

	@Test()
	public void testScript() {
        try {
            // 1. Navigate to https://www.lambdatest.com/
            driver.get(url);

            // 2. Perform an explicit wait till the time all the elements in the DOM are available.
            WebDriverWait waitExplicit = new WebDriverWait(driver, explicitWaitTimeout);
            waitExplicit.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));

            // 3. Scroll to the WebElement ‘SEE ALL INTEGRATIONS’
            WebElement seeAllIntegrationsLinkElm = driver.findElement(seeAllIntegrationsLink);
            scrollToElement(seeAllIntegrationsLinkElm);

            // 4. Click on the link and ensure that it opens in a new Tab.
            // Click by js because element is hidden by another element after scroll
            clickElementByJs(seeAllIntegrationsLinkElm);

            // 5. Save the window handles in a List (or array). Print the window handles of the opened windows (now there are two windows open).
            Set<String> windowList = driver.getWindowHandles();
            for (String window : windowList) {
                System.out.println("The current window handle: " + window);
            }

            // 6. Verify whether the URL is the same as expected URL (if not throw an Assert)
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.lambdatest.com/integrations");

            // 7. On that page, scroll to the page where the WebElement (Codeless Automation) is present.
            scrollToElement(driver.findElement(codelessAutomationTitle));

            // 8. Click the ‘LEARN MORE’ link for Testing Whiz. The page should open in the same window.
            driver.findElement(learMoreTestingWhizLink).click();



            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}

	private void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void clickElementByJs(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }
}
