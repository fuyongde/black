package com.jason.black.client;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by fuyongde on 2017/1/22.
 */
public class User {
    int id;
    String name;
    int age = 18;
    int sex = 1;

    public User() {
    }

    public User(int id, String name, int age, int sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public static void main(String[] args) {
        User a = new User(1, "a", 20, 1);
        User b = new User(2, "b", 18, 0);
        User c = new User(3, "c", 27, 0);
        User d = new User(4, "d", 29, 1);

        List<User> users = Lists.newArrayList(a, b, c, d);

        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, (user -> user)));

        Map<Integer, List<User>> userGroupMap = users.stream().collect(Collectors.groupingBy(User::getSex));

        System.out.println("a");
    }
}
