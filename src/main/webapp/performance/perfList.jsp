<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<table class="easyui-datagrid" title="Basic DataGrid" style="width:700px;height:250px"
	data-options="singleSelect:true,fitColumns:true,border:false,fit:true,url:'${pageContext.request.contextPath}/performance/datagrid_data1.json',method:'get'">
	<thead>
		<tr>
			<th data-options="field:'itemid',width:80">Item ID</th>
			<th data-options="field:'productid',width:100">Product</th>
			<th data-options="field:'listprice',width:80,align:'right'">List Price</th>
			<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
			<th data-options="field:'attr1',width:250">Attribute</th>
			<th data-options="field:'status',width:60,align:'center'">Status</th>
		</tr>
	</thead>
</table>
