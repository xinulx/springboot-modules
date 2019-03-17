<!--最新发布-->
<div class="sjfb_title"><span>最新发布<i></i></span></div>
<!-- 分类数据项 -->
<div class="sjfb_tab">
    <div class="j-scroll" data-setting="{autoScroll:false}">
        <span class="prev"></span>
        <span class="next"></span>
        <div class="scroll-wrap">
            <ul class="scroll-body">
                <#list resultObj as item>
                <li>
                    <a desc="${item.desc}" href="javascript:void(0)" data-id="#atab${item_index + 1}"
                       data-toggle="mouse">
                        ${item.name}
                    </a>
                </li>
                </#list>
            </ul>
        </div>
    </div>
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
<div class="sjfb_ny" id="tabs1">
    <div class="guimo_con">
        <div class="active">
            <ul class="guimo_list">
                <li><span>指标解释：</span><span id="desc_0"></span></li>
                <li><span>相关资料</span></li>
            </ul>
        </div>
    </div>
    <div class="guimo_tab2">
        <ul class="clearfix">
            <li class="active">
                <a href="javascript:void(0)" data-id="#agtab1" data-toggle="mouse">绝对量<span></span></a>
            </li>
            <li>
                <a href="javascript:void(0)" data-id="#agtab2" data-toggle="mouse">增速（%）<span></span></a>
            </li>
        </ul>
    </div>
    <div class="date_list" id="chart_0"></div>
</div>