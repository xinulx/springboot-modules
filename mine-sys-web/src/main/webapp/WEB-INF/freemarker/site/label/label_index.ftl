<style type="text/css">
    .ztree li span.button.add {
        margin-left: 2px;
        margin-right: -1px;
        background-position: -144px 0;
        vertical-align: top;
        *vertical-align: middle
    }
</style>
<div id="content_layout" class="mini-layout" style="width:100%;height:100%;">
    <div region="west" showHeader="false" splitSize="0" width="260"
         style="border:none; background:#F6FAFD; border-right: solid 1px #e5e5e5">

        <div style="padding:5px; overflow: auto;">
            <div id="label_tree" class="ztree label-ztree"></div>
        </div>

    </div>
    <div region="center" style="border:none; overflow: auto;">
        <div id="label_body">
        </div>
    </div>
</div>
<script src="${ctx}/js/pages/label_mgr.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $.resetHeight("label_tree", 100);
        label_mgr.init();
    });
</script>