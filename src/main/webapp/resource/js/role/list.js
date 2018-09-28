/**
 * ajax验证角色名称是否存在
 */
var ajaxValidateRoleName=function(rule, value, callback){
	if (!value) {
        return callback(new Error('账号不能为空'));
      }
	var isRepetition = true;
	var errMsg = '角色名称重复';
	$.ajax({
		   type: "POST",
		   url: "/role/validateRoleName",
		   data: "roleName="+value,
		   success: function(data){
			   if(data.status ){
				   //角色存在
				   if(data.entity){
					   return callback(new Error('角色名称重复'));
				   } else {
					   return callback();
				   }
			   } else {
				   return callback(new Error(data.errMsg));
			   }

		   }
		});
};

var app= new Vue({
    el: '#app',
    data: {
      //分配权限对话框是否显示
      dialogVisible:false,
      //创建角色对话框是否显示
      createRoleDialogVisible:false,
      //要修改的权限的角色的id
      selectRoleId:null,
      //权限对话框保存按钮是否禁用
      disabled:false,
      //创建角色对话框创建按钮是否禁用
      createRoleButtonDisabled:false,
      createRoleform:{
    	  name:''
      },
      rules:{
    	  name:[{ required: true, message: '请输入角色名称', trigger: 'blur' },
    	        {validator: ajaxValidateRoleName, trigger: 'blur'}]
      },
      //所有的权限
      resourceTree:[],
      defaultProps:{
      	children: 'childResources',
          label: 'name'
      },
      //选中的权限
      checkedResourceTree:[],
      roleTable:{
    	  totalCount:0,//列表总数量
      	  pageSize:10,//每页显示数量
      	  currentPage:1,//当前页
      	  loading:false,
      	  tableData: []//当前页面数据
      }
    },
    methods: {
    	//页码变动调用方法
        handleCurrentChange(val) {
    		//设置当前页
    		this.roleTable.currentPage = val;
    		this.roleTable.loading=true;
    		//ajax获取当前页需要的数据
        	$.ajax({
        		   type: "POST",
        		   url: "/role/page",
        		   data: "page="+val+"&size="+this.roleTable.pageSize,
        		   success: function(data){
        			   if(data.status ){
        				   app.roleTable.tableData = data.entity.results;
        				   app.roleTable.totalCount = data.entity.totalCount;
        			   } else {
        				   app.$alert(data.errMsg, '系统提示', {
        				          confirmButtonText: '确定'
        				        });
        			   }
    				   app.roleTable.loading = false;

        		   }
        		});
          //console.log('当前页: '+val+" "+this.pageSize);
        },
        //每页显示数量变动调用方法
        handleSizeChange(val) {
            this.roleTable.pageSize=val;
            //刷新页面
            this.handleCurrentChange(1);
          },
        createRole:function(){
        	
        },
          //初始化权限树
        initResourceTree:function(){
        	$.ajax({
     		   type: "POST",
     		   url: "/permission/getResourcesTree",
     		   data: "",
     		   success: function(data){
     			   if(data.status ){
     				   app.resourceTree = data.entity;
     			   } else {
     				   app.$alert(data.errMsg, '系统提示', {
     				          confirmButtonText: '确定'
     				        });
     			   }
     		   }
     		});
        },
        //获取角色对应的权限
        openTreeDialog:function(row){
        	this.dialogVisible = true;
        	this.selectRoleId=row.id;
        	this.$nextTick(function(){
        		app.$refs.trees.setCheckedKeys([]);
        	});
        	$.ajax({
      		   type: "POST",
      		   url: "/permission/getRoleResourceIds",
      		   data: "roleId="+row.id,
      		   success: function(data){
      			   if(data.status ){
      				 app.$refs.trees.setCheckedKeys(data.entity);
      			   } else {
      				   app.$alert(data.errMsg, '系统提示', {
      				          confirmButtonText: '确定'
      				        });
      			   }
      		   }
      		});
        	
        	
        },
        
        openCreateRoleDialog:function(){
        	this.createRoleDialogVisible = true;
        	this.createRoleform.name='';
        	this.$nextTick(function(){
        		app.$refs.roleTrees.setCheckedKeys([]);
            	app.$refs["createRoleform"].resetFields();

        	});
        	this.createRoleButtonDisabled = false;
        	
        },
        roleCreate:function(){
        	this.$refs["createRoleform"].validate(function(valid){
                if (valid) {
                	this.createRoleButtonDisabled = true;
                	$.ajax({
                		   type: "POST",
                		   url: "/role/create",
                		   traditional: true,
                		   data: {
                			   "roleName":app.createRoleform.name,
                			   "resourceIds":app.$refs.roleTrees.getCheckedKeys(true)
                		   },
                		   success: function(data){
                			   app.createRoleDialogVisible=false;
                			   app.createRoleButtonDisabled=false;
                			   if(data.status ){
                				   //刷新角色表
                				 app.handleCurrentChange(1);
                				 app.$message({
                                     type: 'success',
                                     message: '创建成功!'
                                   });
                			   } else {
                				   app.$alert(data.errMsg, '系统提示', {
                				          confirmButtonText: '确定'
                				        });
                			   }
                		   }
                		});
                } else {
                  return false;
                }
              });
        	
        },
        deleteRole:function(row){
                this.$confirm('删除此角色将清空相关人员的权限, 是否继续?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning',
                  center: true
                }).then(() => {
                	$.ajax({
             		   type: "POST",
             		   url: "/role/delete",
             		   data: {
             			   "id":row.id,
             			   "roledesc":row.roledesc
             		   },
             		   success: function(data){
             			   if(data.status ){
             				   //刷新角色表
             				 app.handleCurrentChange(1);
             				  app.$message({
             	                    type: 'success',
             	                    message: '删除成功!'
             	                  });
             			   } else {
             				   app.$alert(data.errMsg, '系统提示', {
             				          confirmButtonText: '确定'
             				        });
             			   }
             		   }
             		});
                
                }).catch(() => {
                  this.$message({
                    type: 'info',
                    message: '已取消删除'
                  });
                });
        	
        },
        roleResourcesSave:function(){
        	this.disabled = true;
        	$.ajax({
       		   type: "POST",
       		   url: "/role/saveRoleResouces",
       		   traditional: true,
       		   data: {
       			   "roleId":app.selectRoleId,
       			   "resourceIds":app.$refs.trees.getCheckedKeys(true)
       		   },
       		   success: function(data){
       			   app.dialogVisible=false;
       			   app.disabled=false;
       			   if(data.status ){
       				app.$message({
                        type: 'success',
                        message: '保存成功!'
                      });
       			   } else {
       				   app.$alert(data.errMsg, '系统提示', {
       				          confirmButtonText: '确定'
       				        });
       			   }
       		   }
       		});
        }
      }
  });
//初始化页面表格数据
app.handleCurrentChange(1);
//初始化权限树数据
app.initResourceTree();