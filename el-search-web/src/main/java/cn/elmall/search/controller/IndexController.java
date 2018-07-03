package cn.elmall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.elmall.common.pojo.SearchResult;
import cn.elmall.search.service.SearchService;

@Controller
public class IndexController {
	@Autowired
	private SearchService searchService;

	@Value("${show_rows}")
	private Integer show_rows;

	@RequestMapping("search") // 返回一个逻辑视图
	public String Index(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {

		if (keyword==null||"".equals(keyword)) {
			return "search";
		}
		keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		SearchResult search = searchService.search(keyword, page, show_rows);

		// 把结果传递给页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", search.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", search.getRecordCount());
		model.addAttribute("itemList", search.getItemList());

	//	int a=1/0;
		// 返回逻辑视图
		return "search";

	}

}
