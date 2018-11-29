<div class="col-md-12">
    <div class="portlet-body">
        <form id="labelConfigForm" role="form" ms-controller="labelConfigForm">
            <div class="form-body">
                <div class="form-group">
                    <label>属性名称</label>
                    <input type="text" name="fieldname" class="form-control" ms-duplex="fieldname" placeholder="" maxlength="50">
                </div>

                <div class="form-group">
                    <label>数据类型</label>
                    <select class="form-control" name="datatype" id="datatype" ms-duplex="datatype" placeholder="">
                        <option value="string">string</option>
                        <option value="integer">integer</option>
                        <option value="boolean">boolean</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>允许值</label>
                    <input type="text" name="allowval" class="form-control" ms-duplex="allowval" placeholder="" maxlength="50">
                </div>

                <div class="form-group">
                    <label>默认值</label>
                    <input type="text" name="defaultval" class="form-control" ms-duplex="defaultval" placeholder="" maxlength="50">
                </div>

                <div class="form-group">
                    <label>描述</label>
                    <textarea class="form-control" name="description" ms-duplex="description" rows="3" placeholder=""></textarea>
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

    var config_cur = {
        uid: ${uid},
        rowData: {},
        formId: "labelConfigForm"
    };
    mini.parse();

    $(document).ready(function () {
        if(config_cur.uid && config_cur.uid != ''){
            config_cur.rowData = label_mgr.grid.getRowByUID(config_cur.uid);
            $("#"+config_cur.formId+" input").each(function(){
                this.value = config_cur.rowData[this.name];
            });
        }else{
            $("input[name=allowval]").val('string');
        }
        $("#"+config_cur.formId+" textarea").text(config_cur.rowData['description']);
        $("#"+config_cur.formId+" select>option[value="+config_cur.rowData['datatype']+"]").attr("selected",true);
        var datatype = $("#datatype");
        datatype.change(function(){
            $("input[name=allowval]").val(this.value);
        });
        $('#' + config_cur.formId).validator({
            fields: {
                'fieldname': '属性名称:required;',
                'datatype': '数据类型:required;',
                'allowval': '允许值:required;'
            },
            focusCleanup: true,
            timely: 2,
            theme:'yellow_right_effect',
            msgClass:'n-bottom',
            valid: function () {
                Refresh_Grid();
            }
        });
    });
    function GetData() {
        $("#"+config_cur.formId+" input").each(function(){
            config_cur.rowData[this.name]=this.value;
        });
        config_cur.rowData['datatype'] = $("select[name=datatype]").val();
        config_cur.rowData['defaultval'] = $("input[name=defaultval]").val();
        config_cur.rowData['description'] = $('textarea[name=description]').val();
        return config_cur.rowData;
    }
    function Refresh_Grid() {
        var formData = GetData();
        if (config_cur.uid == 0) {
            label_mgr.gridData.push(formData);
            setFormData();
        } else {
            label_mgr.grid.updateRow(formData);
        }
        saveData();
    }

</script>