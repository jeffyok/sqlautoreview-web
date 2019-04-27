package com.jeffy.sqlautoreview.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeffy.sqlautoreview.base.pagination.EasyUiPager;
import com.jeffy.sqlautoreview.core.BaseController;
import com.jeffy.sqlautoreview.core.JsonResponse;
import com.jeffy.sqlautoreview.core.JsonTableData;
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.model.SqlmapperFileModel;
import com.jeffy.sqlautoreview.service.ProjectService;
import com.jeffy.sqlautoreview.service.SqlmapperFileService;
import com.jeffy.sqlautoreview.vo.SqlmapperFileVo;

/**  
 * mapper文件控制器    
 * @ClassName: MapperFileController    
 * @author 陈剑飞    
 * @date 2016年10月10日 上午11:23:50    
 * @version  v 1.0    
 */
@Controller
@RequestMapping("sqlmapperFile/")
public class SqlmapperFileController extends BaseController {
	@Autowired
	private SqlmapperFileService sqlapperFileService;
	@Autowired
	private ProjectService projectService;
	private final String SQLMAPPER_FILE_LIST_PAGE="sqlmapperFile";
	
	/**
	 * sqlmapper 文件列表
	 * @author 陈剑飞    
	 * @Title: list    
	 * @Return: String 返回值
	 */
	@RequestMapping("list")
	public String list(Integer projectId,HttpServletRequest request){
		//设置项目列表
		List<ProjectModel> projectList = projectService.queryList(new ProjectModel(), null);
		request.setAttribute("projectList", projectList);
		request.setAttribute("projectId", projectId);
		return SQLMAPPER_FILE_LIST_PAGE;
	}
	
	/**
	 * 获取数据列表
	 * @param model 模型对象
	 * @param pager 分页对象
	 * @return JsonTableData
	 * @throws Exception
	 */
	@RequestMapping("/dataList") 
	@ResponseBody
	public JsonTableData<SqlmapperFileVo>  dataList(SqlmapperFileModel model,EasyUiPager pager) throws Exception{
		JsonTableData<SqlmapperFileVo> data = new JsonTableData<SqlmapperFileVo>();
		List<SqlmapperFileVo> result = sqlapperFileService.querySqlmapperFileVoList(model, pager);
		data.setTotal(sqlapperFileService.queryCount(model));
		data.setRows(result==null?new ArrayList<SqlmapperFileVo>():result);
		return data;
	}
	

	/**
	 * 删除数据
	 * @param userIds 主键列表
	 * @param response 
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResponse delete(Integer mapperFileId) throws Exception{
		JsonResponse response = new JsonResponse(JsonResponse.SUCCESS,"删除成功");
		sqlapperFileService.deleteById(mapperFileId);
		return response;
	}
}
