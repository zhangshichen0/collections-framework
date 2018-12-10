/**
 * 
 */
package com.zsc.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouqisheng
 *
 */
@Slf4j
public class HttpUtils {
    private static final OkHttpClient client;
    private static final Properties mediaTypes;

    private HttpUtils() {

    }

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client = builder.connectTimeout(5, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
        mediaTypes = new Properties();
        try {
            mediaTypes.load(HttpUtils.class.getClassLoader().getResourceAsStream("http_media_type.properties"));
        } catch (IOException e) {
            log.error("加载http content-type对应表异常", e);
            throw new RuntimeException("加载http content-type对应表异常", e);
        }
    }
    
    /**
     * 获取媒体类型
     * @param file
     * @return
     */
    public static MediaType getMediaType(File file) {
        String fileName = file.getName();
        String posfix = fileName.substring(fileName.lastIndexOf('.'));
        MediaType mediaType = MediaType.parse(mediaTypes.getProperty(posfix));
        if (mediaType == null) {
            return MediaType.parse("application/octet-stream");
        }
        return mediaType;
    }
    
    /**
     * 获取媒体类型
     * @param fileName
     * @return
     */
    public static MediaType getMediaType(String fileName) {
        String posfix = fileName.substring(fileName.lastIndexOf('.'));
        MediaType mediaType = MediaType.parse(mediaTypes.getProperty(posfix));
        if (mediaType == null) {
            return MediaType.parse("application/octet-stream");
        }
        return mediaType;
    }

    /**
     * 执行get请求
     * @param url
     * @param headers
     * @return
     */
    public static String get(String url, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestBuilder.build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.warn("访问 {} 失败，返回: code:{} msg: {}, body:[]",url, response.code(), response.message(), response.body());
            }

        } catch (IOException e) {
            log.error("do get error, url={}", url, e);
        }
        return null;
    }
    
    /**
     * 执行post请求
     * 
     * @param url
     * @param headers
     * @param params 已经编码过的参数
     * @return
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Entry<String, String> entry : params.entrySet()) {
            formBodyBuilder.addEncoded(entry.getKey(), entry.getValue());
        }
        Request request = requestBuilder.post(formBodyBuilder.build()).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.warn("访问 {} 失败，返回: code:{} msg: {}, body:[]",url, response.code(), response.message(), response.body());
            }

        } catch (IOException e) {
            log.error("do post error, url={}", url, e);
        }
        return null;
    }
    
    /**
     * 执行post请求，多媒体上传
     * 
     * @param url
     * @param headers
     * @param params 已经编码过的参数
     * @return
     */
    public static String postMultipart(String url, Map<String, String> headers, Map<String, Object> params) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        for (Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof File) {
                File file = (File) entry.getValue();
                MediaType mediaType = getMediaType(file);
                bodyBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(mediaType, file));
            } else {
                bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
            
        }
        Request request = requestBuilder.post(bodyBuilder.build()).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.warn("访问 {} 失败，返回: code:{} msg: {}, body:[]",url, response.code(), response.message(), response.body());
            }
        } catch (IOException e) {
            log.error("do post error, url={}", url, e);
        }
        return null;
    }
    
    /**
     * 执行post请求
     * @param url
     * @param headers
     * @param mediaType
     * @param content
     * @return
     */
    public static String post(String url, Map<String, String> headers, MediaType mediaType, String content) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request request = requestBuilder.post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.warn("访问 {} 失败，返回: code:{} msg: {}, body:[]",url, response.code(), response.message(), response.body());
            }
        } catch (IOException e) {
            log.error("do post error, url={}", url, e);
        }
        return null;
    }
    
    /**
     * 执行post请求
     * @param url
     * @param headers
     * @param mediaType
     * @param content
     * @return
     */
    public static byte[] postBinary(String url, Map<String, String> headers, MediaType mediaType, String content) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request request = requestBuilder.post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
               return response.body().bytes();
            } else {
                log.warn("访问 {} 失败，返回: code:{} msg: {}, body:[]",url, response.code(), response.message(), response.body());
            }
        } catch (IOException e) {
            log.error("do post error, url={}", url, e);
        }
        return null;
    }
    
    /**
     * 执行post请求
     * @param url
     * @param headers
     * @param mediaType
     * @param content
     * @return
     */
    public static String post(String url, Map<String, String> headers, MediaType mediaType, byte[] content) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request request = requestBuilder.post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
               return response.body().string();
            } else {
                log.warn("访问 {} 失败，返回: code:{} msg: {}, body:[]",url, response.code(), response.message(), response.body());
            }
        } catch (IOException e) {
            log.error("do post error, url={}", url, e);
        }
        return null;
    }
    
    
    /**
     * put上传文件
     * @param url
     * @param headers
     * @param file
     * @param contentType
     * @return
     */
    public static Response putFile(String url, Map<String, String> headers, File file, String contentType) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        MediaType mediaType = MediaType.parse(contentType);
        Request request = requestBuilder.put(RequestBody.create(mediaType, file)).build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            log.error("do post error, url={}", url, e);
        }
        return null;
    }
    
    /**
     * put上传文件
     * @param url
     * @param headers
     * @param file
     * @param contentType
     * @return
     */
    public static Response putFile(String url, Map<String, String> headers, byte[] file, String contentType) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        MediaType mediaType = MediaType.parse(contentType);
        Request request = requestBuilder.put(RequestBody.create(mediaType, file)).build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            log.error("do post error, url={}", url, e);
        }
        return null;
    }
}
