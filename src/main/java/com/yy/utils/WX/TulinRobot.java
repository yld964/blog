package com.yy.utils.WX;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.utils.HttpRequester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取图林机器人自动回复
 */
public class TulinRobot {
    public final static String apiId = "b9f8a7161e2b4eed8c930d22d7d9c7cf";
    public final static String userid = "1qazxsw2";

    /**
     * 根据微信消息获取回复
     * @param wxmsg
     * @return
     */
    public static String getReply(String wxmsg){
        ObjectMapper objectMapper = new ObjectMapper();
        Map parms = new HashMap();
        parms.put("key",apiId);
        parms.put("userid",userid);
        parms.put("info",wxmsg);
        try {
            String strparms = objectMapper.writeValueAsString(parms);
            //发送请求
            String responseStr = HttpRequester.doRequest("http://www.tuling123.com/openapi/api","post",strparms);
            if(responseStr!=null){
                Map remap = objectMapper.readValue(responseStr,Map.class);
                return remap.get("text").toString();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return "我不知道你在说什么";
    }
}
