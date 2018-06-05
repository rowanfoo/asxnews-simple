package com.selenium.asxnews;

import com.selenium.asxnews.controller.AsxNews;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsxnewsApplicationTests {

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

    @Autowired  AsxNews asxNews;

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
    public void loadData(String urls) {
        System.out.println("---------------------contextLoads----------------");
        HttpURLConnection connection = null;

        StringBuilder sb =  new StringBuilder();

        try {


             URL url = new URL(urls);

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
}
