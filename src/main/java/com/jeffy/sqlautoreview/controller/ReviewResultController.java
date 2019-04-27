package com.jeffy.sqlautoreview.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.model.ReviewResultModel;
import com.jeffy.sqlautoreview.service.ProjectService;
import com.jeffy.sqlautoreview.service.ReviewResultService;
import com.jeffy.sqlautoreview.utils.ExcelUtils;
import com.jeffy.sqlautoreview.vo.ReviewResultVo;

/**  
 * sql review结果控制器    
 * @ClassName: ReviewResultController    
 * @author 陈剑飞    
 * @date 2016年10月10日 上午11:25:56    
 * @version  v 1.0    
 */
@Controller
@RequestMapping("reviewResult/")
public class ReviewResultController extends BaseController {
	@Autowired
	private ReviewResultService reviewResultService;
	@Autowired
	private ProjectService projectService;
	private final String REVIEWRESULT_LIST_PAGE="reviewResult";
	
	/**
	 * review 结果列表
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
		return REVIEWRESULT_LIST_PAGE;
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
	public JsonTableData<ReviewResultVo>  dataList(ReviewResultModel model,EasyUiPager pager) throws Exception{
		JsonTableData<ReviewResultVo> data = new JsonTableData<ReviewResultVo>();
		if(StringUtils.isNotEmpty(model.getRealTablename())){
			model.setRealTablename("%"+model.getRealTablename()+"%");
		}
		if(pager!=null){
			if("createTime".equals(pager.getSort())){
				pager.setSort("t.createTime");
			}
			if("updateTime".equals(pager.getSort())){
				pager.setSort("t.updateTime");
			}
		}
		List<ReviewResultVo> result = reviewResultService.queryReviewResultVoList(model, pager);
		data.setTotal(reviewResultService.queryCount(model));
		data.setRows(result==null?new ArrayList<ReviewResultVo>():result);
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
		reviewResultService.deleteById(id);
		return response;
	}
	
	/**
	 * 导出review 结果
	 * @author 陈剑飞    
	 * @Title: exportReviewResult    
	 * @param model 
	 * @Return: void 返回值
	 */
	@RequestMapping("exportReviewResult")
	public void exportReviewResult(ReviewResultModel model,HttpServletResponse response) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ExcelUtils excelUtils = new ExcelUtils();
		List<ReviewResultVo> reviewResultVoList = reviewResultService.queryReviewResultVoList(model, null);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		if(reviewResultVoList!=null){
			Map<String,Object> map = null;
			for(ReviewResultVo reviewResultVo:reviewResultVoList){
				map =BeanToMapUtil.convertBean(reviewResultVo);
				map.put("createTime", reviewResultVo.getCreateTime()!=null?format.format(reviewResultVo.getCreateTime()):"");
				result.add(map);
			}
		}
		Map<String,String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("id", "ID");
		headerMap.put("projectName", "项目名称");
		headerMap.put("projectChName", "项目中文名");
		headerMap.put("realTablename", "真实表名");
		headerMap.put("existIndexes", "已存在索引");
		headerMap.put("newIndexes", "新索引");
		headerMap.put("mergeResult", "合并结果");
		headerMap.put("createTime", "创建时间");
		excelUtils.exportExcel(result, headerMap, "SQL检查结果", response);
	}
}
