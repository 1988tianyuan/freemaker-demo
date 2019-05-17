package com.liugeng.freemakerdemo;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTest {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
		File resourceFile = new File("F:\\学习\\刘耕的编程文件夹\\实践\\freemaker-demo\\src\\main\\resources\\IAM.json");
		Resource resource = objectMapper.readValue(resourceFile, Resource.class);
		Map<String, Object> productData = resource.getProductData();

		Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_28);
		File baseFile = new File("F:\\学习\\刘耕的编程文件夹\\实践\\freemaker-demo\\src\\main\\resources");
		freeMarkerConfig.setTemplateLoader(new FileTemplateLoader(baseFile));
		Template template = freeMarkerConfig.getTemplate("freemarker.ftl", "utf-8");
		StringWriter writer = new StringWriter();
		template.process(productData, writer);
		System.out.println(writer.toString().replace(" ", ""));
	}
}
