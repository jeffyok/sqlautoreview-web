package com.jeffy.sqlautoreview.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.jeffy.sqlautoreview.core.AppException;
import com.jeffy.sqlautoreview.core.ErrorCodeEnums;

/**  
 * 扫描文件工具类   
 * @ClassName: ScanFileUtils    
 * @author 陈剑飞    
 * @date 2016年10月11日 上午10:39:59    
 * @version  v 1.0    
 */
public class ScanFileUtils {
	/**
	 * 扫描结果集
	 */
	private List<String> fileList = new ArrayList<String>();
	
	/**
	 * 扫描文件   
	 * @author 陈剑飞    
	 * @Title: scanFileList    
	 * @param rootPath 
	 * @Return: void 返回值
	 */
	public void scanFileList(String rootPath){
		File file = new File(rootPath);
		if(!file.exists()){
			throw new AppException(ErrorCodeEnums.FIEL_NOTEXIST.getCode(),rootPath+"目录不存在");
		}
		if(file.isDirectory()){
			File[] files = file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {//只列出mapper文件
					File file = new File(dir.getAbsolutePath()+File.separator+name);
					if(file.isDirectory()){
						return true;
					}
					return name.endsWith("Mapper.xml");
				}
			});
			for(File tempFile:files){
				if(tempFile.isDirectory()){
					scanFileList(tempFile.getAbsolutePath());
				}else{
					fileList.add(tempFile.getAbsolutePath());
				}
			}
		}else{
			fileList.add(file.getAbsolutePath());
		}
	}

	public List<String> getFileList() {
		return fileList;
	}
	
	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public static void main(String args[]){
		ScanFileUtils utils = new ScanFileUtils();
		utils.scanFileList("E:\\eclipse_open_work\\sqlautoreview-web\\src\\main\\resources\\mybatis");
		System.out.println(utils.fileList);
	}
}
