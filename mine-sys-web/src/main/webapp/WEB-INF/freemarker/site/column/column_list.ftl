<div class="col-md-2 tree-bg">
    <ul id="columnTree" class="ztree"></ul>
</div>
<div class="col-md-10 col-md-offset-2 hidden right-layout" id="rightInfo">
    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#tab_1_1">栏目信息</a></li>
    </ul>
    <br/>
    <div class="tab-content">
        <div class="tab-pane active" id="tab_1_1">
            <form id="columnForm" method="post" autocomplete="off" class="local-form">
                <input type="text" style="display: none" name="parentId" value="0"/>
                <input type="text" style="display: none" name="id"/>
                <div class="form-group">
                    <div class="ch-field">
                        <label>栏目编号：</label>
                    </div>
                    <input type="text" class="form-control" id="code" name="code" placeholder="栏目编号">
                </div>
                <div class="form-group">
                    <div class="ch-field">
                        <label>栏目名称：</label>
                    </div>
                    <input type="text" class="form-control" id="name" name="name" placeholder="栏目名称">
                </div>
                <div class="form-group">
                    <div class="ch-field">
                        <label>排序号：</label>
                    </div>
                    <input type="text" class="form-control" id="num" name="num" placeholder="排序号">
                </div>
                <div class="form-group">
                    <div class="ch-field">
                        <label>栏目类型：</label>
                    </div>
                    <select class="form-control" id="type" name="type"></select>
                </div>
                <div class="form-group">
                    <div class="ch-field">
                        <label>跳转地址：</label>
                    </div>
                    <input class="form-control" id="link" name="link" placeholder="跳转地址"/>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default">提交</button>
                    <button type="reset" class="btn btn-danger">重置</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="col-md-10 col-md-offset-2 hidden right-layout" id="buttonInfo"></div>
