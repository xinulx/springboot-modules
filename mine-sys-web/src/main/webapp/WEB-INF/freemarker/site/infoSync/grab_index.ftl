<div class="portlet light">
    <div class="portlet-body">
        <div class="table-toolbar" style="margin-bottom: 5px">
            <div class="row">
                <div class="col-md-6 col-lg-6">
                    <button type="button" class="btn btn-default" onClick="edit()">添加</button>
                    <button type="button" class="btn btn-danger btn-delete" onClick="batchDel()">批量删除</button>
                </div>
                <div class="col-md-6 col-lg-6">
                    <form class="form-inline tr search-toolbar pull-right" role="form">
                        <div class="form-group">
                            <input class="form-control" name="name" id="name" placeholder="关键词"/>
                        </div>
                        <button type="button" class="btn btn-default" onClick="search()">搜 索</button>
                        <button type="button" class="btn btn-success" onClick="searchClear()">显示全部</button>
                    </form>
                </div>
            </div>
        </div>
        <div id="datagrid" class="mini-datagrid ls-mini-grid-cell" style="width:100%;height:600px;" multiSelect="true"
             idField="id" sizeList="[15,20,30,45]" pageSize="20"
             url="/infoSync/grab/getPage">
            <div property="columns">
                <div type="checkcolumn" width="5%" headerAlign="center" align="center"></div>
                <div field="id" width="5%" headerAlign="center" align="center">ID</div>
                <div field="name" width="15%" headerAlign="center" align="center">任务名称</div>
                <div field="domain" width="15%" headerAlign="center" align="center">所属域名</div>
                <div field="grabUrl" width="40%" headerAlign="center" align="center">抓取地址</div>
                <div width="20%" headerAlign="center" align="center" renderer="renderOpt">操作</div>
            </div>
        </div>

    </div>
</div>
<script src="/js/common/jsonviewer.js"></script>
<div id="jsonDiv" class="hidden"><pre id="json-renderer"></pre></div>
<script>

    var cur = {
        params: {
            name: ''
        }
    };
    $(document).ready(function () {
        mini.parse();
        cur.grid = mini.get("datagrid");
        cur.grid.load();
        $.autoHeight('datagrid', 120);
    });

    function edit(id) {
        var url = "/infoSync/grab/edit";
        if (id) {
            url = "/infoSync/grab/edit?id=" + id;
        }
        editIndex = Mine.layer.openWin('编辑网页采集配置', url, "600", "500");
    }

    // 操作列
    function renderOpt(e) {
        var rec = e.record;
        var exeStr =  '<button class="btn btn-default btn-sm" onclick="runGrab(' + rec.id + ')">立即抓取</button> ';
        var editStr = '<button class="btn btn-default btn-sm" onclick="edit(' + rec.id + ')">修 改</button> ';
        var moveStr = '<button class="btn btn-danger btn-sm" onclick="batchDel(' + rec.id + ')">删除</button> ';
        return exeStr + editStr + moveStr;
    }


    //条件
    var search = function () {
        cur.params.title = $("#name").val();
        cur.grid.load(cur.params);
    };

    //清除条件
    var searchClear = function () {
        $("#name").val('');
        cur.params.title = '';
        cur.grid.load(cur.params);
    };

    function batchDel(id) {
        var ids = [];
        if (id) {
            ids.push(id);
        } else {
            var rows = cur.grid.getSelecteds();
            if (rows.length == 0) {
                alert("请选择一条记录");
                return;
            }
            for (i in rows) {
                ids.push(rows[i].id);
            }
        }
        if (confirm("确认删除选中过的记录？")) {
            $.get(
                "/infoSync/grab/batchDel",
                {ids: ids},
                function (text) {
                    Mine.layer.tips("删除成功！", 1);
                    cur.grid.reload();
                    cur.grid.deselectAll(true);
                }
            );
        }
    }

    function runGrab(id){
        $.get(
            "/infoSync/grab/runGrab",
            {id: id},
            function (text) {
                try {
                    var input = eval('(' + JSON.stringify(text.data) + ')');
                }
                catch (error) {
                    return alert("Cannot eval JSON: " + error);
                }
                var options = {
                    collapsed: false,
                    withQuotes: true
                };
                $('#json-renderer').jsonViewer(input, options);
                layer.alert($("#jsonDiv").html(),{area:["800px","600px"],scrollbar: false});
            }
        );
    }

</script>