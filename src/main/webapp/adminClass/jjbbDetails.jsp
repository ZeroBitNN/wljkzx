<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#adminClass_jjbbDetails_Form').form({
			onLoadSuccess : function(data) {
				var tmpStr = data.attachment.split("<br>");
				var linkHtml = "";
				for (var i = 0; i < tmpStr.length; i++) {
					var attached = tmpStr[i].split("</>");
					var name = attached[0];
					var link = attached[1];
					//<a href="url" target='_blank'>Link text</a>
					linkHtml += "<a href='" + link + "' target='_blank'>" + name + "</a><br>";
				}
				//console.info(linkHtml);
				$('#attached').html(linkHtml);
			}
		});
	});
</script>

<form id="adminClass_jjbbDetails_Form" method="post">
	<div style="width:85%;padding:30px 60px;">
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="ID:" name="id" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="日期:" name="datetime" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="发起通知人:" name="notifier" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="类型:" name="type" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="专业:" name="domain" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="通知来源:" name="source" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="工单编号:" name="serialNum" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="交办人:" name="comeFrom" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="标题:" name="title" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" label="内容:" name="content" labelPosition="top"
				data-options="multiline:true" style="width:100%;height:350px" readonly="readonly">
		</div>
		<div style="margin-bottom:10px">
			<input type="text" name="attachment" hidden="hidden"> 
			附件:<br> <span id="attached"></span>
		</div>
	</div>
</form>
