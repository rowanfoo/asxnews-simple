package com.selenium.asxnews.service;

import com.selenium.asxnews.data.entity.News;
import com.selenium.asxnews.data.repo.NewsRepo;
import com.selenium.asxnews.parser.AsxNewsParser;
import com.selenium.asxnews.util.Loadhref;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Log
public class ElasticNewsService {

    @Resource
    NewsRepo newsRepo;
    String urls =  "";

    @Autowired
    HtmlPage page;
    @Autowired
    AsxNewsParser hotcopperParser;
    @Autowired
    Loadhref href;


    /**
     *  import all news for today
     */
    public   void importnewsbydate() {
        importnews(true , 0);

    }
    /**
     * import to what page no
     */
    public   void  importnewsbypage(int pageno) {
        importnews(false , pageno);

    }



    private  void importnews(boolean stopdate , int end){
        System.out.println( "----- setLoopElasticNews--------"  );
        page.loadbasePage();
        int stop=28;
        if (!stopdate)stop =end ;
    //    System.out.println( "----- setLoopElasticNews-   stop -------"  + end );


        for (int x =0;x<stop;x++){

            urls ="https://hotcopper.com.au/announcements/asx/page-"+x +"/";
            System.out.println( "----- URLS   -------"  + urls );
            String s = page.getPage(urls);
            ArrayList<News> arr =hotcopperParser.parse(s);
            log.info("------------ URLS--" + urls);

            if(stopdate){
 //               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                System.out.println( "----- setLoopElasticNews-  DATE-------" + arr.get(0).getDate()  );
                LocalDate localDate = LocalDate.parse(arr.get(0).getDate());
                if (! LocalDate.now().isEqual(localDate)) return;
            }
            arr.forEach((a)->{
                log.info("------------ URLS--" + urls);
                System.out.println( "----- importnews  load data --------" + a.getCode() );
                String text = href. loadData(a.getLink());

                try {
                    int number = new Random().nextInt(10);
                    TimeUnit.SECONDS.sleep(number);
                } catch (InterruptedException e) {e.printStackTrace();}

                if(text != null){
                    a.setNotes(text);
                    newsRepo.save(a);
                    log.info( "----- SAVE--------" +a.getCode()   +  "   index:  "+ a.getDate()  );
                };

            });

        }


        System.out.println("---------------------END IMPORTING  -----");
    }








}
