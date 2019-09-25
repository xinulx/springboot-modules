<!-- 分类数据项 -->
<div class="col-md-12" align="center" style="margin: 0 15%">
    <ul class="col-md-8 nav nav-tabs">
    <#list resultObj as item>
        <li role="atab${item_index + 1}" <#if item_index == 0>class="active"</#if>>
            <a href="#atab${item_index + 1}" class="btn" desc="${item.desc}" data-toggle="tab">${item.name}</a>
        </li>
    </#list>
    </ul>
</div>
<div class="col-md-12 tab-content bg-danger" align="center">
    <#list resultObj as item>
        <div class="tab-pane <#if item_index == 0>active</#if>" id="atab${item_index + 1}" style="width:100%">
            <#include "item_chart.ftl">
        </div>
    </#list>
</div>
<!-- 子数据项 -->
<div class="date_one datecon">
    <#list resultObj as item>
    <#if item.children?size gt 0>
    <div class="date_con1" id="atab${item_index}">
        <div class="guimo_tab1">
            <ul class="clearfix">
            <#list item.children as child>
                <li>
                    <a name="${child.name}" desc="${(child.desc)!}" importName="${(child.importName)!}" title="${child.name}（${child.unit}）" href="javascript:loadItemChart(0,'${child.name}','${child.importName}','${child.desc}')">
                        ${child.name}（${child.unit}）
                    </a>
                </li>
           </#list>
            </ul>
        </div>
    </div>
    <#else>
    <div id="atab${item_index}"></div>
    </#if>
    </#list>
</div>
<!-- 数据项说明及图表展示 -->