package cn.elmall.sso.service;

import cn.elmall.common.utils.ElResult;

public interface LoginService {

	ElResult login(String username,String password);
}
