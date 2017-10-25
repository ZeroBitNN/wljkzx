<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<form id="admin_yhgl_addForm" method="post">
    <table align="center">
      <tr>
        <th><br></th>
        <td><br></td>
      </tr>
      <tr>
        <th align="right">用户名：</th>
        <td align="left"><input name="username" class="easyui-validatebox" data-options="required:true" /></td>
        <td></td>
        <th align="right">角色：</th>
        <td align="left"><input id="admin_yhgl_roleCombo" name="role" class="easyui-combobox"
          data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/userAction!doNotNeedSecurity_getRole.action',required:true" /></td>
      </tr>
      <tr>
        <th align="right">密码：</th>
        <td align="left"><input name="pwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
        <td></td>
        <th align="right">重复密码：</th>
        <td align="left"><input name="rePwd" type="password" class="easyui-validatebox"
          data-options="required:true,validType:'eqPwd[\'#admin_yhgl_addForm input[name=pwd]\']'" /></td>
      </tr>
      <tr>
        <th><br></th>
        <td><br></td>
      </tr>
    </table>
  </form>