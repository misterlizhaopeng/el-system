package cn.elmall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.elmall.common.utils.ElResult;
import cn.elmall.common.utils.JsonUtils;
import cn.elmall.sso.service.TokenService;

@Controller
public class IndexController {

	@Autowired
	private TokenService tokenService;

	@RequestMapping("page/register")
	public String registerPage() {

		return "register";
	}

	@RequestMapping("page/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/user/tokens/{token}", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String showUserInfo(@PathVariable String token, String callback) {
		ElResult result = tokenService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JsonUtils.objectToJson(result) + ")";
		}
		return JsonUtils.objectToJson(result);
	}

	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken1(@PathVariable String token, String callback) {
		ElResult result = tokenService.getUserByToken(token);
		//响应结果之前，判断是否为jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			//把结果封装成一个js语句响应
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}
	
	
}
