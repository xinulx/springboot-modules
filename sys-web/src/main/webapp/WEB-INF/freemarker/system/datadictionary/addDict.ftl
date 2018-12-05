<div class="col-md-12">
    <div class="portlet-body form">
        <form role="form" id="formDict" ms-controller="formDict">
            <div class="form-body">
                <div class="form-group">
                    <div class="ch-field">
                        <label>字典名称</label>
                    </div>
                    <input type="text" class="form-control" id="name" name="name" placeholder="" ms-duplex="name">
                </div>

                <div class="form-group">
                    <div class="ch-field">
                        <label>字典编码</label>
                    </div>
                    <input type="text" class="form-control" id="code" name="code" placeholder="" ms-duplex="code">
                </div>

                <div class="form-group">
                    <div class="ch-field">
                        <label>是否对外开放 （超管及站点管理员可编辑字典项）</label>
                    </div>
                    <div class="radio-group">
                        <label class="radio-inline"><input type="radio" name="isOpen" value="1" checked ms-duplex-number="isOpen">
                            是 </label>
                        <label class="radio-inline"><input type="radio" name="isOpen" value="0" checked ms-duplex-number="isOpen">
                            否 </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="ch-field">
                        <label>是否应用</label>
                    </div>
                    <div class="radio-group">
                        <label class="radio-inline"><input type="radio" name="isUsed" value="1" checked ms-duplex-number="isUsed">
                            是 </label>
                        <label class="radio-inline"><input type="radio" name="isUsed" value="0" ms-duplex-number="isUsed">
                            否 </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="ch-field">
                        <label>描述</label>
                    </div>
                    <textarea class="form-control" id="description" name="description" rows="3" placeholder="" ms-duplex="description"></textarea>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default">保 存</button>
                    <button type="reset" class="btn btn-danger">重 置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    var addDict_cur = {
        formId:"formDict"
    };

    jQuery(document).ready(function () {
        //验证
        $('#formDict').validator({
            fields: {
                'name': '字典名称:required',
                'code': '字典编码:required'
            },
            focusCleanup: true,
            timely: 1,
            theme:'yellow_right_effect',
            msgClass:'n-bottom',
            valid: function () {
                saveDict();
            }
        });

        var data = {
            name: "",
            code: "",
            isUsed: 0,
            isOpen: 0,
            description: ""
        };
    });

    var saveDict = function () {
        var data = Mine.getFormData(addDict_cur.formId);
        $.ajax({
            url: "/dataDict/saveDict",
            type:'post',
            data: data,
            success:function (d) {
                if (d.status == 0) {
                    Mine.layer.tips("操作失败：" + d.desc,2);
                } else {
                    Mine.layer.tips("保存成功");
                    grid.reload();
                    layer.close(editIndex);
                }
            }
        });
    };
</script>
</html>