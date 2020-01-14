/**
 * 用jquery扩展静态方法
 */
$.extend({
    /**
     * 获取常量数据
     * @param id
     * @param tableName
     * @param columnName
     * @param desc
     */
    getConstant: function (id, tableName, columnName, desc) {
        var sel = $("#" + id);
        $.ajax({
            type: 'post',
            url: '/common/getConstant',
            dataType: 'json',
            data: {tableName: tableName, columnName: columnName},
            async: false,
            success: function (result) {
                sel.empty().append('<option value="">请选择' + desc + '</option>');
                for (var i = 0; i < result.data.length; i++) {
                    sel.append('<option value="' + result.data[i].CODE + '">' + result.data[i].NAME + '</option>');
                }
            }
        });
    },
    /**
     * 重置高度
     * @param id
     * @param cuteHeight
     */
    resetHeight: function (id, cuteHeight) {
        var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度
        var bodyHeight = document.getElementById(id); //寻找ID为content的对象
        if (bodyHeight) {
            bodyHeight.style.height = (h - cuteHeight) + "px"; //你想要自适应高度的对象
        }
    },
    /**
     * 自动浮动高度
     * @param id
     * @param cuteHeight
     */
    autoHeight: function (id, cuteHeight) {
        setInterval(function () {
            $.resetHeight(id, cuteHeight);
        }, 200);
    },
    /**
     * 首页菜单
     * @param menu
     */
    sidebarMenu: function (menu) {
        var animationSpeed = 300;
        $(menu).on('click', 'li a', function (e) {
            var $this = $(this);
            var checkElement = $this.next();
            if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
                checkElement.slideUp(animationSpeed, function () {
                    checkElement.removeClass('menu-open');
                });
                checkElement.parent("li").removeClass("active");
            } else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
                var parent = $this.parents('ul').first();
                var ul = parent.find('ul:visible').slideUp(animationSpeed);
                ul.removeClass('menu-open');
                var parent_li = $this.parent("li");
                checkElement.slideDown(animationSpeed, function () {
                    checkElement.addClass('menu-open');
                    parent.find('li.active').removeClass('active');
                    parent_li.addClass('active');
                });
            }
            if (checkElement.is('.treeview-menu')) {
                e.preventDefault();
            }
        });
    }
});

/**
 * 自定义插件库
 * @type {{encode: (function(*=): string), getFormData: (function(*): string), treeDataFilter: Mine.treeDataFilter, decode: (function(*=): string), layer: {openWin: (function(*=, *=, *, *): (*|c.index|s.index)), openWin2: (function(*=, *=, *, *): (*|c.index|s.index)), tips: Mine.layer.tips, openIframe: (function(*=, *=, *, *): (*|c.index|s.index))}, treeDataType: String}}
 */
