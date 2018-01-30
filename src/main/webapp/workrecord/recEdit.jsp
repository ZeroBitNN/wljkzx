<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="workrecord_recEdit_Form" method="post">
	<div class="easyui-panel" data-options="fit:true,border:false" style="width:90%;padding:30px 60px;">
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="ID:" name="id" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="工单类别:" name="recordtype" labelPosition="top" style="width:100%;height:52px"
				data-options="editable:false,
              valueField : 'label',
              textField : 'value',
              data : [ {
                label : '日常故障/事务处理',
                value : '日常故障/事务处理'
              }, {
                label : '班组协作/事务工单&生产任务单',
                value : '班组协作/事务工单&生产任务单'
              }, {
                label : '大面积故障管控',
                value : '大面积故障管控'
              }, {
                label : '网络割接管控',
                value : '网络割接管控'
              } ]">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="申告人及其联系:" name="proposer" labelPosition="top" data-options="required:true"
				style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="故障号码:" name="faultnumber" labelPosition="top" data-options=""
				style="width:100%;height:52px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="故障描述:" name="describe" labelPosition="top" data-options="multiline:true"
				style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="故障处理情况:" name="situation" labelPosition="top" data-options="multiline:true"
				style="width:100%;height:150px">
		</div>
		<div style="margin-bottom:20px">
      <input class="easyui-textbox" label="记录人:" name="inputer" labelPosition="top" data-options=""
        style="width:100%;height:52px" readonly="readonly">
    </div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" label="处理人:" name="handler" labelPosition="top" data-options=""
				style="width:100%;height:52px" readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-datetimebox" label="创建时间:" name="createtime" labelPosition="top" style="width:100%;height:52px"
				data-options="editable:false">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-datetimebox" label="修改时间:" name="modifytime" labelPosition="top" style="width:100%;height:52px"
				data-options="editable:false">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-combobox" label="状态:" name="status" labelPosition="top" style="width:100%;height:52px"
				data-options="editable:false,
				      valueField : 'label',
              textField : 'value',
              data : [ {
                label : '已处理',
                value : '已处理'
              }, {
                label : '未处理',
                value : '未处理'
              } ]">
		</div>
	</div>
</form>