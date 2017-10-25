<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function() {
		$('#portal_link_datalist').datalist({
			method : 'post',
			url : '${pageContext.request.contextPath}/linksAction!doNotNeedSecurity_linksDataList.action',
			fit : true,
			border : false,
			groupField : 'category',
			onSelect : function(index, row) {
				$(this).datalist('unselectAll');
			},
			toolbar : '#portal_link_toolbar'
		});

		$('#portal_link_searchbox').searchbox({
			searcher : function(value, name) {
				//alert(value + "," + name)
				$('#portal_link_datalist').datalist('load', {
					text : value
				});
			},
			prompt : '请输入要搜索的链接名称'
		});
	});
</script>

<div id="portal_link_datalist"></div>

<div id="portal_link_toolbar">
	<input id="portal_link_searchbox" style="width:30%" name="text"></input>
</div>