package com.jason.utils;

import com.google.common.base.Strings;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fuyongde on 2016/8/31.
 */
public class Identities {
    private static SecureRandom random = new SecureRandom();
    private static Clock clock = Clock.DEFAULT;
    private static AtomicInteger atomicInteger;

    private static String[] majuscules = new String[]{
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    private static String[] chars = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    static {
        //类首次加载的时候，取毫秒数
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.MILLISECOND);
        atomicInteger = new AtomicInteger(i);
    }

    /**
     * 获取int类型的值
     *
     * @return
     */
    private static synchronized int getAtomicInteger() {
        if (atomicInteger.get() > 999) {
            atomicInteger.set(0);
        }

        return atomicInteger.getAndIncrement();
    }

    /**
     * 补位
     *
     * @param number 待补位的数
     * @param length 补位的长度
     * @return
     */
    private static String fillDigit(int number, int length) {
        String str = String.valueOf(number);
        if (str.length() > length) {
            return str;
        }

        while (str.length() < length) {
            str = "0".concat(str);
        }

        return str;
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 产生一个sku（该方法并发量1000）
     *
     * @param category 品类
     * @param batch    批次
     * @return
     */
    public static String sku(String category, String batch) {
        //获取以秒为单位的时间戳
        long timestamp = clock.getTimestamp(Clock.Type.SECOND);
        //获取时间戳后四位
        long lastFour = timestamp % 10000;
        //获取时间戳的前几位
        long first = timestamp / 10000;

        first = 0xFFFFF & first;
        StringBuffer sku = new StringBuffer();
        for (int k = 0; k < 5; k++) {
            long i = 0xF & first;
            sku.append(majuscules[new Long(i).intValue()]);
            first = first >> 4;
        }

        sku.append(fillDigit(new Long(lastFour).intValue(), 4));
        sku.append(fillDigit(getAtomicInteger(), 3));
        if (!Strings.isNullOrEmpty(batch)) {
            sku.insert(0, batch);
        }
        if (!Strings.isNullOrEmpty(category)) {
            sku.insert(0, category);
        }

        return sku.toString();
    }

    /**
     * 产生一个随机id（该方法并发量1000）
     *
     * @return
     */
    public static String genId() {
        long timestamp = clock.getTimestamp(Clock.Type.SECOND);
        timestamp = 0xFFFFFFF & timestamp;
        StringBuffer genId = new StringBuffer();
        for (int k = 0; k < 7; k++) {
            long i = 0x1F & timestamp;
            genId.append(chars[new Long(i).intValue()]);
            timestamp = timestamp >> 5;
        }

        int x = getAtomicInteger();
        genId.append(chars[0x1F & x]);
        x = x >> 5;
        genId.append(chars[0x1F & x]);
        return genId.toString();
    }
}
