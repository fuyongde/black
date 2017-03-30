package com.jason.black.client;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        double a = 1245.345d;
        double b = 1.0d;
        double c = -23.0d;
        double d = -123.23d;

        System.out.println("");

        System.out.println(String.valueOf(a));
        System.out.println(String.valueOf(b));
        System.out.println(String.valueOf(c));
        System.out.println(String.valueOf(d));

        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

        System.out.println("a : " + isWholeNumber(decimalFormat.format(a)));
        System.out.println("b : " + isWholeNumber(decimalFormat.format(b)));
        System.out.println("c : " + isWholeNumber(decimalFormat.format(c)));
        System.out.println("d : " + isWholeNumber(decimalFormat.format(d)));
    }

    private static boolean isMatch(String regex, String orginal){
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }
}

