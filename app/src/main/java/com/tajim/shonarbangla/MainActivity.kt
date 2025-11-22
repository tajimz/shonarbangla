package com.tajim.shonarbangla

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayout
import com.tajim.shonarbangla.adapter.ViewPager2
import com.tajim.shonarbangla.databinding.ActivityMainBinding
import com.tajim.shonarbangla.databinding.AlertCustomBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var pagerAdapter: ViewPager2

    companion object {
        var PRICE: Float = 0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView)
            .setAppearanceLightStatusBars(false)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pagerAdapter = ViewPager2(this)
        setupViewpager()
        setup()
    }

    private fun setupViewpager() {
        binding.pager.adapter = pagerAdapter

        binding.tabLay.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.pager.registerOnPageChangeCallback(object : androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLay.getTabAt(position)?.select()
            }
        })
    }

    private fun setup() {
        binding.imgI.setOnClickListener {
            val alertBinding = AlertCustomBinding.inflate(layoutInflater)

            val alertDialog = AlertDialog.Builder(this)
                .setView(alertBinding.root)
                .setCancelable(true)
                .create()

            alertDialog.show()

            val window: Window? = alertDialog.window
            if (window != null) {
                val params = window.attributes
                params.width = (340 * resources.displayMetrics.density).toInt()
                window.attributes = params
            }

            alertBinding.declineCus.setOnClickListener {
                openUrl("https://www.facebook.com/trtajim/")
                alertDialog.dismiss()
            }

            alertBinding.allowCus.setOnClickListener {
                openUrl("http://localhost")
                alertDialog.dismiss()
            }
        }
    }

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open link", Toast.LENGTH_SHORT).show()
        }
    }
}
