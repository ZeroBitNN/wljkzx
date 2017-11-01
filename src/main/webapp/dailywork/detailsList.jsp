<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#dailywork_detailsList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/dailyworkDetailsAction!doNotNeedSecurity_datagrid.action',
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			fitColumns : true,
			nowrap : true,
			singleSelect : true,
			pageSize : 40,
			sortName : 'timepoint',
			remoteSort : false,
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'dailydate',
					title : '日期',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						return value.substr(0, 10);
					}
				},
				{
					field : 'timepoint',
					title : '时间',
					width : 50,
					align : 'center',
					sortable : true
				},
				{
					field : 'content',
					title : '工作内容',
					width : 400,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'status',
					title : '执行情况',
					width : 80,
					align : 'center',
					sortable : true
				},
				{
					field : 'recorder',
					title : '执行人',
					width : 80,
					align : 'center'
				},
				{
					field : 'recordtime',
					title : '执行时间',
					width : 120,
					align : 'center'
				},
				{
					field : 'remark',
					title : '备注',
					width : 150,
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
			rowStyler : function(index, row) {
				if (row.status == '未处理') {
					return 'color:red;font-weight:bold;';
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
				var detailsEditDialog;
				detailsEditDialog = $('<div/>').dialog({
					title : '处理',
					width : 700,
					height : 620,
					left : 350,
					top : 40,
					href : '${pageContext.request.contextPath}/dailywork/detailsEdit.jsp',
					modal : true,
					collapsible : true,
					maximizable : true,
					resizable : true,
					buttons : [{
						text : '确认',
						iconCls : 'icon-edit',
						handler : function() {
							$('#dailywork_detailsEdit_Form').form('submit', {
								url : '${pageContext.request.contextPath}/dailyworkDetailsAction!doNotNeedSecurity_edit.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										/*console.info(result);*/
										if (result.success) {
											detailsEditDialog.dialog('close');
											$('#dailywork_detailsList_datagrid').datagrid('load');
										}
										$.messager.show({
											title : '提示：',
											msg : result.msg
										});
									} catch (e) {
										$.messager.alert('警告！', data, 'warning');
									}
								}
							});
						}
					}, {
						text : '取消',
						handler : function() {
							detailsEditDialog.dialog('close');
						}
					}],
					onClose : function() {
						detailsEditDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#dailywork_detailsEdit_Form').form('load', rowData);
					}
				});
			},
			toolbar : '#dailywork_detailsList_toolbar'
		});

	});

	function changeDate(newValue, oldValue) {
		//console.info(newValue);
		$('#dailywork_detailsList_datagrid').datagrid('load', {
			dateStr : newValue
		});
	}
</script>

<table id="dailywork_detailsList_datagrid"></table>

<div id="dailywork_detailsList_toolbar">
	日期：<input name="dailydate" data-options="required:true,onChange:changeDate"
		value="now();" class="easyui-datebox" style="width: 150px;" />
</div>
