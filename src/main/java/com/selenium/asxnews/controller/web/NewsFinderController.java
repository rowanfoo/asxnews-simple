package com.selenium.asxnews.controller.web;

import com.selenium.asxnews.data.entity.News;
import com.selenium.asxnews.service.ElasticNewsWebService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class NewsFinderController {
    @Autowired
    ElasticNewsWebService elasticNewsWebService;
    @GetMapping("/hello")
    @ApiOperation(value = "iindex page",  notes = "index html , search page")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }



    @PostMapping ("/search")
    @ApiOperation(value = "search news ",  notes = "search page by param")


    public String search(Model model, @ApiParam(required = true, name = "page", value = "POST FORM with date,search , checksearch")
                                            @RequestParam Map<String, String> params) {
        String date =params.get("MyNoteDate");
        String search = params.get("search");
        String checksearch = params.get("checksearch");

        if(date.isEmpty()){
            System.out.println("--------------EMPTY " + date);
            date = LocalDate.now().toString();
        }

        if(! search.isEmpty()){
            checksearch =  search;
        }
//        params.forEach((a,b)->{
//                System.out.println("--------------param " + a +"  =  " + b);
//
//                }
//
//        );
        System.out.println("--------------RUN "  + checksearch + date );
        List<News>  data = elasticNewsWebService.search(checksearch, date   );
        model.addAttribute("data", data);
        model.addAttribute("date", date);
        System.out.println("-------------------------data  ---: " + data.size());


        return "hello";
    }



}
