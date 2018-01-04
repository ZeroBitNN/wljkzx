<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#performance_perfList_dg').datagrid({
			url : '${pageContext.request.contextPath}/perfAction!getPerfDg.action',
			singleSelect : true,
			fitColumns : true,
			border : false,
			fit : true,
			rownumbers : true,
			striped : true,
			onLoadSuccess : function(data) {
				if (data.total == -1) {
					$.messager.alert('警告！', '相关绩效参数未配置！请配置参数并保存。', 'warning');
				}
			}
		});
	});
</script>

<div class="easyui-panel" title="个人绩效表" style="width:700px;height:200px;"
	data-options="footer:'#performance_perfList_footer',fit:true,border:false">
	<table id="performance_perfList_dg">
		<thead>
			<tr>
				<th data-options="field:'id',width:5,hidden:true">ID</th>
				<th data-options="field:'name',width:100,align:'center'">姓名</th>
				<th
					data-options="field:'item1',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2)+'%';}">工单类目1</th>
				<th
					data-options="field:'item2',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2)+'%';}">工单类目2</th>
				<th
					data-options="field:'item3',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2)+'%';}">工单类目3</th>
				<th
					data-options="field:'item4',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2)+'%';}">工单类目4</th>
				<th
					data-options="field:'gdfz',width:200,align:'center',formatter: function(value,row,index){return value.toFixed(0);}">按工单实际占比计算工单分值</th>
				<th data-options="field:'otheritem',width:100,align:'center'">其它类目</th>
				<th
					data-options="field:'zzhz',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(0);}">最终汇总</th>
				<th data-options="field:'ranking',width:100,align:'center'">排名</th>
				<th data-options="field:'grjx',width:100,align:'center'">个人绩效</th>
				<th data-options="field:'jjjx',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2);}">计件绩效</th>
				<th data-options="field:'kf',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2);}">扣罚</th>
				<th data-options="field:'isperf',width:100,align:'center'">是否计件</th>
			</tr>
		</thead>
	</table>
</div>
<div id="performance_perfList_footer" style="padding:5px;">Footer Content.</div>