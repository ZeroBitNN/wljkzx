<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	var portal;
	var panels;
	$(function() {

		panels = [ {
			id : 'p1',
			title : '近期值班要求',
			height : 240,
			collapsible : true,
			href : 'layout/portal/demand.jsp',
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#p1').panel('refresh');
				}
			} ]
		}, {
			id : 'p2',
			title : '工作链接',
			height : 240,
			collapsible : true,
			href : 'layout/portal/link.jsp',
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#p2').panel('refresh');
				}
			} ]
		}, {
			id : 'p3',
			title : '近期保障',
			height : 240,
			collapsible : true,
			//content : '<h1>近期暂无保障</h1>',
			href : 'layout/portal/guarantee.jsp',
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#p3').panel('refresh');
				}
			} ]
		}, {
			id : 'p4',
			title : '交接班未完成事项',
			height : 240,
			collapsible : true,
			href : 'layout/portal/memo.jsp',
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#p4').panel('refresh');
				}
			} ]
		}, {
			id : 'p5',
			title : '本人未处理工作',
			height : 240,
			collapsible : true,
			href : 'layout/portal/workrecord.jsp',
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#p5').panel('refresh');
				}
			} ]
		}, {
			id : 'p6',
			title : '今日管控工作',
			height : 240,
			collapsible : true,
			href : 'layout/portal/dailyworkDetails.jsp',
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#p6').panel('refresh');
				}
			} ]
		} ];

		portal = $('#layout_portal_portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-state');
		if (!state) {
			state = 'p1,p2,p3:p6,p5,p4'; /*冒号代表列，逗号代表行*/
		}
		addPanels(state);
		portal.portal('resize');

	});

	function getPanelOptions(id) {
		for (var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	function getPortalState() {
		var aa = [];
		for (var columnIndex = 0; columnIndex < 2; columnIndex++) {
			var cc = [];
			var panels = portal.portal('getPanels', columnIndex);
			for (var i = 0; i < panels.length; i++) {
				cc.push(panels[i].attr('id'));
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}
	function addPanels(portalState) {
		var columns = portalState.split(':');
		for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for (var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					portal.portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
</script>

<div id="layout_portal_portal" style="position:relative" style="overflow: hidden;">
	<div></div>
	<div></div>
</div>
