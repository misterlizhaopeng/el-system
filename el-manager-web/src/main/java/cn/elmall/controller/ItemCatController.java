package cn.elmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.elmall.common.pojo.EasyTreeNode;
import cn.elmall.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyTreeNode> getEasyTreeNode(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
		return itemCatService.getItemCatInfo(parentId);
	}
	
	@RequestMapping("aa/abb")
	@ResponseBody
	public String getss() {
		return "aa/bb";
		
	}

}
