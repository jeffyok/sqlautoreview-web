<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/taglibs.jsp" %>
<html>
<head>
    <title>SQL自动REVIEW管理系统</title>
    <%@include file="/commons/resource.jsp" %>
    <style type="text/css">
		* {
			font-size: 12px;
			font-family: Tahoma, Verdana, 微软雅黑, 新宋体;
		}
		
		ul.my_menu {
			list-style: none;
			position: relative;
			border: 0px;
			padding: 0px 16px;
			font-size: 13px;
		}
		
		ul.my_menu li {
			line-height: 25px;
		}
		
		ul.my_menu li a {
			text-decoration: none;
			color: #616161;
			display: block;
			line-height: 25px;
			height: 25px;
		}
		
		ul.my_menu li a .nav {
			margin-left: 3px;
		}
		
		ul.my_menu li a .icon {
			vertical-align: middle;
		}
		
		ul.my_menu li a:hover {
			color: #2a6496;
			background-color: #ccc;
		}
		
		ul.my_menu .icon {
			width: 16px;
			height: 16px;
			display: inline-block;
		}
		
		.easyui-accordion ul li div.hover {
			border: 1px dashed #99BBE8;
			background: #E0ECFF;
			cursor: pointer;
		}
		
		.easyui-accordion ul li div.hover a {
			color: #416AA3;
		}
		
		.easyui-accordion ul li div.selected {
			border: 1px solid #99BBE8;
			background: #E0ECFF;
			cursor: default;
		}
		
		.easyui-accordion ul li div.selected a {
			color: #416AA3;
			font-weight: bold;
		}
		.ui-header {
			background: url("${ctx}/static/images/layout-browser-hd-bg.gif") repeat-x center;
			font-family: 'lucida grande',tahoma,arial,sans-serif;
			color: white;
			margin: 0px;
			padding: 0px;
		}
		.ui-header h1 {
			font-size: 18px;
			color: white;
			font-weight: normal;
			padding: 3px 1px;
			float: left;
			font-weight: bold;
			margin-left: 1px;
			text-indent: 5px;
		}
		.ui-login {
			float: right;
			padding: 5px;
		}
		.ui-login a {
			color: #D7E8F7;
		}
</style>
<script type="text/javascript" src="${ctx}/static/js/app/index.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:34px;margin-bottom: 5px;">
		<%@include file="/commons/frame/top.jsp" %>
	</div>
	<div data-options="region:'south'" style="height:25px;text-align: center;padding-top: 3px">
		Copyright©2018
	</div>

		<%@include file="/commons/frame/left.jsp" %>

	<div data-options="region:'center',iconCls:'icon-ok'">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
				
			<h1>欢迎使用SQL自动REVIEW平台</h1>

			</div>
		</div>
	</div>
</body>
</html>