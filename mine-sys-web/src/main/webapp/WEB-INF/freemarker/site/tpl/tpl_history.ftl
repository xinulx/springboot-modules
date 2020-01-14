<link href="${ctx}/plugins/codemirror/lib/codemirror.css" rel="stylesheet" type="text/css"/>
<body class="page-body-white">
<div id="ui_layout" class="mini-layout" style="width: 100%;height: 100%;">
    <div region="center" style="border:none; overflow: auto;padding: 10px">
        <textarea id="content" style="width: 100%;height: 100%;min-height: 400px" readonly class="form-control readonly"></textarea>
        <div class="pull-right" style="margin-top: 5px">
            <button type="button" class="btn btn-default" onclick="recovery()">恢复</button>
            <button type="button" class="btn btn-danger" onclick="layer.close(detailIndex)">关 闭</button>
        </div>
    </div>
</div>
<script src="${ctx}/plugins/codemirror/lib/codemirror.js" type="text/javascript"></script>
<script src="${ctx}/plugins/codemirror/mode/xml/xml.js" type="text/javascript"></script>
<script src="${ctx}/plugins/codemirror/mode/javascript/javascript.js" type="text/javascript"></script>
<script src="${ctx}/plugins/codemirror/mode/css/css.js" type="text/javascript"></script>
<script src="${ctx}/plugins/codemirror/mode/htmlmixed/htmlmixed.js" type="text/javascript"></script>
<script src="${ctx}/plugins/codemirror/addon/edit/matchbrackets.js" type="text/javascript"></script>
<script>
    var hisCur = {
        tempId:${tempId}
    };
    var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
        lineNumbers: true,//是否显示每一行的行数
        readOnly:false,//只读
        styleActiveLine: true,
        lineWrapping: true,	//代码折叠
        foldGutter: true,
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
        matchBrackets: true
    });
    editor.setSize('500px', '400px');
    $(document).ready(function () {
        // $('#content').val(Mine.decode(cur.rec.tempContent));
        editor.setValue(Mine.decode(cur.rec.tempContent));
    });

    function recovery() {
        if(confirm('确定恢复，恢复版本将覆盖当前使用版本?')) {
            $.ajax({
                url: '/tpl/tplRecovery',
                data: {
                    tempId: hisCur.tempId,
                    content: cur.rec.tempContent
                },
                success: function (resp) {
                    if (resp.status == '1') {
                        callback_tplDetail();
                    } else {
                        Mine.layer.tips(resp.desc, 2);
                    }
                    layer.close(detailIndex);
                }
            });
        }
    }
</script>
</body>
</html>