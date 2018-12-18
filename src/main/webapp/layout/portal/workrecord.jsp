<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var dg;
	var recEditDialog;
	$(function() {
		dg = $('#protal_workrecord_datagrid').datagrid({
			singleSelect : true,
			url : '${pageContext.request.contextPath}/workAction!doNotNeedSecurity_getUntreated.action',
			fit : true,
			nowrap : true,
			rownumbers : true,
			border : false,
			sortName : 'createtime',
			sortOrder : 'desc',
			columns : [ [
				{
					field : 'handler',
					title : '处理人',
					width : 70,
					align : 'center'
				},
				{
					field : 'proposer',
					title : '申告人及其联系',
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
					field : 'faultnumber',
					title : '故障号码',
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
					field : 'describe',
					title : '故障描述',
					width : 250,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'createtime',
					title : '记录时间',
					width : 150,
					sortable : true
				},
				{
					field : 'inputer',
					title : '记录人',
					width : 70,
					align : 'center'
				}
			] ],
			rowStyler : function(index, row) {
				return 'color:green';
			},
			onDblClickRow : function(index, row) {
				recEditDialog = $('<div/>').dialog({
					title : '编辑记录',
					width : 700,
					height : 620,
					left : 350,
					top : 20,
					href : '${pageContext.request.contextPath}/workrecord/recEdit.jsp',
					modal : false,
					collapsible : true,
					maximizable : true,
					resizable : true,
					buttons : [ {
						text : '修改',
						iconCls : 'icon-edit',
						handler : function() {
							$('#workrecord_recEdit_Form').form('submit', {
								url : '${pageContext.request.contextPath}/workAction!edit.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										/*console.info(result);*/
										if (result.success) {
											recEditDialog.dialog('close');
											dg.datagrid('load');
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
							recEditDialog.dialog('close');
						}
					} ],
					onClose : function() {
						recEditDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#workrecord_recEdit_Form').form('load', row);
					}
				});
			}
		});
	});
</script>

<table id="protal_workrecord_datagrid"></table>