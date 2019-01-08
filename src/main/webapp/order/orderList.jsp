<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(function() {
		var dg = $('#order_orderlist_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/orderAction!datagrid.action',
			fit : true,
			border : false,
			striped : true,
			pagination : true,
			nowrap : false,
			multiSort : true,
			sortName : 'isArchived',
			sortOrder : 'desc',
			frozenColumns : [ [
				{
					field : 'id',
					title : '编号',
					width : 10,
					checkbox : true
				},
				{
					field : 'isArchived',
					title : '工单状态',
					width : 70,
					align : 'center',
					sortable : true
				},
				{
					field : 'noticetime',
					title : '通报/管控<BR>时限(分钟)',
					width : 60,
					align : 'center'
				}
			] ],
			columns : [ [
				{
					field : 'typeid',
					title : '管控类型',
					width : 60,
					align : 'center'
				},
				{
					field : 'orderCategory',
					title : '专业',
					width : 60
				},
				{
					field : 'ordernum',
					title : '运维单号',
					width : 150,
					formatter : function(value, row, index) {
						return '<span title="' + value + '">' + value + '</span>';
					}
				},
				{
					field : 'title',
					title : '标题',
					width : 250,
					formatter : function(value, row, index) {
						return '<span title="' + value + '">' + value + '</span>';
					}
				},
				{
					field : 'author',
					title : '接单人',
					width : 50
				},
				{
					field : 'createtime',
					title : '接单时间',
					width : 150
				},
				{
					field : 'editor',
					title : '最后一<BR>次修改人',
					width : 60
				},
				{
					field : 'modifytime',
					title : '最后一次修改时间',
					width : 150
				},
				{
					field : 'archivedtime',
					title : '归档时间',
					width : 150
				},
				{
					field : 'content',
					title : '内容',
					width : 1,
					hidden : true
				}
			] ],
			rowStyler : function(index, row) {
				if (row.isArchived == '在途') {
					if (row.typeid == '短信通报') {
						return 'color:red;font-weight:bold;';
					} else {
						return 'color:green';
					}
				}
			},
			onDblClickRow : function(index, row) {
				//console.info(row);
				var d = $('<div/>').dialog({
					title : '电子运维单：' + row.ordernum,
					width : 800,
					height : 600,
					href : '${pageContext.request.contextPath}/order/orderEdit.jsp',
					collapsible : true,
					resizable : true,
					left : 300,
					top : 20,
					maximizable : true,
					buttons : [ {
						text : '修改工单',
						handler : function() {
							$('#order_orderEdit_editForm').form('submit', {
								url : '${pageContext.request.contextPath}/orderAction!editOrder.action',
								success : function(data) {
									try {
										var result = jQuery.parseJSON(data);
										if (result.success) {
											d.dialog('close');
											$('#order_orderlist_datagrid').datagrid('updateRow', {
												index : $('#order_orderlist_datagrid').datagrid('getRowIndex', row),
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
								},
								onSubmit : function() {
									return $(this).form('enableValidation').form('validate');
								}
							});
						}
					} ],
					onClose : function() {
						$('#order_orderlist_datagrid').datagrid('load', {});
						$(this).dialog('destroy');
					},
					onLoad : function() {
						$('#order_orderEdit_editForm').form('load', row);
					}
				});
			},
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : '#order_orderlist_toolbar'
		});

		var pager = dg.datagrid('getPager');
		pager.pagination({
			showRefresh : false
		});
	});

	function archived() {
		var titles = [];
		var ids = "";
		var rows = $('#order_orderlist_datagrid').datagrid('getSelections');
		if (rows && rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				titles.push('<span>工单标题：' + row.title + '</span>');
				if (i != rows.length - 1) {
					ids += row.id + ",";
				} else {
					ids += row.id;
				}

			}
			$.messager.confirm('是否确认归档以下工单？', titles.join('<br/>'), function(r) {
				if (r) {
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/orderAction!archived.action",
						data : "ids=" + ids,
						dataType : "json",
						success : function(result) {
							$.messager.alert('提示：', result.msg);
						},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});

					$('#order_orderlist_datagrid').datagrid('load', {});
				}
			});

		} else {
			$.messager.alert('注意！', '请选择需要归档的工单！');
		}

	}

	function searchRec() {
		$('#order_orderlist_datagrid').datagrid('load', {
			ordernum : $('#order_orderList_searchTable input[name=ordernum]').val(),
			title : $('#order_orderList_searchTable input[name=title]').val()
		});
	}

	function clearSearchOrder() {
		$('#order_orderList_searchTable input[name=ordernum]').val('');
		$('#order_orderList_searchTable input[name=title]').val('');
		$('#order_orderList_archivedCombo').combobox('clear');
		$('#order_orderList_categoryCombo').combobox('setValue', '请选择');
		$('#order_orderList_typeCombo').combobox('setValue', '请选择');
		$('#order_orderlist_datagrid').datagrid('load', {});
	}

	function unselectOrder() {
		$('#order_orderlist_datagrid').datagrid('unselectAll');
	}
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',title:'精确查询',border:false" style="height:120px;background:#F4F4F4;">
		<table id="order_orderList_searchTable" class="datagrid-toolbar" style="width:100%;height:100%">
			<tr>
				<td align="right">运维单号：</td>
				<td align="left"><input name="ordernum" /></td>
			</tr>
			<tr>
				<td align="right">工单标题：</td>
				<td align="left"><input name="title" /></td>
			</tr>
			<tr>
				<td align="right"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
					onclick="searchRec();">查询</a></td>
				<td align="left"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true"
					onclick="clearSearchOrder();">重置列表</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center'">
		<table id="order_orderlist_datagrid"></table>
	</div>
