package com.jeffy.sqlautoreview.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeffy.sqlautoreview.base.pagination.EasyUiPager;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.core.BaseController;
import com.jeffy.sqlautoreview.core.ErrorCodeEnums;
import com.jeffy.sqlautoreview.core.JsonResponse;
import com.jeffy.sqlautoreview.core.JsonTableData;
import com.jeffy.sqlautoreview.enums.YesNoEnum;
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.service.ProjectService;
import com.jeffy.sqlautoreview.service.SqlmapperFileService;
import com.jeffy.sqlautoreview.service.SqlreviewService;

/**  
 * 项目控制器    
 * @ClassName: ProjectController    
 * @author 陈剑飞    
 * @date 2016年10月10日 上午11:24:07    
 * @version  v 1.0    
 */
@Controller
@RequestMapping("project/")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SqlmapperFileService sqlmapperFileService;
	@Autowired
	private SqlreviewService sqlreviewService;
	private final String PROJECT_LIST_PAGE="project";
	
	/**
	 * 项目列表
	 * @author 陈剑飞    
	 * @Title: list    
	 * @Return: String 返回值
	 */
	@RequestMapping("list")
	public String list(){
		return PROJECT_LIST_PAGE;
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
	public JsonTableData<ProjectModel>  dataList(ProjectModel model,EasyUiPager pager) throws Exception{
		JsonTableData<ProjectModel> data = new JsonTableData<ProjectModel>();
		if(StringUtils.isNotEmpty(model.getProjectName())){
			model.setProjectName("%"+model.getProjectName()+"%");
		}
		List<ProjectModel> result = projectService.queryList(model, pager);
		data.setTotal(projectService.queryCount(model));
		data.setRows(result==null?new ArrayList<ProjectModel>():result);
		return data;
	}
	
	/**
	 * 添加项目信息
	 * @param model 模型对象
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@ResponseBody
	public JsonResponse add(ProjectModel model,HttpServletRequest request) throws Exception{
		JsonResponse response = new JsonResponse(JsonResponse.SUCCESS,"保存成功");
		if(projectService.isExistsProject(model)){
			response.setResult(JsonResponse.FAIL);
			response.setMsg("项目名已存在");
			return response;
		}
		model.setReviewFlag(YesNoEnum.NO.getType());
		projectService.add(model);
		return response;
	}
	
	/**
	 * 修改数据
	 * @param model 模型对象
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public JsonResponse edit(ProjectModel model,HttpServletRequest request) throws Exception{
		JsonResponse response = new JsonResponse(JsonResponse.SUCCESS,"修改成功");
		if(projectService.isExistsProject(model)){
			response.setResult(JsonResponse.FAIL);
			response.setMsg("项目名已存在");
			return response;
		}
		projectService.updateBySelective(model);
		return response;
	}
	
	/**
	 * 根据ID查找项目信息
	 * @param projectId 项目ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getById")	
	@ResponseBody
	public ProjectModel getById(Integer projectId) throws Exception{
		return projectService.getById(projectId);
	}
	
	/**
	 * 删除数据
	 * @param userIds 主键列表
	 * @param response 
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResponse delete(Integer projectId) throws Exception{
		JsonResponse response = new JsonResponse(JsonResponse.SUCCESS,"删除成功");
		projectService.deleteById(projectId);
		return response;
	}
	
	/**
	 * sql review  
	 * @author 陈剑飞    
	 * @Title: sqlReview    
	 * @param projectId 项目ID
	 * @throws Exception 
	 * @Return: JsonResponse 返回值
	 */
	@RequestMapping("/review")
	@ResponseBody
	public JsonResponse review(Integer projectId)throws Exception{
		JsonResponse response = new JsonResponse(JsonResponse.SUCCESS,"review成功");
		try {
			sqlreviewService.review(projectId);
		} catch (AppException e) {
			if(ErrorCodeEnums.FIEL_NOTEXIST.getCode().equals(e.getCode())){
				response.setMsg("review失败，mapper文件根路径不存在");
				response.setResult(JsonResponse.FAIL);
			} else if(ErrorCodeEnums.REVIEW_DB_FAIL.getCode().equals(e.getCode())){
				response.setMsg("review失败，"+e.getMsg());
				response.setResult(JsonResponse.FAIL);
			}else{
				throw new AppException(e);
			}
		}
		return response;
	}
}
