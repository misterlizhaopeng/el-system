package cn.elmall.search.service;

import cn.elmall.common.pojo.SearchResult;

//查询
public interface SearchService {
	SearchResult search(String keyword, int page, int rows)  throws Exception;
}