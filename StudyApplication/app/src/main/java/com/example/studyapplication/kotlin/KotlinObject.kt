package com.example.studyapplication.kotlin

// class
class A {
    fun fooA() {
        println("call fooA")
    }
}

// interface
interface B {
    fun fooB()
}

// abstract class
abstract class C {
    abstract fun fooC1()

    fun fooC2() {

    }
}


// object 创建类的同时创建了对象

// 1. 匿名对象 匿名内部类
fun <T> foo(t: T) where T : C, T : B {
    t.fooB()
    t.fooC1()
}


// 单例
// 1. 无法实现懒汉模式
// 2. 无法 getInstance() 参数
object HttpManager {
    fun request() {
        println("request")
    }
}

class HttpHelper {
    object InnerObject1 {

        fun foo1() {
        }

        @JvmStatic
        fun foo2() {
        }


    }

//    companion object InnerObject2 {
//
//        fun func1() {
//        }
//
//        @JvmStatic
//        fun func2() {
//        }
//    }

    companion object {

        fun func1() {
        }

        @JvmStatic
        fun func2() {
        }
    }

}

class RealMoonCake private constructor(name: String) {
    companion object {
        fun create(name: String): RealMoonCake {
            println("create $name")
            return RealMoonCake(name)
        }
    }
}

// 委托实现单例的懒加载
object MoonCakeManager {
    val instance by lazy {
        initDevice()
    }

    private fun initDevice(): RealMoonCake {
        return RealMoonCake.create("五仁月饼")
    }

    fun packageMoonCake() {
        println("package MoonCake")
    }
}

class HttpManagerByCompanion private constructor(uri: String) {
    companion object {
        @Volatile
        private var INSTANCE: HttpManagerByCompanion? = null

        fun getInstance(uri: String): HttpManagerByCompanion {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: HttpManagerByCompanion(uri).also {
                    INSTANCE = it
                }
            }
        }
    }

    fun login() {
        println("login: $INSTANCE")
    }
}

// 抽象类
abstract class AbcSingleton<in P, out T> {
    @Volatile
    private var instance: T? = null

    protected abstract fun creator(p: P): T

    fun getInstance(p: P): T {
        return instance ?: synchronized(this) {
            instance ?: creator(p).also {
                instance = it
            }
        }
    }
}

class LocalStorageManager private constructor(path: String) {
    companion object : AbcSingleton<String, LocalStorageManager>() {
        override fun creator(p: String): LocalStorageManager {
            return LocalStorageManager(p)
        }
    }
}


class HttpManagerByAbc private constructor(path: String) {
    companion object : AbcSingleton<String, HttpManagerByAbc>() {
        override fun creator(p: String): HttpManagerByAbc {
            return HttpManagerByAbc(p)
        }
    }
}

fun main() {
    A()

    val item = object : C(), B {
        override fun fooB() {
        }

        override fun fooC1() {

        }
    }
    foo(item)

    HttpManager.request()

    HttpHelper.InnerObject1.foo1()

    HttpHelper.func1()

    println(MoonCakeManager.instance)
    MoonCakeManager.packageMoonCake()

    val instance = HttpManagerByCompanion.getInstance("https://www.example.com")
    println(instance)
    instance.login()


    val storage1 = LocalStorageManager.getInstance("/data/local")
    val storage2 = LocalStorageManager.getInstance("/data/local")

    println(storage1 === storage2)

    val instance1 = HttpManagerByAbc.getInstance("https://www.example.com")
    val instance2 = HttpManagerByAbc.getInstance("https://www.example.com")
    println(instance1 === instance2)
}