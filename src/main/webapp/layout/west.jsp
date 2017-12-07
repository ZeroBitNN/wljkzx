<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function _init() {
		$.ajax({
			url : '${pageContext.request.contextPath}/repairAction!init.action',
			success : function(r) {
				//console.info(r);
				if (r) {
					$.messager.alert('提示！', r, 'info');
				} else {
					$.messager.alert('成功！', '系统初始化成功！', 'info');
				}
			}
		});
	}

	function repairAdmin() {
		$.ajax({
			url : '${pageContext.request.contextPath}/repairAction!repairAdmin.action',
			success : function(r) {
				//console.info(r);
				if (r) {
					$.messager.alert('提示！', r, 'info');
				} else {
					$.messager.alert('成功！', '超级管理员账号修复成功！', 'info');
				}
			}
		});
	}

	function repairMenu() {
		$.ajax({
			url : '${pageContext.request.contextPath}/repairAction!initMenu.action',
			success : function(r) {
				//console.info(r);
				if (r) {
					$.messager.alert('提示！', r, 'info');
				} else {
					$.messager.alert('成功！', '菜单更新成功！', 'info');
				}
			}
		});
	}

	function repairResources() {
		$.ajax({
			url : '${pageContext.request.contextPath}/repairAction!initResources.action',
			success : function(r) {
				//console.info(r);
				if (r) {
					$.messager.alert('提示！', r, 'info');
				} else {
					$.messager.alert('成功！', '权限更新成功！', 'info');
				}
			}
		});
	}

	function WSTest() {
		var url = '${pageContext.request.contextPath}/wstest.jsp';
		addTab({
			title : 'WebSocket测试',
			href : url,
			closable : true
		});
	}

</script>

<div class="easyui-panel" data-options="title:'功能导航',border:false,fit:true">
	<div class="easyui-accordion" data-options="fit:true,border:false">
		<div title="系统菜单"
			data-options="iconCls:'icon-menu',
		selected:true,
		tools:[{
         iconCls:'icon-reload',
         handler:function(){
             $('#layout_west_tree').tree('reload');
         }
     }]
		">
			<ul id="layout_west_tree" class="easyui-tree"
				data-options="url:'${pageContext.request.contextPath}/menuAction!doNotNeedSecurity_getAllMenu.action',
					  parentField:'pid',
					  lines:true,
            onClick: function(node){
              if(node.attributes.url){
                var url='${pageContext.request.contextPath}'+node.attributes.url
                addTab({title:node.text,href:url,closable:true});
              }
            }">
			</ul>
		</div>
		<!-- <div title="Title2" data-options="iconCls:'icon-reload'"></div>  -->
		<div title="超级管理员菜单" data-options="iconCls:'icon-myAdmin'">
			<ul>
				<li><a href="javascript:void(0);" onclick="_init();">系统初始化</a></li>
				<li><a href="javascript:void(0);" onclick="repairAdmin();">修复管理员账号</a></li>
				<li><a href="javascript:void(0);" onclick="repairMenu();">更新菜单</a></li>
				<li><a href="javascript:void(0);" onclick="repairResources();">更新权限</a></li>
				<li><a href="javascript:void(0);" onclick="WSTest();">WebSocket测试</a></li>
				<!-- 
				<li><a href="${pageContext.request.contextPath}/knowledge/knowledge.jsp" target="_blank">知识库</a></li>
				 -->
			</ul>
		</div>
	</div>
</div>