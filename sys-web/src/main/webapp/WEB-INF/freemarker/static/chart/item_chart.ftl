<div id="myChart1" class="bg-warning" style="width: 800px;height: 500px"></div>
<div id="myChart2" class="bg-warning" style="width: 800px;height: 500px"></div>
<script src="${ctx}/plugins/echarts/echarts.min.js" type="text/javascript"></script>
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