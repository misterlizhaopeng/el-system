package cn.elmall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.elmall.common.jedis.JedisClient;
import cn.elmall.common.utils.ElResult;
import cn.elmall.common.utils.JsonUtils;
import cn.elmall.pojo.TbUser;
import cn.elmall.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;

	@Override
	public ElResult getUserByToken(String token) {
		String string = jedisClient.get("SESSION:" + token);
		if (StringUtils.isBlank(string)) {
			ElResult.build(201, "用户登录已过期");
		}
		//取到用户信息更新token的过期时间
		jedisClient.expire("SESSION:" + token, 1800);
		TbUser jsonToPojo = JsonUtils.jsonToPojo(string, TbUser.class);
		return ElResult.ok(jsonToPojo);
	}

}
