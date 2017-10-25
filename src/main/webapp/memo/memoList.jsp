<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.SessionInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String searchType = request.getParameter("searchType");
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>

<script type="text/javascript" charset="utf-8">
	var searchTypeVal;
	var dg;
	var editRow = undefined;
	$(function() {
		searchTypeVal = "<%=searchType%>";
		var url;
		//console.info(searchTypeVal);
		switch (searchTypeVal) {
		case 'All':
			url = '${pageContext.request.contextPath}/memoAction!doNotNeedSecurity_getAllDatagrid.action';
			break;
		case 'ldjb':
			url = '${pageContext.request.contextPath}/memoAction!doNotNeedSecurity_getLdjbDatagrid.action';
			break;
		case 'ptjb':
			url = '${pageContext.request.contextPath}/memoAction!doNotNeedSecurity_getPtjbDatagrid.action';
			break;
		}
		//console.info(url);
		$('#memo_memoList_MemofromCombo').combobox({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
			valueField : 'username',
			textField : 'username'
		});
		$('#memo_memoList_MemotoCombo').combobox({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
			valueField : 'username',
			textField : 'username'
		});

		dg = $('#memo_memoList_datagrid').datagrid({
			url : url,
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			nowrap : true,
			singleSelect : true,
			sortName : 'status',
			sortOrder : 'asc',
			columns : [ [
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'memofrom',
					title : '交班人',
					width : 70,
					align : 'center',
					editor : {
						type : 'combobox',
						options : {
							url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
							valueField : 'username',
							textField : 'username',
							required : true
						}
					},
					sortable : true
				},
				{
					field : 'memoto',
					title : '接班人',
					width : 70,
					align : 'center',
					editor : {
						type : 'combobox',
						options : {
							url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
							valueField : 'username',
							textField : 'username',
							required : true
						}
					},
					sortable : true
				},
				{
					field : 'memotype',
					title : '日志类型',
					width : 90,
					align : 'center',
					editor : {
						type : 'combobox',
						options : {
							valueField : 'label',
							textField : 'value',
							data : [ {
								label : '普通交班',
								value : '普通交班'
							}, {
								label : '领导交办',
								value : '领导交办'
							} ],
							required : true
						}
					},
					sortable : true
				},
				{
					field : 'classes',
					title : '班次',
					width : 70,
					align : 'center',
					editor : {
						type : 'combobox',
						options : {
							valueField : 'label',
							textField : 'value',
							data : [ {
								label : '早班',
								value : '早班'
							}, {
								label : '中班',
								value : '中班'
							}, {
								label : '晚班',
								value : '晚班'
							} ],
							required : true
						}
					},
					sortable : true
				},
				{
					field : 'createtime',
					title : '记录时间',
					width : 150,
					editor : {
						type : 'datetimebox',
						options : {
							required : true
						}
					},
					sortable : true
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
					},
					editor : {
						type : 'textarea',
						options : {}
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
					},
					editor : {
						type : 'textarea',
						options : {}
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
					},
					editor : {
						type : 'textarea',
						options : {}
					}
				},
				{
					field : 'modifytime',
					title : '修改时间',
					width : 150,
					sortable : true
				},
				{
					field : 'status',
					title : '状态',
					width : 60,
					align : 'center',
					editor : {
						type : 'checkbox',
						options : {
							on : '已完成',
							off : '未完成'
						}
					},
					sortable : true
				}
			] ],
			rowStyler : function(index, row) {
				if (row.status == '未完成') {
					if (row.memotype == '领导交办') {
						return 'color:red;font-weight:bold;';
					} else {
						return 'color:green';
					}
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
				if (editRow != undefined) {
					dg.datagrid('endEdit', editRow);
				}

				if (editRow == undefined) {
					dg.datagrid('beginEdit', rowIndex);
					editRow = rowIndex;
					dg.datagrid('unselectAll');
				}
			},
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					add();
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
						dg.datagrid('endEdit', editRow);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					dg.datagrid('unselectAll');
					dg.datagrid('rejectChanges');
					editRow = undefined;
				}
			} ],
			onAfterEdit : function(rowIndex, rowData, changes) {
				var inserted = dg.datagrid('getChanges', 'inserted');
				var updated = dg.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRow = undefined;
					dg.datagrid('unselectAll');
					return;
				}
				var url = '';
				if (inserted.length > 0) {
					url = '${pageContext.request.contextPath}/memoAction!add.action';
				}
				if (updated.length > 0) {
					url = '${pageContext.request.contextPath}/memoAction!edit.action';
				}
				$.ajax({
					url : url,
					type : "POST",
					data : rowData,
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							dg.datagrid('acceptChanges');
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
							editRow = undefined;
							dg.datagrid('reload');
						} else {
							/*datagrid.datagrid('rejectChanges');*/
							dg.datagrid('beginEdit', editRow);
							$.messager.alert('错误', r.msg, 'error');
						}
						dg.datagrid('unselectAll');
					},
					error : function(data) {
						$.messager.alert('警告！', data.responseText, 'warning');
					}
				});
			}
		});

	/*
	dg.datagrid('enableCellEditing').datagrid('gotoCell', {
		index : 0,
		field : 'id'
	});
	*/
	});

	function _search() {
		dg.datagrid('load', serializeObject($('#memo_memoList_searchForm')));
	}

	function cleanSearch() {
		$('#memo_memoList_searchForm').form('reset');
		dg.datagrid('load', {});
	}

	function add() {
		if (editRow != undefined) {
			dg.datagrid('endEdit', editRow);
		}

		if (editRow == undefined) {
			dg.datagrid('unselectAll');
			var row = {
				id : getUUID()
			};
			dg.datagrid('appendRow', row);
			editRow = dg.datagrid('getRows').length - 1;
			dg.datagrid('selectRow', editRow);
			dg.datagrid('beginEdit', editRow);
		}
	}

	function del() {
		if (editRow != undefined) {
			dg.datagrid('endEdit', editRow);
			return;
		}
		var rows = dg.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/memoAction!delete.action',
						type : "POST",
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(r) {
							if (r.success) {
								dg.datagrid('load');
								dg.datagrid('unselectAll');
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
		var rows = dg.datagrid('getSelections');
		if (rows.length == 1) {
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
					$('#memo_memoEdit_editForm').form('load', rows[0]);
				}
			});
		} else {
			$.messager.alert('提示', '请选择一项进行修改！', 'error');
		}
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div region="north" border="false" title="查询条件" style="height: 122px;overflow: hidden;" align="left">
		<form id="memo_memoList_searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<th>内容</th>
					<td><input name="content" style="width:315px;" /></td>
					<th>日志类型</th>
					<td>
						<%
							if (searchType.equals("ldjb")) {
						%> <input class="easyui-textbox" name="memotype" value="领导交办"
						data-options="editable:false,disabled:true" /> <%
						 	}
						 	if (searchType.equals("ptjb")) {
						 %><input class="easyui-textbox" name="memotype" value="普通交班"
												data-options="editable:false,disabled:true" /> <%
						 	}
						 	if (searchType.equals("All")) {
						 %> <select class="easyui-combobox" name="memotype" data-options="required:true,editable:false">
													<option value="">---请选择---</option>
													<option value="普通交班">普通交班</option>
													<option value="领导交办">领导交办</option>
											</select> <%
						 	}
						 %>
					</td>
				</tr>
				<tr>
					<th>记录时间</th>
					<td><input name="createtimeStart" class="easyui-datetimebox" editable="false"
						style="width: 155px;" />至<input name="createtimeEnd" class="easyui-datetimebox"
						editable="false" style="width: 155px;" /></td>
					<th>交班人</th>
					<td><input id="memo_memoList_MemofromCombo" class="easyui-combobox" name="Memofrom"></td>
				</tr>
				<tr>
					<th>修改时间</th>
					<td><input name="modifytimeStart" class="easyui-datetimebox" editable="false"
						style="width: 155px;" />至<input name="modifytimeEnd" class="easyui-datetimebox"
						editable="false" style="width: 155px;" /></td>
					<th>接班人</th>
					<td><input id="memo_memoList_MemotoCombo" class="easyui-combobox" name="Memoto">
						&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">查询</a>&nbsp;&nbsp;<a
						href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div region="center" border="false">
		<table id="memo_memoList_datagrid"></table>
	</div>
</div>