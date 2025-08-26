package com.example.studyapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studyapplication.data.RegisterTokenBean
import com.example.studyapplication.data.SingleUserBean
import com.example.studyapplication.databinding.ActivityKotlinBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

data class RegisterRequest(val email: String, val password: String)

interface ReqResService {
    @POST("/api/register")
    @Headers("x-api-key: reqres-free-v1")
    suspend fun register(@Body request: RegisterRequest): RegisterTokenBean

    @GET("/api/users/{userId}")
    @Headers("x-api-key: reqres-free-v1")
    suspend fun getSingleUser(@Path("userId") userId: Int): SingleUserBean

    @GET("/img/faces/{avatar}")
    suspend fun getAvatar(@Path("avatar") avatar: String): ResponseBody
}

class KotlinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKotlinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kotlin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun reInitView() {
        binding.responseStatusTv.text = ""
        binding.responseContentTv.text = ""
        binding.nameTv.text = ""
        binding.signTv.text = ""
        binding.avatarIv.setImageResource(R.mipmap.ic_launcher)
    }

    private fun retrofitPost() {
        val service = Retrofit.Builder().baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ReqResService::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                binding.responseStatusTv.text = "注册中..."

                val registerResponse = withContext(Dispatchers.IO) {
                    service.register(RegisterRequest("eve.holt@reqres.in", "pistol"))
                }
                binding.responseStatusTv.text = "获取注册完成"
                "获取到注册 token: ${registerResponse.token}, 注册 ID: ${registerResponse.id}".also {
                    binding.responseContentTv.text = it
                }

                val userResponse = withContext(Dispatchers.IO) {
                    service.getSingleUser(registerResponse.id)
                }
                binding.responseStatusTv.text = "获取信息完成"
                "${userResponse.data.firstName}·${userResponse.data.lastName}".also {
                    binding.nameTv.text = it
                }
                binding.signTv.text = userResponse.support.text
                val avatar = userResponse.data.avatar.split("/").last()

                val avatarResponse = withContext(Dispatchers.IO) {
                    service.getAvatar(avatar)
                }
                binding.responseStatusTv.text = "获取图片完成"
                val bitmap = BitmapFactory.decodeStream(avatarResponse.byteStream())
                binding.avatarIv.setImageBitmap(bitmap)

                binding.responseStatusTv.text = "获取完成"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun initView() {
        binding.requestBtn.setOnClickListener {
            reInitView()
            retrofitPost()
        }
    }
}