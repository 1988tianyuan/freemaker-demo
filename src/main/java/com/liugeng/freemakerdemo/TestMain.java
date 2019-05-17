package com.liugeng.freemakerdemo;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class TestMain {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
		File resourceFile = new File("D:\\code learning\\freemaker-demo\\src\\main\\resources\\IAM.json");
		Resource resource = objectMapper.readValue(resourceFile, Resource.class);
		Map<String, Object> productData = resource.getProductData();

		List<FieldDescr> template = new LinkedList<>();
		template.add(new SingleField("loadBalancerAddress"));
		template.add(new SingleField("loadBalancerName"));



		List<FieldDescr> field4DescrList = new LinkedList<>();
		field4DescrList.add(new SingleField("testName"));
		FieldDescr field4 = new ObjectField("test", field4DescrList);

		List<FieldDescr> fieldDescrList = new LinkedList<>();
		fieldDescrList.add(new SingleField("protocol"));
		fieldDescrList.add(new SingleField("serverPort"));
		fieldDescrList.add(new SingleField("loadBalancerPort"));
		FieldDescr field5 = new ObjectField("rule", fieldDescrList);
		FieldDescr field3 = new ListField("loadBalancerRuleList", Type.OBJECT, field5);

		template.add(field3);
		template.add(field4);
		System.out.println(objectMapper.writeValueAsString(template));


		Map<String, Object> resultMap = new TreeMap<>();
		for (FieldDescr fieldDescr : template) {
			switch (fieldDescr.getType()) {
				case SINGLE:
					extractSingle(fieldDescr, "", resultMap, productData);
					break;
				case OBJECT:
					ObjectField objectField = (ObjectField) fieldDescr;
					extractObject(fieldDescr, "", resultMap, objectField.getFieldDescrList(), productData);
					break;
				case LIST:
					String name = fieldDescr.getName();
					List list = (List) productData.get(name);
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i) instanceof  Map) {
							Map currentData = (Map) list.get(i);
							extractList(fieldDescr, fieldDescr.getName() + "[" + i + "].", resultMap, currentData, null);
						} else {
							extractList(fieldDescr, fieldDescr.getName() + "[" + i + "].", resultMap, null, list.get(i));
						}
					}
			}
		}
		System.out.println(objectMapper.writeValueAsString(resultMap));
	}

	public static void extractList(FieldDescr fieldDescr, String prefix, Map<String, Object> resultMap,
		Map<String, Object> currentData, Object currentSingleData) {
		ListField listField = (ListField) fieldDescr;
		if (listField.subType.equals(Type.SINGLE)) {
			String name = prefix;
			resultMap.put(name, currentSingleData);
		} else {
			ObjectField sub = (ObjectField) listField.getSubFieldDescr();
			for (FieldDescr descr : sub.getFieldDescrList()) {
				if (descr.getType().equals(Type.SINGLE)) {
					SingleField singleField = (SingleField) descr;
					extractSingle(singleField, prefix, resultMap, currentData);
				} else if (descr.getType().equals(Type.OBJECT)) {
					ObjectField objectField = (ObjectField) descr;
					extractObject(objectField, prefix, resultMap, objectField.getFieldDescrList(), (Map<String, Object>)currentData.get(descr.getName()));
				}
			}
		}
	}

	public static void extractObject(FieldDescr fieldDescr, String prefix,
		Map<String, Object> resultMap, List<FieldDescr> fieldDescrList, Map<String, Object> productData) {
		String name = fieldDescr.getName();
		Map objectMap = (Map) productData.get(name);
		String totalName = prefix + name;
		for (FieldDescr subField : fieldDescrList) {
			Object value = objectMap.get(subField.getName());
			resultMap.put(totalName + "." + subField.getName(), value);
		}
	}

	public static void extractSingle(FieldDescr fieldDescr, String prefix,
		Map<String, Object> resultMap, Map<String, Object> productData) {
		resultMap.put(prefix + fieldDescr.getName(), productData.get(fieldDescr.getName()));
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class FieldDescr {
		protected Type type;
		protected String name;
	}

	@Getter
	@Setter
	@ToString
	static class SingleField extends FieldDescr {
		public SingleField(String name) {
			super(Type.SINGLE, name);
		}
	}

	@Setter
	@Getter
	@ToString
	static class ListField extends FieldDescr {
		public ListField(String name, Type subType, FieldDescr fieldDescrList) {
			super(Type.LIST, name);
			this.subType = subType;
			this.subFieldDescr = fieldDescrList;
		}
		private Type subType;
		private FieldDescr subFieldDescr;
	}

	@Setter
	@Getter
	@ToString
	static class ObjectField extends FieldDescr {
		public ObjectField(String name, List<FieldDescr> fieldDescrList) {
			super(Type.OBJECT, name);
			this.fieldDescrList = fieldDescrList;
		}

		private List<FieldDescr> fieldDescrList;
	}

	enum Type {
		SINGLE, LIST, OBJECT
	}
}
