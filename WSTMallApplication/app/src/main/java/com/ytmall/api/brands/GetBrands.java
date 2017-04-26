package com.ytmall.api.brands;

/**
 * areaId3:县区ID
 brandName:品牌信息 
 */
import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetBrands extends AbstractParam {
	private String a = "getBrands";
	public String areaId3;
	public String brandName;
	@Override
	public String getA() {
		return a;
	}
}
