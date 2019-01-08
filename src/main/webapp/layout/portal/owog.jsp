<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="css/sliderStyle.css">
<script src="jslib/YuxiSlider.jQuery.min.js"></script>

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
						listHtml += "<li><a href='${pageContext.request.contextPath}/owog/index.jsp' target='_blank'>"
						listHtml += "<img src='" + data[i].imgsrc + "' width='601' height='410' /></a></li>";
					}
				} else {
					listHtml = "<li>暂无图片数据！</li>";
				}
				//console.info(listHtml);
				$('#imagesList').html(listHtml);
			}
		});
		
		$(".slider").YuxiSlider({
			width : 601, //容器宽度
			height : 410, //容器高度
			control : $('.control'), //绑定控制按钮
			during : 8000, //间隔3秒自动滑动
			speed : 800, //移动速度0.8秒
			mousewheel : false, //是否开启鼠标滚轮控制
			direkey : false //是否开启左右箭头方向控制
		});
	});
</script>

<div style="margin:20px 0;"></div>
<div class="demo">
	<a class="control prev"></a><a class="control next abs"></a>
	<!--自定义按钮，移动端可不写-->
	<div class="slider">
		<!--主体结构，请用此类名调用插件，此类名可自定义-->
		<ul id="imagesList">
		<!-- 
			<li><a href="${pageContext.request.contextPath}/owog/index.jsp" target="_blank"><img
					src="owog/images/1.jpg" alt="两弯似蹙非蹙笼烟眉，一双似喜非喜含情目。" /></a></li>
			<li><a href="${pageContext.request.contextPath}/owog/index.jsp" target="_blank"><img
					src="owog/images/2.jpg" alt="态生两靥之愁，娇袭一身之病。" /></a></li>
			<li><a href="${pageContext.request.contextPath}/owog/index.jsp" target="_blank"><img
					src="owog/images/3.jpg" alt="泪光点点，娇喘微微。" /></a></li>
			<li><a href="${pageContext.request.contextPath}/owog/index.jsp" target="_blank"><img
					src="owog/images/4.jpg" alt="闲静似娇花照水，行动如弱柳扶风。" /></a></li>
			<li><a href="${pageContext.request.contextPath}/owog/index.jsp" target="_blank"><img
					src="owog/images/5.jpg" alt="心较比干多一窍，病如西子胜三分。" /></a></li>
			<li><a href="${pageContext.request.contextPath}/owog/index.jsp" target="_blank"><img
					src="owog/images/3.jpg" alt="泪光点点，娇喘微微。" /></a></li>
			<li><a href="${pageContext.request.contextPath}/owog/index.jsp" target="_blank"><img
					src="owog/images/2.jpg" alt="态生两靥之愁，娇袭一身之病。" /></a></li>
		 -->
		</ul>
	</div>
</div>
