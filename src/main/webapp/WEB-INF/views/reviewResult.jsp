<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<title>review 结果管理</title>
   		<%@include file="/commons/resource.jsp" %>
        <script type="text/javascript" src="${ctx}/static/js/app/reviewResult.js"></script>
        <style type="text/css">
        	.operate-link:HOVER{
        		color: red;
        	}
        	blockquote {
			    border-left-width: 10px;
			    background-color: rgba(102,128,153,0.05);
			    border-top-right-radius: 5px;
			    border-bottom-right-radius: 5px;
			    padding: 15px 20px;
			    border-left: 5px solid rgba(102,128,153,0.075);
			    padding: 10.5px 21px;
			    margin: 0 0 21px;
			}
        </style>
  </head>
	<body class="easyui-layout">
	<!-- Search panel start -->
 	 <div class="ui-search-panel" region="north" style="height: 70px;" title="搜索框" data-options="striped: true,collapsible:true,iconCls:'icon-search',border:false">  
	 	 <form id="searchForm">
	 	 	<p class="ui-fields">
	 	 	    <label class="ui-label">表名:</label><input name="realTablename" class="easyui-box ui-text">
	 	 	    <label class="ui-label">项目:</label>
	 	 	    <select name="projectId" class="easyui-combobox" style="width: 160px;height: 25px;">
	 	 	    	<option value="">请选择</option>
		 	 	    <c:forEach items="${projectList}" var="project">
		 	 	    	<option value="${project.projectId}" <c:if test="${project.projectId==projectId}">selected</c:if>>${project.projectChName}</option>
		 	 	    </c:forEach>
	 	 	    </select>
	        </p>
	        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	        <a href="#" id="btn-export" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	     </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <div region="center" border="false" >
     	<table id="data-list"></table>
     </div>
  </body>
</html>
