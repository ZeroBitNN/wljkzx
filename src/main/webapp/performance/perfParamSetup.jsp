<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	var addRow;
	var editingId;
	var perfparam_tg_tb = [{
		text : '保存',
		iconCls : 'icon-save',
		handler : function() {
			if (editingId != undefined) {
				$('#performance_perfParamSetup_tg').treegrid('endEdit', editingId);
			}
		}
	}, '-', {
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
					title : '数值',
					field : 'value',
					width : '15%',
					align : 'center',
					editor : 'numberbox'
				},
				{
					title : '百分比值',
					field : 'percent',
					width : '15%',
					align : 'center',
					formatter : formatProgress,
					editor : 'numberbox'
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

	});

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
</script>

<div class="easyui-accordion" style="width:500px;height:300px;" data-options="fit:true,border:false">
	<div title="计件工作项类目设置" data-options="selected:true,iconCls:'icon-ok'"
		style="padding:10px;overflow: hidden;">
		<table id="performance_perfParamSetup_tg"></table>
	</div>
	<div title="Help" data-options="iconCls:'icon-help'" style="padding:10px;">
		<p>The accordion allows you to provide multiple panels and display one ore more at a time.
			Each panel has built-in support for expanding and collapsing. Clicking on a panel header to
			expand or collapse that panel body. The panel content can be loaded via ajax by specifying a
			'href' property. Users can define a panel to be selected. If it is not specified, then the first
			panel is taken by default.</p>
	</div>
	<div title="DataGrid" style="padding:10px"
		data-options="
                tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                        $('#dg').datagrid('reload');
                    }
                }]">
		<table id="dg" class="easyui-datagrid"
			data-options="url:'',method:'get',fit:true,fitColumns:true,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'itemid',width:80">Item ID</th>
					<th data-options="field:'productid',width:100">Product ID</th>
					<th data-options="field:'listprice',width:80,align:'right'">List Price</th>
					<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
					<th data-options="field:'attr1',width:150">Attribute</th>
					<th data-options="field:'status',width:50,align:'center'">Status</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="performance_perfParamSetup_mm" class="easyui-menu" style="width:120px;">
	<div onclick="append()" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="removeIt()" data-options="iconCls:'icon-remove'">删除</div>
	<div class="menu-sep"></div>
	<div onclick="collapse()">收起</div>
	<div onclick="expand()">展开</div>
</div>