<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.SessionInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>

<script type="text/javascript">
	var attachCurrentUser;
	var attachDg;
	$(function() {
		attachCurrentUser = "<%=sessionInfo.getUser().getUsername()%>";
		attachDg = $('#attachment_attachedList_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/attachmentAction!doNotNeedSecurity_getAllDg.action',
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			nowrap : true,
			fitColumns : true,
			singleSelect : false,
			sortName : 'uploadtime',
			sortOrder : 'desc',
			columns : [ [
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'filename',
					title : '文件名',
					width : 200,
					formatter : function(value, row, index) {
						//<a href="http://www.w3school.com.cn/">Visit W3School!</a>
						return "<a href='" + "${pageContext.request.contextPath}" + row.url + "' target='_blank'>" + value + "</a>";
					}
				},
				{
					field : 'describe',
					title : '描述',
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
					field : 'account',
					title : '上传人',
					width : 70,
					sortable : true
				},
				{
					field : 'uploadtime',
					title : '上传时间',
					width : 150,
					sortable : true
				},
				{
					field : 'url',
					title : 'URL地址',
					width : 5,
					hidden : true
				}
			] ],
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : [ {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					delAttached();
				}
			}, '-', {
				text : '取消选择',
				iconCls : 'icon-cancel',
				handler : function() {
					attachDg.datagrid('unselectAll');
				}
			} ]
		});

		$('#attachment_attachedList_handlerCombo').combobox({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
			valueField : 'id',
			textField : 'username'
		});

	});

	function searchAttached() {
		attachDg.datagrid('load', serializeObject($('#attachment_attachedList_searchForm')));
	}

	function cleanSearchAttached() {
		$('#attachment_attachedList_searchForm').form('reset');
		attachDg.datagrid('load', {});
	}

	function delAttached() {
		var titles = [];
		var ids = "";
		var rows = attachDg.datagrid('getSelections');
		if (rows && rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				titles.push('<span>文件：' + row.filename + '</span>');
				if (i != rows.length - 1) {
					ids += row.id + ",";
				} else {
					ids += row.id;
				}

			}
			$.messager.confirm('是否确认删除以下文件？', titles.join('<br/>'), function(r) {
				if (r) {
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/attachmentAction!del.action",
						data : "ids=" + ids,
						dataType : "json",
						success : function(result) {
							$.messager.alert('提示：', result.msg);
							$('#attachment_attachedList_datagrid').datagrid('load');
							$('#attachment_attachedList_datagrid').datagrid('unselectAll');
						},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});
				}
			});

		} else {
			$.messager.alert('注意！', '请选择需要删除的文件！');
		}
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div region="north" border="false" title="查询条件" style="height: 92px;"
		align="left">
		<form id="attachment_attachedList_searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<th>文件名</th>
					<td><input name="filename" style="width:315px;" /></td>
					<th>上传人</th>
					<td><input id="attachment_attachedList_handlerCombo"
						class="easyui-combobox" name="account" data-options="editable:false"
						style="width:155px;"></td>
				</tr>
				<tr>
					<th>关键字</th>
					<td><input name="describe" style="width:315px;" /></td>
					<th>上传时间</th>
					<td><input name="uploadtimeStart" class="easyui-datetimebox"
						editable="false" style="width: 155px;" />至<input name="uploadtimeEnd"
						class="easyui-datetimebox" editable="false" style="width: 155px;" />&nbsp;&nbsp;<a
						href="javascript:void(0);" class="easyui-linkbutton" onclick="searchAttached();">查询</a>&nbsp;&nbsp;<a
						href="javascript:void(0);" class="easyui-linkbutton"
						onclick="cleanSearchAttached();">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div region="center" border="false">
		<table id="attachment_attachedList_datagrid"></table>
	</div>
</div>