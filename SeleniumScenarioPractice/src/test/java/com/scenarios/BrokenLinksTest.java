package com.scenarios;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.Base;

public class BrokenLinksTest extends Base {

	WebDriver driver; 
	
	@BeforeMethod
	public void loadUrl() {
		Base.initialization();
		driver = Base.driver;
		driver.get("https://www.quora.com/");
	}
	
	@Test
	public void brokenLinksTest() throws Exception {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		
		for(WebElement element : links) {
			URL url = new URL(element.getAttribute("href"));
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(2000);
			httpCon.connect();
			
			if(httpCon.getResponseCode()==200) {
				System.out.println(url.toString() + " - " + httpCon.getResponseMessage());
			}
			
			if(httpCon.getResponseCode()==404) {
				System.out.println(url.toString() + " - " + httpCon.getResponseMessage());
			}
		}
	}
	
	@AfterMethod
	public void closeBrowser() {
		driver.close();
	}
}
