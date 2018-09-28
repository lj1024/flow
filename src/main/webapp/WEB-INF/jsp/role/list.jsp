<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						
						
		   <div class="buttonAboveTable"><shiro:hasPermission name="/role/create"><el-button @click="openCreateRoleDialog" type="primary" round >创建角色</el-button></shiro:hasPermission></div>
			
			<el-table :data="roleTable.tableData" border style="width: 100%"
			    v-loading="roleTable.loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
			>
    <el-table-column
        label="ID"
        prop="id" 
        style="width: 20%">
      </el-table-column>
      <el-table-column
        prop="roledesc"
        label="角色名称"
        style="width: 20%">
      </el-table-column>
    <el-table-column
        label="操作"
        style="width: 20%">
            <template slot-scope="scope">
         <shiro:hasPermission name="/role/assignResources">
        <el-button type="text" @click="openTreeDialog(scope.row)" >分配权限</el-button>
        </shiro:hasPermission>
        <shiro:hasPermission name="/role/delete">
        <el-button type="text" @click="deleteRole(scope.row)">删除</el-button>
        </shiro:hasPermission>
      </template>
      </el-table-column>
    </el-table>
    <el-pagination
       background
       @size-change="handleSizeChange"
       @current-change="handleCurrentChange"
       :current-page.sync="roleTable.currentPage"
      :page-sizes="[5,10, 20, 50, 100]"
      :page-size="roleTable.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="roleTable.totalCount">
    </el-pagination>
  
  </div>
  
    <el-dialog
  title="权限分配"
  :visible.sync="dialogVisible"
  width="30%"
  >
  <el-tree
  :data="resourceTree"
  show-checkbox
  default-expand-all
  node-key="id"
  ref="trees"
  highlight-current
  :props="defaultProps">
</el-tree>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="roleResourcesSave" :disabled="disabled">保存</el-button>
  </span>
</el-dialog>

    <el-dialog
  title="创建角色"
  :visible.sync="createRoleDialogVisible"
  width="30%"
  >
<el-form ref="createRoleform" status-icon :rules="rules" :model="createRoleform" label-position="top" label-width="50px">
  <el-form-item label="角色名称" size="small" prop="name">
    <el-input v-model="createRoleform.name" placeholder="请输入角色名称"></el-input>
   </el-form-item>
  <el-form-item label="权限">  
    <el-tree
    style="margin-left:150px"
  :data="resourceTree"
  show-checkbox
  default-expand-all
  node-key="id"
  ref="roleTrees"
  highlight-current
  :props="defaultProps">
</el-tree>
  </el-form-item>
</el-form>
<span slot="footer" class="dialog-footer">
    <el-button @click="createRoleDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="roleCreate" :disabled="createRoleButtonDisabled">立即创建</el-button>
  </span>

</el-dialog>
			</el-col> 
		</el-row> 
	</div>


</body>
<script src="/resource/js/role/list.js"></script>
</html>