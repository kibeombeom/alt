package com.altcoin.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class HttpUtil {

    public static String requestGet(String strUrl, String accept) throws Exception {
        URL url = new URL(strUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", accept);
        conn.setConnectTimeout(5000);
        conn.connect();

        if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
//            throw new Exception();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static String requestPost(String strUrl, Map<String, String> reqProps, String params) throws Exception {
        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        if(reqProps != null)
            for(String key : reqProps.keySet()) {
                conn.setRequestProperty(key, reqProps.get(key));
            }
        conn.setConnectTimeout(5000);

        params = (params == null ? "" : params);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static JSONObject getJSONfromGet(String apiUrl) throws Exception {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(requestGet(apiUrl, "application/json"));
    }

    public static JSONObject getJSONfromPost(String apiUrl, Map<String, String> reqProps, String params) throws Exception {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(requestPost(apiUrl, reqProps, params));
    }

    public static String paramsBuilder(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        for(String key : map.keySet()) {
            builder.append(key);
            builder.append("=");
            builder.append(map.get(key));
            builder.append("&");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    public static JSONObject settingApiKeys(Map<String, Object> obj){
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("access_token", obj.get("access_token"));
        jsonObj.put("nonce", getNonce());

        return jsonObj;
    }

    public static String getNonce(){
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String getNonceFull(){
        return String.valueOf(System.currentTimeMillis());
    }

    public static Map<String, Object> changeNonce(Map<String, Object> params){
        String nonce = (String) params.get("nonce");

        if(nonce.length() == 10){
            params.put("nonce", getNonce());
        } else {
            params.put("nonce", getNonceFull());
        }

        return params;
    }
    
    public static boolean isNullCheck(String str){
    	if(str != null && !str.trim().equals("") && str.trim().length() != 0)
    		return true;
    	else
    		return false;
    }

}
