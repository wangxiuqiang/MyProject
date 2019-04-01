package com.test.redis.pojo;


public class User {

    public String name;
    public long id;
    public String phone;

    /**
     * 使用静态工厂方法创建类对象
     * @return
     */
    public User() {}

    public static User newIntance() {
        return new User();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
