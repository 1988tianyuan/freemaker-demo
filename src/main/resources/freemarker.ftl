policyName => ${policyName}
policyNo => ${policyNo}
type => ${type}
<#list permissions as permission>
    permission[${permission_index}].effect => ${permission.effect}
    <#if permission.targets?size != 0>
        <#list permission.targets?sort_by("product") as target>
            permission[${permission_index}].target[${target_index}].product => ${target.product}
            <#list target.actionNrns as actionNrn>
                permission[${permission_index}].target[${target_index}].actionNrns[${actionNrn_index}] => ${actionNrn}
            </#list>
            <#list target.resources as rs>
                permission[${permission_index}].target[${target_index}].resources[${rs_index}] => ${rs.mbrNo}
                permission[${permission_index}].target[${target_index}].resources[${rs_index}] => ${rs.resource}
                permission[${permission_index}].target[${target_index}].resources[${rs_index}] => ${rs.region}
            </#list>
        </#list>
    </#if>
</#list>



