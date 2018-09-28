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
			<el-table
      :data="tableData"
      border
      style="width: 100%">
    <el-table-column
        label="ID"
        prop="id" 
        style="width: 20%">
     <!--     <template scope="scope">
        <span style="margin-left: 10px">{{ (currentPage -1) * pageSize +scope.$index+1 }}</span>
      </template> -->
      </el-table-column>
      <el-table-column
        prop="name"
        label="资源名称"
        style="width: 20%">
      </el-table-column>
    <el-table-column
        prop="parentid"
        label="父资源名称ID"
        style="width: 20%">
      </el-table-column>
    <el-table-column
        prop="resurl"
        label="资源链接"
        style="width: 20%">
      </el-table-column>
    <el-table-column
        label="资源类型"
        style="width: 20%">
            <template slot-scope="scope">
        <span v-if="scope.row.type == 0">主菜单</span>
        <span  v-else-if="scope.row.type == 1">子菜单</span>
        <span  v-else-if="scope.row.type == 2">按钮</span>
        <span  v-else>其他</span>
        
     
      </template>
      </el-table-column>
    </el-table>
    <el-pagination
       background
       @size-change="handleSizeChange"
       @current-change="handleCurrentChange"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount">
    </el-pagination>
			
			</div>
			</el-col> 
		</el-row> 
	</div>


</body>
<script>

var app= new Vue({
    el: '#app',
    data: {
      title:'',//搜索标题
  	  message:"hello world!",
  	  totalCount:0,//列表总数量
  	  pageSize:10,//每页显示数量
  	  currentPage:1,//当前页
  	tableData: []//当前页面数据
    },
    methods: {
        handleCurrentChange(val) {
    		//设置当前页
    		this.currentPage = val;
    		//ajax获取当前页需要的数据
        	$.ajax({
        		   type: "POST",
        		   url: "/permission/page",
        		   data: "page="+val+"&size="+this.pageSize,
        		   success: function(data){
        			   if(data.status ){
        				   app.tableData = data.entity.results;
        				   app.totalCount = data.entity.totalCount;
        			   } else {
        				   app.$alert(data.errMsg, '系统提示', {
        				          confirmButtonText: '确定'
        				        });
        			   }
        		   }
        		});
          //console.log('当前页: '+val+" "+this.pageSize);
        },
        handleSizeChange(val) {
            this.pageSize=val;
            //刷新页面
            this.handleCurrentChange(1);
          }
      }
  });
//初始化页面表格数据
app.handleCurrentChange(1);
  </script>
</html>