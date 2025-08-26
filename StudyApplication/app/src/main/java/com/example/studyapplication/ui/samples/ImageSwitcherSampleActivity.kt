package com.example.studyapplication.ui.samples

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.studyapplication.R
import com.example.studyapplication.databinding.ActivityImageSwitcherSampleBinding

class ImageSwitcherSampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageSwitcherSampleBinding

    private lateinit var localImages: IntArray
    private lateinit var networkImages: List<String>

    private var localIndex = 0
    private var networkIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_switcher_sample)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityImageSwitcherSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initImages()

        initView()
    }

    private fun initImages() {
        localImages = intArrayOf(
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,
        )

        networkImages = (1..10).map { "https://reqres.in/img/faces/${it}-image.jpg" }
    }

    private fun initView() {
        // 设置工厂，决定内部显示什么控件
        binding.localImageSwitcher.setFactory {
            ImageView(this).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        }
        // 初始显示
        binding.localImageSwitcher.setImageResource(localImages[localIndex])
        // 点击切换下一张
        binding.localImageSwitcher.setOnClickListener {
            localIndex = (localIndex + 1) % localImages.size
            binding.localImageSwitcher.setImageResource(localImages[localIndex])
        }

        binding.networkImageSwitcher.setFactory {
            ImageView(this).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        }
        Glide.with(this).load(networkImages[networkIndex])
            .into(binding.networkImageSwitcher.currentView as ImageView)
        binding.networkImageSwitcher.setOnClickListener {
            networkIndex = (networkIndex + 1) % networkImages.size
            Glide.with(this).load(networkImages[networkIndex])
                .into(binding.networkImageSwitcher.currentView as ImageView)
        }
    }

}
