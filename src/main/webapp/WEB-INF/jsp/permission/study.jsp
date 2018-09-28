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
	<my-component></my-component>
	<input v-model="msg">
	<child :message.sync="msg"></child>
	<my-checkbox v-model="foo" v-bind:value="msg"></my-checkbox>
	<currency-input v-model="price"></currency-input>
	<el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>
	 <el-dialog
  title="提示"
  :visible.sync="dialogVisible"
  width="30%"
  
  >
  <span>这是一段信息</span>
  <span slot="footer" class="dialog-footer">
   <!--  <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button> -->
  </span>
</el-dialog>
	
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="活动名称" prop="name">
    <el-input v-model="ruleForm.name"></el-input>
  </el-form-item>
  <el-form-item label="活动区域" prop="region">
    <el-select v-model="ruleForm.region" placeholder="请选择活动区域">
      <el-option label="区域一" value="shanghai"></el-option>
      <el-option label="区域二" value="beijing"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="活动时间" required>
    <el-col :span="11">
      <el-form-item prop="date1">
        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>
      </el-form-item>
    </el-col>
    <el-col class="line" :span="2">-</el-col>
    <el-col :span="11">
      <el-form-item prop="date2">
        <el-time-picker type="fixed-time" placeholder="选择时间" v-model="ruleForm.date2" style="width: 100%;"></el-time-picker>
      </el-form-item>
    </el-col>
  </el-form-item>
  <el-form-item label="即时配送" prop="delivery">
    <el-switch v-model="ruleForm.delivery"></el-switch>
  </el-form-item>
  <el-form-item label="活动性质" prop="type">
    <el-checkbox-group v-model="ruleForm.type">
      <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
      <el-checkbox label="地推活动" name="type"></el-checkbox>
      <el-checkbox label="线下主题活动" name="type"></el-checkbox>
      <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
    </el-checkbox-group>
  </el-form-item>
  <el-form-item label="特殊资源" prop="resource">
    <el-radio-group v-model="ruleForm.resource">
      <el-radio label="线上品牌商赞助"></el-radio>
      <el-radio label="线下场地免费"></el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="活动形式" prop="desc">
    <el-input type="textarea" v-model="ruleForm.desc"></el-input>
  </el-form-item>

</el-form>


<el-cascader
    :options="options"
    v-model="selectedOptions"
    >
  </el-cascader>
	
	</div>

</body>
<script>
Vue.component('currency-input', {
	  template: '\
	    <span>\
	      $\
	      <input\
	        ref="input"\
	        v-bind:value="value"\
	        v-on:input="updateValue($event.target.value)"\
	      >\
	    </span>\
	  ',
	  props: ['value'],
	  methods: {
	    // 不是直接更新值，而是使用此方法来对输入值进行格式化和位数限制
	    updateValue: function (value) {
	      var formattedValue = value
	        // 删除两侧的空格符
	        .trim()
	        // 保留 2 位小数
	        .slice(
	          0,
	          value.indexOf('.') === -1
	            ? value.length
	            : value.indexOf('.') + 3
	        )
	      // 如果值尚不合规，则手动覆盖为合规的值
	      if (formattedValue !== value) {
	        this.$refs.input.value = formattedValue
	      }
	      // 通过 input 事件带出数值
	      this.$emit('input', Number(formattedValue))
	    }
	  }
	})
Vue.component('my-component', {
	  template: '<div>A custom component!</div>'
	});
Vue.component('child', {
  // 声明 props
  props: ['message'],
  // 就像 data 一样，prop 也可以在模板中使用
  // 同样也可以在 vm 实例中通过 this.message 来使用
  template: '<button v-on:click="changeMessage">{{ message }}</button>',
  methods:{
	  changeMessage:function(){
		  this.$emit('update:message', '123');
	  }
  }
});
Vue.component('my-checkbox', {
	  model: {
	    prop: 'checked',
	    event: 'change'
	  },
	  props: {
	    checked: Boolean,
	    // 这样就允许拿 `value` 这个 prop 做其它事了
	    value: String
	  },
	  methods:{
		  changeValue:function(checked){
			  this.$emit('change', checked);
			  
		  }
	  },
	  template: '<input type="checkbox" id="checkbox" v-bind:checked="checked" v-on:change="changeValue($event.target.checked)" v-bind:value="value">'
		   
	})
