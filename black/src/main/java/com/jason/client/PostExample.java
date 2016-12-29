package com.jason.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.dto.RegionDto;
import com.jason.handler.ErrorResult;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

/**
 * Created by fuyongde on 2016/12/29.
 */
public class PostExample {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Response post(String url) throws IOException {
        RequestBody body = new FormBody.Builder().add("username", "chenmiaoshao").add("password", "chenmiaoshan").build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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
