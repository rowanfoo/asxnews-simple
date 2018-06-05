package com.selenium.asxnews.parser;

import com.selenium.asxnews.data.entity.FundNews;

import java.util.ArrayList;

public interface AsxNewsParser {
    public ArrayList<FundNews> parse(String content);

}
