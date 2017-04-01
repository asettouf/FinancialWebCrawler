package org.quant.api.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(url);
//		System.out.println(driver.getCurrentUrl());
		String htmlContent = driver.getPageSource();
//		htmlContent = htmlContent.replaceAll("(", "");
//		htmlContent = htmlContent.replaceAll(")", "");
		driver.quit();
		Document result = Jsoup.parse(htmlContent);
//		logger.debug(result.html());
		Elements Fields = result.getElementsByClass("Bdt");
		Map<String, List<Double>> datesAndNumber = new HashMap<String, List<Double>>();
		Elements buffer;
		String date;
		List<Double> numbers;
		for (Element field: Fields){
			buffer = field.select("span");
			date = buffer.get(0).text();
			buffer.remove(0);
			numbers = new ArrayList<Double>();
			for (Element elem: buffer){
				String tmp = elem.text();
				if (elem.text().contains(",")){
					tmp = elem.text().replaceAll(",", "");
				} else if(elem.text().contains("Dividend")){
//					logger.debug( "YOHO" + tmp);
					continue;
				}
				logger.debug(tmp);
				numbers.add(Double.parseDouble(tmp));
			}
			datesAndNumber.put(date, numbers);
		}
		logger.debug(datesAndNumber);
		
	}


}
