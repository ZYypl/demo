package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 */
public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 获取客户端的真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    logger.error("获取用户真实IP", e);
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * httpPost
     *
     * @param url       路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        return httpPost(url, jsonParam, false);
    }

    /**
     * put请求
     *
     * @param url       url地址
     * @param jsonParam 参数
     *                  不需要返回结果
     * @return
     */
    public static JSONObject httpPut(String url, JSONObject jsonParam) {
        // post请求返回结果
        JSONObject jsonResult = null;
        HttpPut method = new HttpPut(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(),
                        "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /** 读取服务器返回过来的json字符串数据 **/
                    str = EntityUtils.toString(result.getEntity());
                    /** 把json字符串转换成json对象 **/
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                } finally {
                    result.close();
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * 发送post请求
     *
     * @param url            路径
     * @param jsonParam      参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
        JSONObject jsonResult = null;
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        // 创建默认的httpClient实例.
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            httppost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String str = "";
                    try {
                        /** 读取服务器返回过来的json字符串数据 **/
                        str = EntityUtils.toString(response.getEntity());
                        if (noNeedResponse) {
                            return null;
                        }
                        /** 把json字符串转换成json对象 **/
                        jsonResult = JSONObject.parseObject(str);
                    } catch (Exception e) {
                        logger.error("post请求提交失败:" + url, e);
                    }
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return jsonResult;
    }

    /**
     * 发送post请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpPost(String url) {
        JSONObject jsonResult = null;
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建默认的httpClient实例.
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String str = "";
                    try {
                        /** 读取服务器返回过来的json字符串数据 **/
                        str = EntityUtils.toString(response.getEntity());
                        /** 把json字符串转换成json对象 **/
                        jsonResult = JSONObject.parseObject(str);
                    } catch (Exception e) {
                        logger.error("post请求提交失败:" + url, e);
                    }
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url, String jsonParam) {
        // get请求返回结果
        JSONObject jsonResult = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            List<NameValuePair> params = Lists.newArrayList();
            params.add(new BasicNameValuePair("accessToken", jsonParam));
            String str = "";
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            // 创建httpget.
            url = URLDecoder.decode(url, "UTF-8");
            HttpGet httpget = new HttpGet(url + "?" + str);
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                /** 请求发送成功，并得到响应 **/
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    /** 读取服务器返回过来的json字符串数据 **/
                    String strResult = EntityUtils.toString(response.getEntity());
                    /** 把json字符串转换成json对象 **/
                    jsonResult = JSONObject.parseObject(strResult);
                } else {
                    logger.error("get请求提交失败:" + url);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (ParseException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return jsonResult;
    }

    public static JSONObject httpDelete(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 发送get请求
            HttpDelete request = new HttpDelete(url);
            // 执行get请求.
            CloseableHttpResponse responseDelete = client.execute(request);
            try {
                /** 请求发送成功，并得到响应 **/
                if (responseDelete.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    /** 读取服务器返回过来的json字符串数据 **/
                    String strResult = EntityUtils.toString(responseDelete.getEntity());
                    /** 把json字符串转换成json对象 **/
                    jsonResult = JSONObject.parseObject(strResult);
                    url = URLDecoder.decode(url, "UTF-8");
                } else {
                    logger.error("get请求提交失败:" + url);
                }
            } finally {
                responseDelete.close();
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    private HttpUtil() {
    }

    ;
}
