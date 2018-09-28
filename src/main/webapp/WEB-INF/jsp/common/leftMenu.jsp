<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div>
<el-menu
      default-active="${pageContext.request.getAttribute('javax.servlet.forward.request_uri')}"
      class="el-menu-vertical-demo"
      background-color="#d3dce6"
      
      :unique-opened=true
      @select="handleSelect"
      >
      <el-submenu index="1">
        <template slot="title">
           <i class="el-icon-setting"></i>
          <span>导航一</span>
        </template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="1-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项1</el-menu-item>
        </el-submenu>
      </el-submenu>
      
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span >导航二</span>
      </el-menu-item>
      
      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>证书管理 </span>
        </template>
        <shiro:hasPermission name="/cert/list">
        <el-menu-item index="/cert/list"><i class="el-icon-menu"></i>证书</el-menu-item>
         </shiro:hasPermission>
       <shiro:hasPermission name="/certdictionary/list"> 
        <el-menu-item index="/certdictionary/list"><i class="el-icon-menu"></i>证书字典</el-menu-item>
        </shiro:hasPermission> 
      </el-submenu>
      
       <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>系统设置   </span>
        </template>
        <shiro:hasPermission name="/user/list">
        <el-menu-item index="/user/list"><i class="el-icon-setting"></i>用户管理</el-menu-item>
        </shiro:hasPermission>
        <shiro:hasPermission name="/role/list">
        <el-menu-item index="/role/list"><i class="el-icon-setting"></i>角色管理</el-menu-item>
        </shiro:hasPermission>
        <shiro:hasPermission name="/permission/list">
        <el-menu-item index="/permission/list"><i class="el-icon-setting"></i>资源管理</el-menu-item>
        </shiro:hasPermission>
      </el-submenu>
    </el-menu>
</div>