<div class="col-md-12">
    <form role="form" id="formDictItem" ms-controller="formDictItem">
        <input type="hidden" name="itemId" class="form-control" value="${ItemEO.itemId}">
        <input type="hidden" name="dataDicId" class="form-control" value="${ItemEO.dataDicId}">

        <div class="form-body">
            <div class="form-group">
                <div class="ch-field">
                    <label>对应字典名称</label>
                </div>
                <input type="text" class="form-control readonly" name="dataDicName" readonly value="${DataDict.name}">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>字典项名称</label>
                </div>
                <input type="text" class="form-control" name="name" placeholder="" value="${ItemEO.name}">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>字典项编码</label>
                </div>
                <input type="text" class="form-control" name="code" value="${ItemEO.code}" readonly>
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>value值</label>
                </div>
                <input type="text" class="form-control" name="value" value="${ItemEO.value}">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>排序</label>
                </div>
                <input type="number" class="form-control" name="sortNum" value="${ItemEO.sortNum}">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>设为默认值</label>
                </div>
                <div class="radio-group">
                    <label class="radio-inline">
                        <input <#if '${ItemEO.isDefault}' = '1'>checked</#if> type="radio" name="isDefault" value="1">是
                    </label>
                    <label class="radio-inline">
                        <input <#if '${ItemEO.isDefault}' = '0'>checked</#if> type="radio" name="isDefault" value="0">否
                    </label>
                </div>
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>描述</label>
                </div>
                <textarea class="form-control" name="description" rows="3">${ItemEO.description}</textarea>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">保 存</button>
                <button type="reset" class="btn btn-danger">重 置</button>
            </div>
        </div>
    </form>
</div>
<script>
    var cur = {
        formId:"formDictItem"
    };
    jQuery(document).ready(function () {
        var itemId =${ItemEO.itemId};
        var dataDicId =${ItemEO.dataDicId};
        var dictName = "${DataDict.name}";
        var name = "${ItemEO.name}";
        var code = "${ItemEO.code}";
        var value = '${ItemEO.value}';
        var sortNum = "${ItemEO.sortNum}";
        var isDefault = "${ItemEO.isDefault}";
        var description = "${ItemEO.description}";
        var data = {
            itemId: itemId,
            dataDicId: dataDicId,
            dataDicName: dictName,
            name: name,
            code: code,
            value: value,
            sortNum: sortNum,
            isDefault: isDefault,
            description: description
        };
        console.log("数据字典项="+JSON.stringify(data));
        //验证
        $('#formDictItem').validator({
            fields: {
                'name': '字典名称:required',
                'code': '字典编码:required',
                'value': 'value值:required',
                'sortNum': '排序:required;integer'
            },
            focusCleanup: true,
            timely: 1,
            theme:'yellow_right_effect',
            msgClass:'n-bottom',
            valid: function () {
                saveItem();
            }
        });

    });

    var saveItem = function () {
        var data = Mine.getFormData(cur.formId);
        $.ajax({
            url: "/dictItem/updateItem",
            type:'post',
            data: data,
            success:function (d) {
                if (d.status == 0) {
                    Mine.layer.tips("保存失败：" + d.desc,2);
                } else {
                    Mine.layer.tips("保存成功！");
                    grid2.reload();
                    layer.close(editItemIndex);
                }
            }
        });
    };
</script>