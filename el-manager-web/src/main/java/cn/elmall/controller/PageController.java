package cn.elmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.elmall.common.pojo.EasyUIDataGridResult;
import cn.elmall.service.ItemService;

@Controller
public class PageController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	// 响应具体的页面（这个写法很特殊）
	@RequestMapping("/{page}")
	public String pageItem(@PathVariable String page) {
		return page;
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getEasyUIDataGridResult(Integer page, Integer rows) {
		EasyUIDataGridResult dataGridResult = itemService.getItemList(page, rows);
		return dataGridResult;
	}
}
