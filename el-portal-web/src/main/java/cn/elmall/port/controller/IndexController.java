package cn.elmall.port.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.elmall.content.service.ContentService;
import cn.elmall.pojo.TbContent;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	@Value("${current_id}")
	private long categoryid;
	/**
	 * 1-><welcome-file>index.html</welcome-file>
	 * 2->通过了 <url-pattern>*.html</url-pattern>的匹配
	 * 3->找到该@RequestMapping("index")
	 * 4->找到/WEB-INF/jsp/下面的index.jsp
	 * 
	 * 总结：index.html 可以匹配 *.html，然后在把.html去掉，剩下的内容去找RequestMapping的内容值即可；
	 * 
	 * @date 2018年6月25日
	 * @author misterLip
	 */
	@RequestMapping("index")
	public String Index(Model model) {
		List<TbContent> contentList = contentService.contentList(categoryid);
		model.addAttribute("ad1List", contentList);
		return "index";
	}
}
