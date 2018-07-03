package cn.elmall.fast;

import org.junit.Test;

import cn.elmall.common.utils.FastDFSClient;

public class FastDFSTest {
	@Test
	public void testFastDfsClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("F:\\hhhhhhhhhhhhhhhhhhhhhhhhhhhh\\java\\el-manager-web\\src\\main\\resources\\conf\\client.conf");
		String file = fastDFSClient.uploadFile("C:\\Users\\Administrator\\Desktop\\山东政区图.jpg");
		System.out.println(file);
		//group1/M00/00/00/wKgZhVsweh-Acx4fAAT-FAff9Ec231.jpg
		
		/*
		 * 通过如下链接就可以访问到上传的图片：
		 * http://192.168.25.133/group1/M00/00/00/wKgZhVsweh-Acx4fAAT-FAff9Ec231.jpg
		 * */
	}

}
