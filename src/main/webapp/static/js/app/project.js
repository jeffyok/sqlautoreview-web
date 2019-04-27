$package('upms.project');
upms.project = function(){
	var _box = null;
	var _this = {
		addAction:baseUrl+'/project/add',
		editForm:function(){
			return $("#editForm");
		},
		editWin:function(){
			return $("#edit-win");
		},
		add:function(){
			if(_this.editForm().form('validate')){
				_this.editForm().attr('action',_this.addAction);
				upms.saveForm(_this.editForm(),function(data){
					var param = $("#searchForm").serializeObject();
					$("#data-list").datagrid('reload',param);
					_this.editWin().dialog('close');
				});
			}
		},
		initForm:function(){
			//新增操作
			$("#addBtn").click(function(){
				$("#editForm").resetForm();
				$("#edit-win").dialog({
					buttons:[
						{
							text:'保  存',
							iconCls: 'icon-ok',
							handler:function(){
								_this.add();
							}
						},
						{
							text:'关  闭',
							iconCls: 'icon-cancel',
							handler:function(){
								_this.editWin().dialog('close');
							}
						}
				]});
				_this.editWin().dialog('open');
			});
		},
		config:{
  			dataGrid:{
	   			url:'dataList',
	   			loadMsg:"处理中，请稍候…",
			    toolbar:"#toolbar",
			    idField:"projectId",
			    sortName:"createTime",
			    sortOrder:"desc",
	   			columns:[[
						{field:'projectName',title:'项目名称',width:150,align:'left',sortable:true},
						{field:'projectChName',title:'项目中文名',width:120,align:'left',sortable:true},
						{field:'dbInfo',title:'数据库信息',width:220,align:'left',sortable:true,formatter:function(value, row, index){
							var divStr="<div stype='padding:5px'>";
							divStr+="<span style='font-weight: bold;'>数据库IP：</span>"+row.dbIp+"</br>" +
									"<span style='font-weight: bold;'>数据库端口：</span>"+row.dbPort+
									"</br><span style='font-weight: bold;'>数据库用户：</span>"+row.dbName
								  +"</br><span style='font-weight: bold;'>数据库密码：</span>"+row.dbPassword
							divStr+="</div>";
							return divStr;
						}},
						{field:'mapperRootPath',title:'mapper文件根路径',width:320,align:'left',sortable:true},
						{field:'projectDesc',title:'项目描述',width:150,align:'left',sortable:true},
						{field:'reviewTime',title:'上次REVIEW时间',width:120,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'reviewFlag',title:'review状态',width:80,align:'center',sortable:true,formatter:function(value, row, index){
							if(value=='1'){
								return '已Review';
							}else{
								return '未Review';
							}
						}},
						{field:'createTime',title:'创建时间',width:120,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'operate',title:'操作',width:320,align:'center',sortable:true,formatter:function(value, row, index){
							var updateOperate = "<a href='#' class='operate-link' onclick=editProject('"+row.projectId+"')>修改</a>";
							var deleteOperate="<a href='#' class='operate-link' onclick=deleteProject('"+row.projectId+"')>删除</a><br>";
							var reviewOperate = "<a href='#' class='operate-link' onclick=review('"+row.projectId+"','"+row.reviewFlag+"')>REVIEW</a>";
							var queryMapper = "<a href='#' class='operate-link' onclick=queryMapper('"+row.projectId+"')>查看mapper</a>";
							var queryView = "<a href='#' class='operate-link' onclick=queryView('"+row.projectId+"')>查看review过程</a>";
							var queryViewResult = "<a href='#' class='operate-link' onclick=queryViewResult('"+row.projectId+"')>查看review结果</a>";
							return reviewOperate+"&nbsp;&nbsp;"+updateOperate+"&nbsp;&nbsp;"+deleteOperate+"&nbsp;&nbsp;"+queryMapper+"&nbsp;&nbsp;"+"&nbsp;&nbsp;"+queryView+"&nbsp;&nbsp;"+queryViewResult;
						}}
				]]
			}
		},
		init:function(){
			_this.initForm();
			_box = new MyDataGrid(_this.config); 
			_box.init();
		}
	}
	_this._box = _box;
	return _this;
}();
$(function(){
	upms.project.init();
});

/**
 * 修改项目
 * @param projectId
 */
function editProject(projectId){
		$("#editForm").resetForm();
		upms.progress();
		var data ={};
		var idKey = 'projectId'; //主键名称
		data[idKey] = projectId;
		upms.getById(baseUrl+"/project/getById",data,function(result){
			upms.closeProgress();
			$("#editForm").form('load',result);
			$("#edit-win").dialog({
					buttons:[
						{
							text:'保  存',
							iconCls: 'icon-ok',
							handler:function(){
								var editForm = $("#editForm");
								if(editForm.form('validate')){
									editForm.attr('action',baseUrl+'/project/edit');
									upms.saveForm(editForm,function(data){
										var param = $("#searchForm").serializeObject();
										$("#data-list").datagrid('reload',param);
										$("#edit-win").dialog('close');
									});
								}
							}
						},
						{
							text:'关  闭',
							iconCls: 'icon-cancel',
							handler:function(){
								$("#edit-win").dialog('close');
							}
						}
			]});
			$("#edit-win").dialog('open');
		});
}

/**
 * 删除项目
 * @param projectId
 */
function deleteProject(projectId){
	$.messager.confirm('Confirm','是否删除选中记录?',function(r){  
	    if (r){
	    	var data ={};
			data['projectId'] = projectId;
	    	upms.deleteForm(baseUrl+'/project/delete',data,function(){
				var param = $("#searchForm").serializeObject();
				$("#data-list").datagrid('reload',param);
			});
	    }  
	});
}


/**
 * review
 * @param projectId
 * @param reviewFlag review 状态
 */
function review(projectId,reviewFlag){
	var msg = "是review选中项目?";
	if(reviewFlag=="1"){
		msg = "该项目已review，重新自动review会清空掉之前的review和review结果,是否重新review？";
	}
	$.messager.confirm('Confirm',msg,function(r){  
	    if (r){
	    	var params ={};
	    	params['projectId'] = projectId;
	    	upms.progress();
	    	upms.ajaxJson(baseUrl+'/project/review',params,function(data){
	    		upms.closeProgress();
				if(data.result=="success"){
					upms.alert("提示", "review成功", "info",function(){
						var param = $("#searchForm").serializeObject();
						$("#data-list").datagrid('reload',param);
					});
				}else{
					upms.alert("提示", data.msg, "error");
				}
			});
	    }  
	});
}

/**
 * 查看mapper
 * @param projectId
 */
function queryMapper(projectId){
	window.location.href=baseUrl+"/sqlmapperFile/list?projectId="+projectId;
	$('#navs li div',window.parent.document).removeClass("selected");
	$('[ref=20]',window.parent.document).parent().addClass("selected");
}

/**
 * 查看view
 * @param projectId
 */
function queryView(projectId){
	window.location.href=baseUrl+"/sqlReview/list?projectId="+projectId;
	$('#navs li div',window.parent.document).removeClass("selected");
	$('[ref=30]',window.parent.document).parent().addClass("selected");
}

/**
 * 查看view
 * @param projectId
 */
function queryViewResult(projectId){
	window.location.href=baseUrl+"/reviewResult/list?projectId="+projectId;
	$('#navs li div',window.parent.document).removeClass("selected");
	$('[ref=40]',window.parent.document).parent().addClass("selected");
}