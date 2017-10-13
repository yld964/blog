package com.yy.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpRequester {
    public static String doRequest(String url,String method,String parms){
        HttpClient httpClient = getHttpClient();
        HttpEntity entity = null;
        try {
            if("get".equalsIgnoreCase(method)){
                entity = doGet(url,parms,httpClient);
            } else {
                entity = doPost(url,parms,httpClient);
            }
            if(entity!=null){
                return EntityUtils.toString(entity,"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpEntity doGet(String url, String parms, HttpClient httpClient){
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            return response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpEntity doPost(String url,String parms,HttpClient httpClient){
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        try {
            if(parms!=null){
                StringEntity entity = new StringEntity(parms,"utf-8");
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            return response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取httpclient，支持https请求
     * @return
     */
    private static HttpClient getHttpClient(){
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
}
