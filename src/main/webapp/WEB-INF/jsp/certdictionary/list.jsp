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

	<div id="app">

		<el-row> <el-col :span="24" class="grid-top bg-blue-light">
		<jsp:include page="/WEB-INF/jsp/common/top.jsp" /> </el-col> </el-row>
		<el-row> <el-col :span="6"> <jsp:include
			page="/WEB-INF/jsp/common/leftMenu.jsp" /> </el-col> <el-col :span="18">
		<div class="flow-main">
			<el-input placeholder="输入关键字进行过滤" v-model="filterText" clearable> </el-input>
			<el-tree :data="certDicTree"  default-expand-all :expand-on-click-node="false"
				node-key="id" ref="trees" highlight-current :props="defaultProps"
				:filter-node-method="filterNode" :render-content="renderContent" >
			</el-tree>



		</div>


    <el-dialog
  title="创建证书字典"
  :visible.sync="createCertDicDialogVisible"
  width="30%"
  >
<el-form ref="createCertDicform" status-icon  :model="createCertDicform" label-position="top" label-width="50px">
<el-input v-model="createCertDicform.parentId" type="hidden" ></el-input>
<template v-if="currentNodeCertType === 'Cert'">
    <el-form-item label="证书" size="small" prop="cert">
    <el-input v-model="createCertDicform.cert" disabled ></el-input>
   </el-form-item>
    <el-form-item label="证书行业" size="small" prop="dicName"  :rules="[
      { required: true, message: '请输入证书行业', trigger: 'blur' },
      {validator: ajaxValidateDicName, trigger: 'blur'}
    ]">
    <el-input v-model="createCertDicform.dicName" placeholder="请输入证书行业" ></el-input>
   </el-form-item>
</template>
<template v-else-if="currentNodeCertType === 'Trade'">
   <el-form-item label="证书" size="small" prop="cert">
    <el-input v-model="createCertDicform.cert" disabled ></el-input>
   </el-form-item>
    <el-form-item label="证书行业" size="small" prop="trade">
    <el-input v-model="createCertDicform.trade" disabled ></el-input>
   </el-form-item>
   <el-form-item label="证书分类" size="small" prop="dicName"  :rules="[
      { required: true, message: '请输入证书分类', trigger: 'blur' },
      {validator: ajaxValidateDicName, trigger: 'blur'}
    ]">
    <el-input v-model="createCertDicform.dicName" placeholder="请输入证书分类"  ></el-input>
   </el-form-item>
</template>

<template v-else-if="currentNodeCertType === 'Domain'">
   <el-form-item label="证书" size="small" prop="cert">
    <el-input v-model="createCertDicform.cert" disabled ></el-input>
   </el-form-item>
    <el-form-item label="证书行业" size="small" prop="trade">
    <el-input v-model="createCertDicform.trade" disabled ></el-input>
   </el-form-item>
   <el-form-item label="证书分类" size="small" prop="domain">
    <el-input v-model="createCertDicform.domain"  disabled></el-input>
   </el-form-item>
   <el-form-item label="证书等级" size="small" prop="dicName"  :rules="[
      { required: true, message: '请输入证书等级', trigger: 'blur' },
      {validator: ajaxValidateDicName, trigger: 'blur'}
    ]" >
    <el-input v-model="createCertDicform.dicName" placeholder="请输入证书等级"></el-input>
   </el-form-item>
</template>
</el-form>
<span slot="footer" class="dialog-footer">
    <el-button @click="createCertDicDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="createCertDic" :disabled="createCertDicButtonDisabled">立即创建</el-button>
  </span>

</el-dialog>



<el-dialog
  title="编辑证书字典"
  :visible.sync="editCertDicDialogVisible"
  width="30%"
  >
<el-form ref="editCertDicform" status-icon  :model="editCertDicform" label-position="top" label-width="50px">
<el-input v-model="editCertDicform.id" type="hidden" ></el-input>

<template v-if="currentNodeCertType === 'Trade'">
    <el-form-item label="证书" size="small" prop="cert">
    <el-input v-model="editCertDicform.cert" disabled ></el-input>
   </el-form-item>
    <el-form-item label="证书行业" size="small" prop="dicName"  :rules="[
      { required: true, message: '请输入证书行业', trigger: 'blur' },
      {validator: ajaxValidateDicName, trigger: 'blur'}
    ]">
    <el-input v-model="editCertDicform.dicName" placeholder="请输入证书行业" ></el-input>
   </el-form-item>
</template>
<template v-else-if="currentNodeCertType === 'Domain'">
   <el-form-item label="证书" size="small" prop="cert">
    <el-input v-model="editCertDicform.cert" disabled ></el-input>
   </el-form-item>
    <el-form-item label="证书行业" size="small" prop="trade">
    <el-input v-model="editCertDicform.trade" disabled ></el-input>
   </el-form-item>
   <el-form-item label="证书分类" size="small" prop="dicName"  :rules="[
      { required: true, message: '请输入证书分类', trigger: 'blur' },
      {validator: ajaxValidateDicName, trigger: 'blur'}
    ]">
    <el-input v-model="editCertDicform.dicName" placeholder="请输入证书分类"  ></el-input>
   </el-form-item>
</template>

<template v-else-if="currentNodeCertType === 'Level'">
   <el-form-item label="证书" size="small" prop="cert">
    <el-input v-model="editCertDicform.cert" disabled ></el-input>
   </el-form-item>
    <el-form-item label="证书行业" size="small" prop="trade">
    <el-input v-model="editCertDicform.trade" disabled ></el-input>
   </el-form-item>
   <el-form-item label="证书分类" size="small" prop="domain">
    <el-input v-model="editCertDicform.domain"  disabled></el-input>
   </el-form-item>
   <el-form-item label="证书等级" size="small" prop="dicName"  :rules="[
      { required: true, message: '请输入证书等级', trigger: 'blur' },
      {validator: ajaxValidateDicName, trigger: 'blur'}
    ]" >
    <el-input v-model="editCertDicform.dicName" placeholder="请输入证书等级"></el-input>
   </el-form-item>
</template>

</el-form>
<span slot="footer" class="dialog-footer">
    <el-button @click="editCertDicDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="editCertDic" :disabled="editCertDicButtonDisabled">保存</el-button>
  </span>

</el-dialog>

		</el-col> 
		</el-row>
	</div>


</body>
<script src="/resource/js/certdictionary/list.js"></script>
</html>