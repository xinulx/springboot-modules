<div class="portlet light">
    <div class="portlet-body">
        <div class="table-toolbar" style="margin-bottom: 5px">
            <div class="row">
                <div class="col-md-7">
                    <div class="btn-group" <#if isRoot ==true >style="display:none"</#if>>
                        <button id="adduser_btn" class="btn btn-default" onclick="add()"> 新 增</button>
                    </div>
                </div>
                <div class="col-md-5">
                    <form class="form-inline tr search-toolbar pull-right" role="form">
                        <div class="form-group">
                            <input type="text" id="searchKey" name="searchKey" class="form-control" placeholder="字典名称">
                        </div>
                        <button type="button" class="btn btn-primary" onclick="search()">查询</button>
                        <button type="button" class="btn btn-success" onclick="searchClear()">显示全部</button>
                    </form>
                </div>
            </div>
        </div>
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:600px;" onPageChanged="onPageChanged"
             allowResize="true" allowCellSelect="false" onlyCheckSelection="true" url="/dataDict/getPage" sizeList="[20,30,50]" pageSize="20"
             idField="id" multiSelect="flase">
            <div property="columns">
                <div type="indexcolumn" width="50" headerAlign="center" align="center">序号</div>
                <div field="name" width="180" headerAlign="center" allowSort="flase" renderer="itemLink" align="center">
                    字典名称
                </div>
                <div field="code" width="160" headerAlign="center" allowSort="flase" align="center">编码</div>
                <div field="createDate" width="160" headerAlign="center" dateFormat="yyyy-MM-dd" allowSort="flase"
                     align="center">创建时间
                </div>
                <div field="description" width="100%" headerAlign="center" allowSort="flase">描述</div>
                <#--<#if '${isRoot!}'!='true'>-->
                <div field="" width="80" headerAlign="center" allowSort="flase" align="center" renderer="isOpen">是否开放</div>
                <div field="" width="80" headerAlign="center" allowSort="flase" align="center" renderer="isUsed">是否应用</div>
                <div width="115" headerAlign="center" allowSort="true" renderer="renderOp" align="center">操作</div>
                <#--</#if>-->
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load({dataFlag: 1});

    function add() {
        editIndex = Mine.layer.openWin('新增字典',"/dataDict/addDict", '550', '410');
    }

    function itemLink(e) {
        var link = "";
        var rec = e.record;
        link = '<a href="javascript:;" onclick="dictItemList(' + rec.dictId + ',\'' + rec.name + '\');"><u>' + rec.name + '</u></a>';
        return link;
    }

    function isUsed(e) {
        var rec = e.record;
        if (rec.isUsed == 1) {
            str = "<input type='checkBox' <#if isRoot != true>disabled </#if> value='1' id='" + rec.itemId + "' onclick='resetUse(" + rec.dictId + ")' checked/>";
        } else {
            str = "<input type='checkBox' <#if isRoot != true>disabled </#if> value='1' id='" + rec.itemId + "' onclick='resetUse(" + rec.dictId + ")' />";
        }
        return str;
    }

    function isOpen(e) {
        var rec = e.record;
        if (rec.isOpen == 1) {
            str = "<span class='font-red-sunglo'><strong>是</strong></span>";
        } else {
            str = "否";
        }
        return str;
    }

    function resetUse(id) {
        $.ajax({
            data: {
                id: id
            },
            url: "/dataDict/resetUse",
            success: function (text) {
                grid.reload();
            }
        });
    }

    //操作加载
    function renderOp(e) {
        var str = "";
        var cstr = "";
        var rec = e.record;
        if (rec.isUsed == 1) {
            cstr = 'disabled="true"'
        }
        tgBtn = '<button class="btn btn-default btn-sm" <#if isRoot=true>disabled </#if>  onclick="edit(' + rec.dictId + ')">修 改</button> ';
        qsBtn = '<button class="btn btn-danger btn-sm" <#if isRoot=true>disabled </#if> ' + cstr + ' onclick="deleteDict(' + rec.dictId + ')">删 除</button>';
        str += tgBtn + qsBtn;
        return str;
    }

    dictItemList = function (p, name) {
        itemListIndex = Mine.layer.openWin(name + ' 字典项列表',"/dictItem/dictItemList?dictId=" + p, '1200', '520');
    };

    function edit(id) {
        editIndex = Mine.layer.openWin('修改字典',"/dataDict/editDict?dictId=" + id, '550', '410');
    }
    function deleteDict(id) {

        if (confirm("确定删除选中项及子项？")) {
            $.ajax({
                data: {
                    id: id
                },
                url: "/dataDict/deleteDict",
                success: function (text) {
                    grid.reload();
                }
            });
        }
    }

    function search() {
        var name = $("#searchKey").val();
        grid.load({name: name, dataFlag: 1});
    }

    //清除条件
    searchClear = function () {
        $("#searchKey").val("");
        grid.load({dataFlag: 1});
    };
    function onKeyEnter(e) {
        search();
    }

</script>