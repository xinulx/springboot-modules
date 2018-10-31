<div class="portlet light">
    <div class="portlet-body">
        <div class="table-toolbar">
            <div class="row">
                <div class="col-md-6 col-lg-6">
                    <button type="button" class="btn btn-primary" onClick="edit(0,0)">添加分类</button>
                </div>
                <div class="col-md-6 col-lg-6 pull-right">
                    <form class="form-inline tr search-toolbar pull-right" role="form">
                        <div class="form-group">
                            <input class="form-control" name="name" id="name" placeholder="数据项名称"/>
                        </div>
                        <button type="button" class="btn btn-default" onClick="search()">搜 索</button>
                        <button type="button" class="btn btn-success btn-show-all" onClick="searchClear()">显示全部</button>
                    </form>
                </div>
            </div>
        </div>
        <br/>
        <div id="datagrid" class="mini-treegrid ls-mini-grid-cell" style="width:100%;height:600px;" multiSelect="true"
             url="/dataPublish/dataItem/getPage" expandOnLoad="true"
             showTreeIcon="false" treeColumn="itemName" idField="id" parentField="parentId" resultAsTree="false">
            <div property="columns">
                <div type="indexcolumn" width="5%" headerAlign="center" align="center">序号</div>
                <div name="itemName" field="itemName" width="30%" headerAlign="center" align="left">数据项名称</div>
                <div field="itemImportName" width="25%" headerAlign="center" align="center">数据项导入名称</div>
                <div field="itemUnit" width="20%" headerAlign="center" align="center">数据项单位</div>
                <div width="20%" headerAlign="center" align="right" renderer="renderOpt">操作</div>
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
        cur.grid.on("drawcell", function (e) {
           var rec = e.record;
           if(rec.parentId == 0 && e.field == 'itemName'){
               e.cellStyle = "background:#ecedef;color:#57a3f1;font-weight:800";
               e.isLeaf = false;
               e.cellHtml="<span class=\"glyphicon glyphicon-bookmark\" aria-hidden=\"true\"></span>&nbsp;" + e.cellHtml;
           }else if(e.field == 'itemName'){
               e.cellHtml="<span class=\"glyphicon glyphicon-book\" aria-hidden=\"true\"></span>&nbsp;" + e.cellHtml;
           }
        });
        cur.grid.load();
    });

    function edit(id,pid){
        var url = "/dataPublish/dataItem/edit?id="+id+"&parentId="+pid;
        editIndex = Mine.layer.openWin('编辑数据项',url, "500", "300");
    }

    // 操作列
    function renderOpt(e) {
        var rec = e.record;
        var addStr = '<button class="btn btn-default btn-sm btn-primary" onclick="edit(0,' + rec.id + ')">添加数据项</button> ';
        var editStr = '<button class="btn btn-default btn-sm btn-default" onclick="edit(' + rec.id + ','+rec.parentId+')">修 改</button> ';
        var moveStr = '<button class="btn btn-default btn-sm btn-danger" style="margin-right:25px" onclick="batchDel(' + rec.id + ')">删除</button> ';
        if(rec.parentId == 0){
            return addStr + editStr + moveStr;
        }else{
            return editStr + moveStr;
        }
    }


    //条件
    var search = function () {
        cur.params.name = $("#name").val();
        cur.grid.load(cur.params);
    };

    //清除条件
    var searchClear = function () {
        $("#name").val('');
        cur.params.name = '';
        cur.grid.load(cur.params);
    };

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
                "/dataPublish/dataItem/batchDel",
                {ids: ids},
                function (d) {
                    if(d.status == 1){
                        Mine.layer.tips("删除成功！",1);
                        cur.grid.reload();
                        cur.grid.deselectAll(true);
                    }else{
                        Mine.layer.tips(d.desc,2);
                    }
                }
            );
        }
    }

</script>