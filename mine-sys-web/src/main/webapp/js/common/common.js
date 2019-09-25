/*
函数：格式化日期
参数：formatStr-格式化字符串
d：将日显示为不带前导零的数字，如1
dd：将日显示为带前导零的数字，如01
ddd：将日显示为缩写形式，如Sun
dddd：将日显示为全名，如Sunday
M：将月份显示为不带前导零的数字，如一月显示为1
MM：将月份显示为带前导零的数字，如01
MMM：将月份显示为缩写形式，如Jan
MMMM：将月份显示为完整月份名，如January
yy：以两位数字格式显示年份
yyyy：以四位数字格式显示年份
h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
hh：使用12小时制将小时显示为带前导零的数字
H：使用24小时制将小时显示为不带前导零的数字
HH：使用24小时制将小时显示为带前导零的数字
m：将分钟显示为不带前导零的数字
mm：将分钟显示为带前导零的数字
s：将秒显示为不带前导零的数字
ss：将秒显示为带前导零的数字
l：将毫秒显示为不带前导零的数字
ll：将毫秒显示为带前导零的数字
tt：显示am/pm
TT：显示AM/PM
返回：格式化后的日期
*/
Date.prototype.format = function (formatStr) {
    var date = this;
    /*
    函数：填充0字符
    参数：value-需要填充的字符串, length-总长度
    返回：填充后的字符串
    */
    var zeroize = function (value, length) {
        if (!length) {
            length = 2;
        }
        value = new String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    };
    return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function ($0) {
        switch ($0) {
            case 'd':
                return date.getDate();
            case 'dd':
                return zeroize(date.getDate());
            case 'ddd':
                return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
            case 'dddd':
                return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
            case 'M':
                return date.getMonth() + 1;
            case 'MM':
                return zeroize(date.getMonth() + 1);
            case 'MMM':
                return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
            case 'MMMM':
                return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
            case 'yy':
                return new String(date.getFullYear()).substr(2);
            case 'yyyy':
                return date.getFullYear();
            case 'h':
                return date.getHours() % 12 || 12;
            case 'hh':
                return zeroize(date.getHours() % 12 || 12);
            case 'H':
                return date.getHours();
            case 'HH':
                return zeroize(date.getHours());
            case 'm':
                return date.getMinutes();
            case 'mm':
                return zeroize(date.getMinutes());
            case 's':
                return date.getSeconds();
            case 'ss':
                return zeroize(date.getSeconds());
            case 'l':
                return date.getMilliseconds();
            case 'll':
                return zeroize(date.getMilliseconds());
            case 'tt':
                return date.getHours() < 12 ? 'am' : 'pm';
            case 'TT':
                return date.getHours() < 12 ? 'AM' : 'PM';
        }
    });
};

// document.writeln(new Date().format("yyyy-MM-dd hh:mm:ss"));
function getConstant(id, tableName, columnName, desc) {
    var sel = $("#" + id);
    $.ajax({
        type: 'post',
        url: '/common/getConstant',
        dataType: 'json',
        data: {tableName: tableName, columnName: columnName},
        async: false,
        success: function (result) {
            if (result.data.length == 0) {
                sel.empty().append('<option value="">请选择' + desc + '</option>');
            } else {
                sel.empty().append('<option value="">请选择' + desc + '</option>');
                for (var i = 0; i < result.data.length; i++) {
                    sel.append('<option value="' + result.data[i].CODE + '">' + result.data[i].NAME + '</option>');
                }
            }
        }
    });
}

