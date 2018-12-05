<div id="toolBtn" class="table-toolbar" style="margin-bottom: 5px">
    <div class="row">
        <div class="col-md-7">
            <form class="form-inline tr search-toolbar pull-left" role="form">
                <button type="button" class="btn btn-default" onclick="addOrEdit()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;添加
                </button>
                <button type="button" class="btn btn-danger" onclick="batchDel()">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>&nbsp;删除
                </button>
            </form>
        </div>
        <div class="col-md-5">
            <form class="form-inline tr search-toolbar pull-right" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" id="rolekey" name="rolekey" placeholder="编码/名称" />
                </div>
                <button type="button" class="btn btn-primary" onclick="search()">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查询
                </button>
                <button type="button" class="btn btn-warning" onclick="searchAll()">
                    <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>&nbsp;显示全部
                </button>
            </form>
        </div>
    </div>
</div>
<div id="dataGrid" class="mini-datagrid" style="width: 100%;overflow-x:hidden"
     url="${ctx}/role/getPage" idField="id"
     sizeList="[15,30,50,100]" pageSize="15" multiSelect="true" allowResize="true">
    <div property="columns">
        <div type="checkcolumn" width="5%">选择</div>
        <div type="indexcolumn" width="5%" headerAlign="center">序号</div>
        <div field="id" width="10%" headerAlign="center">ID</div>
        <div field="roleCode" width="15%" headerAlign="center">角色编码</div>
        <div field="roleName" width="15%" headerAlign="center" renderer="onGenderRenderer">角色名称</div>
        <div field="roleDesc" width="20%%" headerAlign="center" >描述</div>
        <div field="createDate" width="15%" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建日期</div>
        <div headerAlign="center" width="15%" align="center" renderer="onActionRenderer">操作</div>
    </div>
</div>
<script>
    mini.parse();
    var grid = mini.get("dataGrid");
    var organId = ${organId!''};
    resetHeight("dataGrid",120);
    grid.load({organId:organId});
    function search(){
        grid.load({searchKey:$("#rolekey").val(),organId:organId});
    }

    function searchAll(){
        $("#rolekey").val('');
        grid.load({organId:organId});
    }

    function menuAuth(){
        var id;
        var rows = grid.getSelecteds();
        if(rows.length == 0 || rows.length > 1){
            layer.msg("请选择一条记录",{icon:7,shade: 0.01,time:1000});
            return;
        }else{
            id = rows[0]['id'];
        }
        var url = "/role/menuAuth?organId="+organId+"&id="+id;
        roleIndex = Mine.layer.openWin("配置菜单",url,300,450);
    }

    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var id = record.id;
        var rowIndex = e.rowIndex;

        var s = '<button class="btn btn-default btn-sm" onclick="addOrEdit(' + id + ')">修改</button>&nbsp;'
                + '&nbsp;<button class="btn btn-danger btn-sm" onclick="batchDel(' + id + ')">删除</button>';
        return s;
    }

    function batchDel(id){
        var ids = [];
        if(id == null){
            var rows = grid.getSelecteds();
            if(rows.length == 0){
                layer.msg("请选择一条记录",{icon:7,shade: 0.01,time:1000});
                return;
            }else{
                for(var index in rows){
                    ids.push(rows[index].id)
                }
            }
        }else{
            ids.push(id);
        }
        console.log(ids);
        layer.confirm('是否确定删除选择记录？', {
            icon:3,
            btn: ['确定','取消'] //按钮
        }, function(){
            try {
                $.post("/role/delete", {ids: ids}, function (result) {
                    if (result.status == 1) {
                        layer.msg(result.desc, {icon: 1, shade: 0.01, time: 1000}, function (index) {
                            layer.close(index);
                            grid.reload();
                        });
                    } else {
                        layer.msg(result.desc, {icon: 2, shade: 0.01, time: 1000});
                    }
                });
            }catch(e){
                layer.msg(e, {icon: 2, shade: 0.01, time: 1000});
            }
        }, function(){
        });
    }
    var roleIndex;
    function addOrEdit(id){
        var url = id == null?"/role/edit?organId="+organId:"/role/edit?organId="+organId+"&id="+id;
        roleIndex = Mine.layer.openWin(id==null?"添加角色":"修改角色",url,500,420);
    }
</script>