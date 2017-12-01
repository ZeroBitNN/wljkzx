<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#portal_dailyworkDetails_datagrid').datagrid({
			singleSelect : true,
			url : '${pageContext.request.contextPath}/dailyworkDetailsAction!doNotNeedSecurity_datagrid.action',
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			remoteSort : false,
			sortName : 'timepoint',
			multiSort : true,
			columns : [[
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
					field : 'status',
					title : '执行情况',
					width : 70,
					align : 'center',
					sortable : true,
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
			onDblClickRow : function(index, row) {
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
											$('#portal_dailyworkDetails_datagrid').datagrid('load');
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
						$('#dailywork_detailsEdit_Form').form('load', row);
					}
				});
			}
		});
	});
</script>

<table id="portal_dailyworkDetails_datagrid"></table>
