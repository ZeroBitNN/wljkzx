<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<form id="guarantee_gNoticeAdd_Form" method="post">
	<div class="easyui-panel" style="width:90%;padding:30px 60px;"
		data-options="fit:true,border:false">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="ID:" name="id"
				labelPosition="top" data-options="editable:false"
				style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-datebox" label="日期:" name="noticedate"
				labelPosition="top" data-options="required:true,editable:false"
				style="width:100%;height:52px" value="new Date();">
		</div>
		<div style="margin-bottom:20px">
			<input id="dt" class="easyui-timespinner" label="时间:"
				name="noticetime" labelPosition="top"
				data-options="required:true,validType:'eqTimepoint'"
				style="width:100%;height:52px"><br> <span
				style="color: red;">(格式："hh:mm"，如："09:05")</span>
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="易信通报内容内容:" name="content"
				labelPosition="top" data-options="multiline:true"
				style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="通报人:" name="recorder"
				labelPosition="top" data-options="editable:false"
				style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="备注或问题记录:" name="remarks"
				labelPosition="top" data-options="multiline:true"
				style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="关联ID:" name="related"
				labelPosition="top" data-options="editable:false"
				style="width:100%;height:52px">
		</div>
	</div>
</form>
