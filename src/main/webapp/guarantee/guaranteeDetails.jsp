<%@page import="model.SessionInfo"%>
<%@page import="model.TGuarantee"%>
<%@page import="dao.impl.GuaranteeDaoImpl"%>
<%@page import="dao.GuaranteeDaoI"%>
<%@page import="pageModel.Guarantee"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<script type="text/javascript">
	var currentUser;
	var guaranteeId;
	$(function() {
		guaranteeId = "<%=id%>";
		currentUser = "<%=sessionInfo.getUser().getUsername()%>";
		$.ajax({
			url : '${pageContext.request.contextPath}/guaranteeAction!doNotNeedSecurity_getGuarantee.action',
			data : {
				id : guaranteeId
			},
			dataType : 'json',
			success : function(data) {
				//console.log(data);
				$('#title').html("<h3>" + data.title + "</h3>");
				$('#content').textbox('setValue',data.content);
				$('#requirement').html("<span style='color: red;'>" + data.requirement + "</span>");
				$('#noticetemplate').text(data.noticetemplate);
				$('#guaranteeDate').html("<span style='color: red;'>" + data.timestart.substr(0, 10) + '&nbsp;&nbsp;至&nbsp;&nbsp;' + data.timeend.substr(0, 10) + "</span>");
				$('#timepoint').text(data.timepoint);
				$('#attachment').html(data.attachment);
				$('#status').text(data.status);
			},
			error : function(data) {
				$.messager.alert('警告！', '读取保障内容错误！', 'warning');
			}
		});

		$('#guarantee_guaranteeDetails_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/guaranteeNoticeAction!doNotNeedSecurity_getNoticeDg.action',
			queryParams : {
				related : guaranteeId
			},
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			nowrap : true,
			fitColumns : true,
			pageSize : 20,
			singleSelect : true,
			sortName : 'noticetime',
			sortOrder : 'asc',
			view : groupview,
			groupField : 'noticedate',
			groupFormatter : function(value, rows) {
				return value.substr(0, 10);
			},
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'noticetime',
					title : '时间',
					align : 'center',
					width : 50,
					sortable : true,
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
					title : '易信通报内容',
					width : 370
				},
				{
					field : 'recorder',
					title : '通报人',
					align : 'center',
					width : 70,
					sortable : true
				},
				{
					field : 'remarks',
					title : '备注或问题记录',
					width : 370
				},
				{
					field : 'related',
					title : '关联ID',
					width : 10,
					hidden : true
				}
			]],
			onDblClickRow : function(rowIndex, rowData) {
				var editGNoticeDialog = $('<div/>').dialog({
					title : '修改',
					width : 800,
					height : 720,
					left : 350,
					top : 20,
					href : '${pageContext.request.contextPath}/guarantee/gNoticeAdd.jsp',
					modal : false,
					collapsible : true,
					maximizable : true,
					resizable : true,
					buttons : [{
						text : '修改',
						iconCls : 'icon-edit',
						handler : function() {
							$('#guarantee_gNoticeAdd_Form').form('submit', {
								url : '${pageContext.request.contextPath}/guaranteeNoticeAction!edit.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										/*console.info(result);*/
										if (result.success) {
											editGNoticeDialog.dialog('close');
											$('#guarantee_guaranteeDetails_datagrid').datagrid('updateRow', {
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
									} finally {
										$('#guarantee_gNoticeAdd_Form input').val('');
									}
								}
							});
						}
					}, {
						text : '取消',
						handler : function() {
							editGNoticeDialog.dialog('close');
						}
					}],
					onClose : function() {
						editGNoticeDialog.dialog('destroy');
					},
					onLoad : function() {
						$('#guarantee_gNoticeAdd_Form').form('load', {
							id : rowData.id,
							related : rowData.related,
							noticedate : rowData.noticedate,
							noticetime : rowData.noticetime,
							content : rowData.content,
							recorder : currentUser,
							remarks : rowData.remarks
						});
					}
				});
			},
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。~~~", 'warning');
			},
			toolbar : [{
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					addGNotice();
				}
			}, '-', {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					editGNotice();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					delGNotice();
				}
			}, '-', {
				text : '导出EXCEL',
				iconCls : 'icon-myExcel',
				handler : function() {
					exportNotice();
				}
			}]
		});
	});

	function exportNotice() {
		var tempUrl = "${pageContext.request.contextPath}/guaranteeNoticeAction!doNotNeedSecurity_exportExcel.action?related=" + guaranteeId;
		window.open(tempUrl, "_blank");
	}

	function addGNotice() {
		var addGNoticeDialog = $('<div/>').dialog({
			title : '添加',
			width : 800,
			height : 720,
			left : 350,
			top : 20,
			href : '${pageContext.request.contextPath}/guarantee/gNoticeAdd.jsp',
			modal : false,
			collapsible : true,
			maximizable : true,
			resizable : true,
			buttons : [{
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					$('#guarantee_gNoticeAdd_Form').form('submit', {
						url : '${pageContext.request.contextPath}/guaranteeNoticeAction!add.action',
						success : function(data) {
							try {
								var result = jQuery.parseJSON(data);
								/*console.info(result);*/
								if (result.success) {
									addGNoticeDialog.dialog('close');
									$('#guarantee_guaranteeDetails_datagrid').datagrid('load', {
										related : guaranteeId
									});
								}
								$.messager.show({
									title : '提示：',
									msg : result.msg
								});
							} catch (e) {
								$.messager.alert('警告！', data, 'warning');
							} finally {
								$('#guarantee_gNoticeAdd_Form input').val('');
							}
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					addGNoticeDialog.dialog('close');
				}
			}],
			onClose : function() {
				addGNoticeDialog.dialog('destroy');
			},
			onLoad : function() {
				$('#guarantee_gNoticeAdd_Form').form('load', {
					related : guaranteeId,
					recorder : currentUser
				});
			}
		});
	}

	function delGNotice() {
		var rows = $('#guarantee_guaranteeDetails_datagrid').datagrid('getChecked');
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除 所选记录 ？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/guaranteeNoticeAction!delete.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$('#guarantee_guaranteeDetails_datagrid').datagrid('load', {
									related : guaranteeId
								});
								$('#guarantee_guaranteeDetails_datagrid').datagrid('unselectAll');
							}
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

	function editGNotice() {
		var rows = $('#guarantee_guaranteeDetails_datagrid').datagrid('getChecked');
		if (rows.length == 1) {
			var editGNoticeDialog = $('<div/>').dialog({
				title : '修改',
				width : 800,
				height : 720,
				left : 350,
				top : 20,
				href : '${pageContext.request.contextPath}/guarantee/gNoticeAdd.jsp',
				modal : false,
				collapsible : true,
				maximizable : true,
				resizable : true,
				buttons : [{
					text : '修改',
					iconCls : 'icon-edit',
					handler : function() {
						$('#guarantee_gNoticeAdd_Form').form('submit', {
							url : '${pageContext.request.contextPath}/guaranteeNoticeAction!edit.action',
							success : function(data) {
								try {
									var result = jQuery.parseJSON(data);
									/*console.info(result);*/
									if (result.success) {
										editGNoticeDialog.dialog('close');
										$('#guarantee_guaranteeDetails_datagrid').datagrid('updateRow', {
											index : $('#guarantee_guaranteeDetails_datagrid').datagrid('getRowIndex', rows[0]),
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
									$('#guarantee_gNoticeAdd_Form input').val('');
								}
							}
						});
					}
				}, {
					text : '取消',
					handler : function() {
						editGNoticeDialog.dialog('close');
					}
				}],
				onClose : function() {
					editGNoticeDialog.dialog('destroy');
				},
				onLoad : function() {
					$('#guarantee_gNoticeAdd_Form').form('load', {
						id : rows[0].id,
						related : rows[0].related,
						noticedate : rows[0].noticedate,
						noticetime : rows[0].noticetime,
						content : rows[0].content,
						recorder : currentUser,
						remarks : rows[0].remarks
					});
				}
			});
		} else {
			$.messager.alert('提示：', '请选择一条需要修改的记录！', 'info');
		}
	}
</script>

<div id="order_orderEdit_layout" class="easyui-layout"
	data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div class="easyui-panel" title="保障细节"
			data-options="fit:true,border:false"
			style="width:90%;padding: 10px 30px;">
			<table border="1" cellspacing="0" cellpadding="5" width="100%">
				<tr>
					<td id="title" colspan="2" style="text-align:center;"></td>
				</tr>
				<tr>
					<th style="text-align:right;vertical-align: top;" width="10%">保障内容：</th>
					<td><input class="easyui-textbox" id="content"
						readonly="readonly" data-options="multiline:true"
						style="width:90%;height:220px"></td>
				</tr>
				<tr>
					<th style="text-align:right;vertical-align: top;" width="10%">具体工作要求：</th>
					<td id="requirement"></td>
				</tr>
				<tr>
					<th style="text-align:right;vertical-align: top;" width="10%">易信通报模板：</th>
					<td id="noticetemplate"></td>
				</tr>
				<tr>
					<th style="text-align:right;vertical-align: top;" width="10%">保障日期：</th>
					<td id="guaranteeDate"></td>
				</tr>
				<tr>
					<th style="text-align:right;vertical-align: top;" width="10%">通报时间点：</th>
					<td id="timepoint"></td>
				</tr>
				<tr>
					<th style="text-align:right;vertical-align: top;" width="10%">附件列表：</th>
					<td id="attachment"></td>
				</tr>
				<tr>
					<th style="text-align:right;vertical-align: top;" width="10%">状态：</th>
					<td id="status"></td>
				</tr>
			</table>
		</div>
	</div>
	<div
		data-options="region:'south',split:false,border:true,title:'保障记录',collapsible:true"
		style="height:50%;">
		<table id="guarantee_guaranteeDetails_datagrid"></table>
	</div>
</div>
