/**
#************************************************
# 项目名称：WSTMall
# 版本号： V1.0  
#************************************************
# 文件说明：
#          
#************************************************
# 子模块说明：
#                     
#************************************************
# 创建人员： 谈泳豪
# 创建人员QQ：1511895018
# 创建日期：2015-7-24
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.api;

import java.io.File;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;


@RequestType(type = HttpMethod.POST)
public class UploadPic extends AbstractParam{
	
	private String a = "uploadPic";
	
	public File Filedata;//照片对象
	public String dir="users";//照片目录 “users”--必须传这个过来 testa

	@Override
	public String getA() {
		return a;
	}
	
}
