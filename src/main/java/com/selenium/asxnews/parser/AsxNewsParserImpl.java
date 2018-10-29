package com.selenium.asxnews.parser;

import com.selenium.asxnews.data.entity.AsxNewsDocument;
import com.selenium.asxnews.data.entity.FundNews;
import com.selenium.asxnews.process.AsxNewsProcess;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

@Component
public class AsxNewsParserImpl implements  AsxNewsParser {

    @Autowired
    AsxNewsProcess asxNewsProcess;

    @Override
    public ArrayList<AsxNewsDocument> parse(String content){
        return null;
    }
//
//    @Override
//    public ArrayList<FundNews> parse(String content) {
//        System.out.println("RUNB");
//        ArrayList<FundNews> arr = new ArrayList<>();
//        try {
//            int count = 0;
//
//
//
//            Document doc = Jsoup.parse(content)  ;
//
//            Elements table = doc.select("table");
//
//
//            HashSet codes = new HashSet();
//            //   ArrayList<NewsAccess> myarr = new ArrayList<NewsAccess>();
//            System.out.println("RUNB 13");
//            for (Element tb : table) {
//                System.out.println("TB 1:" );
//                Elements cap = tb.select("caption");
//                System.out.println("AsxNewsString CAPTION:" + cap.text());
//
//
//
//                    System.out.println("CORRECT TABLE");
//                    boolean firsttime = false;
//
//                    Elements tr = tb.select("tr");
//                    // int count=0;
//                    String code = "";
//                    String link = "";
//                    String title = "";
//
//                    for (Element etr : tr) {
//
//                        if (firsttime) {
//                            Elements td = etr.select("td");
//                            count = 0;
//                            for (Element etd : td) {
//                                if (count == 0)
//                                    code = etd.text();
//
//
//                                if (count == 3) {
//                                    Elements  href= etd.select("a");
//                                    title = href.text();
//                                    link = href.attr("href");
//
//
//                                }
//
//                                count++;
//
//                            }
//                            System.out.println("AsxNewsString :"+code +" :: "+ title);
////                            if (!codes.contains(code + title)) {
////                                //                     myarr.add(new NewsAccess(code, new Date(), asxUrl+link, title));
////                                //  System.out.println("FINISH :" + code + asxUrl+link+" : "+ title);
////                                codes.add(code + title);
////                            }
////                            asxNewsProcess.process(new FundNews(code,LocalDate.now(),title,link));
//                            arr.add(new FundNews(code,LocalDate.now(),title,link));
//
//
//
//
//                        }
//                        firsttime = true;
//
//                    }
//
//
//
//            }
//
//
//            //            if(myarr.isEmpty())logger.severe("AsxNewsString NO NEWS " );
////
////
////            try (DAOFactoryNews dc = new DAOFactoryNews()) {
////                dc.insertNewNews(myarr);
////            } catch (Exception e) {
////                logger.severe("AsxNewsString Error insertNews " + e);
////
////
////            }
//
//            //System.out.println("FINISH ");
//
//        } catch (Exception e) {
////            logger.severe("AsxNewsString Error  " + e);
//            System.out.println("AsxNewsString Error  " + e);
//        }
//        //      logger.info("AsxNewsString FINISH ");
//     //   System.out.println("AsxNewsString FINISH " );
//        return arr;
//    }
}
