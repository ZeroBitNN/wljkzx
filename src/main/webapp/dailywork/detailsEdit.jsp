<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<form id="dailywork_detailsEdit_Form" method="post">
	<div class="easyui-panel" style="width:90%;padding:30px 60px;" data-options="fit:true,border:false">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="ID:" name="id" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="时间:" name="timepoint" labelPosition="top"
				data-options="editable:false" style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="工作内容:" name="content" labelPosition="top"
				data-options="multiline:true,editable:false" style="width:100%;height:100px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="执行情况:" name="status" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,
	              valueField : 'label',
	              textField : 'value',
	              data : [ {
	                label : '未处理',
	                value : '未处理'
	              }, {
	                label : '正常',
	                value : '正常'
	              }, {
	                label : '异常',
	                value : '异常'
	              }, {
	                label : '已发通报',
	                value : '已发通报'
	              }, {
	                label : '已审核',
	                value : '已审核'
	              }, {
	                label : '已完成',
	                value : '已完成'
	              }, {
	                label : '有遗留',
	                value : '有遗留'
	              } ],
	              onChange : function(newValue,oldValue){
								if (newValue=='异常'||newValue=='有遗留'){
									$('#remarkText').textbox({
										multiline:true,
										required:true
									});
								} else {
									$('#remarkText').textbox({
										multiline:true,
										required:false
									});
								}
							 }
	              ">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" id="remarkText" label="情况备注:" name="remark" labelPosition="top"
				data-options="multiline:true" style="width:100%;height:100px"><br> <span
				style="color: red;">(执行情况为：“异常”、“有遗留”时必填)</span>
		</div>
	</div>
</form>
