$package('upms.sqlmapperFile');
upms.sqlmapperFile = function(){
	var _box = null;
	var _this = {
		initForm:function(){
			
		},
		config:{
  			dataGrid:{
	   			url:baseUrl+'/sqlmapperFile/dataList',
	   			loadMsg:"处理中，请稍候…",
			    idField:"mapperFileId",
			    sortName:"scanTime",
			    sortOrder:"desc",
			    pageSize:20,
	   			columns:[[
	   			        {field:'mapperFileId',title:'文件ID',width:100,align:'center',sortable:true},
						{field:'projectName',title:'项目名称',width:150,align:'left'},
						{field:'projectChName',title:'项目中文名',width:120,align:'left'},
						{field:'mapperFilePath',title:'文件路径',width:500,align:'left',sortable:true},
						{field:'fileName',title:'文件名',width:200,align:'left',sortable:true},
						{field:'scanTime',title:'扫描时间',width:140,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'updateTime',title:'修改时间',width:140,align:'center',sortable:true,formatter:upms.dateTimeFormatter},
						{field:'operate',title:'操作',width:120,align:'center',sortable:true,formatter:function(value, row, index){
							var deleteOperate="<a href='#' class='operate-link' onclick=deleteSqlmapperFile('"+row.mapperFileId+"')>删除</a>";
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
	upms.sqlmapperFile.init();
});

/**
 * 删除项目
 * @param projectId
 */
function deleteSqlmapperFile(mapperFileId){
	$.messager.confirm('Confirm','是否删除选中记录?',function(r){  
	    if (r){
	    	var data ={};
			data['mapperFileId'] = mapperFileId;
	    	upms.deleteForm(baseUrl+'/sqlmapperFile/delete',data,function(){
				var param = $("#searchForm").serializeObject();
				$("#data-list").datagrid('reload',param);
			});
	    }  
	});
}