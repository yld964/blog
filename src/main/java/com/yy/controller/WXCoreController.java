package com.yy.controller;

import com.yy.utils.DataTrans;
import com.yy.utils.WX.TulinRobot;
import com.yy.utils.WX.WXMsg;
import com.yy.utils.WX.WXTextMsg;
import com.yy.utils.WX.WXReceiveMsg;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WXCoreController {
    private String TOKEN = "1qazxsw2";

    /**
     * 接收并校验四个请求参数
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return echostr
     */
    @RequestMapping(value = "/",method= RequestMethod.GET)
    public String checkName(@RequestParam(name="signature")String signature,
                            @RequestParam(name="timestamp")String timestamp,
                            @RequestParam(name="nonce")String nonce,
                            @RequestParam(name="echostr")String echostr){
        System.out.println("-----------------------开始校验------------------------");
        //排序
        String sortString = sort(TOKEN, timestamp, nonce);
        //加密
        String myString = sha1(sortString);
        //校验
        if (myString != null && myString != "" && myString.equals(signature)) {
            System.out.println("签名校验通过");
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
            return echostr;
        } else {
            System.out.println("签名校验失败");
            return "";
        }
    }

    @RequestMapping(value = "/",method = RequestMethod.POST,produces = { "application/xml" })
    public void msg(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("UTF8");
        System.out.println("接收到消息，开始处理");
        String result = "";



        try {
            InputStream in = request.getInputStream();
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(in);

            // 得到xml根元素
            Element root = doc.getRootElement();
            Map map = new HashMap();
            if(root!=null){
                recursionParseXml(root,map);
            }
            WXReceiveMsg wxReceiveMsg = new WXReceiveMsg(map);
            System.out.println("消息类型是" + wxReceiveMsg.getMsgType());

            WXTextMsg wxMsg = new WXTextMsg();
            //文本消息
            if("text".equalsIgnoreCase(wxReceiveMsg.getMsgType())){
                //返回微信的消息
                wxMsg.setCreateTime(System.currentTimeMillis());
                wxMsg.setFromUserName(wxReceiveMsg.getToUserName());
                wxMsg.setToUserName(wxReceiveMsg.getFromUserName());
                wxMsg.setMsgType("text");
                wxMsg.setContent(TulinRobot.getReply(wxReceiveMsg.getContent().toString()));
                result = DataTrans.objToXML(wxMsg,"xml");
                System.out.println(result);
                response.getWriter().write(result);
            //事件
            } else if ("event".equalsIgnoreCase(wxReceiveMsg.getMsgType())){
                wxMsg.setMsgType("text");
                wxMsg.setContent("你发的啥，我不懂");
                result = DataTrans.objToXML(wxMsg,"xml");
                System.out.println(result);
                response.getWriter().write(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void recursionParseXml(Element root,Map<String, String> map){
        List<Element> elementList = root.elements();
        if(elementList.size()==0){
            map.put(root.getName(), root.getText());
        }else{
            for (Element e : elementList){
                recursionParseXml(e,map);
            }
        }
    }

    /**
     * 排序方法
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

