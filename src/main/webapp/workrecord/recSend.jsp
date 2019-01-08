<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="workrecord_recSend_Form" method="post">
	<div class="easyui-panel" data-options="fit:true,border:false" style="width:90%;padding:30px 60px;">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="ID:" name="id" labelPosition="top" data-options="" style="width:100%;height:52px"
				readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="处理人:" name="handler" labelPosition="top" style="width:100%;height:52px"
				data-options="editable:false,
        url:'${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
        valueField:'username',textField:'username'">
		</div>
	</div>
</form>