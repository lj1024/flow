/**
 * ajax验证登录账号是否存在
 */
var ajaxValidateUserName=function(rule, value, callback){
	if (!value) {
        return callback(new Error('账号不能为空'));
      }
	var isRepetition = true;
	var errMsg = '账号重复';
	$.ajax({
		   type: "POST",
		   url: "/user/validateUserName",
		   data: "userName="+value,
		   success: function(data){
			   if(data.status ){
				   //用户名存在
				   if(data.entity){
					   return callback(new Error('账号重复'));
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

      //创建对话框是否显示
      createDialogVisible:false,
      //创建对话框创建按钮是否禁用
      createButtonDisabled:false,
      //创建对话框是否显示
      editDialogVisible:false,
      //创建对话框创建按钮是否禁用
      editButtonDisabled:false,
      createForm:{
    	  username:'',
    	  realName:'',
    	  sex:'Male',
    	  phone:'',
    	  email:'',
    	  status:'1',
    	  roleid:null
    	  
      },
      editForm:{
    	  id:'',
    	  username:'',
    	  realName:'',
    	  sex:'Male',
    	  phone:'',
    	  email:'',
    	  status:null,
    	  password:'',
    	  isLeave:'No',
    	  roleid:null
      },
      createRules:{
    	  username:[{ required: true, message: '请输入登录账号', trigger: 'blur' },
    	            {validator: ajaxValidateUserName, trigger: 'blur'},
    	            { min: 3, max: 10, message: '长度在 3 到 10个字符', trigger: 'blur' },
    	            {pattern: /^(\w){3,10}$/,message: '只能输入3-10个字母、数字、下划线'}
    	            
    	            ],
    	  realName:[{ min: 2, max: 10, message: '长度在 2 到 10个字符', trigger: 'blur' }],
    	  phone:[ { type: 'number', message: '手机号必须为数字',trigger: 'blur'}],
    	  email:[{ type: 'email', message: '请输入正确的邮箱地址',trigger: 'blur'}],
          roleid:[{ required: true, message: '请选择角色', trigger: 'change' }]
      },
      editRules:{
    	  realName:[{ min: 2, max: 10, message: '长度在 2 到 10个字符', trigger: 'blur' }],
    	  phone:[ { type: 'number', message: '手机号必须为数字',trigger: 'blur'}],
    	  email:[{ type: 'email', message: '请输入正确的邮箱地址',trigger: 'blur'}],
          password:[{pattern: /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,16}$/,message: '只能输入6-16个字符,含有小写字母、大写字母、数字、特殊符号的两种及以上'}],
          roleid:[{ required: true, message: '请选择角色', trigger: 'change' }]

      },
      mainTable:{
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
    		this.mainTable.currentPage = val;
    		this.mainTable.loading=true;
    		//ajax获取当前页需要的数据
        	$.ajax({
        		   type: "POST",
        		   url: "/cert/page",
        		   data: "page="+val+"&size="+this.mainTable.pageSize,
        		   success: function(data){
        			   if(data.status ){
        				   app.mainTable.tableData = data.entity.results;
        				   app.mainTable.totalCount = data.entity.totalCount;
        			   } else {
        				   app.$alert(data.errMsg, '系统提示', {
        				          confirmButtonText: '确定'
        				        });
        			   }
    				   app.mainTable.loading = false;

        		   }
        		});
        },
        //每页显示数量变动调用方法
        handleSizeChange(val) {
            this.mainTable.pageSize=val;
            //刷新页面
            this.handleCurrentChange(1);
          },
        openCreateDialog:function(){
        	this.createDialogVisible = true;
        	this.$nextTick(function(){
            	this.$refs["createForm"].resetFields();

        	});
        	this.createButtonDisabled = false;
        	
        },
        openEditDialog:function(row){
        	console.log(row)
        	this.editDialogVisible = true;
        	this.$nextTick(function(){
            	this.$refs["editForm"].resetFields();
            	this.editForm.id=row.user.id;
            	this.editForm.username=row.user.username;
            	this.editForm.phone=row.user.phone;
            	this.editForm.email=row.user.email;
            	this.editForm.status=row.user.status.toString();
            	this.editForm.sex=row.user.sex;
            	this.editForm.realName=row.user.realName;
            	this.editForm.isLeave=row.user.isLeave;
            	this.editForm.roleid=row.role.id&&row.role.id.toString();

        	});
        	
        	this.editButtonDisabled = false;
        	
        },
        create:function(){
        	this.$refs["createForm"].validate(function(valid){
                if (valid) {
                	this.createButtonDisabled = true;
                	$.ajax({
                		   type: "POST",
                		   url: "/user/create",
                		   data: app.createForm,
                		   success: function(data){
                			   app.createDialogVisible=false;
                			   app.createButtonDisabled=false;
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
        edit:function(row){
        	this.$refs["editForm"].validate(function(valid){
                if (valid) {
                	this.editButtonDisabled = true;
                	$.ajax({
                		   type: "POST",
                		   url: "/user/update",
                		   data: app.editForm,
                		   success: function(data){
                			   app.editDialogVisible=false;
                			   app.editButtonDisabled=false;
                			   if(data.status ){
                				   //刷新用户表
                				 app.handleCurrentChange(1);
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
                } else {
                  return false;
                }
              });
        	
        },
        del:function(row){
            this.$confirm('确定要删除此用户?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
              center: true
            }).then(() => {
            	$.ajax({
         		   type: "POST",
         		   url: "/user/delete",
         		   data: {
         			   "userid":row.user.id
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
    	
    }
        
      }
  });
//初始化页面表格数据
app.handleCurrentChange(1);
