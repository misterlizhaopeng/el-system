package cn.elmall.search.listenmq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.elmall.common.pojo.SearchItem;
import cn.elmall.search.mapper.TbItemSolrMapper;

public class ItemAddMessageListener implements MessageListener {

	// 注入 SolrServer
	@Autowired
	private SolrServer solrServer;

	@Autowired
	private TbItemSolrMapper itemMapper;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long itemId = new Long(text);
			// 等待事务提交
			// Thread.sleep(1000);
			// 根据商品id查询商品信息
			SearchItem searchItem = itemMapper.getItemById(itemId);
			// 创建一个文档对象
			SolrInputDocument document = new SolrInputDocument();
			// 向文档对象中添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			// 把文档写入索引库
			solrServer.add(document);
			// 提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
