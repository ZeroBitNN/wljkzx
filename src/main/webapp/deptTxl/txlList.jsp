<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#deptTxl_txlList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/deptTxlAction!doNotNeedSecurity_datagrid.action',
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			fitColumns : true,
			nowrap : true,
			singleSelect : true,
			pageSize : 40,
			sortName : 'id',
			sortOrder : 'desc',
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 100,
				},
				{
					field : 'xm',
					title : '姓名',
					width : 200
				},
				{
					field : 'sj',
					title : '手机',
					width : 100,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'dh',
					title : '电话',
					width : 100,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'xlt',
					title : '小灵通',
					width : 100
				},
				{
					field : 'dwzw',
					title : '职务',
					width : 200
				},
				{
					field : 'dwbm',
					title : '单位部门',
					width : 200
				}
			]],
			toolbar : '#deptTxl_txlList_toolbar'
		});

		$('#deptTxl_txlList_searchbox').searchbox({
			searcher : function(value, name) {
				//alert(value + "," + name)
				$('#deptTxl_txlList_datagrid').datagrid('load', {
					content : value
				});
			},
			prompt : '请输入姓名搜索'
		});
	});
</script>

<table id="deptTxl_txlList_datagrid"></table>

<div id="deptTxl_txlList_toolbar">
	&nbsp;&nbsp;<input id="deptTxl_txlList_searchbox" style="width:200px;" name="content"></input>
</div>