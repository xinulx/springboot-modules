<div class="col-md-2 tree-bg">
    <ul id="organTree" class="ztree"></ul>
</div>
<div class="col-md-10 col-md-offset-2 hidden right-layout" id="rightInfo"></div>

<script type="text/javascript">
    $.autoHeight("organTree",85);
    var zTree, selectNode;
    var setting = {
        data: {
            key: {
                name: "organName",
                title:'my_title'
            },
            simpleData: {
                enable: true,
                pIdKey: "parentId"
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                selectNode = treeNode;
                if(treeNode.open){
                    zTree.expandNode(treeNode, false, false, true);
                }else{
                    zTree.expandNode(treeNode, true, false, true);
                }
                $("#rightInfo").load("/user/list?organId=" + selectNode.id).show();
            }
        }
    };

    function getNodes() {
        var zNodes = [];
        $.ajax({
            type: 'post',
            url: '/organ/getOrganTree',
            dataType: 'json',
            async: false,
            success: function (result) {
                Mine.initTreeData(zNodes,result,'organName');
                for(var i = 0 ; i < zNodes.length ; i ++){
                    zNodes[i].icon = '/images/organ/'+ zNodes[i].organType +'.png';
                }
            }
        });
        return zNodes;
    }

    $(document).ready(function () {
        var nodes = getNodes();
        zTree = $.fn.zTree.init($("#organTree"), setting, nodes);
        if (nodes.length == 0) {
            $("#organTree").empty().append('<li><a href="javascript:void(0)">暂无组织机构</a></li>');
        }
    });


</script>
