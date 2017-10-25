<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#netmanage_wgmm_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/netManageAction!datagrid.action',
			fit : true,
			border : false,
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			fitColumns : true,
			columns : [ [
				{
					field : 'id',
					title : '编号',
					width : 150,
					checkbox : true
				},
				{
					field : 'name',
					title : '系统名称',
					width : 150,
					sortable : true
				},
				{
					field : 'link',
					title : '链接地址',
					width : 150
				},
				{
					field : 'username',
					title : '用户名',
					width : 150
				},
				{
					field : 'pwd',
					title : '密码',
					width : 150
				}
			] ],
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : '#netmanage_wgmm_toolbar'
		});
	});

	function add() {
		var d = $('<div/>').dialog({
			title : '添加',
			width : 500,
			href : '${pageContext.request.contextPath}/netManage/wgmmAdd.jsp',
			modal : true,
			buttons : [ {
				text : '添加',
				handler : function() {
					$('#netManage_wgmm_addForm').form('submit', {
						url : '${pageContext.request.contextPath}/netManageAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								if (result.success) {
									$('#netManage_wgmm_addForm input').val('');
									d.dialog('close');
									$('#netmanage_wgmm_datagrid').datagrid('insertRow', {
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

	function edit() {
		var rows = $('#netmanage_wgmm_datagrid').datagrid('getChecked');
		if (rows.length == 1) {
			var d = $('<div/>').dialog({
				title : '修改',
				width : 500,
				href : '${pageContext.request.contextPath}/netManage/wgmmEdit.jsp',
				modal : true,
				buttons : [ {
					text : '修改',
					handler : function() {
						$('#netManage_wgmm_editForm').form('submit', {
							url : '${pageContext.request.contextPath}/netManageAction!edit.action',
							success : function(data) {
								try {
									var result = jQuery.parseJSON(data);
									if (result.success) {
										d.dialog('close');
										$('#netmanage_wgmm_datagrid').datagrid('updateRow', {
											index : $('#netmanage_wgmm_datagrid').datagrid('getRowIndex', rows[0]),
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
					$('#netManage_wgmm_editForm').form('load', rows[0]);
				}
			});
		} else {
			$.messager.alert('提示：', '请选择一个需要修改的网管系统！', 'info');
		}
	}

	function del() {
		var rows = $('#netmanage_wgmm_datagrid').datagrid('getChecked');
		/*console.info(rows);*/
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除 <b>' + rows[0].name + '</b> ？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/netManageAction!delete.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							$('#netmanage_wgmm_datagrid').datagrid('load');
							$('#netmanage_wgmm_datagrid').datagrid('unselectAll');
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
			$.messager.alert('提示：', '请选择需要删除的系统！', 'info');
		}
	}

	function searchRec() {
		$('#netmanage_wgmm_datagrid').datagrid('load', serializeObject($('#netmanage_wgmm_searchForm')));
	}

	function clearSearch() {
		$('#netmanage_wgmm_toolbar input[name=name]').val('');
		$('#netmanage_wgmm_datagrid').datagrid('load', {});
	}
</script>

<table id="netmanage_wgmm_datagrid"></table>

<div id="netmanage_wgmm_toolbar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float:left;" onclick="add();">添加</a>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" style="float:left;"
		onclick="edit();">修改</a>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" style="float:left;"
		onclick="del();">删除</a>
	<div class="datagrid-btn-separator"></div>
	<form id="netmanage_wgmm_searchForm">
		&nbsp;&nbsp;按系统名称查询：<input name="name" /> <a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-search',plain:true" onclick="searchRec();">查询</a> <a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true" onclick="clearSearch();">重置</a>
	</form>
</div>