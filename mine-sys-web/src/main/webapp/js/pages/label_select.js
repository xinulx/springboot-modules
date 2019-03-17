var label_select = function () {
    var ztree_settings = {
        view: {
            nameIsHTML: true,
            showTitle: true,
            selectedMulti: false,
            dblClickExpand: false,
            expandSpeed: "fast"
        },
        async: {
            enable: true,
            type: "GET",
            dataType: "JSON",
            url: "",
            otherParam: {},
            autoParam: ["id=pid"]
        },
        callback: {},
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        }
    };

    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    function addlabel(node) {
        if (node != null) {
            if (node.config != null) {
                insertLabelVal(node.config);
            }
        }
    }

    var label = function () {
        //初始化布局
        mini.parse();
        function addDiyDom(treeId, node) {
            cur.map.put(node.tId, node);
            var aObj = $("#" + node.tId + "_a");
            if (!node.isParent) {
                var addStr = ' <span class="button tree-edit-insert" nodeId=' + node.tId + ' id="addBtn_' + node.id + '" title="' + node.name + '"> </span>';
                aObj.append(addStr);
            }
            var btn = $("#addBtn_" + node.id);
            btn && btn.on("click", function () {
                var tId = $(this).attr("nodeId");
                addlabel(cur.map.get(tId));
                return false;
            });
        }

        function dataFilter(treeId, parentNode, responseData) {
            var treeData = responseData.data;
            for(var i=0,len=treeData.length;i<len;i++){
                if(treeData[i].isParent == 0){
                    treeData[i].icon="/images/label.png";
                }
            }
            return treeData;
        }

        function onClick(event, treeId, node) {
            if (node.isParent) {
                cur.labelTree.cancelSelectedNode(node);
                cur.labelTree.expandNode(node);
            }
            event.stopPropagation();
        }

        var settings = $.extend(true, ztree_settings, {
            view: {
                fontCss: getFont,
                addDiyDom: addDiyDom
            },
            async: {
                url: '/label/tree?dataFlag=1',
                dataFilter: dataFilter
            },
            callback: {
                onClick: onClick,
                onAsyncSuccess: function () {
                    $("#label_tree").parent().css('padding', '5px 15px 15px 5px');
                }
            }
        });

        cur.labelTree = $.fn.zTree.init($("#label_tree"), settings);

    };

    function insertLabelVal(value) {
        var tx = document.getElementById('tplContent');
        insertAtCursor(tx, value);
    }

    return {
        label: label
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
