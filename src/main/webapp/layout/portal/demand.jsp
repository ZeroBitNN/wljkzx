<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$.ajax({
			url : '${pageContext.request.contextPath}/demandAction!doNotNeedSecurity_listForShow.action',
			dataType : 'json',
			success : function(data) {
				var listHtml = '';
				//console.info(data);
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						//console.info(data[i].content);
						listHtml += "<li>" + data[i].content + "</li>";
					}
				}
				$('#demandList').html(listHtml);
			}
		});
	});
</script>

<div style="overflow:hidden;height:100%;width:90%;">
	<marquee direction="up" scrollamount="3" scrolldelay="200">
		<ul id="demandList">
			<!-- 
			<li>五个时间点巡视传输和动环系统:交接班时间点和大夜班两个时间点（0点，6点）。</li>
			<li>分析班交班工作配合：近期可能会有区域上协作单要求对几个违规拨号用户账号进行“封堵”，分析班已通知区域非行政班时间要求派发协作单至“宽带解封堵”及我们班工位，并电话通知我们值班5534086配合操作，操作方法将绑定信息改为协作单号即可。操作单注意查看工单是否有相关领导审批。</li>
			<li>（网警、电信内部人员）调取用户信息，请严格按照《用户信息核查调取流程》执行。</li>
			<li>解绑工作要求：为规避自动解绑程序出现异常而未能及时发现问题，要求：
				<ol>
					<li>每天早、中、夜三个班值班人员抽查一次解绑情况。随机抽取台账解绑账号，在创意上核查确认解绑是否正常，即：确认台账解绑后，创意上认证方式是否为：不做任何绑定，是则正常，否则异常。</li>
					<li>如遇异常情况，请及时手工解绑，并及时上报小郑。</li>
					<li>每个班次抽查确认后，需在jkb群留痕，以便日后核查。</li>
				</ol>
			</li>
			<li>割接放号工作要求：
				<ol>
					<li>首先明确是割接到IMS还是割接到软交换，切记不要弄错放号脚本。</li>
					<li>割接到IMS的，放号除需在SHLR上执行“IMS割接脚本”，仍需在IMS的EDS执行“IMS_EDS/ENUM批量脚本”，执行脚本前请检查脚本正确性。</li>
				</ol>
			</li>
			 -->
		</ul>
		<br> <br> <br> <br> <br>
	</marquee>
</div>