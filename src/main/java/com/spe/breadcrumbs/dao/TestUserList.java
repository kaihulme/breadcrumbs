package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;

import java.util.concurrent.CopyOnWriteArrayList;
import java.lang.Long;


public class TestUserList {
    private static final CopyOnWriteArrayList<User> testList = new CopyOnWriteArrayList<>();
    static {
        User john = new User(new Long("1"),"John","Adam","JA@gmail.com");
        User harry = new User(new Long("2"),"Harry","Calway","HC@hotmail.com");
        User ashley = new User(new Long("3"),"Ashley","Johnson","AJ@yahoo.com");
        User loretta = new User(new Long("4"),"Lorreta","Andrews","LA@gmail.com");
        //testList = testList.addAll(Arrays.asList(john,harry,ashley,loretta));
        testList.add(john);
        testList.add(harry);
        testList.add(ashley);
        testList.add(loretta);
    }

    public static CopyOnWriteArrayList<User> getTestUsers(){
        return testList;
    }

}
