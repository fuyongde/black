package com.jason.black.utils;

import com.google.common.collect.Lists;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by fuyongde on 2017/3/13.
 */
public class HttpHelper {

    public static final MediaType JSON = MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE);

    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    public static Response get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response get(String url, Map<String, String> paramMap) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String paramUrl = null;
        if (!CollectionUtils.isEmpty(paramMap)) {
            List<String> params = Lists.newArrayList();
            paramMap.entrySet().forEach(entry -> {
                params.add(entry.getKey() + "=" + entry.getValue());
            });
            paramUrl = String.join("&", params);
        }

        if (StringUtils.isNotBlank(paramUrl)) {
            url = url.concat("?").concat(paramUrl);
        }

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response post(String url, Map<String, String> paramMap) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(paramMap)) {
            paramMap.entrySet().forEach(entry -> {
                builder.add(entry.getKey(), entry.getValue());
            });
        }

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }

    public static Response postJson(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody jsonBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(jsonBody)
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }

}
