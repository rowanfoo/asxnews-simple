package com.selenium.asxnews.parser;

import com.selenium.asxnews.data.entity.News;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Component
@Primary
/*
Parse the new hotcopper annoucement page
 */
public class HotcopperParser implements  AsxNewsParser
{

        @Value( "${url.base}" )
        String baseurl;
    @SneakyThrows
    @Override
    public ArrayList<News> parse(String content) {
        System.out.println("-----------PARSE START-------- "   );
        ArrayList<News> arrayList = new ArrayList<>();
        Document doc = Jsoup.parse(content);


        Elements rows = doc.select("table[class*=table is-fullwidth is-hidden-touch]").get(0).select("tr");
      //  System.out.println("-----------PARSE row -------- "   + rows );
        rows.forEach(a->{
            //System.out.println("-----------PARSE a -------- "   + a);

            if( ! a.select("td").isEmpty()  ){
//                System.out.println("---------------------ele-------- "  + a.select("td").get(0).text() );
//                System.out.println("---------------------ele 1-------- "  + a.select("td").get(1).text() );
//                System.out.println("---------------------ele 2-------- "  + a.select("td").get(2).text() );
//                System.out.println("---------------------DATE ------ele 3-------- "  + a.select("td").get(3).text()  );
//                System.out.println("---------------------ele 4-------- "  + a.select("td").get(4).text() );
//                System.out.println("---------------------ele 4-------- "  + a.select("td").get(4).select("a").first().attr("href")) ;
                LocalDate localDate = null;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                    //convert String to LocalDate

                    String date  = a.select("td").get(3).text();
                    if(date.indexOf("/") == -1 ){ /// this happens because if before 11pm , field quotes time i.e 4:20 , after 11pm , it converts to date 11/12/2018
                        localDate = LocalDate.now();
                    }else{
                        localDate = LocalDate.parse(a.select("td").get(3).text() , formatter);

                    }

                }catch (Exception e){
                    System.out.println("---------------------PARESER -----"+e);
                }

                arrayList.add(  new News(a.select("td").get(0).text(), a.select("td").get(1).text(),
                        baseurl+a.select("td").get(4).select("a").first().attr("href") , localDate.toString()) );
            }

        });
        System.out.println("---------------------END PARESER -----");
        TimeUnit.MINUTES.sleep(1);
        return arrayList;
    }
}
