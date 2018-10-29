package com.selenium.asxnews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.regex.Pattern;


public class AsxNewsString  {

    String htmlToParse;
    AsxNewsString(String html){
        htmlToParse=html;
        System.out.println("AsxNewsString");
        run();
    }



    public void run(){

        System.out.println("RUNB");

        try {
            int count = 0;



            Document doc = Jsoup.parse(htmlToParse)  ;

            Elements table = doc.select("table");

            System.out.println("RUNB 12");
            String asxUrl="http://www.asx.com.au/";
            HashSet codes = new HashSet();

            for (Element tb : table) {
                System.out.println("TB 1:" );
                String t = tb.attr("class");
                String pattern = ".*announcements.*";
                boolean matches = Pattern.matches(pattern, t);
                Elements cap = tb.select("caption");
                System.out.println("AsxNewsString CAPTION:" + cap.text());

                System.out.println("AsxNewsString MATCHES:" + matches);

               // if (Pattern.matches(pattern, t)) {
                 if(true){
                    System.out.println("CORRECT TABLE");
                    boolean firsttime = false;

                    Elements tr = tb.select("tr");
                    // int count=0;
                    String code = "";
                    String link = "";
                    String title = "";

                    for (Element etr : tr) {

                        if (firsttime) {
                           // Elements th = etr.select("th");
                            //System.out.println("CORRECT
                            // CODE:"+((Element)th.get(0)).text());
                            //code = ((Element) th.get(0)).text();
                            //System.out.println("CORRECT code : "+ code);
                            Elements td = etr.select("td");
                            count = 0;
                            for (Element etd : td) {
                                if (count == 0)
                                    code = etd.text();

                                //if (count == 2)
                                  //   System.out.println("CORRECT TABLE  code  title : "+code +" : "+ title);

                                if (count == 3) {
                                    Elements  href= etd.select("a");
                                 //   System.out.println("href  " + href.attr("href") );
                                   // System.out.println("href text     " + href.text() );
                                    title = href.text();
                                    // System.out.println("CORRECT
                                    // URL:"+etd.val() );
                                    // System.out.println("CORRECT
                                    // URL:"+etd.attributes() );
                                    // System.out.println("CORRECT
                                    // URL:"+etd.childNodes() );
                                    // System.out.println("CORRECT
                                    // URL:"+etd.children() );
                                    //link = etd.children().toString();
                                    //link = etd.child(0).attr("href") ;
                                    //		System.out.println("HREF: "+etd.child(0).attr("href")   );


                                }
                               // System.out.println("CORRECT TABLE  code  title : "+code +" : "+ title);

                                count++;

                            }
                            System.out.println("AsxNewsString :"+code +" :: "+ title);
                            if (!codes.contains(code + title)) {
           //                     myarr.add(new NewsAccess(code, new Date(), asxUrl+link, title));
                              //  System.out.println("FINISH :" + code + asxUrl+link+" : "+ title);
                                codes.add(code + title);
                            }

                        }
                        firsttime = true;

                    }

                }

            }


            System.out.println("FINISH ");

        } catch (Exception e) {
//            logger.severe("AsxNewsString Error  " + e);
            System.out.println("AsxNewsString Error  " + e);
        }
  //      logger.info("AsxNewsString FINISH ");
            System.out.println("AsxNewsString FINISH " );

    }


    public static void main(String[] args) {


		/*
				   //get current date time with Date()
		Date mydate= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(mydate);
		int month = cal.get(Calendar.YEAR);
		System.out.println("yr"+month);

		System.out.println("yr"+(month-1));
			*/
        //new ASXNews().run();













    }






}
