package cn.elmall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.elmall.common.pojo.EasyTreeNode;
import cn.elmall.common.utils.ElResult;
import cn.elmall.content.service.ContentCatService;
import cn.elmall.mapper.TbContentCategoryMapper;
import cn.elmall.pojo.TbContentCategory;
import cn.elmall.pojo.TbContentCategoryExample;
import cn.elmall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper categoryMapper;

	/**
	 * 查询商品分类
	 */
	@Override
	public List<EasyTreeNode> getContentCat(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		List<EasyTreeNode> easyTreeNodes = new ArrayList<EasyTreeNode>();
		for (TbContentCategory tbContentCategory : list) {
			EasyTreeNode easyTreeNode = new EasyTreeNode();
			easyTreeNode.setId(tbContentCategory.getId());
			easyTreeNode.setText(tbContentCategory.getName());
			easyTreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			easyTreeNodes.add(easyTreeNode);
		}
		return easyTreeNodes;
	}

	/**
	 * 添加商品分类
	 */
	@Override
	public ElResult addContentCat(long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		category.setParentId(parentId);
		category.setName(name);
		category.setStatus(1);
		category.setSortOrder(1);
		category.setIsParent(false);
		category.setCreated(new Date());
		category.setUpdated(new Date());

		// 添加商品分类
		categoryMapper.insert(category);

		TbContentCategory contentCategory = categoryMapper.selectByPrimaryKey(parentId);
		if (contentCategory != null && contentCategory.getParentId() != 1) {
			//当前节点的父节点如果不是父节点，更新其为父节点
			contentCategory.setIsParent(true);
			categoryMapper.updateByPrimaryKey(contentCategory);
		}
		return ElResult.ok(category);
	}
}
