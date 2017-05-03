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
# 创建日期：2015-6-11
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.bean;

import java.io.Serializable;

import com.ytmall.util.EAJson;


/**
 * @ClassName User
 * @Create Date 2015-6-11 下午6:45:25 
 * 
 * @author Tans      QQ     1511895018
 * WSTMall开源商城-合作团队     官网地址:http://www.wstmall.com   联系QQ:707563272
 */
public class User implements Serializable {

	public String userId;//用户ID
	public String loginName;//登录账号
	public String userName;//用户名称，这个值不一定有，用户有填才会有值
	public String userPhoto;//用户头像
	public String userScore;//用户积分
	public String userSex;//性别
	public String userType;//0:普通会员  1:店鋪用户  2:两者都是

	public String userMoney;//我的金额
	public String lockMoney;//冻结金额
	public String distributMoney;//分销佣金
	public String shopMoney;//店铺销售金额
	public String shopLockMoney;//店铺冻结金额

	public int hasShop;//1。是经销商0。不是
	public int shopId;
	public String payPwd;//是否设置过支付密码
	public String userPhone;
	public String recommend_user;//我的好友
	public int is_super_user;//0普通会员1高级会员
	public String jinMoney;
	public String yinMoney;
	public String lockJinMoney;
	public String jifenPhone;

	public int user_source = -1;
	public String user_link_account;

	public String getName(){
		if(userName==null||userName.equals("")){
			return loginName;
		}else{
			return userName;
		}
	}
	
	public String getSex(){
		if(userSex.equals("1")){
			return "男";
		}else if(userSex.equals("2")){
			return "女";
		}else{
			return "保密";
		}
	}
	
	public String getSexNum(String sexName){
		if(sexName.equals("男")){
			return "1";
		}else if(sexName.equals("女")){
			return "2";
		}else{
			return "0";
		}
	}
	
}
