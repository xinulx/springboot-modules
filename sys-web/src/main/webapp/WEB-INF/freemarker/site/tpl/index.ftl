<div class="col-md-2 tree-bg">
    <div id="ui_tree" class="ztree"></div>
</div>
<div class="col-md-10 col-md-offset-2 right-layout">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="#tab_1_1" data-toggle="tab">模板信息</a>
        </li>
        <li>
            <a href="#tab_1_2" data-toggle="tab">历史版本</a>
        </li>
    </ul>
    <div class="tab-content" style="padding-top: 8px;">
        <div class="tab-pane active" id="tab_1_1" style="width:100%">
            <div class="table-toolbar" style="height: 20px;margin-bottom: 20px">
                <div class="row">
                    <div class="col-md-7">
                        <div class="pdr10">
                            <button type="button" id="btn_save" class="btn btn-primary" onclick="saveTpl()">保 存</button>
                            <button type="button" id="btn_add_label" class="btn btn-default" onclick="addLabelBtn()">插入标签</button>
                            <button type="button" id="btn_upload" class="btn btn-default" onclick="upload()">上 传</button>
                            <button type="button" id="btn_download" class="btn btn-info" onclick="download()">下 载</button>
                            <a href="javascript:void(0)" id="btn_preview" target="_blank" class="btn btn-success">预 览</a>
                        </div>
                    </div>
                </div>
            </div>
            <div id="label_layout" class="mini-layout" style="width:100%;border: 0">
                <div title="标签" visible="false" showCloseButton="true" showHeader="true" region="west"
                     width="200" showSplit="false" showCollapseButton="false" style="height: 460px">
                    <div id="label_tree" class="ztree"></div>
                </div>
                <div region="center" style="width: 100%;border: 0">
                    <textarea id="tplContent" name="tplContent" style="width:100%;border: 0;outline: none;color:#3d80b3"></textarea>
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab_1_2">
            <div id="datagrid" class="mini-datagrid" allowCellSelect="false" onlyCheckSelection="true" allowResize="true"
                 url="/tpl/getHistoryEOByTplId" sizeList="[5,10,20,50]" pageSize="10"
                 idField="id" multiSelect="false" showColumnsMenu="true">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="tempName" width="100%" align="left" headerAlign="left" renderer="tempName">
                        模板名称
                    </div>
                    <div width="80" align="center" headerAlign="center" renderer="showDetail">模板内容
                    </div>
                    <div field="createDate" width="140" align="center" headerAlign="center" allowSort="true"
                         dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">修改时间
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<iframe style="display: none;" id="downLoad"></iframe>
<script src="${ctx}/js/pages/tpl_manage.js"></script>
<script src="${ctx}/js/pages/label_select.js"></script>
<script>
    resetHeight("ui_tree",85);
    resetHeight("label_layout",176);
    resetHeight("tplContent",185);
    resetHeight("datagrid",135);
    mini.parse();
    var cur = {
        editBtn: $('#btn_edit'),
        saveBtn: $('#btn_save'),
        backBtn: $('#btn_back'),
        addLabelBtn: $('#btn_add_label'),
        uploadBtn: $('#btn_upload'),
        downloadBtn: $('#btn_download'),
        tplContent: $('#tplContent'),
        label_layout: mini.get('label_layout'),
        historygrid: mini.get('datagrid')
    };

    var rec = null;
    $(document).ready(function () {
        $("#center_tab").css('display', 'none');
        tpl_manage.tpl();
        label_select.label();
        cur.historygrid.load({id: 0});
    });

    function upload() {
        var url = "/tpl/uploadTpl?id=" + cur.node.id;
        tplUploadIndex = Mine.layer.openWin('上传模板',url, '400', '150');
    }

    function callback_upload(msg) {
        fileScan();
        Mine.layer.tips(msg,0);
    }

    function download() {
        var name = prompt('模板名称', cur.node.name + '.html')
        if (name != null && name != "") {
            downloadFile(name);
        }
    }

    function downloadFile(fileName) {
        var node = cur.node;
        if (!node) {
            Mine.layer.tips("选择要下载的模板!",0);
            return;
        }

        $("#downLoad").attr("src", "${ctx}/tpl/downLoad?id=" + cur.node.id + "&fileName=" + fileName);
    }

    function saveTpl() {
        if (cur.node == null || !cur.node.template) {
            $('#tplContent').val('');
            return;
        }
        $.ajax({
            url: '/tpl/saveTplContent',
            type:'post',
            data: {
                id: cur.node.id,
                content:  Mine.encode($('#tplContent').val()),// base64编码
                version: $('#tplContent').data("version"),
                base64: 'content'
            },
            success: function (resp) {
                cur.historygrid.load({id: cur.node.id});
                if(resp.status == 0){
                    Mine.layer.tips(resp.desc, 2);
                }else{
                    Mine.layer.tips(resp.desc, 1);
                    $('#tplContent').data("version",resp.data);
                    sendSocket("更新模板["+cur.node.name+"]内容"+resp.desc);
                }
            }
        });
    }

    function addLabelBtn() {
        cur.label_layout.updateRegion("west", {visible: true});
    }

    function closeLabelBtn() {
        cur.label_layout.updateRegion("west", {visible: false});
    }

    function tempName() {
        return cur.node.name;
    }

    function showDetail(e) {
        var record = e.record;
        return '<a href="javascript:;"  onclick="tplDetail(' + record.id + ')"><u>详情</u></a>';
    }

    /*
    * 查看历史版本详情
    * */
    function tplDetail(id) {
        var rec = cur.historygrid.findRow(function (row) {
            if (row.id == id) return true;
        });
        cur.rec= rec;
        var url = "/tpl/history?tempId=" + cur.node.id;
        detailIndex = Mine.layer.openWin('历史版本',url, '600', '500');
    }

    function callback_tplDetail() {
        fileScan();
        Mine.layer.tips('模板恢复成功!',1);
    }

    function fileScan() {
        $.ajax({
            url: '/tpl/getTplContent',
            data: {
                id: cur.node.id
            },
            success: function (resp) {
                $('#tplContent').val(Mine.decode(resp.content));
                cur.historygrid.load({id: cur.node.id});
            }
        });
    }

    function callback_editTpl() {
        if (cur.tpl_opt_type == 'edit') {
            if (cur.node_edit != null) {
                cur.node.name = cur.node_edit.name;
                cur.treeObj.updateNode(cur.node, true);
                cur.historygrid.load({id: cur.node.id});
            }
        } else {
            if (cur.node_edit != null) {
                cur.node_edit.tempTypeName = cur.node.tempTypeName;
                var nodes = [cur.node_edit];
                cur.treeObj.addNodes(cur.node, nodes);
                Mine.layer.tips("添加成功!", 1);
            }
        }
    }

    function insertAtCursor(myField, myValue) {
        //IE
        if (document.selection) {
            myField.focus();
            sel = document.selection.createRange();
            sel.text = myValue;
            sel.select();
        }
        //MOZILLA/NETSCAPE
        else if (myField.selectionStart || myField.selectionStart == '0') {
            var startPos = myField.selectionStart;
            var endPos = myField.selectionEnd;
            var restoreTop = myField.scrollTop;
            myField.value = myField.value.substring(0, startPos) + myValue + myField.value.substring(endPos, myField.value.length);
            if (restoreTop > 0) {
                myField.scrollTop = restoreTop;
            }
            /*myField.focus(); //解决chrome浏览器光标位置改变问题*/
            myField.selectionStart = startPos + myValue.length;
            myField.selectionEnd = startPos + myValue.length;
        } else {
            myField.value += myValue;
            /*myField.focus(); //解决chrome浏览器光标位置改变问题*/
        }
    }
</script>
