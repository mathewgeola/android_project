package com.example.studyapplication.generics

class Wrapper1<T> {
    fun getValue(): T {
        return 1 as T;
    }

    fun setValue(t: T) {

    }
}

class Wrapper2<out T> {
    fun getValue(): T {
        return 1 as T;
    }
}

class Wrapper3<in T> {
    fun setValue(t: T) {

    }
}

fun main() {
    val w1: Wrapper1<out Number> = Wrapper1<Long>()
    val w2: Wrapper1<in Long> = Wrapper1<Number>()

    val w3: Wrapper2<Number> = Wrapper2<Long>()

    val w4: Wrapper3<Long> = Wrapper3<Number>()
}