var Mine = {
    stompClient: null,//websocket client
    tipsCount: 0,
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
                console.log('this is a unit node');
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
    },

    /**
     * 弹窗消息
     * @param content
     */
    timeTips: function (content) {
        if (!content) {
            return;
        }
        var time = new Date().getTime();
        var msgDiv = '<div id="messageContent_' + time + '" class="hidden message-content"></div>';
        $('body').append(msgDiv);
        Mine.tipsCount++;
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
            if (Mine.tipsCount > 0) {
                Mine.tipsCount--;
            }
        }).attr("title", "点击立即关闭提醒");
        var index = layer.open({
            type: 1,
            shade: false,
            offset: [function () {
                if (Mine.tipsCount == 1) {
                    return 55;
                } else {
                    return 55 + (Mine.tipsCount - 1) * 110;
                }
            }, document.body.clientWidth - 380],
            closeBtn: 0,//不显示关闭按钮
            title: false, //不显示标题
            content: $("#messageContent_" + time),
            cancel: function () {
                $("#messageContent_" + time).remove();
                if (Mine.tipsCount > 0) {
                    Mine.tipsCount--;
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
            if (Mine.tipsCount > 0) {
                Mine.tipsCount--;
            }
        }, 5000);
    },
    /**
     * 根据消息状态获取图标
     * @param status
     * @returns {string}
     */
    getIcon: function (status) {
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
    },
    /**
     * 监听服务端系统消息
     */
    initSocket: function () {
        if (Mine.stompClient == null) {
            var socket = new SockJS('http://' + location.host + '/websocket?token=mq');
            Mine.stompClient = Stomp.over(socket);
        }
        Mine.stompClient.connect({token: "mq"}, function (frame) {
            Mine.stompClient.subscribe('/topic/receiveMessage', function (event) {
                var content = JSON.parse(event.body);
                if (content.type == 'log' || content.type == 'fileLog') {
                    return;
                }
                $.toast({
                    text: content.body,
                    heading: 'success',
                    icon: Mine.getIcon(content.status),
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
    },
    /**
     * 发送消息
     * @param message
     */
    sendSocket: function (message) {
        if (!message) {
            message = "我是一条消息！时间-" + new Date().toLocaleTimeString();
        }
        $.post("/common/activeMQ/process", {message: message});
    },
    /**
     * 重置部分tree数据
     * @param result
     * @param oldData
     * @param type
     */
    initTreeData: function (result, oldData, type) {
        if (oldData && oldData.length > 0) {
            for (var i = 0; i < oldData.length; i++) {
                oldData[i]['my_title'] = oldData[i][type] + "ID：" + oldData[i].id;
                result.push(oldData[i]);
            }
        }
    },
    /**
     * 显示分页
     * @param id
     * @param url
     */
    showPagination: function (id, url) {
        $("#" + id).load(url, function () {
            $(".mask-loading").hide();
        });
    },
    /**
     * 排序上移
     * @param _a
     */
    moveUp: function (_a) {
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
    },
    /**
     * 排序下移
     * @param _a
     */
    moveDown: function (_a) {
        var _row = _a.parentNode.parentNode;
        //如果不是最后一行，则与下一行交换顺序
        var _node = _row.nextSibling;
        while (_node && _node.nodeType != 1) {
            _node = _node.nextSibling;
        }
        if (_node) {
            this.swapNode(_row, _node);
        } else {
            Mine.layer.tips("已经是最后一行了", 0);
        }
    },
    /**
     * 节点互换位置
     * @param node1
     * @param node2
     */
    swapNode: function (node1, node2) {
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
};

/**
 * 重写alert
 * @param msg
 * @param callback
 */
window.alert = function (msg, callback) {
    layer.alert(msg, {icon: 7}, function (index) {
        layer.close(index);
        if (typeof (callback) === "function") {
            callback("ok");
        }
    });
};

(function ($) {
    'use strict';
    $.fn.LineProgressbar = function (options) {
        var options = $.extend({
            percentage: 0,
            ShowProgressCount: true,
            duration: 1000,
            fillBackgroundColor: '#38db73',
            backgroundColor: '#EEEEEE',
            radius: '0px',
            height: '15px',
            width: '100%'
        }, options);
        return this.each(function (index, el) {
            $(el).html('<div class="progressbar"><div class="proggress"></div><div class="percentCount"></div></div>');
            var progressFill = $(el).find('.proggress');
            var progressBar = $(el).find('.progressbar');
            progressFill.css({
                backgroundColor: options.fillBackgroundColor,
                height: options.height,
                borderRadius: options.radius
            });
            progressBar.css({
                width: options.width,
                backgroundColor: options.backgroundColor,
                borderRadius: options.radius
            });
            progressFill.animate({width: options.percentage + "%"}, {
                step: function (x) {
                    if (options.ShowProgressCount) {
                        $(el).find(".percentCount").text(Math.round(x) + "%");
                    }
                }, duration: options.duration
            });
        });
    }
})(jQuery);