package com.liugeng.freemakerdemo;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

	private String id;

	private String nrn;

	private String domainCode;
	private String productName;
	private String regionCode;
	private Long memberNo;
	private String resourceType;
	private String resourceId;

	private String productResourceType;

	private String resourceName;
	private String zoneCode;
	private String actionIdNo;
	private Long actionSubAccountId;
	private String actionUserType;

	private Long createTime;
	private Long eventTime;
	private String action;
	private String actionResultType;

	private Long resourceUpdateTime;
	private String resourceUpdateType;
	private String requestType;
	private String resourceDataVersion;

	private List<String> relatedNrn;
	private List<String> group;

	private Map<String, String> tag;

	private String productDataVersion;
	private String productDataStatus;
	private Map<String, Object> productData;
	private String comment;

	private String sourceType;
	private String sourceSubType;
	private String sourceIp;
}
