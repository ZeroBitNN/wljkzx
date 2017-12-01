<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		$('#order_orderEdit_typeCombo').combobox({
			url : '${pageContext.request.contextPath}/orderAction!doNotNeedSecurity_getOrderType.action',
			valueField : 'id',
			textField : 'name'
		});

		$('#order_orderEdit_categoryCombo').combobox({
			url : '${pageContext.request.contextPath}/orderAction!doNotNeedSecurity_getOrderCategory.action',
			valueField : 'id',
			textField : 'name'
		});


	//$('#order_orderEdit_addNoticeForm input[name=orderid]').val();
	});

	var noticeToolbar = [ {
		text : '新发通报',
		iconCls : 'icon-add',
		handler : function() {
			$('#order_orderEdit_contentTb').textbox('clear');
			$('#order_orderEdit_addNoticeDialog').dialog('open');
		}
	}, '-', {
		text : '删除通报',
		iconCls : 'icon-remove',
		handler : function() {
			deleteNotice();
		}
	} ];

	function deleteNotice() {
		var rows = $('#order_orderEdit_NoticeDatagrid').datagrid('getChecked');
		//console.info(rows);
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除通报？<p><b>' + rows[0].content + '</b></p>', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/orderNoticeAction!delNotice.action',
						data : {
							id : rows[0].id
						},
						dataType : 'json',
						success : function(data) {
							$('#order_orderEdit_NoticeDatagrid').datagrid('load');
							$('#order_orderEdit_NoticeDatagrid').datagrid('unselectAll');
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

<div id="order_orderEdit_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div style="margin:20px 0;"></div>
		<table align="center" width="80%">
			<tr>
				<td>
					<form id="order_orderEdit_editForm" class="easyui-form" method="post"
						data-options="novalidate:true,
						onLoadSuccess:function(){
						 var orderId = $('#order_orderEdit_editForm input[name=id]').val();
						 var typeVal = $('#order_orderEdit_editForm input[name=typeid]').val();
						 $('#order_orderEdit_NoticeDatagrid').datagrid({
							    url:'${pageContext.request.contextPath}/orderNoticeAction!datagrid.action',
							    queryParams:{orderid:orderId},
							    singleSelect:true,
							    fit:true,
							    fitColumns:true,
			            toolbar:noticeToolbar,
			            border:false,
			            striped:true,
			            pagination:true,
			            nowrap:false,
			            sortName:'time',
			            sortOrder:'desc',
			            onLoadError:function(){
						        $.messager.alert('警告！', '您没有访问此功能的权限！请联系管理员给你赋予相应权限。', 'warning');
						      }
							});
						 $('#order_orderEdit_orderIdTb').textbox('setValue',orderId);
						 if (typeVal!='短信通报'){
						   $('#order_orderEdit_layout').layout('collapse','south');
						 }
						}">
						<div style="margin-bottom:20px">
							<label class="label-top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ID：</label> <input name="id"
								class="easyui-validatebox" readonly="readonly" style="width:60%;" />
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">管控级别：</label> <input id="order_orderEdit_typeCombo" class="easyui-combobox"
								name="typeid" data-options="required:true,editable:false">
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">通报/管控时限：</label> <select class="easyui-combobox" name="noticetime"
								data-options="required:true,editable:false">
								<option value="30">30分钟</option>
								<option value="60">1小时</option>
								<option value="120">2小时</option>
								<option value="240">4小时</option>
								<option value="480">8小时</option>
								<option value="720">12小时</option>
								<option value="1440">24小时</option>
							</select>
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">运维单号：</label> <input class="easyui-textbox" name="ordernum" style="width:80%"
								data-options="required:true">
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">工单标题：</label> <input class="easyui-textbox" style="width:80%" type="text" name="title"
								data-options="required:true,validType:'length[0,100]'"></input>
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">所属专业：</label> <input id="order_orderEdit_categoryCombo" class="easyui-combobox"
								name="orderCategory" data-options="required:true,editable:false">
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">工单内容：</label> <input class="easyui-textbox" name="content" data-options="multiline:true"
								style="width:80%;height:300px"></input>
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">发单时间：</label> <input class="easyui-datetimebox" name="createtime"
								data-options="required:true,showSeconds:false,editable:false" style="width:150px">
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">工单状态：</label> <select class="easyui-combobox" name="isArchived"
								data-options="required:true,editable:false" data-options="required:true">
								<option value="在途">在途</option>
								<option value="已归档">已归档</option>
							</select>
						</div>
						<div style="margin-bottom:20px">
							<label class="label-top">归档时间：</label> <input class="easyui-datetimebox" name="archivedtime"
								data-options="showSeconds:false,editable:false" style="width:150px">
						</div>
					</form>
				</td>
			</tr>
		</table>
	</div>

	<div data-options="region:'south',split:true,title:'通报内容',border:false" style="height:45%;">
		<table class="easyui-datagrid" id="order_orderEdit_NoticeDatagrid">
			<thead>
				<tr>
					<th data-options="field:'id',width:1,align:'center',hidden:true">id</th>
					<th data-options="field:'author',width:60,align:'center'">通报人</th>
					<th data-options="field:'time',width:150,align:'center',sortable:true">通报时间</th>
					<th data-options="field:'content',width:500,align:'left'">通报内容</th>
					<th data-options="field:'orderid',width:1,hidden:true">工单ID</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="order_orderEdit_addNoticeDialog" class="easyui-dialog" align="center" style="width:800px;height:500px"
	data-options="title:'新发通报',closed:true,modal:true,buttons:[{
    text:'确定',
    handler:function(){
      $('#order_orderEdit_addNoticeForm').form('submit',{
        url:'${pageContext.request.contextPath}/orderNoticeAction!addNotice.action',
        success:function(data){
	        try{
	         var result = jQuery.parseJSON(data);
	         if (result.success) {
	           $('#order_orderEdit_addNoticeDialog').dialog('close');
	           $('#order_orderEdit_NoticeDatagrid').datagrid('insertRow',{
	            index:0,
	            row:result.obj
	           });
	         }
	         $.messager.show({
	           title : '提示：',
	           msg : result.msg
	         });                   
	        } catch(e) {
	          $.messager.alert('警告！', data, 'warning');
	        }
        },
        onSubmit : function() {
	        return $(this).form('enableValidation').form('validate');
	      }
      });
    }
  }]">
	<form id="order_orderEdit_addNoticeForm" method="post">
		<table style="width:80%;text-align:'center';">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td><input id="order_orderEdit_orderIdTb" name="orderid" class="easyui-textbox" label="工单ID："
					labelPosition="top" readonly="readonly" style="width: 80%;" /></td>
			</tr>
			<tr>
				<td><input id="order_orderEdit_contentTb" name="content" class="easyui-textbox"
					data-options="multiline:true,required:true" label="通报内容：" labelPosition="top" style="width:90%;height:300px;" /></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
		</table>
	</form>
</div>