<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		$('#kbms_category_categoryList_tree').tree({
			url : '${pageContext.request.contextPath}/kbmsCategoryAction!getTree.action',
			parentField : 'pid',
			animate : true,
			lines : true,
			onContextMenu : function(e, node) {
				e.preventDefault();
				$(this).tree('select', node.target);
				$('#kbms_category_categoryList_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onDblClick : function(node) {
				$(this).tree('beginEdit', node.target);
			},
			onAfterEdit : function(node) {
				$.ajax({
					url : '${pageContext.request.contextPath}/kbmsCategoryAction!edit.action',
					type : "POST",
					data : {
						id : (node.id ? node.id : null),
						text : node.text,
						pid : (node.pid ? node.pid : null)
					},
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							var rObj = r.obj;
							$('#kbms_category_categoryList_tree').tree('update', {
								target : node.target,
								id : rObj.id,
								text : rObj.text
							});
							$.messager.show({
								title : '提示!',
								msg : r.msg
							});
						} else {
							$.messager.show({
								title : '错误!',
								msg : r.msg
							});
						}
					},
					error : function(data) {
						$.messager.alert('警告！', data.responseText, 'warning');
					}
				});
			},
			formatter : function(node) {
				var s = node.text;
				if (node.children) {
					s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ' 项子类)</span>';
				}
				return s;
			}
		});
	});

	function collapseAll() {
		$('#kbms_category_categoryList_tree').tree('collapseAll');
	}

	function expandAll() {
		$('#kbms_category_categoryList_tree').tree('expandAll');
	}
</script>

<div style="margin:20px 10px;">
	<a href="#" class="easyui-linkbutton" onclick="collapseAll()">折叠所有</a> <a href="#"
		class="easyui-linkbutton" onclick="expandAll()">展开所有</a>
</div>
<div class="easyui-panel" style="padding:5px;width:90%;" data-options="border:false">
	<div class="easyui-panel" style="width:100%;padding:5px;" data-options="border:false">
		<ul id="kbms_category_categoryList_tree"></ul>
	</div>
</div>

<div id="kbms_category_categoryList_menu" class="easyui-menu" style="width:120px;">
	<div onclick="append()" data-options="iconCls:'icon-add'">新增</div>
	<div onclick="removeit()" data-options="iconCls:'icon-remove'">删除</div>
	<div class="menu-sep"></div>
	<div onclick="expand()">展开</div>
	<div onclick="collapse()">折叠</div>
</div>
<script type="text/javascript">
	function append() {
		var t = $('#kbms_category_categoryList_tree');
		var node = t.tree('getSelected');
		t.tree('append', {
			parent : (node ? node.target : null),
			data : [{
				text : 'new item',
				pid : node.id
			}]
		});
	}
	function removeit() {
		var node = $('#kbms_category_categoryList_tree').tree('getSelected');
		console.info(node);
		$.messager.confirm('确认！', '是否确认删除 ' + node.text+' 类？<br/><b>重要提示：如该类目下有子类，其子类会一并删除。</b>', function(r) {
			if (r) {
				$('#kbms_category_categoryList_tree').tree('remove', node.target);
				$.ajax({
					url : '${pageContext.request.contextPath}/kbmsCategoryAction!delete.action',
					type : "POST",
					data : {
						id : (node.id ? node.id : null),
					},
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							$.messager.show({
								title : '提示!',
								msg : r.msg
							});
						} else {
							$.messager.show({
								title : '错误!',
								msg : r.msg
							});
						}
					},
					error : function(data) {
						$.messager.alert('警告！', data.responseText, 'warning');
					}
				});
			}
		});
	}
	function collapse() {
		var node = $('#kbms_category_categoryList_tree').tree('getSelected');
		$('#kbms_category_categoryList_tree').tree('collapse', node.target);
	}
	function expand() {
		var node = $('#kbms_category_categoryList_tree').tree('getSelected');
		$('#kbms_category_categoryList_tree').tree('expand', node.target);
	}
</script>