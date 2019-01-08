<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="contacts_contacts_Form" method="post">
	<div class="easyui-panel" data-options="fit:true,border:false" style="width:90%;padding:30px 60px;">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="ID:" name="id" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="联系人:" name="name" labelPosition="top" data-options="required:true"
				style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="联系号码:" name="phonenumber" labelPosition="top" data-options="required:true"
				style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="所属部门:" name="dept" labelPosition="top" data-options="required:true"
				style="width:100%;height:52px">
		</div>
	</div>
</form>