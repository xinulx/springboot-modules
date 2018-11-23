<form class="layer-form" id="userForm">
    <input type="hidden" class="form-control" id="organId" name="organId" value="${organId!''}">
    <input type="hidden" class="form-control" id="id" name="id" value="${(eo.id)!}">
    <div class="form-group">
        <label>单位</label>
        <input readonly value="${(organEO.organName)!}" type="text" class="form-control" placeholder="">
    </div>
    <div class="form-group">
        <label>排序号</label>
        <input value="${(eo.orderNum)!''}" type="text" class="form-control" id="orderNum" name="orderNum" placeholder="">
    </div>
    <div class="form-group">
        <label>授予角色</label>
        <input value="${(eo.roleId)!''}" type="text" class="form-control" id="roleId" name="roleId" placeholder="">
    </div>
    <div class="form-group">
        <label>用户ID</label>
        <input <#if (eo.id)??>readonly</#if> value="${(eo.userId)!}" type="text" class="form-control" id="userId" name="userId" placeholder="">
    </div>
    <div class="form-group">
        <label>用户名称</label>
        <input value="${(eo.userName)!''}" type="text" class="form-control" id="userName" name="userName" placeholder="">
    </div>
    <div class="form-group">
        <label>性别</label>
        <select id="gender" name="gender" class="form-control">
            <option value="FeMale" <#if (eo.gender)! =='FeMale'>selected</#if>>女</option>
            <option value="Male" <#if (eo.gender)! =='Male'>selected</#if>>男</option>
        </select>
    </div>
    <div class="form-group">
        <label>设置状态</label>
        <select id="status" name="status" class="form-control">
            <option value="Normal" <#if (eo.status)! =='Normal'>selected</#if>>启用</option>
            <option value="Removed" <#if (eo.status)! =='Removed'>selected</#if>>删除</option>
            <option value="Locked" <#if (eo.status)! =='Locked'>selected</#if>>锁定</option>
            <option value="Disabled" <#if (eo.status)! =='Disabled'>selected</#if>>禁用</option>
        </select>
    </div>
    <div class="form-group">
        <label>密码</label>
        <input value="${(eo.passWord)!''}" type="password" class="form-control" id="passWord" name="passWord" placeholder="">
    </div>
    <div class="form-group">
        <label>秘钥</label>
        <input value="${(eo.key)!''}" type="key" class="form-control" id="key" name="key" placeholder="">
    </div>
    <div class="form-group">
        <label>职务</label>
        <input value="${(eo.position)!''}" type="text" class="form-control" id="position" name="position" placeholder="">
    </div>
    <div class="form-group">
        <label>联系电话</label>
        <input value="${(eo.phone)!''}" type="text" class="form-control" id="phone" name="phone" placeholder="">
    </div>
    <div class="form-group">
        <label>电子邮件</label>
        <input value="${(eo.emailAddress)!''}" type="text" class="form-control" id="emailAddress" name="emailAddress" placeholder="">
    </div>
    <div class="form-group">
        <label>办公电话</label>
        <input value="${(eo.officePhone)!''}" type="text" class="form-control" id="officePhone" name="officePhone" placeholder="">
    </div>
    <div class="form-group">
        <label>办公地址</label>
        <input value="${(eo.officeAddress)!''}" type="text" class="form-control" id="officeAddress" name="officeAddress" placeholder="">
    </div>
    <div class="center-block">
        <button type="submit" class="btn btn-default">保 存</button>
        <button type="reset" class="btn btn-danger">重 置</button>
    </div>
</form>
<script>
    $("#userForm").validator({
        fields: {
            userId: "编码:required; length(6~50)",
            passWord: "密码:required; length(6~20)",
            roleId: "角色:required;",
            gender: "角色:required;",
            key: "秘钥:required; length(1~10)",
            userName: "名称:required; length(2~50)"
        },
        focusCleanup: true,
        timely: 2,
        theme:'yellow_right_effect',
        msgClass:'n-bottom',
        valid: function (form) {
            // form.submit();
            var formData = decodeURIComponent($("#userForm").serialize(), true);
            $.post("/user/save",formData,function (result) {
                if (result.status == '1') {
                    layer.msg(result.desc, {icon: 1, shade: 0.01, time: 1000},function(index){
                        layer.close(index);
                        layer.close(roleIndex);
                        grid.reload();
                    });
                } else {
                    layer.msg(result.desc, {icon: 2, shade: 0.01, time: 1000});
                }
            });
        }
    });
</script>