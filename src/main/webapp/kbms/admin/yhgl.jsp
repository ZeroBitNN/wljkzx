<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#kbms_admin_yhgl_dg').datagrid({
			url : '${pageContext.request.contextPath}/kbmsUserAction!datagrid.action',
			fit : true,
			border : false,
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			fitColumns : true,
			pageSize : 30,
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 150,
					checkbox : true
				},
				{
					field : 'username',
					title : '用户名',
					width : 150,
					sortable : true
				},
				{
					field : 'pwd',
					title : '密码',
					width : 150,
					formatter : function(value, row, index) {
						return "******************";
					}
				},
				{
					field : 'createtime',
					title : '创建时间',
					width : 150
				},
				{
					field : 'modifytime',
					title : '修改时间',
					width : 150
				}
			]],
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : '#kbms_admin_yhgl_toolbar'
		});
	});

	function searchUser() {
		$('#kbms_admin_yhgl_dg').datagrid('load', serializeObject($('#kbms_admin_yhgl_searchForm')));
	}

	function clearSearch() {
		$('#kbms_admin_yhgl_toolbar input[name=username]').val('');
		$('#kbms_admin_yhgl_dg').datagrid('load', {});
	}

	function addUser() {
		var d = $('<div/>').dialog({
			title : '添加用户',
			width : 500,
			top : 250,
			href : '${pageContext.request.contextPath}/kbms/admin/yhglAdd.jsp',
			modal : true,
			buttons : [{
				text : '添加',
				iconCls : 'icon-addUser',
				handler : function() {
					$('#kbms_admin_yhgl_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/kbmsUserAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								/*console.info(result);*/
								if (result.success) {
									d.dialog('close');
									$('#kbms_admin_yhgl_dg').datagrid('insertRow', {
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
								$('#kbms_admin_yhgl_addForm input').val('');
							}
						}
					});
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}

	function editUser() {
		var rows = $('#kbms_admin_yhgl_dg').datagrid('getChecked');
		if (rows.length == 1) {
			var d = $('<div/>').dialog({
				title : '修改用户',
				width : 500,
				top : 200,
				href : '${pageContext.request.contextPath}/kbms/admin/yhglEdit.jsp',
				modal : true,
				buttons : [{
					text : '修改',
					handler : function() {
						$('#kbms_admin_yhglEdit_editForm').form('submit', {
							url : '${pageContext.request.contextPath}/kbmsUserAction!edit.action',
							success : function(data) {
								//console.info(data);
								try {
									var result = jQuery.parseJSON(data);
									if (result.success) {
										d.dialog('close');
										$('#kbms_admin_yhgl_dg').datagrid('updateRow', {
											index : $('#kbms_admin_yhgl_dg').datagrid('getRowIndex', rows[0]),
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
				}],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad : function() {
					$('#kbms_admin_yhglEdit_editForm').form('load', rows[0]);
					$('#kbms_admin_yhglEdit_editForm input[name=modifytime]').val(date2str(new Date(), "yyyy-MM-d h:m:s"));
				}
			});
		} else {
			$.messager.alert('提示：', '请选择一个要修改的用户！', 'info');
		}

	}

	function deleteUser() {
		var rows = $('#kbms_admin_yhgl_dg').datagrid('getChecked');
		//console.info(rows);
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除用户 <b>' + rows[0].username + '</b> ？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/kbmsUserAction!delete.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							$('#kbms_admin_yhgl_dg').datagrid('load');
							$('#kbms_admin_yhgl_dg').datagrid('unselectAll');
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
			$.messager.alert('提示：', '请选择要删除的用户！', 'info');
		}
	}

	function date2str(x, y) {
		var z = {
			y : x.getFullYear(),
			M : x.getMonth() + 1,
			d : x.getDate(),
			h : x.getHours(),
			m : x.getMinutes(),
			s : x.getSeconds()
		};
		return y.replace(/(y+|M+|d+|h+|m+|s+)/g, function(v) {
			return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-(v.length > 2 ? v.length : 2))
		});
	}
</script>

<table id="kbms_admin_yhgl_dg"></table>

<div id="kbms_admin_yhgl_toolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-addUser',plain:true" style="float:left;" onclick="addUser();">添加用户</a>
	<div class="datagrid-btn-separator"></div>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-editUser',plain:true" style="float:left;" onclick="editUser();">修改用户</a>
	<div class="datagrid-btn-separator"></div>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-removeUser',plain:true" style="float:left;" onclick="deleteUser();">删除用户</a>
	<div class="datagrid-btn-separator"></div>
	<form id="kbms_admin_yhgl_searchForm">
		&nbsp;&nbsp;按用户名查询：<input name="username" /> <a href="javascript:void(0)"
			class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUser();">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true" onclick="clearSearch();">重置</a>
	</form>
</div>
