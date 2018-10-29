package com.selenium.asxnews.util;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class Loadhref {
     // load the href , pdf ,
    @SneakyThrows
    public   String  loadData(String urls) {
        HttpURLConnection connection = null;

        StringBuilder sb =  new StringBuilder();
        PDDocument document =null;
        String notes=null;
        try {

            System.out.println("-----------------------load data -------------" + urls);
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
            notes=  String.join(" ", lines);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (document != null) {
                document.close();
            }

        }
        return notes;
    }

}
