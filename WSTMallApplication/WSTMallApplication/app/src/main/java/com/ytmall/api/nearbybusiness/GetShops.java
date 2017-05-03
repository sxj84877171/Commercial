package com.ytmall.api.nearbybusiness;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetShops extends AbstractParam {
	public String a="getShops";

	public String areaId2;//城市ID
	public double latitude;//纬度
	public double longitude;//经度
	public int desc;//排序类型 0-距离从近到远 1-配送费用从低到高 2:好评度
	public int descType;//排序方式 0:升序 1:降序
	public int p;//页码
	public String key;
	@Override
	public String getA() {
		return a;
	}
}
