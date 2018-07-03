package cn.elmall.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.elmall.common.utils.ElResult;
import cn.elmall.pojo.TbItem;
import cn.elmall.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	// 注入jmsTemplate
	@Autowired
	private JmsTemplate jmsTemplate;
	// 注入destination 之topic类型，原因为：消费者可能为多个
	@Resource
	private Destination topicDestination;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItem(@PathVariable long itemId) {
		TbItem item = itemService.getItem(itemId);
		return item;
	}

	@RequestMapping(value = "item/save", method = RequestMethod.POST)
	@ResponseBody
	public ElResult addItem(TbItem tbItem, String desc) {

		ElResult addItem = itemService.addItem(tbItem, desc);
		if (addItem.getData() != null) {
			//利用activemq，同步索引库
			final String data = addItem.getData().toString();
			jmsTemplate.send(topicDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(data);
					return textMessage;
				}
			});
		}
		return addItem;
	}

}
