package cn.elmall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.elmall.common.pojo.SearchItem;
import cn.elmall.common.utils.ElResult;
import cn.elmall.search.mapper.TbItemSolrMapper;
import cn.elmall.search.service.ItemAddToSolrService;

@Service
public class ItemAddToSolrServiceImpl implements ItemAddToSolrService {

	@Autowired
	private TbItemSolrMapper itemSolrMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public ElResult addItemToSolr() {
		try {

			List<SearchItem> itemList = itemSolrMapper.getItemList();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				// 写入索引库
				solrServer.add(document);
			}

			// 提交
			solrServer.commit();
			return ElResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ElResult.build(500, "导入索引库失败！");
		}
	}

}
