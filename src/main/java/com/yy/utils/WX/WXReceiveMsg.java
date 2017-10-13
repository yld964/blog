package com.yy.utils.WX;

import java.util.Map;

public class WXReceiveMsg {
    //消息内容
    private Map map;

    public WXReceiveMsg(Map map){
        this.map = map;
    }

    //获取用户微信号
    public String getFromUserName(){
        return (String)map.get("FromUserName");
    }
    //获取公众号微信号
    public String getToUserName(){
        return (String)map.get("ToUserName");
    }
    //获取消息类型
    public String getMsgType(){
        return (String)map.get("MsgType");
    }
    //获取消息内容
    public Object getContent(){
        return map.get("Content");
    }
}
