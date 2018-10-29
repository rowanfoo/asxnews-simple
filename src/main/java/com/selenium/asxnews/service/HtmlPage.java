package com.selenium.asxnews.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
/**
 * Setup up the webdriver and load page (url)
 */
public class HtmlPage {
    @Value( "${url.base}" )
    String baseurl;

    @Autowired
    WebDriver webdriver;


    // No idea why hotcopper ,, cant lead the annoucement-1 page
    // got to load a any page first.
    public void loadbasePage(){
      webdriver.get(baseurl);
      webdriver.getPageSource();
    }


    public String getPage(String url ){


        webdriver.get(url);
        //  System.out.println("------URL :" + url2);
        return  webdriver.getPageSource();

    }


}
