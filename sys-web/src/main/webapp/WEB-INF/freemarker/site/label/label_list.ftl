<div class="col-md-12">
    <div class="portlet-body">
        <div class="tab-pane active" id="tab_1_1">
            <div class="table-toolbar" style="margin: 5px 0">
                <div class="row">
                    <div class="col-md-7">
                        <div class="pdr10">
                            <#--<#if "${isRoot!''}" =="">-->
                            <button class="btn btn-default btn-add" onclick="editRow()"> 新增</button>
                            <#--</#if>-->
                        </div>
                    </div>
                </div>
            </div>

            <div id="label_datagrid" style="width: 100%; height: 500px" class="mini-datagrid" allowCellWrap="true"
                 sizeList="[20,30,50]" pageSize="50" showPager="false"
                 idField="id" multiSelect="flase">
                <div property="columns">
                    <div type="indexcolumn" width="50" headerAlign="center" align="center">序号</div>
                    <div field="fieldname" width="150" headerAlign="center" allowSort="false" align="center">名称</div>
                    <div field="datatype" width="80" headerAlign="center" allowSort="false" align="center">数据类型</div>
                    <div field="allowval" width="100" headerAlign="center" allowSort="false" align="center">允许值</div>
                    <div field="defaultval" width="100" headerAlign="center" allowSort="false" align="center">默认值</div>
                    <div field="description" width="100%" headerAlign="center" allowSort="false">说明</div>
                    <#--<#if "${isRoot!''}" =="">-->
                    <div width="115" headerAlign="center" allowSort="flase" align="center" renderer="labOpt">选项</div>
                    <#--</#if>-->
                </div>
            </div>

        </div>
    </div>
</div>
<script>
    label_mgr.id = ${id};
    label_mgr.gridData = ${labelConfig};
    function getFormData() {
        var tmp = [], json = label_mgr.grid.getData();
        for (var i = 0, l = json.length; i < l; i++) {
            tmp.push({
                fieldname: json[i].fieldname,
                datatype: json[i].datatype,
                allowval: json[i].allowval,
                defaultval: json[i].defaultval,
                description: json[i].description
            });
        }
        return tmp;
    }

    function editRow(row_uid) {
        editRowIndex = Mine.layer.openWin('标签编辑','/label/config?uid=' + (row_uid || ''), '400', '520');
    }

    function delRow(row_uid) {
        if (confirm("确认删除吗？")) {
            var row = label_mgr.grid.getRowByUID(row_uid);
            label_mgr.grid.removeRow(row);
            saveData();
        }
    }

    function labOpt(e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var edit = '<button class="btn btn-default btn-sm btn-edit" onclick="editRow(\'' + uid + '\')"> 修 改 </button>&nbsp;';
        var del =  '<button class="btn btn-danger btn-sm btn-delete" onclick="delRow(\'' + uid + '\')"> 删 除 </button>';
        return edit + del;
    }

    //取grid数据
    function setFormData(d) {
        label_mgr.grid.setData(d || label_mgr.gridData);
    }

    //保存数据
    function saveData() {
        $.ajax({
            url: "/label/editLabelConfig",
            data: {
                id: label_mgr.id,
                labelConfig: mini.encode(getFormData())
            },
            success: function (d) {
                if (d.status) {
                    var node = label_mgr.ztree.getNodeByParam("id", label_mgr.id, null);
                    label_mgr.ztree.reAsyncChildNodes(node, "refresh");
                    //操作成功！
                    Mine.layer.tips("更新成功！",1);
                    layer.close(editRowIndex);
                } else {
                    Mine.layer.tips(d.desc,2);
                }
            }
        });
    }

    mini.parse();
    //获取datagrid
    label_mgr.grid = mini.get("label_datagrid");
    //加载属性字段
    setFormData(label_mgr.gridData);

</script>