package cn.elmall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.elmall.item.pojo.Item;
import cn.elmall.pojo.TbItem;
import cn.elmall.pojo.TbItemDesc;
import cn.elmall.service.ItemService;

@Controller
public class IndexController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		// 调用服务取商品基本信息
		TbItem tbItem = itemService.getItem(itemId);
		Item item = new Item(tbItem);
		// 取商品描述信息
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		// 把信息传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		// 返回逻辑视图
		return "item";
	}
}
