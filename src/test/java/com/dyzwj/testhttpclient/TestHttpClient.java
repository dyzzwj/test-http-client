package com.dyzwj.testhttpclient;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TestHttpClient {



    //无参数get请求
    @Test
    public void test1() throws Exception{

        //创建HttpClient对象

        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建HttpGet请求

        HttpGet httpGet = new HttpGet("http://localhost:10000/version/getAll/honglin");

        //执行请求，获得响应
        try(CloseableHttpResponse response = httpClient.execute(httpGet)){
            //获取响应码
            int responseCode = response.getStatusLine().getStatusCode();
            if(responseCode == 200){
                HttpEntity httpEntity = response.getEntity();
                //请求题内容
                String content = EntityUtils.toString(httpEntity, "UTF-8");
                FileUtils.writeStringToFile(new File("/Users/sirzheng/Documents/content.txt"),content,"UTF-8");

                System.out.println(content.length());

            }
        }




    }

    //有参数get请求

    /**
     * 两种方式：
     *  1.直接将参数拼接到url后面 如：?wd=java
     *  2.使用URI的方法设置参数 setParameter("wd", "java")
     */

    @Test
    public void test2 () throws Exception{

        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://www.baidu.com/s");
        uriBuilder.setParameter("wd","java");
        URI uri = uriBuilder.build();


        HttpGet httpGet = new HttpGet(uri);


        try(CloseableHttpResponse response = httpClient.execute(httpGet)){
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity,"UTF-8");
                FileUtils.writeStringToFile(new File("/Users/sirzheng/Documents/java.html"),content,"UTF-8");
            }

        }



    }


    //POST请求
    @Test
    public void test3(){

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        try(CloseableHttpResponse response = httpClient.execute(httpPost)){
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            System.out.println(statusCode);
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "UTF-8");
                FileUtils.writeStringToFile(new File("/Users/sirzheng/Documents/java.html"),content,"UTF-8");
                System.out.println(content.length());
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    //带有参数的POST请求
    @Test
    public void test4() throws Exception{

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://172.18.225.16:10000/version/getCustomers");
        List<NameValuePair> parameters = new ArrayList<>();

       // parameters.add(new BasicNameValuePair("customer","ali"));

        //parameters.add(new BasicNameValuePair("q","java"));

        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters);

        httpPost.setEntity(encodedFormEntity);

        httpPost.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");

        try(CloseableHttpResponse response = httpClient.execute(httpPost)){

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            if(statusCode == 200){

                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "UTF-8");
                FileUtils.writeStringToFile(new File("/Users/sirzheng/Documents/java.html"),content,"UTF-8");

                System.out.println(content.length());

            }

        }


    }
}
