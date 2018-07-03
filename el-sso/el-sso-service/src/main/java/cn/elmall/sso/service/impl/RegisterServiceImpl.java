package cn.elmall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.elmall.common.utils.ElResult;
import cn.elmall.mapper.TbUserMapper;
import cn.elmall.pojo.TbUser;
import cn.elmall.pojo.TbUserExample;
import cn.elmall.pojo.TbUserExample.Criteria;
import cn.elmall.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public ElResult checkLogin(String login, String type) {
		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		if ("1".equals(type)) {
			createCriteria.andUsernameEqualTo(login);
		} else if ("2".equals(type)) {
			createCriteria.andPhoneEqualTo(login);
		} else {
			return ElResult.build(400, "验证类型出错！");
		}

		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return ElResult.ok(false);
		}
		return ElResult.ok(true);
	}

	@Override
	public ElResult register(TbUser tbUser) {
		if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())
				|| StringUtils.isBlank(tbUser.getPhone())) {
			return ElResult.build(400, "数据不完整");
		}
		ElResult checkLogin = checkLogin(tbUser.getUsername(),"1");
		if (!(boolean) checkLogin.getData()) {
			return ElResult.build(400, "此用户名已经被占用");
		}
		
		ElResult checkLogin2 = checkLogin(tbUser.getPhone(),"2");
		if (!(boolean) checkLogin2.getData()) {
			return ElResult.build(400, "此手机已经被占用");
		}
		
		//补全pojo的属性
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		String md5Pwd=DigestUtils.md5DigestAsHex(tbUser.getUsername().getBytes());
		tbUser.setPassword(md5Pwd);
		tbUserMapper.insert(tbUser);
		return ElResult.ok();
	}

}
