package cn.elmall.soo.test;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPublish_SSO {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext*.xml");
		System.out.println("SSO provider starting ....");
		// System.in.read();表示停止程序，让spring容器一直运行着，如果没有此行代码，spring 容器会立刻关闭；
		// 还有一个方法：while(true){Thread.sleep(1000);};睡一秒执行一次，这样cpu不至于利用很高！！！
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
