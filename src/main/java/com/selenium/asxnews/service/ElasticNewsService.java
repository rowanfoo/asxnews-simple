package com.selenium.asxnews.service;

import com.selenium.asxnews.data.entity.AsxNewsDocument;
import com.selenium.asxnews.data.repo.AsxNewsRepo;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class ElasticNewsService {

    @Resource
    AsxNewsRepo asxNewsRepo;

    public void setAllElasticNews(){
        ArrayList<AsxNewsDocument> arr = parseNews();
        arr.forEach((a)->{
            AsxNewsDocument news = new AsxNewsDocument(a.getCode() ,a.getTitle(), a.getLink());
            String text = loadData(a.getLink());
            if(text.isEmpty()){
                news.setNotes(text);
                asxNewsRepo.save(news);

            };




        });
    }


    public ArrayList<AsxNewsDocument>   parseNews() {
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

            for(int j=0;j<links.size();j++) {
                link = links.get(j);
                Elements elems = link.children();
                AsxNewsDocument asxnewsdoc = new AsxNewsDocument();

                for (Element a : elems) {
                    if(a.attr("class").equals("listblock tags small-hide") ){
                        asxnewsdoc.setCode(a.text() );

                    }
                    if(a.attr("class").equals("listblock summary") ){
                        asxnewsdoc.setTitle (a.text() );

                    }
                    if(a.attr("class").equals("listblock file-size extra-small-hide") ){
                        asxnewsdoc.setLink (a.children().first().attr("abs:href"));
                        arr.add(asxnewsdoc);
                        asxnewsdoc = new AsxNewsDocument();
                    }

                }

            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }
    @SneakyThrows
     public String  loadData(String urls) {
        HttpURLConnection connection = null;

        StringBuilder sb =  new StringBuilder();
        PDDocument document =null;
        try {


            URL url = new URL(urls);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            InputStream is = connection.getInputStream();
             document = PDDocument  .load(is);

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);

            // split by whitespace
            String lines[] = pdfFileInText.split("\\r?\\n");

           return  String.join(" ", lines);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (document != null) {
                document.close();
            }
        }
        return "";
    }




}
