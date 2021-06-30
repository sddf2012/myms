package com.my.common.instrument.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liu peng bo
 * date: 2020/3/16 17:40
 */
@Slf4j
public class HttpClientUtils {
    private static final String UTF_8 = "UTF-8";

    private static final String APPLICATION_JSON = "application/json";

    public static final String TEXT_PLAIN = "text/plain";


    private static final String FORM_URLENCODED = "application/x-www-form-urlencoded";

    private static final int CONNECT_TIMEOUT = 10000;

    private static final int SOCKET_TIMEOUT = 60000;

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    public static HttpRespInfo doGet(String url, int connect, int socket) throws Exception {
        return doGet(url, null, null, connect, socket);
    }

    public static HttpRespInfo doGetForHeader(String url, Map<String, String> headers) throws Exception {
        return doGet(url, headers, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        return doGet(url, headers, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doGet(String url, Map<String, String> headers, Map<String, String> params, int connectTimeout, int socketTimeout) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (!CollectionUtils.isEmpty(params)) {

            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        /*
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;


        log.info("http req url:{}", url);
        log.info("http req header:{}", JSON.toJSONString(headers));
        log.info("http req params:{}", JSON.toJSONString(params));
        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doPost(String url) throws Exception {
        return doPostFinal(url, null, null, null);
    }

    public static HttpRespInfo doPost(String url, Map<String, String> headers) throws Exception {
        return doPostFinal(url, headers, null, null);
    }

    public static HttpRespInfo doPostWithParams(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        return doPostFinal(url, headers, params, null);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url           请求地址
     * @param headers       请求头集合
     * @param paramsContent body
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doPostWithBody(String url, Map<String, String> headers, Map<String, String> paramsContent) throws Exception {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Content-Type", FORM_URLENCODED);
        // 创建entity
        HttpEntity entity = null;
        if (!CollectionUtils.isEmpty(paramsContent)) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Map.Entry<String, String>> entrySet = paramsContent.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            entity = new UrlEncodedFormEntity(nvps, UTF_8);
        }
        return doPostFinal(url, headers, null, entity);
    }

    /**
     * 发送post请求；带请求头和body
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param content body
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doPostWithBody(String url, Map<String, String> headers, String content) throws Exception {
        return doPostWithBody(url, headers, content, APPLICATION_JSON);
    }

    /**
     * 发送post请求；带请求头和body
     *
     * @param url         请求地址
     * @param headers     请求头集合
     * @param content     body
     * @param contentType contentType
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doPostWithBody(String url, Map<String, String> headers, String content, String contentType) throws Exception {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Content-Type", contentType);
        // 创建entity
        HttpEntity entity = null;
        if (StringUtils.hasText(content)) {
            entity = new StringEntity(content, UTF_8);
        }
        return doPostFinal(url, headers, null, entity);
    }

    private static HttpRespInfo doPostFinal(String url, Map<String, String> headers, Map<String, String> params, HttpEntity httpEntity) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (!CollectionUtils.isEmpty(params)) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        // 创建http对象
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        /*
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        packageHeader(headers, httpPost);

        // 封装body
        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        log.info("http req url:{}", url);
        log.info("http req header:{}", JSON.toJSONString(headers));
        log.info("http req params:{}", JSON.toJSONString(params));

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doPut(String url) throws Exception {
        return doPut(url, null);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doPut(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);

        packageParam(params, httpPut);

        CloseableHttpResponse httpResponse = null;

        log.info("http req url:{}", url);
        log.info("http req params:{}", JSON.toJSONString(params));
        try {
            return getHttpClientResult(httpResponse, httpClient, httpPut);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return result
     * @throws Exception ex
     */
    public static HttpRespInfo doDelete(String url, Map<String, String> headers) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);

        packageHeader(headers, httpDelete);

        CloseableHttpResponse httpResponse = null;
        log.info("http req url:{}", url);
        log.info("http req header:{}", JSON.toJSONString(headers));
        try {
            return getHttpClientResult(httpResponse, httpClient, httpDelete);
        } finally {
            release(httpResponse, httpClient);
        }
    }


    /**
     * Description: 封装请求头
     *
     * @param params     params
     * @param httpMethod 请求方法
     */
    private static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (!CollectionUtils.isEmpty(params)) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param params     params
     * @param httpMethod httpMethod
     * @throws UnsupportedEncodingException ex
     */
    private static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (!CollectionUtils.isEmpty(params)) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, UTF_8));
        }
    }

    /**
     * Description: 获得响应结果
     *
     * @param httpResponse httpResponse
     * @param httpClient   httpClient
     * @param httpMethod   httpMethod
     * @return result
     * @throws Exception ex
     */
    private static HttpRespInfo getHttpClientResult(CloseableHttpResponse httpResponse,
                                                    CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
        // 执行请求
        httpResponse = httpClient.execute(httpMethod);
        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String content = null;
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), UTF_8);
            }
            log.info("http response:{}", content == null ? null : content.trim());
            return new HttpRespInfo(httpResponse.getStatusLine().getStatusCode(), content);
        }
        return new HttpRespInfo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * Description: 释放资源
     *
     * @param httpResponse httpResponse
     * @param httpClient   httpClient
     * @throws IOException ex
     */
    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

}
