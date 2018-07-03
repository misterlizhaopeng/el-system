package cn.elmall.search.listenmq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage =(TextMessage) message;
		//获取消息的内容
		try {
			String text = textMessage .getText();
			System.out.println(text);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
