package com.altcoin.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.MediaType;

import com.altcoin.base.vo.UserHeader;

public class Encoding {

    public static Map<String, Object> coinoneEncoding(Map<String, Object> params, UserHeader header){
        HttpUtil.changeNonce(params);

        String payload = Base64.encodeBase64String(params.toString().getBytes());
        String signature = Encryptor.getHmacSha512(header.getCoinoneSecert().toUpperCase(), payload).toLowerCase();

        Map<String, Object> data = new HashMap<String, Object>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("content-type", MediaType.APPLICATION_JSON_VALUE);
        map.put("accept", MediaType.APPLICATION_JSON_VALUE);
        map.put("X-COINONE-PAYLOAD", payload);
        map.put("X-COINONE-SIGNATURE", signature);

        data.put("map", map);
        data.put("payload", payload);

        return data;
    }

    public static Map<String, Object> poloniexEncoding(Map<String, Object> params, UserHeader header, String urlCode){
        HttpUtil.changeNonce(params);

        Map<String, Object> data = new HashMap<String, Object>();

        String param = "nonce=" + HttpUtil.getNonceFull() + urlCode;

        Map<String, String> map = new HashMap<String, String>();
        map.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        map.put("Key", (String)params.get("access_token"));
        map.put("Sign", Encryptor.getHmacSha512(header.getPoloniexSecert(), param));

        data.put("map", map);
        data.put("param", param);

        return data;
    }

}
