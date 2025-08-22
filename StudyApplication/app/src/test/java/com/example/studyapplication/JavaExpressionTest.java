package com.example.studyapplication;

import org.junit.Test;

import java.util.Random;

public class JavaExpressionTest {
    int add(int a, int b) {
        return a + b;
    }

    public static class User {
        final String username;
        final String desc;

        public User(String username, String desc) {
            this.username = username;
            this.desc = desc;
        }
    }

    @Test
    public void test() {
        int nextInt = new Random().nextInt();
        if (nextInt % 2 == 0) {
            System.out.println("is even");
        } else {
            System.out.println("is odd");
        }

        String s = nextInt % 2 == 0 ? "is even" : "is odd";

        switch (nextInt % 2) {
            case 0:
                System.out.println("is even");
                break;
            default:
                System.out.println("is odd");
                break;
        }
    }
}
