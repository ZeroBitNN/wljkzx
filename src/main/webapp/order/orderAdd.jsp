<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		$('#order_orderAdd_typeCombo').combobox({
			url : '${pageContext.request.contextPath}/orderAction!doNotNeedSecurity_getOrderType.action',
			valueField : 'id',
			textField : 'name',
			onSelect : function(rec) {
				var typeId = rec.id;
			}
		});

		$('#order_orderAdd_categoryCombo').combobox({
			url : '${pageContext.request.contextPath}/orderAction!doNotNeedSecurity_getOrderCategory.action',
			valueField : 'id',
			textField : 'name',
			onSelect : function(rec) {
				var categoryId = rec.id;
			}
		});

	});

	function submitForm() {
		$('#order_orderAdd_form').form('submit', {
			url : '${pageContext.request.contextPath}/orderAction!addOrder.action',
			success : function(data) {
				try {
					var result = jQuery.parseJSON(data);
					if (result.success) {
						//closeTab();
						$('#order_orderAdd_form').form('clear');
						refreshTab();
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

	function clearForm() {
		$('#order_orderAdd_form').form('clear');
	//refreshTab();
	}

	function closeTab() {
		var t = $('#layout_center_tabs');
		if (t && t.tabs('exists', '新增工单')) {
			t.tabs('close', '新增工单');
		}
	}

	function refreshTab() {
		var t = $('#layout_center_tabs');
		if (t.tabs('exists', '工单列表')) {
			t.tabs('select', '工单列表');
			$('#order_orderList_searchTable input[name=ordernum]').val('');
			$('#order_orderList_searchTable input[name=title]').val('');
			$('#order_orderList_typeCombo').combobox('reload');
			$('#order_orderList_archivedCombo').combobox('setValue', '');
			$('#order_orderList_categoryCombo').combobox('reload');
			$('#order_orderlist_datagrid').datagrid('load', {});
		}
	}
</script>

<div class="easyui-panel" data-options="fit:true,border:false">
	<form id="order_orderAdd_form" class="easyui-form" method="post" data-options="novalidate:true">
		<table cellpadding="5" align="center" width="80%">
			<tr>
				<td align="right">运维单号：</td>
				<td><input class="easyui-textbox" name="ordernum" style="width:80%" data-options="required:true"></td>
			</tr>
			<tr>
				<td align="right">管控级别：</td>
				<td><input id="order_orderAdd_typeCombo" class="easyui-combobox" name="typeid"
					data-options="required:true,editable:false"></td>
			</tr>
			<tr>
				<td align="right">通报/管控时限：</td>
				<td><select class="easyui-combobox" name="noticetime" data-options="required:true,editable:false">
						<option value="30">30分钟</option>
						<option value="60">1小时</option>
						<option value="120">2小时</option>
						<option value="240">4小时</option>
						<option value="480">8小时</option>
						<option value="720">12小时</option>
						<option value="1440">24小时</option>
				</select></td>
			</tr>
			<tr>
				<td align="right">工单标题：</td>
				<td><input class="easyui-textbox" style="width:80%" type="text" name="title"
					data-options="required:true,validType:'length[0,50]'"></input></td>
			</tr>
			<tr>
				<td align="right">所属专业：</td>
				<td><input id="order_orderAdd_categoryCombo" class="easyui-combobox" name="orderCategory"
					data-options="required:true,editable:false"></td>
			</tr>
			<tr>
				<td align="right">工单内容：</td>
				<td><input class="easyui-textbox" name="content" data-options="multiline:true" style="width:80%;height:250px"></input></td>
			</tr>
			<tr>
				<td align="right">发单时间：</td>
				<td><input class="easyui-datetimebox" name="createtime"
					data-options="required:true,showSeconds:false,editable:false" value=getNowFormatDate() style="width:150px"></td>
			</tr>
		</table>
	</form>
	<div style="text-align:center;padding:5px 0">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">增加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清空</a>
	</div>
</div>
