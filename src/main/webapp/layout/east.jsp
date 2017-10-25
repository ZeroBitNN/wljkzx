<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	var calendar;
	var contactsDatagrid;
	$(function() {
		calendar = $('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			firstDay : 1,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});

		contactsDatagrid = $('#layout_east_contactsDatagrid').datagrid({
			singleSelect : true,
			url : '${pageContext.request.contextPath}/contactsAction!doNotNeedSecurity_getDatagrid.action',
			fit : true,
			title : '常用联系人',
			striped : true,
			border : false,
			fitColumns : true,
			view : groupview,
			groupField : 'dept',
			groupFormatter : function(value, rows) {
				return value + ' - ' + rows.length + ' Item(s)';
			},
			columns : [ [
				{
					field : 'id',
					title : '编号',
					width : 150,
					hidden : true
				},
				{
					field : 'name',
					title : '联系人',
					width : 70,
					formatter : function(value, row, index) {
						return '<span title="' + value + '">' + value + '</span>';
					}
				},
				{
					field : 'phonenumber',
					title : '联系号码',
					width : 150,
					formatter : function(value, row, index) {
						return '<span title="' + value + '">' + value + '</span>';
					}
				},
				{
					field : 'dept',
					title : '部门',
					width : 150,
					hidden : true
				}
			] ],
			toolbar : '#layout_east_toolbar'
		});

		$('#layout_east_searchbox').searchbox({
			searcher : function(value, name) {
				//alert(value + "," + name)
				contactsDatagrid.datagrid('load', {
					name : value
				});
			},
			prompt : '联系人'
		});
	});
</script>
<div class="easyui-layout" fit="true" border="false">
	<div region="north" border="false" style="height:180px;overflow: hidden;">
		<div id="layout_east_calendar"></div>
	</div>
	<div region="center" border="false" style="overflow: hidden;">
		<table id="layout_east_contactsDatagrid"></table>
	</div>
</div>

<div id="layout_east_toolbar">
	<input id="layout_east_searchbox" style="width:100%" name="text"></input>
</div>