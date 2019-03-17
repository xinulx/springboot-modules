<div class="col-md-12">
    <form role="form" id="formDict" ms-controller="formDict">
        <div class="form-body">
            <div style="display:none;">
                <input type="hidden" id="dictId" name="dictId" ms-duplex="dictId" class="form-control" value="${DictEO.dictId}"></div>
            <div style="display:none;">
                <input type="hidden" id="isUsed" name="isUsed" ms-duplex="isUsed" class="form-control" value="${DictEO.isUsed}"></div>

            <div class="form-group">
                <div class="ch-field">
                    <label>字典编码</label>
                </div>
                <input type="text" class="form-control readonly" disabled  name="code" id="code" placeholder="" ms-duplex="code" value="${DictEO.code}" readonly>
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>字典名称</label>
                </div>
                <input type="text" class="form-control" name="name" id="name" placeholder="" ms-duplex="name" value="${DictEO.name}">
            </div>

            <div class="form-group">
                <div class="ch-field">
                    <label>是否对外开放 （超管及站点管理员可编辑字典项）</label>
                </div>
                <div class="radio-group">
                    <label class="radio-inline"><input type="radio" <#if "${DictEO.isOpen}"=="1">checked</#if> name="isOpen" value="1" ms-duplex-number="isOpen">
                        是 </label>
                    <label class="radio-inline"><input type="radio" <#if "${DictEO.isOpen}"!="1">checked</#if> name="isOpen" value="0" ms-duplex-number="isOpen">
                        否 </label>
                </div>
            </div>
            <div class="form-group">
                <div class="ch-field">
                    <label>是否应用</label>
                </div>
                <div class="radio-group">
                    <label class="radio-inline"><input type="radio" <#if "${DictEO.isUsed}"=="1">checked</#if> name="isUsed" value="1" ms-duplex-number="isUsed">
                        是 </label>
                    <label class="radio-inline"><input type="radio" <#if "${DictEO.isUsed}"!="1">checked</#if> name="isUsed" value="0" ms-duplex-number="isUsed">
                        否 </label>
                </div>
            </div>
            <div class="form-group">
                <div class="ch-field">
                    <label>描述</label>
                </div>
                <textarea class="form-control" name="description" id="description" rows="3" placeholder="" ms-duplex="description">${DictEO.description!''}</textarea>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">保 存</button>
                <button type="reset" class="btn btn-danger">重 置</button>
            </div>
        </div>
    </form>
</div>
<script>
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

    });

    var saveDict = function () {
        var dictId =${DictEO.dictId};
        var code = $("#code").val();
        var name = $("#name").val();
        var description = $("#description").val()
        var isUsed = $("input[name='isUsed']:checked").val();
        var isOpen = $("input[name='isOpen']:checked").val();
        var data = {dictId: dictId, isUsed: isUsed, isOpen: isOpen, code: code, name: name, description: description};
        $.ajax({
            url: "/dataDict/updateDict",
            type:'post',
            data: data,
            success:function (d) {
                if (d.status == 0) {
                    Mine.layer.tips("保存失败：" + d.desc,2);
                } else {
                    Mine.layer.tips("保存成功");
                    grid.reload();
                    layer.close(editIndex);
                }
            }
        });
    };
</script>