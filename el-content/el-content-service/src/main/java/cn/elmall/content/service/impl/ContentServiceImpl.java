package cn.elmall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.elmall.common.jedis.JedisClient;
import cn.elmall.common.utils.ElResult;
import cn.elmall.common.utils.JsonUtils;
import cn.elmall.content.service.ContentService;
import cn.elmall.mapper.TbContentMapper;
import cn.elmall.pojo.TbContent;
import cn.elmall.pojo.TbContentExample;
import cn.elmall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${ContentList}")
	private String ContentList;

	@Override
	public ElResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insertSelective(content);

		// 缓存同步,删除缓存中对应的数据。
		jedisClient.hdel(ContentList, content.getCategoryId() + "");
		return ElResult.ok();
	}

	@Override
	public List<TbContent> contentList(long categoryid) {

		// 先从数据库中获取数据
		try {
			String result = jedisClient.hget(ContentList, categoryid + "");
			if (StringUtils.isNotBlank(result)) {
				List<TbContent> jsonToList = JsonUtils.jsonToList(result, TbContent.class);
				return jsonToList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryid);

		List<TbContent> selectByExampleWithBLOBs = contentMapper.selectByExampleWithBLOBs(example);

		// 从数据库中获取数据添加到缓存中
		try {
			jedisClient.hset(ContentList, categoryid + "", JsonUtils.objectToJson(selectByExampleWithBLOBs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectByExampleWithBLOBs;
	}

}
