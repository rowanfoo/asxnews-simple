package com.selenium.asxnews;

import com.selenium.asxnews.controller.AsxNews;
import com.selenium.asxnews.data.entity.AsxNewsDocument;
import com.selenium.asxnews.data.repo.AsxNewsRepo;
import com.selenium.asxnews.service.ElasticNewsService;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsxnewsApplicationTests {
    @Autowired  AsxNews asxNews;
    @Resource
    AsxNewsRepo asxNewsRepo;
@Autowired
ElasticNewsService fundNewsService;
    @Test
    public void testrepo() {
        AsxNewsDocument asxnewsdoc = new AsxNewsDocument();
        asxnewsdoc.setCode("test");
        asxnewsdoc.setTitle("test");
        asxnewsdoc.setNotes("test");

        asxNewsRepo.save(asxnewsdoc);
    }
    @Test
    public void testrunAsxNews() {
        System.out.println("---------------------testrunAsxNews----------------");
        fundNewsService.setAllElasticNews();
    }
    @Test
    public void contextLoads() {
        System.out.println("---------------------contextLoads----------------");
        HttpURLConnection connection = null;

        StringBuilder sb =  new StringBuilder();

        try {
//            URL url = new URL("https://www.asx.com.au/asxpdf/20180604/pdf/43vjljv5t7mxz0.pdf");

            URL url = new URL( "https://hotcopper.com.au/documentdownload?id=uOMxKKzFkiWRTLKhOROKAxjvTDYC6wi4zxeZrfhgke92GA%3D%3D");

           // URL url = new URL(urls);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            InputStream is = connection.getInputStream();
            PDDocument document = PDDocument  .load(is);
            System.out.println("---------------------contextLoads---------------- "+ document.isEncrypted());

          //  if (!document.isEncrypted() ) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);

                // split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                    System.out.println(line);
                }

          //  }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void soupTwo() {
        String urls =  "https://hotcopper.com.au/announcements/";
        Document doc = null;
        ArrayList<AsxNewsDocument> arr = new ArrayList<>();

        try {

            doc = Jsoup.connect(urls)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                    .timeout(3000)
                    .post();

            Elements links = doc.select("li.post-item  ");
            Element link;
//            String pattern = ".*documentdownload.*";

            for(int j=0;j<links.size();j++) {
                link = links.get(j);
                Elements elems = link.children();
                AsxNewsDocument asxnewsdoc = new AsxNewsDocument();
                //elems.forEach((a)->{
                for (Element a : elems) {
                    //System.out.println("******************************"+a.hasClass("listblock file-size extra-small-hide") );
                    //  System.out.println("******************************"+a.select("div[class=listblock tags small-hide]").first().text() );
                    //  System.out.println("******************************"+a.attr("class") );
                    if(a.attr("class").equals("listblock tags small-hide") ){
                       // System.out.println("------------CODE---------------"+a.text()  + " " );
                        asxnewsdoc.setCode(a.text() );

                    }
                    if(a.attr("class").equals("listblock summary") ){
                     //   System.out.println("------------summary---------------"+a.text()  + " " );
                        asxnewsdoc.setTitle (a.text() );

                    }
                    if(a.attr("class").equals("listblock file-size extra-small-hide") ){
                      //  System.out.println("------------summary---------------"+a.children().first().attr("abs:href"));
                        asxnewsdoc.setLink (a.children().first().attr("abs:href"));

                        System.out.println("---------------------parseNews----------------"+asxnewsdoc);
                        arr.add(asxnewsdoc);
                        asxnewsdoc = new AsxNewsDocument();
                    }

                }




               // });

            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }







    @Test
    public void runjob() {
        System.out.println("---------------------runjob----------------");
        asxNews.news ();
    }


    @Test
    public void parse() {
        String urls =  "https://hotcopper.com.au/announcements/";
        Document doc = null;
        ArrayList<String> arr = new ArrayList<>();

        try {
//            URL url = new URL( urls);
//            HttpURLConnection connection = null;
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

          //  doc = Jsoup.connect(connection.getInputStream()).get();
             doc = Jsoup.connect(urls)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                    .timeout(3000)
                    .post();

            Elements links = doc.select("a[href]");
            Element link;
            String pattern = ".*documentdownload.*";

            for(int j=0;j<links.size();j++) {
                link = links.get(j);
                boolean isMatch = Pattern.matches(pattern, link.attr("abs:href").toString());
                if (isMatch) {
                    System.out.println("a= " + link.attr("abs:href").toString());
                    arr.add(link.attr("abs:href").toString());
                }
            }

            loadData(arr.get(1) );


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void loadData(String urls) {
        System.out.println("---------------------contextLoads----------------");
        HttpURLConnection connection = null;

        StringBuilder sb =  new StringBuilder();
        PDDocument document=null;
        try {


             URL url = new URL(urls);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            InputStream is = connection.getInputStream();
             document = PDDocument  .load(is);
            System.out.println("---------------------contextLoads---------------- "+ document.isEncrypted());

            //  if (!document.isEncrypted() ) {

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);
            //System.out.println("Text:" + st);

            // split by whitespace
            String lines[] = pdfFileInText.split("\\r?\\n");
            for (String line : lines) {
                System.out.println(line);
            }

            //  }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
