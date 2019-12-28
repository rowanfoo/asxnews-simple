package com.selenium.asxnews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.sql.Driver;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

//public class AsxnewsApplication {
//}

 @SpringBootApplication
@EnableScheduling

public class AsxnewsApplication
{
    @Value( "${selenium.pathfile}" )
    private String seleniumpath;
    @Value("${holidays}") String holidays;

    @Bean
    public WebDriver webdriver(){
        File src = new File(seleniumpath);

        System.out.println("START  AsxNewsChromeBrowser: ");
        System.setProperty("webdriver.chrome.driver", src.getAbsolutePath());

        WebDriver driver;
        ChromeOptions ChromeOptions = new ChromeOptions();
         ChromeOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
        driver = new ChromeDriver(ChromeOptions);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS); // just after you have initiated browser
        return driver;
    }
    @Bean
    public ArrayList<LocalDate> holidays() {
        ArrayList<LocalDate> arr = new ArrayList<>();
        System.out.println("---- HOLIDAYS  ------" + holidays );
        StringTokenizer st = new StringTokenizer(holidays ,",");
        System.out.println("---- Split by space ------");

        while (st.hasMoreTokens()) {
            // System.out.println(st.nextElement());
            arr.add( (LocalDate.parse(st.nextToken() )));
        }
        return arr;

    }


    public static void main(String[] args) {
        SpringApplication.run(AsxnewsApplication.class, args);
    }
    @Configuration
    @EnableSwagger2
    public class SwaggerConfig {
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
        }
    }

}




