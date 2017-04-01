//package org.quant.api.crawler;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.linkedin.crawler.models.ExperienceItem;
//import org.linkedin.crawler.models.Item;
//import org.linkedin.crawler.utils.Section;
//import org.quant.crawler.interfaces.Crawler;
//
//public class CrawlerImpl implements Crawler {
//
//	private Document linkedinPage;
//	private final String defaultUrl = "http://google.fr";
//	private final static Logger logger = LogManager.getLogger();
//	private final Elements crawlSection;
//
//	@Override
//	public void run() {
//		this.crawl();
//
//	}
//
//	private CrawlerImpl() {
//		super();
//		this.crawlSection = null;
//		try {
//			this.linkedinPage = Jsoup.connect(defaultUrl).get();
//		} catch (IOException e) {
//			this.linkedinPage = null;
//			logger.error("Error creating the default crawler", e);
//		}
//	}
//
//	private CrawlerImpl(Document linkedinPage, Section section) {
//		super();
//		this.linkedinPage = linkedinPage;
//		this.section = section;
//		this.crawlSection = this.linkedinPage
//				.select("#" + section.getIdAttribute());
//	}
//
//	// static instance factory
//	public static CrawlerImpl newInstance(Document documentToCrawl,
//			Section section) {
//		logger.debug("Creating new crawler");
//		return new CrawlerImpl(documentToCrawl, section);
//
//	}
//
//	@Override
//	public void crawl() {
//		logger.debug(this.crawlSection.html());
//		logger.debug(this.linkedinPage.html());
//		List<Item> items = new ArrayList<Item>();
//		Elements subSections = this.crawlSection.select(".position");
//		for (Element subSection : subSections) {
//			items.add(this.createItem(subSection));
//		}
//		for (Item item : items) {
//			logger.debug(item);
//		}
//	}
//
//	private Item createItem(Element section) {
//		Element date, title, description;
//		date = section.select(".date-range").get(0);
//		title = section.select(".item-title").get(0).select("a").get(0);
//		description = section.select(".description").get(0);
//		Item item = new ExperienceItem();
//		item.setTitle(title.text());
//		String[] dates = this.parseDate(date);
//		item.setStartDate(dates[0]);
//		item.setEndDate(dates[1]);
//		item.setDescriptionItems(this.parseDescription(description));
//		return item;
//	}
//
//	private List<String> parseDescription(Element description) {
//		List<String> descriptions = new ArrayList<String>();
//		String fullDesc = description.text();
//		for (String desc : fullDesc.split("<br>")) {
//			descriptions.add(desc);
//		}
//
//		return descriptions;
//	}
//
//	private String[] parseDate(Element date) {
//		String[] dates = new String[2];
//		dates[0] = date.select("time").get(0).text();
//		dates[1] = date.text().contains("Present") ? "Now"
//				: date.select("time").get(1).text();
//		return dates;
//	}
//
//	// check that no timeout occured during retrieval of profile page, an
//	// exception would be better TODO
//	public boolean isPageCorrect(String checker) {
//		logger.debug("Checking page is correct");
//		if (this.linkedinPage.text().contains(checker)) {
//			logger.debug("This is linkedin!");
//			// logger.debug(linkedinPage.text());
//			return true;
//		} else {
//			logger.debug("Shit I fucked up");
//			// logger.debug(linkedinPage.text());
//			return false;
//		}
//	}
//
//}
