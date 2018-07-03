package cn.elmall.sso.service;

import cn.elmall.common.utils.ElResult;
import cn.elmall.pojo.TbUser;

public interface RegisterService {
	/**
	 * 验证
	 * 
	 * 1用户名，2手机，其他返回错误消息
	 * @date 2018年7月1日
	 * @author misterLip
	 */
	ElResult checkLogin(String login,String type);
	ElResult register(TbUser tbUser);
	
}
