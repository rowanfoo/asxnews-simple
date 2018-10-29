package com.selenium.asxnews;

import com.selenium.asxnews.controller.AsxNewsController;
import com.selenium.asxnews.data.entity.AsxNewsDocument;
import com.selenium.asxnews.data.entity.FundNews;
import com.selenium.asxnews.data.repo.AsxNewsRepo;
import com.selenium.asxnews.parser.AsxNewsParser;
import com.selenium.asxnews.service.ElasticNewsService;
import com.selenium.asxnews.service.HtmlPage;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsxnewsApplicationTests {
    @Autowired
    AsxNewsController asxNewsController;
    @Resource
    AsxNewsRepo asxNewsRepo;
@Autowired

ElasticNewsService fundNewsService;



    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testme(){

        try {

            Document doc = null;
//            doc = Jsoup.connect("https://hotcopper.com.au/announcements/asx/page-10")
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
//                    .timeout(3000)
//                    .post();
//            doc = Jsoup.connect("https://hotcopper.com.au/announcements/asx/page-10/").get();
//             doc  = Jsoup.connect("https://hotcopper.com.au/announcements/asx/page-10/")
//                    .timeout(60000).validateTLSCertificates(false).get();
//

            System.setProperty("javax.net.ssl.trustStore", "E:/InteliJ/work/asxnewss/src/main/resources/abc.jks");
             doc = Jsoup.connect("https://hotcopper.com.au/announcements/asx/page-10/").get();


//             doc = Jsoup
//                    .connect("https://hotcopper.com.au/announcements/asx/page-10/")
//                     .userAgent("Mozilla/5.0")
//                     .timeout(5000).get();
//


            Elements links = doc.select("a[href]");
            links.forEach((a)->{
                System.out.println( "-----here **  " +  a.text() );

            });

            System.out.println( "-----here " + doc.data() );
            System.out.println( "-----here " + doc.title() );
            System.out.println( "-----here " + doc.toString() );

            System.out.println( "-----here " + doc.outerHtml() );

            System.out.println( "-----here******** "  +doc.select("td[class*='stats-td']").first());
            System.out.println( "-----here******** "  +doc.select("td.stats-td") .first());

        }catch (Exception e){
            System.out.println("EROR " + e);
        }


    }



    @Test
    public void testmultipledata() {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchQuery("notes", "lowest cost "))
//                .build();
//        List<AsxNewsDocument> articles = elasticsearchTemplate.queryForList(searchQuery, AsxNewsDocument.class);
//
//        System.out.println("----------------------QUERY---------------"+searchQuery.getQuery());
//
//        articles.forEach((a)->{ System.out.println("---------------------datessssssss----------------"+a.getId() +" : " +   a.getTitle()+ "  " + a.getDate());        } );
//        // System.out.println("---------------------datessssssss----------------"+ articles);

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder boolQueryBuilder = boolQuery()
                .must(matchQuery("notes", "low") )
                .must(matchQuery("date", "2018-05-28"));
        searchQueryBuilder.withQuery(boolQueryBuilder);
        SearchQuery searchQuery  = searchQueryBuilder.build();



        List<AsxNewsDocument> articles = elasticsearchTemplate.queryForList( searchQuery, AsxNewsDocument.class);
        System.out.println("----------------------QUERY---------------"+searchQuery.getQuery());
        articles.forEach((a)->{ System.out.println("---------------------datessssssss----------------"+a.getId() +" : " +   a.getTitle()+ "  " + a.getDate());        } );


    }



    @Test
    public void testdata() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("notes", "lowest cost "))
                .build();
        List<AsxNewsDocument> articles = elasticsearchTemplate.queryForList(searchQuery, AsxNewsDocument.class);
        articles.forEach((a)->{ System.out.println("---------------------datessssssss----------------"+a.getId() +" : " +   a.getTitle());        } );
       // System.out.println("---------------------datessssssss----------------"+ articles);


    }



    @Test
    public void testdate() {
String date = "29/05/18 07:32";

        LocalDate fromCustomPattern = LocalDate.parse(date.substring(0,date.indexOf(" ")) , DateTimeFormatter.ofPattern("dd/MM/yy"));
        System.out.println("---------------------testdate----------------"+ fromCustomPattern);
    }


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
      //  fundNewsService.importElasticNews(true);
    }
    @Test
    public void testrunloppAsxNews() {
        System.out.println("---------------------testrunloppAsxNews----------------");
       // fundNewsService.setLoopElasticNews(true);
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

    @Value( "${selenium.pathfile}" )
    private String seleniumpath;

    @Test
    @SneakyThrows
    public void souptest3(){
        File src = new File(seleniumpath);

        System.out.println("START  AsxNewsChromeBrowser: ");
        System.setProperty("webdriver.chrome.driver", src.getAbsolutePath());

        WebDriver driver;
        ChromeOptions ChromeOptions = new ChromeOptions();
       // ChromeOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
        driver = new ChromeDriver(ChromeOptions);

        String html = "";
                      // https://hotcopper.com.au/announcements/asx/page-10/
            String url ="https://hotcopper.com.au/announcements/asx/page-10/"; // yesterday
        String url2 = "https://hotcopper.com.au";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // just after you have initiated browser

        System.out.println("------URL :" + url2);
            driver.get(url2);
//            driver.navigate().refresh();
        System.out.println("------URL :" + url);

            driver.navigate().to(url);
            try {
            TimeUnit.SECONDS.sleep(5);
             //   driver.get(url);

                System.out.println("---------------------getPage ");
                html = driver.getPageSource();
                System.out.println("---------------------quit ");

               // driver.quit();
               // System.out.println("---------------------parse "  + html);
                Document doc = Jsoup.parse(html);

               // System.out.println( "-----here******** "  +doc.select("table[class*=table]").first());

  //              Iterator<Element>  links = doc.select("table[class*=table]").iterator();
//                Elements ele = doc.select("table[class*=table]").last().children();
//                ele.forEach(a->{
//                    System.out.println("---------------------ele-------- "  + a.attr("class"));
//
//                });
//                doc.select("table[class*=table is-fullwidth is-hidden-touch]").forEach((table)->{
//                System.out.println("---------------------CLASS -------- "  + table.attr("class"));
//
//                    Elements rows = table.select("tr");
//                    rows.forEach(a->{
//                        if( ! a.select("td").isEmpty()  ){
//                            System.out.println("---------------------ele-------- "  + a.select("td").get(0).text() );
//                            System.out.println("---------------------ele 1-------- "  + a.select("td").get(1).text() );
//                            System.out.println("---------------------ele 2-------- "  + a.select("td").get(2).text() );
//                            System.out.println("---------------------ele 3-------- "  + a.select("td").get(3).text() );
//                            System.out.println("---------------------ele 4-------- "  + a.select("td").get(4).text() );
//                        }
//
//
//
//                    });
//
//
//                });


                Elements rows = doc.select("table[class*=table is-fullwidth is-hidden-touch]").get(0).select("tr");
                System.out.println("---------------------CLASS -------- "  + doc.select("table[class*=table]").get(0).attr("class"));
                rows.forEach(a->{
                    if( ! a.select("td").isEmpty()  ){
                        System.out.println("---------------------ele-------- "  + a.select("td").get(0).text() );
                        System.out.println("---------------------ele 1-------- "  + a.select("td").get(1).text() );
                        System.out.println("---------------------ele 2-------- "  + a.select("td").get(2).text() );
                        System.out.println("---------------------ele 3-------- "  + a.select("td").get(3).text() );
                        System.out.println("---------------------ele 4-------- "  + a.select("td").get(4).text() );
                        System.out.println("---------------------ele 4-------- "  + a.select("td").get(4).select("a").first().attr("href")) ;

                    }



                });


//                //Element link;
//                links.forEachRemaining( (link)->{
//
//                            System.out.println( "-----LINK " + link .text() );
//
//
//                            //for(int j=0;j<links.size();j++) {
//                //    link = links.get(j);
//
//                    Elements elems = link.children();
//
//                    for (Element a : elems) {
//                        if(a.attr("class").equals("stock-pill") ){
//                            System.out.println( "-----x" + a.text() );
//
//                        }
//                        if(a.attr("class").equals("stock-td") ){
//                            System.out.println( "-----y" + a.text() );
//
//
//                        }
//                        if(a.attr("class").equals("stats-td") ){
//                            System.out.println( "----- DATE---------" + a.text() );
//
//
//                            //LocalDate date = LocalDate.parse(a.text() .substring(0,a.text() .indexOf(" ")) , DateTimeFormatter.ofPattern("dd/MM/yy"));
//
//                        }
//
//                    }
//
//                }
//                    );












            } catch (Exception e) {
                System.out.println("driver page : " + e);

            }



        finally {
            driver.quit();
        }
        System.out.println("Run news parser : ");

    }
    @Autowired
    HtmlPage page;
    @Autowired
    AsxNewsParser hotcopperParser;
    @Value( "${url.base}" )
    String baseurl;


    @Test
    public void soup4() {
        String url ="https://hotcopper.com.au/announcements/asx/page-10/"; // yesterday
        String s = page.getPage(url);
//        System.out.println("---------------------soup4----------------" + s) ;
        System.out.println("---------------------soup4- class---------------" + hotcopperParser.getClass().getName() ) ;
        ArrayList<AsxNewsDocument> arr = hotcopperParser.parse(s);
        arr.forEach(a->{

            System.out.println("---------------------soup4----------------" + a) ;
        });


    }
    @Autowired
    ElasticNewsService     elasticNewsService;

    @Test
    public void soup5() {
        elasticNewsService.importnewsbydate();
    }

    @Test
    public void soup6()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse("08/10/18", formatter);
        String  mydate =  localDate.toString();

        System.out.println("-------------date-------------" + mydate);
        System.out.println("-------------date-------------" + LocalDate.now().isEqual(LocalDate.parse(mydate) )      )  ;





    }





    @Test
    public void runjob() {
        System.out.println("---------------------runjob----------------");
        asxNewsController.news ();
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
