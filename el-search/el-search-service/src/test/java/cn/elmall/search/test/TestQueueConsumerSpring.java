package cn.elmall.search.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestQueueConsumerSpring {
	@Test
	public void testQueueConsumer() throws Exception {
		// 初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq-consumer.xml");
		// 等待
		System.in.read();
	}

}
