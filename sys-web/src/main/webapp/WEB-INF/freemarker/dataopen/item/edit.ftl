<div class="col-md-12">
    <form role="form" id="resource_form" ms-controller="resource_form">
        <div class="form-body">
            <input name="id" class="form-control" type="hidden" ms-duplex="id">
            <input name="parentId" class="form-control" type="hidden" ms-duplex="parentId">
            <div class="form-group">
                <div class="cn-field">
                    <label>数据项名称(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="itemName" id="itemName" placeholder=""
                          ms-duplex="itemName" maxlength="40"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                <label>数据项导入名称(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="itemImportName" id="itemImportName" placeholder=""
                          ms-duplex="itemImportName"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>数据项单位</label>
                </div>
                <input class="form-control" name="itemUnit" id="itemUnit" placeholder=""
                       ms-duplex="itemUnit"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>数据项解读</label>
                </div>
                <textarea class="form-control" name="itemDesc" id="itemDesc" placeholder="在此添加描述信息..."
                          ms-duplex="itemDesc"></textarea>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">保存</button>
                <button type="button" class="btn btn-danger" onclick="layer.close(editIndex);">关闭</button>
            </div>
        </div>
    </form>
</div>
<script>
    mini.parse();
    var cur_form = {
        infoId: '${infoId}',
        parentId:'${parentId}'
    };
    $(document).ready(function () {
        //拉取数据
        var getData = function () {
            $.get(
                "/dataPublish/dataItem/getItemEO",
                {
                    id: cur_form.infoId
                },
                function (d) {
                    data = d.data;
                    $("input[name=id]").val(data.id);
                    $("input[name=parentId]").val(cur_form.parentId);
                    $("input[name=itemName]").val(data.itemName);
                    $("input[name=itemImportName]").val(data.itemImportName);
                    $("input[name=itemUnit]").val(data.itemUnit);
                    $("textarea[name=itemDesc]").val(data.itemDesc);
                }
            );
        };
        getData();

        $('#resource_form').validator({
            fields: {
                'itemName': '数据项名称:required;',
                'itemImportName':'数据项导入名称:required;'
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
                "/dataPublish/dataItem/saveItem",
                    formData,
                function (d) {
                if (d.status == 1) {
                    Mine.layer.tips("保存成功！",1);
                    cur.grid.reload();
                    layer.close(editIndex);
                } else {
                    Mine.layer.tips(d.desc,2);
                }
            })
        }

    })
</script>
</body>
</html>