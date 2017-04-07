package com.jason.black.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.black.domain.dto.RegionDTO;
import com.jason.black.handler.ErrorResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

/**
 * Created by fuyongde on 2016/12/28.
 */
public class GetExample {
    OkHttpClient client = new OkHttpClient();

    Response run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request).execute();
    }

    public static void main(String[] args) throws IOException {
        GetExample example = new GetExample();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Response response = example.run("http://localhost:8080/black/api/users/test");
        if (response.isSuccessful()) {
            String result = response.body().string();
            JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, RegionDTO.class);
            List<RegionDTO> regionDTOS = mapper.readValue(result, javaType);
            regionDTOS.forEach(System.out::print);
        } else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()){
            String error = response.body().string();
            ErrorResult errorResult = mapper.readValue(error, ErrorResult.class);
            System.out.println(errorResult);
        }

    }
}
