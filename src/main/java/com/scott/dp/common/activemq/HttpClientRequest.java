package com.scott.dp.common.activemq;

import com.scott.dp.common.utils.Ognl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @CLASSNAME :HttpClientRequest
 * @Description :Http请求工具
 * @Author :Mr.薛
 * @Data :2019/1/17 0017  9:42
 * @Version :V1.0
 * @Status : 编写
 **/
public class HttpClientRequest {


    public static String httpGet(String url) throws Exception{
        //相对于commons-httpclient 3.1这里采用接口的方式来获取httpclient了
        HttpClient httpClient = HttpClients.createDefault();
        //声明请求方式
        HttpGet httpGet = new HttpGet(url);
        //获取相应数据，这里可以获取相应的数据
        HttpResponse httpResponse = httpClient.execute(httpGet);
        //拿到实体
        HttpEntity httpEntity= httpResponse.getEntity();
        //获取结果，这里可以正对相应的数据精细字符集的转码
        String result = "";
        if (httpEntity != null) {
            result = EntityUtils.toString(httpEntity,"utf-8");
        }
        //关闭连接
        httpGet.releaseConnection();
        return result;
    }

    public static String httpTokenGet(String reqUrl,String token) throws Exception{
        HttpURLConnection conn = null;
        String result = "";
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            if(Ognl.isNotEmpty(token)) {
                conn.addRequestProperty("token", token);
            }

            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = in.read(buf)) > 0;) {
                out.write(buf, 0, i);
            }
            out.flush();
            out.close();
            result = new String(out.toByteArray(), "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }


    @SuppressWarnings("unused")
	public static String httpPost(String url,String jsondata) throws Exception{
        //相对于commons-httpclient 3.1这里采用接口的方式来获取httpclient了
        HttpClient httpClient = HttpClients.createDefault();
        //声明请求方式
        HttpPost httpPost = new HttpPost(url);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
        //设置消息头
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        httpPost.setHeader("Accept","application/json");
        //设置发送数据(数据尽量为json),可以设置数据的发送时的字符集
        httpPost.setEntity(new StringEntity(jsondata,"utf-8"));
        //获取相应数据，这里可以获取相应的数据
        HttpResponse httpResponse = httpClient.execute(httpPost);
        //拿到实体
        HttpEntity httpEntity= httpResponse.getEntity();
        //获取结果，这里可以正对相应的数据精细字符集的转码
        String result = "";
        if (httpEntity != null) {
            result = EntityUtils.toString(httpEntity,"utf-8");
        }
        //关闭连接
        httpPost.releaseConnection();
        return result;
    }


    @SuppressWarnings("unused")
	public static String httpPut(String url,String jsondata) throws Exception{
        SSLContext sslContext = SSLContext.getInstance("TLS");
        System.setProperty("https.protocols", "TLSv1");
        //相对于commons-httpclient 3.1这里采用接口的方式来获取httpclient了
        HttpClient httpClient = HttpClients.createDefault();
        //声明请求方式
        HttpPut httpPut = new HttpPut(url);
        //设置消息头
        httpPut.setHeader("Content-Type","application/json;charset=utf-8");
        httpPut.setHeader("Accept","application/json");
        //设置发送数据(数据尽量为json),可以设置数据的发送时的字符集
        httpPut.setEntity(new StringEntity(jsondata,"utf-8"));
        //获取相应数据，这里可以获取相应的数据
        HttpResponse httpResponse = httpClient.execute(httpPut);
        //拿到实体
        HttpEntity httpEntity= httpResponse.getEntity();
        //获取结果，这里可以正对相应的数据精细字符集的转码
        String result = "";
        if (httpEntity != null) {
            result = EntityUtils.toString(httpEntity,"utf-8");
        }
        //关闭连接
        httpPut.releaseConnection();
        return result;
    }


    public static String httpDelete(String url) throws Exception{
        //相对于commons-httpclient 3.1这里采用接口的方式来获取httpclient了
        HttpClient httpClient = HttpClients.createDefault();
        //声明请求方式
        HttpDelete httpDelete = new HttpDelete(url);
        //设置消息头(这里可以根据自己的接口来设定消息头)
        httpDelete.setHeader("Content-Type","application/json;charset=utf-8");
        httpDelete.setHeader("Accept","application/json");
        //获取相应数据，这里可以获取相应的数据
        HttpResponse httpResponse = httpClient.execute(httpDelete);
        //拿到实体
        HttpEntity httpEntity= httpResponse.getEntity();
        //获取结果，这里可以正对相应的数据精细字符集的转码
        String result = "";
        if (httpEntity != null) {
            result = EntityUtils.toString(httpEntity,"utf-8");
        }
        //关闭连接
        httpDelete.releaseConnection();
        return result;
    }


    /**
     * get请求，参数拼接在地址上
     * @param url 请求地址加参数
     * @return 响应
     */
    public static String get(String url)
    {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get请求，参数放在map里
     * @param url 请求地址
     * @param map 参数map
     * @return 响应
     */
    public static String getMap(String url,Map<String,String> map)
    {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameters(pairs);
            HttpGet get = new HttpGet(builder.build());
            response = httpClient.execute(get);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if(entity != null)
        {
            long lenth = entity.getContentLength();
            if(lenth != -1 && lenth < 2048)
            {
                result = EntityUtils.toString(entity,"UTF-8");
            }else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }


    /**
     * post请求，参数为json字符串
     * @param url 请求地址
     * @param jsonString json字符串
     * @return 响应
     */
    public static String postJson(String url,String jsonString)
    {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送post请求，参数用map接收
     * @param url 地址
     * @param map 参数
     * @return 返回值
     */
    public static String postMap(String url,Map<String,String> map) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry : map.entrySet())
        {
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