var app= new Vue({
    el: '#app',
    data: {
    	options: [{
            value: 'zhinan',
            label: '指南',
            children: [{
              value: 'shejiyuanze',
              label: '设计原则',
              children: [{
                value: 'yizhi',
                label: '一致'
              }, {
                value: 'fankui',
                label: '反馈'
              }, {
                value: 'xiaolv',
                label: '效率'
              }, {
                value: 'kekong',
                label: '可控'
              }]
            }, {
              value: 'daohang',
              label: '导航',
              children: [{
                value: 'cexiangdaohang',
                label: '侧向导航'
              }, {
                value: 'dingbudaohang',
                label: '顶部导航'
              }]
            }]
          }, {
            value: 'zujian',
            label: '组件',
            children: [{
              value: 'basic',
              label: 'Basic',
              children: [{
                value: 'layout',
                label: 'Layout 布局'
              }, {
                value: 'color',
                label: 'Color 色彩'
              }, {
                value: 'typography',
                label: 'Typography 字体'
              }, {
                value: 'icon',
                label: 'Icon 图标'
              }, {
                value: 'button',
                label: 'Button 按钮'
              }]
            }, {
              value: 'form',
              label: 'Form',
              children: [{
                value: 'radio',
                label: 'Radio 单选框'
              }, {
                value: 'checkbox',
                label: 'Checkbox 多选框'
              }, {
                value: 'input',
                label: 'Input 输入框'
              }, {
                value: 'input-number',
                label: 'InputNumber 计数器'
              }, {
                value: 'select',
                label: 'Select 选择器'
              }, {
                value: 'cascader',
                label: 'Cascader 级联选择器'
              }, {
                value: 'switch',
                label: 'Switch 开关'
              }, {
                value: 'slider',
                label: 'Slider 滑块'
              }, {
                value: 'time-picker',
                label: 'TimePicker 时间选择器'
              }, {
                value: 'date-picker',
                label: 'DatePicker 日期选择器'
              }, {
                value: 'datetime-picker',
                label: 'DateTimePicker 日期时间选择器'
              }, {
                value: 'upload',
                label: 'Upload 上传'
              }, {
                value: 'rate',
                label: 'Rate 评分'
              }, {
                value: 'form',
                label: 'Form 表单'
              }]
            }, {
              value: 'data',
              label: 'Data',
              children: [{
                value: 'table',
                label: 'Table 表格'
              }, {
                value: 'tag',
                label: 'Tag 标签'
              }, {
                value: 'progress',
                label: 'Progress 进度条'
              }, {
                value: 'tree',
                label: 'Tree 树形控件'
              }, {
                value: 'pagination',
                label: 'Pagination 分页'
              }, {
                value: 'badge',
                label: 'Badge 标记'
              }]
            }, {
              value: 'notice',
              label: 'Notice',
              children: [{
                value: 'alert',
                label: 'Alert 警告'
              }, {
                value: 'loading',
                label: 'Loading 加载'
              }, {
                value: 'message',
                label: 'Message 消息提示'
              }, {
                value: 'message-box',
                label: 'MessageBox 弹框'
              }, {
                value: 'notification',
                label: 'Notification 通知'
              }]
            }, {
              value: 'navigation',
              label: 'Navigation',
              children: [{
                value: 'menu',
                label: 'NavMenu 导航菜单'
              }, {
                value: 'tabs',
                label: 'Tabs 标签页'
              }, {
                value: 'breadcrumb',
                label: 'Breadcrumb 面包屑'
              }, {
                value: 'dropdown',
                label: 'Dropdown 下拉菜单'
              }, {
                value: 'steps',
                label: 'Steps 步骤条'
              }]
            }, {
              value: 'others',
              label: 'Others',
              children: [{
                value: 'dialog',
                label: 'Dialog 对话框'
              }, {
                value: 'tooltip',
                label: 'Tooltip 文字提示'
              }, {
                value: 'popover',
                label: 'Popover 弹出框'
              }, {
                value: 'card',
                label: 'Card 卡片'
              }, {
                value: 'carousel',
                label: 'Carousel 走马灯'
              }, {
                value: 'collapse',
                label: 'Collapse 折叠面板'
              }]
            }]
          }, {
            value: 'ziyuan',
            label: '资源',
            children: [{
              value: 'axure',
              label: 'Axure Components'
            }, {
              value: 'sketch',
              label: 'Sketch Templates'
            }, {
              value: 'jiaohu',
              label: '组件交互文档'
            }]
          }],
          selectedOptions: [],
    	dialogVisible:true,
    	price:10,
    	foo:true,
    	msg:"helo",
      title:'',//搜索标题
  	  message:"hello world!",
  	  totalCount:0,//列表总数量
  	  pageSize:10,//每页显示数量
  	  currentPage:1,//当前页
  	tableData: [],//当前页面数据
  	ruleForm: {
        name: '',
        region: 'shanghai',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }
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
        				   this.tableData = data.entity.results;
        				   this.totalCount = data.entity.totalCount;
        			   } else {
        				   this.$alert(data.errMsg, '系统提示', {
        				          confirmButtonText: '确定'
        				        });
        			   }
        		   }
        		});
          console.log('当前页: '+val+" "+this.pageSize);
        },
      }
  });
//初始化页面表格列表数据
app.handleCurrentChange(1);

  </script>
</html>