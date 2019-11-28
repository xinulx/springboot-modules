<div class="col-md-12">
    <form role="form" id="resource_form" ms-controller="resource_form">
        <div class="form-body">
            <input name="id" class="form-control" type="hidden" ms-duplex="id">
            <div class="form-group">
                <div class="cn-field">
                    <label>任务名称(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="name" id="name" placeholder=""
                       ms-duplex="name" maxlength="40"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>抓取地址(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="grabUrl" id="grabUrl" placeholder=""
                       ms-duplex="grabUrl" maxlength="400"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>抓取条数(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="num" id="num" placeholder=""
                       ms-duplex="num" maxlength="40"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>行选择器(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="list" id="list" placeholder=""
                          ms-duplex="list" maxlength="40"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>标题选择器(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="title" id="title" placeholder=""
                          ms-duplex="title"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>链接选择器(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="url" id="url" placeholder=""
                       ms-duplex="url"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>日期选择器(<font color="red">*</font>)</label>
                </div>
                <input class="form-control" name="date" id="date" placeholder=""
                       ms-duplex="date"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>域名</label>
                </div>
                <input class="form-control" name="domain" id="domain" placeholder=""
                       ms-duplex="domain"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>替换匹配正则</label>
                </div>
                <input class="form-control" name="replaceRule" id="replaceRule" placeholder=""
                       ms-duplex="replaceRule"/>
            </div>
            <div class="form-group">
                <div class="cn-field">
                    <label>替换值</label>
                </div>
                <input class="form-control" name="replaceValue" id="replaceValue" placeholder=""
                       ms-duplex="replaceValue"/>
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
        infoId: '${infoId}'
    };
    $(document).ready(function () {
        //拉取数据
        var getData = function () {
            $.get(
                "/infoSync/grab/getEO",
                {
                    id: cur_form.infoId
                },
                function (d) {
                    data = d.data;
                    Object.keys(data).forEach(function(key){
                        $('input[name='+key+']').val(data[key]);
                    });
                }
            )
        };
        getData();

        $('#resource_form').validator({
            fields: {
                'name': '任务名称:required;',
                'grabUrl': '抓取地址:required;',
                'list':'行选择器:required;',
                'title':'标题选择器:required;',
                'url':'链接选择器:required;',
                'date':'日期选择器:required;',
                // 'replaceRule':'匹配规则:required;',
                // 'replaceValue':'替换值:required;'
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
                "/infoSync/grab/save",
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