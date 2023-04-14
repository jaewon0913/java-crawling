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
import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlingService {
    private static String WEB_DRIVER_ID = "webdriver.chrome.driver";//property 키
    private static String WEB_DRIVER_PATH = "C:\\Users\\11242\\Desktop\\study\\crawling\\src\\main\\resources\\static\\selenium/chromedriver.exe";

    public CrawlingService() {

    }

    public void crawling(String bank){

        if("hana".equals(bank)) {
            //jsoupCrawl();
            seleniumCrawl();
        }
    }

    private void seleniumCrawl(){

        //  메소드 실행 시마다 새로 세팅해서 WebDriver 가 호출이 되어야 할 것 같음
        System.setProperty(WEB_DRIVER_ID,WEB_DRIVER_PATH);

        ChromeOptions options=new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");//팝업 무시
        options.addArguments("headless");// 창이없이 프로세스 사용
        options.addArguments("--remote-allow-origins=*");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        WebDriver webDriver = new ChromeDriver(options);

        //  목록에서 상품 상세화면 링크 저장
        List<String> depositLinkList = new ArrayList<>();
        List<String> savingLinkList = new ArrayList<>();

        try {
            //  목록화면 URL
            webDriver.get("https://www.kebhana.com/cont/mall/mall08/mall0805/index.jsp?_menuNo=62608");

            Thread.sleep(2000);

            //  상품 조회
            List<WebElement> deposit
                    = webDriver.findElements(By.cssSelector("#contents > div.banking-content > div.banking-row-area > div.wrap-product-list > ul > li"));

            for (WebElement element : deposit) {
                System.out.println("----------------------------");
                System.out.println(element.getText());
                depositLinkList.add(element.findElement(By.tagName("a")).getAttribute("href").toString());
            }

            //  적금 버튼 누르기 위한 WebElement
            WebElement webElement
                    = webDriver.findElement(By.xpath("//*[@id=\"search\"]/ul/li[1]/div/ul/li[2]/a"));

            System.out.println(webElement.getText());
            //  적금 버튼 클릭
            if(webElement.getText().equals("적 금")) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
                javascriptExecutor.executeScript("arguments[0].click();", webElement);
            }

            Thread.sleep(2000);

            List<WebElement> saving
                    = webDriver.findElements(By.cssSelector("#contents > div.banking-content > div.banking-row-area > div.wrap-product-list > ul > li"));
                    //= webDriver.findElements(By.className("item type2"));

            for (WebElement element : saving) {
                System.out.println("@@@@@@@@@@@@@@@@@@");
                System.out.println(element.getText());
                savingLinkList.add(element.findElement(By.tagName("a")).getAttribute("href").toString());
            };

            webDriver.quit();
        } catch (Exception e){
            e.printStackTrace();
        }

        for(String link : depositLinkList) {
            System.err.println(link);
        }

        for(String link : savingLinkList) {
            System.err.println(link);
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
