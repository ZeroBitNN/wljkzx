<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="netManage_wgmm_editForm" method="post">
	<table align="center" style="width:90%;">
		<tr>
			<th><br></th>
			<td><br></td>
		</tr>
		<tr>
			<th>ID</th>
			<td colspan="3"><input name="id" class="easyui-textbox" readonly="readonly" style="width:100%" /></td>
		</tr>
		<tr>
			<th>系统名称</th>
			<td colspan="3"><input name="name" class="easyui-validatebox" data-options="required:true" style="width:98%" /></td>
		</tr>
		<tr>
			<th>链接地址</th>
			<td colspan="3"><input name="link" class="easyui-textbox" style="width:100%" /></td>
		</tr>
		<tr>
			<th>用户名</th>
			<td><input name="username" /></td>
			<th>密码</th>
			<td><input name="pwd" style="width:96%" /></td>
		</tr>
		<tr>
			<th><br></th>
			<td><br></td>
		</tr>
	</table>
</form>