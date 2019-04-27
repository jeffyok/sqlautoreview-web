$package('upms.sqlmapperFile');
upms.sqlReview = function(){
	var _box = null;
	var _this = {
		initForm:function(){
			//导出操作
			$("#btn-export").click(function(){
				$("#searchForm").attr("action",baseUrl+"/sqlReview/exportReview");
				$("#searchForm").submit();
			});
		},
		config:{
  			dataGrid:{
	   			url:baseUrl+'/sqlReview/dataList',
	   			loadMsg:"处理中，请稍候…",
			    idField:"id",
			    pageSize:20,
			    sortName:"autoReviewTime",
			    sortOrder:"desc",
	   			columns:[[
	   			        {field:'status',title:'自动检查状态',width:80,align:'center',sortable:true,formatter:function(value, row, index){
							if(value=="0"){
								return '未检查';
							}else if(value=="1"){
								return '<span style="color:green">已检查</span>';
							}else if(value=="2"){
								return '<span style="color:red">暂不支持</span>';
							}
						}},
						{field:'projectName',title:'项目名称',width:200,align:'left',formatter:function(value, row, index){
							return row.projectName+"<br>("+row.projectChName+")";
						}},
						{field:'realSql',title:'检查sql信息',width:450,align:'left',formatter:function(value, row, index){
							var divStr="<div>";
							divStr+="<span style='font-weight: bold;'>文件名：</span>"+row.fileName+"</br><span style='font-weight: bold;'>方法名：</span>"
								  +row.javaClassId+"</br><span style='font-weight: bold;'>表名：</span>"+(row.tableName==undefined?"":row.tableName)
								  +"</br><span style='font-weight: bold;'>方法备注：</span>"+(row.sqlComment==undefined?"":row.sqlComment)+"<blockquote>"+row.realSql+"</blockquote>"
							divStr+="</div>";
							return divStr;
						}},
						{field:'autoReviewErr',title:'自动检查信息',width:260,align:'left',formatter:function(value, row, index){
							var divStr="<div>";
							divStr+="<span style='font-weight: bold;'>自动检查错误信息：</span><blockquote>"+(row.autoReviewErr==undefined?"":row.autoReviewErr)+"</blockquote><span style='font-weight: bold;'>自动检查提示：</span><blockquote>"+(row.autoReviewTip==undefined?"":row.autoReviewTip)+"</blockquote><span style='font-weight: bold;'>自动Review索引：</span><blockquote>"+(row.sqlAutoIndex==undefined?"":row.sqlAutoIndex)+"</blockquote>";
							divStr+="</div>";
							return divStr;
						}},
						{field:'autoReviewTime',title:'自动检查时间',width:100,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'dbaReviewStatus',title:'DBA Review状态',width:100,align:'center',sortable:true,formatter:function(value, row, index){
							if(value=="0"){
								return '未审核';
							}else if(value=="1"){
								return '<span style="color:green">通过</span>';
							}else if(value=="2"){
								return '<span style="color:red">不通过</span>';
							}
						}},
						{field:'dbaReviewTime',title:'dba检查时间',width:100,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'sqlDbaIndex',title:'dba检查信息',width:140,align:'left',formatter:function(value, row, index){
							var divStr="<div>";
							divStr+="<span style='font-weight: bold;'>dba索引：</span><blockquote>"+(row.sqlDbaIndex==undefined?"":row.sqlDbaIndex)+"</blockquote><span style='font-weight: bold;'>dba建议：</span><blockquote>"+(row.dbaAdvice==undefined?"":row.dbaAdvice)+"</blockquote>";
							divStr+="</div>";
							return divStr;
						}},
						{field:'updateTime',title:'修改时间',width:100,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'operate',title:'操作',width:140,align:'center',sortable:true,formatter:function(value, row, index){
							var deleteOperate="<a href='#' class='operate-link' onclick=deleteSqlReview('"+row.id+"')>删除</a>";
							var dbaReviewOperate="<a href='#' class='operate-link' onclick=dbaReviewPage('"+row.id+"')>DBA Review</a>";
							return deleteOperate+"&nbsp;&nbsp;"+dbaReviewOperate;
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
	upms.sqlReview.init();
});

/**
 * 删除项目
 * @param projectId
 */
function deleteSqlReview(id){
	$.messager.confirm('Confirm','是否删除选中记录?',function(r){  
	    if (r){
	    	var data ={};
			data['id'] = id;
	    	upms.deleteForm(baseUrl+'/sqlReview/delete',data,function(){
				var param = $("#searchForm").serializeObject();
				$("#data-list").datagrid('reload',param);
			});
	    }  
	});
}

/**
 * dba review 页面
 * @param id
 */
function dbaReviewPage(id){
	$("#reviewForm").resetForm();
	upms.progress();
	var data ={};
	var idKey = 'id'; //主键名称
	data[idKey] = id;
	upms.getById(baseUrl+"/sqlReview/getById",data,function(result){
		upms.closeProgress();
		$("#reviewForm").form('load',result);
	    var val=$('input:radio[name="dbaReviewStatus"]:checked').val();
		if(val == null || val == undefined){
			 $("input[name='dbaReviewStatus'][value=1]").click();
		}
		$("#review-win").dialog({
				buttons:[
					{
						text:'保  存',
						iconCls: 'icon-ok',
						handler:function(){
							var reviewForm = $("#reviewForm");
							if(reviewForm.form('validate')){
								reviewForm.attr('action',baseUrl+'/sqlReview/dbaReview');
								upms.saveForm(reviewForm,function(data){
									var param = $("#searchForm").serializeObject();
									$("#data-list").datagrid('reload',param);
									$("#review-win").dialog('close');
								});
							}
						}
					},
					{
						text:'关  闭',
						iconCls: 'icon-cancel',
						handler:function(){
							$("#review-win").dialog('close');
						}
					}
		]});
		$("#review-win").dialog('open');
	});
}