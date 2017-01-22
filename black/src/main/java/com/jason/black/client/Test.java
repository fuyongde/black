package com.jason.black.client;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * Created by fuyongde on 2017/1/20.
 */
public class Test {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("a");
        }
        String result = String.join(",", list);
        System.out.println(result);
        List<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6, 6);
        long count = nums.stream().filter(num -> !Objects.isNull(num)).count();
        System.out.println(count);

        nums.stream().filter(num -> !Objects.isNull(num)).forEach(System.out::print);

        System.out.println("");

        nums.stream().distinct().filter(num->!Objects.isNull(num)).forEach(System.out::print);

        
    }
}

