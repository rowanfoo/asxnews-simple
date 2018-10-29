package com.selenium.asxnews.service;

import com.selenium.asxnews.data.entity.AsxNewsDocument;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
/*
Used for search Elastic News
 */
public class ElasticNewsWebService {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    public List<AsxNewsDocument>  search ( String search , String date ) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder boolQueryBuilder = boolQuery()
                .must(matchQuery("notes", search) )
                .must(matchQuery("date", date ));
        System.out.println("----------------------QUERY---------------1");
        searchQueryBuilder.withQuery(boolQueryBuilder);
        System.out.println("----------------------QUERY---------------2");

        SearchQuery searchQuery  = searchQueryBuilder.build();

        System.out.println("----------------------QUERY---------------"+searchQuery.getQuery());

        List<AsxNewsDocument> articles = elasticsearchTemplate.queryForList( searchQuery, AsxNewsDocument.class);

        return articles;
    }



}
