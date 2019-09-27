package com.slplex.ziro.login;

import io.realm.RealmObject;

public class User extends RealmObject {
    String name,phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
