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
<div id="myChart1" class="bg-warning" style="width: 800px;height: 500px"></div>
<div id="myChart2" class="bg-warning" style="width: 800px;height: 500px"></div>
<div class="text-warning" align="center">
    八百里分麾下炙，五十弦翻塞外声
</div>
</body>
</html>
<script src="/plugins/echarts/echarts.min.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        var xData1 = eval(["2017.04", "2017.05", "2017.06", "2017.07", "2017.08", "2017.09"]);
        var yData1 = eval([154.6, 184.2, 219.7, 253.3, 286.2, 321.4]);
        var myChart1 = echarts.init(document.getElementById('myChart1'));
        initMap(myChart1, xData1, yData1, "规模以上工业增加值", "#77DDFF");
        var xData2 = eval(["2018.04", "2018.05", "2018.06", "2018.07", "2018.08", "2018.09"]);
        var yData2 = eval([10.1, 10.1, 10.1, 10.2, 9.5, 9.1]);
        var myChart2 = echarts.init(document.getElementById('myChart2'));
        initMap(myChart2, xData2, yData2, "规模以上工业增加值", "#77DDFF");
    });

    function initMap(chart, xData, yData, charName, colorType) {
       // 显示标题，图例和空的坐标轴
        chart.setOption({
            title: {
                text: '',
                left: ''
            },
            tooltip: {},
            legend: {
                data: [charName]
            },
            xAxis: {
                data: xData
            },
            toolbox: {
                show: false,
                feature: {
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            yAxis: [{
                type: 'value',
                axisLabel: {
                    formatter: '{value} '
                }
            }],
            series: [{
                name: charName,
                type: 'bar',
                color: '',
                barWidth: 30,
                itemStyle: {
                    normal: {
                        label: {show: true, position: 'top'},
                        position: 'bottom',
                        color: colorType,
                        // 定制显示（按顺序）
                        color: function (params) {
                            var colorList = ['#C33531', '#EFE42A', '#64BD3D', '#EE9201', '#29AAE3', '#B74AE5', '#0AAF9F', '#E89589', '#16A085', '#4A235A', '#C39BD3 ', '#F9E79F', '#BA4A00', '#ECF0F1', '#616A6B', '#EAF2F8', '#4A235A', '#3498DB'];
                            return colorList[params.dataIndex]
                        }
                    }
                }
                /*, markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                }*/,
                data: yData
            }]

        });
    }
</script>