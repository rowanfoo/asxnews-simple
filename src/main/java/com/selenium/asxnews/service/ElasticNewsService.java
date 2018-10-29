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




    /**
     * used for running loop
     * In past was used to get all data
     */
//    public void setLoopElasticNews(){
//
//        for (int x =0;x<26;x++){
//            urls =  "https://hotcopper.com.au/announcements/page-"+x;
//            ArrayList<AsxNewsDocument> arr = parseNews();
//            log.info("------------ URLS--" + urls);
//
//            arr.forEach((a)->{
//
//                String text = loadData(a.getLink());
//
//                try {
//                    int number = new Random().nextInt(30);
//                    TimeUnit.SECONDS.sleep(number);
//                } catch (InterruptedException e) {e.printStackTrace();}
//
//                if(text != null){
//                    a.setNotes(text);
//                    System.out.println( "----- SAVE--------" +a.getCode()  );
//                    log.info( "----- SAVE--------" +a.getCode()   +  "   index:  "+ a.getDate()  );
//                    asxNewsRepo.save(a);
//                };
//
//            });
//
//        }
//    }
    @Autowired
    HtmlPage page;
    @Autowired
    AsxNewsParser hotcopperParser;
    @Autowired
    Loadhref href;
//    @SneakyThrows
//    public void setLoopElasticNews(){
//        System.out.println( "----- setLoopElasticNews--------"  );
//        page.loadbasePage();
//        for (int x =0;x<28;x++){
//
//            urls ="https://hotcopper.com.au/announcements/asx/page-"+x +"/";
//            String s = page.getPage(urls);
//            ArrayList<AsxNewsDocument> arr =hotcopperParser.parse(s);
//            log.info("------------ URLS--" + urls);
//
//            arr.forEach((a)->{
//                System.out.println( "----- Load data--------"  );
//
//                String text = href. loadData(a.getLink());
//                System.out.println( "----- Load data 2--------"  );
//
//                try {
//                    int number = new Random().nextInt(30);
//                    TimeUnit.SECONDS.sleep(number);
//                } catch (InterruptedException e) {e.printStackTrace();}
//
//                if(text != null){
//                    a.setNotes(text);
////                    System.out.println( "----- SAVE--------" +a.getCode()  );
//                    System.out.println( "----- SAVE--------" +a );
//
//                    log.info( "----- SAVE--------" +a.getCode()   +  "   index:  "+ a.getDate()  );
//                    asxNewsRepo.save(a);
//                };
//
//            });
//
//        }
//    }
    public   void importnewsbydate() {
        importnews(true , 0);

    }
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




    /**
     * Run daily to import today
     * @param stopdate
     */
 //   public void importElasticNews(){
//        System.out.println("---------------------soup4----------------"  ;
////        AtomicBoolean  bool = new AtomicBoolean(true);
////        "https://hotcopper.com.au/announcements/page-"+count;
//        for (int count =1;count<201 && bool.get() ;count++){
//            urls =  "https://hotcopper.com.au/announcements/asx/page-"+count   +"/";
//
//            String s = page.getPage(urls,true);
////        System.out.println("---------------------soup4----------------" + s) ;
//            ArrayList<AsxNewsDocument> arr = hotcopperParser.parse(s);
//
//
//
//            arr.forEach((a)->{
//
//
//                String text = loadData(a.getLink());
//
//                try {
//                    int number = new Random().nextInt(60);
//                    TimeUnit.SECONDS.sleep(number);
//                } catch (InterruptedException e) {e.printStackTrace();}
//
//                if(text != null){
//                    a.setNotes(text);
//                    System.out.println( "----- SAVE--------" +a.getCode()  );
//                    log.info("------------ SAVE --" + a.getCode()   + " -" + a.getDate());
//
//                   //   asxNewsRepo.save(a);
//                };
//
//            });
//
//        }
//    }




    private ArrayList<AsxNewsDocument>   parseNews() {

        Document doc = null;
        ArrayList<AsxNewsDocument> arr = new ArrayList<>();
        System.out.println( "-----run parseNews " );

        try {

            doc = Jsoup.connect(urls)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                    .timeout(3000)
                    .post();
            System.out.println( "-----here " );
//            Elements links = doc.select("li.post-item  ");
            Elements links = doc.select(".table is-fullwidth is-hidden-touch");

            System.out.println( "-----here******** "  +doc.select("table[class*=table]").first());
            System.out.println( "-----link " +  links);
               Element link;

            for(int j=0;j<links.size();j++) {
                link = links.get(j);


                Elements elems = link.children();
                AsxNewsDocument asxnewsdoc = new AsxNewsDocument();

                for (Element a : elems) {
                    if(a.attr("class").equals("listblock tags small-hide") ){
                        System.out.println( "-----x" + a.text() );
                        asxnewsdoc.setCode(a.text() );

                    }
                    if(a.attr("class").equals("listblock summary") ){
                        System.out.println( "-----y" + a.text() );

                        asxnewsdoc.setTitle (a.text() );

                    }
                    if(a.attr("class").equals("listblock time time-data") ){
                        System.out.println( "----- DATE---------" + a.text() );


                        LocalDate date = LocalDate.parse(a.text() .substring(0,a.text() .indexOf(" ")) , DateTimeFormatter.ofPattern("dd/MM/yy"));
//                        asxnewsdoc.setDate(date.toString() );

                    }
                    if(a.attr("class").equals("listblock file-size extra-small-hide") ){
                       // System.out.println( "-----z " + a.text() );
if(a.children().first() != null) {
    //System.out.println("-----zzz-----  " + a.children().first().attr("abs:href"));
    asxnewsdoc.setLink(a.children().first().attr("abs:href"));
    arr.add(asxnewsdoc);
    asxnewsdoc = new AsxNewsDocument();
}
                    }

                }

            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }
//     @SneakyThrows
//     private  String  loadData(String urls) {
//        HttpURLConnection connection = null;
//
//        StringBuilder sb =  new StringBuilder();
//        PDDocument document =null;
//        String notes=null;
//        try {
//
//            System.out.println("-----------------------load data -------------" + urls);
//            URL url = new URL(urls);
//
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//
//            InputStream is = connection.getInputStream();
//             document = PDDocument  .load(is);
//
//            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//            stripper.setSortByPosition(true);
//
//            PDFTextStripper tStripper = new PDFTextStripper();
//
//            String pdfFileInText = tStripper.getText(document);
//
//            // split by whitespace
//            String lines[] = pdfFileInText.split("\\r?\\n");
//            notes=  String.join(" ", lines);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if (document != null) {
//                document.close();
//            }
//
//        }
//        return notes;
//    }




}
