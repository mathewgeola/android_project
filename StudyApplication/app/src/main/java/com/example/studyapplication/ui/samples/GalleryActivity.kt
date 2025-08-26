package com.example.studyapplication.ui.samples

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.studyapplication.R

class GalleryActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tvIndex: TextView
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton

    private val urls = (1..10).map { "https://reqres.in/img/faces/${it}-image.jpg" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        viewPager = findViewById(R.id.viewPager)
        tvIndex = findViewById(R.id.tvIndex)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)

        val adapter = PhotoPagerAdapter(urls)
        viewPager.adapter = adapter

        updateIndex(0)

        // 滑动监听
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndex(position)
            }
        })

        // 左右按钮
        btnPrev.setOnClickListener {
            val prev = viewPager.currentItem - 1
            if (prev >= 0) viewPager.currentItem = prev
        }

        btnNext.setOnClickListener {
            val next = viewPager.currentItem + 1
            if (next < urls.size) viewPager.currentItem = next
        }
    }

    private fun updateIndex(position: Int) {
        "${position + 1}/${urls.size}".also { tvIndex.text = it }
    }
}
