package com.example.studyapplication.generics;

public class GenericsDemo {
    public static class Wrapper<T> {
        public Wrapper() {
        }

        public Wrapper(T value) {
            this.value = value;
        }

        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        static public <T, V, S> T someMethod(V t, S s) {
            return null;
        }
    }

    public static class WrapperNoGeneric {
        private Object value;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }


//    public static void f1(Wrapper<Integer> value) {
//    }
//
//    public static void f1(Wrapper<Long> value) {
//    }


    public interface GetValue<T> {
        T getValue();
    }

    public static class GetValueImpl implements GetValue<Integer> {

        @Override
        public Integer getValue() {
            return 0;
        }
    }

    public <T, V, S> T someMethod(V t, S s) {
        return null;
    }

    // 泛型的上下边界
    public static void showNumberValue(Wrapper<? extends Number> numberWrapper) {
        System.out.println(numberWrapper.getValue().intValue());
    }
}
