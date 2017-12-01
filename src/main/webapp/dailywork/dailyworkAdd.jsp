<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {});
</script>

<form id="dailywork_dailyworkAdd_Form" method="post">
	<div class="easyui-panel" style="width:90%;padding:30px 60px;" data-options="fit:true,border:false">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="时间点:" name="timepoint" labelPosition="top"
				data-options="required:true,validType:'eqTimepoint'" style="width:100%;height:52px"><br>
			<span style="color: red;">(格式："hh:mm"，如："09:05"，多个时间点用半角"|"分隔，如："10:00|12:00|14:00")</span>
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="专业类型:" name="category" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,
		        url:'${pageContext.request.contextPath}/orderAction!doNotNeedSecurity_getOrderCategory.action',
		        valueField:'id',textField:'name'">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="工作内容:" name="content" labelPosition="top"
				data-options="multiline:true,required:true" style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="发布人:" name="publisher" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,
		        url:'${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
		        valueField:'username',textField:'username'">
		</div>
		<div style="margin-bottom:20px">
			<input label="发布时间:" labelPosition="top" name="releasetime" data-options="required:true"
				value="now();" class="easyui-datetimebox" editable="false" style="width: 150px;" />
		</div>
	</div>
</form>