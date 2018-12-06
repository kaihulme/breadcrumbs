package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;

import java.util.concurrent.CopyOnWriteArrayList;


public class TestUserList {
    private static final CopyOnWriteArrayList<User> testList = new CopyOnWriteArrayList<>();
    static {
        User john = new User(1,"John","Adam","JA@gmail.com");
        User harry = new User(2,"Harry","Calway","HC@hotmail.com");
        User ashley = new User(3,"Ashley","Johnson","AJ@yahoo.com");
        User loretta = new User(4,"Lorreta","Andrews","LA@gmail.com");
        //testList = testList.addAll(Arrays.asList(john,harry,ashley,loretta));
        testList.add(john);
        testList.add(harry);
        testList.add(ashley);
        testList.add(loretta);
    }

    public static CopyOnWriteArrayList getTestUser(){
        return testList;
    }

}
