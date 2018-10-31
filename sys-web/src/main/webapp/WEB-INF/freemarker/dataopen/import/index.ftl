<div class="portlet light">
    <div class="portlet-body">
        <div class="table-toolbar">
            <div class="row">
                <div class="col-md-6 col-lg-6">
                    <button type="button" class="btn btn-default" onClick="edit()">添加指标信息</button>
                    <button type="button" class="btn btn-warning" onClick="batchImport()">批量导入</button>
                    <button type="button" class="btn btn-danger btn-delete" onClick="batchDel()">批量删除</button>
                </div>
                <div class="col-md-6 col-lg-6">
                    <form class="form-inline tr search-toolbar pull-right" role="form">
                        <div class="form-group">
                            <input class="form-control" name="date" id="date" placeholder="日期"/>
                        </div>
                        <div class="form-group">
                            <input class="form-control" name="name" id="name" placeholder="数据项名称"/>
                        </div>
                        <button type="button" class="btn btn-default" onClick="search()">搜 索</button>
                        <button type="button" class="btn btn-success" onClick="searchClear()">显示全部</button>
                    </form>
                </div>
            </div>
        </div>
        <br/>
        <div id="datagrid" class="mini-datagrid ls-mini-grid-cell" style="width:100%;height:600px;" multiSelect="true"
             idField="id" sizeList="[15,20,30,45]" pageSize="20"
             url="/dataPublish/dataImport/getPage">
            <div property="columns">
                <div type="checkcolumn" width="5%" headerAlign="center" align="center"></div>
                <div type="indexcolumn" width="5%" headerAlign="center" align="center">序号</div>
                <div field="periodDate" width="10%" headerAlign="center" align="center" allowSort="true" showSortIcon="true">日期</div>
                <div field="itemName" width="20%" headerAlign="center" align="center">数据项名称</div>
                <div field="unitValue" width="15%" headerAlign="center" align="center" allowSort="true" showSortIcon="true">绝对量</div>
                <div field="growth" width="15%" headerAlign="center" align="center" allowSort="true" showSortIcon="true">增速</div>
                <div field="remark" width="30%" headerAlign="center" align="center">解读</div>
                <div width="10%" headerAlign="center" align="center" renderer="renderOpt">操作</div>
            </div>
        </div>

    </div>
</div>
<script>

    var cur = {
        params:{
            name:''
        }
    };
    $(document).ready(function () {
        mini.parse();
        cur.grid = mini.get("datagrid");
        cur.grid.load();
    });

    function batchImport(){
        editIndex = Mine.layer.openWin('[淮北市月度或季度指标]数据导入',"/dataPublish/dataImport/importPage", "500", "300");
    }

    function edit(id){
        var url = "/dataPublish/dataImport/edit";
        if(id){
            url = "/dataPublish/dataImport/edit?id="+id;
        }
        editIndex = Mine.layer.openWin('编辑指标数据',url, "500", "300");
    }

    // 操作列
    function renderOpt(e) {
        var rec = e.record;
        var editStr = '<button class="btn btn-default btn-sm btn-default" onclick="edit(' + rec.id + ')">修 改</button> ';
        var moveStr = '<button class="btn btn-default btn-sm btn-danger" onclick="batchDel(' + rec.id + ')">删除</button> ';
        return editStr + moveStr;
    }


    //条件
    var search = function () {
        cur.params.name = $("#name").val();
        cur.params.periodDate = $("#date").val();
        cur.grid.load(cur.params);
    }

    //清除条件
    var searchClear = function () {
        $("#name,#date").val('');
        cur.params.name = '';
        cur.params.periodDate = '';
        cur.grid.load(cur.params);
    }

    function batchDel(id){
        var ids = [];
        if(id){
            ids.push(id);
        }else{
            var rows = cur.grid.getSelecteds();
            if(rows.length == 0){
                alert("请选择一条记录");
                return;
            }
            for(i in rows){
                ids.push(rows[i].id);
            }
        }
        if(confirm("确认删除选中过的记录？")){
            $.get(
                "/dataPublish/dataImport/batchDel",
                {ids: ids},
                function (text) {
                    Mine.layer.tips("删除成功！",1);
                    cur.grid.reload();
                    cur.grid.deselectAll(true);
                }
            );
        }
    }

</script>