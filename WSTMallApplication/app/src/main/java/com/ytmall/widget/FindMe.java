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
# 创建日期：2015-6-15
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.widget;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
//import com.amap.api.location.LocationManagerProxy;
//import com.amap.api.location.LocationProviderProxy;
import com.ytmall.application.Const;
import com.ytmall.bean.City;
import com.ytmall.bean.Point;

/**
 * @ClassName FindMe
 * @Create Date 2015-6-15 上午11:09:06 
 * 
 * @author Tans      QQ     1511895018
 * WSTMall开源商城-合作团队     官网地址:http://www.wstmall.com   联系QQ:707563272
 */
public class FindMe
//		implements AMapLocationListener
{

//	private Activity activity;
//	private FindMeCallback findMeCallback;
//	private LocationManagerProxy mLocationManagerProxy;
//
//	public FindMe(Activity activity, FindMeCallback findMeCallback){
//		this.activity=activity;
//		this.findMeCallback=findMeCallback;
//		init();
//	}
//
//	private void init(){
//		mLocationManagerProxy = LocationManagerProxy.getInstance(activity);
//
//		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//		// 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
//		// 在定位结束后，在合适的生命周期调用destroy()方法
//		// 其中如果间隔时间为-1，则定位只定一次
//		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 1, 15, this);
//
//		mLocationManagerProxy.setGpsEnable(true);
//	}
//
//	public static interface FindMeCallback {
//
//		void afterFindMe(Point point,City city,City city2);
//
//	}
//
//	@Override
//	public void onLocationChanged(AMapLocation amapLocation) {
//		// TODO Auto-generated method stub
//
//		if(amapLocation != null && amapLocation.getErrorCode() == 0){
//			Point point=new Point(amapLocation.getLatitude(), amapLocation.getLongitude());
//			City city=new City(Const.cache.city.getCityid(), amapLocation.getCity());
//			City city2=new City(Const.cache.city2.getCityid(), amapLocation.getDistrict());
//			findMeCallback.afterFindMe(point, city,city2);
//			mLocationManagerProxy.removeUpdates(this);
//			mLocationManagerProxy.destroy();
//		}
//	}

//	@Override
//	public void onLocationChanged(Location location) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}

}
