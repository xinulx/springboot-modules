<div class="mini-splitter" style="width:100%" id="mainSplitter">
    <div size="10%" showCollapseButton="true">
        <div class="tabbable-line">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_1_1" data-toggle="tab">内容协同</a>
                </li>
                <li>
                    <a href="#tab_1_2" data-toggle="tab">政务公开</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab_1_1">
                    <ul id="columnTree" class="ztree"></ul>
                </div>
                <div class="tab-pane" id="tab_1_2">
                </div>
            </div>
        </div>
    </div>
    <div showCollapseButton="true">
        <div id="contentMgr" style="overflow-y: auto;overflow-x: hidden;margin: 15px;">
            <div style="height: 30px;line-height: 0px;border-bottom: 1px solid #ccc;font-size: 20px;color:green;font-weight: 900">
                <span class="glyphicon glyphicon-retweet" aria-hidden="true"
                      style="position: relative;top: 3px;"></span>
                <span class="caption-subject font-green-sharp bold uppercase">生成静态</span>
            </div>
            <div class="alert alert-warning" style="margin-top: 10px">
                <strong>温馨提示：</strong> 生成静态的速度取决于当前栏目下新闻的多少，请耐心等待！<br/><br/>
                <div>
                    <button type="button" class="btn btn-primary" ms-if="siteBtn & columnBtn"
                            ms-click="funPublish($event,1)">
                        <span class="glyphicon glyphicon-retweet" aria-hidden="true"
                              style="position: relative;top: 2px;"></span> 生成 [全站] 首页
                    </button>
                    <button type="button" class="btn btn-success" ms-if="siteBtn & columnBtn"
                            ms-click="funPublish($event,1)">
                        <span class="glyphicon glyphicon-retweet" aria-hidden="true"
                              style="position: relative;top: 2px;"></span> 生成 [全站] 栏目页
                    </button>
                    <button type="button" class="btn btn-danger" ms-if="siteBtn & columnBtn"
                            ms-click="funPublish($event,1)">
                        <span class="glyphicon glyphicon-retweet" aria-hidden="true"
                              style="position: relative;top: 2px;"></span> 生成 [全站] 文章页
                    </button>
                </div>
            </div>
            <div class="row alert-default" style="margin: 0">
                <div id="progressbar1"></div>
                <div id="progressbar2"></div>
                <div id="progressbar3"></div>
                <script type="text/javascript">
                    $(function () {
                        $('#progressbar1').LineProgressbar({
                            percentage: 50,
                            radius: '0',
                            height: '20px',
                            width:'68%',
                            fillBackgroundColor: '#739ace'
                        });
                        $('#progressbar2').LineProgressbar({
                            percentage: 20,
                            radius: '0',
                            height: '20px',
                            width:'68%',
                            fillBackgroundColor: '#739ace'
                        });
                        $('#progressbar3').LineProgressbar({
                            percentage: 100,
                            radius: '0',
                            height: '20px',
                            width:'68%',
                            fillBackgroundColor: '#739ace'
                        });
                    })
                </script>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $.resetHeight("mainSplitter", 85);
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
                Mine.showPagination("contentMgr", "/content/" + treeNode.type);
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
                Mine.initTreeData(zNodes, result, 'name');
                Mine.treeDataFilter(zNodes, Mine.treeDataType.COLUMN);
                zNodes[0].open = true;
            }
        });
        return zNodes;
    }

    $(document).ready(function () {
        mini.parse();
        $.autoHeight("contentMgr", 100);
        zTree = $.fn.zTree.init($("#columnTree"), setting, getNodes());
    });
</script>

