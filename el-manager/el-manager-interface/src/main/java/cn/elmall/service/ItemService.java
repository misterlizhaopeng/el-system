package cn.elmall.service;

import cn.elmall.common.pojo.EasyUIDataGridResult;
import cn.elmall.common.utils.ElResult;
import cn.elmall.pojo.TbItem;
import cn.elmall.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItem(long item);

	EasyUIDataGridResult getItemList(int page, int rows);

	// 添加商品信息和商品描述信息
	ElResult addItem(TbItem item, String desc);
	// 添加商品信息，得到新添加上面的对象
	// TbItem addItem(TbItem item ,String desc);
	
	TbItemDesc getItemDescById(long itemId);
	TbItem getItemById(Long itemId);
}
