<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.SessionInfo"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<script type="text/javascript">
	var websocket = null;
	$(function() {
		//判断当前浏览器是否支持WebSocket
		if ('WebSocket' in window) {
			websocket = new WebSocket("ws://localhost:"+<%=request.getServerPort()%>+"/wljkzx/dailyworkSocket");
		} else {
			$.messager.alert('警告！', '当前浏览器 不支持WebSocket,将不会收到弹窗提示。如需要使用弹窗提示功能，请更换浏览器！', 'warning');
		}
		
		//连接发生错误的回调方法
		websocket.onerror = function() {
			$.messager.alert('警告！','WebSocket连接发生错误，请重新登陆！','warning');
		};
		
		//连接成功建立的回调方法
		//websocket.onopen = function() {
			//$.messager.alert('提示','WebSocket连接成功');
		//}
		
		//接收到消息的回调方法
		websocket.onmessage = function(event) {
			//setMessageInnerHTML(event.data);
			$.messager.alert('提示',event.data);
		}
		
		window.onbeforeunload = function(){
			if (websocket){
				websocket.close();
			}
		}
				
		$('#layout_north_loginForm').form({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_login.action',
			success : function(data) {
				var result = jQuery.parseJSON(data);
				if (result.success) {
					$('#layout_north_loginDialog').dialog('close');
				}
				$.messager.show({
					title : '提示：',
					msg : result.msg
				});
			}
		});

		$('#layout_north_pwdForm').form({
			url : '${pageContext.request.contextPath}/userAction!doNotNeedSecurity_modifyPwd.action',
			success : function(data) {
				var result = jQuery.parseJSON(data);
				if (result.success) {
					$('#layout_north_pwdForm input').val('');
					$('#layout_north_pwdDialog').dialog('close');
				}
				$.messager.show({
					title : '提示：',
					msg : result.msg
				});
			}
		});

		$('#layout_north_pwdForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#layout_north_pwdForm').submit();
			}
		});
	});

	var logoutFun = function() {
		if (websocket){
			websocket.close();
		}
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/userAction!doNotNeedSecurity_logout.action",
			dataType : "json",
			success : function(result) {
				$.messager.alert({
					title : '提示：',
					closable : false,
					msg : result.msg,
					fn : function() {
						location.replace('${pageContext.request.contextPath}/index.jsp');
					}
				});
			}
		});
	};

	var lockWindowFun = function() {
		$.post('${pageContext.request.contextPath}/userAction!doNotNeedSecurity_logout.action', function(result) {
			$('#layout_north_loginForm input[name=pwd]').val('');
			$('#layout_north_loginDialog').dialog('open');
		}, 'json');
	};
</script>

<div
	style="font-size:x-large;color:#FF9900;font-weight:bold; position: absolute; left: 20px; top: 10px;">
	网&nbsp;&nbsp;络&nbsp;&nbsp;综&nbsp;&nbsp;合&nbsp;&nbsp;支&nbsp;&nbsp;撑&nbsp;&nbsp;班 <span
		class="panel-title" style="font-size:small;font-weight: bole;">综&nbsp;合&nbsp;办&nbsp;公&nbsp;系&nbsp;统</span>
</div>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 5px;">
	<%
		if (sessionInfo != null) {
			try {
			out.print(util.StringUtil.formateString("<b>欢迎您，{0}</b>",
					sessionInfo.getUser().getRole() + " " + sessionInfo.getUser().getUsername()));
			} catch (Exception e) {
				sessionInfo = null;
				response.sendRedirect("${pageContext.request.contextPath}/index.jsp");
			}
		}
	%>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-controlpanel'">控制面板</a> <a
		href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_zxMenu',iconCls:'icon-logout'">注销</a>
</div>

<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'icon-modifypwd'" onclick="$('#layout_north_pwdDialog').dialog('open');">修改密码</div>
	<!-- 
	  <div class="menu-sep"></div>
	  <div data-options="iconCls:''" onclick="showMyInfoFun();">我的信息</div>
	   -->
	<div class="menu-sep"></div>
	<div data-options="iconCls:'icon-myTheme'">
		<span>更换皮肤</span>
		<div id="layout_north_themeMenu" style="width: 150px; display: none;">
			<div onclick="changeTheme('default');" title="default">default</div>
			<div onclick="changeTheme('gray');" title="gray">gray</div>
			<div onclick="changeTheme('material');" title="gray">material</div>
			<div onclick="changeTheme('metro');" title="metro">metro</div>
			<div onclick="changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
			<div onclick="changeTheme('black');" title="black">black</div>
			<div class="menu-sep"></div>
			<div onclick="changeTheme('ui-cupertino');" title="black">ui-cupertino</div>
			<div onclick="changeTheme('ui-dark-hive');" title="black">ui-dark-hive</div>
			<div onclick="changeTheme('ui-pepper-grinder');" title="black">ui-pepper-grinder</div>
			<div onclick="changeTheme('ui-sunny');" title="black">ui-sunny</div>
		</div>
	</div>
</div>

<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'icon-mylock'" onclick="lockWindowFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'icon-logout'" onclick="logoutFun();">退出系统</div>
</div>

<div id="layout_north_loginDialog" class="easyui-dialog"
	data-options="title:'解锁',modal:true,closed:true,closable:false,buttons:[{
        text:'解锁',
        iconCls:'icon-help',
        handler:function(){
          $('#layout_north_loginForm').submit();
        }
      }]">

	<form id="layout_north_loginForm" method="post">
		<table>
			<tr>
				<th>用户名</th>
				<td><%=sessionInfo.getUser().getUsername()%><input name="username" readonly="readonly"
					type="hidden" value="<%=sessionInfo.getUser().getUsername()%>" /></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" type="password" class="easyui-validatebox"
					data-options="required:true" /></td>
			</tr>
		</table>
	</form>
</div>

<div id="layout_north_pwdDialog" class="easyui-dialog"
	data-options="title:'修改密码',modal:true,closed:true,buttons:[{
        text:'修改',
        iconCls:'icon-help',
        handler:function(){
          $('#layout_north_pwdForm').submit();
        }
      }]">

	<form id="layout_north_pwdForm" method="post">
		<table>
			<tr>
				<th>新密码</th>
				<td><input name="pwd" type="password" class="easyui-validatebox"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th>重复密码</th>
				<td><input type="password" class="easyui-validatebox"
					data-options="required:true,validType:'eqPwd[\'#layout_north_pwdForm input[name=pwd]\']'" /></td>
			</tr>
			<tr>
				<td><input name="username" readonly="readonly" type="hidden"
					value="<%=sessionInfo.getUser().getUsername()%>" /></td>
				<td><input name="id" readonly="readonly" type="hidden"
					value="<%=sessionInfo.getUser().getId()%>" /></td>
			</tr>
		</table>
	</form>
</div>
