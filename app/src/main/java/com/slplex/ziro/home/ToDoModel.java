package com.slplex.ziro.home;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ToDoModel extends RealmObject {
    String date;
    String title;

    public String getDate() {
        return date;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
