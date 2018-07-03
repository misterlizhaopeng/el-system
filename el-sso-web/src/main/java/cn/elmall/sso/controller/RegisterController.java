package cn.elmall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.elmall.common.utils.CookieUtils;
import cn.elmall.common.utils.ElResult;
import cn.elmall.pojo.TbUser;
import cn.elmall.sso.service.LoginService;
import cn.elmall.sso.service.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	@Autowired
	private LoginService loginService;

	@RequestMapping("/user/check/{nameOrPhone}/{type}")
	@ResponseBody
	public ElResult checkUser(@PathVariable String nameOrPhone, @PathVariable String type) {
		ElResult checkLogin = registerService.checkLogin(nameOrPhone, type);
		return checkLogin;
	}

	@RequestMapping("/user/register")
	@ResponseBody
	public ElResult checkUser(TbUser tbUser) {
		ElResult register = registerService.register(tbUser);
		return register;
	}

	@RequestMapping("user/login")
	@ResponseBody
	public ElResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		ElResult login = loginService.login(username, password);
		if (login.getStatus() == 200) {
			CookieUtils.setCookie(request, response, "TT_TOKEN", login.getData().toString());
		}
		return login;
	}
}
