package com.lzl.ossservice.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LZLUtils {

    public static String encodeUrl(String url) {
        String path = url.substring(0,url.lastIndexOf("/")+1);
        String query = url.substring(url.lastIndexOf("/")+1);

        try {

            String encodeQuery = URLEncoder.encode(query, "UTF-8");

            return path + encodeQuery;

        } catch (UnsupportedEncodingException e) {

            return "Issue while encoding" + e.getMessage();

        }
    }


}
