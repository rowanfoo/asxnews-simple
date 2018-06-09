package com.selenium.asxnews.data.repo;

import com.selenium.asxnews.data.entity.AsxNewsDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



public interface  AsxNewsRepo  extends ElasticsearchRepository<AsxNewsDocument, String> {
}
