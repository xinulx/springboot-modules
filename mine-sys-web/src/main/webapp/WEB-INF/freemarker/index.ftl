<#include "common/header.ftl">
<body>
<div class="container-fluid">
    <div class="row top-header">
        <div class="col-xs-12">
            <div class="pull-left">
                <span class="logo-title">Java Platform</span>
                <div id="mainHeader" class="row mainHeader"></div>
            </div>
            <div class="pull-right">
                <div class="f_left">
                    <span class="glyphicon glyphicon-map-marker"></span>&nbsp;${(localUser.OrganName)!'单位'}
                </div>
                <div class="f_left">
                    <span class="glyphicon glyphicon-edit"></span>&nbsp;${(localUser.PersonName)!'个人信息'}
                </div>
                <div class="f_left">
                    <span class="glyphicon glyphicon-info-sign"></span>&nbsp;信息
                </div>
                <div class="f_left">
                    <span class="glyphicon glyphicon-off"></span>&nbsp;退出登录
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2" id="mainMenu"></div>
        <div class="col-md-10" id="mainContainer"></div>
    </div>
</div>
<div id="layerWin" class="hidden"></div>
<div id="layerWin2" class="hidden"></div>
<iframe id="download_frame" name="download_frame" class="hide" src="about:blank"></iframe>
<#include "common/footer.ftl">
<script id="headerMenu" type="text/html">
    <ul>
        <% for( var i = 0 ; i < data.length ; i ++ ) {%>
        <li onclick="loadLeftMenu(this)" data-id="<%=data[i].id%>">
            <i class="iconfont <%=data[i].iconClass%>"></i>&nbsp;<%=data[i].name%>
        </li>
        <%}%>
    </ul>
</script>
<script id="leftMenu" type="text/html">
    <% for( var i = 0 ; i < data.length ; i ++ ) {%>
    <% if(data[i].type == 'INDICATOR'){ %>
    <li class="header"><%=data[i].name%></li>
    <%}%>
    <%if(data[i].children.length > 0){%>
    <% for( var n = 0 ; n < data[i].children.length ; n ++ ) {%>
    <li class="treeview" type="<%=data[i].children[n].type%>" reqUrl="<%=data[i].children[n].reqUrl%>">
        <a href="#">
            <i class="iconfont <%=data[i].children[n].iconClass%>"></i>
            <span><%=data[i].children[n].name%></span>
            <i class="iconfont icon-msnui-rightmini pull-right"></i>
        </a>
        <%if(data[i].children[n].children.length > 0){%>
        <ul class="treeview-menu">
            <% for( var j = 0 ; j < data[i].children[n].children.length ; j ++ ) {%>
            <li type="<%=data[i].children[n].children[j].type%>" reqUrl="<%=data[i].children[n].children[j].reqUrl%>">
                <a href="#">
                    <i class="iconfont <%=data[i].children[n].children[j].iconClass%>"></i>
                    <span><%=data[i].children[n].children[j].name%></span>
                    <i class="iconfont icon-msnui-rightmini pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <%include('leftMenu',{data:data[i].children[n].children[j].children})%>
                </ul>
            </li>
            <%}%>
        </ul>
        <%}%>
    </li>
    <%}%>
    <%}else{%>
    <li type="<%=data[i].type%>" reqUrl="<%=data[i].reqUrl%>">
        <a href="#">
            <i class="iconfont <%=data[i].iconClass%>"></i>
            <span><%=data[i].name%></span>
            <i class="iconfont icon-msnui-rightmini pull-right"></i>
        </a>
    </li>
    <%}%>
    <%}%>
</script>
<script>
    $(document).ready(function () {
        Mine.initSocket();
        loadSysMenu();
        $(".f_left:last").click(function () {
            $.get("/login/logout", function (d) {
                if (d.status == 1) {
                    window.location.reload(true);
                } else {
                    window.location = "/login/logout";
                }
            });
        });
    });

    function loadSysMenu() {
        $.post("/menu/getMenuTree", {type: "CATAGORY"}, function (result) {
            if (result.status && result.status == -9) {
                layer.confirm('登陆超时，是否重新登陆？', {icon: 3, btn: ['确定', '取消']}, function () {
                    location.href = '/login/loginIn?etc=' + new Date().getTime();
                });
                return;
            }
            $("#mainHeader").empty().html(template('headerMenu', {data: result}));
            $("#mainHeader ul li").click(function () {
                $(this).addClass('active-header').siblings().removeClass('active-header');
            });
            $("#mainHeader ul li:first").trigger("click");
        });
    }

    function loadLeftMenu(obj) {
        var id = $(obj).attr("data-id");
        $.post("/menu/getMenuTree", {id: id, queryFlag: '1'}, function (result) {
            if (result.status && result.status == -9) {
                layer.confirm('登陆超时，是否重新登陆？', {icon: 3, btn: ['确定', '取消']}, function () {
                    location.href = '/login/loginIn?etc=' + new Date().getTime();
                });
                return;
            }
            $("#mainMenu").empty().html("<ul class=\"sidebar-menu\">" + template('leftMenu', {data: result}) + "</ul>");
            $.sidebarMenu($('.sidebar-menu'));
            $('.sidebar-menu li').click(function () {
                $(this).addClass('active').siblings().removeClass('active');
                var type = $(this).attr("type");
                var url = $(this).attr("reqUrl");
                if (type == 'URL') {
                    if (url == undefined || url == '') {
                        layer.msg("缺少地址参数，无法访问该功能！", {icon: 2, shade: 0.01, time: 1000});
                        return;
                    } else {
                        $("#mainContainer").empty().load(url, function (response, status, xhr) {
                            if (status == 'error') {
                                var errorCode = xhr.status;
                                if (errorCode == '403') {
                                    $(this).load("/common/403");
                                } else if (errorCode == '404') {
                                    $(this).load("/common/404");
                                } else if (errorCode == '500') {
                                    $(this).load("/common/500");
                                } else if (errorCode == '404') {
                                    $(this).load("/common/503");
                                }
                            }
                        });
                    }
                }
            });
            $("#mainMenu ul li").each(function () {
                if (this.type == 'URL') {
                    $(this).trigger('click').addClass('active');
                    return false;
                }
            });
        });
    }

    $.autoHeight('mainContainer', 70);
</script>
</body>
</html>