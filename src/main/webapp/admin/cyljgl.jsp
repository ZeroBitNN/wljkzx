<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var editRowCylj = undefined;
	$(function() {
		$('#admin_cyljgl_datagrid').datagrid({
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
					addCylj();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					delCylj();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					editCylj();
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (editRowCylj != undefined) {
						$('#admin_cyljgl_datagrid').datagrid('endEdit', editRowCylj);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					$('#admin_cyljgl_datagrid').datagrid('unselectAll');
					$('#admin_cyljgl_datagrid').datagrid('rejectChanges');
					editRowCylj = undefined;
				}
			} ],
			onDblClickRow : function(rowIndex, rowData) {
				if (editRowCylj != undefined) {
					$('#admin_cyljgl_datagrid').datagrid('endEdit', editRowCylj);
				}

				if (editRowCylj == undefined) {
					$('#admin_cyljgl_datagrid').datagrid('beginEdit', rowIndex);
					editRowCylj = rowIndex;
					$('#admin_cyljgl_datagrid').datagrid('unselectAll');
				}
			},
			onAfterEdit : function(rowIndex, rowData, changes) {
				var inserted = $('#admin_cyljgl_datagrid').datagrid('getChanges', 'inserted');
				var updated = $('#admin_cyljgl_datagrid').datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRowCylj = undefined;
					$('#admin_cyljgl_datagrid').datagrid('unselectAll');
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
							$('#admin_cyljgl_datagrid').datagrid('acceptChanges');
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
							editRowCylj = undefined;
							$('#admin_cyljgl_datagrid').datagrid('reload');
						} else {
							/*$('#admin_cyljgl_datagrid').datagrid('rejectChanges');*/
							$('#admin_cyljgl_datagrid').datagrid('beginEdit', editRowCylj);
							$.messager.alert('错误', r.msg, 'error');
						}
						$('#admin_cyljgl_datagrid').datagrid('unselectAll');
					},
					error : function(data) {
						$.messager.alert('警告！', data.responseText, 'warning');
					}
				});
			}
		});
	});

	function addCylj() {
		if (editRowCylj != undefined) {
			$('#admin_cyljgl_datagrid').datagrid('endEdit', editRowCylj);
		}

		if (editRowCylj == undefined) {
			$('#admin_cyljgl_datagrid').datagrid('unselectAll');
			var row = {
				id : getUUID()
			};
			$('#admin_cyljgl_datagrid').datagrid('appendRow', row);
			editRowCylj = $('#admin_cyljgl_datagrid').datagrid('getRows').length - 1;
			$('#admin_cyljgl_datagrid').datagrid('selectRow', editRowCylj);
			$('#admin_cyljgl_datagrid').datagrid('beginEdit', editRowCylj);
		}
	}

	function delCylj() {
		if (editRowCylj != undefined) {
			$('#admin_cyljgl_datagrid').datagrid('endEdit', editRowCylj);
			return;
		}
		var rows = $('#admin_cyljgl_datagrid').datagrid('getSelections');
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
								$('#admin_cyljgl_datagrid').datagrid('load');
								$('#admin_cyljgl_datagrid').datagrid('unselectAll');
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

	function editCylj() {
		var rows = $('#admin_cyljgl_datagrid').datagrid('getSelections');
		if (rows.length == 1) {
			if (editRowCylj != undefined) {
				$('#admin_cyljgl_datagrid').datagrid('endEdit', editRowCylj);
			}

			if (editRowCylj == undefined) {
				editRowCylj = $('#admin_cyljgl_datagrid').datagrid('getRowIndex', rows[0]);
				$('#admin_cyljgl_datagrid').datagrid('beginEdit', editRowCylj);
				$('#admin_cyljgl_datagrid').datagrid('unselectAll');
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
