<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	var recDatagrid;
	$(function() {
		recDatagrid = $('#workrecord_recReport_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/reportAction!doNotNeedSecurity_getReport.action',
			fit : true,
			border : false,
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			showFooter : true,
			columns : [[
				{
					field : 'name',
					title : '处理人',
					align : 'center',
					width : 100
				},
				{
					field : 'untreated',
					title : '未处理',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'processed',
					title : '已处理',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'sum',
					title : '总计',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				}
			]],
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : '#workrecord_recReport_toolbar'
		});

	//var firstDay = getFirstDay();
	//console.info(firstDay);
	});

	function getFirstDay() {
		var date = new Date();
		date.setDate(1);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}

	function searchReport() {
		//dg.datagrid('load', serializeObject($('#memo_memoList_searchForm')));
		recDatagrid.datagrid('load', {
			createtimeStart : $('#workrecord_recReport_toolbar input[name=createtimeStart]').val(),
			createtimeEnd : $('#workrecord_recReport_toolbar input[name=createtimeEnd]').val()
		});
	}

	function exportExcel() {
		var timeStart = $("input[name='createtimeStart']").val();
		var timeEnd = $("input[name='createtimeEnd']").val();
		//?id=77&nameid=2905210001&page=1
		var timeLimit = "?timeStart=" + timeStart + "&timeEnd=" + timeEnd;
		window.open("${pageContext.request.contextPath}/reportAction!doNotNeedSecurity_exportExcel.action" + timeLimit, "_blank");
	}
</script>

<table id="workrecord_recReport_datagrid"></table>

<div id="workrecord_recReport_toolbar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-myExcel',plain:true"
		style="float:left;" onclick="exportExcel();">导出EXCEL</a>
	<div class="datagrid-btn-separator"></div>
	&nbsp;&nbsp;时间：<input name="createtimeStart" data-options="value:getFirstDay()"
		class="easyui-datebox" editable="false" style="width: 155px;" />&nbsp;&nbsp;至&nbsp;&nbsp;<input
		name="createtimeEnd" value="new Date()" class="easyui-datebox" editable="false"
		style="width: 155px;" /> <a href="#" class="easyui-linkbutton"
		data-options="iconCls:'icon-search',plain:true" onclick="searchReport();">查询</a>
</div>