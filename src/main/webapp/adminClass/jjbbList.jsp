<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#adminClass_jjbbList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/deptJjbbAction!doNotNeedSecurity_Datagrid.action',
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
					field : 'title',
					title : '标题',
					width : 500
				},
				{
					field : 'serialNum',
					title : '工单编号',
					width : 200,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'datetime',
					title : '日期',
					width : 150
				},
				{
					field : 'notifier',
					title : '发起通知人',
					width : 200,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'type',
					title : '类型',
					width : 80
				},
				{
					field : 'domain',
					title : '专业',
					width : 80
				},
				{
					field : 'source',
					title : '通知来源',
					width : 0,
					hidden : true
				},
				{
					field : 'comeFrom',
					title : '交办人',
					width : 0,
					hidden : true
				},
				{
					field : 'content',
					title : '内容',
					width : 0,
					hidden : true
				},
				{
					field : 'attachment',
					title : '附件',
					width : 0,
					hidden : true
				}
			]],
			onDblClickRow : function(rowIndex, rowData) {
				var jjbbDetailsDialog;
				jjbbDetailsDialog = $('<div/>').dialog({
					title : '交接班详情',
					width : 900,
					height : 680,
					left : 300,
					top : 50,
					href : '${pageContext.request.contextPath}/adminClass/jjbbDetails.jsp',
					modal : false,
					collapsible : true,
					maximizable : true,
					resizable : true,
					buttons : [{
						text : '关闭',
						handler : function() {
							jjbbDetailsDialog.dialog('close');
						}
					}],
					onClose : function() {
						jjbbDetailsDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#adminClass_jjbbDetails_Form').form('load', rowData);
					}
				});
			},
			toolbar : '#adminClass_jjbbList_toolbar'
		});
	});

	function searchJjbb() {
		$('#adminClass_jjbbList_datagrid').datagrid('load', serializeObject($('#adminClass_jjbbList_searchForm')));
	}

	function clearSearchJjbb() {
		$('#adminClass_jjbbList_toolbar input').val('');
		$('#adminClass_jjbbList_datagrid').datagrid('load', {});
	}
</script>

<table id="adminClass_jjbbList_datagrid"></table>

<div id="adminClass_jjbbList_toolbar">
	<form id="adminClass_jjbbList_searchForm">
		&nbsp;&nbsp;标题：<input name="title" />&nbsp;&nbsp;内容：<input name="content" /> <a href="#"
			class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchJjbb();">查询</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true"
			onclick="clearSearchJjbb();">重置</a>
	</form>
</div>