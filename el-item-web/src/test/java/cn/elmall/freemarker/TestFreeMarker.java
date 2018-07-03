package cn.elmall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.elmall.freemarker.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreeMarker {
	public static void main(String[] args) throws Exception {
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());

		// 第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(
				new File("F:\\hhhhhhhhhhhhhhhhhhhhhhhhhhhh\\java\\el-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));

		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");

		// 第四步：加载一个模板，创建一个模板对象。
		//Template template = configuration.getTemplate("hello.ftl");
		Template template = configuration.getTemplate("newFile.ftl");

		// 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。

		Map map = new HashMap<>();
		map.put("hello", "test freemarkersss");
		map.put("student", new Student(1, "a1"));
		
		List<Student> list =new ArrayList<Student>();
		list.add(new Student(1, "a1"));
		list.add(new Student(2, "a2")); 
		list.add(new Student(3, "a3"));
		map.put("stus", list);
		map.put("myval", null);
		map.put("date", new Date());
		// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		Writer writer = new FileWriter(new File("d:\\b.html"));

		// 第七步：调用模板对象的process方法输出文件。
		template.process(map, writer);

		// 第八步：关闭流。
		writer.close();

	}
}
