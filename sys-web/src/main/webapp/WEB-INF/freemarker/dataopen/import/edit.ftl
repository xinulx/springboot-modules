<div class="col-md-12">
    <form role="form" id="resource_form" ms-controller="resource_form">
        <div class="form-body">
            <input name="id" class="form-control" type="hidden" ms-duplex="id">
            <div class="form-group">
                <div class="cn-field">
                    <label>日期(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="periodDate" id="periodDate" placeholder=""
                       ms-duplex="periodDate" maxlength="40"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>数据项名称(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="itemName" id="itemName" placeholder=""
                          ms-duplex="itemName" maxlength="40"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>绝对量(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="unitValue" id="unitValue" placeholder=""
                          ms-duplex="unitValue"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>增速(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="growth" id="growth" placeholder=""
                       ms-duplex="growth"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>解读</label>
                </div>
                <textarea class="form-control" name="remark" id="remark" placeholder="在此添加描述信息..."
                          ms-duplex="remark"></textarea>
            </div>
            <div class="form-group" align="center">
                <button type="submit" class="btn btn-success">保存</button>
                <button type="button" class="btn btn-danger" onclick="layer.close(editIndex);">关闭</button>
            </div>
        </div>
    </form>
</div>
<script>
    mini.parse();
    var cur_form = {
        infoId: '${infoId}'
    };
    $(document).ready(function () {
        //拉取数据
        var getData = function () {
            $.get(
                "/dataPublish/dataImport/getItemEO",
                {
                    id: cur_form.infoId
                },
                function (d) {
                    data = d.data;
                    $("input[name=id]").val(data.id);
                    $("input[name=periodDate]").val(data.periodDate);
                    $("input[name=itemName]").val(data.itemName);
                    $("input[name=unitValue]").val(data.unitValue);
                    $("input[name=growth]").val(data.growth);
                    $("textarea[name=remark]").val(data.remark);
                }
            )
        };
        getData();

        $('#resource_form').validator({
            fields: {
                'periodDate': '日期:required;',
                'itemName': '数据项名称:required;',
                'unitValue':'绝对量:required;',
                'growth':'增速:required;'
            },
            focusCleanup: true,
            timely: 1,
            theme:'yellow_right_effect',
            msgClass:'n-bottom',
            valid: function () {
                doSave();
            }
        });

        var doSave = function () {
            var formData = decodeURIComponent($("#resource_form").serialize(), true);
            $.post(
                "/dataPublish/dataImport/saveItem",
                 formData,
                function (d) {
                    if (d.status == 1) {
                        Mine.layer.tips("保存成功！",1);
                        cur.grid.reload();
                        layer.close(editIndex);
                    } else {
                        Mine.layer.tips(d.desc,2);
                    }
                }
            )
        }

    })
</script>