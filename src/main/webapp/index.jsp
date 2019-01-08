<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="model.SessionInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");

	String easyuiTheme = "default";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("easyuiTheme")) {
				easyuiTheme = cookies[i].getValue();
				break;
			}
		}
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title>网络综合支撑班综合系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="Index page">

<script type="text/javascript" src="jslib/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="jslib/jq.cookie.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.5.2/plugins/jquery.portal.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-pivotgrid/jquery.pivotgrid.js"></script>
<!-- UMEditor -->
<script type="text/javascript" src="jslib/umeditor1.2.3/third-party/template.min.js"></script>
<script type="text/javascript" charset="utf-8" src="jslib/umeditor1.2.3/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="jslib/umeditor1.2.3/umeditor.min.js"></script>
<script type="text/javascript" src="jslib/umeditor1.2.3/lang/zh-cn/zh-cn.js"></script>

<link id="easyuiTheme" rel="stylesheet"
	href="jslib/jquery-easyui-1.5.2/themes/<%=easyuiTheme%>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="jslib/jquery-easyui-1.5.2/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="myThemes/myIcon.css" type="text/css"></link>
<link rel="stylesheet" href="css/myCss.css" type="text/css"></link>
<!-- UMEditor -->
<link href="jslib/umeditor1.2.3/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="jslib/sockjs-0.3.min.js"></script>
<script type="text/javascript" src="jslib/utils.js"></script>
<script type="text/javascript">
	$(function() {
		$('#index_layout').layout('collapse', 'east');
	});
</script>
</head>
<body id="index_layout" class="easyui-layout">
	<div data-options="region:'north'" style="height: 60px">
		<%
			if (sessionInfo != null) {
		%>
		<jsp:include page="layout/north.jsp"></jsp:include>
		<%
			}
		%>
	</div>
	<div data-options="region:'south'" style="height: 20px"></div>
	<div data-options="region:'west'" style="width: 200px">
		<jsp:include page="layout/west.jsp"></jsp:include>
	</div>
	<div data-options="region:'east',title:'当前日期',split:true" style="width: 200px">
		<jsp:include page="layout/east.jsp"></jsp:include>
	</div>
	<div data-options="region:'center',title:'欢迎使用网络综合支撑班综合系统'">
		<jsp:include page="layout/center.jsp"></jsp:include>
	</div>
	<%
		if (sessionInfo == null) {
	%>
	<jsp:include page="user/login.jsp"></jsp:include>
	<jsp:include page="user/reg.jsp"></jsp:include>
	<%
		}
	%>

</body>
</html>
