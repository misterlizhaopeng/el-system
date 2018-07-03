package cn.elmall.search.test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {

	@Test
	public void addSolrJTest() throws Exception {

		// 1.创建SolrServer,如果不写.../collection1,那么默认为.../collection1
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr-4.10.3");
		// 2.创建文本对象
		SolrInputDocument document = new SolrInputDocument();
		// 3.向文档中添加域。必须有id域，域的名称必须在schema.xml中存在
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		document.addField("item_price", "199");
		// 4.把文档添加到索引库
		solrServer.add(document);
		// 提交
		solrServer.commit();
	}

	@Test
	public void deleteSolrJTest() throws Exception {
		// 1.创建solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr-4.10.3/collection1");
		// 2.调用SolrServer对象的根据id删除的方法。
		// solrServer.deleteById("test001");
		solrServer.deleteByQuery("id:test001");

		// 提交
		solrServer.commit();
	}

	/**
	 * 
	 * 不分页的情况下，solr查询默认返回10条记录，因为solr默认返回10条
	 * df:表示默认搜索域，如果不指定，默认值为text；
	 * 搜索域中的域的可以理解为数据库中的字段；so，搜索域，就是按照某个字段搜索
	 * 
	 * 
	 * @date 2018年6月27日
	 * @author misterLip
	 */
	@Test
	public void searchSolrJTest() throws Exception {
		// 1.创建solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr-4.10.3");
		// 2.SolrQuery
		SolrQuery query = new SolrQuery();
		// 3.添加查询条件，添加查询条件、过滤条件。。。
//		query.setQuery("*:*");
		query.set("q", "*:*");
		// 4.执行查询，得到一个response对象
		QueryResponse queryResponse = solrServer.query(query);
		// 5 得到查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());

		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	/**
	 * df:表示默认搜索域，如果不指定，默认值为text；
	 * 
	 * @date 2018年6月27日
	 * @author misterLip
	 */
	@Test
	public void queryDocumentWithHighLighting() throws Exception {
		// 第一步：创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr-4.10.3");
		// 第二步：创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 第三步：向SolrQuery中添加查询条件、过滤条件。。。
		//query.setQuery("测试");
		query.setQuery("手机");
		//指定默认搜索域
		query.set("df", "item_keywords");
		//开启高亮显示
		query.setHighlight(true);
		//高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		// 第四步：执行查询。得到一个Response对象。
		QueryResponse response = solrServer.query(query);
		// 第五步：取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
		// 第六步：遍历结果并打印。
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = null;
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			System.out.println(itemTitle);
			System.out.println(solrDocument.get("item_price"));
		}
	}

	
	
}
