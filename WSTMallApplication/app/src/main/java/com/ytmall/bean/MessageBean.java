package com.ytmall.bean;

import java.io.Serializable;

/** "id":"信息Id",
 "msgContent":"信息内容",
 "createTime":"发不信息时间 2015-07-01 00:42:09",
 "msgStatus":"信息状态 0：未读 1：已读"**/
public class MessageBean implements Serializable {
    public String id;
    public String msgContent;
    public String createTime;
    public String msgStatus;
}
