package cn.elmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.elmall.common.pojo.EasyTreeNode;
import cn.elmall.common.utils.ElResult;
import cn.elmall.content.service.ContentCatService;
import cn.elmall.content.service.ContentService;
import cn.elmall.pojo.TbContent;

@Controller
public class ContentController {
	@Autowired
	private ContentCatService catService;

	@Autowired
	private ContentService contentService;

	// 商品分类
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyTreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") long parentId) {
		List<EasyTreeNode> contentCat = catService.getContentCat(parentId);
		return contentCat;
	}

	@RequestMapping("content/category/create")
	@ResponseBody
	public ElResult createContentCat(long parentId, String name) {
		ElResult addContentCat = catService.addContentCat(parentId, name);
		return addContentCat;
	}

	// 商品内容
	@RequestMapping("/content/save")
	@ResponseBody
	public ElResult createContent(TbContent content) {
		ElResult addContent = contentService.addContent(content);
		return addContent;
	}

}
