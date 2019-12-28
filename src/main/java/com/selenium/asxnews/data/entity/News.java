package com.selenium.asxnews.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String title;
    // private String notes;
    private String link;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    // @Field(type = FieldType.Date, format = DateFormat.basic_date)
    //private LocalDate date;
    //must be in STRING , elastic cant convert LocalDate object  - DateTimeFormatter.ofPattern("dd/MM/yy")
//    private String date;

    private LocalDate date;

//
//    public News(String code, String title, String link) {
//        this.code = code;
//        this.title = title;
//        this.link = link;
//        //  date = LocalDate.now();
//
//    }
//
//    public News(String code, String title, String link, String date) {
//        this.code = code;
//        this.title = title;
//        this.link = link;
//        this.date = date;
//
//    }
}
