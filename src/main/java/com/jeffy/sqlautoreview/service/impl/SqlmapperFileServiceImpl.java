package com.jeffy.sqlautoreview.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.base.BaseServiceImpl;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.mapper.ProjectMapper;
import com.jeffy.sqlautoreview.mapper.ReviewResultMapper;
import com.jeffy.sqlautoreview.mapper.SqlmapperFileMapper;
import com.jeffy.sqlautoreview.mapper.SqlreviewMapper;
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.model.SqlmapperFileModel;
import com.jeffy.sqlautoreview.service.SqlmapperFileService;
import com.jeffy.sqlautoreview.utils.ScanFileUtils;
import com.jeffy.sqlautoreview.vo.SqlmapperFileVo;

/**  
 * sqlmapper file 服务实现类   
 * @ClassName: SqlmapperFileServiceImpl    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:08:54    
 * @version  v 1.0    
 */
@Service
@Transactional
public class SqlmapperFileServiceImpl extends BaseServiceImpl<SqlmapperFileModel> implements SqlmapperFileService{
	@Autowired
	private SqlmapperFileMapper mapper;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private SqlreviewMapper sqlreviewMapper;
	@Autowired
	private ReviewResultMapper reviewResultMapper;
	
	@Override
	protected BaseMapper getMapper() {
		return mapper;
	}

	@Override
	public void scanMapperFile(Integer projectId) throws AppException{
		Date now = new Date();
		//重新扫描要删除已存在的sql mapper文件
		//mapper.deleteByProjectId(projectId);
		//根据项目删除 sql review
		sqlreviewMapper.deleteByProjectId(projectId);
		//根据项目删除 review 结果
		reviewResultMapper.deleteByProjectId(projectId);
		ProjectModel projectModel = projectMapper.getById(projectId);
		String[] rootPathArray = projectModel.getMapperRootPath().split(",");//根路径可以配置多个，用逗号隔开
		for(String rootPath : rootPathArray){
			ScanFileUtils scanFileUtils = new ScanFileUtils();
			scanFileUtils.scanFileList(rootPath);
			List<String> mapperFileList = scanFileUtils.getFileList();
			if(mapperFileList != null  && mapperFileList.size() > 0){
				SqlmapperFileModel sqlmapperFileModel = null;
				for(String file:mapperFileList){
					sqlmapperFileModel = new SqlmapperFileModel();
					sqlmapperFileModel.setMapperFilePath(file);
					int pos = file.lastIndexOf("/");
					if(pos==-1){
						pos = file.lastIndexOf("\\");
					}
					String fileName = file.substring(pos+1);
					sqlmapperFileModel.setFileName(fileName);
					sqlmapperFileModel.setProjectId(projectId);
					sqlmapperFileModel.setScanTime(now);
					//先检查更新，更新失败则插入数据
					int result = mapper.updateSqlmapperFileModelByProjectIdAndfileName(sqlmapperFileModel);
					if(result==0){
						mapper.add(sqlmapperFileModel);
					}
				}
			}
		}
		//更新扫描状态
		projectModel.setReviewTime(now);
		projectMapper.updateBySelective(projectModel);
	}

	@Override
	public List<SqlmapperFileVo> querySqlmapperFileVoList(
			SqlmapperFileModel model, Pager pager) throws AppException{
		if(StringUtils.isNotEmpty(model.getFileName())){
			model.setFileName("%"+model.getFileName()+"%");
		}
		return mapper.querySqlmapperFileVoList(model, pager);
	}
}
