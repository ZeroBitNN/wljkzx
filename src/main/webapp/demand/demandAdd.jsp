<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#isShowBtn').switchbutton({
			onText : '是',
			offText : '否',
			checked : true,
			value : '是'
		});
	});
</script>

<form id="demand_demandAdd_Form" method="post">
	<div class="easyui-panel" style="width:90%;padding:30px 60px;" data-options="fit:true,border:false">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="ID:" name="id" labelPosition="top"
				data-options="editable:false" style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="内容:" name="content" labelPosition="top"
				data-options="multiline:true,required:true" style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<label class="label-top">是否显示：</label> <input id="isShowBtn" class="easyui-switchbutton"
				name="isshow">
		</div>
	</div>
</form>
