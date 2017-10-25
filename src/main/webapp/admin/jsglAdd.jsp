<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {});
</script>

<form id="admin_jsglAdd_addForm" method="post">
	<table align="center" style="width:90%;">
		<tr>
			<th><br></th>
			<td><br></td>
		</tr>
		<tr>
			<th>角色名称</th>
			<td><input name="name" class="easyui-validatebox" data-options="required:true" /></td>
		</tr>
		<tr>
			<th>角色权限</th>
			<td><input name="resourcesIds" class="easyui-combobox" multiple="true" multiline="true"
				style="width:90%;height:65px;"
				data-options="valueField:'id',textField:'name',groupField:'pid',editable:false,url:'${pageContext.request.contextPath}/resourcesAction!doNotNeedSecurity_getResources.action',required:true" /></td>
		</tr>
		<tr>
			<th><br></th>
			<td><br></td>
		</tr>
	</table>
</form>