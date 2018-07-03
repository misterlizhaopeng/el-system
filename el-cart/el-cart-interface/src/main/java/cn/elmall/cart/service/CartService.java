package cn.elmall.cart.service;

import java.util.List;

import cn.elmall.common.utils.ElResult;
import cn.elmall.pojo.TbItem;

public interface CartService {
	
	ElResult addCart(long userId, long itemId, int num);
	ElResult mergeCart(long userId, List<TbItem> itemList);
	List<TbItem> getCartList(long userId);
	ElResult updateCartNum(long userId, long itemId, int num);
	ElResult deleteCartItem(long userId, long itemId);
}
