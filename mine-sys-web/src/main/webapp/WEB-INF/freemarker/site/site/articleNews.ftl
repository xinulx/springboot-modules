<link rel="stylesheet" href="/css/jquery-ui.css">
<script src="/js/jquery-ui.js"></script>
<div id="toolBtn" class="table-toolbar" style="padding: 5px">
    <div class="row">
        <div class="col-md-7">
            <form class="form-inline tr search-toolbar pull-left" role="form">
                <button type="button" class="btn btn-default" onclick="addOrEdit()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;添加
                </button>
                <button type="button" class="btn btn-danger" onclick="batchDel()">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>&nbsp;删除
                </button>
            </form>
        </div>
        <div class="col-md-5">
            <form class="form-inline tr search-toolbar pull-right" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" id="title" name="title" placeholder="请输入关键字" />
                </div>
                <button type="button" class="btn btn-primary" onclick="search()">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;查询
                </button>
                <button type="button" class="btn btn-warning" onclick="searchAll()">
                    <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>&nbsp;显示全部
                </button>
            </form>
        </div>
    </div>
</div>
<div id="contentList" style="padding: 5px">
    <table class="table table-hover" id="contentTable">
        <thead class="bg-info mini-grid-border">
            <tr>
                <th width="3%" align="center">序号</th>
                <th width="4%" align="center">排序</th>
                <th width="3%" align="center">
                    <div class="checkbox-div">
                        <i class="content-checkbox" name="checkAll">&nbsp;&nbsp;&nbsp;&nbsp;</i>
                    </div>
                </th>
                <th width="70%">标题</th>
                <th width="15%">属性</th>
                <th width="5%">操作</th>
            </tr>
        </thead>
        <tbody id="articleNewsBody"></tbody>
    </table>
    <ul class="page pull-right" maxshowpageitem="10" pagelistcount="15" id="page"></ul>
</div>
<script type="text/html" id="articleNewsBodyTemplate">
<%
    for( var i = 0 ; i < data.length ; i ++ ) {
    var article = data[i],rowNum = startNumber + i + 1;
%>
    <tr class="content-record">
        <td scope="row"><%=rowNum%></td>
        <td class="arrow-move">
            <span class="sort-order" onclick="moveUp(this)">↑</span>
            <span class="sort-order" onclick="moveDown(this)">↓</span>
        </td>
        <td>
            <div class="checkbox-div">
                <i class="content-checkbox" name="check">&nbsp;&nbsp;&nbsp;&nbsp;</i>
            </div>
        </td>
        <td><a href="/content/article/<%=article.id%>" target="_blank"><%=article.title%></a>
            <div class="content-info">
                <span class="w230">发布日期：<%=article.publishDate%></span>
                <span class="w180">作者：<%=article.author%></span>
                <span class="w100">点击量：<%=article.hit%></span>
                <span class="w100">app点击量：<%=article.appHit%></span>
                <span class="w100">发布人：<%=article.publishUserId%></span>
                <span>录入单位：滁州市人民政府（政府办公室）</span>
            </div>
        </td>
        <td>发布</td>
        <td>
            修改
        </td>
    </tr>
    <%}%>
</script>
<script>
    var orderIDS,doc_list_body,docIDS,title;
    function getOrderID(ui, attr) {
        return ui.item.parent().children().map(function () {
            var num = $(this).data(attr);
            return num;
        }).get().join(',');
    }
    function updateNum(docIDS,orderIDS){

    }
    $(function(){
        getPageData();
    });

    function search(){
        title = $.trim($('#title').val());
        getPageData();
    }

    function searchAll(){
        title = '';
        getPageData();
    }

    function getPageData(pageIndex){
        $.post("/content/getPage",{pageIndex:pageIndex,title:title},function(d){
            $("#articleNewsBody").html(template('articleNewsBodyTemplate', d));
            $("#contentList table tbody").sortable({
                items: '>tr',
                axis: "y",
                cursor: "move",
                handle: ".arrow-move",
                helper: function (e, ui) {
                    ui.children().each(function () {
                        var _this = $(this);
                        _this.width(_this.width());
                    });
                    return ui;
                },
                connectWith: ">tbody",
                forcePlaceholderSize: true,
                placeholder: 'must-have-class',
                start: function (e, ui) {
                    ui.placeholder.html('<td colspan="5"></td>');
                    !orderIDS && (orderIDS = getOrderID(ui, "no"));
                },
                update: function (e, ui) {
                    docIDS = getOrderID(ui, "id");
                    updateNum(docIDS, orderIDS);
                }
            }).disableSelection();
            $("#page").initPage(d.total,d.pageIndex + 1,function(curPage){
                getPageData(curPage - 1);
            });
            initCheckClick();
        });
    }

    function initCheckClick(){
        $(".content-checkbox").click(function(){
            var _this = $(this);
            var name = _this.attr("name");
            if(name == 'checkAll'){
                if(_this.hasClass('content-checkbox')){
                    $(".content-checkbox").removeClass('content-checkbox').addClass('content-checkbox-select');
                }else{
                    $(".content-checkbox-select").removeClass('content-checkbox-select').addClass('content-checkbox');
                }
            }else{
                if(_this.hasClass('content-checkbox')){
                    _this.removeClass('content-checkbox').addClass('content-checkbox-select');
                }else{
                    _this .removeClass('content-checkbox-select').addClass('content-checkbox');
                }
            }
        });
    }
</script>