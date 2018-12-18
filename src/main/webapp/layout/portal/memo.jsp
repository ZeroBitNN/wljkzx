<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var dg;
	$(function() {
		dg = $('#protal_memo_datagrid').datagrid({
			singleSelect : true,
			url : '${pageContext.request.contextPath}/memoAction!doNotNeedSecurity_getUnfinish.action',
			fit : true,
			nowrap : true,
			border : false,
			view : groupview,
			groupField : 'memotype',
			groupFormatter : function(value, rows) {
				return value + ' - ' + rows.length + ' Item(s)';
			},
			columns : [ [
				{
					field : 'memoto',
					title : '接班人',
					width : 70,
					align : 'center'
				},
				{
					field : 'content',
					title : '值班记事',
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
					field : 'othercontent',
					title : '其他记事',
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
					field : 'leaveover',
					title : '遗留问题',
					width : 250,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				}
			] ],
			rowStyler : function(index, row) {
				if (row.memotype == '领导交办') {
					return 'color:red;font-weight:bold;';
				} else {
					return 'color:green';
				}
			},
			onDblClickRow : function(index, row) {
				//console.info(index);
				//console.info(row);
				var memoDialog = $('<div/>').dialog({
					title : '交接班日志',
					width : 800,
					height : 600,
					href : '${pageContext.request.contextPath}/memo/memoEdit.jsp',
					collapsible : true,
					resizable : true,
					left : 300,
					top : 20,
					maximizable : true,
					buttons : [ {
						text : '修改日志',
						handler : function() {
							$('#memo_memoEdit_editForm').form('submit', {
								url : '${pageContext.request.contextPath}/memoAction!edit.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										if (result.success) {
											memoDialog.dialog('close');
											dg.datagrid('load', {});
										}
										$.messager.show({
											title : '提示：',
											msg : result.msg
										});
									} catch (e) {
										$.messager.alert('警告！', data, 'warning');
									}
								},
								onSubmit : function() {
									return $(this).form('enableValidation').form('validate');
								}
							});
						}
					}, {
						text : '取消',
						handler : function() {
							memoDialog.dialog('close');
						}
					} ],
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad : function() {
						$('#memo_memoEdit_editForm').form('load', row);
					}
				});
			}
		});
	});
</script>

<table id="protal_memo_datagrid"></table>