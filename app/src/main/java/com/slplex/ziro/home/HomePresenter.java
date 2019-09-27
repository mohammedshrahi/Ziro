package com.slplex.ziro.home;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class HomePresenter {
    HomeView homeView;
    public HomePresenter(HomeView homeView, Context context) {
        this.homeView = homeView;
        Realm.init(context);
        inzlize();


    }

    public void addTodo(ToDoModel toDoModel) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(toDoModel);
        realm.commitTransaction();
        homeView.add(toDoModel);
    }

    public void updateTodo(ToDoModel toDoModel, int pos) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<ToDoModel>realmResults= realm.where(ToDoModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.get(pos).setTitle(toDoModel.getTitle());
                realmResults.get(pos).setDate(toDoModel.getDate());
                realm.copyToRealmOrUpdate(realmResults.get(pos));
                homeView.update(toDoModel,pos);

            }
        });
    }

    public void deleteTodo(ToDoModel toDoModel, int pos) {
        Realm realm= Realm.getDefaultInstance();
        RealmResults<ToDoModel>realmResults= realm.where(ToDoModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.get(pos).deleteFromRealm();
                homeView.delete(toDoModel, pos);

            }
        });

    }

    void inzlize() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<ToDoModel> list = realm.where(ToDoModel.class).findAll();
        List<ToDoModel> models = realm.copyFromRealm(list);
        homeView.inzlize(models);

    }


    public interface HomeView {
        void add(ToDoModel toDoModel);

        void delete(ToDoModel toDoModel, int pos);

        void update(ToDoModel toDoModel, int pos);

        void inzlize(List<ToDoModel> models);

    }
}
