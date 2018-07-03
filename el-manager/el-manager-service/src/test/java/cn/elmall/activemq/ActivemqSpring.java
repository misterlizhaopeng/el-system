package cn.elmall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActivemqSpring {
	
	/**
	 * queue 方式消息生产者测试
	 * 
	 * @date 2018年6月28日
	 * @author misterLip
	 */
	@Test
	public void testActivemqSpring() {
		// 初始化spring容器
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq-producer.xml");
		// 从spring容器中获得JmsTemplate对象
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		// 从spring容器中取Destination对象
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		
		
		// 使用JmsTemplate对象发送消息。
		for (int i = 0; i < 10; i++) {
			jmsTemplate.send(destination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage("spring activemq-李朋10s0");
					return textMessage;
				}
			});
		}
	}
}
