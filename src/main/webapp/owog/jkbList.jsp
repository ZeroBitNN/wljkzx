<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var jkbEditRow = undefined;
	$(function() {
		$('#owog_jkbList_jkbDg').datagrid({
			fit : true,
			nowrap : false,
			striped : true,
			border : false,
			rownumbers : true,
			singleSelect : true,
			url : '${pageContext.request.contextPath}/owogJkbAction!datagrid.action',
			method : 'get',
			onDblClickRow : function(rowIndex, rowData) {
				if (jkbEditRow != undefined) {
					$('#owog_jkbList_jkbDg').datagrid('endEdit', jkbEditRow);
				}

				if (jkbEditRow == undefined) {
					$('#owog_jkbList_jkbDg').datagrid('beginEdit', rowIndex);
					jkbEditRow = rowIndex;
					$('#owog_jkbList_jkbDg').datagrid('unselectAll');
				}
			},
			onLoadError : function() {
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			toolbar : [{
				text : '导出模板',
				iconCls : 'icon-exportE',
				handler : function() {
					exportJkb();
				}
			}, {
				text : '按模板导入',
				iconCls : 'icon-importE',
				handler : function() {
					$('#owog_jkbList_filebox').textbox('clear');
					$('#owog_jkbList_importDia').dialog('open');
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (jkbEditRow != undefined) {
						$('#owog_jkbList_jkbDg').datagrid('endEdit', jkbEditRow);
					}
				}
			}, {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					$('#owog_jkbList_jkbDg').datagrid('unselectAll');
					$('#owog_jkbList_jkbDg').datagrid('rejectChanges');
					jkbEditRow = undefined;
				}
			}, '-', {
				text : '计算本周分数及排名',
				iconCls : 'icon-calc',
				handler : function() {
					jkbCalc();
				}
			}, {
				text : '查询历史数据',
				iconCls : 'icon-search',
				handler : function() {
					$('#owog_jkbList_historyDia').dialog('open');
				}
			}],
			onAfterEdit : function(rowIndex, rowData, changes) {
				var updated = $('#owog_jkbList_jkbDg').datagrid('getChanges', 'updated');
				if (updated.length < 1) {
					jkbEditRow = undefined;
					$('#owog_jkbList_jkbDg').datagrid('unselectAll');
					return;
				}
				$.ajax({
					url : '${pageContext.request.contextPath}/owogJkbAction!edit.action',
					type : "POST",
					data : rowData,
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							$('#owog_jkbList_jkbDg').datagrid('acceptChanges');
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
							jkbEditRow = undefined;
							$('#owog_jkbList_jkbDg').datagrid('reload');
						} else {
							/*datagrid.datagrid('rejectChanges');*/
							$('#owog_jkbList_jkbDg').datagrid('beginEdit', jkbEditRow);
							$.messager.alert('错误', r.msg, 'error');
						}
						$('#owog_jkbList_jkbDg').datagrid('unselectAll');
					},
					error : function(data) {
						$.messager.alert('警告！', data.responseText, 'warning');
					}
				});
			}
		});
	});

	function exportJkb() {
		//var tempUrl = "${pageContext.request.contextPath}/owogJkbAction!doNotNeedSecurity_exportExcel.action";
		var tempUrl = "${pageContext.request.contextPath}/owogJkbAction!doNotNeedSecurity_downloadExcel.action";
		window.open(tempUrl, "_blank");
	}

	function jkbCalc() {
		$.ajax({
			url : '${pageContext.request.contextPath}/owogJkbAction!calc.action',
			type : "POST",
			dataType : 'json',
			success : function(r) {
				if (r.success) {
					$.messager.show({
						msg : r.msg,
						title : '成功'
					});
					$('#owog_jkbList_jkbDg').datagrid('reload');
				} else {
					$.messager.alert('错误', r.msg, 'error');
				}
			},
			error : function(data) {
				$.messager.alert('警告！', data.responseText, 'warning');
			}
		});
	}
</script>

