package cn.elmall.content.service;

import java.util.List;

import cn.elmall.common.utils.ElResult;
import cn.elmall.pojo.TbContent;

public interface ContentService {

	// 添加商品内容
	ElResult addContent(TbContent content);
	
	List<TbContent > contentList(long categoryid);

}
