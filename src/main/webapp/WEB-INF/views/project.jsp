<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<title>项目管理</title>
   		<%@include file="/commons/resource.jsp" %>
        <script type="text/javascript" src="${ctx}/static/js/app/project.js"></script>
        <style type="text/css">
        	.operate-link:HOVER{
        		color: red;
        	}
        </style>
  </head>
	<body class="easyui-layout">
	<!-- Search panel start -->
 	 <div class="ui-search-panel" region="north" style="height: 70px;" title="搜索框" data-options="striped: true,collapsible:true,iconCls:'icon-search',border:false">  
	 	 <form id="searchForm">
	 	 	<p class="ui-fields">
	 	 	    <label class="ui-label">项目名:</label><input name="projectName" class="easyui-box ui-text">
	 	 	    <label class="ui-label">review状态:</label>
	 	 	    <select name="reviewFlag" class="easyui-combobox" style="width: 100px;height: 25px;">
	 	 	    	<option value="">请选择</option>
		 	 	    <option value="0">未review</option>
		 	 	    <option value="1">已review</option>
	 	 	    </select>
	        </p>
	        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	     </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <div region="center" border="false" >
     	<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" id="addBtn" plain="true">新增</a>
		</div>
     	<table id="data-list"></table>
     </div>

     <!-- Edit Form -->
     <div style="display: none">
     	<div id="edit-win" class="easyui-dialog" title="项目信息编辑页面" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:550px;height:480px;">  
	     	<form id="editForm" class="ui-form" method="post">  
	     		 <input class="hidden" type="text" name="projectId">
	     		 <div class="ui-edit">
		     	   <div class="ftitle">项目信息</div>    
		           <div class="fitem">  
		               <label>项目名:</label>  
		               <input class="ui-text easyui-validatebox" type="text" name="projectName" data-options="required:true" maxlength="50">
		           </div>  
		           <div class="fitem">  
		               <label>项目中文名:</label>  
		               <input class="ui-text easyui-validatebox" type="text" name="projectChName" data-options="required:true" maxlength="50">
		           </div>
		           <div class="fitem">  
		               <label>数据库ip:</label>  
		               <input class="ui-text easyui-validatebox" type="text" name="dbIp" data-options="required:true" maxlength="200">
		           </div>
		           <div class="fitem">  
		               <label>数据库端口:</label>  
		               <input class="easyui-numberbox ui-text" type="text" name="dbPort" data-options="required:true" max="65536" maxlength="20">
		           </div>
		           <div class="fitem">  
		               <label>数据库名:</label>  
		               <input class="easyui-validatebox ui-text" type="text" name="dbName" data-options="required:true" maxlength="50">
		           </div>
		           <div class="fitem">  
		               <label>数据库用户名:</label>  
		               <input class="ui-text easyui-validatebox" type="text" name="dbUser" data-options="required:true" maxlength="20">
		           </div>
		           <div class="fitem">  
		               <label>数据库密码:</label>  
		               <input class="ui-text easyui-validatebox" type="text" name="dbPassword" data-options="required:true" maxlength="20">
		           </div>
		           <div class="fitem">  
		               <label >mapper文件根路径(根路径可以配置多个用"，"逗号隔开):</label>
			           <textarea rows="20" cols="20" class="easyui-validatebox ui-text" name="mapperRootPath" data-options="required:true" style="width:350;height:80px;line-height: 16px;"></textarea> 	
		           </div>
		           <div class="fitem">  
		               <label>项目描述:</label>
		               <textarea rows="20" cols="20" class="easyui-validatebox ui-text" name="projectDesc" data-options="required:true" style="width:350;height:80px;line-height: 16px;"></textarea>
		           </div>
		         </div>
	     	</form>
	  	 </div>
     </div>
  </body>
</html>
