<link href="/css/jsonviewer.css" type="text/css" rel="stylesheet" />
<div class="portlet light">
    <div class="portlet-body">
        <div class="table-toolbar" style="margin-bottom: 5px">
            <div class="row">
                <div class="col-md-7">
                    <div class="btn-group">
                        <button id="adduser_btn" class="btn btn-default btn-refresh" onclick="reload()"> 刷 新</button>
                    </div>
                    <div class="btn-group">
                        <button id="adduser_btn" class="btn btn-success btn-refresh-all" onclick="reloadAll()"> 刷新全部
                        </button>
                    </div>
                    <div class="btn-group">
                        <button id="adduser_btn" class="btn btn-danger btn-refresh-all" onclick="deleteAll()"> 删除全部
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:600px;" allowCellSelect="false"
             onlyCheckSelection="true"
             allowResize="true" url="/cache/getPage" sizeList="[20,30,50]" pageSize="20" showPager="false"
             idField="id" multiSelect="true">
            <div property="columns">
                <div type="checkcolumn" width="5%"></div>
                <div type="indexcolumn" width="10%" headerAlign="center" align="center">序号</div>
                <div field="cacheKey" width="70%" headerAlign="center" allowSort="flase" align="left">缓存CacheKey</div>
                <div width="15%" headerAlign="center" allowSort="true" renderer="renderOp" align="center">操作</div>
            </div>
        </div>
    </div>
</div>
<div id="jsonDiv" class="hidden"><pre id="json-renderer"></pre></div>
<script src="/js/jsonviewer.js"></script>
<script>

    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load({dataFlag: 1});

    //操作加载
    function renderOp(e) {
        var str;
        var rec = e.record;
        var tgBtn = '<button id="adduser_btn" class="btn btn-info btn-sm" onclick="getValue(\'' + rec.cacheKey + '\')">查 看</button>&nbsp;'+
                    '<button id="adduser_btn" class="btn btn-default btn-sm" onclick="reloadByKey(\'' + rec.cacheKey + '\')">刷 新</button>&nbsp;'+
                    '<button id="adduser_btn" class="btn btn-danger btn-sm" onclick="deleteByKey(\'' + rec.cacheKey + '\')">删 除</button>';
        str = tgBtn;
        return str;
    }

    function reloadAll() {
        if (confirm("确定要刷新所有缓存？")) {
            $.ajax({
                url: "/cache/reloadAllCache",
                success:function (d) {
                    if (d.status == 0) {
                        Mine.layer.tips("刷新失败：" + d.desc,2);
                    } else {
                        Mine.layer.tips("成功",1);
                    }
                },
                error:function(e){
                    alert("刷新失败：" + JSON.stringify(e));
                }
            });
        }
    }

    function reloadByKey(key) {
        if (confirm("确定刷新选中记录？")) {
            var cacheKey = [];
            cacheKey.push(key);
            $.ajax({
                data: {
                    cacheKey: cacheKey
                },
                url: "/cache/reloadCache",
                success: function (text) {
                    if (text.status == 0) {
                        Mine.layer.tips("刷新失败：" + d.desc,2);
                    } else {
                        Mine.layer.tips("刷新成功",1);
                    }
                },
                error:function(e){
                    alert("刷新失败：" + JSON.stringify(e));
                }
            });
        }

    }

    function getValue(key) {
        $.ajax({
            data: {
                key: key
            },
            url: "/cache/getCacheBySid",
            success: function (text) {
                try {
                    var input = eval('(' + JSON.stringify(text.attributes) + ')');
                }
                catch (error) {
                    return alert("Cannot eval JSON: " + error);
                }
                var options = {
                    collapsed: false,
                    withQuotes: true
                };
                $('#json-renderer').jsonViewer(input, options);
                layer.alert($("#jsonDiv").html(),{area:["750px","640px"],scrollbar: false});
            }
        });

    }

    function deleteByKey(key){
        $.ajax({
            data: {
                key: key
            },
            url: "/cache/delete",
            success: function (d) {
                if(d.status == 1){
                    Mine.layer.tips(d.desc,1);
                    grid.reload();
                }else{
                    Mine.layer.tips("操作失败，请联系管理员",2);
                }
            }
        });
    }

    function deleteAll(){
        if (confirm("确定删除所有记录？")) {
            $.ajax({
                url: "/cache/deleteAll",
                success: function (d) {
                    if (d.status == 1) {
                        Mine.layer.tips(d.desc, 1);
                        grid.reload();
                    } else {
                        Mine.layer.tips("操作失败，请联系管理员", 2);
                    }
                }
            });
        }
    }

    function reload() {
        var grid = mini.get("datagrid1");
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定刷新选中记录？")) {
                var cacheKey = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    cacheKey[i] = rows[i].cacheKey;
                }
                grid.loading("操作中，请稍后......");
                $.ajax({
                    data: {
                        cacheKey: cacheKey
                    },
                    url: "/globConfig/reloadCache",
                    success: function (text) {
                        if (text.status == 0) {
                            Mine.layer.tips("刷新失败：" + d.desc,2);
                        } else {
                            Mine.layer.tips("刷新成功",1);
                        }
                        grid.reload();
                    }
                });
            }
        } else {
            Mine.layer.tips("请选中一条记录",0);
        }
    }
</script>