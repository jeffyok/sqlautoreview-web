<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<title>sql review管理</title>
   		<script type="text/javascript">
			var applicationId = "${applicationId}";
   		</script> 
   		<%@include file="/commons/resource.jsp" %>
        <script type="text/javascript" src="${ctx}/static/js/app/sqlReview.js"></script>
        <style type="text/css">
        	.operate-link:HOVER{
        		color: red;
        	}
        	blockquote,.blockquote {
			    border-left-width: 10px;
			    background-color: rgba(102,128,153,0.05);
			    border-top-right-radius: 5px;
			    border-bottom-right-radius: 5px;
			    padding: 15px 20px;
			    border-left: 5px solid rgba(102,128,153,0.075);
			    margin: 0 0 5px;
			}
			.ui-text {
			    vertical-align: middle;
			    border: 1px solid #A4BED4;
			    font-size: 12px;
			    line-height: 25px;
			    height: 25px;
			    padding: 2px;
			    _height: 18px;
			    _line-height: 18px;
			    width: 150px;
			}
        </style>
  </head>
	<body class="easyui-layout">
	<!-- Search panel start -->
 	 <div class="ui-search-panel" region="north" style="height: 90px;" title="搜索框" data-options="striped: true,collapsible:true,iconCls:'icon-search',border:false">  
	 	 <form id="searchForm">
	 	 	<p class="ui-fields">
	 	 		<label class="ui-label">项目:</label>
	 	 	    <select name="projectId" class="easyui-combobox" style="width: 160px;height: 25px;">
	 	 	    	<option value="">请选择</option>
		 	 	    <c:forEach items="${projectList}" var="project">
		 	 	    	<option value="${project.projectId}" <c:if test="${project.projectId==projectId}">selected</c:if>>${project.projectChName}</option>
		 	 	    </c:forEach>
	 	 	    </select>&nbsp;&nbsp;&nbsp;
	 	 	    <label class="ui-label">自动检查状态:</label>
	 	 	    <select name="status" class="easyui-combobox" style="width: 100px;height: 25px;">
	 	 	    	<option value="">请选择</option>
		 	 	    <option value="0">未检查</option>
		 	 	    <option value="1">已检查</option>
	 	 	    </select>&nbsp;&nbsp;&nbsp;
	 	 	    <label class="ui-label">DBA检查状态:</label>
	 	 	    <select name="dbaReviewStatus" class="easyui-combobox" style="width: 100px;height: 25px;">
	 	 	    	<option value="">请选择</option>
		 	 	    <option value="0">未审核</option>
		 	 	    <option value="1">通过</option>
		 	 	    <option value="2">不通过</option>
	 	 	    </select>&nbsp;&nbsp;&nbsp;
	 	 	    <label class="ui-label">表名:</label><input name="tableName" class="easyui-box ui-text">
	 	 	    <label class="ui-label">mapper文件名:</label><input name="fileName" class="easyui-box ui-text"><br>
	 	 	    <label class="ui-label">mapper方法名:</label><input name="javaClassId" class="easyui-box ui-text">
	 	 	    <a href="#" id="btn-search" class="easyui-linkbutton" style="display: inline-block;" iconCls="icon-search">搜索</a>
	            <a href="#" id="btn-export" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	        </p>
	       
	     </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <div region="center" border="false" >
     	<table id="data-list"></table>
     </div>

     <!-- Edit Form -->
     <div style="display: none">
     	<div id="review-win" class="easyui-dialog" title="用户信息编辑页面" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:700px;height:830px;">  
	     	<form id="reviewForm" class="ui-form" method="post">  
	     		 <input class="hidden" type="text" name="id">
	     		 <div class="ui-edit">
		     	   <div class="ftitle">sql review信息</div>    
		           <div class="fitem">  
		               <label>项目:</label>  
		               <input type="text" class="ui-text" name="projectName" readonly="readonly">
		           </div>
		           <div class="fitem">  
		               <label>mapper方法名:</label>  
		               <input type="text" class="ui-text" name=javaClassId readonly="readonly">
		           </div> 
		           <div class="fitem">  
		               <label>mapper方法注释:</label>  
		               <input type="text" class="ui-text" name=sqlComment readonly="readonly">
		           </div>
		           <div class="fitem">  
		               <label>自动检查错误信息:</label>  
		               <textarea rows="20" cols="20" class="easyui-validatebox ui-text" name="autoReviewErr" readonly="readonly" style="width:480px;height:45px;line-height: 16px;"></textarea>
		           </div>
		           <div class="fitem">  
		               <label>自动检查提示:</label>
		               <textarea rows="20" cols="20" class="easyui-validatebox ui-text" name="autoReviewTip" readonly="readonly" style="width:480px;height:45px;line-height: 16px;"></textarea>
		           </div>
		           <div class="fitem">  
		               <label>review sql:</label> 
		               <textarea rows="20" cols="20" class="easyui-validatebox ui-text" name="realSql" readonly="readonly" style="width:480px;height:250px;line-height: 16px;"></textarea>
		           </div>
		           <div class="fitem">  
		               <label>sql自动Review索引:</label>  
		               <textarea rows="10" cols="20" class="easyui-validatebox ui-text" name="sqlAutoIndex" readonly="readonly" style="width:480px;height:70px;line-height: 16px;"></textarea>
		           </div>
		           <fieldset style="border: 1px solid #A4BED4">
				    	<legend style="font-weight: bold;font-size: 14px;">dba review</legend>
				    	<div class="fitem">
			               <label>dba索引:</label>  
			               <textarea rows="20" cols="20" class="easyui-validatebox ui-text" name="sqlDbaIndex" style="width:480px;height:55px;line-height: 16px;"></textarea>
			           </div>
			           <div class="fitem">  
			               <label>dba建议:</label>  
			               <textarea rows="20" cols="20" class="easyui-validatebox ui-text" name="dbaAdvice" style="width:480px;height:55px;line-height: 16px;"></textarea>
			           </div>
			           <div class="fitem">  
			               <label>审核:</label>  
			               <input type="radio" name="dbaReviewStatus" value="1" checked="checked" style="width: 30px;cursor: pointer;"/>通过 
			               <input type="radio" name="dbaReviewStatus" value="2" style="width: 30px;cursor: pointer;"/>不通过
			           </div>
				  </fieldset>
		         </div>
	     	</form>
	  	 </div>
     </div>
  </body>
</html>
