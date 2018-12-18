<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table id="workrecord_tbReport_datagrid"></table>

<div id="workrecord_tbReport_toolbar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-myExcel',plain:true"
		style="float:left;" onclick="exportExcel();">导出EXCEL</a>
	<div class="datagrid-btn-separator"></div>
	&nbsp;&nbsp;时间：<input name="createtimeStart" data-options="value:getFirstDay()"
		class="easyui-datebox" editable="false" style="width: 155px;" />&nbsp;&nbsp;至&nbsp;&nbsp;<input
		name="createtimeEnd" value="new Date()" class="easyui-datebox" editable="false"
		style="width: 155px;" /> <a href="#" class="easyui-linkbutton"
		data-options="iconCls:'icon-search',plain:true" onclick="searchTbReport();">查询</a>
</div>

<script type="text/javascript">
	var recTbDatagrid;
	$(function() {
		recTbDatagrid = $('#workrecord_tbReport_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/tbReportAction!doNotNeedSecurity_getReport.action',
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
					title : '名字',
					align : 'center',
					width : 100
				},
				{
					field : 'tbNumber',
					title : '通报数量',
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
			toolbar : '#workrecord_tbReport_toolbar'
		});

	});
	
	function getFirstDay() {
		var date = new Date();
		date.setDate(1);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
	
	function searchTbReport(){
		recTbDatagrid.datagrid('load', {
			createtimeStart : $('#workrecord_tbReport_toolbar input[name=createtimeStart]').val(),
			createtimeEnd : $('#workrecord_tbReport_toolbar input[name=createtimeEnd]').val()
		});
	}
	
	function exportExcel(){}
</script>