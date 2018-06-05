package com.selenium.asxnews.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FundNews {

    private String code;
    private LocalDate date;
    private String link ;
    private String title;
    private String seen;
    private String ok;
    private String notes;
    private String yesnotes;

    public FundNews(){}

    public FundNews(String code, LocalDate date, String title, String link) {
        this.code = code;
        this.date = date;
        this.link = link;
        this.title = title;
    }


    @Override
    public String toString() {
        return "FundNews{" +
                "code='" + code + '\'' +
                ", date=" + date +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
