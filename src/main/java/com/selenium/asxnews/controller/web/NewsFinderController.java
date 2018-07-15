package com.selenium.asxnews.controller.web;

import com.selenium.asxnews.data.entity.AsxNewsDocument;
import com.selenium.asxnews.service.ElasticNewsWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class NewsFinderController {
    @Autowired
    ElasticNewsWebService elasticNewsWebService;
    @RequestMapping("/hello")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }



    @RequestMapping(value="/search" , method=RequestMethod.POST)
    public String search(Model model, @RequestParam Map<String, String> params) {
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
        List<AsxNewsDocument>  data = elasticNewsWebService.search(checksearch, date   );
        model.addAttribute("data", data);
        model.addAttribute("date", date);
        System.out.println("-------------------------data  ---: " + data.size());


        return "hello";
    }



}
