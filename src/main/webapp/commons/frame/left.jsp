<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
	var _menus = [ {
			"menuId" : "10",
			"icon" : "icon-ok",
			"menuName" : "项目管理",
			"linkUrl" : "project/list"
		    }, 
		    {
			"menuId" : "20",
			"icon" : "icon-sys",
			"menuName" : "SQLmaper文件管理",
			"linkUrl" : "sqlmapperFile/list"
		    },
		    {
				"menuId" : "30",
				"icon" : "icon-sys",
				"menuName" : "SQL REVIEW过程",
				"linkUrl" : "sqlReview/list"
		    },
		    {
				"menuId" : "40",
				"icon" : "icon-sys",
				"menuName" : "REVIEW 结果",
				"linkUrl" : "reviewResult/list"
		    } 
    ];
    $(function () {
    	//var menus=${menus};
    	initMenu(_menus);
    	// 导航菜单绑定初始化
    	$("#navs").accordion( {
    		animate : false
    	});
    	InitLeftMenu();   	
    });
    function addNav(data) {
    	$.each(data, function(i, sm) {
    		var menulist = "";
    		menulist += '<ul class="my_menu">';
    		$.each(sm.menus, function(j, o) {
    			menulist += '<li><div><a ref="' + o.menuid + '" href="#" rel="'
    					+ o.url + '" ><span class="icon ' + o.icon
    					+ '" >&nbsp;</span><span class="nav">' + o.menuname
    					+ '</span></a></div></li> ';
    		});
    		menulist += '</ul>';

    		$('#navs').accordion('add', {
    			title : sm.menuname,
    			content : menulist,
    			iconCls : 'icon ' + sm.icon
    		});
    	});
    	

    }

    /*
     * 初始化菜单
     */
    function initMenu(menus) {
    	var menulist = "";
    	for(var i=0;i<menus.length;i++){
    		var menu = menus[i];
    		menulist += '<ul class="my_menu">';
    		if(menu.menus!=null){
    			$.each(menu.menus, function(j, o) {
        			menulist += '<li><div><a ref="' + o.menuId + '" href="#" rel="'
        					+ o.linkUrl+ '" class="menu-item" ><span class="nav">' + o.menuName
        					+ '</span></a></div></li> ';
        		});
    		}else{
    			menulist += '<li><div><a ref="' + menu.menuId + '" href="#" rel="'
				+ menu.linkUrl+ '" class="menu-item" ><span class="nav">' + menu.menuName
				+ '</span></a></div></li> ';
    		}
    		menulist += '</ul>';
    	}
    	$('#navs').accordion('add', {
			title : "导航菜单",
			content : menulist
		});
    	var pp = $('#navs').accordion('panels');
    	var t = pp[0].panel('options').title;
    	$('#navs').accordion('select', t);
   }
    

 // 初始化左侧
    function InitLeftMenu() {
    	$('#navs li a').bind('click', function() {
    		var tabTitle = $(this).children('.nav').text();
    		var url = $(this).attr("rel");
    		var menuid = $(this).attr("ref");
    		//var icon = getIcon(menuid, icon);

    		addTab(tabTitle, url);
    		$('#navs li div').removeClass("selected");
    		$(this).parent().addClass("selected");
    	});
    }


    // 获取左侧导航的图标
    function getIcon(menuid) {
    	var icon = 'icon ';
    	$.each(_menus, function(i, n) {
    		$.each(n, function(j, o) {
    			$.each(o.menus, function(k, m){
    				if (m.menuid == menuid) {
    					icon += m.icon;
    					return false;
    				}
    			});
    		});
    	});
    	return icon;
    }
	 
    function addTab(subtitle, url, icon) {
    	if (!$('#tabs').tabs('exists', subtitle)) {
    		$('#tabs').tabs('add', {
    			title : subtitle,
    			content : createFrame(url),
    			closable : true
    		});
    	} else {
    		//$('#tabs').tabs('select', subtitle);
    		//$('#mm-tabupdate').click();
    		var tab = $('#tabs').tabs('getTab',subtitle);
			var index = $('#tabs').tabs('getTabIndex',tab);
			$('#tabs').tabs('select',index);
			if(tab && tab.find('iframe').length > 0){
				 var _refresh_ifram = tab.find('iframe')[0];  
			     _refresh_ifram.contentWindow.location.href=url; 
		    } 
    	}
    }

    function createFrame(url) {
    	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    	return s;
    }
</script>
<div id='navs' class="easyui-accordion" data-options="region:'west',split:true" border="false" style="width: 200px;">
</div>