var label_mgr = function () {
    var ztree_elem = "label_tree", isLoad = false;

    //加载框架页面
    function loadFormPage(action, pid) {
        $("#label_body").load('/label/' + action);
    }

    //初始化树
    var init = function (searchName) {

        mini.parse();

        var label_tree = $("#" + ztree_elem);
        //树配置
        var setting = {
            isEdit: true,
            view: {
                showTitle: true,
                addDiyDom: addDiyDom,
                dblClickExpand: false
            },
            async: {
                enable: true,
                url: "/label/tree",
                otherParam: {
                    "searchName": searchName || ''
                },
                autoParam: ["id=pid"],
                dataFilter: function (treeId, parentNode, responseData) {
                    var treeData = responseData.data.list;
                    if (!isLoad) {
                        treeData = {
                            "id": 0,
                            "pId": 0,
                            "name": "标签列表",
                            "config": null,
                            "description": null,
                            "isRoot": true,
                            "isParent":true,
                            "open": true
                        };
                        isLoad = true;
                    }
                    return treeData;
                }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    if (treeNode.isParent) {
                        label_mgr.ztree.cancelSelectedNode(treeNode);
                        label_mgr.ztree.expandNode(treeNode, !treeNode.open, true, true);
                    } else {
                        loadFormPage('list?action=show&id=' + treeNode.id);
                    }
                },
                onAsyncSuccess: function (event, treeId, treeNode) {
                    label_mgr.ztree = $.fn.zTree.getZTreeObj(ztree_elem);
                    var nodes = label_mgr.ztree.getNodes();
                    if(nodes && nodes[0].id == 0){
                        label_mgr.ztree.expandNode(nodes[0], true, false, true);
                    }
                }
            }
        };
        $.fn.zTree.init(label_tree, setting);
    };

    //添加树上面的操作按扭
    function addDiyDom(treeId, node) {
        var aObj = $("#" + node.tId + "_a");

        var addStr = "<span class='button tree-edit-add' id='addBtn_" + node.id + "' title='添加标签'></span>";
        var editStr = " <span class='button tree-edit-edit' id='editBtn_" + node.id + "' title='修改标签'></span>";
        var delStr = "<span class='button tree-edit-del' id='delBtn_" + node.id + "' title='删除标签'></span>";
        var copyStr = " <span class='button tree-edit-copy' id='copyBtn_" + node.id + "' title='复制标签'></span>";

        if (node.isParent && node.isRoot) {
            aObj.after(addStr);
            var btn = $("#addBtn_" + node.id);
            btn && btn.on("click", function (e) {
                editLabel(node.id, "");
                return false;
            });
        }
        if (!node.isParent && !node.isRoot) {
            aObj.after(editStr+delStr+copyStr);

            var editBtn_ = $("#editBtn_" + node.id);
            editBtn_ && editBtn_.on("click", function () {
                editLabel(node.pId, node.id);
                return false;
            });

            var copyBtn_ = $("#copyBtn_" + node.id);
            copyBtn_ && copyBtn_.on("click", function () {
                copyLabel(node.pId, node.id);
                return false;
            });

            var delBtn_ = $("#delBtn_" + node.id);
            delBtn_ && delBtn_.on("click", function () {
                if (confirm("删除以后无法恢复，您确信要删除此项目吗？")) {
                    delLabel(node.pId, node.id);
                }
                return false;
            });
        }
        if (node.isParent && !node.isRoot) {
            aObj.after(addStr+editStr);

            var addBtn_ = $("#addBtn_" + node.id);
            addBtn_ && addBtn_.on("click", function (e) {
                editLabel(node.id, "");
                return false;
            });
            var editBtn_ = $("#editBtn_" + node.id);
            editBtn_ && editBtn_.on("click", function () {
                editLabel(node.pId, node.id);
                return false;
            });
        }

    }

    function editLabel(pid, id) {
        editIndex = Mine.layer.openWin('标签属性编辑', '/label/edit?id=' + id + '&pid=' + pid, '500', '300');
    }

    function copyLabel(pid, id) {
        editIndex = Mine.layer.openWin('复制标签', '/label/edit?id=' + id + '&pid=' + pid + '&actionType=copy', '500', '300');
    }

    //删除标签
    var delLabel = function (pid, id) {
        $.ajax({
            url: "/label/delLabel",
            data: {
                id: id
            },
            success: function (d) {
                if (d.status) {
                    var node = label_mgr.ztree.getNodeByParam("id", pid, null);
                    label_mgr.ztree.reAsyncChildNodes(node, "refresh");
                } else {
                    Mine.layer.tips(d.desc, 2);
                }
            }
        });
    };

    return {
        init: init
    }

}();