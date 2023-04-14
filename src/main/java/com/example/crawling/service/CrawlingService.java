package com.example.crawling.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CrawlingService {

    private final WebDriver webDriver;//셀리니움 사용을 위한 webDriver 주입
    private static String WEB_DRIVER_ID = "webdriver.chrome.driver";//property 키
    private static String WEB_DRIVER_PATH = "C:\\Users\\11242\\Desktop\\study\\crawling\\src\\main\\resources\\static\\selenium/chromedriver.exe";

    public CrawlingService() {
        System.setProperty(WEB_DRIVER_ID,WEB_DRIVER_PATH);

        ChromeOptions options=new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");//팝업 무시
        options.addArguments("headless");// 창이없이 프로세스 사용
        options.addArguments("--remote-allow-origins=*");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        this.webDriver = new ChromeDriver(options);
    }

    public void crawling(String bank){

        if("hana".equals(bank)) {
            //jsoupCrawl();
            seleniumCrawl();
        }
    }

    private void seleniumCrawl(){

        try {
            webDriver.get("https://www.kebhana.com/cont/mall/mall08/mall0805/index.jsp?_menuNo=62608");

            Thread.sleep(2000);

            List<WebElement> deposit
                    = webDriver.findElements(By.cssSelector("#contents > div.banking-content > div.banking-row-area > div.wrap-product-list > ul"));

            for (WebElement element : deposit) {
                System.out.println("----------------------------");
                System.out.println(element.getText());
            }

            WebElement webElement
                    = webDriver.findElement(By.xpath("//*[@id=\"search\"]/ul/li[1]/div/ul/li[2]/a"));

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(webElement.getText());

            if(webElement.getText().equals("적 금")) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
                javascriptExecutor.executeScript("arguments[0].click();", webElement);
            }

            Thread.sleep(2000);

            List<WebElement> saving
                    = webDriver.findElements(By.cssSelector("#contents > div.banking-content > div.banking-row-area > div.wrap-product-list > ul"));
                    //= webDriver.findElements(By.className("item type2"));

            for (WebElement element : saving) {
                System.out.println("@@@@@@@@@@@@@@@@@@");
                System.out.println(element.getText());
            };

            webDriver.quit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void jsoupCrawl(){
        final String hanaBankUrl = "https://www.kebhana.com/cont/mall/mall08/mall0805/index.jsp?_menuNo=62608";
        Connection conn = Jsoup.connect(hanaBankUrl);


        try {
            Document document = conn.get();
            Elements elements = document.getElementsByClass("schDeposit");

            for (Element element : elements) {
                System.err.println(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
