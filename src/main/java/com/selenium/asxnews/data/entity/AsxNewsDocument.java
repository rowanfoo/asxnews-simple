package com.selenium.asxnews.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   // @Field(type = FieldType.Date, format = DateFormat.basic_date)
    //private LocalDate date;
    //must be in STRING , elastic cant convert LocalDate object  - DateTimeFormatter.ofPattern("dd/MM/yy")
    private String date;

    public AsxNewsDocument(){
    //    date = LocalDate.now();
    }


    public AsxNewsDocument(String code, String title,  String link) {
        this.code = code;
        this.title = title;
        this.link = link;
     //  date = LocalDate.now();

    }
    public AsxNewsDocument(String code, String title,  String link, String  date) {
        this.code = code;
        this.title = title;
        this.link = link;
        this.date = date;

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
