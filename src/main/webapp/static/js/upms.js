$package('upms');
var upms={
	/*Json 工具类*/
	isJson:function(str){
		var obj = null; 
		try{
			obj = upms.paserJson(str);
		}catch(e){
			return false;
		}
		var result = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
		return result;
	},
	paserJson:function(str){
		return eval("("+str+")");
	},
	/*弹出框*/
	alert:function(title, msg, icon, callback){
		$.messager.alert(title,msg,icon,callback);
	},
	/*弹出框*/
	confirm:function(title, msg,callback){
		$.messager.confirm(title,msg,callback);
	},
	/*右下消息*/
	show:function(options){
		$.messager.show(options);
	},
	progress:function(title,msg){
		 var win = $.messager.progress({  
            title: title ||'Please waiting',  
            msg: msg ||'Loading data...'  
         }); 
	},
	closeProgress:function(){
		$.messager.progress('close');
	},
	/*重新登录页面*/
	toLogin:function(){
		window.top.location= baseUrl+"/login";
	},
	checkLogin:function(data){//检查是否登录超时
		if(data.logoutFlag){
			upms.closeProgress();
			upms.alert('提示',"登录超时,点击确定重新登录.",'error',upms.toLogin);
			return false;
		}
		return true;
	},
	ajaxSubmit:function(form,option){
		form.ajaxSubmit(option);
	},
	ajaxJson: function(url,dataParam,callback){
		$.ajax(url,{
			type:'post',
			 	dataType:'json',
			 	data:dataParam,
			 	success:function(data){
			 		//坚持登录
			 		if(!upms.checkLogin(data)){
			 			return false;
			 		}		 	
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			upms.closeProgress();
			 			var data = $.parseJSON(response.responseText);
				 		//检查登录
				 		if(!upms.checkLogin(data)){
				 			return false;
				 		}else{
					 		upms.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			upms.alert('提示',"请求出现异常,请联系管理员.",'error');
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
		});
	},
	submitForm:function(form,callback,dataType){
			var option =
			{
			 	type:'post',
			 	dataType: dataType||'json',
			 	success:function(data){
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			upms.closeProgress();
			 			var data = $.parseJSON(response.responseText);
				 		//检查登录
				 		if(!upms.checkLogin(data)){
				 			return false;
				 		}else{
					 		upms.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			upms.alert('提示',"请求出现异常,请联系管理员.",'error');
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
			 }
			 upms.ajaxSubmit(form,option);
	},
	saveForm:function(form,callback){
		if(form.form('validate')){
			upms.progress('请稍后','Save ing...');
			//ajax提交form
			upms.submitForm(form,function(data){
				upms.closeProgress();
			 	if(data.result=="success"){
			 		upms.show({
						title:'提示',
						msg:data.msg,
						showType:'slide'
			        });
			 		if(callback){
			 			callback(data);
			 		}
		        }else{
		       	   upms.alert('提示',data.msg,'error');  
		        }
			});
		 }
	},
	/**
	 * 
	 * @param {} url
	 * @param {} option {id:''} 
	 */
	getById:function(url,dataParam,callback){
		upms.progress();
		upms.ajaxJson(url,dataParam,function(data){
			upms.closeProgress();
			if(callback){
		       	callback(data);
		    }
		});
	},
	deleteForm:function(url,dataParam,callback){
		upms.progress();
		upms.ajaxJson(url,dataParam,function(data){
				upms.closeProgress();
				if(data.result=="success"){
					upms.show({
						title:'提示',
						msg:data.msg,
						showType:'slide'
			        });
					if(callback){
				       	callback(data);
				    }
				}else{
					upms.alert('提示',data.msg,'error');  
				}
		});
	},
	checkSelect : function(rows){//检查grid是否有勾选的行, 有返回 true,没有返回true
		if(rows && rows.length > 0){
			return true;
		}
		upms.alert('警告','未选中记录.','warning');  
		return false;
		
	},
	checkSelectOne : function(rows){//检查grid是否只勾选了一行,是返回 true,否返回true
		if(!upms.checkSelect(rows)){
			return false;
		}
		if(rows.length == 1){
			return true;
		}
		upms.alert('警告','只能选择一行记录.','warning');  
		return false;
	},
	timeFormatter: function (value, rec, index) {
	   	if(value!=null){
	    		var date = new Date(value);  
            return date.getMinutes()+":"+date.getSeconds();
        }else{
	        	return "";
	    }
	},
    dateTimeFormatter: function (value, rec, index) {
    	if(value!=null){
     		var date = new Date(value);
            return date.getFullYear()+'-'+(date.getMonth()+ 1)+'-'+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        }else{
        	return "";
        }
    },
    dateFormatter: function (value, rec, index) {
    	if(value!=null){
     		var date = new Date(value);  
            return date.getFullYear()+'-'+(date.getMonth()+ 1)+'-'+date.getDate();
        }else{
        	return "";
        }
    }
}

/* 自定义密码验证*/
$.extend($.fn.validatebox.defaults.rules, {  
    equals: {  
        validator: function(value,param){  
            return value == $(param[0]).val();  
        },  
        message: 'Field do not match.'  
    }  
});  

/*表单转成json数据*/
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

/* easyui datagrid 添加和删除按钮方法*/
$.extend($.fn.datagrid.methods, {  
    addToolbarItem: function(jq, items){  
        return jq.each(function(){  
            var toolbar = $(this).parent().prev("div.datagrid-toolbar");
            for(var i = 0;i<items.length;i++){
                var item = items[i];
                if(item === "-"){
                    toolbar.append('<div class="datagrid-btn-separator"></div>');
                }else{
                    var btn=$("<a href=\"javascript:void(0)\"></a>");
                    btn[0].onclick=eval(item.handler||function(){});
                    btn.css("float","left").appendTo(toolbar).linkbutton($.extend({},item,{plain:true}));
                }
            }
            toolbar = null;
        });  
    },
    removeToolbarItem: function(jq, param){  
        return jq.each(function(){  
            var btns = $(this).parent().prev("div.datagrid-toolbar").children("a");
            var cbtn = null;
            if(typeof param == "number"){
                cbtn = btns.eq(param);
            }else if(typeof param == "string"){
                var text = null;
                btns.each(function(){
                    text = $(this).data().linkbutton.options.text;
                    if(text == param){
                        cbtn = $(this);
                        text = null;
                        return;
                    }
                });
            } 
            if(cbtn){
                var prev = cbtn.prev()[0];
                var next = cbtn.next()[0];
                if(prev && next && prev.nodeName == "DIV" && prev.nodeName == next.nodeName){
                    $(prev).remove();
                }else if(next && next.nodeName == "DIV"){
                    $(next).remove();
                }else if(prev && prev.nodeName == "DIV"){
                    $(prev).remove();
                }
                cbtn.remove();    
                cbtn= null;
            }                        
        });  
    }                 
});