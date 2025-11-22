package com.tajim.shonarbangla

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tajim.shonarbangla.others.BaseActivity
import com.tajim.shonarbangla.others.SQLiteHelper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SplashActivity : BaseActivity() {

    lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sqLiteHelper = SQLiteHelper(this)
        getData()
    }

    private fun getData() {
        jsonArrayReq(false, "http://localhost/shonarbangla/scrap/getPrice.php", null, object : JsonArrayCallBack {
            override fun onSuccess(result: JSONArray) {
                sqLiteHelper.clear()
                Log.d("appLog", result.toString())

                for (i in 0 until result.length()) {
                    try {
                        var jsonObject = JSONObject()
                        jsonObject = result.get(i) as JSONObject

                        val k22_price = jsonObject.getString("22k_price")
                        val k21_price = jsonObject.getString("21k_price")
                        val k18_price = jsonObject.getString("18k_price")

                        val k22_priceS = jsonObject.getString("22k_price_silver")
                        val k21_priceS = jsonObject.getString("21k_price_silver")
                        val k18_priceS = jsonObject.getString("18k_price_silver")

                        val k22_priceSaudi = jsonObject.getString("22k_price_saudi")
                        val k21_priceSaudi = jsonObject.getString("21k_price_saudi")
                        val k18_priceSaudi = jsonObject.getString("18k_price_saudi")
                        val date = jsonObject.getString("date")

                        sqLiteHelper.insertData(
                            k22_price, k21_price, k18_price,
                            k22_priceS, k21_priceS, k18_priceS,
                            date, k22_priceSaudi, k21_priceSaudi, k18_priceSaudi
                        )

                    } catch (e: JSONException) {
                        throw RuntimeException(e)
                    }
                }

                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onFailed() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        })
    }
}
