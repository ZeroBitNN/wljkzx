<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<title>网操维一周一标识</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="网操维,网络操作维护中心,一周一标杆">
<meta http-equiv="description" content="网操维一周一标杆">
<!-- 引入jQuery及其工具 -->
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="../jslib/jq.cookie.js"></script>
<!-- 引入EasyUI及其扩展工具 -->
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../jslib/jquery-easyui-1.5.2/plugins/jquery.portal.js"></script>
<script type="text/javascript" src="../jslib/utils.js"></script>
<script src="js/YuxiSlider.jQuery.min.js"></script>
<!-- 默认的CSS主题样式 -->
<link id="easyuiTheme" rel="stylesheet"
	href="../jslib/jquery-easyui-1.5.2/themes/default/easyui.css" type="text/css"></link>
<!-- 引入图标样式 -->
<link rel="stylesheet" href="../jslib/jquery-easyui-1.5.2/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="../myThemes/myIcon.css" type="text/css"></link>
<!-- 图片轮播样式 -->
<link rel="stylesheet" href="css/sliderStyle.css">
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : '${pageContext.request.contextPath}/owogImagesAction!doNotNeedSecurity_listForShow.action',
			dataType : 'json',
			success : function(data) {
				var listHtml = '';
				//console.info(data);
				if (data && data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						//console.info(data[i].content);
						var newSrc = data[i].imgsrc.substr(data[i].imgsrc.indexOf("/") + 1);
						listHtml += "<li><img src='" + newSrc + "' width='708' height='517' /></li>";
					}
				} else {
					listHtml = "<li>暂无图片数据！</li>";
				}
				//console.info(listHtml);
				$('#owog_index_imagesList').html(listHtml);
			}
		});

		$(".slider").YuxiSlider({
			width : 708, //容器宽度
			height : 517, //容器高度
			control : $('.control'), //绑定控制按钮
			during : 8000, //间隔3秒自动滑动
			speed : 800, //移动速度0.8秒
			mousewheel : false, //是否开启鼠标滚轮控制
			direkey : false //是否开启左右箭头方向控制
		});

		$('#owog_index_hisDg').datagrid({
			url : '${pageContext.request.contextPath}/owogImagesAction!doNotNeedSecurity_getAllDg.action',
			singleSelect : true,
			fit : true,
			fitColumns : true,
			toolbar : '#owog_index_tb',
			border : false,
			pageSize : 30,
			striped : true,
			rownumbers : true,
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 1,
					hidden : true
				},
				{
					field : 'class_',
					title : '班组',
					width : 50,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == undefined) {
							return '';
						} else {
							return '<span title="' + value + '">' + value + '</span>';
						}
					}
				},
				{
					field : 'name',
					title : '标杆人员姓名',
					width : 80,
					sortable : true
				},
				{
					field : 'startdate',
					title : '周期开始时间',
					width : 150,
					sortable : true
				},
				{
					field : 'enddate',
					title : '周期结束时间',
					width : 150,
					sortable : true
				},
				{
					field : 'filename',
					title : '历史照片文件',
					width : 200,
					formatter : function(value, row, index) {
						return "<a href='" + "${pageContext.request.contextPath}/" + row.imgsrc + "' target='_blank'>" + value + "</a>";
					}
				},
				{
					field : 'uploader',
					title : '照片上传人',
					width : 80,
					sortable : true
				},
				{
					field : 'uploadtime',
					title : '上传时间',
					width : 150,
					sortable : true
				}
			]],
			pagination : true,
			nowrap : false,
			sortName : 'uploadtime',
			sortOrder : 'desc',
			view : groupview,
			groupField : 'gName',
			groupFormatter : function(value, rows) {
				return value + ' - 已获得 <span style="color: #FF0000;font-weight: bold;">' + rows.length + '</span> 次周标杆';
			}
		});
	});

	function collapseGName() {
		$('#owog_index_hisDg').datagrid('collapseGroup');
	}

	function expandGName() {
		$('#owog_index_hisDg').datagrid('expandGroup');
	}
	
	function searchHisForm(){
		$('#owog_index_hisDg').datagrid('load', serializeObject($('#owog_index_searchForm')));
	}
	
	function clearHisForm(){
		$('#owog_index_searchForm').form('clear');
		$('#owog_index_hisDg').datagrid('load', {});
	}
</script>
</head>

<body id="owog_index_layout" class="easyui-layout">
	<div data-options="region:'south',title:'历史数据查询',split:true,collapsible:true"
		style="height:250px;background:#E7F0FF;">
		<table id="owog_index_hisDg"></table>
	</div>
	<div data-options="region:'center',title:'网操维上周标杆人员'" style="padding:20px;">
		<div class="demo">
			<a class="control prev"></a><a class="control next abs"></a>
			<!--自定义按钮，移动端可不写-->
			<div class="slider">
				<!--主体结构，请用此类名调用插件，此类名可自定义-->
				<ul id="owog_index_imagesList">
					<!-- 
					<li><img src="images/1.jpg" alt="两弯似蹙非蹙笼烟眉，一双似喜非喜含情目。" /></li>
					<li><img src="images/2.jpg" alt="态生两靥之愁，娇袭一身之病。" /></li>
					<li><img src="images/3.jpg" alt="泪光点点，娇喘微微。" /></li>
					<li><img src="images/4.jpg" alt="闲静似娇花照水，行动如弱柳扶风。" /></li>
					<li><img src="images/5.jpg" alt="心较比干多一窍，病如西子胜三分。" /></li>
					<li><img src="images/3.jpg" alt="泪光点点，娇喘微微。" /></li>
					<li><img src="images/2.jpg" alt="态生两靥之愁，娇袭一身之病。" /></li>
					 -->
				</ul>
			</div>
		</div>
	</div>

	<div id="owog_index_tb" style="padding:2px 5px;">
		<form id="owog_index_searchForm" method="post">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-collapse" plain="true"
				style="float:left;" onclick="collapseGName();">折叠</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-expand" plain="true" style="float:left;"
				onclick="expandGName();">展开</a>
			<div class="datagrid-btn-separator"></div>
			&nbsp;&nbsp;时间周期: <input class="easyui-datebox" name="startdate" style="width:110px"> 至:
			<input name="enddate" class="easyui-datebox" style="width:110px"> &nbsp;&nbsp;班组: <select
				class="easyui-combobox" name="class_" panelHeight="auto" style="width:100px">
				<option value="传输组">传输组</option>
				<option value="动力组">动力组</option>
				<option value="交换组">交换组</option>
				<option value="数据组">数据组</option>
				<option value="分析班">分析班</option>
				<option value="业务班">业务班</option>
				<option value="支撑班">支撑班</option>
			</select> &nbsp;&nbsp;标杆人员姓名: <input class="easyui-textbox" name="name" style="width:110px" /> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="searchHisForm();">查询</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true" onclick="clearHisForm();">重置</a>
		</form>
	</div>

</body>
</html>
