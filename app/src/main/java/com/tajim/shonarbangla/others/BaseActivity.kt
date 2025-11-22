package com.tajim.shonarbangla.others

import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

open class BaseActivity : AppCompatActivity() {

    private var loadingAlert: AlertDialog? = null

    fun jsonArrayReq(
        loading: Boolean,
        url: String,
        jsonArray: JSONArray?,
        jsonArrayCallBack: JsonArrayCallBack
    ) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        if (loading) startLoading()

        val request = JsonArrayRequest(
            Request.Method.POST,
            url,
            jsonArray,
            Response.Listener { response ->
                endLoading()
                jsonArrayCallBack.onSuccess(response)
            },
            Response.ErrorListener { error: VolleyError ->
                endLoading()
                Log.e("appLog", error.toString())
                Toast.makeText(this@BaseActivity, "Unknown Error - Volley", Toast.LENGTH_SHORT)
                    .show()
                jsonArrayCallBack.onFailed()
            }
        )

        requestQueue.add(request)
    }

    protected fun startLoading() {
        val progressBar = ProgressBar(this)
        val dialog = AlertDialog.Builder(this)
            .setView(progressBar)
            .setCancelable(false)
            .create()
        dialog.show()
        dialog.window?.setLayout(175, 175)
        loadingAlert = dialog
    }

    protected fun endLoading() {
        if (loadingAlert != null && loadingAlert!!.isShowing) {
            loadingAlert!!.dismiss()
        }
    }

    interface JsonArrayCallBack {
        fun onSuccess(result: JSONArray)
        fun onFailed()
    }
}
