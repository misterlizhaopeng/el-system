package cn.elmall.sso.service;

import cn.elmall.common.utils.ElResult;

public interface TokenService {
	ElResult getUserByToken(String token);
}
