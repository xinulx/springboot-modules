<div class="col-md-12">
    <ul id="authMenuTree" class="ztree" style="overflow: auto;height:350px"></ul>
</div>
<div class="col-md-12 text-center" style="margin-top: 15px">
    <button type="button" class="btn btn-default" onclick="saveMenuRel()">保 存</button>
    <button type="button" class="btn btn-danger" onclick="layer.close(roleIndex);">关 闭</button>
</div>
<script type="text/javascript">
    var organId = ${organId!''};
    var id = ${id!''};
    var zTree;
    var checkNodes = eval(${existMenuId});
    var setting = {
        view: {

        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true,
                pIdKey:"pid"
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                if (treeNode.nocheck) {
                    zTree.cancelSelectedNode(treeNode);
                    zTree.expandNode(treeNode, !treeNode.open, false, true, true);
                } else {
                    zTree.checkNode(treeNode, !treeNode.checked, true);
                }
            }
        }
    };

    function getNodes() {
        var zNodes = [];
        $.ajax({
            type: 'post',
            url: '/menu/getMenuTree',
            dataType: 'json',
            async: false,
            success: function (result) {
                zNodes = result;
            }
        });
        return zNodes;
    }

    function isChecked(node) {
        var flag = false;
        for(var i = 0 ; i < checkNodes.length ; i ++){
            if(node.id == checkNodes[i] && node.type == 'URL'){
                flag = true;
            }
        }
        return flag;
    }

    $(function () {
        var nodes = getNodes();
        zTree = $.fn.zTree.init($("#authMenuTree"), setting, nodes);
        if (nodes.length == 0) {
            $("#authMenuTree").empty().append('<li><a href="javascript:void()">暂无菜单</a></li>');
        }
        for(var i = 0 ; i < nodes.length ; i ++){
            if(isChecked(nodes[i])){
                var curNode = zTree.getNodeByParam("id",nodes[i].id,null);
                zTree.checkNode(curNode, !curNode.checked, true);
            }
        }
    });
    function saveMenuRel(){
        if(!organId || organId == ''){
            Mine.layer.tips("单位为空，请重新登录",2);
            return;
        }
        if(!id || id == ''){
            Mine.layer.tips("角色为空，请重新选择",0);
            return;
        }
        var nodes = zTree.getCheckedNodes(true);
        if(nodes.length == 0){
            Mine.layer.tips("未选择菜单",3);
            return;
        }
        var ids = [];
        for(var i = 0 ; i < nodes.length ; i ++){
            ids.push(nodes[i]['id']);
        }
        $.post(
            "/role/saveMenuRel",
            {
                organId:organId,
                roleId:id,
                ids:ids
            },
            function(data){
                if(data.status == 1){
                    Mine.layer.tips(data.desc,1);
                    layer.close(roleIndex);
                }else{
                    Mine.layer.tips(data.desc,2);
                }
            }
        );
    }
</script>