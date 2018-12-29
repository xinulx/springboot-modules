<!DOCTYPE html>
<html lang="z-ch">
<head>
    <title>${ctx}</title>
    <@includeX id="29" />
</head>
<body>
            <!--最新发布-->
<div class="sjfb_title"><span>最新发布<i></i></span></div>
<!-- 分类数据项 -->
<div class="sjfb_tab">
    <div class="j-scroll" data-setting="{autoScroll:false}">
        <span class="prev"></span>
        <span class="next"></span>
        <div class="scroll-wrap">
            <ul class="scroll-body">
                                <li>
                    <a desc="21" href="javascript:void(0)" data-id="#atab1"
                       data-toggle="mouse">
                        进出口总额
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<!-- 子数据项 -->
<div class="date_one datecon">
    <div class="date_con1" id="atab0">
        <div class="guimo_tab1">
            <ul class="clearfix">
                <li>
                    <a name="出口" desc="" importName="出口" title="出口（亿元）" href="javascript:loadItemChart(0,'出口','出口','')">
                        出口（亿元）
                    </a>
                </li>
                <li>
                    <a name="#进口" desc="" importName="#进口" title="#进口（亿元）" href="javascript:loadItemChart(0,'#进口','#进口','')">
                        #进口（亿元）
                    </a>
                </li>
            </ul>
        </div>
    </div>
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
</body>
</html>