var Mine = {
    layer: {
        openWin: function (title, url, width, height) {
            var cct = $("#layerWin").empty().load(url);
            return layer.open({
                type: 1,
                title: title,
                area: [width + "px", height + "px"],
                content: cct
            });
        },
        openWin2: function (title, url, width, height) {
            var cct = $("#layerWin2").empty().load(url);
            return layer.open({
                type: 1,
                title: title,
                area: [width + "px", height + "px"],
                content: cct
            });
        },
        openIframe: function (title, url, width, height) {
            return layer.open({
                type: 2,
                title: title,
                area: [width + "px", height + "px"],
                content: url
            });
        },
        tips: function (desc, icon) {
            layer.msg(desc, {icon: icon == undefined ? 1 : icon, shade: 0.01, time: 1000});
        }
    },
    encode: function (content) {
        return window.btoa(encodeURIComponent(content));
    },
    decode: function (content) {
        return decodeURIComponent(window.atob(content));
    },
    getFormData: function (formId) {
        return decodeURIComponent($('#' + formId).serialize(), true);
    },
    /**
     * 定义 ztree 杖举
     * @return   {String}
     */
    treeDataType: function () {
        return {
            "SITE": "SITE",
            "UNIT": "UNIT",
            "USER": "USER",
            "ROLE": "ROLE",
            "TPL": "TPL",
            "MENU": "MENU",
            "COLUMN": "COLUMN",
            "LABEL": "LABEL",
            "CATALOG": "CATALOG",
            "GUIDE_ORGAN": "GUIDE_ORGAN",
            "PUBLIC_CONTENT": "PUBLIC_CONTENT",
            "SUBMITTED": "SUBMITTED",
            "NET_WORK": "NET_WORK",
            "TREE": "TREE"
        }
    }(),
    /**
     * ztree树的数据过滤器，用于统一处理各种的图标
     * @treeDataFilter
     * @param   {Object}       ztree 异步加载的 JSON 对象
     * @param   {String}       树的类型，参与 treeDataType 方法
     */
    treeDataFilter: function (responseData, type) {
        if (!responseData) {
            return false
        }
        switch (type) {
            case this.treeDataType.SITE:
                for (var i = 0, l = responseData.length; i < l; i++) {
                    var node = responseData[i];
                    if (node.type == "CMS_Site" || node.type == "SUB_Site") {
                        node.icon = GLOBAL_CONTEXTPATH + "/assets/images/" + node.type + ".png";
                    } else {
                        if ((node.columnTypeCode == "workGuide" || node.columnTypeCode == "sceneService") && !node.isParent) {
                            node.icon = GLOBAL_CONTEXTPATH + "/assets/images/" + node.columnTypeCode + "2.png";
                        } else {
                            node.icon = GLOBAL_CONTEXTPATH + "/assets/images/" + node.columnTypeCode + ".png";
                        }
                    }
                    if (node.columnTypeCode == "redirect") {
                        node.tips = "跳转地址：" + node.transUrl;
                    } else {
                        node.tips = "ID:" + node.indicatorId;
                    }
                }
                break;
            case this.treeDataType.UNIT:
            case this.treeDataType.USER:
                for (var i = 0, l = responseData.length; i < l; i++) {
                    var node = responseData[i];
                    node.icon = GLOBAL_CONTEXTPATH + "/assets/images/" + (node.type || node.nodeType || "").toLowerCase() + ".gif";
                    node.tips = "单位ID：" + node.id;
                }
                break;
            case this.treeDataType.GUIDE_ORGAN:
                for (var i = 0, l = responseData.length; i < l; i++) {
                    var node = responseData[i];
                    node.icon = GLOBAL_CONTEXTPATH + "/assets/images/organ.gif";
                }
                break;
            case this.treeDataType.LABEL:
                for (var i = 0, l = responseData.length; i < l; i++) {
                    var node = responseData[i];
                    node.tips = "标签ID：" + node.id + "\n标签描述：" + node.description;
                    if (node.pId > 0) {
                        node.icon = GLOBAL_CONTEXTPATH + "/assets/images/label01.png";
                    }
                    node.isParent = node.isParent == 1 ? true : false;
                }
                break;
            case this.treeDataType.PUBLIC_CONTENT:
                for (var i = 0, l = responseData.length; i < l; i++) {
                    var node = responseData[i];
                    if (node.data) {
                        if (node.type == "Organ") {
                            node.tips = "单位ID：" + node.organId;
                        } else {
                            node.tips = "目录ID：" + node.data.catId;
                        }
                    } else {
                        node.tips = "目录ID：" + node.id;

                    }
                }
                break;
            case this.treeDataType.ROLE:
                var icon = "";
                for (var i = 0, l = responseData.length; i < l; i++) {
                    var node = responseData[i];
                    if (node.code == "super_admin" || node.code == "site_admin") {
                        icon = node.code;
                    } else {
                        icon = "common_admin";
                    }
                    node.icon = GLOBAL_CONTEXTPATH + "/assets/images/" + icon + ".gif";
                }
                break;
            case this.treeDataType.COLUMN:
                for (var i = 0, l = responseData.length; i < l; i++) {
                    var node = responseData[i];
                    node.icon = GLOBAL_CONTEXTPATH + "/images/column/" + node.type + ".png";
                }
                break;
            case this.treeDataType.TREE:
                break;
        }
        return responseData
    }
};

window.alert = function (msg, callback) {
    layer.alert(msg, {icon: 7}, function (index) {
        layer.close(index);
        if (typeof (callback) === "function") {
            callback("ok");
        }
    });
};

function resetHeight(id, cuteHeight) {
    var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度
    var bodyHeight = document.getElementById(id); //寻找ID为content的对象
    if (bodyHeight) {
        bodyHeight.style.height = (h - cuteHeight) + "px"; //你想要自适应高度的对象
    }
}

function autoHeight(id, cuteHeight) {
    setInterval(function () {
        resetHeight(id, cuteHeight);
    }, 200);
}

var stompClient = null;
var tipsCount = 0;

