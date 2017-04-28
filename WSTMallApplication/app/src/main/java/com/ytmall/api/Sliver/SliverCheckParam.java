package com.ytmall.api.Sliver;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/4/27
 * 公司：长沙硕铠电子科技有限公司
 * Email：sunxiangjin@soarsky-e.com
 * 公司网址：http://www.soarsky-e.com
 * 公司地址（Add）  ：湖南省长沙市岳麓区麓谷信息港A座8楼
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为
 */
@RequestType(type = HttpMethod.GET)
public class SliverCheckParam extends AbstractParam {

    public String a = "rechargeToYinMoneySn" ;

    @Override
    public String getA() {
        return a;
    }
}
