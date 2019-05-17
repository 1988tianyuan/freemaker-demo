package com.liugeng.freemakerdemo;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FreemarkerTest {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
		File resourceFile = new File("D:\\code learning\\freemaker-demo\\src\\main\\resources\\IAM.json");
		Resource resource = objectMapper.readValue(resourceFile, Resource.class);
		Map<String, Object> productData = resource.getProductData();
		System.out.println(productData);



	}
}
