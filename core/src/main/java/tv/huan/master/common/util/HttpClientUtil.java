/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.huan.master.common.util;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HttpClientUtil {


    public static String doPostByJson(String url, String data) throws Exception {

        Integer statusCode;
        CloseableHttpClient httpclient = HttpClients.createDefault();        //        请求超时
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity(data, "UTF-8");
        httpPost.setEntity(entity);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        HttpResponse response = httpclient.execute(httpPost);
        statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpException("Http Status is error.");
        }
        return EntityUtils.toString(response.getEntity(),"UTF-8");
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param data 请求的查询参数
     * @return 返回请求响应的HTML
     */
    public static String doPostbyxml(String url, String data) throws Exception {
        Integer statusCode;
        CloseableHttpClient httpclient = HttpClients.createDefault();        //        请求超时
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity(data, "UTF-8");
        entity.setContentType("application/json;charset=UTF-8");
        httpPost.setEntity(entity);
        httpPost.addHeader("Content-Type", "application/xml");
        HttpResponse response = httpclient.execute(httpPost);
        statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpException("Http Status is error.");
        }
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public static String getPostContent(HttpServletRequest request) throws IOException {
        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            String result = doPostByJson("http://118.194.161.27:8102/push/getIp", "{\"dnum\":\"0\",\"huanid\":\"0\",\"snum\":\"0\"}");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
