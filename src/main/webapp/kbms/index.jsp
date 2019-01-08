<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<title>网操维知识库系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="网操维,网络操作维护中心,知识库">
<meta http-equiv="description" content="网操维知识库系统">
<!-- 引入jQuery及其工具 -->
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="../jslib/jq.cookie.js"></script>
<!-- 引入EasyUI及其扩展工具 -->
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/plugins/jquery.portal.js"></script>
<script type="text/javascript" src="../jslib/utils.js"></script>
<!-- 默认的CSS主题样式 -->
<link id="easyuiTheme" rel="stylesheet"
	href="../jslib/jquery-easyui-1.5.2/themes/default/easyui.css" type="text/css"></link>
<!-- 引入图标样式 -->
<link rel="stylesheet" href="../jslib/jquery-easyui-1.5.2/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="../myThemes/myIcon.css" type="text/css"></link>

<script type="text/javascript">
	$(function() {
		$('#kbms_index_loginBtn').bind('click', function() {
			$('#kbms_index_loginDialog').dialog('open');
		});
		
		$('#kbms_index_pwdForm').form({
			url : '',
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
	});
</script>
</head>

<!-- 
<a href="http://www.w3school.com.cn/">Visit W3School!</a>
 -->
<body id="kbms_index_layout" class="easyui-layout">
	<c:set var="sessionInfo" value="${sessionScope.kbmsSessionInfo}" />
	<c:choose>
		<c:when test="${not empty sessionInfo}">
			<c:set var="centerTitle" value="${sessionInfo.getUser().getUsername() },您好！欢迎使用网操维知识库系统！" />
		</c:when>
		<c:otherwise>
			<c:set var="centerTitle" value="游客，您好！欢迎使用网操维知识库系统！" />
		</c:otherwise>
	</c:choose>
	<div data-options="region:'north',split:false" style="height:60px;background:#E7F0FF;">
		<div style="position: absolute; left: 10px; top: 0px;height:100%;">
			<table border="0" height="100%">
				<tr>
					<td>Logo</td>
					<td>搜索栏</td>
					<td>提交按钮</td>
				</tr>
			</table>
		</div>
		<c:choose>
			<c:when test="${not empty sessionInfo}">
				<div style="position: absolute; right: 10px; bottom: 15px;">
					<a href="javascript:void(0);" class="easyui-menubutton"
						data-options="menu:'#kbms_index_kzmbMenu',iconCls:'icon-controlpanel'">控制面板</a> <a
						href="javascript:void(0);" class="easyui-menubutton"
						data-options="menu:'#kbms_index_zxMenu',iconCls:'icon-logout'">注销</a>
				</div>
			</c:when>
			<c:otherwise>
				<div style="position: absolute; right: 10px; bottom: 15px;">
					<a id="kbms_index_loginBtn" href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-myLogin'">用户登录 </a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div data-options="region:'south',split:false,collapsible:false"
		style="height:30px;background:#E7F0FF;"></div>
	<div data-options="region:'west',title:'知识目录',split:true,collapsible:false" style="width:200px;"></div>
	<div data-options="region:'center',title:'${centerTitle }'" style="padding:5px;"></div>

	<!-- 用户登录对话框 -->
	<div id="kbms_index_loginDialog" class="easyui-dialog"
		data-options="title:'用户登录',modal:true,closable:true,closed:true">
		<form id="kbms_index_loginForm" method="post">
			<div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;" border="false">
				<div style="margin-bottom:10px">
					<input class="easyui-textbox" name="username" style="width:100%;height:35px;padding:12px"
						data-options="prompt:'Username',iconCls:'icon-man',iconWidth:38">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" type="password" name="pwd"
						style="width:100%;height:35px;padding:12px"
						data-options="prompt:'Password',iconCls:'icon-lock',iconWidth:38">
				</div>
				<div>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-myLogin'"
						style="padding:5px 0px;width:100%;" onclick="kbms_index_userLogin();"> <span
						style="font-size:14px;">登录</span>
					</a>
				</div>
			</div>
		</form>
	</div>

	<!-- 控制面板菜单 -->
	<div id="kbms_index_kzmbMenu" style="width: 100px; display: none;">
		<div data-options="iconCls:'icon-add'" onclick="showMyInfoFun();">新增知识</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-modifypwd'" onclick="$('#kbms_index_pwdDialog').dialog('open');">修改密码</div>
	</div>

	<!-- 用户注销菜单 -->
	<div id="kbms_index_zxMenu" style="width: 100px; display: none;">
		<div data-options="iconCls:'icon-mylock'" onclick="lockWindowFun();">锁定窗口</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-logout'" onclick="logoutFun();">退出系统</div>
	</div>

	<!-- 修改密码对话框 -->
	<div id="kbms_index_pwdDialog" class="easyui-dialog"
		data-options="title:'修改密码',modal:true,closed:true,buttons:[{
        text:'修改',
        iconCls:'icon-help',
        handler:function(){
          $('#kbms_index_pwdForm').submit();
        }
      }],
      onClose:function(){
      	$('#pwdTextbox').textbox('clear');
      	$('#rePwdTextbox').textbox('clear');
      }">

		<form id="kbms_index_pwdForm" method="post">
			<div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;" border="false">
				<div style="margin-bottom:10px">
					<div>新密码:</div>
					<input class="easyui-textbox" type="password" id="pwdTextbox" name="pwd"
						style="width:100%;height:35px;padding:12px"
						data-options="prompt:'新密码',iconCls:'icon-lock',iconWidth:38,required:true">
				</div>
				<div style="margin-bottom:10px">
					<div>重复密码:</div>
					<input class="easyui-textbox" type="password" id="rePwdTextbox" name="rePwd"
						style="width:100%;height:35px;padding:12px"
						data-options="prompt:'重复密码',iconCls:'icon-lock',iconWidth:38,required:true,validType:'eqPwd[\'#kbms_index_pwdForm input[name=pwd]\']'">
				</div>
				<input name="username" readonly="readonly" type="hidden"
						value="${sessionInfo.getUser().getUsername() }" />
				<input name="id" readonly="readonly" type="hidden"
						value="${sessionInfo.getUser().getId() }" />
			</div>
		</form>
	</div>
</body>
</html>
