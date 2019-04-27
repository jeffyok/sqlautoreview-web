package com.jeffy.sqlautoreview.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeffy.sqlautoreview.autoreview.CreateIndex;
import com.jeffy.sqlautoreview.autoreview.MergeIndex;
import com.jeffy.sqlautoreview.autoreview.XmlToSQL;
import com.jeffy.sqlautoreview.base.BaseMapper;
import com.jeffy.sqlautoreview.base.BaseServiceImpl;
import com.jeffy.sqlautoreview.base.pagination.Pager;
import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.core.ErrorCodeEnums;
import com.jeffy.sqlautoreview.enums.YesNoEnum;
import com.jeffy.sqlautoreview.mapper.ProjectMapper;
import com.jeffy.sqlautoreview.mapper.ReviewResultMapper;
import com.jeffy.sqlautoreview.mapper.SqlmapperFileMapper;
import com.jeffy.sqlautoreview.mapper.SqlreviewMapper;
import com.jeffy.sqlautoreview.model.ProjectModel;
import com.jeffy.sqlautoreview.model.SqlmapperFileModel;
import com.jeffy.sqlautoreview.model.SqlreviewModel;
import com.jeffy.sqlautoreview.service.SqlreviewService;
import com.jeffy.sqlautoreview.utils.ScanFileUtils;
import com.jeffy.sqlautoreview.vo.SqlreviewVo;

/**  
 * sqlreview 服务实现类  
 * @ClassName: SqlreviewServiceImpl    
 * @author 陈剑飞    
 * @date 2016年10月10日 下午3:18:45    
 * @version  v 1.0    
 */
@Service
@Transactional
public class SqlreviewServiceImpl  extends BaseServiceImpl<SqlreviewModel> implements SqlreviewService{
	private Logger logger = LoggerFactory.getLogger(SqlreviewServiceImpl.class);
	@Autowired
	private SqlreviewMapper mapper;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private SqlmapperFileMapper sqlmapperFileMapper;
	@Autowired
	private ReviewResultMapper reviewResultMapper;
	
	@Override
	protected BaseMapper getMapper() {
		return mapper;
	}

	@Override
	public void review(Integer projectId) throws Exception{
		ProjectModel projectModel = projectMapper.getById(projectId);
		//测试数据库连接
		testReviewDb(projectModel);
		//扫描mapper文件
		scanMapperFile(projectId);
		//根据项目id删除sql review
		mapper.deleteByProjectId(projectId);
		//删除当前项目的review结果
		reviewResultMapper.deleteByProjectId(projectModel.getProjectId());
		
		logger.info("SQLMAP FILE解析文件开始");
		XmlToSQL xmlToSQL = new XmlToSQL();
		List<SqlmapperFileModel> sqlmapperFileList = sqlmapperFileMapper.getSqlmapperFileListByProjectId(projectId);
		List<SqlreviewModel> sqlreviewModelList = xmlToSQL.readSqlMap(sqlmapperFileList);
		mapper.addBatch(sqlreviewModelList);
		logger.info("SQLMAP FILE解析文件结束");
		
		logger.info("auto review开始");
		CreateIndex createIndex = new CreateIndex(projectModel);
		//查找待审核sql
		List<SqlreviewModel> sqlReviewList = mapper.getAllSqlReviewByProjectId(projectId, 0);
		createIndex.reviewSQL(sqlReviewList,mapper);
		logger.info("auto review结束");
		
		logger.info("合并索引开始");

		MergeIndex mergeIndex=new MergeIndex(projectModel);
		//查找待审核sql
		List<SqlreviewModel> auditSqlReviewList = mapper.getAllSqlReviewByProjectId(projectId,1);
		mergeIndex.mergeAllTableIndexes(auditSqlReviewList,reviewResultMapper);
		logger.info("合并索引结束");
		//更新自动review状态
		projectModel.setReviewTime(new Date());
		projectModel.setReviewFlag(YesNoEnum.YES.getType());
		projectMapper.updateBySelective(projectModel);
	}
	
	/**
	 * 扫描mapper文件
	 * @author 陈剑飞    
	 * @Title: scanMapperFile    
	 * @param projectId
	 * @throws AppException 
	 * @Return: void 返回值
	 */
	private void scanMapperFile(Integer projectId) throws AppException{
		Date now = new Date();
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
					int result = sqlmapperFileMapper.updateSqlmapperFileModelByProjectIdAndfileName(sqlmapperFileModel);
					if(result==0){
						sqlmapperFileMapper.add(sqlmapperFileModel);
					}
				}
			}
		}
	}
	
	/**
	 * 测试review db数据库配置信息
	 * 
	 * @author 陈剑飞
	 * @Title: testReviewDb
	 * @param projectModel
	 * @Return: void 返回值
	 */
	private boolean testReviewDb(ProjectModel projectModel) throws AppException{
		boolean result =false;
		Connection conn = null;
		try {
			String JDriver = "com.mysql.jdbc.Driver";
			String conURL = "jdbc:mysql://" + projectModel.getDbIp() + ":"
					+ projectModel.getDbPort() + "/" + projectModel.getDbName();
			Class.forName(JDriver);
			conn = DriverManager.getConnection(conURL,projectModel.getDbUser(), projectModel.getDbPassword());
			result = true;
		} catch (Exception se) {
			logger.error("无法创建到数据库的连接", se);
			throw new AppException(ErrorCodeEnums.REVIEW_DB_FAIL);
		}finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<SqlreviewVo> querySqlreviewVoList(SqlreviewVo vo,
			Pager pager) throws AppException{
		if(StringUtils.isNotEmpty(vo.getJavaClassId())){
			vo.setJavaClassId("%"+vo.getJavaClassId()+"%");
		}
		if(StringUtils.isNotEmpty(vo.getFileName())){
			vo.setFileName("%"+vo.getFileName()+"%");
		}
		return mapper.querySqlreviewVoList(vo, pager);
	}

	@Override
	public int querySqlreviewVoCount(SqlreviewVo vo) throws AppException{
		if(StringUtils.isNotEmpty(vo.getJavaClassId())){
			vo.setJavaClassId("%"+vo.getJavaClassId()+"%");
		}
		if(StringUtils.isNotEmpty(vo.getFileName())){
			vo.setFileName("%"+vo.getFileName()+"%");
		}
		return mapper.querySqlreviewVoCount(vo);
	}

}
