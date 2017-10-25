<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	var cylxrDatagrid;
	var cylxrDialog;
	$(function() {
		cylxrDatagrid = $('#admin_cylxrgl_Datagrid').datagrid({
			singleSelect : true,
			title : '常用联系人列表',
			url : '${pageContext.request.contextPath}/contactsAction!doNotNeedSecurity_getDatagrid.action',
			striped : true,
			fit : true,
			border : false,
			fitColumns : true,
			view : groupview,
			groupField : 'dept',
			groupFormatter : function(value, rows) {
				return value + ' - ' + rows.length + ' Item(s)';
			},
			toolbar : '#admin_cylxrgl_toolbar'
		});

		$('#admin_cylxrgl_searchbox').searchbox({
			searcher : function(value, name) {
				//alert(value + "," + name)
				cylxrDatagrid.datagrid('load', {
					name : value
				});
			},
			prompt : '联系人'
		});
	});

	function addContacts() {
		cylxrDialog = $('<div/>').dialog({
			title : '添加联系人',
			width : 700,
			height : 450,
			href : '${pageContext.request.contextPath}/contacts/contacts.jsp',
			modal : true,
			resizable : true,
			buttons : [ {
				text : '添加',
				iconCls : 'icon-addUser',
				handler : function() {
					$('#contacts_contacts_Form').form('submit', {
						url : '${pageContext.request.contextPath}/contactsAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								/*console.info(result);*/
								if (result.success) {
									cylxrDialog.dialog('close');
									cylxrDatagrid.datagrid('load', {});
								}
								$.messager.show({
									title : '提示：',
									msg : result.msg
								});
							} catch (e) {
								$.messager.alert('警告！', data, 'warning');
							} finally {
								$('#contacts_contacts_Form input').val('');
							}
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					cylxrDialog.dialog('close');
				}
			} ],
			onClose : function() {
				cylxrDialog.dialog('destroy');
			},
			onLoad : function() {
				$('#contacts_contacts_Form').form('load', {
					id : getUUID()
				});
			}
		});
	}

	function editContacts() {
		var rows = cylxrDatagrid.datagrid('getChecked');
		if (rows.length == 1) {
			cylxrDialog = $('<div/>').dialog({
				title : '修改联系人',
				width : 700,
				height : 450,
				href : '${pageContext.request.contextPath}/contacts/contacts.jsp',
				modal : true,
				resizable : true,
				buttons : [ {
					text : '确认修改',
					iconCls : 'icon-editUser',
					handler : function() {
						$('#contacts_contacts_Form').form('submit', {
							url : '${pageContext.request.contextPath}/contactsAction!edit.action',
							success : function(data) {
								try {
									var result = jQuery.parseJSON(data);
									/*console.info(result);*/
									if (result.success) {
										cylxrDialog.dialog('close');
										cylxrDatagrid.datagrid('load');
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
						cylxrDialog.dialog('close');
					}
				} ],
				onClose : function() {
					cylxrDialog.dialog('destroy');
				},
				onLoad : function() {
					$('#contacts_contacts_Form').form('load', rows[0]);
				}
			});
		} else {
			$.messager.alert('提示：', '请选择一个要修改的联系人！', 'info');
		}
	}

	function deleteContacts() {
		var rows = cylxrDatagrid.datagrid('getChecked');
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除联系人 <b>' + rows[0].name + '</b> ？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/contactsAction!delete.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							cylxrDatagrid.datagrid('load');
							cylxrDatagrid.datagrid('unselectAll');
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
			$.messager.alert('提示：', '请选择要删除的联系人！', 'info');
		}
	}
</script>

<table id="admin_cylxrgl_Datagrid">
	<thead>
		<tr>
			<th data-options="field:'id',width:80,checkbox:true">ID</th>
			<th data-options="field:'name',width:80">联系人</th>
			<th data-options="field:'phonenumber',width:100">联系号码</th>
			<th data-options="field:'dept',width:80">所属部门</th>
		</tr>
	</thead>
</table>

<div id="admin_cylxrgl_toolbar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-addUser',plain:true" style="float:left;"
		onclick="addContacts();">添加联系人</a>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-editUser',plain:true" style="float:left;"
		onclick="editContacts();">修改联系人</a>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-removeUser',plain:true" style="float:left;"
		onclick="deleteContacts();">删除联系人</a>
	<div class="datagrid-btn-separator"></div>
	&nbsp;&nbsp;<input id="admin_cylxrgl_searchbox" style="width:200px;" name="text"></input>
</div>