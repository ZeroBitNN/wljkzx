<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	var performance_perfList_tb = [{
		text : '导出计件工单量详单',
		iconCls : 'icon-exportE',
		handler : function() {
			$('#performance_perfList_exDia').dialog('open');
		}
	},'-',{
		text : '查询历史数据',
		iconCls : 'icon-search',
		handler : function() {
			$('#performance_perfList_historyDia').dialog('open');
		}
	}];

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
			},
			toolbar : performance_perfList_tb
		});
	});
</script>

<div class="easyui-panel" title="计件人员计件绩效表" style="width:700px;height:200px;"
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
				<th
					data-options="field:'jjjx',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2);}">计件绩效</th>
				<th
					data-options="field:'kf',width:100,align:'center',formatter: function(value,row,index){return value.toFixed(2);}">扣罚</th>
				<th data-options="field:'isperf',width:100,align:'center'">是否计件</th>
			</tr>
		</thead>
	</table>
</div>
<div id="performance_perfList_footer" style="padding:5px;">
	<div class="easyui-layout" style="width:100%;height:200px;">
		<div data-options="region:'east',split:true,collapsible:false" style="width:30%;">
			<div id="eastP" class="easyui-panel" title="计件档次分配情况："
				style="width:100%;height:100%;padding:10px;background:#F0F0F0;"
				data-options="closable:false,collapsible:false,href:'performance/levelIntro.jsp'"></div>
		</div>
		<div data-options="region:'west',split:true,collapsible:false" style="width:30%;">
			<div id="westP" class="easyui-panel" title="工单类目说明："
				style="width:100%;height:100%;padding:10px;background:#F0F0F0;"
				data-options="closable:false,collapsible:false,href:'performance/itemIntro.jsp'"></div>
		</div>
		<div data-options="region:'center'" style="width:40%;">
			<div id="centerP" class="easyui-panel" title="计件绩效说明："
				style="width:100%;height:100%;padding:10px;background:#F0F0F0;"
				data-options="closable:false,collapsible:false">
				<p>计件说明：计件绩效按名次划分，第1/2/3/4名在计件人均数基础上按计件一/二/三/四档增加；倒数第1/2/3/4则在计件人均数基础上按计件四/三/二/一档扣除。</p>
			</div>
		</div>
	</div>
</div>

<div id="performance_perfList_exDia" class="easyui-dialog" align="center" style="width:300px"
	data-options="title:'计件人员工单量详单',closed:true,modal:true,buttons:[{
    text:'确定',
    handler:function(){
        var tempUrl = '${pageContext.request.contextPath}/perfAction!doNotNeedSecurity_exportIntro.action?perfdate='+$('#performance_perfList_combobox').combobox('getValue');
		window.open(tempUrl, '_blank');
		$('#performance_perfList_combobox').combobox('unselect');
		$('#performance_perfList_exDia').dialog('close');
    }
  }]">
	<table style="width:80%;text-align:'center';">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td><select id="performance_perfList_combobox" class="easyui-combobox" name="perfdate"
				label="请选择日期:" labelPosition="top" style="width:100%"
				data-options="valueField:'perfdate',textField:'perfdate',
					url:'${pageContext.request.contextPath}/perfAction!doNotNeedSecurity_getPerfdate.action'">
			</select><br></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
	</table>
</div>

<div id="performance_perfList_historyDia" class="easyui-dialog" align="center" style="width:300px"
	data-options="title:'查询历史数据',closed:true,modal:true,buttons:[{
    text:'确定',
    handler:function(){
    	$('#performance_perfList_dg').datagrid('load', {    
		    perfdate :  $('#performance_perfList_hisCb').combobox('getValue')
		});
		$('#performance_perfList_historyDia').dialog('close');
    }
  }]">
	<table style="width:80%;text-align:'center';">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td><select id="performance_perfList_hisCb" class="easyui-combobox" name="perfdate"
				label="请选择时间:" labelPosition="top" style="width:100%"
				data-options="valueField:'perfdate',textField:'perfdate',
					url:'${pageContext.request.contextPath}/perfAction!doNotNeedSecurity_getPerfdate.action'">
			</select><br></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
	</table>
</div>