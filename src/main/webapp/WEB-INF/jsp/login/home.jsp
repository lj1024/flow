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
			<div class="grid-content bg-purple"></div>
			
			
			
			</el-col> 
		</el-row> 
	</div>


</body>
<script>
   var app= new Vue({
      el: '#app',
      data: {
    	  message:"login"
      }
    })
   
  </script>
</html>