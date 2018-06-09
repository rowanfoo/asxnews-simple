package com.selenium.asxnews.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
@Document(indexName = "asxnews", type = "news")
@Getter
@Setter
public class AsxNewsDocument  {
    @Id
    String id;

    private String  code;
    private String  title;
    private String  notes;
    private String   link;
    private LocalDate date;

    public AsxNewsDocument(){
        date = LocalDate.now();
    }


    public AsxNewsDocument(String code, String title,  String link) {
        this.code = code;
        this.title = title;
        this.link = link;
        date = LocalDate.now();

    }

    @Override
    public String toString() {
        return "AsxNewsDocument{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", notes='" + notes + '\'' +
                ", link='" + link + '\'' +
                ", date=" + date +
                '}';
    }
}
