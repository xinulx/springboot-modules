<div class="mini-splitter" style="width:100%" id="mainSplitter">
    <div size="10%" showCollapseButton="true" class="tree-bg">
        <ul id="columnTree" class="ztree"></ul>
    </div>
    <div showCollapseButton="true">
        <div id="contentMgr" style="overflow-y: auto;overflow-x: hidden"></div>
        <div class="mask-loading">
            <div class="show-mask">页面正在加载中...</div>
        </div>
    </div>
</div>
<script type="text/javascript">
    resetHeight("mainSplitter", 85);
    var zTree, curNode;
    var setting = {
        view: {
            nameIsHTML: true
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
                curNode = treeNode;
                showPagination("contentMgr","/content/" + treeNode.type);
            }
        }
    };

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
        mini.parse();
        autoHeight("contentMgr", 100);
        zTree = $.fn.zTree.init($("#columnTree"), setting, getNodes());
    });
</script>

