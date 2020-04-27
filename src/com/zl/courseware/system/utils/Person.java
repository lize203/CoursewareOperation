package com.zl.courseware.system.utils;

import java.util.concurrent.TimeUnit;

public class Person {
    int age = 0;
    private static Person p = null;

    private Person() {

    }
    public static synchronized Person getInstance() {
        if (p == null) {
            synchronized (Person.class) {
                if (p == null) {
                    p = new Person();
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    p.age = 5;
                }
            }
        }
        return p;
    }

    public static void main(String[] as) {
        for (int i=0;i<100;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(getInstance().age);
                }
            }).start();
        }
    }
}