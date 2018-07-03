package cn.elmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.elmall.common.pojo.EasyTreeNode;
import cn.elmall.mapper.TbItemCatMapper;
import cn.elmall.pojo.TbItemCat;
import cn.elmall.pojo.TbItemCatExample;
import cn.elmall.pojo.TbItemCatExample.Criteria;
import cn.elmall.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EasyTreeNode> getItemCatInfo(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<EasyTreeNode> list = new ArrayList<EasyTreeNode>();
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(example);
		for (TbItemCat tbItemCat : selectByExample) {
			EasyTreeNode easyTreeNode = new EasyTreeNode();
			easyTreeNode.setId(tbItemCat.getId());
			easyTreeNode.setText(tbItemCat.getName());
			easyTreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
			list.add(easyTreeNode);
		}
		return list;
	}

}
