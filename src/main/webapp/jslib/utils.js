/**
 * 更改easyui加载panel时的提示文字
 * 
 * @author 宋宝定
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.panel.defaults, {
	loadingMessage : '加载中....'
});

/**
 * 更改easyui加载grid时的提示文字
 * 
 * @author 宋宝定
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.datagrid.defaults, {
	loadMsg : '数据加载中....'
});

/**
 * @author 宋宝定
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox, 添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

$.extend($.fn.validatebox.defaults.rules, {
	eqTimepoint : {
		validator : function(value, param) {
			//var reg = /(\d{2}(:)\d{2}(\|)?)+/;
			var reg =/^((0?[0-9]|1[0-9]|2[0-3]):(0?[0-9]|[1-5][0-9])(\|)?)+$/;
			return reg.test(value);
		},
		message : '输入格式不正确！'
	}
});

$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled,
		textFiled,
		parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i,
			l,
			treeData = [],
			tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * @author 宋宝定
 * 
 * @requires jQuery,EasyUI
 * 
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({ /* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * @author 宋宝定
 * 
 * @requires jQuery
 * 
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/**
 * 获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
 */
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	+ " " + date.getHours() + seperator2 + date.getMinutes()
	+ seperator2 + date.getSeconds();
	return currentdate;
}

/**
 * DATAGRID-GROUPVIEW
 */
$.extend($.fn.datagrid.defaults, {
	groupHeight : 25,
	expanderWidth : 30,
	groupStyler : function(value, rows) {
		return ''
	}
});

var groupview = $.extend({}, $.fn.datagrid.defaults.view, {
	render : function(target, container, frozen) {
		var table = [];
		var groups = this.groups;
		for (var i = 0; i < groups.length; i++) {
			table.push(this.renderGroup.call(this, target, i, groups[i], frozen));
		}
		$(container).html(table.join(''));
	},

	renderGroup : function(target, groupIndex, group, frozen) {
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var fields = $(target).datagrid('getColumnFields', frozen);
		var hasFrozen = opts.frozenColumns && opts.frozenColumns.length;

		if (frozen) {
			if (!(opts.rownumbers || hasFrozen)) {
				return '';
			}
		}

		var table = [];

		var css = opts.groupStyler.call(target, group.value, group.rows);
		var cs = parseCss(css, 'datagrid-group');
		table.push('<div group-index=' + groupIndex + ' ' + cs + '>');
		if ((frozen && (opts.rownumbers || opts.frozenColumns.length)) ||
			(!frozen && !(opts.rownumbers || opts.frozenColumns.length))) {
			table.push('<span class="datagrid-group-expander">');
			table.push('<span class="datagrid-row-expander datagrid-row-collapse">&nbsp;</span>');
			table.push('</span>');
		}
		if ((frozen && hasFrozen) || (!frozen)) {
			table.push('<span class="datagrid-group-title">');
			table.push(opts.groupFormatter.call(target, group.value, group.rows));
			table.push('</span>');
		}
		table.push('</div>');

		table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
		var index = group.startIndex;
		for (var j = 0; j < group.rows.length; j++) {
			var css = opts.rowStyler ? opts.rowStyler.call(target, index, group.rows[j]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string') {
				styleValue = css;
			} else if (css) {
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}

			var cls = 'class="datagrid-row ' + (index % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen ? 1 : 2) + '-' + index;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + index + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, index, group.rows[j]));
			table.push('</tr>');
			index++;
		}
		table.push('</tbody></table>');
		return table.join('');

		function parseCss(css, cls) {
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string') {
				styleValue = css;
			} else if (css) {
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			return 'class="' + cls + (classValue ? ' ' + classValue : '') + '" ' +
				'style="' + styleValue + '"';
		}
	},

	bindEvents : function(target) {
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0], 'events') || $._data(body[0], 'events')).click[0].handler;
		body.unbind('click').bind('click', function(e) {
			var tt = $(e.target);
			var expander = tt.closest('span.datagrid-row-expander');
			if (expander.length) {
				var gindex = expander.closest('div.datagrid-group').attr('group-index');
				if (expander.hasClass('datagrid-row-collapse')) {
					$(target).datagrid('collapseGroup', gindex);
				} else {
					$(target).datagrid('expandGroup', gindex);
				}
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	},

	onBeforeRender : function(target, rows) {
		var state = $.data(target, 'datagrid');
		var opts = state.options;

		initCss();

		var groups = [];
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var group = getGroup(row[opts.groupField]);
			if (!group) {
				group = {
					value : row[opts.groupField],
					rows : [ row ]
				};
				groups.push(group);
			} else {
				group.rows.push(row);
			}
		}

		var index = 0;
		var newRows = [];
		for (var i = 0; i < groups.length; i++) {
			var group = groups[i];
			group.startIndex = index;
			index += group.rows.length;
			newRows = newRows.concat(group.rows);
		}

		state.data.rows = newRows;
		this.groups = groups;

		var that = this;
		setTimeout(function() {
			that.bindEvents(target);
		}, 0);

		function getGroup(value) {
			for (var i = 0; i < groups.length; i++) {
				var group = groups[i];
				if (group.value == value) {
					return group;
				}
			}
			return null;
		}
		function initCss() {
			if (!$('#datagrid-group-style').length) {
				$('head').append(
					'<style id="datagrid-group-style">' +
					'.datagrid-group{height:' + opts.groupHeight + 'px;overflow:hidden;font-weight:bold;border-bottom:1px solid #ccc;white-space:nowrap;word-break:normal;}' +
					'.datagrid-group-title,.datagrid-group-expander{display:inline-block;vertical-align:bottom;height:100%;line-height:' + opts.groupHeight + 'px;padding:0 4px;}' +
					'.datagrid-group-title{position:relative;}' +
					'.datagrid-group-expander{width:' + opts.expanderWidth + 'px;text-align:center;padding:0}' +
					'.datagrid-row-expander{margin:' + Math.floor((opts.groupHeight - 16) / 2) + 'px 0;display:inline-block;width:16px;height:16px;cursor:pointer}' +
					'</style>'
				);
			}
		}
	},
	onAfterRender : function(target) {
		$.fn.datagrid.defaults.view.onAfterRender.call(this, target);

		var view = this;
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		if (!state.onResizeColumn) {
			state.onResizeColumn = opts.onResizeColumn;
		}
		if (!state.onResize) {
			state.onResize = opts.onResize;
		}
		opts.onResizeColumn = function(field, width) {
			view.resizeGroup(target);
			state.onResizeColumn.call(target, field, width);
		}
		opts.onResize = function(width, height) {
			view.resizeGroup(target);
			state.onResize.call($(target).datagrid('getPanel')[0], width, height);
		}
		view.resizeGroup(target);
	}
});

