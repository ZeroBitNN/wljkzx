<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	var addRow;
	var editingId;
	var editingRow;
	var perfPersonRow;
	var importUrl;
	var authority = 1;
	var perfparam_tg_tb = [{
		text : '保存',
		iconCls : 'icon-save',
		handler : function() {
			if (authority != 1) {
				return;
			}
			if (editingId != undefined) {
				$('#performance_perfParamSetup_tg').treegrid('endEdit', editingId);
			}
		}
	}, {
		text : '取消',
		iconCls : 'icon-cancel',
		handler : function() {
			if (editingId != undefined) {
				$('#performance_perfParamSetup_tg').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
			if (addRow != undefined) {
				$('#performance_perfParamSetup_tg').treegrid('remove', addRow.id);
				addRow = undefined;
			}
		}
	}, '-', {
		text : '展开所有',
		iconCls : 'icon-expand',
		handler : function() {
			$('#performance_perfParamSetup_tg').treegrid('expandAll');
		}
	}, {
		text : '收起所有',
		iconCls : 'icon-collapse',
		handler : function() {
			$('#performance_perfParamSetup_tg').treegrid('collapseAll');
		}
	}];

	$(function() {
		$('#performance_perfParamSetup_tg').treegrid({
			url : '${pageContext.request.contextPath}/perfParamAction!getParamTg.action',
			idField : 'id',
			treeField : 'name',
			animate : true,
			fit : true,
			toolbar : perfparam_tg_tb,
			onContextMenu : onContextMenu,
			showFooter : true,
			columns : [[
				{
					title : '类别名称',
					field : 'name',
					width : '30%',
					editor : 'text'
				},
				{
					title : '当前工单量',
					field : 'value',
					width : '15%',
					align : 'center',
					formatter : function(value, row, index) {
						if (!row.pid) {
							return "<b>大类合计：" + value + "</b>";
						} else {
							return value;
						}
					}
				},
				{
					title : '百分比值',
					field : 'percent',
					width : '15%',
					align : 'center',
					formatter : formatProgress,
					editor : {
						type : 'numberbox',
						options : {
							precision : 1
						}
					}
				},
				{
					title : '描述',
					field : 'describe',
					width : '40%',
					editor : 'text'
				}
			]],
			onDblClickRow : function(row) {
				if (editingId != undefined) {
					$('#performance_perfParamSetup_tg').treegrid('endEdit', editingId);
					return;
				}
				if (row) {
					editingId = row.id;
					$('#performance_perfParamSetup_tg').treegrid('beginEdit', editingId);
				}
			},
			onLoadError : function() {
				authority = undefined;
				$.messager.alert('警告！', "您没有访问此功能的权限！请联系管理员给你赋予相应权限。", 'warning');
			},
			onAfterEdit : function(row, changes) {
				if (editingId != undefined) {
					$('#performance_perfParamSetup_tg').treegrid('endEdit', editingId);
				}
				$.ajax({
					url : '${pageContext.request.contextPath}/perfParamAction!doNotNeedSecurity_save.action',
					type : "POST",
					data : row,
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
							editingId = undefined;
							$('#performance_perfParamSetup_tg').treegrid('reload');
							$('#performance_perfParamSetup_dg').datagrid('reload');
						} else {
							$('#memo_memoList_datagrid').datagrid('beginEdit', editingId);
							$.messager.alert('错误', r.msg, 'error');
						}
					},
					error : function(data) {
						$.messager.alert('警告！', data.responseText, 'warning');
					}
				});
			}
		});

		$('#performance_perfParamSetup_levelForm').form('load',
			'${pageContext.request.contextPath}/perfParamAction!getLevel.action');

		$('#performance_perfParamSetup_dg').datagrid({
			singleSelect : true,
			fitColumns : true,
			striped : true,
			sortName : 'itemgroup',
			url : '${pageContext.request.contextPath}/perfNumAction!getPerfNumDg.action',
			fit : true,
			onLoadError : function() {
				authority = undefined;
				$.messager.alert('警告！', '您没有修改工作量数据的权限！请联系管理员给你赋予相应权限。', 'warning');
			},
			view : groupview,
			groupField : 'name',
			groupFormatter : function(value, rows) {
				return value + ' - ' + rows.length + ' Item(s)';
			},
			toolbar : [{
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (authority != 1) {
						return;
					}
					if (editingRow != undefined) {
						$('#performance_perfParamSetup_dg').datagrid('endEdit', editingRow);
						editingRow = undefined;
					}
					var rows = $('#performance_perfParamSetup_dg').datagrid('getChanges');
					var rowData = JSON.stringify(rows);
					$.ajax({
						url : '${pageContext.request.contextPath}/perfNumAction!doNotNeedSecurity_save.action',
						type : "POST",
						data : {
							changeRows : rowData
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$.messager.show({
									msg : result.msg,
									title : '保存成功'
								});
								$('#performance_perfParamSetup_tg').treegrid('reload');
							} else {
								$.messager.alert('错误', result.msg, 'error');
							}
						},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#performance_perfParamSetup_dg').datagrid('rejectChanges');
					if (editingRow != undefined) {
						editingRow = undefined;
					}
				}
			}, '-', {
				text : '展开所有',
				iconCls : 'icon-expand',
				handler : function() {
					$('#performance_perfParamSetup_dg').datagrid('expandGroup');
				}
			}, {
				text : '收起所有',
				iconCls : 'icon-collapse',
				handler : function() {
					$('#performance_perfParamSetup_dg').datagrid('collapseGroup');
				}
			}, '-', {
				text : '导出数据模板',
				iconCls : 'icon-exportE',
				handler : function() {
					exportNum();
				}
			}, {
				text : '按模板导入数据',
				iconCls : 'icon-importE',
				handler : function() {
					$('#performance_perfParam_filebox').textbox('clear');
					importUrl='${pageContext.request.contextPath}/perfNumAction!doNotNeedSecurity_importExcel.action';
					$('#performance_perfParam_importDialog').dialog('open');
				}
			}],
			onAfterEdit : function(rowIndex, rowData, changes) {
				if (editingRow != undefined) {
					editingRow = undefined;
				}
			},
			onClickCell : function(index, field, value) {
				editingRow = index;
			}
		});

		$('#performance_perfParamSetup_dg').datagrid('enableCellEditing').datagrid('gotoCell', {
			index : 0,
			field : 'id'
		});

		$('#performance_perfPerson_dg').datagrid({
			singleSelect : true,
			fitColumns : true,
			striped : true,
			url : '${pageContext.request.contextPath}/perfAction!getGrjxDg.action',
			fit : true,
			onLoadError : function() {
				authority = undefined;
				$.messager.alert('警告！', '您没有修改数据的权限！请联系管理员给你赋予相应权限。', 'warning');
			},
			toolbar : [{
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (authority != 1) {
						return;
					}
					if (perfPersonRow != undefined) {
						$('#performance_perfPerson_dg').datagrid('endEdit', perfPersonRow);
						perfPersonRow = undefined;
					}
					var rows = $('#performance_perfPerson_dg').datagrid('getChanges');
					var rowData = JSON.stringify(rows);
					$.ajax({
						url : '${pageContext.request.contextPath}/perfAction!doNotNeedSecurity_save.action',
						type : "POST",
						data : {
							changeRows : rowData
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$.messager.show({
									msg : result.msg,
									title : '保存成功'
								});
							} else {
								$.messager.alert('错误', result.msg, 'error');
							}
						},
						error : function(data) {
							$.messager.alert('警告！', data.responseText, 'warning');
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#performance_perfPerson_dg').datagrid('rejectChanges');
					if (perfPersonRow != undefined) {
						perfPersonRow = undefined;
					}
				}
			}, '-', {
				text : '导出数据模板',
				iconCls : 'icon-exportE',
				handler : function() {
					exportGrjx();
				}
			}, {
				text : '按模板导入',
				iconCls : 'icon-importE',
				handler : function() {
					$('#performance_perfParam_filebox').textbox('clear');
					importUrl='${pageContext.request.contextPath}/perfAction!doNotNeedSecurity_importGrjx.action';
					$('#performance_perfParam_importDialog').dialog('open');
				}
			}],
			onAfterEdit : function(rowIndex, rowData, changes) {
				if (perfPersonRow != undefined) {
					perfPersonRow = undefined;
				}
			},
			onClickCell : function(index, field, value) {
				perfPersonRow = index;
			}
		});
	});

	$('#performance_perfPerson_dg').datagrid('enableCellEditing').datagrid('gotoCell', {
		index : 0,
		field : 'id'
	});

	function exportGrjx(){
		var tempUrl = "${pageContext.request.contextPath}/perfAction!doNotNeedSecurity_exportGrjx.action";
		window.open(tempUrl, "_blank");
	}
	
	function exportNum() {
		var tempUrl = "${pageContext.request.contextPath}/perfNumAction!doNotNeedSecurity_exportExcel.action";
		window.open(tempUrl, "_blank");
	}

	function onContextMenu(e, row) {
		if (row) {
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#performance_perfParamSetup_mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	}

	function formatProgress(value) {
		if (value) {
			var s = '<div style="width:100%;border:1px solid #ccc">' +
				'<div style="width:' + value + '%;background:#cc0000;color:#000">' + value + '%' + '</div>'
			'</div>';
			return s;
		} else {
			return '';
		}
	}

	function append() {
		if (editingId != undefined) {
			$('#performance_perfParamSetup_tg').treegrid('endEdit', editingId);
		}
		var node = $('#performance_perfParamSetup_tg').treegrid('getSelected');
		var rowData = {
			id : getUUID(),
			name : '新增子类',
			pid : node.id
		};
		addRow = rowData;
		$('#performance_perfParamSetup_tg').treegrid('append', {
			parent : node.id,
			data : [rowData]
		});
		editingId = rowData.id;
		$('#performance_perfParamSetup_tg').treegrid('beginEdit', editingId);
	}
	function removeIt() {
		if (editingId != undefined) {
			$('#performance_perfParamSetup_tg').treegrid('endEdit', editingId);
			editingId = undefined;
		}
		var node = $('#performance_perfParamSetup_tg').treegrid('getSelected');
		if (node) {
			if (!node.pid) {
				$.messager.alert('警告！', '不允许删除初始类别。', 'warning');
				return;
			} else {
				$.messager.confirm('请确认', '是否确认删除 <b>' + node.name + '</b> 类目？', function(r) {
					if (r) {
						$.ajax({
							url : '${pageContext.request.contextPath}/perfParamAction!doNotNeedSecurity_delete.action',
							type : "POST",
							data : node,
							dataType : 'json',
							success : function(result) {
								if (result.success) {
									$.messager.show({
										msg : result.msg,
										title : '成功'
									});
									$('#performance_perfParamSetup_tg').treegrid('reload');
									$('#performance_perfParamSetup_dg').datagrid('reload');
								} else {
									$.messager.alert('错误', r.msg, 'error');
								}
							},
							error : function(data) {
								$.messager.alert('警告！', data.responseText, 'warning');
							}
						});

					}
				});
			}
		}
	}
	function collapse() {
		var node = $('#performance_perfParamSetup_tg').treegrid('getSelected');
		if (node) {
			$('#performance_perfParamSetup_tg').treegrid('collapse', node.id);
		}
	}
	function expand() {
		var node = $('#performance_perfParamSetup_tg').treegrid('getSelected');
		if (node) {
			$('#performance_perfParamSetup_tg').treegrid('expand', node.id);
		}
	}

	function submitLevelForm() {
		$('#performance_perfParamSetup_levelForm').form('submit', {
			url : '${pageContext.request.contextPath}/perfLevelAction!doNotNeedSecurity_saveLevel.action',
			success : function(data) {
				var saveResult = jQuery.parseJSON(data);
				if (saveResult.success) {
					$.messager.show({
						msg : saveResult.msg,
						title : '成功'
					});
				}
			}
		});
	}
	
	function clearLevelForm() {
		$('#performance_perfParamSetup_levelForm').form('load',
			'${pageContext.request.contextPath}/perfParamAction!getLevel.action');
	}
</script>

<div class="easyui-accordion" style="width:500px;height:300px;" data-options="fit:true,border:false">
	<div title="计件工作项类目设置" data-options="selected:true,iconCls:'icon-item'"
		style="padding:10px;overflow: hidden;">
		<table id="performance_perfParamSetup_tg"></table>
	</div>
	<div title="计件工作量录入" style="padding:10px;overflow: hidden;"
		data-options="
				iconCls:'icon-input',
                tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                        $('#performance_perfParamSetup_dg').datagrid('reload');
                    }
                }]">
		<table id="performance_perfParamSetup_dg" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'id',width:80,hidden:true">ID</th>
					<th data-options="field:'name',align:'center',width:200,hidden:true">姓名</th>
					<th data-options="field:'itemgroup',width:300">工单类目</th>
					<th data-options="field:'item',width:100">工作项</th>
					<th data-options="field:'value',width:500,editor:'numberbox'">工作量</th>
					<th data-options="field:'perfdate',width:50,hidden:true">绩效日期</th>
				</tr>
			</thead>
		</table>
	</div>
	<div title="计件档次占比设置及个人绩效录入" data-options="iconCls:'icon-levels'" style="padding:10px;"
		align="center">
		<div class="easyui-panel" title="计件档次占比" titleDirection="up" halign="left"
			style="width:100%;height:43%;" align="center">
			<div style="width:50%" align="center">
				<div style="margin:10px 0 0 0;"></div>
				<form id="performance_perfParamSetup_levelForm" method="post"
					data-options="onLoadError:function(){
						$('#submitLevelButton').linkbutton('disable');
						$('#clearLevelButton').linkbutton('disable');
					}">
					<div style="margin-bottom:10px">
						<input class="easyui-numberspinner" name="level1"
							data-options="label:'计件一档：',labelPosition:'left',min: 0,max: 100,suffix:'%'"
							style="width:80%;">
					</div>
					<div style="margin-bottom:10px">
						<input class="easyui-numberspinner" name="level2"
							data-options="label:'计件二档：',labelPosition:'left',min: 0,max: 100,suffix:'%'"
							style="width:80%;">
					</div>
					<div style="margin-bottom:10px">
						<input class="easyui-numberspinner" name="level3"
							data-options="label:'计件三档：',labelPosition:'left',min: 0,max: 100,suffix:'%'"
							style="width:80%;">
					</div>
					<div style="margin-bottom:10px">
						<input class="easyui-numberspinner" name="level4"
							data-options="label:'计件四档：',labelPosition:'left',min: 0,max: 100,suffix:'%'"
							style="width:80%;">
					</div>
					<div style="margin-bottom:10px">
						<input class="easyui-numberspinner" name="levelSum"
							data-options="label:'计件占总绩效百分比：',labelPosition:'top',min: 0,max: 100,suffix:'%'"
							style="width:80%;">
					</div>
					<div style="margin-bottom:10px">
						<a href="javascript:void(0)" id="submitLevelButton" class="easyui-linkbutton"
							onclick="submitLevelForm()" style="width:80px" data-options="iconCls:'icon-save'">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" id="clearLevelButton" class="easyui-linkbutton"
							onclick="clearLevelForm()" style="width:80px" data-options="iconCls:'icon-cancel'">取消</a>
					</div>
				</form>
			</div>
		</div>
		<div style="margin-bottom:10px"></div>
		<div class="easyui-panel" title="个人绩效录入" titleDirection="up" halign="left"
			style="width:100%;height:55%;" align="center">
			<table id="performance_perfPerson_dg" class="easyui-datagrid">
				<thead>
					<tr>
						<th data-options="field:'id',width:80,hidden:true">ID</th>
						<th data-options="field:'name',align:'right',width:300">姓名</th>
						<th data-options="field:'grjx',width:300,editor:{type:'numberbox',options:{precision:2}}">个人绩效</th>
						<th
							data-options="field:'isperf',align:'center',width:100,
							editor:{type:'checkbox',options:{on:'是',off:'否'}}">是否计件</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div id="performance_perfParamSetup_mm" class="easyui-menu" style="width:120px;">
	<div onclick="append()" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="removeIt()" data-options="iconCls:'icon-remove'">删除</div>
	<div class="menu-sep"></div>
	<div onclick="collapse()">收起</div>
	<div onclick="expand()">展开</div>
