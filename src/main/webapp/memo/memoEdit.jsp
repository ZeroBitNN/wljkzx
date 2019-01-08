<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="memo_memoEdit_editForm" method="post">
	<div class="easyui-panel" data-options="fit:true,border:false" style="width:90%;padding:30px 60px;">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="ID:" name="id" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="交班人:" name="memofrom" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,
				url:'${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
				valueField:'username',textField:'username'">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="接班人:" name="memoto" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,
        url:'${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getAllUser.action',
        valueField:'username',textField:'username'">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="日志类型:" name="memotype" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,valueField : 'label',
              textField : 'value',
              data : [ {
                label : '普通交班',
                value : '普通交班'
              }, {
                label : '领导交办',
                value : '领导交办'
              } ]">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="班次:" name="classes" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,valueField : 'label',
              textField : 'value',
              data : [ {
                label : '早班',
                value : '早班'
              }, {
                label : '中班',
                value : '中班'
              }, {
                label : '晚班',
                value : '晚班'
              } ]">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-datetimebox" label="记录时间:" name="createtime" labelPosition="top"
				style="width:100%;height:52px" data-options="editable:false">
		</div>
		<div style="margin-bottom:20px">
			<input class=easyui-textbox label="值班记事:" labelPosition="top" data-options="multiline:true"
				name="content" labelPosition="top" style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="其他记事:" name="othercontent" labelPosition="top"
				data-options="multiline:true" style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="遗留问题:" name="leaveover" labelPosition="top"
				data-options="multiline:true" style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-datetimebox" label="修改时间:" name="modifytime" labelPosition="top"
				style="width:100%;height:52px" data-options="editable:false">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="状态:" name="status" labelPosition="top"
				style="width:100%;height:52px"
				data-options="editable:false,valueField : 'label',
              textField : 'value',
              data : [ {
                label : '已完成',
                value : '已完成'
              }, {
                label : '未完成',
                value : '未完成'
              } ]">
		</div>
	</div>
</form>