<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {});
</script>

<form id="admin_jsglEdit_editForm" method="post">
	<table align="center" style="width:90%;">
		<tr>
			<th>ID</th>
			<td><input name="id" readonly="readonly" style="width: 70%;" /></td>
		</tr>
		<tr>
			<th>角色名称</th>
			<td><input name="name"  style="width: 70%;" class="easyui-validatebox" data-options="required:true" /></td>
		</tr>
		<tr>
			<th>角色权限</th>
			<td><input name="resourcesIds" class="easyui-combobox" multiple="true" multiline="true"
				style="width:90%;height:65px;"
				data-options="valueField:'id',
					textField:'name',
					groupField:'pid',
					editable:false,
					url:'${pageContext.request.contextPath}/resourcesAction!doNotNeedSecurity_getResources.action',
					required:true,
					queryParams:{'id':<%=request.getParameter("id") %>}" /></td>
		</tr>
	</table>
</form>