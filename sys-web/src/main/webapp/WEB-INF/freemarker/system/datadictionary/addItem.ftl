<div class="col-md-12">
    <form role="form" id="formDictItem" ms-controller="formDictItem">
        <input type="hidden" name="dataDicId" class="form-control" ms-duplex="dataDicId" value="${DataDict.dictId}"></input>
        <div class="form-body">
            <div class="form-group">
                <div class="ch-field">
                    <label>对应字典名称</label>
                </div>
                <input type="text" class="form-control readonly"
                       disabled id="dataDicName" name="dataDicName" placeholder="" ms-duplex="dataDicName" readonly
                       value="${DataDict.name}">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>字典项名称</label>
                </div>
                <input type="text" class="form-control" id="name" name="name" placeholder="" ms-duplex="name">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>字典项编码</label>
                </div>
                <input type="text" class="form-control" id="code" name="code" placeholder="唯一编码" ms-duplex="code">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>value值</label>
                </div>
                <input type="text" class="form-control" id="value" name="value" placeholder="" ms-duplex="value">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>排序</label>
                </div>
                <input type="number" class="form-control" name="sortNum" id="sortNum" placeholder="" ms-duplex="sortNum" value="${maxSort}">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>设为默认值</label>
                </div>
                <div class="radio-group">
                    <label class="radio-inline"><input type="radio" name="isDefault" value="1" ms-duplex-number="isDefault">
                        是 </label>
                    <label class="radio-inline"><input type="radio" name="isDefault" value="0" ms-duplex-number="isDefault" checked>
                        否 </label>
                </div>
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>描述</label>
                </div>
                <textarea class="form-control" name="description" rows="3" id="description" placeholder="" ms-duplex="description"></textarea>
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
        var dictId =${DataDict.dictId};
        var dictName = "${DataDict.name}";
        var maxSort =${maxSort};
        var data = {
            dataDicId: dictId,
            dataDicName: dictName,
            name: "",
            code: "",
            value: "",
            sortNum: maxSort,
            isDefault: 0,
            description: ""
        };
        console.log("字典数据="+JSON.stringify(data));
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
                saveDictItem();
            }
        });

    });

    var saveDictItem = function () {
        var data = Mine.getFormData(cur.formId);
        $.ajax({
            url: "/dictItem/saveItem",
            data: data,
            success:function (d) {
                if (d.status == 0) {
                    Mine.layer.tips("操作失败：" + d.desc,2);
                } else {
                    Mine.layer.tips("保存成功");
                    grid2.reload();
                    layer.close(editItemIndex);
                }
            }
        });
    };
</script>