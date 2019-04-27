$package('upms.reviewResult');
upms.reviewResult = function(){
	var _box = null;
	var _this = {
		initForm:function(){
			//导出操作
			$("#btn-export").click(function(){
				$("#searchForm").attr("action",baseUrl+"/reviewResult/exportReviewResult");
				$("#searchForm").submit();
			});
		},
		config:{
  			dataGrid:{
	   			url:'dataList',
	   			loadMsg:"处理中，请稍候…",
			    idField:"id",
			    sortName:"createTime",
			    sortOrder:"desc",
	   			columns:[[
	   			        {field:'id',title:'ID',width:80,align:'center',sortable:true},
	   			        {field:'projectName',title:'项目名称',width:140,align:'left',formatter:function(value, row, index){
							return row.projectName+"<br>("+row.projectChName+")";
						}},
						{field:'realTablename',title:'真实表名',width:160,align:'left',sortable:true},
						{field:'existIndexes',title:'已存在索引',width:300,align:'left',sortable:true,formatter:function(value, row, index){
							return "<blockquote>"+(value==undefined?"":value)+"</blockquote>";
						}},
						{field:'newIndexes',title:'新索引',width:340,align:'left',sortable:true,formatter:function(value, row, index){
							return "<blockquote>"+(value==undefined?"":value)+"</blockquote>";
						}},
						{field:'mergeResult',title:'合并结果',width:340,align:'left',sortable:true,formatter:function(value, row, index){
							return "<blockquote>"+(value==undefined?"":value)+"</blockquote>";
						}},
						{field:'createTime',title:'创建时间',width:100,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'updateTime',title:'修改时间',width:100,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'operate',title:'操作',width:100,align:'center',sortable:true,formatter:function(value, row, index){
							var deleteOperate="<a href='#' class='operate-link' onclick=deleteReviewResult('"+row.id+"')>删除</a>";
							return deleteOperate;
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
	upms.reviewResult.init();
});


/**
 * 删除项目
 * @param projectId
 */
function deleteReviewResult(id){
	$.messager.confirm('Confirm','是否删除选中记录?',function(r){  
	    if (r){
	    	var data ={};
			data['id'] = id;
	    	upms.deleteForm(baseUrl+'/reviewResult/delete',data,function(){
				var param = $("#searchForm").serializeObject();
				$("#data-list").datagrid('reload',param);
			});
	    }  
	});
}