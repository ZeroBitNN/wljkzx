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
	$(function() {
		currentUser = "<%=sessionInfo.getUser().getUsername()%>";
		$('#dailywork_dailyworkList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/dailyworkAction!doNotNeedSecurity_datagrid.action',
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			fitColumns : true,
			nowrap : true,
			singleSelect : false,
			pageSize : 40,
			sortName : 'timepoint',
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'timepoint',
					title : '时间',
					width : 100,
					align : 'center',
					sortable : true
				},
				{
					field : 'category',
					title : '专业',
					width : 100,
					align : 'center',
					sortable : true
				},
				{
					field : 'content',
					title : '工作描述',
					width : 500,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'publisher',
					title : '发布人',
					width : 80,
					align : 'center',
					sortable : true
				},
				{
					field : 'releasetime',
					title : '发布时间',
					width : 150,
					align : 'center',
					sortable : true
				}
			]],
			onDblClickRow : function(rowIndex, rowData) {
				var dailyworkEditDialog;
				dailyworkEditDialog = $('<div/>').dialog({
					title : '修改记录',
					width : 700,
					height : 680,
					left : 400,
					top : 50,
					href : '${pageContext.request.contextPath}/dailywork/dailyworkEdit.jsp',
					modal : false,
					collapsible : true,
					maximizable : true,
					resizable : true,
					buttons : [{
						text : '修改',
						iconCls : 'icon-edit',
						handler : function() {
							$('#dailywork_dailyworkAdd_Form').form('submit', {
								url : '${pageContext.request.contextPath}/dailyworkAction!edit.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										/*console.info(result);*/
										if (result.success) {
											dailyworkEditDialog.dialog('close');
											$('#dailywork_dailyworkList_datagrid').datagrid('load');
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
							dailyworkEditDialog.dialog('close');
						}
					}],
					onClose : function() {
						dailyworkEditDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#dailywork_dailyworkAdd_Form').form('load', rowData);
					}
				});
			},
			toolbar : '#dailywork_dailyworkList_toolbar'
		});

		$('#dailywork_dailyworkList_searchbox').searchbox({
			searcher : function(value, name) {
				//alert(value + "," + name)
				$('#dailywork_dailyworkList_datagrid').datagrid('load', {
					content : value
				});
			},
			prompt : '搜索工作描述关键字'
		});
	});

	function addDailywork() {
		var dailyAddDialog = $('<div/>').dialog({
			title : '添加记录',
			width : 700,
			height : 620,
			left : 400,
			top : 50,
			href : '${pageContext.request.contextPath}/dailywork/dailyworkAdd.jsp',
			modal : false,
			collapsible : true,
			maximizable : true,
			resizable : true,
			buttons : [{
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					$('#dailywork_dailyworkAdd_Form').form('submit', {
						url : '${pageContext.request.contextPath}/dailyworkAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								/*console.info(result);*/
								if (result.success) {
									dailyAddDialog.dialog('close');
									$('#dailywork_dailyworkList_datagrid').datagrid('load', {});
								}
								$.messager.show({
									title : '提示：',
									msg : result.msg
								});
							} catch (e) {
								$.messager.alert('警告！', data, 'warning');
							} finally {
								$('#dailywork_dailyworkAdd_Form input').val('');
							}
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					dailyAddDialog.dialog('close');
				}
			}],
			onClose : function() {
				dailyAddDialog.dialog('destroy');
			},
			onLoad : function() {
				$('#dailywork_dailyworkAdd_Form').form('load', {
					publisher : currentUser
				});
			}
		});
	}

	function delDailywork() {
		var ids = "";
		var rows = $('#dailywork_dailyworkList_datagrid').datagrid('getSelections');
		if (rows && rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (i != rows.length - 1) {
					ids += row.id + ",";
				} else {
					ids += row.id;
				}

			}
			$.messager.confirm('确认！', '是否删除所选记录？', function(r) {
				if (r) {
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/dailyworkAction!del.action",
						data : "ids=" + ids,
						dataType : "json",
						success : function(result) {
							$.messager.alert('提示：', result.msg);
							$('#dailywork_dailyworkList_datagrid').datagrid('load');
							$('#dailywork_dailyworkList_datagrid').datagrid('unselectAll');
						},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});
				}
			});

		} else {
			$.messager.alert('注意！', '请选择需要删除的记录！');
		}
	}

	function unselectDailywork() {
		$('#dailywork_dailyworkList_datagrid').datagrid('unselectAll');
	}
</script>

<table id="dailywork_dailyworkList_datagrid"></table>

<div id="dailywork_dailyworkList_toolbar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
		style="float:left;" onclick="addDailywork();">增加</a>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
		style="float:left;" onclick="delDailywork();">删除</a>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
		style="float:left;" onclick="unselectDailywork();">取消所选</a>
	<div class="datagrid-btn-separator"></div>
	&nbsp;&nbsp;<input id="dailywork_dailyworkList_searchbox" style="width:200px;" name="content"></input>
</div>