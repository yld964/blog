package com.yy.utils.WX;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.utils.HttpRequester;

public class WXUtils {
    //appid
    private static final String appid = "wx78c56414058d56c9";
    //appsecret
    private static final String appsecret = "1b87bbad39e37e02829f3bd1cb244395";

    //获取access_token的url，post，200次/天，一次能使用两小时
    private static  String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //菜单创建，post，100次/天
    private static  String create_mene_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //两小时有效期
    private static final String access_token = "2CTEUEZ6AgR4CwjL9OEGxYHlDhnRjC43d0I6JigQvyzJQXoW6LDfzenLVVO4UK3FWHaCd1u5P48-53tdlRoK0O2JYyN5QpUTh76RUOTUrrOXdexBkU37ylmnP2zq2YXzEODdAHAZJY";

    public static void main(String[] args) {
//        uY_TzIssNW5FT7zVYCZSj05_hgVMvnZ-ybtLmbKR7BATCFu18UfCf9Qc5o3_D4ZR8JXm94F0_Byb762LvZLNiQoHMkp5bB1CZwtRSbfV4oPxxhmR_-u6cfaTdJVTs3SpCCKhACAQQY
//        access_token_url = access_token_url.replace("APPID",appid).replace("APPSECRET",appsecret);
//        String access_token = HttpRequester.doRequest(access_token_url,"post",null);
//        System.out.println(access_token);
//        if(1==1)return;

        create_mene_url = create_mene_url.replace("ACCESS_TOKEN",access_token);

        //二级菜单
        NoChildrenButton btn11 = new NoChildrenButton();
        btn11.setName("天气预报");
        btn11.setType("click");
        btn11.setKey("11");

        ViewButton btn12 = new ViewButton();
        btn12.setName("跳到百度");
        btn12.setUrl("http://www.baidu.com");

        //一级菜单
        HasChildrenButton btn1 = new HasChildrenButton();
        btn1.setName("生活助手");
        btn1.setSub_button(new Button[]{btn11,btn12});
        NoChildrenButton btn2 = new NoChildrenButton();
        btn2.setKey("2");
        btn2.setType("location_select");
        btn2.setName("发送位置");
        Menu menu = new Menu();
        menu.setButton(new Button[]{btn1,btn2});
        try {
            String parms = new ObjectMapper().writeValueAsString(menu);
            System.out.println(parms);
            String result =  HttpRequester.doRequest(create_mene_url,"post",parms);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