$.extend($.fn.datagrid.methods, {
	groups : function(jq) {
		return jq.datagrid('options').view.groups;
	},
	expandGroup : function(jq, groupIndex) {
		return jq.each(function() {
			var view = $.data(this, 'datagrid').dc.view;
			var group = view.find(groupIndex != undefined ? 'div.datagrid-group[group-index="' + groupIndex + '"]' : 'div.datagrid-group');
			var expander = group.find('span.datagrid-row-expander');
			if (expander.hasClass('datagrid-row-expand')) {
				expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
				group.next('table').show();
			}
			$(this).datagrid('fixRowHeight');
		});
	},
	collapseGroup : function(jq, groupIndex) {
		return jq.each(function() {
			var view = $.data(this, 'datagrid').dc.view;
			var group = view.find(groupIndex != undefined ? 'div.datagrid-group[group-index="' + groupIndex + '"]' : 'div.datagrid-group');
			var expander = group.find('span.datagrid-row-expander');
			if (expander.hasClass('datagrid-row-collapse')) {
				expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
				group.next('table').hide();
			}
			$(this).datagrid('fixRowHeight');
		});
	},
	scrollToGroup : function(jq, groupIndex) {
		return jq.each(function() {
			var state = $.data(this, 'datagrid');
			var dc = state.dc;
			var grow = dc.body2.children('div.datagrid-group[group-index="' + groupIndex + '"]');
			if (grow.length) {
				var groupHeight = grow.outerHeight();
				var headerHeight = dc.view2.children('div.datagrid-header')._outerHeight();
				var frozenHeight = dc.body2.outerHeight(true) - dc.body2.outerHeight();
				var top = grow.position().top - headerHeight - frozenHeight;
				if (top < 0) {
					dc.body2.scrollTop(dc.body2.scrollTop() + top);
				} else if (top + groupHeight > dc.body2.height() - 18) {
					dc.body2.scrollTop(dc.body2.scrollTop() + top + groupHeight - dc.body2.height() + 18);
				}
			}
		});
	}
});

