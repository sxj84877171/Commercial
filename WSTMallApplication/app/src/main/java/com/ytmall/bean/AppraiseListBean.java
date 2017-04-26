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
# 创建日期：2015-8-10
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.bean;

import java.io.Serializable;

/**
 * @ClassName AppraiseListBean
 * @Create Date 2015-8-10 下午6:20:36 
 * 
 * @author Tans      QQ     1511895018
 * WSTMall开源商城-合作团队     官网地址:http://www.wstmall.com   联系QQ:707563272
 */
public class AppraiseListBean implements Serializable {

	public String loginName;//用户,
	public String userPhoto;//用户头像
	public String goodsScore;//商品评分,
	public String serviceScore;//服务评分,
	public String timeScore;//时效评分,
	public String content;//点评内容,
	public String createTime;//创建时间,

}
