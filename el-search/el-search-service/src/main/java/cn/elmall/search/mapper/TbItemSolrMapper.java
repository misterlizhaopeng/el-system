package cn.elmall.search.mapper;

import java.util.List;

import cn.elmall.common.pojo.SearchItem;

public interface TbItemSolrMapper {
	List<SearchItem> getItemList();
	SearchItem getItemById(Long itemId);
}
