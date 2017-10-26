<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.SessionInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>

<script type="text/javascript">
	var currentUser;
	var recAddDialog;
	var recEditDialog;
	$(function() {
		currentUser = "<%=sessionInfo.getUser().getUsername()%>";
		$('#workrecord_recList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/workAction!doNotNeedSecurity_getAllDg.action',
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			nowrap : true,
			singleSelect : true,
			sortName : 'createtime',
			sortOrder : 'desc',
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
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
					width : 270,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'situation',
					title : '故障处理情况',
					width : 270,
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
					field : 'modifytime',
					title : '修改时间',
					width : 150,
					sortable : true
				},
				{
					field : 'handler',
					title : '处理人',
					width : 70,
					sortable : true
				},
				{
					field : 'inputer',
					title : '记录人',
					width : 70,
					sortable : true
				},
				{
					field : 'status',
					title : '状态',
					width : 60,
					align : 'center',
					sortable : true
				}
			]],
			rowStyler : function(index, row) {
				if (row.status == '未处理') {
					return 'color:green;';
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
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
					buttons : [{
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
											$('#workrecord_recList_datagrid').datagrid('load');
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
					}],
					onClose : function() {
						recEditDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#workrecord_recEdit_Form').form('load', rowData);
					}
				});
			},
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : [{
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					addWorkrecord();
				}
			}, '-', {
				text : '转派',
				iconCls : 'icon-mySend',
				handler : function() {
					sendWorkrecord();
				}
			}, '-', {
				text : '修改[只能修改本人记录]',
				iconCls : 'icon-edit',
				handler : function() {
					editWorkrecord();
				}
			}, '-', {
				text : '删除[只能删除本人记录]',
				iconCls : 'icon-remove',
				handler : function() {
					delWorkrecord();
				}
			}]
		});

		$('#workrecord_recList_handlerCombo').combobox({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
			valueField : 'id',
			textField : 'username'
		});
	});

	function sendWorkrecord() {
		var rows = $('#workrecord_recList_datagrid').datagrid('getChecked');
		if (rows.length == 1) {
			if (rows[0].handler != currentUser) {
				$.messager.alert('警告！', '只能转派本人工单。', 'warning');
				return;
			}
			recSendDialog = $('<div/>').dialog({
				title : '转派至：',
				width : 500,
				top : 100,
				href : '${pageContext.request.contextPath}/workrecord/recSend.jsp',
				modal : true,
				collapsible : true,
				maximizable : true,
				resizable : true,
				buttons : [{
					text : '确定',
					iconCls : 'icon-mySend',
					handler : function() {
						$('#workrecord_recSend_Form').form('submit', {
							url : '${pageContext.request.contextPath}/workAction!doNotNeedSecurity_send.action',
							success : function(data) {
								try {
									var result = jQuery.parseJSON(data);
									/*console.info(result);*/
									if (result.success) {
										recSendDialog.dialog('close');
										$('#workrecord_recList_datagrid').datagrid('load');
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
						recSendDialog.dialog('close');
					}
				}],
				onClose : function() {
					recSendDialog.dialog('destroy');
				},
				onLoad : function() {
					$('#workrecord_recSend_Form').form('load', rows[0]);
				}
			});
		} else {
			$.messager.alert('提示：', '请选择一条要转派的记录！', 'info');
		}
	}

	function searchWorkrecord() {
		$('#workrecord_recList_datagrid').datagrid('load', serializeObject($('#workrecord_recList_searchForm')));
	}

	function cleanSearchWorkrecord() {
		$('#workrecord_recList_searchForm').form('reset');
		$('#workrecord_recList_datagrid').datagrid('load', {});
	}

	function addWorkrecord() {
		recAddDialog = $('<div/>').dialog({
			title : '添加记录',
			width : 700,
			height : 620,
			left : 350,
			top : 20,
			href : '${pageContext.request.contextPath}/workrecord/recAdd.jsp',
			modal : false,
			collapsible : true,
			maximizable : true,
			resizable : true,
			buttons : [{
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					$('#workrecord_recAdd_Form').form('submit', {
						url : '${pageContext.request.contextPath}/workAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								/*console.info(result);*/
								if (result.success) {
									recAddDialog.dialog('close');
									$('#workrecord_recList_datagrid').datagrid('load', {});
								}
								$.messager.show({
									title : '提示：',
									msg : result.msg
								});
							} catch (e) {
								$.messager.alert('警告！', data, 'warning');
							} finally {
								$('#workrecord_recAdd_Form input').val('');
							}
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					recAddDialog.dialog('close');
				}
			}],
			onClose : function() {
				recAddDialog.dialog('destroy');
			},
			onLoad : function() {
				$('#workrecord_recAdd_Form').form('load', {
					id : getUUID(),
					inputer : currentUser,
					handler : currentUser
				});
			}
		});
	}

	function editWorkrecord() {
		var rows = $('#workrecord_recList_datagrid').datagrid('getChecked');
		if (rows.length == 1) {
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
				buttons : [{
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
										$('#workrecord_recList_datagrid').datagrid('load');
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
				}],
				onClose : function() {
					recEditDialog.dialog('destroy');
				},
				onLoad : function() {
					$('#workrecord_recEdit_Form').form('load', rows[0]);
				}
			});
		} else {
			$.messager.alert('提示：', '请选择一个要修改的记录！', 'info');
		}
	}

	function delWorkrecord() {
		var rows = $('#workrecord_recList_datagrid').datagrid('getChecked');
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除所选记录？', function(r) {
				if (r) {
					$.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/workAction!delete.action',
						data : {
							id : rows[0].id,
							handler : rows[0].handler
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$('#workrecord_recList_datagrid').datagrid('load');
							}
							$('#workrecord_recList_datagrid').datagrid('unselectAll');
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
			$.messager.alert('提示：', '请选择要删除的记录！', 'info');
		}
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div region="north" border="false" title="查询条件" style="height: 150px;" align="left">
		<form id="workrecord_recList_searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<th>申告人及其联系</th>
					<td><input name="proposer" style="width:315px;" /></td>
					<th>处理状态</th>
					<td><select class="easyui-combobox" name="status" data-options="editable:false">
							<option value="">---请选择---</option>
							<option value="未处理">未处理</option>
							<option value="已处理">已处理</option>
					</select></td>
				</tr>
				<tr>
					<th>故障号码</th>
					<td><input name="faultnumber" style="width:315px;" /></td>
					<th>处理人</th>
					<td><input id="workrecord_recList_handlerCombo" class="easyui-combobox" name="handler"
						data-options="editable:false"></td>
				</tr>
				<tr>
					<th>故障描述</th>
					<td><input name="describe" style="width:315px;" /></td>
					<th>故障处理情况</th>
					<td><input name="situation" style="width:315px;" /></td>
				</tr>
				<tr>
					<th>记录时间</th>
					<td><input name="createtimeStart" class="easyui-datetimebox" editable="false"
						style="width: 155px;" />至<input name="createtimeEnd" class="easyui-datetimebox"
						editable="false" style="width: 155px;" /></td>
					<th>修改时间</th>
					<td><input name="modifytimeStart" class="easyui-datetimebox" editable="false"
						style="width: 155px;" />至<input name="modifytimeEnd" class="easyui-datetimebox"
						editable="false" style="width: 155px;" />&nbsp;&nbsp;<a href="javascript:void(0);"
						class="easyui-linkbutton" onclick="searchWorkrecord();">查询</a>&nbsp;&nbsp;<a
						href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearchWorkrecord();">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div region="center" border="false">
		<table id="workrecord_recList_datagrid"></table>
	</div>
</div>