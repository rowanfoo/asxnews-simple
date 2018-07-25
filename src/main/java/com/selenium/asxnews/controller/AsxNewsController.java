package com.selenium.asxnews.controller;

import com.selenium.asxnews.data.entity.FundNews;
import com.selenium.asxnews.parser.AsxNewsParser;

import com.selenium.asxnews.service.ElasticNewsService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


@RestController
public class AsxNewsController {
    @Value( "${selenium.pathfile}" )
    private String seleniumpath;
    @Autowired
    AsxNewsParser parser;

    @Autowired
    ElasticNewsService elasticNewsService;

    public void run(){

    }

    @GetMapping("/all")
    public void all() {
        elasticNewsService.setLoopElasticNews();

    }

        @GetMapping("/news")
    public ArrayList<FundNews> news() {

        System.out.println("---------------------file "+ seleniumpath);
        File src = new File(seleniumpath);
        ArrayList<FundNews> arr = new ArrayList<>();



        System.out.println("START  AsxNewsChromeBrowser: ");
        System.setProperty("webdriver.chrome.driver", src.getAbsolutePath());

//        WebDriver driver = new ChromeDriver();
        WebDriver driver;

        ChromeOptions ChromeOptions = new ChromeOptions();
        ChromeOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
        driver = new ChromeDriver(ChromeOptions);

        String html = "";
        try {
           // String url = "https://www.asx.com.au/asx/statistics/prevBusDayAnns.do"; // today

           String url ="https://www.asx.com.au/asx/statistics/todayAnns.do"; // yesterday
            System.out.println("------URL :" + url);
            driver.get(url);

            try {
                TimeUnit.SECONDS.sleep(20);
                System.out.println("---------------------getPage ");
                html = driver.getPageSource();
                System.out.println("---------------------quit ");

                driver.quit();
                System.out.println("---------------------parse ");

                arr =  parser.parse(html);

                arr.forEach((a)->{

                    System.out.println("  OBJ : " + a);

                });


            } catch (Exception e) {
                System.out.println("driver page : " + e);

            }

        } catch (Exception e) {
            System.out.println("------Exception :" + e);
        }

        finally {
            driver.quit();
        }
        System.out.println("Run news parser : ");
        elasticNewsService.importElasticNews(true);
        return arr;


        //System.out.println("FINISH : ");
    }




}
