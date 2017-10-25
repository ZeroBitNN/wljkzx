<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#admin_jsgl_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/rolesAction!doNotNeedSecurity_datagrid.action',
			fit : true,
			pagination : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			nowrap : false,
			border : false,
			fitColumns : true,
			toolbar : [ {
				text : '增加角色',
				iconCls : 'icon-add',
				handler : function() {
					addRole();
				}
			}, '-', {
				text : '修改角色',
				iconCls : 'icon-edit',
				handler : function() {
					editRole();
				}
			}, '-', {
				text : '删除角色',
				iconCls : 'icon-remove',
				handler : function() {
					var rows = $('#admin_jsgl_datagrid').datagrid('getChecked');
					/*console.info(rows);*/
					if (rows.length > 0) {
						$.messager.confirm('确认', '是否确认删除角色 <b>' + rows[0].name + '</b> ？', function(r) {
							if (r) {
								$.ajax({
									url : '${pageContext.request.contextPath}/rolesAction!delete.action',
									data : {
										id : rows[0].id
									},
									dataType : 'json',
									success : function(data) {
										$('#admin_jsgl_datagrid').datagrid('load');
										$('#admin_jsgl_datagrid').datagrid('unselectAll');
										$.messager.show({
											title : '提示',
											msg : data.msg
										});
									},
									error : function(data) {
										$.messager.alert('警告！', data.responseText, 'warning');
									}
								});
							}
						});
					} else {
						$.messager.alert('提示：', '请选择需要删除的角色！', 'info');
					}
				}
			} ]
		});
	});

	function addRole() {
		var d = $('<div/>').dialog({
			title : '增加角色',
			width : 500,
			href : '${pageContext.request.contextPath}/admin/jsglAdd.jsp',
			modal : true,
			buttons : [ {
				text : '新增',
				handler : function() {
					$('#admin_jsglAdd_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/rolesAction!add.action',
						success : function(data) {
							//console.info(data);
							try {
								var result = jQuery.parseJSON(data);
								if (result.success) {
									d.dialog('close');
									$('#admin_jsgl_datagrid').datagrid('insertRow', {
										index : 0,
										row : result.obj
									});
								}
								$.messager.show({
									title : '提示：',
									msg : result.msg
								});
							} catch (e) {
								$.messager.alert('警告！', data, 'warning');
							} finally {
								$('#admin_jsglAdd_addForm input').val('');
							}
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}

	function editRole() {
		var rows = $('#admin_jsgl_datagrid').datagrid('getChecked');
		if (rows.length == 1) {
			var d = $('<div/>').dialog({
				title : '修改角色',
				queryParams : {
					id : "'" + rows[0].id + "'",
					name : "'" + rows[0].name + "'",
					resourcesIds : "'" + rows[0].resourcesIds + "'"
				},
				width : 500,
				href : '${pageContext.request.contextPath}/admin/jsglEdit.jsp',
				modal : true,
				buttons : [ {
					text : '修改',
					handler : function() {
						$('#admin_jsglEdit_editForm').form('submit', {
							url : '${pageContext.request.contextPath}/rolesAction!edit.action',
							success : function(data) {
								//console.info(data);
								try {
									var result = jQuery.parseJSON(data);
									if (result.success) {
										d.dialog('close');
										$('#admin_jsgl_datagrid').datagrid('updateRow', {
											index : $('#admin_jsgl_datagrid').datagrid('getRowIndex', rows[0]),
											row : result.obj
										});
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
				} ],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad : function() {
					//console.info(rows[0]);
					$('#admin_jsglEdit_editForm').form('load', {
						id : rows[0].id,
						name : rows[0].name
					});
				}
			});
		} else {
			$.messager.alert('提示：', '请选择一个要修改的角色！', 'info');
		}
	}
</script>

<table id="admin_jsgl_datagrid" class="easyui-datagrid">
	<thead>
		<tr>
			<th data-options="field:'id',width:10,checkbox:true">ID</th>
			<th data-options="field:'name',width:60">角色名称</th>
			<th data-options="field:'resourcesIds',width:100">角色权限</th>
		</tr>
	</thead>
</table>