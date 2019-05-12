<style>
    .holiday-date li {
        list-style: none;
        margin: 5px 0;
        padding: 5px 0;
    }

    .btn-ok, .btn-ok:hover {
        color: #fff;
        background-color: #4cd085;
        border-color: #4cd085;
    }

    .btn-remove, .btn-remove:hover {
        color: #CCCCCC;
        background-color: #FFFFFF;
        border-color: #CCCCCC;
    }
    .w300{
        width:300px;
    }
    .w200{
        width:200px;
    }
</style>
<div id="ui_layout" class="mini-layout" style="width:100%;height:100%;">
    <div region="center" style="border:none; overflow: auto;display: none">
        <div class="tabbable-line" style=" margin:12px 20px 15px;">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_1_1" data-toggle="tab">限时发布策略</a>
                </li>
                <li>
                    <a href="#tab_1_2" data-toggle="tab" onclick="javascript:$('#searchAll').trigger('click');">受限操作配置</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab_1_1">

                    <form id="strategyForm" role="form" ms-controller="strategyForm">
                        <input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <div style="font-weight: 900;font-size: 14px;margin: 5px 0">启用配置</div>
                                <div class="radio-group">
                                    <div class="">
                                        <label class="radio-inline">
                                            <input type="radio" name="isUsed" value="1"> 启用 </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="isUsed" value="0"> 禁用 </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div style="font-weight: 900;font-size: 14px;margin: 5px 0">工作日[周一 ~ 周五]</div>
                                <input type="hidden" name="workStartDate">
                                <input type="datetime" class="form-control w300" placeholder="开始时间" id="workStartDate"
                                       name="workStartDateShow"
                                       onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'workEndDate\')}',readOnly:true})"
                                       readonly ms-duplex="workStartDate">
                                <input type="hidden" name="workEndDate">
                                <input type="datetime" class="form-control w300" placeholder="结束时间" id="workEndDate"
                                       name="workEndDateShow"
                                       onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'workStartDate\')}',readOnly:true})"
                                       readonly ms-duplex="workEndDate">
                            </div>
                            <div class="form-group">
                                <div style="font-weight: 900;font-size: 14px;margin: 5px 0">双休日(<span
                                            class="text-warning">双休日默认不允许发布，若配置了时间段，则在对应时间段可以发布！主要用于假前、假后可能会有双休日也上班的情况！</span>)
                                </div>
                                <div class="conf-content">
                                    <div class="mt-checkbox-inline" id="ruleCheckbox_">
                                        <label class="mt-checkbox">
                                            <input value="saturday" type="checkbox" checked="checked" name="saturday"
                                                   ms-duplex="saturday"> 周六<span></span>
                                        </label>
                                        <label class="mt-checkbox">
                                            <input value="sunday" type="checkbox" checked="checked" name="sunday"
                                                   ms-duplex="sunday"> 周日<span></span>
                                        </label>
                                        <span class="help-inline">（默认全选，若假前假后只有一天在双休日范围内，则请勾选）</span>
                                    </div>
                                </div>
                                <input type="text" class="form-control w300" placeholder="开始时间" id="weekStartDate"
                                       name="weekStartDate"
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'weekEndDate\')}',readOnly:true})"
                                       readonly ms-duplex="weekStartDate">
                                <input type="text" class="form-control w300" placeholder="结束时间" id="weekEndDate"
                                       name="weekEndDate"
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'weekStartDate\')}',readOnly:true})"
                                       readonly ms-duplex="weekEndDate">
                            </div>
                            <div class="form-group">
                                <div style="font-weight: 900;font-size: 14px;margin: 5px 0">节假日</div>
                                <div class="holiday-date">
                                    <li>
                                        <input type="text" class="form-control w300" placeholder="开始时间" id="startDate1"
                                               name="startDate1"
                                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate1\')}',readOnly:true})"
                                               readonly/>
                                        <input type="text" class="form-control w300" placeholder="结束时间" id="endDate1"
                                               name="endDate1"
                                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate1\')}',readOnly:true})"
                                               readonly/>
                                        <input class="form-control w200" name="holidayName" placeholder="假日名称"/>
                                        <button class="btn btn-success" type="button" onclick="editButton(1)">添加
                                        </button>
                                    </li>
                                </div>
                            </div>
                            <div class="form-actions noborder">
                                <button type="submit" class="btn btn-default btn-save">保 存</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="tab-pane" id="tab_1_2">
                    <div class="table-toolbar" style="padding-bottom: 5px;margin-top: 5px;">
                        <div class="row">
                            <div class="col-md-4">
                                <input class="form-control" id="searchKey" placeholder="请输入映射地址/方法名称"/>
                            </div>
                            <div class="col-md-8">
                                <button class="btn btn-primary" onclick="search()"><span
                                            class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查询
                                </button>
                                <button id="searchAll" class="btn btn-info" onclick="searchAll()"><span
                                            class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>&nbsp;显示所有
                                </button>
                                <button class="btn btn-default publishBtn btn-save-release" onclick="search('1')"><span
                                            class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;只显示已配置
                                </button>
                                <button class="btn btn-default publishBtn btn-save-release"
                                        onclick="updateStatus(null,'1')"><span class="glyphicon glyphicon-retweet"
                                                                               aria-hidden="true"></span>&nbsp;批量设置
                                </button>
                                <button id="addButton_btn" class="btn btn-success" onclick="asyncMapping()"><span
                                            class="glyphicon glyphicon-refresh" aria-hidden="true"></span>&nbsp;同步映射
                                </button>
                            </div>
                        </div>
                    </div>

                    <div id="url_datagrid" class="mini-datagrid" style="width:100%;height: 800px;" allowResize="true"
                         allowCellSelect="false" onlyCheckSelection="true"
                         sizeList="[20,30,50]" pageSize="20" idField="id" multiSelect="true"
                         url="/publishStrategy/getPage">
                        <div property="columns">
                            <div type="checkcolumn" width="5%"></div>
                            <div width="5%" headerAlign="center" allowSort="true" renderer="renderOp" align="center">
                                操作
                            </div>
                            <div field="url" width="30%" headerAlign="center" allowSort="flase" align="left">映射地址</div>
                            <div field="className" width="40%" headerAlign="center" allowSort="flase" align="left">类名
                            </div>
                            <div field="methodName" width="10%" headerAlign="center" allowSort="flase" align="center">
                                方法
                            </div>
                            <div field="type" width="10%" headerAlign="center" allowSort="flase" align="center">请求方式
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var cur = {
        holidaysCount: 1,
        isFull: true,
        srcUserIds: '',
        srcUserNames: ''
    };
    $(document).ready(function () {
        mini.parse();
        cur.grid = mini.get("url_datagrid");
        cur.grid.load();
        getStrategyEO();

    });

    function renderOp(e) {
        var rec = e.record;
        var opt = '<button type="button" class="btn btn-sm btn-default btn-ok" onclick="updateStatus(' + rec.id + ',0)" title="取消"><span class="glyphicon glyphicon-ok"></span></button>';
        if (rec.isUsed == 0) {
            opt = '<button type="button" class="btn btn-sm btn-default btn-remove" onclick="updateStatus(' + rec.id + ',1)" title="设置"><span class="glyphicon glyphicon-ok"></span></button>';
        }
        return opt;
    }

    function updateStatus(id, status) {
        var ids = [];
        if (id) {
            ids.push(id);
        } else {
            var selecteds = cur.grid.getSelecteds();
            for (var i = 0; i < selecteds.length; i++) {
                ids.push(selecteds[i].id);
            }
        }
        if (ids.length == 0) {
            alert("请选择配置项");
            return;
        }
        $.ajax({
            url: '/publishStrategy/batchUpdate',
            data: {
                ids: ids,
                status: status
            },
            success: function (d) {
                if (d.status == 1) {
                    Mine.layer.tips("设置成功！");
                    cur.grid.reload();
                }
            }
        });
    }

    function search(status) {
        var val = $("#searchKey").val();
        if (status) {
            cur.grid.load({searchText: val, type: status});
        } else {
            if (!val || $.trim(val) == '') {
                alert("请输入查询条件");
                return;
            }
            cur.grid.load({searchText: val});
        }
    }

    function searchAll() {
        $("#searchKey").val('');
        cur.grid.load();
    }

    function getHolidayDates() {
        var dates = [];
        $(".holiday-date li").each(function () {
            var date = new Object();
            date.startDate = $(this).find('input:eq(0)').val();
            date.endDate = $(this).find('input:eq(1)').val();
            date.holidayName = $(this).find('input:eq(2)').val();
            if (date.holidayName == '') {
                cur.isFull = false;
            }
            if (date.startDate == '') {
                cur.isFull = false;
            }
            if (date.endDate == '') {
                cur.isFull = false;
            }
            dates.push(date);
        });
        return dates;
    }

    function editButton(type, obj, values) {
        if (type == 0) {
            $(obj).parent().remove();
            cur.holidaysCount--;
            return;
        }
        cur.holidaysCount++;
        var li = "<li>\n" +
            "    <input type=\"text\" class=\"form-control w300\" placeholder=\"开始时间\" id=\"startDate" + cur.holidaysCount + "\"\n" +
            "      name=\"startDate" + cur.holidaysCount + "\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\\'endDate" + cur.holidaysCount + "\\')}',readOnly:true})\" readonly />\n" +
            "    <input type=\"text\" class=\"form-control w300\" placeholder=\"结束时间\" id=\"endDate" + cur.holidaysCount + "\"\n" +
            "      name=\"endDate" + cur.holidaysCount + "\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\\'startDate" + cur.holidaysCount + "\\')}',readOnly:true})\" readonly />\n" +
            "    <input class=\"form-control w200\" name=\"holidayName\" placeholder=\"假日名称\" />\n" +
            "    <button class=\"btn btn-success\" type=\"button\" onclick=\"editButton(1)\">添加</button>\n" +
            "    <button class=\"btn btn-danger\" type=\"button\" onclick=\"editButton(0,this)\">删除</button>"
        " </li>";
        $(".holiday-date").append(li);
        if (values && values != '') {
            var _this = $(".holiday-date li:last");
            _this.find('input:eq(0)').val(values['startDate']);
            _this.find('input:eq(1)').val(values['endDate']);
            _this.find('input:eq(2)').val(values['holidayName']);
        }
    }

    function getStrategyEO() {
        $.ajax({
            url: "/publishStrategy/getStrategyEOById",
            success: function (data) {
                data = data.data;
                if(data.workStartDate){
                    data.workStartDate = data.workStartDate.slice(11,16);
                    $("input[name=workStartDateShow]").val(data.workStartDate);
                }
                if(data.workEndDate){
                    data.workEndDate = data.workEndDate.slice(11,16);
                    $("input[name=workEndDateShow]").val(data.workEndDate);
                }
                for(var name in data){
                    if(name != 'isUsed'){
                        $("input[name=" +name + "]").val(data[name]);
                    }
                }
                $("input[name=isUsed][value="+data.isUsed+"]").attr('checked',true);
                if(data.saturday){
                    $("input[name=saturday]").attr('checked',true);
                }else{
                    $("input[name=saturday]").attr('checked',false);
                }
                if(data.sunday){
                    $("input[name=sunday]").attr('checked',true);
                }else{
                    $("input[name=sunday]").attr('checked',false);
                }
                var holiDaysDates = data.holiDaysDates;
                if (holiDaysDates) {
                    var holiDaysDate = JSON.parse(holiDaysDates);
                    cur.holidaysCount = 0;
                    $(".holiday-date").empty();
                    for (var i = 0; i < holiDaysDate.length; i++) {
                        editButton(1, null, holiDaysDate[i]);
                    }
                    $(".holiday-date li:first button:eq(1)").remove();
                }
            }
        });
    }

    $('#strategyForm').validator({
        valid: function () {
            saveStrategyEO();
        }
    });

    function saveStrategyEO() {
        var value = Mine.getFormData("strategyForm");
        if ($("input[name=isUsed]")[0].checked) {
            value = value.replace("isUsed","isUsedTmp");
            value += '&isUsed=1';
        }
        var workStartDate = $("#workStartDate").val();
        if(workStartDate){
            value = value.replace("workStartDate=",'workStartDate=1970-01-01 ' + workStartDate + ':00');
        }
        var workEndDate = $("#workEndDate").val();
        if(workEndDate){
            value = value.replace("workEndDate=",'workEndDate=1970-01-01 ' + workEndDate + ':00');
        }
        var holiDaysDates = getHolidayDates();
        value += "&holiDaysDates=" + JSON.stringify(holiDaysDates);
        console.log(value);
        $.ajax({
            url: "/publishStrategy/saveStrategyEO",
            type:'post',
            data: value,
            success: function (data) {
                if (data.status == 1) {
                    Mine.layer.tips("保存成功");
                    return;
                } else {
                    Mine.layer.tips("保存失败", 2);
                }
            }
        });
    }

    function asyncMapping() {
        var tips = layer.msg('正在同步映射地址...', {icon: 16, shade: 0.01});
        $.ajax({
            url: '/publishStrategy/v1/getAllUrl',
            success:function (d) {
                if (d.status == 1) {
                    layer.close(tips);
                    Mine.layer.tips("同步成功！");
                    cur.grid.reload();
                }
            }
        });
    }
</script>