package cn.elmall.service;

import java.util.List;

import cn.elmall.common.pojo.EasyTreeNode;

public interface ItemCatService {
	List<EasyTreeNode> getItemCatInfo(Long parentId);
}
