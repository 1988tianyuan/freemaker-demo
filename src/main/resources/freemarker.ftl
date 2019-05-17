loadBalancerAddress : ${productData.loadBalancerAddress}
<#list productData.loadBalancerRuleList as rule>
RuleList[${rule_index}].protocol : ${rule.protocol}
RuleList[${rule_index}].serverPort : ${rule.serverPort}
RuleList[${rule_index}].loadBalancerPort : ${rule.loadBalancerPort}
</#list>
algorithmTypeCode : ${productData.algorithmTypeCode}



