package com.example.studyapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class JavaToKotlinActivity : AppCompatActivity() {
    class User(val username: String, val desc: String)

    fun User.customApply(block: User.() -> Unit): User {
//        block(this) // 编译器会把 this 作为接收者
        this.block()
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_java_to_kotlin)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View?>(R.id.main),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
                v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            })

        val user: User? = null
        if (user != null) {
//            tvUser.setText(user.username)
//            tvDesc.setText(user.desc)
        }

        user?.apply {
//            tvUser.setText(username)
//            tvDesc.setText(desc)
        }

        user?.customApply {
//            tvUser.setText(username)
//            tvDesc.setText(desc)
        }

        user?.also {
            println(it)
        }

        findViewById<TextView>(R.id.textview).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
            }
        })

        findViewById<TextView>(R.id.textview).setOnClickListener { }
    }
}