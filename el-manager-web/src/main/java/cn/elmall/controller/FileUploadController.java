package cn.elmall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.elmall.common.utils.FastDFSClient;
import cn.elmall.common.utils.JsonUtils;

@Controller
public class FileUploadController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	/**
	 * 返回string，是为了[兼容多个浏览器]能上传成功图片
	 * produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8"=>Content-Type:application/json;charset=UTF-8；为了正常显示图片中文名字
	 * @date 2018年6月25日
	 * @author misterLip
	 */
	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile) {
		try { // 获取文件扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			// 2、创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			// 3、执行上传处理
			String path;

			path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);

			// 4、拼接返回的url和ip地址，拼装成完整的url
			String url = IMAGE_SERVER_URL + path;
			// 5、返回map
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);

			String objectToJson = JsonUtils.objectToJson(result);
			return objectToJson;
		} catch (Exception e) {
			e.printStackTrace();

			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "上传图片失败");

			String objectToJson = JsonUtils.objectToJson(result);
			return objectToJson;
		}
	}
}
