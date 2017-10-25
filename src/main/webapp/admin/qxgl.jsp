<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {});
</script>

<table class="easyui-datagrid"
	data-options="
                singleSelect:true,
                title:'权限列表',
                url:'${pageContext.request.contextPath}/resourcesAction!getRes.action',
                fit:true,
                border:false,
                fitColumns:true,
                onLoadError:function(){
					        $.messager.alert('警告！', '您没有访问此功能的权限！请联系管理员给你赋予相应权限。', 'warning');
					      },
                view:groupview,
                groupField:'pid',
                groupFormatter:function(value,rows){
                    return value + ' - ' + rows.length + ' Item(s)';
                }
            ">
	<thead>
		<tr>
			<th data-options="field:'id',width:80,hidden:true">ID</th>
			<th data-options="field:'name',width:80">权限名称</th>
			<th data-options="field:'pid',width:100">权限组</th>
			<th data-options="field:'url',width:80">权限资源</th>
			<th data-options="field:'description',width:80">描述</th>
		</tr>
	</thead>
</table>
