package com.tajim.shonarbangla

import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tajim.shonarbangla.adapter.HistoryAdapter
import com.tajim.shonarbangla.databinding.ActivityHistoryBinding
import com.tajim.shonarbangla.others.SQLiteHelper

class HistoryActivity : AppCompatActivity() {

    lateinit var sqLiteHelper: SQLiteHelper
    lateinit var list: ArrayList<HashMap<String, String>>
    lateinit var map: HashMap<String, String>
    lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView)
            .setAppearanceLightStatusBars(false)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list = ArrayList()
        sqLiteHelper = SQLiteHelper(this)
        val cursor: Cursor = sqLiteHelper.getData()

        while (cursor.moveToNext()) {
            map = HashMap()
            map["date"] = cursor.getString(7)
            map["c22_bd"] = cursor.getString(1)
            map["c21_bd"] = cursor.getString(2)
            map["c18_bd"] = cursor.getString(3)
            map["c22_sa"] = cursor.getString(8)
            map["c21_sa"] = cursor.getString(9)
            map["c18_sa"] = cursor.getString(10)

            list.add(map)
        }

        setup()
    }

    private fun setup() {
        val historyAdapter = HistoryAdapter(this, list)
        binding.recycler.adapter = historyAdapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

        binding.imgArrowBack.setOnClickListener { onBackPressed() }
        binding.imgDownload.setOnClickListener {
            Toast.makeText(this, "Download in excel format, coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}
