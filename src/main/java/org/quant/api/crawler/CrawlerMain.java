package org.quant.api.crawler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CrawlerMain {
	public static Logger logger = LogManager.getLogger(CrawlerMain.class.getName());
	public static String symbol = "IBM";
	public static String url = "http://finance.yahoo.com/quote/" + symbol + "/history?ltr=1";

	public static void main(String[] args) {
//		 testCrawl();
		seleniumGetDoc();
	}

	public static void seleniumGetDoc() {
		System.setProperty("webdriver.chrome.driver",
				"src/main/resources/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(url);
		System.out.println(driver.getCurrentUrl());
		String htmlContent = driver.getPageSource();
		driver.quit();
		Document result = Jsoup.parse(htmlContent);
//		logger.debug(result.html());
		Elements dateFields = result.select(".BdT > td > span");
		logger.debug(dateFields);
		
	}

	public static void testCrawl() {
		Document result;
		
		try {
			result = Jsoup.connect(url).get();
			Elements dateFields = result.select(".BdT");
			logger.debug(result.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
