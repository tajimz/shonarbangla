package com.tajim.shonarbangla.others

import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private var loadingAlert: AlertDialog? = null

    protected fun startLoading() {
        val progressBar = ProgressBar(context)
        val dialog = AlertDialog.Builder(context!!)
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
}
