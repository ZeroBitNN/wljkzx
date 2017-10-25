<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#guarantee_guaranteeAdd_Form').form({
			fit : true,
			border : false,
			onLoadSuccess : function() {
				var gid = $('#guarantee_guaranteeAdd_Form input[name=id]').val();
				//console.info(gid);
				$('#guarantee_guaranteeAdd_attachmentDatagrid').datagrid({
					url : '${pageContext.request.contextPath}/attachmentAction!doNotNeedSecurity_getAllDg.action',
					queryParams : {
						relatedid : gid
					},
					singleSelect : false,
					fit : true,
					fitColumns : true,
					toolbar : attachmentToolbar,
					border : false,
					striped : true,
					columns : [ [
						{
							field : 'id',
							title : '编号',
							width : 1,
							checkbox : true
						},
						{
							field : 'filename',
							title : '文件名',
							width : 200,
							formatter : function(value, row, index) {
								return "<a href='" + "${pageContext.request.contextPath}" + row.url + "' target='_blank'>" + value + "</a>";
							}
						},
						{
							field : 'describe',
							title : '描述',
							width : 250,
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
							width : 50,
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
					pagination : true,
					nowrap : false,
					sortName : 'uploadtime',
					sortOrder : 'desc',
					onLoadError : function() {
						$.messager.alert('警告！', '您没有访问此功能的权限！请联系管理员给你赋予相应权限。', 'warning');
					}
				});
				$('#guarantee_guaranteeAdd_relatedIdTb').textbox('setValue', gid);
			}
		});
	});
	
	function deleteAttachment() {
		var titles = [];
		var ids = "";
		var rows = $('#guarantee_guaranteeAdd_attachmentDatagrid').datagrid('getSelections');
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
							$('#guarantee_guaranteeAdd_attachmentDatagrid').datagrid('load');
						},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});

					attachDg.datagrid('load', {});
				}
			});

		} else {
			$.messager.alert('注意！', '请选择需要删除的文件！');
		}
	}
	
	var attachmentToolbar = [ {
		text : '上传附件',
		iconCls : 'icon-add',
		handler : function() {
			$('#guarantee_guaranteeAdd_filebox').textbox('clear');
			$('#guarantee_guaranteeAdd_textbox').textbox('clear');
			$('#guarantee_guaranteeAdd_addAttachmentDialog').dialog('open');
		}
	}, '-', {
		text : '删除附件',
		iconCls : 'icon-remove',
		handler : function() {
			deleteAttachment();
		}
	} ];
</script>

<div id="guarantee_guaranteeAdd_layout" class="easyui-layout"
	data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<form id="guarantee_guaranteeAdd_Form" method="post">
			<div class="easyui-panel" style="width:90%;padding:30px 60px;"
				data-options="fit:true,border:false">
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="ID:" name="id" labelPosition="top"
						data-options="" style="width:100%;height:52px" readonly="readonly">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="标题:" name="title" labelPosition="top"
						data-options="required:true" style="width:100%;height:52px">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="保障内容:" name="content"
						labelPosition="top" data-options="multiline:true,required:true"
						style="width:100%;height:150px">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="具体工作要求:" name="requirement"
						labelPosition="top" data-options="multiline:true"
						style="width:100%;height:150px">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="易信通报模板:" name="noticetemplate"
						labelPosition="top" data-options="multiline:true,required:true"
						style="width:100%;height:150px">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="记录人:" name="recorder"
						labelPosition="top" data-options="editable:false"
						style="width:100%;height:52px">
				</div>
				<div style="margin-bottom:20px">
					<input label="保障开始时间:" labelPosition="top" name="timestart"
						data-options="required:true" class="easyui-datebox" editable="false"
						style="width: 150px;" />
				</div>
				<div style="margin-bottom:20px">
					<input label="保障结束时间:" labelPosition="top" name="timeend"
						data-options="required:true" class="easyui-datebox" editable="false"
						style="width: 150px;" />
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="通报时间点:" name="timepoint"
						labelPosition="top"
						data-options="required:true,validType:'eqTimepoint'"
						style="width:100%;height:52px"><br> <span
						style="color: red;">(格式："hh:mm"，如："09:05"，多个时间点用半角"|"分隔，如："10:00|12:00|14:00")</span>
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-combobox" label="状态:" name="status"
						labelPosition="top" style="width:100%;height:52px"
						data-options="editable:false,
              valueField : 'label',
              textField : 'value',
              data : [ {
                label : '已结束',
                value : '已结束'
              }, {
                label : '保障中',
                value : '保障中',
                selected : true
              } ]">
				</div>
			</div>
		</form>
	</div>

	<div data-options="region:'south',split:true,title:'附件列表',border:false"
		style="height:35%;">
		<table class="easyui-datagrid" id="guarantee_guaranteeAdd_attachmentDatagrid">
		</table>
	</div>
</div>

<div id="guarantee_guaranteeAdd_addAttachmentDialog" class="easyui-dialog" align="center" style="width:500px"
	data-options="title:'附件上传',closed:true,modal:true,buttons:[{
    text:'上传',
    handler:function(){
      $('#guarantee_guaranteeAdd_addAttachmentForm').form('submit',{
        url:'${pageContext.request.contextPath}/attachmentAction!upload.action',
        success:function(data){
	        try{
	         var result = jQuery.parseJSON(data);
	         if (result.success) {
	           $('#guarantee_guaranteeAdd_addAttachmentDialog').dialog('close');
	           $('#guarantee_guaranteeAdd_attachmentDatagrid').datagrid('load');
	         }
	         $.messager.alert({
	           title : '提示：',
	           msg : result.msg,
	           width: 400,
	           height: 200
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
	<form id="guarantee_guaranteeAdd_addAttachmentForm" method="post" enctype="multipart/form-data">
		<table style="width:80%;text-align:'center';">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td><input id="guarantee_guaranteeAdd_relatedIdTb" name="relatedid" class="easyui-textbox" label="关联ID："
					labelPosition="top" readonly="readonly" style="width: 100%;" /></td>
			</tr>
			<tr>
				<td><input id="guarantee_guaranteeAdd_filebox" class="easyui-filebox" name="filedata" label="上传文件:"
					labelPosition="top" style="width:100%"
					data-options="multiple:true,buttonText:'请选择...'"><br>
					<span style="color: red;">(上传文件大小不得超过50M，文件类型为：txt,rar,zip,doc,docx,xls,xlsx,bmp,jpg,jpeg,gif,png,pdf)</span>
				</td>
			</tr>
			<tr>
				<td><input id="guarantee_guaranteeAdd_textbox" class="easyui-textbox" name="describe" label="描述:"
					labelPosition="top" data-options="multiline:true" style="width:100%;"></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
		</table>
	</form>
</div>