package com.selenium.asxnews.sched;

import com.selenium.asxnews.service.ElasticNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailySched {
    @Autowired
    ElasticNewsService fundNewsService;

    @Scheduled(cron = "0 16 15 ? * MON-FRI")
    public void run(){
        fundNewsService.importElasticNews(true);
    }

}
