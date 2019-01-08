<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$.ajax({
			url : '${pageContext.request.contextPath}/perfParamAction!doNotNeedSecurity_itemList.action',
			dataType : 'html',
			success : function(data) {
				$('#itemIntro').html(data);
			}
		});
	});
</script>

<div style="overflow:hidden;height:100%;width:90%;">
	<ul style="padding:5px;" id="itemIntro"></ul>
</div>
