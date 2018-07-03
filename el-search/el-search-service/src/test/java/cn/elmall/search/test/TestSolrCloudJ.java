package cn.elmall.search.test;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloudJ {

	@Test
	public void test01() throws Exception {
		
		//创建CloudSolrServer，用其指向zookeeper集群；
		CloudSolrServer cloudSolrServer = new CloudSolrServer(
				"192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");

		//创建默认集合（其下面包括分片）
		cloudSolrServer.setDefaultCollection("collection2");

		SolrInputDocument document = new SolrInputDocument();

		document.addField("item_title", "测试商品");
		document.addField("item_price", "100");
		document.addField("id", "test001");

		cloudSolrServer.add(document);
		cloudSolrServer.commit();

	}
}
