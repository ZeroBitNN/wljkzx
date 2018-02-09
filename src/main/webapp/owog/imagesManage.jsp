<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<script type="text/javascript">
	var imagesToolbar = [{
		text : '删除照片(只能删除本人上传照片)',
		iconCls : 'icon-remove',
		handler : function() {
			deleteAttachment();
		}
	}, "-", {

	}];

	$(function() {
		$('#owog_imagesManage_imagesDg').datagrid({
			url : '${pageContext.request.contextPath}/owogImagesAction!doNotNeedSecurity_getAllDg.action',
			singleSelect : true,
			fit : true,
			fitColumns : true,
			toolbar : '#owog_imagesManage_tb',
			border : false,
			pageSize : 30,
			striped : true,
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 1,
					checkbox : true
				},
				{
					field : 'class_',
					title : '班组',
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
					field : 'name',
					title : '标杆人员姓名',
					width : 80,
					sortable : true
				},
				{
					field : 'startdate',
					title : '周期开始时间',
					width : 150,
					sortable : true
				},
				{
					field : 'enddate',
					title : '周期结束时间',
					width : 150,
					sortable : true
				},
				{
					field : 'filename',
					title : '文件名',
					width : 200,
					formatter : function(value, row, index) {
						return "<a href='" + "${pageContext.request.contextPath}/" + row.imgsrc + "' target='_blank'>" + value + "</a>";
					}
				},
				{
					field : 'uploader',
					title : '上传人',
					width : 80,
					sortable : true
				},
				{
					field : 'uploadtime',
					title : '上传时间',
					width : 150,
					sortable : true
				}
			]],
			pagination : true,
			nowrap : false,
			sortName : 'uploadtime',
			sortOrder : 'desc'
		});
	});

	function submitForm() {
		$('#owog_imagesManage_Form').form('submit', {
			url : '${pageContext.request.contextPath}/owogImagesAction!upload.action',
			success : function(data) {
				try {
					var result = jQuery.parseJSON(data);
					/*console.info(result);*/
					if (result.success) {
						$('#owog_imagesManage_imagesDg').datagrid('load', {});
					}
					$.messager.alert('提示', result.msg, 'info');
				} catch (e) {
					$.messager.alert('警告！', data, 'warning');
				} finally {
					$('#owog_imagesManage_Form').form('clear');
				}
			}
		});
	}

	function clearForm() {
		$('#owog_imagesManage_Form').form('clear');
	}
	
	function searchImages(){
		$('#owog_imagesManage_imagesDg').datagrid('load', serializeObject($('#owog_imagesManage_searchForm')));
	}
	
	function clearImages(){
		$('#owog_imagesManage_searchForm').form('clear');
		$('#owog_imagesManage_imagesDg').datagrid('load', {});
	}

	function delImages() {
		var rows = $('#owog_imagesManage_imagesDg').datagrid('getChecked');
		//console.info(rows);
		if (rows.length > 0) {
			$.messager.confirm('确认', '是否确认删除图片 <b>' + rows[0].filename + '</b> ？', function(r) {
					if (r) {
						$.ajax({
							url : '${pageContext.request.contextPath}/owogImagesAction!delete.action',
							data : {
								id : rows[0].id
							},
							dataType : 'json',
							success : function(result) {
								//console.info(r);
								if (result.success) {
									$('#owog_imagesManage_imagesDg').datagrid('load', {});
									$('#owog_imagesManage_imagesDg').datagrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg : result.msg
									});
								} else {
									$.messager.alert('提示', result.msg, 'info');
								}
							},
							error : function(data) {
								$.messager.alert('警告！', data.responseText, 'warning');
							}
						});
					}
				});
		} else {
			$.messager.alert('提示：', '请选择要删除的图片！', 'info');
		}
	}
</script>

<div id="owog_imagesManage_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<form id="owog_imagesManage_Form" method="post" enctype="multipart/form-data">
			<div class="easyui-panel" style="width:90%;padding:23px 350px;" data-options="border:false">
				<div style="margin-bottom:20px">
					<input class="easyui-combobox" label="班组:" name="class_" labelPosition="top"
						style="width:100%;height:52px"
						data-options="editable:false,
						              valueField : 'label',
						              required : true,
						              textField : 'value',
						              data : [ {
						                label : '传输组',
						                value : '传输组'
						              }, {
						                label : '动力组',
						                value : '动力组'
						              }, {
						                label : '分析班',
						                value : '分析班'
						              }, {
						                label : '业务班',
						                value : '业务班'
						              }, {
						                label : '交换组',
						                value : '交换组'
						              }, {
						                label : '支撑班',
						                value : '支撑班'
						              }, {
						                label : '数据组',
						                value : '数据组'
						              } ]">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" label="员工姓名:" name="name" labelPosition="top"
						data-options="required:true" style="width:100%;height:52px">
				</div>
				<div style="margin-bottom:20px">
					<input label="周期开始时间:" labelPosition="top" name="startdate" data-options="required:true"
						class="easyui-datebox" editable="false" style="width: 100%;" />
				</div>
				<div style="margin-bottom:20px">
					<input label="周期结束时间:" labelPosition="top" name="enddate" data-options="required:true"
						class="easyui-datebox" editable="false" style="width: 100%;" />
				</div>
				<div style="margin-bottom:20px">
					<input id="owog_imagesManage_filebox" class="easyui-filebox" name="filedata" label="上传文件:"
						labelPosition="top" style="width:100%" data-options="buttonText:'请选择...',accept:'image/*'">
					<br> <br> <span style="color: red;">(上传文件大小不得超过50M，文件类型为：bmp,jpg,jpeg,gif,png)</span>
				</div>
				<div style="text-align:center;padding:5px 0">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"
						style="width:80px">提交</a> <a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="clearForm()" style="width:80px">重置</a>
				</div>
			</div>
		</form>
	</div>

	<div data-options="region:'south',split:true,title:'历史照片列表',border:false" style="height:35%;">
		<table id="owog_imagesManage_imagesDg">
		</table>
	</div>
</div>

<div id="owog_imagesManage_tb" style="padding:2px 5px;">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		style="float:left;" onclick="delImages();">删除照片(只能删除本人上传的照片)</a>
	<div class="datagrid-btn-separator"></div>
	<form id="owog_imagesManage_searchForm" method="post">
		&nbsp;&nbsp;时间周期: <input class="easyui-datebox" name="startdate"
			style="width:110px"> 至: <input name="enddate" class="easyui-datebox" style="width:110px">
		&nbsp;&nbsp;班组: <select class="easyui-combobox" name="class_" panelHeight="auto" style="width:100px">
			<option value="传输组">传输组</option>
			<option value="动力组">动力组</option>
			<option value="交换组">交换组</option>
			<option value="数据组">数据组</option>
			<option value="分析班">分析班</option>
			<option value="业务班">业务班</option>
			<option value="支撑班">支撑班</option>
		</select>
		&nbsp;&nbsp;标杆人员姓名: <input class="easyui-textbox" name="name" style="width:110px" />
		&nbsp;&nbsp;上传人: <input class="easyui-textbox" name="uploader" style="width:110px" />
		<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="searchImages();">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true" onclick="clearImages();">重置</a>
	</form>
</div>
