<div class="col-md-12">
    <form id="export_form" role="form" ms-controller="user_form" autocomplete="off">
        <div class="form-body">
            <div class="form-group">
                <p><label>文件上传：</label><br/>
                    <input type="file" id="file1" name="file"/>
            </div>
            <div class="form-group">
                <label style="color:red" id="unit_tips">备注：
                    1) 自动过滤指标名称为空的行，空单元格的绝对量与增速统计为0
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    2) 工作表名称请按照[2018.3]格式填写，以便统计年月
                </label>
            </div>
            <div class="form-group" align="center">
                <button type="button" class="btn btn-success" onclick="saveData()">保存</button>
                <button type="button" class="btn btn-danger" onclick="layer.close(editIndex);">关闭</button>
            </div>
        </div>
    </form>
</div>
<script src="${ctx}/js/ajaxfileupload.min.js"></script>
<script>
    $(document).ready(function () {
        mini.parse();
    });

    function saveData() {
        if ($("#file1").val().length > 0) {
            var file = $("#file1").val();
            var suffix = file.substring(file.indexOf(".") + 1);
            if (suffix != "xls") {
                Mine.layer.tips("模板格式不正确!",2);
                return;
            }
            ajaxFileUpload();
        } else {
            alert("请选择excle文件");
        }
    }

    function ajaxFileUpload() {
        if (!confirm("确定导入?")) {
            return;
        }
        var loadIndex = layer.msg('正在导入...', {
            icon: 16
            ,shade: 0.01
        });
        jQuery.ajaxFileUpload({
            url: '/dataPublish/dataImport/process',
            type: 'post',
            data: {},
            secureuri: false,
            fileElementId: 'file1',
            dataType: 'json',
            success: function (data, status) {
                layer.close(loadIndex);
                console.log(data);
                if (data.status == 1) {
                    Mine.layer.tips("上传成功",1);
                    layer.close(editIndex);
                    cur.grid.reload();
                } else {
                    Mine.layer.tips(data.desc,2);
                    return false;
                }
            },
            error: function (data, status, e) {
                Mine.layer.tips(e+"",2);
            }
        });
        return false;
    }
</script>