$.extend(groupview, {
	refreshGroupTitle : function(target, groupIndex) {
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = this.groups[groupIndex];
		var span = dc.body1.add(dc.body2).children('div.datagrid-group[group-index=' + groupIndex + ']').find('span.datagrid-group-title');
		span.html(opts.groupFormatter.call(target, group.value, group.rows));
	},
	resizeGroup : function(target, groupIndex) {
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var ht = dc.header2.find('table');
		var fr = ht.find('tr.datagrid-filter-row').hide();
		var ww = ht.width();
		if (groupIndex == undefined) {
			var groupHeader = dc.body2.children('div.datagrid-group');
		} else {
			var groupHeader = dc.body2.children('div.datagrid-group[group-index=' + groupIndex + ']');
		}
		groupHeader._outerWidth(ww);
		var opts = state.options;
		if (opts.frozenColumns && opts.frozenColumns.length) {
			var width = dc.view1.width() - opts.expanderWidth;
			var isRtl = dc.view1.css('direction').toLowerCase() == 'rtl';
			groupHeader.find('.datagrid-group-title').css(isRtl ? 'right' : 'left', -width + 'px');
		}
		if (fr.length) {
			if (opts.showFilterBar) {
				fr.show();
			}
		}
	// fr.show();
	},

	insertRow : function(target, index, row) {
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = null;
		var groupIndex;

		if (!state.data.rows.length) {
			$(target).datagrid('loadData', [ row ]);
			return;
		}

		for (var i = 0; i < this.groups.length; i++) {
			if (this.groups[i].value == row[opts.groupField]) {
				group = this.groups[i];
				groupIndex = i;
				break;
			}
		}
		if (group) {
			if (index == undefined || index == null) {
				index = state.data.rows.length;
			}
			if (index < group.startIndex) {
				index = group.startIndex;
			} else if (index > group.startIndex + group.rows.length) {
				index = group.startIndex + group.rows.length;
			}
			$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);

			if (index >= group.startIndex + group.rows.length) {
				_moveTr(index, true);
				_moveTr(index, false);
			}
			group.rows.splice(index - group.startIndex, 0, row);
		} else {
			group = {
				value : row[opts.groupField],
				rows : [ row ],
				startIndex : state.data.rows.length
			}
			groupIndex = this.groups.length;
			dc.body1.append(this.renderGroup.call(this, target, groupIndex, group, true));
			dc.body2.append(this.renderGroup.call(this, target, groupIndex, group, false));
			this.groups.push(group);
			state.data.rows.push(row);
		}

		this.setGroupIndex(target);
		this.refreshGroupTitle(target, groupIndex);
		this.resizeGroup(target);

		function _moveTr(index, frozen) {
			var serno = frozen ? 1 : 2;
			var prevTr = opts.finder.getTr(target, index - 1, 'body', serno);
			var tr = opts.finder.getTr(target, index, 'body', serno);
			tr.insertAfter(prevTr);
		}
	},

	updateRow : function(target, index, row) {
		var opts = $.data(target, 'datagrid').options;
		$.fn.datagrid.defaults.view.updateRow.call(this, target, index, row);
		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));
		this.refreshGroupTitle(target, groupIndex);
	},

	deleteRow : function(target, index) {
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);

		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));

		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);

		var group = this.groups[groupIndex];
		if (group.rows.length > 1) {
			group.rows.splice(index - group.startIndex, 1);
			this.refreshGroupTitle(target, groupIndex);
		} else {
			body.children('div.datagrid-group[group-index=' + groupIndex + ']').remove();
			for (var i = groupIndex + 1; i < this.groups.length; i++) {
				body.children('div.datagrid-group[group-index=' + i + ']').attr('group-index', i - 1);
			}
			this.groups.splice(groupIndex, 1);
		}

		this.setGroupIndex(target);
	},

	setGroupIndex : function(target) {
		var index = 0;
		for (var i = 0; i < this.groups.length; i++) {
			var group = this.groups[i];
			group.startIndex = index;
			index += group.rows.length;
		}
	}
});

