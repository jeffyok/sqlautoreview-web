<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp" %>
<html>
<head>
    <title>404-页面未找到</title>
    <script type="text/javascript">
    	function goHome(){
    		if(window.top != window.self){
    			window.top.location = "${ctx}";
    		}
    	}
    </script>
</head>
<body>
    <div class="std-error-panel">
        <div class="head">页面未找到</div>
        <div class="body">您访问的页面未找到。</div>
        <div class="foot">
            <a href="#" onclick="goHome()">返回首页</a>
        </div>
    </div>
</body>
</html>
