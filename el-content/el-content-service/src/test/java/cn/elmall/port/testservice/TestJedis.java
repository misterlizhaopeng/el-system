package cn.elmall.port.testservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.elmall.common.jedis.JedisClient;

public class TestJedis {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-redis.xml");
		JedisClient bean = applicationContext.getBean(JedisClient.class);
		bean.set("keyFirstx", "这是第一个测试xxx");
		String string = bean.get("keyFirstx");
		System.out.println(string);
	}
}
