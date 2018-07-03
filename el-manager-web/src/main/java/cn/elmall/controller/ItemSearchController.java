package cn.elmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.elmall.common.utils.ElResult;
import cn.elmall.search.service.ItemAddToSolrService;

@Controller
public class ItemSearchController {
	@Autowired
	private ItemAddToSolrService itemAddToSolrService;

	@RequestMapping("/index/item/import")
	@ResponseBody
	public ElResult addItem() {
		return itemAddToSolrService.addItemToSolr();
	}

}
