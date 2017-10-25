<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">

	$(function() {
		$('#portal_guarantee_datalist').datalist({
			method : 'post',
			url : '${pageContext.request.contextPath}/guaranteeAction!doNotNeedSecurity_guaranteeDataList.action',
			fit : true,
			border : false,
			valueField : 'id',
			textField : 'title',
			onDblClickRow : function(index, row) {
				//console.log(row); //Object { id: "0", page: 0, rows: 0, title: "近期暂无保障" }
				if (row.id != '0') {
					var url = '${pageContext.request.contextPath}/guarantee/guaranteeDetails.jsp';
					addTab({
						title : '通讯保障',
						href : url,
						queryParams : {
							id : row.id
						},
						closable : true
					});
				}
			},
			toolbar : '#portal_guarantee_toolbar'
		});
		
		$('#portal_guarantee_searchbox').searchbox({
			searcher : function(value, name) {
				//alert(value + "," + name)
				$('#portal_guarantee_datalist').datalist('load', {
					content : value
				});
			},
			prompt : '请输入要搜索的内容'
		});
	});
</script>

<div id="portal_guarantee_datalist"></div>

<div id="portal_guarantee_toolbar">
	<input id="portal_guarantee_searchbox" style="width:30%" name="content"></input>
</div>