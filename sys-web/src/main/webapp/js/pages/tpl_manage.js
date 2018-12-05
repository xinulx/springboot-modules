var editTplIndex;
var tpl_manage = function () {
    var isInit = false;
    var ztree_settings = {
        data: {
            key: {
                title: "id"
            },
            simpleData:{
                enable:true,
                pIdKey:"pid"
            }
        },
        view: {
            nameIsHTML: true,
            showTitle: true,
            selectedMulti: false,
            dblClickExpand: false,
            expandSpeed: "fast"
        }
    };

    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    function editTpl(type, node) {
        cur.node = node;
        cur.tpl_opt_type = type;
        var url = "/tpl/addOrEdit?id=" + node.id + "&type=" + type;;
        var title = '';
        if (type == 'add_class') {
            title = '添加模板分类';
        }else if (type == 'add') {
            title = '添加模板';
        } else {
            title = '编辑模板';
        }
        editTplIndex = Mine.layer.openWin(title,url, '500', '260');
    }

    function removeTpl(id, tid) {
        if (confirm('确定删除?')) {
            $.ajax({
                url: '/tpl/delete',
                data: {
                    id: id
                },
                success: function (resp) {
                    if (resp.status == 1) {
                        var node = cur.treeObj.getNodeByTId(tid);
                        cur.treeObj.removeNode(node, false);
                    } else {
                        Mine.layer.tips(resp.desc);
                    }
                }
            });
        }
    }

    var tpl = function () {
        //初始化布局
        mini.parse();
        cur.map = new Map();

        function addDiyDom(treeId, node) {
            cur.map.put(node.tId, node);
            var aObj = $("#" + node.tId + "_a");
            if (node.id == -1) {
                var addStr = "<span class='button tree-edit-add' data-toggle='tooltip' title='&nbsp;&nbsp;添加&nbsp;&nbsp;' id='diyBtn0_" + node.id + "'></span>";
                aObj.after(addStr);
                $("[data-toggle='tooltip']").tooltip({placement:'bottom'});
                var btn = $("#diyBtn0_" + node.id);
                btn && btn.on("click", function () {
                    editTpl("add_class", node);
                    return false;
                });
                return;
            }
            if (!node.children) {
                var addStr = "<span class='button tree-edit-add' data-toggle='tooltip' title='&nbsp;&nbsp;添加&nbsp;&nbsp;' id='diyBtn1_" + node.id + "'></span>";
                var editStr = "<span class='button tree-edit-edit' data-toggle='tooltip' title='&nbsp;&nbsp;修改&nbsp;&nbsp;' id='diyBtn3_" + node.id + "'></span>";
                var delStr = "<span class='button tree-edit-del' nodeTid=" + node.tId + " nodeId=" + node.id + " data-toggle='tooltip' title='&nbsp;&nbsp;删除&nbsp;&nbsp;' id='diyBtn2_" + node.id + "'></span>";
                aObj.after('<span>[' + node.id + ']</span>' + addStr + editStr + delStr);
                var btn = $("#diyBtn1_" + node.id);
                btn && btn.on("click", function () {
                    editTpl("add", node);
                    return false;
                });
                var editBtn_ = $("#diyBtn3_" + node.id);
                editBtn_ && editBtn_.on("click", function () {
                    editTpl("edit", node);
                    return false;
                });
                if (delStr != "") {
                    var delBtn_ = $("#diyBtn2_" + node.id);
                    delBtn_ && delBtn_.on("click", function () {
                        var id = $(this).attr("nodeId");
                        var tid = $(this).attr("nodeTid");
                        removeTpl(id, tid);
                        return false;
                    });
                }
            } else {
                var addStr = "<span class='button tree-edit-add' data-toggle='tooltip' title='&nbsp;&nbsp;添加&nbsp;&nbsp;' id='diyBtn1_" + node.id + "'></span>";
                var editStr = "<span class='button tree-edit-edit' data-toggle='tooltip' title='&nbsp;&nbsp;修改&nbsp;&nbsp;' id='diyBtn3_" + node.id + "'></span>";
                aObj.after('<span>[' + node.id + ']</span>' + addStr + editStr);
                var btn = $("#diyBtn1_" + node.id);
                btn && btn.on("click", function () {
                    editTpl("add", node);
                    return false;
                });

                var editBtn_ = $("#diyBtn3_" + node.id);
                editBtn_ && editBtn_.on("click", function () {
                    editTpl("edit", node);
                    return false;
                });
            }
            $("[data-toggle='tooltip']").tooltip({placement:'bottom'});
        }

        function onClick(event, treeId, node) {
            if(node.tempType == 'Virtual'){
                cur.treeObj.expandNode(node, !node.open, false, true);
                return;
            }
            if (node.isParent) {
                cur.treeObj.expandNode(node, !node.open, false, true);
            }
            cur.node = node;
            if (cur.node == null || !cur.node.template) {
                $('#tplContent').val('');
                $("#center_tab").css('display', 'none');
                return;
            } else {
                $("#center_tab").css('display', '');
                cur.historygrid.load({id: cur.node.id});
            }
            $.ajax({
                url: '/tpl/getTplContent',
                data: {
                    id: cur.node.id
                },
                success: function (resp) {
                    $('#tplContent').val(Mine.decode(resp.content)).data("version", resp.version);
                    $("#btn_preview").attr({"href": "/site/tpl/" + cur.node.id});
                }
            });
        }

        var settings = $.extend(true, ztree_settings, {
            view: {
                fontCss: getFont,
                addDiyDom: addDiyDom
            },
            callback: {
                onClick: onClick
            }
        });
        function getTplNodes(){
            var data = [];
            $.ajax({
                url:'/tpl/getEOList?type=Real&siteId=',
                type:'post',
                async:false,
                success:function(d){
                    data = d;
                }
            });
            if (Array.isArray(data)) {
                data.push({
                    "id": -1,
                    "name": "模板列表",
                    "template": false,
                    "tempType":'pclass',
                    "tempTypeName":'模板列表',
                    "isParent": true
                });
            }
            for(var i=0,len=data.length;i<len;i++){
                if(data[i].tempType == 'Real'){
                    data[i].icon="/images/tpl.png";
                }else{
                    data[i].isParent = true;
                }
            }
            return data;
        }
        cur.treeObj = $.fn.zTree.init($("#ui_tree"), settings,getTplNodes());
        var nodes = cur.treeObj.getNodes();
        //加载列表
        if (!isInit) {
            if (nodes.length > 0) {
                cur.treeObj.expandNode(nodes[0], true, false, true);
            }
            isInit = true;
        }
    };

    return {
        tpl: tpl
    }
}();

function Map() {

    var mapObj = {};

    this.put = function (key, value) {
        mapObj[key] = value;
    };

    this.remove = function (key) {
        if (mapObj.hasOwnProperty(key)) {
            delete mapObj[key];
        }
    };

    this.get = function (key) {
        if (mapObj.hasOwnProperty(key)) {
            return mapObj[key];
        }
        return null;
    };

    this.getKeys = function () {
        var keys = [];
        for (var k in mapObj) {
            keys.push(k);
        }
        return keys;
    };

    // 遍历map
    this.each = function (fn) {
        for (var key in mapObj) {
            fn(key, mapObj[key]);
        }
    };

    this.toString = function () {
        var str = "{";
        for (var k in mapObj) {
            str += "\"" + k + "\" : \"" + mapObj[k] + "\",";
        }
        str = str.substring(0, str.length - 1);
        str += "}";
        return str;
    }

}