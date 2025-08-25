package com.example.studyapplication;

import com.example.studyapplication.generics.GenericsDemo;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GenericsDemoTest {
    @Test
    public void test() {
        GenericsDemo.Wrapper<Integer> w1 = new GenericsDemo.Wrapper<>();
//        w1.setValue("");
        w1.setValue(1);
        Integer value = w1.getValue();
        System.out.println(value);

        GenericsDemo.Wrapper<Long> w2 = new GenericsDemo.Wrapper<>();
        System.out.println(w1.getClass().equals(w2.getClass()));

        GenericsDemo.WrapperNoGeneric w3 = new GenericsDemo.WrapperNoGeneric();
//        w3.setValue("111");
        w3.setValue(111);
        Integer value1 = (Integer) w3.getValue();
        System.out.println(value1);

        GenericsDemo.showNumberValue(w1);
        GenericsDemo.showNumberValue(new GenericsDemo.Wrapper<>(1));
        GenericsDemo.showNumberValue(new GenericsDemo.Wrapper<Long>(1L));
        GenericsDemo.showNumberValue(new GenericsDemo.Wrapper<BigDecimal>(BigDecimal.ONE));
//        GenericsDemo.showNumberValue((GenericsDemo.Wrapper) new GenericsDemo.Wrapper<String>(""));

        List<? extends Number> numberList = new ArrayList<Long>(); // 协变
        Number[] numbers = new Long[10];
        List<? super Long> longList = new ArrayList<Number>(); // 逆变
    }
}