</div>

<div id="order_orderlist_toolbar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-archived',plain:true" style="float:left;"
		onclick="archived();">工单归档</a>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" style="float:left;"
		onclick="unselectOrder();">取消所选</a>
	<div class="datagrid-btn-separator"></div>
	&nbsp;&nbsp;管控类型：<input value="请选择" id="order_orderList_typeCombo" class="easyui-combobox" name="orderType"
		data-options="url : '${pageContext.request.contextPath}/orderAction!doNotNeedSecurity_getOrderType.action',
      valueField : 'id',
      textField : 'name',
      editable:false,
      onSelect : function(rec) {
        var typeId = rec.id;
        var orderState = $('#order_orderList_archivedCombo').val();
        var categoryId = $('#order_orderList_categoryCombo').val();
        $('#order_orderlist_datagrid').datagrid('load', {
          typeid : typeId,
          isArchived : orderState,
          orderCategory : categoryId
        });
      }">&nbsp;&nbsp;
	工单状态： <select id="order_orderList_archivedCombo" class="easyui-combobox" name="isArchived"
		data-options="editable:false,onSelect : function(rec) {
        var typeId = $('#order_orderList_typeCombo').val();
        var orderState = rec.value;
        var categoryId = $('#order_orderList_categoryCombo').val();
        $('#order_orderlist_datagrid').datagrid('load', {
          typeid : typeId,
          isArchived : orderState,
          orderCategory : categoryId
        });
      }">
		<option value="" selected="selected">请选择</option>
		<option value="在途">在途</option>
		<option value="已归档">已归档</option>
	</select>&nbsp;&nbsp; 专业：<input value="请选择" id="order_orderList_categoryCombo" class="easyui-combobox" name="orderCategory"
		data-options="url : '${pageContext.request.contextPath}/orderAction!doNotNeedSecurity_getOrderCategory.action',
      valueField : 'id',
      editable:false,
      textField : 'name',
      onSelect : function(rec) {
        var typeId = $('#order_orderList_typeCombo').val();
        var orderState = $('#order_orderList_archivedCombo').val();
        var categoryId = rec.id;
        $('#order_orderlist_datagrid').datagrid('load', {
          typeid : typeId,
          isArchived : orderState,
          orderCategory : categoryId
        });
      }">
</div>

