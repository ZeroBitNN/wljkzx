<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.SessionInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>

<script type="text/javascript">
	var guaranteeAddDialog;
	var currentUser;
	var guaranteeEditDialog;
	$(function() {
		currentUser = "<%=sessionInfo.getUser().getUsername()%>";
		$('#guarantee_guaranteeList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/guaranteeAction!doNotNeedSecurity_getAllDg.action',
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			nowrap : true,
			fitColumns : true,
			singleSelect : true,
			sortName : 'timestart',
			sortOrder : 'desc',
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'title',
					title : '标题',
					width : 300,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'content',
					title : '保障内容',
					width : 370,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'timestart',
					title : '保障开始时间',
					width : 150,
					sortable : true
				},
				{
					field : 'timeend',
					title : '保障结束时间',
					width : 150,
					sortable : true
				},
				{
					field : 'recorder',
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
				if (row.status == '保障中') {
					return 'color:green;';
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
				guaranteeEditDialog = $('<div/>').dialog({
					title : '编辑',
					width : 800,
					height : 720,
					left : 350,
					top : 20,
					href : '${pageContext.request.contextPath}/guarantee/guaranteeAdd.jsp',
					queryParams : {
						gid : rowData.id
					},
					modal : false,
					collapsible : true,
					maximizable : true,
					resizable : true,
					buttons : [{
						text : '修改',
						iconCls : 'icon-edit',
						handler : function() {
							$('#guarantee_guaranteeAdd_Form').form('submit', {
								url : '${pageContext.request.contextPath}/guaranteeAction!edit.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										/*console.info(result);*/
										if (result.success) {
											guaranteeEditDialog.dialog('close');
											$('#guarantee_guaranteeList_datagrid').datagrid('updateRow', {
												index : rowIndex,
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
					}, {
						text : '取消',
						handler : function() {
							guaranteeEditDialog.dialog('close');
						}
					}],
					onClose : function() {
						guaranteeEditDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#guarantee_guaranteeAdd_Form').form('load', {
							id : rowData.id,
							title : rowData.title,
							content : rowData.content,
							requirement : rowData.requirement,
							noticetemplate : rowData.noticetemplate,
							recorder : currentUser,
							timestart : rowData.timestart,
							timeend : rowData.timeend,
							timepoint : rowData.timepoint,
							status : rowData.status
						});
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
					add();
				}
			}, '-', {
				text : '结束保障',
				iconCls : 'icon-myStop',
				handler : function() {
					_stop();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					del();
				}
			}]
		});

		$('#guarantee_guaranteeList_handlerCombo').combobox({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
			valueField : 'id',
			textField : 'username'
		});
	});

	function _search() {
		$('#guarantee_guaranteeList_datagrid').datagrid('load', serializeObject($('#guarantee_guaranteeList_searchForm')));
	}

	function cleanSearch() {
		$('#guarantee_guaranteeList_searchForm').form('reset');
		$('#guarantee_guaranteeList_datagrid').datagrid('load', {});
	}

	function add() {
		var gid = getUUID();
		guaranteeAddDialog = $('<div/>').dialog({
			title : '添加',
			width : 800,
			height : 720,
			left : 350,
			top : 20,
			href : '${pageContext.request.contextPath}/guarantee/guaranteeAdd.jsp',
			modal : false,
			collapsible : true,
			maximizable : true,
			resizable : true,
			buttons : [{
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					$('#guarantee_guaranteeAdd_Form').form('submit', {
						url : '${pageContext.request.contextPath}/guaranteeAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								/*console.info(result);*/
								if (result.success) {
									guaranteeAddDialog.dialog('close');
									$('#guarantee_guaranteeList_datagrid').datagrid('insertRow', {
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
								$('#guarantee_guaranteeAdd_Form input').val('');
							}
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					guaranteeAddDialog.dialog('close');
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/attachmentAction!doNotNeedSecurity_delByRelatedid.action",
						data : {
							relatedid : gid,
						},
						dataType : 'json',
						success : function(data) {},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});
				}
			}],
			onClose : function() {
				guaranteeAddDialog.dialog('destroy');
			},
			onLoad : function() {
				$('#guarantee_guaranteeAdd_Form').form('load', {
					id : gid,
					recorder : currentUser
				});
			}
		});
	}

	function del() {
		var rows = $('#guarantee_guaranteeList_datagrid').datagrid('getChecked');
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除 <b>' + rows[0].title + '</b> ？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/guaranteeAction!delete.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							$('#guarantee_guaranteeList_datagrid').datagrid('load');
							$('#guarantee_guaranteeList_datagrid').datagrid('unselectAll');
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
			$.messager.alert('提示：', '请选择要删除的保障记录！', 'info');
		}
	}

	function _stop() {
		var rows = $('#guarantee_guaranteeList_datagrid').datagrid('getChecked');
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认结束 <b>' + rows[0].title + '</b> ？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/guaranteeAction!stop.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							$('#guarantee_guaranteeList_datagrid').datagrid('load');
							$('#guarantee_guaranteeList_datagrid').datagrid('unselectAll');
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
			$.messager.alert('提示：', '请选择要结束的保障！', 'info');
		}
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div region="north" border="false" title="查询条件" style="height: 121px;" align="left">
		<form id="guarantee_guaranteeList_searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<th>标题</th>
					<td><input name="title" style="width:315px;" /></td>
					<th>记录人</th>
					<td><input id="guarantee_guaranteeList_handlerCombo" class="easyui-combobox"
						name="recorder" data-options="editable:false" style="width:155px;"></td>
				</tr>
				<tr>
					<th>关键字</th>
					<td><input name="keywords" style="width:315px;" /></td>
					<th>保障状态</th>
					<td><select class="easyui-combobox" name="status" style="width:155px;"
						data-options="editable:false">
							<option value="">---请选择---</option>
							<option value="保障中">保障中</option>
							<option value="已结束">已结束</option>
					</select></td>
				</tr>
				<tr>
					<th>保障开始时间</th>
					<td><input name="timestart" class="easyui-datebox" editable="false" style="width: 155px;" />&nbsp;结束时间&nbsp;<input
						name="timeend" class="easyui-datebox" editable="false" style="width: 155px;" /></td>
					<th></th>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">查询</a>&nbsp;&nbsp;<a
						href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div region="center" border="true" title="保障列表">
		<table id="guarantee_guaranteeList_datagrid"></table>
	</div>
</div>