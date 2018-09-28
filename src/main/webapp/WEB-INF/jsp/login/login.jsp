<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resource/css/common/elementUI.css">
<script src="/resource/js/common/vue.js"></script>
<script src="/resource/js/common/elementUI.js"></script>
<script type="text/javascript">

Vue.component('todo-item', {
	   template: '<li>这是个待办项</li>'
	 })
</script>
</head>
<body>
<div id="app">
  {{ message }}
</div>
<form action="/login/login" method="post">
<input type="text" name="username">
<input type="text" name="password">
<input type="submit" value="提交">
</form>
<div id="app-2">
  <span v-bind:title="message" v-bind:id="id">
    鼠标悬停几秒钟查看此处动态绑定的提示信息！
  </span>
  <ol>
  <!-- 创建一个 todo-item 组件的实例 -->
  <todo-item></todo-item>
</ol>
</div>

<div id="example">
  <p>Original message: "{{ message }}"</p>
  <p>Computed reversed message: "{{ reversedMessage }}"</p>
</div>

</body>
 <script>
   var app= new Vue({
      el: '#app',
      data: {
    	  message:"login"
      }
    })
   var app2 = new Vue({
	   el: '#app-2',
	   data: {
	     message: '页面加载于 ' + new Date(),
         id:"test"
	   }
	 })
   
   var vm = new Vue({
	   el: '#example',
	   data: {
	     message: 'Hello'
	   },
	   computed: {
	     // a computed getter
	     reversedMessage: function () {
	       // `this` points to the vm instance
	       return this.message.split('').reverse().join('')
	     }
	   }
	 })
  </script>
</html>