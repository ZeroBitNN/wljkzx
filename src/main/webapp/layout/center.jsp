<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function addTab(opts) {
		var t = $('#layout_center_tabs');
		if (t.tabs('exists', opts.title)) {
			if (t.tabs('exists', '通讯保障')) {
				t.tabs('close', '通讯保障');
				t.tabs('add', opts);
			} else {
				t.tabs('select', opts.title);
			}
		} else {
			if (t.tabs('exists', '领导交办')) {
				t.tabs('close', '领导交办');
			}
			if (t.tabs('exists', '普通交班')) {
				t.tabs('close', '普通交班');
			}
			if (t.tabs('exists', '所有记录')) {
				t.tabs('close', '所有记录');
			}
			t.tabs('add', opts);
		}
	}

	function refresh() {
		$('#layout_center_homepagePanel').panel('refresh');
	}

	function initPanel() {
		$.cookie('portal-state', null);
		$.messager.show({
			title : '提示',
			msg : '初始化成功！请刷新页面！'
		});
	}
</script>

<div id="layout_center_tabs" class="easyui-tabs" data-options="fit:true,border:false,tools:'#tab-tools'">
	<div id="layout_center_homepagePanel" data-options="tools:'#p-tools'" title="首页" border="false"
		href="layout/portal.jsp"></div>
</div>

<div id="p-tools">
	<a href="javascript:void(0)" class="icon-mini-refresh easyui-tooltip" title="刷新" onclick="refresh()"></a>
</div>
<div id="tab-tools">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'"
		onclick="initPanel()">恢复首页面板初始顺序</a>
</div>