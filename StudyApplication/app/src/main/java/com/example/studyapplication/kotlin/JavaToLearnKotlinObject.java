package com.example.studyapplication.kotlin;

public class JavaToLearnKotlinObject {
    interface B {
        void fooB();
    }

    abstract static class C {
        abstract void fooC1();
    }

    public static <T extends C & B> void foo(T t) {
        t.fooB();
        t.fooC1();
    }

    static class Inner extends C implements B {
        @Override
        public void fooB() {

        }

        @Override
        void fooC1() {

        }
    }

    static class HttpManagerJava {
        static void request() {

        }
    }

    public static void main(String[] args) {
        foo(new Inner() {
            @Override
            public void fooB() {
                super.fooB();
            }

            @Override
            void fooC1() {
                super.fooC1();
            }
        });

        HttpManagerJava.request();

        HttpManager.INSTANCE.request();
    }
}