function timeTips(content) {
    if (!content) {
        return;
    }
    var time = new Date().getTime();
    var msgDiv = '<div id="messageContent_' + time + '" class="hidden message-content"></div>';
    $('body').append(msgDiv);
    tipsCount++;
    var msg = '';
    msg += '<div style="font-size: 14px">';
    if (content.status == 1) {
        $("#messageContent_" + time).css('background-color', "#009688");
        msg += '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;&nbsp;';
    } else {
        $("#messageContent_" + time).css('background-color', "#a94442");
        msg += '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;';
    }
    msg += (content.title ? '系统消息' : content.title) + '</div>';
    msg += '<div class="message-content-body">' + content.body + '</div>';
    msg += '<div id="process_' + time + '" class="bg-warning" style="height: 5px;width: 100%"></div>';
    $("#messageContent_" + time).html(msg).css({"cursor": "pointer"}).click(function () {
        layer.close(index);
        $("#messageContent_" + time).remove();
        if (tipsCount > 0) {
            tipsCount--;
        }
    }).attr("title", "点击立即关闭提醒");
    var index = layer.open({
        type: 1,
        shade: false,
        offset: [function () {
            if (tipsCount == 1) {
                return 55;
            } else {
                return 55 + (tipsCount - 1) * 110;
            }
        }, document.body.clientWidth - 380],
        closeBtn: 0,//不显示关闭按钮
        title: false, //不显示标题
        content: $("#messageContent_" + time),
        cancel: function () {
            $("#messageContent_" + time).remove();
            if (tipsCount > 0) {
                tipsCount--;
            }
        }
    });
    var i = 0;
    var id = setInterval(function () {
        i++;
        $("#process_" + time).css("width", (100 - (i * 0.1)) + "%");
    }, 5);
    setTimeout(function () {
        clearInterval(id);
        layer.close(index);
        $("#messageContent_" + time).remove();
        if (tipsCount > 0) {
            tipsCount--;
        }
    }, 5000);
}

function getIcon(status) {
    if (status == null) {
        return "info";
    } else if (status == 1) {
        return "success";
    } else if (status == 2) {
        return "info";
    } else if (status == 3) {
        return "warning";
    } else if (status == 0) {
        return "error";
    }
}

/**
 * 监听服务端系统消息
 */
function initSocket() {
    if (stompClient == null) {
        var socket = new SockJS('http://' + location.host + '/websocket?token=mq');
        stompClient = Stomp.over(socket);
    }
    stompClient.connect({token: "mq"}, function (frame) {
        console.log(frame);
        stompClient.subscribe('/topic/receiveMessage', function (event) {
            console.log('生成消息！');
            var content = JSON.parse(event.body);
            if (content.type == 'log' || content.type == 'fileLog') {
                return;
            }
            $.toast({
                text: content.body,
                heading: 'success',
                icon: getIcon(content.status),
                showHideTransition: 'fade',
                allowToastClose: true,
                allowClickClose: true,
                hideAfter: 5000,
                stack: 5,
                position: {left: 'auto', right: '20px', top: '55px', bottom: 'auto'},
                textAlign: 'left',
                loader: true,
                loaderBg: '#ffffbb',
                beforeShow: function () {
                },
                afterShown: function () {
                },
                beforeHide: function () {
                },
                afterHidden: function () {
                }
            });
        }, {
            token: "mq_message"
        });
    });
    console.info("initSocket success");
}

function sendSocket(message) {
    if (!message) {
        message = "我是一条消息！时间-" + new Date().toLocaleTimeString();
    }
    $.post("/common/activeMQ/process", {message: message});
}

function initTreeData(result, oldData, type) {
    if (oldData && oldData.length > 0) {
        for (var i = 0; i < oldData.length; i++) {
            oldData[i]['my_title'] = oldData[i][type] + "ID：" + oldData[i].id;
            result.push(oldData[i]);
        }
    }
}

function showPagination(id, url) {
    $("#contentMgr").html('<div class="mop-load-x" align="center" style="position: absolute;top: 40%;left: 30%;font-size:30px"></div>');
    Mopload();
    $("#" + id).load(url, function () {
        $(".mask-loading").hide();
    });
}

function moveUp(_a) {
    var _row = _a.parentNode.parentNode;
    //如果不是第一行，则与上一行交换顺序
    var _node = _row.previousSibling;
    while (_node && _node.nodeType != 1) {
        _node = _node.previousSibling;
    }
    if (_node) {
        swapNode(_row, _node);
    } else {
        Mine.layer.tips("已经是第一行了", 0);
    }
}

function moveDown(_a) {
    var _row = _a.parentNode.parentNode;
    //如果不是最后一行，则与下一行交换顺序
    var _node = _row.nextSibling;
    while (_node && _node.nodeType != 1) {
        _node = _node.nextSibling;
    }
    if (_node) {
        swapNode(_row, _node);
    } else {
        Mine.layer.tips("已经是最后一行了", 0);
    }
}

