package cn.elmall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.elmall.common.jedis.JedisClient;
import cn.elmall.common.utils.ElResult;
import cn.elmall.common.utils.JsonUtils;
import cn.elmall.mapper.TbUserMapper;
import cn.elmall.pojo.TbUser;
import cn.elmall.pojo.TbUserExample;
import cn.elmall.pojo.TbUserExample.Criteria;
import cn.elmall.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;

	/**
	 * 判断登录是否成功!! 不成功，返回不成功; 成功，把token作为key写入到redis，人员信息作为value
	 * 
	 * 还有把token写入cookie，这一步放到web端，控制器里面来操作
	 */
	@Override
	public ElResult login(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		// createCriteria.andPasswordEqualTo(password);
		List<TbUser> selectByExample = tbUserMapper.selectByExample(example);
		if (selectByExample == null || selectByExample.size() <= 0) {
			// 表示登录失败
			return ElResult.build(400, "用户名或者密码错误");
		}

		// 在判断密码是否正确
		TbUser userInfo = selectByExample.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(userInfo.getPassword())) {
			// 表示登录失败
			return ElResult.build(400, "用户名或者密码错误");
		}

		String token = UUID.randomUUID().toString();
		String redisKey = "SESSION:" + token;
		// 把密码设置为空
		userInfo.setPassword(null);
		String redisVal = JsonUtils.objectToJson(userInfo);
		jedisClient.set(redisKey, redisVal);
		jedisClient.expire(redisKey, 60 * 30);// 保存半小时
		return ElResult.ok(token);
	}

}