</div>

<div id="performance_perfParam_importDialog" class="easyui-dialog" align="center"
	style="width:500px"
	data-options="title:'导入EXCEL文件',closed:true,modal:true,buttons:[{
    text:'导入',
    handler:function(){
      $('#performance_perfParam_importForm').form('submit',{
        url:importUrl,
        onSubmit: function(){
			$('#progressDlg').dialog('open');
			var proValue = $('#p').progressbar('getValue');
			if (proValue < 100){
				proValue += Math.floor(Math.random() * 10);
				$('#p').progressbar('setValue', proValue);
				timeId=setTimeout(arguments.callee, 500);
			}
			return true;
    	},
        success:function(data){
        	clearTimeout(timeId);
        	$('#p').progressbar('setValue', 0);
        	$('#progressDlg').dialog('close');
	        try{
	         var result = jQuery.parseJSON(data);
	         if (result.success) {
	           $('#performance_perfParam_importDialog').dialog('close');
	           $('#performance_perfParamSetup_dg').datagrid('load');
	           $('#performance_perfPerson_dg').datagrid('load');
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
	<form id="performance_perfParam_importForm" method="post" enctype="multipart/form-data">
		<table style="width:80%;text-align:'center';">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td><input id="performance_perfParam_filebox" class="easyui-filebox" name="filedata"
					label="选择文件:" labelPosition="top" style="width:100%"
					data-options="buttonText:'请选择...',accept:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel'"><br>
					<span style="color: red;">(文件大小不得超过50M，文件类型为：xls,xlsx)</span></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
		</table>
	</form>
</div>

<div id="progressDlg" class="easyui-dialog" title="导入进度" data-options="closed:true,modal:true">
	<div style="padding: 10px 60px 20px 60px">
		<div id="p" class="easyui-progressbar" style="width:400px;"></div>
	</div>
</div>