<div class="col-md-12">
    <div class="portlet-body form">
        <form id="tpl_form" role="form">
            <div class="form-body">
                <br/><br/>
                <div class="pull-right">
                    <button type="button" class="btn btn-default" onclick="uploadTpl()">开始上传</button>
                </div>
                <div class="form-group">
                    <input type="file" name="file" id="file" accept=".*"/>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="${ctx}/js/ajaxfileupload.min.js"></script>
<script>
    var up_cur = {
        id:${id}
    };
    function uploadTpl() {
        if(!file){
            Mine.layer.tips("请选择文件",0);
            return;
        }
        jQuery.ajaxFileUpload({
            url: '${ctx}/tpl/tplUpload?id=' + up_cur.id,//服务器端程序
            secureuri: false,
            fileElementId: 'file',//input框的ID
            dataType: 'json',//返回数据类型
            beforeSend: function () {//上传前需要处理的工作，如显示Loading...
            },
            success: function (data, status) {
                if(data.status == 1) {
                    callback_upload(data.desc);

                } else {
                    callback_upload(data.desc);
                }
                layer.close(tplUploadIndex);
            },
            error: function (data, status, e) {
                callback_upload("系统错误!");
                layer.close(tplUploadIndex);
            }
        });
    }
</script>
