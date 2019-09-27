package com.slplex.ziro.login;

import android.content.Context;

import com.slplex.ziro.home.ToDoModel;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class LoginPresenter  {
    interface  LoginView{
        void login(User user);
        void showBody();
        void hideBody();
        void showProgess();
        void hideProgress();
        void haveUser(User haveuser);
    }
    LoginView loginView;

    public LoginPresenter(LoginView loginView, Context context) {
        this.loginView = loginView;
        Realm.init(context);
        loginView.haveUser(this.haveUser());
    }

    void login(User user)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
        loginView.login(user);

    }
    User haveUser()
    {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<User> list = realm.where(User.class).findAll();
        if(list.size()>0)
        {
            return list.get(0);
        }
        else {
            return null;
        }
        //return  null;
    }


}
