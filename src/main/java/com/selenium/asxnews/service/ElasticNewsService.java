package com.selenium.asxnews.service;

import com.selenium.asxnews.data.entity.AsxNewsDocument;
import com.selenium.asxnews.data.repo.AsxNewsRepo;
import com.selenium.asxnews.parser.AsxNewsParser;
import com.selenium.asxnews.util.Loadhref;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.tomcat.jni.Local;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log
public class ElasticNewsService {

    @Resource
    AsxNewsRepo asxNewsRepo;
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
    public   void importnewsbypage(int pageno) {
        importnews(false , pageno);

    }



    private  void importnews(boolean stopdate , int end){
        System.out.println( "----- setLoopElasticNews--------"  );
        page.loadbasePage();
        int stop=28;
        if (!stopdate)stop =end ;


        for (int x =0;x<stop;x++){

            urls ="https://hotcopper.com.au/announcements/asx/page-"+x +"/";
            String s = page.getPage(urls);
            ArrayList<AsxNewsDocument> arr =hotcopperParser.parse(s);
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
                    asxNewsRepo.save(a);
                    log.info( "----- SAVE--------" +a.getCode()   +  "   index:  "+ a.getDate()  );
                };

            });

        }
    }








}
