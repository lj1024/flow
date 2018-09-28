
var app= new Vue({
    el: '#app',
    
    data: {
    	createCertDicDialogVisible:false,
    	createCertDicButtonDisabled:false,
    	editCertDicDialogVisible:false,
        editCertDicButtonDisabled:false,
    	currentNodeCertType:'',
    	operation:'',
    	createCertDicform:{
    		parentId:'',
    		cert:'',
    		trade:'',
    		domain:'',
    		dicName:''
    		
    	},
    	editCertDicform:{
    		id:'',
    		cert:'',
    		trade:'',
    		domain:'',
    		dicName:''
    		
    	},
    	filterText:'',
      //所有的证书
      certDicTree:[],
      defaultProps:{
      	children: 'childs',
          label: 'dicName'
      }
    },
    watch: {
        filterText:function(val) {
        	console.log(this.$refs.trees);
          app.$refs.trees.filter(val);
        }
      },
    methods: {
    	//ajax验证字典名称是否存在
    	ajaxValidateDicName:function(rule, value, callback){
    		var parentId=null;
    		if(app.operation == 'create'){
    			parentId = app.$refs.trees.getCurrentNode().id;
    		}else if(app.operation == 'edit') {
    			parentId = app.$refs.trees.getCurrentNode().parentId;
    		}
    		if(!parentId){
    			return callback(new Error('证书字典节点异常'));
    		}
    		if (!value) {
    	        return callback(new Error('名称不能为空'));
    	      }
    		$.ajax({
    			   type: "POST",
    			   url: "/certdictionary/validateDicName",
    			   data: {'parentId':parentId,'dicName':value},
    			   success: function(data){
    				   if(data.status ){
    					   //名称存在
    					   if(data.entity){
    						   //编辑情况下,名称和原来一样认为验证通过
    						   if(app.operation == 'edit' && data.entity.id == app.$refs.trees.getCurrentNode().id){
    							   return callback();
    						   } else {
        						   return callback(new Error('名称重复'));

    						   }
    					   } else {
    						   return callback();
    					   }
    				   } else {
    					   return callback(new Error(data.errMsg));
    				   }

    			   }
    			});
    	},
    	
    	filterNode:function(value,data,node) {
    		console.log(node);
            if (!value) return true;
            return (node.parent.label&& node.parent.label.indexOf(value) !== -1) || data.dicName.indexOf(value) !== -1 ;
          },
    	initCertDictionaryTree:function(){
        	$.ajax({
     		   type: "POST",
     		   url: "/certdictionary/getCertDicTree",
     		   data: "",
     		   success: function(data){
     			   if(data.status ){
     				  app.certDicTree = [];
     				   app.certDicTree.push(data.entity);
     			   } else {
     				   app.$alert(data.errMsg, '系统提示', {
     				          confirmButtonText: '确定'
     				        });
     			   }
     		   }
     		});
        },
        createCertDic:function(){
        	this.$refs["createCertDicform"].validate(function(valid){
                if (valid) {
                	this.createCertDicButtonDisabled = true;
                	$.ajax({
               		   type: "POST",
               		   url: "/certdictionary/create",
               		   data: {
               			 parentId:app.createCertDicform.parentId,
               			parentCertType:app.currentNodeCertType,
               			dicName:app.createCertDicform.dicName
               		   },
               		   success: function(response){
             				 app.createCertDicDialogVisible=false;

               			   if(response.status ){
               				 //获取当前选中节点
               				var data = app.$refs.trees.getCurrentNode();
               				console.log(data);
               				if (!data.childs) {
               					app.$set(data, 'childs', []);
               	            }
               				data.childs.push(response.entity)
               				 app.$message({
                                  type: 'success',
                                  message: '创建成功!'
                                });
               				/*  app.certDicTree = [];
               				   app.certDicTree.push(data.entity);*/
               			   } else {
               				   app.$alert(response.errMsg, '系统提示', {
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
        editCertDic:function(){
          	this.$refs["editCertDicform"].validate(function(valid){
                if (valid) {
                	this.editCertDicButtonDisabled = true;
                	$.ajax({
               		   type: "POST",
               		   url: "/certdictionary/edit",
               		   data: {
               			 id:app.editCertDicform.id,
               			dicName:app.editCertDicform.dicName
               		   },
               		   success: function(response){
             				 app.editCertDicDialogVisible = false;

               			   if(response.status ){
               				   console.log(app.$refs.trees.getCurrentNode());
               				 //获取当前选中节点
               				app.$refs.trees.getCurrentNode().dicName=app.editCertDicform.dicName;
               				
               				 app.$message({
                                  type: 'success',
                                  message: '保存成功!'
                                });
               				
               			   } else {
               				   app.$alert(response.errMsg, '系统提示', {
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
          renderContent:function ( createElement,{ node, data, store }) {
        	  
        	  
        	  var self = this;
        	  
        	  if(data.certType == 'Cert'){
        		  return createElement('span', {
            		  style:'flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;'
            	  },[createElement('span',[createElement('span',node.label)]),
            	     createElement('span',
            	    		 [createElement('el-button',
            	    		 {on:{click:function(event){

            	    			// event.stopPropagation();
            	    			 //初始化表单数据
            	    			 self.operation='create';
            	    			 self.currentNodeCertType='';
            					 self.createCertDicform.cert='';
        	    				 self.createCertDicform.trade='';
        	    				 self.createCertDicform.domain='';
        	    				 self.createCertDicform.parentId='';
        	    				 self.createCertDicform.dicName='';
        	    				 //显示创建字典对话框
            	    			 self.createCertDicDialogVisible=true;
            	    			 //启用创建字典按钮
            	    			 self.createCertDicButtonDisabled=false;
            	    			 self.$nextTick(function(){
            	    				//当前节点类型
                	    			 self.currentNodeCertType=data.certType;
            	    				//设置要添加节点的父id
            	    				 self.createCertDicform.parentId=data.id;
            	    				 self.$refs["createCertDicform"].resetFields();

                	    			 if(self.currentNodeCertType === 'Cert') {
                	    				 self.createCertDicform.cert=data.dicName;
                	    			 }else  if(self.currentNodeCertType === 'Trade') {
                	    				 self.createCertDicform.cert=node.parent.label;
                	    				 self.createCertDicform.trade=data.dicName;
                	    			 }else if(self.currentNodeCertType === 'Domain'){
                	    				 self.createCertDicform.cert=node.parent.parent.label;
                	    				 self.createCertDicform.trade=node.parent.label;
                	    				 self.createCertDicform.domain=data.dicName;
                	    			 }
            	    			 });

            	            }},attrs:{type:'text'}},'添加')
            	            ])]);
        		  
        	  } else if (data.certType == 'Trade' || data.certType == 'Domain') {
        		  return createElement('span', {
            		  style:'flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;'
            	  },[createElement('span',[createElement('span',node.label)]),
            	     createElement('span',
            	    		 [createElement('el-button',
            	    		 {on:{click:function(event){

            	    			// event.stopPropagation();
            	    			 //初始化表单数据
            	    			 self.operation='create';
            	    			 self.currentNodeCertType='';
            					 self.createCertDicform.cert='';
        	    				 self.createCertDicform.trade='';
        	    				 self.createCertDicform.domain='';
        	    				 self.createCertDicform.parentId='';
        	    				 self.createCertDicform.dicName='';
        	    				 //显示创建字典对话框
            	    			 self.createCertDicDialogVisible=true;
            	    			 //启用创建字典按钮
            	    			 self.createCertDicButtonDisabled=false;
            	    			 self.$nextTick(function(){
            	    				//当前节点类型
                	    			 self.currentNodeCertType=data.certType;
            	    				//设置要添加节点的父id
            	    				 self.createCertDicform.parentId=data.id;
            	    				 self.$refs["createCertDicform"].resetFields();

                	    			 if(self.currentNodeCertType === 'Cert') {
                	    				 self.createCertDicform.cert=data.dicName;
                	    			 }else  if(self.currentNodeCertType === 'Trade') {
                	    				 self.createCertDicform.cert=node.parent.label;
                	    				 self.createCertDicform.trade=data.dicName;
                	    			 }else if(self.currentNodeCertType === 'Domain'){
                	    				 self.createCertDicform.cert=node.parent.parent.label;
                	    				 self.createCertDicform.trade=node.parent.label;
                	    				 self.createCertDicform.domain=data.dicName;
                	    			 }
            	    			 });

            	            }},attrs:{type:'text'}},'添加'),
            	            createElement('el-button',{
            	            	on:{
            	            		click:function(event){
            	            			//初始化表单数据
                   	    			 self.operation='edit';

                   	    			 self.currentNodeCertType='';
                   					 self.editCertDicform.cert='';
               	    				 self.editCertDicform.trade='';
               	    				 self.editCertDicform.domain='';
               	    				 self.editCertDicform.dicName='';
            	            			 //显示创建字典对话框
                   	    			 self.editCertDicDialogVisible=true;
                   	    			 //启用创建字典按钮
                   	    			 self.editCertDicButtonDisabled=false;
                   	    			 self.editCertDicform.id='';
                   	    			 self.$nextTick(function(){
                   	    			     //当前节点类型
                    	    			 self.currentNodeCertType=data.certType;
                   	    				 self.$refs["editCertDicform"].resetFields();
                   	    				 self.editCertDicform.id=data.id;
                   	    				self.editCertDicform.dicName=data.dicName;
                   	    			 if(self.currentNodeCertType === 'Trade') {
                	    				 self.editCertDicform.cert=node.parent.label;
                	    			 }else  if(self.currentNodeCertType === 'Domain') {
                	    				 self.editCertDicform.cert=node.parent.parent.label;
                	    				 self.editCertDicform.trade=node.parent.label;
                	    			 }else if(self.currentNodeCertType === 'Level'){
                	    				 self.editCertDicform.cert=node.parent.parent.parent.label;
                	    				 self.editCertDicform.trade=node.parent.parent.label;
                	    				 self.editCertDicform.domain=node.parent.label;
                	    			 }

                       	    	
                   	    			 });
            	            		}
            	            	},
            	            	attrs:{type:'text'}
            	            },'编辑'),
            	            createElement('el-button',{
            	            	on:{
            	            		click:function(event){
            	            			event.stopPropagation();
            	            			app.$confirm('确定要删除此字典?', '提示', {
            	            	              confirmButtonText: '确定',
            	            	              cancelButtonText: '取消',
            	            	              type: 'warning',
            	            	              center: true
            	            	            }).then(() => {
            	            	            	
            	            	            	$.ajax({
            	            	         		   type: "POST",
            	            	         		   url: "/certdictionary/delete",
            	            	         		   data: {
            	            	         			   "id":data.id
            	            	         		   },
            	            	         		   success: function(response){
            	            	         			   if(response.status ){
            	            	         				  store.remove(data);
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
            	            	              app.$message({
            	            	                type: 'info',
            	            	                message: '已取消删除'
            	            	              });
            	            	            });
            	            			
            	            		}
            	            	},
            	            	attrs:{type:'text'}
            	            },'删除')])]);
        		  
        	  }else if (data.certType === 'Level') {
        		  
        		  return createElement('span', {
            		  style:'flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;'
            	  },[createElement('span',[createElement('span',node.label)]),
            	     createElement('span',
            	    		 [createElement('el-button',{
            	            	on:{
            	            		click:function(event){
            	            			//初始化表单数据
                   	    			 self.operation='edit';

                   	    			 self.currentNodeCertType='';
                   					 self.editCertDicform.cert='';
               	    				 self.editCertDicform.trade='';
               	    				 self.editCertDicform.domain='';
               	    				 self.editCertDicform.dicName='';
            	            			 //显示创建字典对话框
                   	    			 self.editCertDicDialogVisible=true;
                   	    			 //启用创建字典按钮
                   	    			 self.editCertDicButtonDisabled=false;
                   	    			 self.editCertDicform.id='';
                   	    			 self.$nextTick(function(){
                   	    			     //当前节点类型
                    	    			 self.currentNodeCertType=data.certType;
                   	    				 self.$refs["editCertDicform"].resetFields();
                   	    				 self.editCertDicform.id=data.id;
                   	    				self.editCertDicform.dicName=data.dicName;
                   	    			 if(self.currentNodeCertType === 'Trade') {
                	    				 self.editCertDicform.cert=node.parent.label;
                	    			 }else  if(self.currentNodeCertType === 'Domain') {
                	    				 self.editCertDicform.cert=node.parent.parent.label;
                	    				 self.editCertDicform.trade=node.parent.label;
                	    			 }else if(self.currentNodeCertType === 'Level'){
                	    				 self.editCertDicform.cert=node.parent.parent.parent.label;
                	    				 self.editCertDicform.trade=node.parent.parent.label;
                	    				 self.editCertDicform.domain=node.parent.label;
                	    			 }

                       	    	
                   	    			 });
            	            		}
            	            	},
            	            	attrs:{type:'text'}
            	            },'编辑'),
            	            createElement('el-button',{
            	            	on:{
            	            		click:function(event){
            	            			event.stopPropagation();
            	            			app.$confirm('确定要删除此字典?', '提示', {
            	            	              confirmButtonText: '确定',
            	            	              cancelButtonText: '取消',
            	            	              type: 'warning',
            	            	              center: true
            	            	            }).then(() => {
            	            	            	
            	            	            	$.ajax({
            	            	         		   type: "POST",
            	            	         		   url: "/certdictionary/delete",
            	            	         		   data: {
            	            	         			   "id":data.id
            	            	         		   },
            	            	         		   success: function(response){
            	            	         			   if(response.status ){
            	            	         				  store.remove(data);
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
            	            	              app.$message({
            	            	                type: 'info',
            	            	                message: '已取消删除'
            	            	              });
            	            	            });
            	            			
            	            		}
            	            	},
            	            	attrs:{type:'text'}
            	            },'删除')])]);
        		  
        	  }
        	  
          }
      }

  });

//初始化证书树数据
app.initCertDictionaryTree();