<table id="owog_jkbList_jkbDg">
	<thead>
		<tr>
			<th data-options="field:'id',hidden:true" rowspan="3">ID</th>
			<th data-options="field:'rangetime',width:100" rowspan="3">时间周期</th>
			<th data-options="field:'name',width:80,editor:'textbox'" rowspan="3">姓名</th>
			<th colspan="8">业绩指标(90分)</th>
			<th data-options="field:'ldpf',editor:{type:'numberbox',options:{precision:0}}" rowspan="3">领导评分(10分)</th>
			<th data-options="field:'qzzbsum'" rowspan="3">权重指标合计</th>
			<th colspan="6">加分指标</th>
			<th data-options="field:'allsum'" rowspan="3">最终合计</th>
			<th data-options="field:'ranking'" rowspan="3">排名</th>
			<th colspan="5">一票否决指标</th>
		</tr>
		<tr>
			<th colspan="2">班组计件类工单处理量(30分)</th>
			<th colspan="2">网络大面积故障管控及网络割接管控量(30分)</th>
			<th colspan="2">班组工位综运单、电子运维生产任务单处理(20分)</th>
			<th colspan="2">领导交办事件处理(10分)</th>
			<th>岗位创新</th>
			<th>分公司重大专项</th>
			<th>倒三角服务确认书</th>
			<th>重要客户表扬</th>
			<th>积极参加工会/部门等团体活动</th>
			<th>因班组工作安排临时加班/顶班</th>
			<th data-options="field:'ypfj1',editor:{type:'checkbox',options:{on:'是',off:'否'}}" rowspan="2">考勤缺勤、工休、外出培训、无故迟到、旷工</th>
			<th data-options="field:'ypfj2',editor:{type:'checkbox',options:{on:'是',off:'否'}}" rowspan="2">重要客户投诉</th>
			<th data-options="field:'ypfj3',editor:{type:'checkbox',options:{on:'是',off:'否'}}" rowspan="2">服务事件</th>
			<th data-options="field:'ypfj4',editor:{type:'checkbox',options:{on:'是',off:'否'}}" rowspan="2">工单处理延误、错误等导致其它部门投诉、或上级领导提出问题</th>
			<th data-options="field:'ypfj5',editor:{type:'checkbox',options:{on:'是',off:'否'}}" rowspan="2">网络大面积故障管控、网络割接管控缺失</th>
		</tr>
		<tr>
			<th data-options="field:'zb11',editor:{type:'numberbox',options:{precision:0}}">本周完成情况(70%)</th>
			<th data-options="field:'zb12',editor:{type:'numberbox',options:{precision:0}}">环比提升情况(30%)</th>
			<th data-options="field:'zb21',editor:{type:'numberbox',options:{precision:0}}">本周完成情况(70%)</th>
			<th data-options="field:'zb22',editor:{type:'numberbox',options:{precision:0}}">环比提升情况(30%)</th>
			<th data-options="field:'zb31',editor:{type:'numberbox',options:{precision:0}}">本周完成情况(70%)</th>
			<th data-options="field:'zb32',editor:{type:'numberbox',options:{precision:0}}">环比提升情况(30%)</th>
			<th data-options="field:'zb41',editor:{type:'numberbox',options:{precision:0}}">本周完成情况(70%)</th>
			<th data-options="field:'zb42',editor:{type:'numberbox',options:{precision:0}}">环比提升情况(30%)</th>
			<th data-options="field:'jf1',editor:{type:'numberbox',options:{precision:0}}">获市级奖励加10分/项，区级奖励加20分/项</th>
			<th data-options="field:'jf2',editor:{type:'numberbox',options:{precision:0}}">组织者加10分/项，参与者3分/项</th>
			<th data-options="field:'jf3',editor:{type:'numberbox',options:{precision:0}}">加5分/份</th>
			<th data-options="field:'jf4',editor:{type:'numberbox',options:{precision:0}}">加5分/次</th>
			<th data-options="field:'jf5',editor:{type:'numberbox',options:{precision:0}}">加3分/项</th>
			<th data-options="field:'jf6',editor:{type:'numberbox',options:{precision:0}}">加3分/天</th>
		</tr>
	</thead>
</table>

<div id="owog_jkbList_importDia" class="easyui-dialog" align="center" style="width:500px"
	data-options="title:'导入EXCEL文件',closed:true,modal:true,buttons:[{
    text:'导入',
    handler:function(){
      $('#owog_jkbList_importForm').form('submit',{
        url:'${pageContext.request.contextPath}/owogJkbAction!importExcel.action',
        success:function(data){
	        try{
	         var result = jQuery.parseJSON(data);
	         if (result.success) {
	           $('#owog_jkbList_importDia').dialog('close');
	           $('#owog_jkbList_jkbDg').datagrid('reload');
	           $('#owog_jkbList_combobox').combobox('reload');
	         }
	         $.messager.alert({
	           title : '提示：',
	           msg : result.msg,
	           width: 400,
	           height: 200
	         });                   
	        } catch(e) {
	          $.messager.alert('警告！', data, 'warning');
	        }
        }
      });
    }
  }]">
	<form id="owog_jkbList_importForm" method="post" enctype="multipart/form-data">
		<table style="width:80%;text-align:'center';">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td><input id="owog_jkbList_filebox" class="easyui-filebox" name="filedata" label="选择文件:"
					labelPosition="top" style="width:100%"
					data-options="buttonText:'请选择...',accept:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel'"><br>
					<span style="color: red;">(文件大小不得超过50M，文件类型为：xls,xlsx)</span></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
		</table>
	</form>
</div>

<div id="owog_jkbList_historyDia" class="easyui-dialog" align="center" style="width:300px"
	data-options="title:'支撑班一周一标杆历史数据',closed:true,modal:true,buttons:[{
    text:'确定',
    handler:function(){
    	$('#owog_jkbList_jkbDg').datagrid('load', {    
		    rangetime :  $('#owog_jkbList_combobox').combobox('getValue')
		});
		$('#owog_jkbList_historyDia').dialog('close');
    }
  }]">
	<table style="width:80%;text-align:'center';">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td><select id="owog_jkbList_combobox" class="easyui-combobox" name="rangetime"
				label="请选择时间周期:" labelPosition="top" style="width:100%"
				data-options="valueField:'rangetime',textField:'rangetime',
					url:'${pageContext.request.contextPath}/owogJkbAction!doNotNeedSecurity_getRangetime.action'">
			</select><br></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
	</table>
</div>