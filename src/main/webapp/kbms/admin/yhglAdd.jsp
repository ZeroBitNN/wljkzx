<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<form id="kbms_admin_yhgl_addForm" method="post">
	<div class="easyui-panel" style="width:100%;padding:30px 60px;" data-options="border:false">
		<div style="margin-bottom:20px">
			<label for="username" class="label-top">用户名:</label> <input name="username"
				class="easyui-validatebox tb" data-options="required:true,validType:'length[3,10]'">
		</div>
		<div style="margin-bottom:20px">
			<label for="pwd" class="label-top">密码:</label> <input name="pwd" type="password"
				class="easyui-validatebox tb" data-options="required:true" />
		</div>
		<div style="margin-bottom:20px">
			<label for="rePwd" class="label-top">重复密码:</label> <input name="rePwd" type="password"
				class="easyui-validatebox tb"
				data-options="required:true,validType:'eqPwd[\'#kbms_admin_yhgl_addForm input[name=pwd]\']'" />
		</div>
	</div>
</form>

<style scoped="scoped">
.tb {
	width: 100%;
	margin: 0;
	padding: 5px 4px;
	border: 1px solid #ccc;
	box-sizing: border-box;
}
</style>