<div class="col-md-12">
    <div class="portlet-body">
        <form id="labelForm" role="form" ms-controller="labelForm">
            <input name="id" class="hide">
            <input name="parentId" class="hide">
            <div class="form-body">
                <div class="form-group">
                    <label>标签名称</label>
                    <input type="text" name="labelName" id="labelName" class="form-control"
                           placeholder="" maxlength="30" value="${labelName!}">
                </div>
                <div class="form-group">
                    <label>标签类型</label>
                    <div class="form-control">
                    <#if labelType??>
                        <label class="radio-inline"><input type="radio" name="labelType" id="labelType0" value="0" <#if labelType==0>checked</#if>>标签目录</label>
                        <label class="radio-inline"><input type="radio" name="labelType" id="labelType1" value="1" <#if labelType==1>checked</#if>>内部继承</label>
                        <label class="radio-inline"><input type="radio" name="labelType" id="labelType2" value="2" <#if labelType==2>checked</#if>>自定义</label>
                    <#else>
                        <label class="radio-inline"><input type="radio" name="labelType" id="labelType0" value="0" checked>标签目录</label>
                        <label class="radio-inline"><input type="radio" name="labelType" id="labelType1" value="1">内部继承</label>
                        <label class="radio-inline"><input type="radio" name="labelType" id="labelType2" value="2">自定义</label>
                    </#if>
                    </div>
                </div>
                <div class="form-group">
                    <label>描述</label>
                    <textarea class="form-control" name="labelNotes" rows="3"
                              placeholder="">${labelNotes!}</textarea>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-success">保 存</button>
                    <button type="button" class="btn btn-danger" onclick="layer.close(editIndex);">关 闭</button>
                </div>
            </div>
        </form>

    </div>
</div>
<script>

    var edit_cur = {
        id: ${id},
        pid: ${pid},
        formId: "labelForm",
        actionType: '${actionType!''}'
    };

    $(document).ready(function () {
        $('#' + edit_cur.formId).validator({
            fields: {
                'labelName': '标签名称:required;'
            },
            timely:2,
            theme:'yellow_right_effect',
            msgClass:'n-bottom',
            valid: function () {
                saveData();
            }
        });
    });

    //保存数据
    function saveData() {
        $("input[name=id]").val(edit_cur.id);
        $("input[name=parentId]").val(edit_cur.pid);
        var url;
        if (edit_cur.actionType == "copy") {
            url = "/label/copyLabel";
        } else {
            url = "/label/" + (edit_cur.id == 0 ? "saveLabel" : "editLabel");
        }
        if (edit_cur.id == 0) {
            $("input[name=id]").val('');
        }
        var formData = decodeURIComponent($('#' + edit_cur.formId).serialize(), true);
        $.ajax({
            url: url,
            type:'post',
            data: formData,
            success: function (d) {
                if (d.status) {
                    if (edit_cur.id == 0 || edit_cur.actionType == "copy") {
                        var node = label_mgr.ztree.getNodeByParam("id", edit_cur.pid, null);
                        label_mgr.ztree.reAsyncChildNodes(node, "refresh");
                    } else {
                        var node = label_mgr.ztree.getNodeByParam("id", edit_cur.id, null);
                        node.name = $("input[name=labelName]").val();
                        node.description = $("input[name=labelNotes]").val();
                        label_mgr.ztree.updateNode(node);
                    }
                    layer.close(editIndex);
                } else {
                    Mine.layer.tips(d.desc,2);
                }
            },
            error:function(e){
                Mine.layer.tips(e.responseJSON.message,2);
            }
        });
    }

</script>