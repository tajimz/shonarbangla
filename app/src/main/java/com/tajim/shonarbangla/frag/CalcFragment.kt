package com.tajim.shonarbangla.frag

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.tajim.shonarbangla.MainActivity
import com.tajim.shonarbangla.databinding.FragmentCalcBinding

class CalcFragment : Fragment() {
    private lateinit var binding: FragmentCalcBinding
    private var vori = 0f
    private var ana = 0f
    private var roti = 0f
    private var point = 0f

    private var voriCount = 0
    private var anaCount = 0
    private var rotiCount = 0
    private var pointCount = 0
    private var mojuriCount = 0

    private var voriFinal = 0f
    private var anaFinal = 0f
    private var rotiFinal = 0f
    private var pointFinal = 0f
    private var totalFinal = 0f
    private var mojuriFinal = 0f
    private var priceTotalFinal = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalcBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    private fun setup() {
        binding.edPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val ss = s.toString()
                vori = if (ss.isEmpty()) 0f else ss.toFloat()
                ana = vori / 16
                roti = ana / 6
                point = roti / 10

                binding.tvDorVori.text = "$vori"
                binding.tvDorAna.text = "$ana"
                binding.tvDorRoti.text = "$roti"
                binding.tvDorPoint.text = "$point"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.edPrice.setText("${MainActivity.PRICE}")

        // Text watchers for other fields
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) { calculate() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        binding.edVori.addTextChangedListener(watcher)
        binding.edAna.addTextChangedListener(watcher)
        binding.edRoti.addTextChangedListener(watcher)
        binding.edPoint.addTextChangedListener(watcher)
        binding.edMojuri.addTextChangedListener(watcher)

        binding.btnReset.setOnClickListener {
            binding.edPrice.setText("${MainActivity.PRICE}")
            binding.edVori.text.clear()
            binding.edAna.text.clear()
            binding.edRoti.text.clear()
            binding.edPoint.text.clear()
            binding.edMojuri.text.clear()
            binding.btnReset.visibility = View.INVISIBLE
        }
    }

    private fun calculate() {
        binding.btnReset.visibility = View.VISIBLE

        voriCount = getIntFromEd(binding.edVori)
        anaCount = getIntFromEd(binding.edAna)
        rotiCount = getIntFromEd(binding.edRoti)
        pointCount = getIntFromEd(binding.edPoint)
        mojuriCount = getIntFromEd(binding.edMojuri)

        binding.tvVori.text = "$voriCount ভরি"
        binding.tvAna.text = "$anaCount আনা"
        binding.tvRoti.text = "$rotiCount রতি"
        binding.tvPoint.text = "$pointCount পয়েন্ট"

        binding.tvDorVori.text = "$vori * $voriCount"
        binding.tvDorAna.text = "$ana * $anaCount"
        binding.tvDorRoti.text = "$roti * $rotiCount"
        binding.tvDorPoint.text = "$point * $pointCount"

        voriFinal = vori * voriCount
        anaFinal = ana * anaCount
        rotiFinal = roti * rotiCount
        pointFinal = point * pointCount
        totalFinal = voriFinal + anaFinal + rotiFinal + pointFinal
        mojuriFinal = totalFinal * (mojuriCount.toFloat() / 100)
        priceTotalFinal = totalFinal + mojuriFinal

        binding.tvDamVori.text = "$voriFinal"
        binding.tvDamAna.text = "$anaFinal"
        binding.tvDamRoti.text = "$rotiFinal"
        binding.tvDamPoint.text = "$pointFinal"
        binding.tvDam.text = "$totalFinal"
        binding.tvMojuri.text = "$mojuriFinal"
        binding.tvFinalDam.text = "$priceTotalFinal"
    }

    companion object {
        fun getIntFromEd(editText: EditText): Int {
            val a = editText.text.toString()
            return if (a.isEmpty()) 0 else a.toInt()
        }
    }
}
