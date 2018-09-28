<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
	
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/jsp/common/common.jsp"></jsp:include>
</head>
<body>

	<div id="app" >
	
	<el-row>
		 <el-col :span="24" class="grid-top bg-blue-light"> 
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
		</el-col>  
	</el-row> 
	<el-row>
			<el-col :span="6">
				<jsp:include page="/WEB-INF/jsp/common/leftMenu.jsp" />
			</el-col> 
			<el-col :span="18">
			<div class="flow-main">
						
						
		   <div class="buttonAboveTable"><shiro:hasPermission name="/user/create"><el-button  type="primary" @click="openCreateDialog" round >创建用户</el-button></shiro:hasPermission></div>
			
			<el-table :data="mainTable.tableData" border style="width: 100%"
			    v-loading="mainTable.loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
			>
    <el-table-column
        label="ID"
        prop="user.id" 
        width="50"
        >
      </el-table-column>
      <el-table-column
        prop="user.username"
        label="登录账号"
        style="width: 10%">
      </el-table-column>
        <el-table-column
        prop="user.realName"
        label="姓名"
        style="width: 10%">
      </el-table-column>
       <el-table-column
        prop="role.roledesc"
        label="角色"
        width="100"
        >
      </el-table-column>
      <el-table-column
        label="性别"
        style="width: 10%">
        <template slot-scope="scope">
         <span v-if="scope.row.user.sex == 'Male'">男</span>
         <span v-else-if="scope.row.user.sex == 'Female'">女</span>
         <span v-else>女</span>
      </template>
      </el-table-column>
        <el-table-column
        prop="user.phone"
        label="电话"
        style="width: 10%">
      </el-table-column>
        <el-table-column
        prop="user.email"
        label="邮箱"
        style="width: 10%">
      </el-table-column>
       <el-table-column
        prop="user.createTime"
        label="创建时间"
        width="200"
        >
      </el-table-column>
       <el-table-column
        prop="user.lastLoginTime"
        label="最后一次登录时间"
        width="200"
        >
      </el-table-column>
       <el-table-column
        label="在职状态"
        width="80">
        <template slot-scope="scope">
         <span v-if="scope.row.user.isLeave == 'Yes'">已离职</span>
         <span v-else-if="scope.row.user.isLeave == 'No'">在职</span>
         <span v-else>已离职</span>
      </template>
      </el-table-column>
       <el-table-column
        label="登录"
        style="width: 20%">
         <template slot-scope="scope">
         <span v-if="scope.row.user.status == 1"><i class="el-icon-success successIcon"></i></span>
         <span v-else-if="scope.row.user.status == 0"><i class="el-icon-error dangerIcon"></i></span>
         <span v-else><i class="el-icon-error dangerIcon"></i></span>
      </template>
      </el-table-column>
    <el-table-column
        label="操作"
        style="width: 20%">
            <template slot-scope="scope">
         <shiro:hasPermission name="/user/update">
        <el-button type="text" @click="openEditDialog(scope.row)" >编辑</el-button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/user/delete">
        <el-button type="text" @click="del(scope.row)">删除</el-button>
        </shiro:hasPermission>
      </template>
      </el-table-column>
    </el-table>
    <el-pagination
       background
       @size-change="handleSizeChange"
       @current-change="handleCurrentChange"
       :current-page.sync="mainTable.currentPage"
      :page-sizes="[5,10, 20, 50, 100]"
      :page-size="mainTable.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="mainTable.totalCount">
    </el-pagination>
  
  </div>
  
  <el-dialog
  title="创建用户"
  :visible.sync="createDialogVisible"
  width="30%"
  >
  <el-form ref="createForm" status-icon :rules="createRules" :model="createForm" size="medium"label-position="left" label-width="80px">
  
  <el-tooltip class="item" effect="dark" content="创建的账号默认密码为:abc.123" placement="top">
  <el-form-item label="登录账号" size="small" prop="username">
    <el-input v-model="createForm.username" placeholder="请输入登录账号,建议使用中文名字拼音"></el-input>
   </el-form-item>
    </el-tooltip>
    <el-form-item label="角色" prop="roleid">
    <el-select v-model="createForm.roleid" placeholder="请选择角色" >
    <c:forEach items="${roles}" var="role">
	<el-option label="${ role.roledesc}" value="${ role.id}"></el-option>
	</c:forEach>
    </el-select>
  </el-form-item>
  <el-form-item label="姓名" prop="realName">  
    <el-input v-model="createForm.realName" placeholder="请输入真实姓名"></el-input>
  </el-form-item>
  <el-form-item label="性别" prop="sex">  
    <el-radio-group v-model="createForm.sex">
      <el-radio label="Male">男</el-radio>
      <el-radio label="Female">女</el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="电话" prop="phone">  
        <el-input v-model.number="createForm.phone" placeholder="请输入电话号码"></el-input>
    
  </el-form-item>
  <el-form-item label="邮箱" prop="email">  
            <el-input v-model="createForm.email" placeholder="请输入邮箱"></el-input>
  </el-form-item>
   <el-form-item label="登录开启" prop="status">  
     <el-switch v-model="createForm.status"
     active-value="1"
    inactive-value="0"
    active-color="#13ce66"
     ></el-switch>
  </el-form-item>