<script type="text/javascript">
    autoHeight("columnTree", 85);
    var zTree, currNode, optType = "add", selectNode;
    var setting = {
        view: {
            nameIsHTML: true,
            addDiyDom: function (treeId, treeNode) {
                var aObj = $("#" + treeNode.tId + "_a");
                var addCatalogStr = "<span class='button tree-edit-add-catalog' data-toggle='tooltip' title='添加同级栏目' id='diyBtn0_" + treeNode.id + "' onclick='add(" + treeNode.id + ")'></span>";
                var addStr = "<span class='button tree-edit-add' data-toggle='tooltip' title='添加下级栏目' id='diyBtn1_" + treeNode.id + "' onclick='edit(" + treeNode.id + ",\"add\")'></span>";
                var editStr = "<span class='button tree-edit-edit' data-toggle='tooltip' title='修改栏目' id='diyBtn2_" + treeNode.id + "' onclick='edit(" + treeNode.id + ",\"edit\")'></span>";
                var delStr = "<span class='button tree-edit-del' data-toggle='tooltip' title='删除栏目' id='diyBtn3_" + treeNode.id + "' onclick='delColumn(" + treeNode.id + ")'></span>";
                if (treeNode.isParent) {
                    aObj.after(addCatalogStr + addStr + editStr);
                } else {
                    aObj.after(addCatalogStr + addStr + editStr + delStr);
                }
                $("[data-toggle='tooltip']").tooltip({placement: 'bottom'});
            }
        },
        data: {
            simpleData: {
                enable: true,
                pIdKey: "parentId"
            },
            key: {
                title: "my_title"
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                selectNode = treeNode;
                edit(selectNode.id,'edit');
            }
        }
    };

    function add(id) {
        $("form")[0].reset();
        currNode = zTree.getNodeByParam("id", id);
        $("input[name=parentId]").val(currNode['parentId']);
        optType = 'add';
        showEditForm();
    }

    function edit(id, type) {
        optType = type;
        currNode = zTree.getNodeByParam("id", id);
        zTree.selectNode(currNode);
        if (type == 'add') {
            $("form")[0].reset();
            $("input[name=parentId]").val(currNode['id']);
        } else {
            $("input[name=parentId]").val(currNode['parentId'] == null ? 0 : currNode['parentId']);
            $("input[name=id]").val(currNode['id']);
            $("input[name=code]").val(currNode['code']);
            $("input[name=name]").val(currNode['name']);
            $("input[name=num]").val(currNode['num']);
            $("input[name=link]").val(currNode['link']);
            $("select[name=type] option").attr("selected", false);
            $("select[name=type] option[value=" + currNode['type'] + "]").attr("selected", true);
        }
        showEditForm();
    }

    function delColumn(id) {
        currNode = zTree.getNodeByParam("id", id);
        if (currNode.children != null && currNode.children.length > 0) {
            layer.msg("该栏目含有子栏目，不允许删除！", {icon: 2, shade: 0.01, time: 1000});
            return;
        }
        layer.confirm('删除后将不能恢复，是否确定删除？', {
            icon: 3,
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.post(
                "/column/del",
                {ids: [id]},
                function (result) {
                    if (result.status == 1) {
                        layer.msg(
                            result.desc,
                            {icon: 1, shade: 0.01, time: 1000},
                            function (index) {
                                layer.close(index);
                                zTree.removeNode(currNode);
                                var nodes = zTree.getNodes();
                                if (nodes == undefined || nodes.length == 0) {
                                    $("#columnTree").empty().append('<li><a href="javascript:showEditForm()">暂无栏目，点击添加栏目</a></li>');
                                }
                            }
                        );
                    } else {
                        layer.msg(result.desc, {icon: 2, shade: 0.01, time: 1000});
                    }
                }
            );
        });
    }

    function getNodes() {
        var zNodes = [];
        $.ajax({
            type: 'post',
            url: '/column/getColumnTree',
            dataType: 'json',
            async: false,
            success: function (result) {
                initTreeData(zNodes, result, 'name');
                Mine.treeDataFilter(zNodes, Mine.treeDataType.COLUMN);
                zNodes[0].open = true;
            }
        });
        return zNodes;
    }

    $(document).ready(function () {
        var nodes = getNodes();
        zTree = $.fn.zTree.init($("#columnTree"), setting, nodes);
        if (nodes.length == 0) {
            $("#columnTree").empty().append('<li><a href="javascript:showEditForm()">暂无栏目，点击添加栏目</a></li>');
        }
        formValid();
        getConstant("type", "CMS_COLUMN_INFO", "TYPE", "栏目类型");
    });

    function showEditForm() {
        $("#rightInfo").show();
    }

    function formValid() {
        $("#rightInfo form").validator({
            fields: {
                code: "栏目编码:required; length(1~20)",
                name: "栏目名称:required; length(1~)",
                num: "排序号:required;",
                type: "栏目类型:required;"
            },
            focusCleanup: true,
            timely: 1,
            theme: 'yellow_right_effect',
            msgClass: "n-top",
            valid: function (form) {
                // form.submit();
                var formData = decodeURIComponent($("#columnForm").serialize(), true);
                $.post(
                    "/column/saveOrUpdate",
                    formData,
                    function (result) {
                        if (result.status == 1) {
                            layer.msg(
                                result.desc,
                                {icon: 1, shade: 0.01, time: 1000},
                                function (index) {
                                    layer.close(index);
                                    zTree = $.fn.zTree.init($("#columnTree"), setting, getNodes());
                                    var node = zTree.getNodeByParam("id", result.data);
                                    if (optType == 'add') {
                                        // zTree.addNodes(currNode, node);
                                        $("input[name=id]").val(result.data);
                                        optType = 'edit';
                                    }
                                    currNode = node;
                                    zTree.selectNode(node);
                                }
                            );
                        } else {
                            layer.msg(result.desc, {icon: 2, shade: 0.01, time: 1000});
                        }
                    }
                );
            }
        });
    }
</script>
