<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var datagrid;
	var editRow = undefined;
	$(function() {
		datagrid = $('#admin_cyljgl_datagrid').datagrid({
			singleSelect : true,
			url : '${pageContext.request.contextPath}/linksAction!datagrid.action',
			fit : true,
			border : false,
			fitColumns : true,
			onLoadError : function() {
				$.messager.alert('警告！', '您没有访问此功能的权限！请联系管理员给你赋予相应权限。', 'warning');
			},
			view : groupview,
			groupField : 'category',
			groupFormatter : function(value, rows) {
				return value + ' - ' + rows.length + ' Item(s)';
			},
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					del();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (editRow != undefined) {
						datagrid.datagrid('endEdit', editRow);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
					datagrid.datagrid('rejectChanges');
					editRow = undefined;
				}
			} ],
			onDblClickRow : function(rowIndex, rowData) {
				if (editRow != undefined) {
					datagrid.datagrid('endEdit', editRow);
				}

				if (editRow == undefined) {
					datagrid.datagrid('beginEdit', rowIndex);
					editRow = rowIndex;
					datagrid.datagrid('unselectAll');
				}
			},
			onAfterEdit : function(rowIndex, rowData, changes) {
				var inserted = datagrid.datagrid('getChanges', 'inserted');
				var updated = datagrid.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRow = undefined;
					datagrid.datagrid('unselectAll');
					return;
				}
				var url = '';
				if (inserted.length > 0) {
					url = '${pageContext.request.contextPath}/linksAction!add.action';
				}
				if (updated.length > 0) {
					url = '${pageContext.request.contextPath}/linksAction!edit.action';
				}
				$.ajax({
					url : url,
					type : "POST",
					data : rowData,
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							datagrid.datagrid('acceptChanges');
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
							editRow = undefined;
							datagrid.datagrid('reload');
						} else {
							/*datagrid.datagrid('rejectChanges');*/
							datagrid.datagrid('beginEdit', editRow);
							$.messager.alert('错误', r.msg, 'error');
						}
						datagrid.datagrid('unselectAll');
					},
					error : function(data) {
						$.messager.alert('警告！', data.responseText, 'warning');
					}
				});
			}
		});
	});

	function add() {
		if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
		}

		if (editRow == undefined) {
			datagrid.datagrid('unselectAll');
			var row = {
				id : getUUID()
			};
			datagrid.datagrid('appendRow', row);
			editRow = datagrid.datagrid('getRows').length - 1;
			datagrid.datagrid('selectRow', editRow);
			datagrid.datagrid('beginEdit', editRow);
		}
	}

	function del() {
		if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
			return;
		}
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/linksAction!delete.action',
						type : "POST",
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(r) {
							if (r.success) {
								datagrid.datagrid('load');
								datagrid.datagrid('unselectAll');
								$.messager.show({
									title : '提示',
									msg : '删除成功！'
								});
							}
						},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}

	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			if (editRow != undefined) {
				datagrid.datagrid('endEdit', editRow);
			}

			if (editRow == undefined) {
				editRow = datagrid.datagrid('getRowIndex', rows[0]);
				datagrid.datagrid('beginEdit', editRow);
				datagrid.datagrid('unselectAll');
			}
		} else {
			$.messager.alert('提示', '请选择一项进行修改！', 'error');
		}
	}
</script>

<table id="admin_cyljgl_datagrid">
	<thead>
		<tr>
			<th data-options="field:'id',width:80,hidden:true">ID</th>
			<th data-options="field:'text',width:100,editor:{type:'validatebox',options:{required:true}}">名称</th>
			<th data-options="field:'category',width:50,editor:{type:'validatebox',options:{required:true}}">类别</th>
			<th data-options="field:'url',width:200,editor:{type:'validatebox',options:{required:true}}">链接URL地址</th>
		</tr>
	</thead>
</table>
