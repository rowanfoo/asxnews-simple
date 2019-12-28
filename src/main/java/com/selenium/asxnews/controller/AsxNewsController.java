package com.selenium.asxnews.controller;

import com.selenium.asxnews.data.entity.FundNews;
import com.selenium.asxnews.parser.AsxNewsParser;

import com.selenium.asxnews.service.ElasticNewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /*
        import to what page
     */

    /*
        Schedule import by date , or can be manually trigger
     */
    //@Scheduled(cron = "0 16 15 ? * MON-FRI")


    @GetMapping("/importpage")
    @ApiOperation(value = "import page NUMBER",  notes = "Manually import  news , pass it number of page numbers")
    public void all(@ApiParam(required = true, name = "page", value = "number of pages to import") @RequestParam int page ) {
        System.out.println("Rimportpage --  : " + page);
        elasticNewsService.importnewsbypage(page );

    }
    @GetMapping("/import")
    @ApiOperation(value = "Auto import news base on today date",  notes = "Auto import pages  by a scheduler , will import news until today date")
    public String  imports() {
        System.out.println("Run news parser : ");
        elasticNewsService.importnewsbydate();
        return "running import now ";
    }

}
