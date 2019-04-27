package com.jeffy.sqlautoreview.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeffy.sqlautoreview.base.pagination.EasyUiPager;
import com.jeffy.sqlautoreview.core.BaseController;
import com.jeffy.sqlautoreview.core.BeanToMapUtil;
import com.jeffy.sqlautoreview.core.JsonResponse;
import com.jeffy.sqlautoreview.core.JsonTableData;
import com.jeffy.sqlautoreview.enums.DbaReviewStatusEnum;
import com.jeffy.sqlautoreview.enums.ReviewStatusEnum;
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.model.SqlreviewModel;
import com.jeffy.sqlautoreview.service.ProjectService;
import com.jeffy.sqlautoreview.service.SqlreviewService;
import com.jeffy.sqlautoreview.utils.ExcelUtils;
import com.jeffy.sqlautoreview.utils.MyBeanUtils;
import com.jeffy.sqlautoreview.vo.SqlreviewVo;

/**  
 * sql review 控制器   
 * @ClassName: SqlReviewController    
 * @author 陈剑飞    
 * @date 2016年10月10日 上午11:27:29    
 * @version  v 1.0    
 */
@Controller
@RequestMapping("sqlReview/")
public class SqlreviewController extends BaseController {
	@Autowired
	private SqlreviewService sqlreviewService;
	@Autowired
	private ProjectService projectService;
	private final String SQLREVIEW_LIST_PAGE="sqlReview";
	
	/**
	 * sqlReview列表
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
		return SQLREVIEW_LIST_PAGE;
	}
	
	/**
	 * 获取数据列表
	 * @param model 模型对象
	 * @param pager 分页对象
	 * @return JsonTableData
	 * @throws Exception
	 */
	@RequestMapping("dataList") 
	@ResponseBody
	public JsonTableData<SqlreviewVo>  dataList(SqlreviewVo vo,EasyUiPager pager) throws Exception{
		JsonTableData<SqlreviewVo> data = new JsonTableData<SqlreviewVo>();
		List<SqlreviewVo> result = sqlreviewService.querySqlreviewVoList(vo, pager);
		data.setTotal(sqlreviewService.querySqlreviewVoCount(vo));
		data.setRows(result==null?new ArrayList<SqlreviewVo>():result);
		return data;
	}
	

	/**
	 * 删除数据
	 * @param userIds 主键列表
	 * @param response 
	 * @throws Exception
	 */
	@RequestMapping("delete")
	@ResponseBody
	public JsonResponse delete(Integer id) throws Exception{
		JsonResponse response = new JsonResponse(JsonResponse.SUCCESS,"删除成功");
		sqlreviewService.deleteById(id);
		return response;
	}
	
	/**
	 * 根据ID查找项目信息
	 * @param id 项目ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getById")	
	@ResponseBody
	public SqlreviewVo getById(Integer id) throws Exception{
		SqlreviewModel sqlreviewModel = sqlreviewService.getById(id);
		SqlreviewVo vo = new SqlreviewVo();
		MyBeanUtils.copyProperties(vo, sqlreviewModel);
		ProjectModel projectModel = projectService.getById(sqlreviewModel.getProjectId());
		vo.setProjectName(projectModel.getProjectName());
		vo.setProjectChName(projectModel.getProjectChName());
		return vo;
	}
	
	/**
	 * dba review 
	 * @author 陈剑飞    
	 * @Title: dbaReview    
	 * @param sqlreviewModel
	 * @throws Exception 
	 * @Return: JsonResponse 返回值
	 */
	@RequestMapping("dbaReview")
	@ResponseBody
	public JsonResponse dbaReview(SqlreviewModel sqlreviewModel) throws Exception{
		JsonResponse response = new JsonResponse(JsonResponse.SUCCESS,"dba review成功");
		sqlreviewModel.setDbaReviewTime(new Date());
		sqlreviewService.updateBySelective(sqlreviewModel);
		return response;
	}
	
	/**
	 * 导出review
	 * @author 陈剑飞    
	 * @Title: exportReview 
	 * @param vo SqlreviewVo
	 * @throws Exception 
	 * @Return: void 返回值
	 */
	@RequestMapping("exportReview")
	public void exportReview(SqlreviewVo vo,HttpServletResponse response)throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ExcelUtils excelUtils = new ExcelUtils();
		List<SqlreviewVo> sqlreviewVoList = sqlreviewService.querySqlreviewVoList(vo, null);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		if(sqlreviewVoList!=null){
			Map<String,Object> map = null;
			for(SqlreviewVo sqlreviewVo:sqlreviewVoList){
				map =BeanToMapUtil.convertBean(sqlreviewVo);
				map.put("status", ReviewStatusEnum.getReviewStatus(String.valueOf(sqlreviewVo.getStatus())).getDesc());
				map.put("dbaReviewStatus",StringUtils.isNotEmpty(sqlreviewVo.getDbaReviewStatus())?DbaReviewStatusEnum.getDbaReviewStatus(sqlreviewVo.getDbaReviewStatus()).getDesc():"");
				map.put("dbaReviewTime", sqlreviewVo.getDbaReviewTime()!=null?format.format(sqlreviewVo.getDbaReviewTime()):"");
				map.put("autoReviewTime", sqlreviewVo.getAutoReviewTime()!=null?format.format(sqlreviewVo.getAutoReviewTime()):"");
				result.add(map);
			}
		}
		Map<String,String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("id", "REVIEW ID");
		headerMap.put("projectName", "项目名称");
		headerMap.put("projectChName", "项目中文名");
		headerMap.put("fileName", "文件名");
		headerMap.put("javaClassId", "mapper方法名");
		headerMap.put("sqlComment", "方法备注");
		headerMap.put("realSql", "真实sql");
		headerMap.put("tableName", "表名");
		headerMap.put("autoReviewErr", "自动检查错误信息");
		headerMap.put("autoReviewTip", "自动检查提示");
		headerMap.put("sqlAutoIndex", "自动Review索引");
		headerMap.put("dbaReviewStatus", "DBA Review状态");
		headerMap.put("dbaReviewTime", "dba检查时间");
		headerMap.put("sqlDbaIndex", "dba索引");
		headerMap.put("dbaAdvice", "dba建议");
		headerMap.put("autoReviewTime", "自动检查时间");
		headerMap.put("updateTime", "修改时间");
		excelUtils.exportExcel(result, headerMap, "SQL自动检查", response);
	}
}
