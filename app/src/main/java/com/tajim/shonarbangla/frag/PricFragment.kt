package com.tajim.shonarbangla.frag

import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.tajim.shonarbangla.HistoryActivity
import com.tajim.shonarbangla.MainActivity
import com.tajim.shonarbangla.databinding.FragmentPricBinding
import com.tajim.shonarbangla.others.BaseFragment
import com.tajim.shonarbangla.others.SQLiteHelper
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class PricFragment : BaseFragment() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var binding: FragmentPricBinding
    private var first1 = true
    private var first2 = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPricBinding.inflate(inflater, container, false)

        showInformation(0, true, true)
        implementGraph()
        handleSpinner()

        binding.tvDate.setOnClickListener {
            startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }

        return binding.root
    }

    private fun showInformation(which: Int, isGold: Boolean, isBd: Boolean) {
        sqLiteHelper = SQLiteHelper(requireContext())
        val priceCursor: Cursor = sqLiteHelper.getData()
        if (priceCursor.moveToLast()) {
            val price_k22 = priceCursor.getString(1).toInt()
            val k21_price = priceCursor.getString(2).toInt()
            val k18_price = priceCursor.getString(3).toInt()

            val price_k22S = priceCursor.getString(4).toInt()
            val k21_priceS = priceCursor.getString(5).toInt()
            val k18_priceS = priceCursor.getString(6).toInt()

            val price_k22Saudi = priceCursor.getString(8).toFloat()
            val k21_priceSaudi = priceCursor.getString(9).toFloat()
            val k18_priceSaudi = priceCursor.getString(10).toFloat()

            val date = priceCursor.getString(7)

            var price: Float = when {
                which == 0 && isGold && isBd -> price_k22.toFloat()
                which == 1 && isGold && isBd -> k21_price.toFloat()
                which == 2 && isGold && isBd -> k18_price.toFloat()
                which == 0 && !isGold && isBd -> price_k22S.toFloat()
                which == 1 && !isGold && isBd -> k21_priceS.toFloat()
                which == 2 && !isGold && isBd -> k18_priceS.toFloat()
                which == 0 && isGold && !isBd -> price_k22Saudi
                which == 1 && isGold && !isBd -> k21_priceSaudi
                which == 2 && isGold && !isBd -> k18_priceSaudi
                else -> 0f
            }

            price += 0.00F

            val k22_vori = price * 11.664f
            val k22_ana = k22_vori / 16
            val k22_roti = k22_ana / 6
            val k22_point = k22_roti / 10

            binding.tvDisplay.text = Html.fromHtml(
                """
                <b>প্রতি ভরি:</b> ${floatToBengali(k22_vori)} ৳<br>
                <b>প্রতি আনা:</b> ${floatToBengali(k22_ana)} ৳<br>
                <b>প্রতি রতি:</b> ${floatToBengali(k22_roti)} ৳<br>
                <b>প্রতি পয়েন্ট:</b> ${floatToBengali(k22_point)} ৳<br>
                <b>প্রতি গ্রাম:</b> ${floatToBengali(price)} ৳
            """.trimIndent()
            )

            binding.tvDate.text = Html.fromHtml("তারিখ: $date <b><u>আগের তথ্য দেখুন</u></b>")

            MainActivity.PRICE = k22_vori

            priceCursor.close()
        }
    }

    private fun implementGraph() {
        val cursor = sqLiteHelper.getData()
        val entries1 = ArrayList<Entry>()
        val entries2 = ArrayList<Entry>()
        val dates = ArrayList<String>()

        var index = 0
        var chartIndex = 0
        while (cursor.moveToNext()) {
            if (index % 7 == 0) {
                entries1.add(Entry(chartIndex.toFloat(), cursor.getString(1).toFloat()))
                entries2.add(Entry(chartIndex.toFloat(), cursor.getString(2).toFloat()))
                dates.add(fixDate(cursor.getString(3)))
                chartIndex++
            }
            index++
            if (index > 35) break
        }
        cursor.close()

        val dataSet1 = LineDataSet(entries1, "২২ ক্যারেট স্বর্ণ").apply {
            color = android.graphics.Color.BLUE
            setDrawCircles(false)
            setDrawValues(false)
        }
        val dataSet2 = LineDataSet(entries2, "২১ ক্যারেট স্বর্ণ").apply {
            color = android.graphics.Color.RED
            setDrawCircles(false)
            setDrawValues(false)
        }

        val lineData = LineData()
        lineData.addDataSet(dataSet1)
        lineData.addDataSet(dataSet2)

        binding.chart.data = lineData
        binding.chart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(dates)
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
        }
        binding.chart.description.isEnabled = false
        binding.chart.axisRight.isEnabled = false
        binding.chart.invalidate()
    }

    private fun floatToBengali(value: Float): String {
        val symbols = DecimalFormatSymbols(Locale.ENGLISH).apply { groupingSeparator = ',' }
        val df = DecimalFormat("#,##,###.##", symbols).apply { isGroupingUsed = true }
        val formatted = df.format(value)

        val banglaDigits = arrayOf('০','১','২','৩','৪','৫','৬','৭','৮','৯')
        return formatted.map { c ->
            if (c in '0'..'9') banglaDigits[c - '0'] else c
        }.joinToString("")
    }

    companion object {
        fun fixDate(date: String): String {
            var formatted = date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    val date1 = LocalDate.parse(date)
                    val formatter = DateTimeFormatter.ofPattern("d MMM")
                    formatted = date1.format(formatter).uppercase()
                } catch (e: Exception) { e.printStackTrace() }
            }
            return formatted
        }
    }

    private fun handleSpinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (first1) { first1 = false; return }
                startLoading()
                Handler().postDelayed({
                    refresh()
                    endLoading()
                }, 250)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val listener2 = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (first2) { first2 = false; return }
                startLoading()
                Handler().postDelayed({
                    refresh()
                    endLoading()
                }, 250)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinner2.onItemSelectedListener = listener2
        binding.spinner3.onItemSelectedListener = listener2
    }

    private fun refresh() {
        val isBd = binding.spinner3.selectedItemId == 0L
        val isGold = binding.spinner2.selectedItemId == 0L
        showInformation(binding.spinner.selectedItemId.toInt(), isGold, isBd)
    }
}
