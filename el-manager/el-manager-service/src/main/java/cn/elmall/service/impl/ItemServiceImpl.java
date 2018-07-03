package cn.elmall.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.elmall.common.jedis.JedisClient;
import cn.elmall.common.pojo.EasyUIDataGridResult;
import cn.elmall.common.utils.ElResult;
import cn.elmall.common.utils.IDUtils;
import cn.elmall.common.utils.JsonUtils;
import cn.elmall.mapper.TbItemDescMapper;
import cn.elmall.mapper.TbItemMapper;
import cn.elmall.pojo.TbItem;
import cn.elmall.pojo.TbItemDesc;
import cn.elmall.pojo.TbItemExample;
import cn.elmall.pojo.TbItemExample.Criteria;
import cn.elmall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemDescMapper descMapper;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public TbItem getItem(long item) {
		// TbItem tbItem = tbItemMapper.selectByPrimaryKey(item);
		// return tbItem;

		/*
		 * ITEM_INFO:123456:BASE
		 * 
		 */
		try {
			String result = jedisClient.get("ITEM_INFO:" + item + ":BASE");
			if (StringUtils.isNotBlank(result)) {
				TbItem tbItem = JsonUtils.jsonToPojo(result, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(item);

		List<TbItem> list = tbItemMapper.selectByExample(example);
		if (null != list && list.size() > 0) {

			try {
				jedisClient.set("ITEM_INFO:" + item + ":BASE", JsonUtils.objectToJson(list.get(0)));
				jedisClient.expire("ITEM_INFO:" + item + ":BASE", 3600);// 3600秒
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list.get(0);
		}
		return null;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		// 向前端返回值的对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public ElResult addItem(TbItem item, String desc) {
		// item值id
		long genItemId = IDUtils.genItemId();
		item.setId(genItemId);
		// 1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 向商品表中添加
		tbItemMapper.insert(item);

		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(genItemId);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDesc.setItemDesc(desc);
		// 向商品描述表中添加
		descMapper.insert(itemDesc);
		return ElResult.ok(item.getId());
		// return ElResult.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {

		/* ITEM_INFO:123456:DESC */
		try {
			String string = jedisClient.get("ITEM_INFO:" + itemId + ":DESC");
			if (StringUtils.isNotBlank(string)) {
				TbItemDesc jsonToPojo = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				return jsonToPojo;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		TbItemDesc tbItemDesc = descMapper.selectByPrimaryKey(itemId);

		try {
			jedisClient.set("ITEM_INFO:" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
			jedisClient.expire("ITEM_INFO:" + itemId + ":DESC", 3600);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItemDesc;
	}

	@Override
	public TbItem getItemById(Long itemId) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

}