</el-form>
<span slot="footer" class="dialog-footer">
    <el-button @click="createDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="create" :disabled="createButtonDisabled">立即创建</el-button>
  </span>

</el-dialog>


  <el-dialog
  title="修改用户"
  :visible.sync="editDialogVisible"
  width="30%"
  >
  <el-form ref="editForm" status-icon :rules="editRules" :model="editForm" label-position="left" size="medium" label-width="70px">
  
    <el-input v-model="editForm.id" type="hidden"></el-input>
  <el-form-item label="登录账号"  prop="username">
    <el-input v-model="editForm.username" :disabled="true"  placeholder="请输入登录账号,建议使用中文名字拼音"></el-input>
    </el-form-item>
   
  <el-form-item label="密码"  prop="password">
  <el-tooltip class="item" effect="dark" content="这里可以强制修改用户密码.如不需要,请不要填写" placement="top">
    <el-input v-model="editForm.password" type="password" auto-complete="off" placeholder="请输入密码"></el-input>
    </el-tooltip>
   </el-form-item>
      <el-form-item label="登录" prop="status">  
     <el-switch v-model="editForm.status"
     active-value="1"
    inactive-value="0"
    active-color="#13ce66"
     ></el-switch>
  </el-form-item>
   <el-form-item label="在职" prop="isLeave">  
     <el-switch v-model="editForm.isLeave"
     active-value="No"
    inactive-value="Yes"
    active-color="#13ce66"
     ></el-switch>
  </el-form-item>
    <el-form-item label="角色" prop="roleid">
    <el-select v-model="editForm.roleid" placeholder="请选择角色">
    <c:forEach items="${roles}" var="role">
	<el-option label="${ role.roledesc}" value="${ role.id}"></el-option>
	</c:forEach>
    </el-select>
  </el-form-item>
  <el-form-item label="姓名" prop="realName">  
    <el-input v-model="editForm.realName" placeholder="请输入真实姓名"></el-input>
  </el-form-item>
  <el-form-item label="性别" prop="sex">  
    <el-radio-group v-model="editForm.sex">
      <el-radio label="Male">男</el-radio>
      <el-radio label="Female">女</el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="电话" prop="phone">  
        <el-input v-model.number="editForm.phone" placeholder="请输入电话号码"></el-input>
    
  </el-form-item>
  <el-form-item label="邮箱" prop="email">  
            <el-input v-model="editForm.email" placeholder="请输入邮箱"></el-input>
  </el-form-item>

</el-form>
<span slot="footer" class="dialog-footer">
    <el-button @click="editDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="edit" :disabled="editButtonDisabled">保存</el-button>
  </span>

</el-dialog>



			</el-col> 
		</el-row> 
	</div>


</body>
<script src="/resource/js/user/list.js"></script>
</html>