/**
 * 更换主题
 * 
 * @author 宋宝定
 * @requires jQuery,EasyUI
 * @param themeName
 */
changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {}
			}
		}
	}

	$.cookie('easyuiTheme', themeName, {
		expires : 7
	});
};

/**
 * @author 宋宝定
 * 
 * 生成UUID
 * 
 * @returns UUID字符串
 */
random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
getUUID = function() {
	return (random4() + random4() + "-" + random4() + "-" + random4() + "-" + random4() + "-" + random4() + random4() + random4());
};

/**
 * @author 宋宝定
 * 
 * 获得URL参数
 * 
 * @returns 对应名称的值
 */
getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	//console.info(window.location);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/**
 * Cell Editing in DataGrid
 */
(function($) {
	$.extend($.fn.datagrid.defaults, {
		clickToEdit : true,
		dblclickToEdit : false,
		navHandler : {
			'37' : function(e) {
				var opts = $(this).datagrid('options');
				return navHandler.call(this, e, opts.isRtl ? 'right' : 'left');
			},
			'39' : function(e) {
				var opts = $(this).datagrid('options');
				return navHandler.call(this, e, opts.isRtl ? 'left' : 'right');
			},
			'38' : function(e) {
				return navHandler.call(this, e, 'up');
			},
			'40' : function(e) {
				return navHandler.call(this, e, 'down');
			},
			'13' : function(e) {
				return enterHandler.call(this, e);
			},
			'27' : function(e) {
				return escHandler.call(this, e);
			},
			'8' : function(e) {
				return clearHandler.call(this, e);
			},
			'46' : function(e) {
				return clearHandler.call(this, e);
			},
			'keypress' : function(e) {
				if (e.metaKey || e.ctrlKey) {
					return;
				}
				var dg = $(this);
				var param = dg.datagrid('cell'); // current cell information
				if (!param) {
					return;
				}
				var input = dg.datagrid('input', param);
				if (!input) {
					var tmp = $('<span></span>');
					tmp.html(String.fromCharCode(e.which));
					var c = tmp.text();
					tmp.remove();
					if (c) {
						dg.datagrid('editCell', {
							index : param.index,
							field : param.field,
							value : c
						});
						return false;
					}
				}
			}
		},
		onBeforeCellEdit : function(index, field) {},
		onCellEdit : function(index, field, value) {
			var input = $(this).datagrid('input', {
				index : index,
				field : field
			});
			if (input) {
				if (value != undefined) {
					input.val(value);
				}
			}
		},
		onSelectCell : function(index, field) {},
		onUnselectCell : function(index, field) {}
	});

	function navHandler(e, dir) {
		var dg = $(this);
		var param = dg.datagrid('cell');
		var input = dg.datagrid('input', param);
		if (!input) {
			dg.datagrid('gotoCell', dir);
			return false;
		}
	}

	function enterHandler(e) {
		var dg = $(this);
		var cell = dg.datagrid('cell');
		if (!cell) {
			return;
		}
		var input = dg.datagrid('input', cell);
		if (input) {
			if (input[0].tagName.toLowerCase() == 'textarea') {
				return;
			}
			endCellEdit(this, true);
		} else {
			dg.datagrid('editCell', cell);
		}
		return false;
	}

	function escHandler(e) {
		endCellEdit(this, false);
		return false;
	}

	function clearHandler(e) {
		var dg = $(this);
		var param = dg.datagrid('cell');
		if (!param) {
			return;
		}
		var input = dg.datagrid('input', param);
		if (!input) {
			dg.datagrid('editCell', {
				index : param.index,
				field : param.field,
				value : ''
			});
			return false;
		}
	}

	function getCurrCell(target) {
		var cell = $(target).datagrid('getPanel').find('td.datagrid-row-selected');
		if (cell.length) {
			return {
				index : parseInt(cell.closest('tr.datagrid-row').attr('datagrid-row-index')),
				field : cell.attr('field')
			};
		} else {
			return null;
		}
	}

	function unselectCell(target, p) {
		var opts = $(target).datagrid('options');
		var cell = opts.finder.getTr(target, p.index).find('td[field="' + p.field + '"]');
		cell.removeClass('datagrid-row-selected');
		opts.onUnselectCell.call(target, p.index, p.field);
	}

	function unselectAllCells(target) {
		var panel = $(target).datagrid('getPanel');
		panel.find('td.datagrid-row-selected').removeClass('datagrid-row-selected');
	}

	function selectCell(target, p) {
		var opts = $(target).datagrid('options');
		if (opts.singleSelect) {
			unselectAllCells(target);
		}
		var cell = opts.finder.getTr(target, p.index).find('td[field="' + p.field + '"]');
		cell.addClass('datagrid-row-selected');
		opts.onSelectCell.call(target, p.index, p.field);
	}

	function getSelectedCells(target) {
		var cells = [];
		var panel = $(target).datagrid('getPanel');
		panel.find('td.datagrid-row-selected').each(function() {
			var td = $(this);
			cells.push({
				index : parseInt(td.closest('tr.datagrid-row').attr('datagrid-row-index')),
				field : td.attr('field')
			});
		});
		return cells;
	}

	function gotoCell(target, p) {
		var dg = $(target);
		var opts = dg.datagrid('options');
		var panel = dg.datagrid('getPanel').focus();

		var cparam = dg.datagrid('cell');
		if (cparam) {
			var input = dg.datagrid('input', cparam);
			if (input) {
				input.focus();
				return;
			}
		}

		if (typeof p == 'object') {
			_go(p);
			return;
		}
		var cell = panel.find('td.datagrid-row-selected');
		if (!cell) {
			return;
		}
		var fields = dg.datagrid('getColumnFields', true).concat(dg.datagrid('getColumnFields'));
		var field = cell.attr('field');
		var tr = cell.closest('tr.datagrid-row');
		var rowIndex = parseInt(tr.attr('datagrid-row-index'));
		var colIndex = $.inArray(field, fields);

		if (p == 'up' && rowIndex > 0) {
			rowIndex--;
		} else if (p == 'down') {
			if (opts.finder.getRow(target, rowIndex + 1)) {
				rowIndex++;
			}
		} else if (p == 'left') {
			var i = colIndex - 1;
			while (i >= 0) {
				var col = dg.datagrid('getColumnOption', fields[i]);
				if (!col.hidden) {
					colIndex = i;
					break;
				}
				i--;
			}
		} else if (p == 'right') {
			var i = colIndex + 1;
			while (i <= fields.length - 1) {
				var col = dg.datagrid('getColumnOption', fields[i]);
				if (!col.hidden) {
					colIndex = i;
					break;
				}
				i++;
			}
		}

		field = fields[colIndex];

		_go({
			index : rowIndex,
			field : field
		});

		function _go(p) {
			dg.datagrid('scrollTo', p.index);
			unselectAllCells(target);
			selectCell(target, p);
			var td = opts.finder.getTr(target, p.index, 'body', 2).find('td[field="' + p.field + '"]');
			if (td.length) {
				var body2 = dg.data('datagrid').dc.body2;
				var left = td.position().left;
				if (left < 0) {
					body2._scrollLeft(body2._scrollLeft() + left * (opts.isRtl ? -1 : 1));
				} else if (left + td._outerWidth() > body2.width()) {
					body2._scrollLeft(body2._scrollLeft() + (left + td._outerWidth() - body2.width()) * (opts.isRtl ? -1 : 1));
				}
			}
		}
	}

	// end the current cell editing
	function endCellEdit(target, accepted) {
		var dg = $(target);
		var cell = dg.datagrid('cell');
		if (cell) {
			var input = dg.datagrid('input', cell);
			if (input) {
				if (accepted) {
					if (dg.datagrid('validateRow', cell.index)) {
						dg.datagrid('endEdit', cell.index);
						dg.datagrid('gotoCell', cell);
					} else {
						dg.datagrid('gotoCell', cell);
						input.focus();
						return false;
					}
				} else {
					dg.datagrid('cancelEdit', cell.index);
					dg.datagrid('gotoCell', cell);
				}
			}
		}
		return true;
	}

	function editCell(target, param) {
		var dg = $(target);
		var opts = dg.datagrid('options');
		var input = dg.datagrid('input', param);
		if (input) {
			dg.datagrid('gotoCell', param);
			input.focus();
			return;
		}
		if (!endCellEdit(target, true)) {
			return;
		}
		if (opts.onBeforeCellEdit.call(target, param.index, param.field) == false) {
			return;
		}

		var fields = dg.datagrid('getColumnFields', true).concat(dg.datagrid('getColumnFields'));
		$.map(fields, function(field) {
			var col = dg.datagrid('getColumnOption', field);
			col.editor1 = col.editor;
			if (field != param.field) {
				col.editor = null;
			}
		});

		var col = dg.datagrid('getColumnOption', param.field);
		if (col.editor) {
			dg.datagrid('beginEdit', param.index);
			var input = dg.datagrid('input', param);
			if (input) {
				dg.datagrid('gotoCell', param);
				setTimeout(function() {
					input.unbind('.cellediting').bind('keydown.cellediting', function(e) {
						if (e.keyCode == 13) {
							return opts.navHandler['13'].call(target, e);
						// return false;
						}
					});
					input.focus();
				}, 0);
				opts.onCellEdit.call(target, param.index, param.field, param.value);
			} else {
				dg.datagrid('cancelEdit', param.index);
				dg.datagrid('gotoCell', param);
			}
		} else {
			dg.datagrid('gotoCell', param);
		}

		$.map(fields, function(field) {
			var col = dg.datagrid('getColumnOption', field);
			col.editor = col.editor1;
		});
	}

	function enableCellSelecting(target) {
		var dg = $(target);
		var state = dg.data('datagrid');
		var panel = dg.datagrid('getPanel');
		var opts = state.options;
		var dc = state.dc;
		panel.attr('tabindex', 1).css('outline-style', 'none').unbind('.cellediting').bind('keydown.cellediting', function(e) {
			var h = opts.navHandler[e.keyCode];
			if (h) {
				return h.call(target, e);
			}
		});
		dc.body1.add(dc.body2).unbind('.cellediting').bind('click.cellediting', function(e) {
			var tr = $(e.target).closest('.datagrid-row');
			if (tr.length && tr.parent().length) {
				var td = $(e.target).closest('td[field]', tr);
				if (td.length) {
					var index = parseInt(tr.attr('datagrid-row-index'));
					var field = td.attr('field');
					var p = {
						index : index,
						field : field
					};
					if (opts.singleSelect) {
						selectCell(target, p);
					} else {
						if (opts.ctrlSelect) {
							if (e.ctrlKey) {
								if (td.hasClass('datagrid-row-selected')) {
									unselectCell(target, p);
								} else {
									selectCell(target, p);
								}
							} else {
								unselectAllCells(target);
								selectCell(target, p);
							}
						} else {
							if (td.hasClass('datagrid-row-selected')) {
								unselectCell(target, p);
							} else {
								selectCell(target, p);
							}
						}
					}
				}
			}
		}).bind('mouseover.cellediting', function(e) {
			var td = $(e.target).closest('td[field]');
			if (td.length) {
				td.addClass('datagrid-row-over');
				td.closest('tr.datagrid-row').removeClass('datagrid-row-over');
			}
		}).bind('mouseout.cellediting', function(e) {
			var td = $(e.target).closest('td[field]');
			td.removeClass('datagrid-row-over');
		});

		opts.isRtl = dg.datagrid('getPanel').css('direction').toLowerCase() == 'rtl';
		opts.OldOnBeforeSelect = opts.onBeforeSelect;
		opts.onBeforeSelect = function() {
			return false;
		};
		dg.datagrid('clearSelections');
	}

	function disableCellSelecting(target) {
		var dg = $(target);
		var state = dg.data('datagrid');
		var panel = dg.datagrid('getPanel');
		var opts = state.options;
		opts.onBeforeSelect = opts.OldOnBeforeSelect || opts.onBeforeSelect;
		panel.unbind('.cellediting').find('td.datagrid-row-selected').removeClass('datagrid-row-selected');
		var dc = state.dc;
		dc.body1.add(dc.body2).unbind('.cellediting');
	}

	function enableCellEditing(target) {
		var dg = $(target);
		var opts = dg.datagrid('options');
		var panel = dg.datagrid('getPanel');
		panel.attr('tabindex', 1).css('outline-style', 'none').unbind('.cellediting').bind('keydown.cellediting', function(e) {
			var h = opts.navHandler[e.keyCode];
			if (h) {
				return h.call(target, e);
			}
		}).bind('keypress.cellediting', function(e) {
			return opts.navHandler['keypress'].call(target, e);
		});
		panel.panel('panel').unbind('.cellediting').bind('keydown.cellediting', function(e) {
			e.stopPropagation();
		}).bind('keypress.cellediting', function(e) {
			e.stopPropagation();
		}).bind('mouseover.cellediting', function(e) {
			var td = $(e.target).closest('td[field]');
			if (td.length) {
				td.addClass('datagrid-row-over');
				td.closest('tr.datagrid-row').removeClass('datagrid-row-over');
			}
		}).bind('mouseout.cellediting', function(e) {
			var td = $(e.target).closest('td[field]');
			td.removeClass('datagrid-row-over');
		});

		opts.isRtl = dg.datagrid('getPanel').css('direction').toLowerCase() == 'rtl';
		opts.oldOnClickCell = opts.onClickCell;
		opts.oldOnDblClickCell = opts.onDblClickCell;
		opts.onClickCell = function(index, field, value) {
			if (opts.clickToEdit) {
				$(this).datagrid('editCell', {
					index : index,
					field : field
				});
			} else {
				if (endCellEdit(this, true)) {
					$(this).datagrid('gotoCell', {
						index : index,
						field : field
					});
				}
			}
			opts.oldOnClickCell.call(this, index, field, value);
		}
		if (opts.dblclickToEdit) {
			opts.onDblClickCell = function(index, field, value) {
				$(this).datagrid('editCell', {
					index : index,
					field : field
				});
				opts.oldOnDblClickCell.call(this, index, field, value);
			}
		}
		opts.OldOnBeforeSelect = opts.onBeforeSelect;
		opts.onBeforeSelect = function() {
			return false;
		};
		dg.datagrid('clearSelections')
	}

	function disableCellEditing(target) {
		var dg = $(target);
		var panel = dg.datagrid('getPanel');
		var opts = dg.datagrid('options');
		opts.onClickCell = opts.oldOnClickCell || opts.onClickCell;
		opts.onDblClickCell = opts.oldOnDblClickCell || opts.onDblClickCell;
		opts.onBeforeSelect = opts.OldOnBeforeSelect || opts.onBeforeSelect;
		panel.unbind('.cellediting').find('td.datagrid-row-selected').removeClass('datagrid-row-selected');
		panel.panel('panel').unbind('.cellediting');
	}


	$.extend($.fn.datagrid.methods, {
		editCell : function(jq, param) {
			return jq.each(function() {
				editCell(this, param);
			});
		},
		isEditing : function(jq, index) {
			var opts = $.data(jq[0], 'datagrid').options;
			var tr = opts.finder.getTr(jq[0], index);
			return tr.length && tr.hasClass('datagrid-row-editing');
		},
		gotoCell : function(jq, param) {
			return jq.each(function() {
				gotoCell(this, param);
			});
		},
		enableCellEditing : function(jq) {
			return jq.each(function() {
				enableCellEditing(this);
			});
		},
		disableCellEditing : function(jq) {
			return jq.each(function() {
				disableCellEditing(this);
			});
		},
		enableCellSelecting : function(jq) {
			return jq.each(function() {
				enableCellSelecting(this);
			});
		},
		disableCellSelecting : function(jq) {
			return jq.each(function() {
				disableCellSelecting(this);
			});
		},
		input : function(jq, param) {
			if (!param) {
				return null;
			}
			var ed = jq.datagrid('getEditor', param);
			if (ed) {
				var t = $(ed.target);
				if (t.hasClass('textbox-f')) {
					t = t.textbox('textbox');
				}
				return t;
			} else {
				return null;
			}
		},
		cell : function(jq) { // get current cell info {index,field}
			return getCurrCell(jq[0]);
		},
		getSelectedCells : function(jq) {
			return getSelectedCells(jq[0]);
		}
	});

})(jQuery);