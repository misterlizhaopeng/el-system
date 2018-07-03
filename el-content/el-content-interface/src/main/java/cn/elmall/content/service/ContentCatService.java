package cn.elmall.content.service;

import java.util.List;

import cn.elmall.common.pojo.EasyTreeNode;
import cn.elmall.common.utils.ElResult;

public interface ContentCatService {
	List<EasyTreeNode> getContentCat(long parentId);
	ElResult addContentCat(long parentId,String name);
}
