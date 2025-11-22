package com.tajim.shonarbangla.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tajim.shonarbangla.databinding.HistoryItemBinding
import com.tajim.shonarbangla.others.CONSTANTS

class HistoryAdapter(
    private val context: Context,
    private val arrayList: ArrayList<HashMap<String, String>>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    init {
        Log.d("appLog1", arrayList.toString())
    }

    private lateinit var hashMap: HashMap<String, String>

    inner class HistoryViewHolder(val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        if (position == 0) {
            holder.binding.date.text = "Date"
            holder.binding.c22Bd.text = "২২ ক্যারেট\nস্বর্ণ BD"
            holder.binding.c21Bd.text = "২১ ক্যারেট\nস্বর্ণ BD"
            holder.binding.c18Bd.text = "১৮ ক্যারেট\nস্বর্ণ BD"
            holder.binding.c22Sa.text = "২২ ক্যারেট\nস্বর্ণ SA"
            holder.binding.c21Sa.text = "২১ ক্যারেট\nস্বর্ণ SA"
            holder.binding.c18Sa.text = "১৮ ক্যারেট\nস্বর্ণ SA"
        } else {
            hashMap = arrayList[position - 1]
            holder.binding.date.text = hashMap["date"]
            holder.binding.c22Bd.text = "${hashMap["c22_bd"]}${CONSTANTS.currency}"
            holder.binding.c21Bd.text = "${hashMap["c21_bd"]}${CONSTANTS.currency}"
            holder.binding.c18Bd.text = "${hashMap["c18_bd"]}${CONSTANTS.currency}"
            holder.binding.c22Sa.text = "${hashMap["c22_sa"]}${CONSTANTS.currency}"
            holder.binding.c21Sa.text = "${hashMap["c21_sa"]}${CONSTANTS.currency}"
            holder.binding.c18Sa.text = "${hashMap["c18_sa"]}${CONSTANTS.currency}"
        }
    }

    override fun getItemCount(): Int = arrayList.size + 1
}
