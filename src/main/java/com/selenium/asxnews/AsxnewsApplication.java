package com.selenium.asxnews;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsxnewsApplication  implements CommandLineRunner
{

    public static void main(String[] args) {
        SpringApplication.run(AsxnewsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        File src = new File("E:\\Java\\lib3\\chromedriver.exe");
////        File src = new File("/mydev/drivers/chromedriver");
//      //  File src = new File("/usr/bin/chromedriver");
//
//
//
//        System.out.println("START  AsxNewsChromeBrowser: ");
//        System.setProperty("webdriver.chrome.driver", src.getAbsolutePath());
//
//        String content = new String(Files.readAllBytes(Paths.get("e:\\html.txt")));
        //System.out.println(content);
//        WebDriver driver = new ChromeDriver();
//        String html = "";
//        try {
//            //String url = "http://www.asx.com.au/asx/statistics/todayAnns.do"; // today
//
//            String url ="http://www.asx.com.au/asx/statistics/prevBusDayAnns.do"; // yesterday
//            System.out.println("------URL :" + url);
//            driver.get(url);
//
//            try {
//                Thread.sleep(3000);
//                // System.out.println( "out: "+driver.getPageSource() );
//                System.out.println("  AsxNewsChromeBrowser get page: ");
//                html = driver.getPageSource();
//                System.out.println("------html :" + html);
//
//                driver.quit();
//                new AsxNewsString(html);
//            } catch (Exception e) {
//                System.out.println("driver page : " + e);
//                //logger.severe("AsxNewsChromeBrowser driver fail to get page  " + e);
//
//            }
//
//        } catch (Exception e) {
////            logger.severe("AsxNewsChromeBrowser Err  " + e);
//            System.out.println("------Exception :" + e);
//        }
//
//        finally {
//            // driver.quit();
//        }
//        System.out.println("Run news parser : ");
      //  new AsxNewsString(content);
//
//        System.out.println("FINISH : ");
//        logger.info("AsxNewsChromeBrowser FINISH ");
    }
}
