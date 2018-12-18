<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<form id="admin_yhglEdit_editForm" method="post">
	<table>
		<tr>
			<th>ID</th>
			<td><input name="id" class="easyui-validatebox"
				readonly="readonly" /></td>
			<td></td>
			<th>角色</th>
			<td><input name="role" class="easyui-combobox"
          data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getRole.action',required:true" /></td>
		</tr>
		<tr>
			<th>用户名</th>
			<td><input name="username" class="easyui-validatebox"
				data-options="required:true" /></td>
			<td></td>
			<th>密码</th>
			<td><input name="pwd" type="password" class="easyui-validatebox"
				data-options="required:true" /></td>
		</tr>
		<tr>
			<th>创建时间</th>
			<td><input name="createtime" readonly="readonly" /></td>
			<td></td>
			<th>修改时间</th>
			<td><input name="modifytime" readonly="readonly" /></td>
		</tr>
	</table>
</form>