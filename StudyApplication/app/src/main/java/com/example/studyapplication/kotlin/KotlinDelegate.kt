package com.example.studyapplication.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface MoonCake {
    fun make()
}

class Chef(private val name: String) : MoonCake {
    override fun make() {
        println("Chef $name make moon")
    }
}

class DelegateMoonCakeMaker(private val chef: Chef) : MoonCake by chef


class DelegateName {
    private var value: String = "default"

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "getValue ${property.name} = ${this.value}"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("setValue ${property.name} = $value")
        this.value = value
    }
}

class DelegateProperty {
    var name: String by DelegateName()
}

// ReadOnlyProperty(val) ReadWriteProperty(var)
// val 代理的实现
class DelegateProperty1 : ReadOnlyProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, 这里委托了 ${property.name} 属性"
    }
}

// var 代理的实现
class DelegateProperty2 : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, 这里委托了 ${property.name} 属性"
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("setValue $value")
    }
}


class Test {
    val name: String by DelegateProperty1()
    var job: String by DelegateProperty2()
}

// 1. lazy
val lazyProperty: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    println("执行了lazy")
    "something"
}

// 2. observable
var observableProperty: String by Delegates.observable("初始值") { property, oldValue, newValue ->
    println("property: $property, oldValue: $oldValue, newValue: $newValue")
}

// 2. vetoable 可以拒绝赋值
var vetoableProperty: Int by Delegates.vetoable(10) { property, oldValue, newValue ->
    println("property: $property, oldValue: $oldValue, newValue: $newValue")
    newValue > oldValue
}


fun main() {
    val chef = Chef("tom")
    val delegateMoonCakeMaker = DelegateMoonCakeMaker(chef)
    delegateMoonCakeMaker.make()

    DelegateProperty().name = "example"
    println(DelegateProperty().name)

    println(Test().name)
    println(Test().job)
    Test().job = "Worker"

    println(lazyProperty)
    println(lazyProperty)
    println(lazyProperty)
    println(lazyProperty)

    println(observableProperty)
    observableProperty = "newValue"
    println(observableProperty)
    observableProperty = "newValue2"
    println(observableProperty)

    println(vetoableProperty)
    vetoableProperty = 20
    println(vetoableProperty)
    vetoableProperty = 5
    println(vetoableProperty)
    vetoableProperty = 100
    println(vetoableProperty)
}