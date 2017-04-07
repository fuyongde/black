package com.jason.black.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.black.domain.param.RegisterParam;
import com.jason.black.handler.ErrorResult;
import okhttp3.*;
import okhttp3.MediaType;
import org.springframework.http.*;

import java.io.IOException;

/**
 * Created by fuyongde on 2016/12/29.
 */
public class PostExample {
    public static final MediaType JSON = MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE);

    OkHttpClient client = new OkHttpClient();

    Response post(String url) throws IOException {
        RegisterParam user = new RegisterParam();
        user.setUsername("fuyongqi");
        user.setPassword("fuyongqi");

        ObjectMapper mapper = new ObjectMapper();
        String jsonParam = mapper.writeValueAsString(user);

        RequestBody formBody = new FormBody.Builder().add("username", "fuhongwei").add("password", "fuhongwei").build();
        RequestBody jsonBody = RequestBody.create(JSON, jsonParam);

        Request request = new Request.Builder()
                .url(url)
                .post(jsonBody)
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }

    public static void main(String[] args) throws IOException {
        PostExample example = new PostExample();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Response response = example.post("http://localhost:8080/black/api/users");
        if (response.isSuccessful()) {
            String location = response.header("Location");
            System.out.println(location);
        } else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()){
            String error = response.body().string();
            ErrorResult errorResult = mapper.readValue(error, ErrorResult.class);
            System.out.println(errorResult);
        }
    }
}
