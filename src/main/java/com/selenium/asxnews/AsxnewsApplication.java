package com.selenium.asxnews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.sql.Driver;
import java.util.concurrent.TimeUnit;

public class AsxnewsApplication {
}

// @SpringBootApplication
//@EnableScheduling
//
//public class AsxnewsApplication
//{
//    @Value( "${selenium.pathfile}" )
//    private String seleniumpath;
//
//    @Bean
//    public WebDriver webdriver(){
//        File src = new File(seleniumpath);
//
//        System.out.println("START  AsxNewsChromeBrowser: ");
//        System.setProperty("webdriver.chrome.driver", src.getAbsolutePath());
//
//        WebDriver driver;
//        ChromeOptions ChromeOptions = new ChromeOptions();
//        // ChromeOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
//        driver = new ChromeDriver(ChromeOptions);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // just after you have initiated browser
//        return driver;
//    }
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(AsxnewsApplication.class, args);
//    }
//
//
//}
//



