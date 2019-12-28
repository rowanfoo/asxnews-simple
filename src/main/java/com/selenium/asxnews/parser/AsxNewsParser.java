package com.selenium.asxnews.parser;

import com.selenium.asxnews.data.entity.News;

import java.util.ArrayList;

public interface AsxNewsParser {
    public ArrayList<News> parse(String content);

}
