package cn.elmall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.elmall.cart.service.CartService;
import cn.elmall.common.jedis.JedisClient;
import cn.elmall.common.utils.ElResult;
import cn.elmall.common.utils.JsonUtils;
import cn.elmall.mapper.TbItemMapper;
import cn.elmall.pojo.TbItem;

/**
 * 购物车的实现：包括登录的情况、不登录的情况
 * @author  misterLip
 * @date   2018年7月2日
 */
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;

	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public ElResult addCart(long userId, long itemId, int num) {

		// 判断是否存在，存在数量增加；不存在的情况，从数据库中获取一条添加到redis中

		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
		if (hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			tbItem.setNum(tbItem.getNum() + num);
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
			return ElResult.ok();
		}
		// 如果不存在，根据商品id取商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 设置购物车数据量
		item.setNum(num);
		// 取一张图片
		String image = item.getImage();
		if (StringUtils.isNotBlank(image)) {
			item.setImage(image.split(",")[0]);
		}
		// 添加到购物车列表
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
		return ElResult.ok();

	}

	@Override
	public ElResult mergeCart(long userId, List<TbItem> itemList) {
		// 遍历商品列表
		// 把列表添加到购物车。
		// 判断购物车中是否有此商品
		// 如果有，数量相加
		// 如果没有添加新的商品
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		// 返回成功
		return ElResult.ok();
	}

	@Override
	public List<TbItem> getCartList(long userId) {
		// 根据用户id查询购车列表
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<TbItem> itemList = new ArrayList<>();
		for (String string : jsonList) {
			// 创建一个TbItem对象
			TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
			// 添加到列表
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public ElResult updateCartNum(long userId, long itemId, int num) {
		// 从redis中取商品信息
		String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
		// 更新商品数量
		TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
		tbItem.setNum(num);
		// 写入redis
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
		return ElResult.ok();
	}

	@Override
	public ElResult deleteCartItem(long userId, long itemId) {
		// 删除购物车商品
		jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
		return ElResult.ok();
	}

}
