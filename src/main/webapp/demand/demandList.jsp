<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#demand_demandList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/demandAction!doNotNeedSecurity_datagrid.action',
			fit : true,
			border : false,
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			fitColumns : true,
			sortName : 'createtime',
			sortOrder : 'desc',
			multiSort : true,
			striped : true,
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'content',
					title : '内容',
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
					field : 'recorder',
					title : '发布人',
					width : 60,
					align : 'center',
					sortable : true
				},
				{
					field : 'createtime',
					title : '创建时间',
					width : 150,
					align : 'center',
					sortable : true
				},
				{
					field : 'modifytime',
					title : '修改时间',
					width : 150,
					align : 'center',
					sortable : true
				},
				{
					field : 'isshow',
					title : '是否显示 ',
					width : 60,
					align : 'center',
					sortable : true
				}
			]],
			onDblClickRow : function(rowIndex, rowData) {
				var demandEditDialog = $('<div/>').dialog({
					title : '修改',
					width : 800,
					height : 450,
					left : 350,
					top : 200,
					href : '${pageContext.request.contextPath}/demand/demandAdd.jsp',
					modal : false,
					collapsible : true,
					maximizable : true,
					resizable : true,
					buttons : [{
						text : '修改',
						iconCls : 'icon-add',
						handler : function() {
							$('#demand_demandAdd_Form').form('submit', {
								url : '${pageContext.request.contextPath}/demandAction!edit.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										/*console.info(result);*/
										if (result.success) {
											demandEditDialog.dialog('close');
											$('#demand_demandList_datagrid').datagrid('updateRow', {
												index : rowIndex,
												row : result.obj
											});
										}
										$.messager.alert({
											title : '提示：',
											msg : result.msg
										});
									} catch (e) {
										$.messager.alert('警告！', data, 'warning');
									} finally {
										$('#demand_demandAdd_Form input').val('');
									}
								}
							});
						}
					}, {
						text : '取消',
						handler : function() {
							demandEditDialog.dialog('close');
						}
					}],
					onClose : function() {
						demandEditDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#demand_demandAdd_Form').form('load', rowData);
					}
				});
			},
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : '#demand_demandList_toolbar'
		});
	});

	function addDemand() {
		var demandAddDialog = $('<div/>').dialog({
			title : '添加',
			width : 800,
			height : 450,
			left : 350,
			top : 200,
			href : '${pageContext.request.contextPath}/demand/demandAdd.jsp',
			modal : false,
			collapsible : true,
			maximizable : true,
			resizable : true,
			buttons : [{
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					$('#demand_demandAdd_Form').form('submit', {
						url : '${pageContext.request.contextPath}/demandAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								/*console.info(result);*/
								if (result.success) {
									demandAddDialog.dialog('close');
									$('#demand_demandList_datagrid').datagrid('insertRow', {
										index : 0,
										row : result.obj
									});
								}
								$.messager.alert({
									title : '提示：',
									msg : result.msg
								});
							} catch (e) {
								$.messager.alert('警告！', data, 'warning');
							} finally {
								$('#demand_demandAdd_Form input').val('');
							}
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					demandAddDialog.dialog('close');
				}
			}],
			onClose : function() {
				demandAddDialog.dialog('destroy');
			},
			onLoad : function() {
				$('#demand_demandAdd_Form').form('load', {
					id : getUUID()
				});
			}
		});
	}

	function searchContent() {
		$('#demand_demandList_datagrid').datagrid('load', serializeObject($('#demand_dmeandList_searchForm')));
	}

	function clearSearchDemand() {
		$('#demand_demandList_toolbar input[name=content]').val('');
		$('#demand_demandList_datagrid').datagrid('load', {});
	}

	function delDemand() {
		var rows = $('#demand_demandList_datagrid').datagrid('getChecked');
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除 所选记录 ？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/demandAction!delete.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							$('#demand_demandList_datagrid').datagrid('load');
							$('#demand_demandList_datagrid').datagrid('unselectAll');
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
</script>

<table id="demand_demandList_datagrid"></table>

<div id="demand_demandList_toolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
		style="float:left;" onclick="addDemand();">新增</a>
	<div class="datagrid-btn-separator"></div>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-remove',plain:true" style="float:left;" onclick="delDemand();">删除</a>
	<div class="datagrid-btn-separator"></div>
	<form id="demand_dmeandList_searchForm">
		&nbsp;&nbsp;内容：<input name="content" /> <a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-search',plain:true" onclick="searchContent();">查询</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true" onclick="clearSearchDemand();">重置</a>
	</form>
</div>