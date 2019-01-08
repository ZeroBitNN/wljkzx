<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(function() {
		$('#user_login_loginForm').form({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_login.action',
			success : function(data) {
				var result = jQuery.parseJSON(data);
				if (result.success) {
					$('#user_login_loginDialog').dialog('close');
					// 登陆成功后才加载north、center面板
					var northPanel = $('#index_layout').layout('panel', 'north');
					northPanel.panel('refresh', 'layout/north.jsp');
					var centerPanel = $('#index_layout').layout('panel', 'center');
					centerPanel.panel('refresh', 'layout/center.jsp');
				}
				$.messager.show({
					title : '提示：',
					msg : result.msg
				});
			}
		});

		$('#user_login_loginForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#user_login_loginForm').submit();
			}
		});

		window.setTimeout(function() {
			$('#user_login_loginForm input[name=username]').focus();
		}, 0)
	});

	function userLogin() {
		$('#user_login_loginForm').submit();
	}
</script>

<div id="user_login_loginDialog" class="easyui-dialog" data-options="title:'系统登录',modal:true,closable:false">
	<form id="user_login_loginForm" method="post">
		<div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;" border="false">
			<div style="margin-bottom:10px">
				<input class="easyui-textbox" name="username" style="width:100%;height:35px;padding:12px"
					data-options="prompt:'Username',iconCls:'icon-man',iconWidth:38">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" type="password" name="pwd" style="width:100%;height:35px;padding:12px"
					data-options="prompt:'Password',iconCls:'icon-lock',iconWidth:38">
			</div>
			<div>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-myLogin'" style="padding:5px 0px;width:100%;"
					onclick="userLogin();"> <span style="font-size:14px;">登录</span>
				</a>
			</div>
		</div>
	</form>
</div>