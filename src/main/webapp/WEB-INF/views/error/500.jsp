<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp" %>
<html>
<head>
    <title>500-系统异常</title>
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
        <div class="head">异常信息</div>
        <div class="body">服务器暂时繁忙，请稍候重试或与管理员联系。</div>
        <div class="foot">
            <a href="#" onclick="goHome()">返回首页</a>
        </div>
    </div>
</body>
</html>