function swapNode(node1, node2) {
    //获取父结点
    var _parent = node1.parentNode;
    //获取两个结点的相对位置
    var _t1 = node1.nextSibling;
    var _t2 = node2.nextSibling;
    //将node2插入到原来node1的位置
    if (_t1) _parent.insertBefore(node2, _t1);
    else _parent.appendChild(node2);
    //将node1插入到原来node2的位置
    if (_t2) _parent.insertBefore(node1, _t2);
    else _parent.appendChild(node1);
    Mine.layer.tips("排序成功！");
}

function initevent() {
    jQuery.event.add();
}

//获取随机数
function getRandom(n) {
    return Math.floor(Math.random() * n + 1)
}

function addfourstlye(obj, name, value) {
    var four_list = ["-webkit-", "-moz-", "-o-", ""];
    for (var i = 0; i < four_list.length; i++) {
        obj.css(four_list[i] + name, value);
    }
}

function Mopload() {
    var load_name_list = ["rotating-plane", "double-bounce", "wave", "wandering-cubes", "pulse", "chasing-dots", "three-bounce", "circle", "cube-grid", "run-ball", "fading-circle"];
    var default_load = "rotating-plane";
    var default_index = 0;
    $("[class^=mop-load]").each(function (index) {
        var _mop_html = $(this).html().trim();
        var _mop_class = $(this).attr("class");
        var _temp = _mop_class.split("mop-load-");
        if (_temp.length < 2) {
            return;
        }
        var arr = '<div class="mop-load-div">';
        if (_temp[1].trim() * 1 < load_name_list.length) {
            arr += '<div class="mop-css-' + _temp[1].trim() + '">'
        } else if (_temp[1].trim() == "x") {
            arr += '<div class="mop-css-x">';
        } else {
            return;
        }
        if (_mop_html == "") {
            _mop_html = "Loading Resources……"
        } else {
            $(this).html(_mop_html);
        }
        arr += '</div><div class="mop-load-text" >' + _mop_html + '</div></div>';
        $(this).html(arr);
        //addfourstlye($(this),"transition","height 2s linear 0s;");
        $(this).css("text-align", "center");
        //$(this).find(".mop-load-div").hide();
    });
    $("[class^=mop-css]").each(function (index) {
        var _mop_class = $(this).attr("class");
        var _temp = _mop_class.split("mop-css-");
        if (_temp == "mop-css") {
            $(this).addClass(default_load);
        }
        if (_temp[1].trim() == "x") {
            var rand = getRandom(load_name_list.length - 1);
            $(this).addClass(load_name_list[rand]);
        } else if (_temp[1] * 1 < load_name_list.length) {
            $(this).addClass(load_name_list[_temp[1]]);
        } else {
            return;
        }
    });
    $(".double-bounce").each(function (index) {
        var arr = '<div class="double-bounce1"></div><div class="double-bounce2"></div>';
        $(this).append(arr);
    });
    $(".wave").each(function (index) {
        var arr = '<div class = "rect1" ></div><div class = "rect2" ></div><div class = "rect3" ></div><div class = "rect4" ></div><div class = "rect5" ></div>';
        $(this).append(arr);
    });
    $(".wandering-cubes").each(function (index) {
        var arr = '<div class="cube1"></div><div class="cube2"></div>';
        $(this).append(arr);
    });
    $(".chasing-dots").each(function (index) {
        var arr = '<div class="dot1"></div><div class="dot2"></div>';
        $(this).append(arr);
    });
    $(".three-bounce").each(function (index) {
        var arr = '<div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div>';
        $(this).append(arr);
    });
    $(".circle").each(function (index) {
        var arr = '<div class="spinner-container container1"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div>';
        arr += '<div class="spinner-container container2"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div>';
        arr += '<div class="spinner-container container3"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div>'
        $(this).append(arr);
    });
    $(".cube-grid").each(function (index) {
        var arr = '<div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div>';
        $(this).append(arr);
    });
    $(".run-ball").each(function (index) {
        var arr = '<span class="sk-inner-circle"></span>';
        $(this).append(arr);
    });
    $(".fading-circle").each(function (index) {
        var arr = '<div class="sk-circle1 sk-circle"></div><div class="sk-circle2 sk-circle"></div><div class="sk-circle3 sk-circle"></div><div class="sk-circle4 sk-circle"></div><div class="sk-circle5 sk-circle"></div><div class="sk-circle6 sk-circle"></div>';
        arr += '<div class="sk-circle7 sk-circle"></div><div class="sk-circle8 sk-circle"></div><div class="sk-circle9 sk-circle"></div><div class="sk-circle10 sk-circle"></div><div class="sk-circle11 sk-circle"></div><div class="sk-circle12 sk-circle"></div>';
        $(this).append(arr);
    });
}