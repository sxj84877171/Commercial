package com.ytmall.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FavoriteGoods implements Serializable {
    public String favoriteId;//记录ID
    public String goodsId;//
    public String goodsThums;//商品缩略图
    public String goodsName;//商品名称
    public String isSale;//商品上架状态 0:已下架 1:销售中,
    public String shopPrice;//商品价格
    public String saleCount;//销量
    public String goodsAttrId;//价格属性ID

}
