<div class="col-md-12">
    <div class="table-toolbar" style="margin: 5px auto">
        <div class="row">
            <div class="col-md-7">
                <button id="adduser_btn" class="btn btn-default" onclick="addItem()"> 新 增</button>
                <button id="adduser_btn" class="btn btn-danger" onclick="deleteItem()"> 删 除</button>
            </div>
        </div>
    </div>
    <div id="datagrid2" class="mini-datagrid" style="width:100%;height:425px;" allowCellSelect="false"
         onlyCheckSelection="true" allowResize="true"
         url="/dictItem/getPage"
         sizeList="[20,30,50]" pageSize="10"
         idField="itemId" multiSelect="true">
        <div property="columns">
            <div type="checkcolumn" width="5%"></div>
            <div field="sortNum" width="5%" headerAlign="center" align="center">序号</div>
            <div field="name" width="20%" headerAlign="center" allowSort="true" align="center">字典项名称</div>
            <div field="value" width="15%" headerAlign="center" allowSort="true" align="center">value值</div>
            <div field="code" width="10%" headerAlign="center" allowSort="true" align="center">字典项编码</div>
            <div field="description" width="20%" headerAlign="center" allowSort="true">描述</div>
            <div width="10%" headerAlign="center" allowSort="false" align="center" renderer="defaultVal">默认值</div>
            <div width="15%" headerAlign="center" allowSort="true" renderer="renderOp" align="center">操作</div>
        </div>
    </div>
</div>
<script type="text/javascript">
    mini.parse();
    var dId =${dictId};
    var grid2 = mini.get("datagrid2");

    grid2.load({dataFlag: 1, dictId: dId});
    //操作加载
    function renderOp(e) {
        var str = "";
        var rec = e.record;
        if (!rec.siteId) {
            if (rec.isHide == 0 || rec.isHide == null) {
                hdBtn = '<button class="btn btn-sm btn-info" onclick="hide(' + rec.itemId + "," + rec.isHide + ')">隐 藏</button> '
            } else {
                hdBtn = '<button class="btn btn-sm btn-info" onclick="hide(' + rec.itemId + "," + rec.isHide + ')">显 示</button> '
            }
            tgBtn = '<button class="btn btn-sm btn-default" onclick="editItem(' + rec.itemId + ')">修 改</button> '
            qsBtn = '<button class="btn btn-sm btn-danger" onclick="delItem(' + rec.itemId + ')">删 除</button>'
            str += hdBtn + tgBtn + qsBtn;
        } else {
            str = "系统默认";
        }
        return str;
    }

    function defaultVal(e) {
        var str = "";
        var rec = e.record;
        if (!rec.siteId) {
            if (rec.isDefault == 1) {
                str = "<input type='radio' value='1' id='" + rec.itemId + "' onclick='resetDefault(" + rec.itemId + "," + rec.isDefault + ")' checked/>";
            } else {
                str = "<input type='radio' value='1' id='" + rec.itemId + "' onclick='resetDefault(" + rec.itemId + "," + rec.isDefault + ")' />";
            }
        } else {
            if (rec.isDefault == 1) {
                str = "是";
            } else {
                str = "否";
            }
        }
        return str;
    }

    function resetDefault(itemId, isDefault) {
        var data = {};
        if (isDefault == 1) {
            data = {itemId: itemId, dictId: dId, flag: 0};
        } else {
            data = {itemId: itemId, dictId: dId, flag: 1};
        }
        $.ajax({
            data: data,
            url: "/dictItem/setDefault",
            success: function (text) {
                grid2.reload();
            }
        });

    }

    function addItem() {
        editItemIndex = Mine.layer.openWin2("新增字典项","/dictItem/addItem?dictId=" + dId, '550', '400');
    }

    function hide(itemId, isHide) {
        var data = {};
        if (isHide == 1) {
            data = {itemId: itemId, flag: 0};
        } else {
            data = {itemId: itemId, flag: 1};
        }
        $.ajax({
            data: data,
            url: "/dictItem/setHide",
            success: function (text) {
                grid2.reload();
            }
        });
    }

    function editItem(id) {
        editItemIndex = Mine.layer.openWin2('修改字典项',"/dictItem/editItem?itemId=" + id, '550', '400');
    }

    function delItem(id) {
        var ids = [];
        ids.push(id);
        if (confirm("确定删除选中记录？")) {
            $.ajax({
                data: {
                    ids: ids
                },
                url: "/dictItem/deleteItem",
                success: function (text) {
                    grid2.reload();
                }
            });
        }
    }

    function deleteItem() {
        var rows = grid2.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定删除选中记录？")) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    ids[i] = rows[i].itemId;
                }
                grid2.loading("操作中，请稍后......");
                $.ajax({
                    data: {
                        ids: ids
                    },
                    url: "/dictItem/deleteItem",
                    success: function (text) {
                        grid2.reload();
                    }
                });
            }
        } else {
            alert("请选中一条记录！");
        }
    }

    function search() {
        var name = mini.get("name").getValue();
        grid2.load({name: name, dataFlag: 1});
    }

    function onKeyEnter(e) {
        search();
    }

</script>
