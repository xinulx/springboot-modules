<div class="col-md-12">
    <div class="portlet-body form">
        <form id="tpl_form" role="form">
            <div class="form-body">
                <div class="form-group">
                </div>
                <div class="form-group">
                    <div class="cn-field">
                        <label>模板类型</label>
                    </div>
                    <input type="text" id="tempTypeName" name="tempTypeName" readonly class="form-control readonly" value="">
                    <input type="text" id="tempType" name="tempType" value="" style="display: none">
                    <input type="text" id="pid" name="pid" style="display: none">
                </div>
                <div class="form-group">
                    <div class="cn-field">
                        <label>模板名称</label>
                    </div>
                    <input id="opt_type" type="hidden" value="${type}">
                    <input type="text" id="id" name="id" style="display: none" ms-duplex="id">
                    <input type="text" id="name" name="name" class="form-control" placeholder="填写模板名称">
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-default">保 存</button>
                    <button type="reset" class="btn btn-danger">重 置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    var editCur = {
        id: 'tpl_form',
        type: '${type!}',
        treeId: '${id!}'
    };

    $(document).ready(function () {
        editCur.type = $("#opt_type").val();
        var nodeN, node = cur.node,name = "";
        if (editCur.type == 'add_class' || editCur.type == 'add') {
            if(node.id == -1){
                name = "模板目录";
                node.tempType = 'Virtual';
            }else{
                name = node.name;
                node.tempType = 'Real';
            }
        }else{
            if(node.pid == -1){
                name = "模板目录";
                node.tempType = 'Virtual';
            }else{
                var pNode = cur.treeObj.getNodeByParam("id",node.pid,null);
                name = pNode.name;
                node.tempType = 'Real';
            }
        }
        if (editCur.type == 'add_class' || editCur.type == 'add') {
            nodeN = {id: '', tempType: node.tempType, tempTypeName: name, pid: node.id, name: ''};
        } else {
            nodeN = {id: node.id, tempType: node.tempType, tempTypeName: name, pid: node.pid, name: node.name};
        }
        setData(nodeN);
    });

    //拉取数据
    function setData(data) {
        $("#tpl_form input").each(function(){
            this.value = data[this.name];
        });
    }

    $("#tpl_form").validator({
        fields: {
            tempTypeName: "类型:required; length(1~12)",
            name: "名称:required; length(2~50)"
        },
        focusCleanup: true,
        timely: 1,
        theme:'yellow_right_effect',
        msgClass:'n-top',
        valid: function (form) {
            // form.submit();
            var formData = decodeURIComponent($("#tpl_form").serialize(), true);
            addOrEdit(formData);
        }
    });

    function addOrEdit(formData) {
        if (!$('#tpl_form').isValid()) {
            return;
        }
        var url = "";
        if (editCur.type == 'add_class') {
            url = "/tpl/save";
        } else if (editCur.type == 'add') {
            url = "/tpl/save";
        }else {
            url = "/tpl/update";
        }
        $.ajax({
            url: url,
            data: formData,
            success: function (resp) {
                if (resp.status == '1') {
                    if (resp.data != null) {
                        if (Array.isArray(resp.data)) {
                            cur.node_edit = resp.data[0];
                        } else {
                            cur.node_edit = resp.data;
                        }
                    }
                    layer.close(editTplIndex);
                    callback_editTpl();
                } else {
                    Mine.layer.tips(resp.desc, 2);
                }
            }
        });
    }
</script>
</body>
</html>