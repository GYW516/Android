package com.gyw.Utils;

import com.google.gson.Gson;
import com.gyw.config.Config;
import com.gyw.entity.Result;
import com.gyw.logcat.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * author:Created by 郭永维.
 * http工具类
 */
public class HttpUtils {
    static Gson gson = new Gson();

    /**
     * 发送消息到服务器
     *
     * @param message ：发送的消息
     * @return：消息对象
     */
    public static String sendMessage(String message) {
        String gsonResult = doGet(message);
        LogUtils.LogD("Debug",gsonResult);
        String msg = null;

        if (gsonResult != null) {
            try {
                String[] split = gsonResult.split("text");
                System.out.println(split[2]);
                int startIndex = split[2].indexOf(":") + 2;
                int lastIndex = split[2].lastIndexOf("\"");
                msg = split[2].substring(startIndex, lastIndex);
            } catch (Exception e) {
                msg = "服务器繁忙，请稍后再试！";
            }



        }

        return msg;
    }

    /**
     * get请求
     *
     * @param message ：发送的话
     * @return：数据
     */
    public static String doGet(String message) {
        String result = "";
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL urls = new URL("http://openapi.turingapi.com/openapi/api/v2");
            HttpURLConnection connection = (HttpURLConnection) urls
                    .openConnection();
//            connection.setReadTimeout(5 * 1000);
//            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            //json格式请求体
            JSONObject requestBody = buildRequestBody(message);
            //发送请求
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            //获取响应
            is = connection.getInputStream();
            baos = new ByteArrayOutputStream();
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            baos.flush();
            result = new String(baos.toByteArray(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 设置参数
     *
     * @param message : 信息
     * @return ： url
     */
    private static String setParmat(String message) {
        String url = "";
        try {
            url = Config.URL_KEY + "?" + "key=" + Config.APP_KEY + "&info="
                    + URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    //构建请求体数据
    public static JSONObject buildRequestBody(String text) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("reqType", 0);

        JSONObject inputText = new JSONObject();
        inputText.put("text", text);

        JSONObject perception = new JSONObject();
        perception.put("inputText", inputText);

        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey", "cddd4a80552c403dbf5c054a5cd8fb0b");
        userInfo.put("userId", "");

        requestBody.put("perception", perception);
        requestBody.put("userInfo", userInfo);

        return requestBody;
    }


}
