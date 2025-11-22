package com.tajim.shonarbangla.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tajim.shonarbangla.databinding.FragmentZakatBinding
import com.tajim.shonarbangla.frag.CalcFragment
import com.tajim.shonarbangla.others.CONSTANTS

class ZakatFragment : Fragment() {

    private lateinit var binding: FragmentZakatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentZakatBinding.inflate(inflater, container, false)
        handleCalculations()
        return binding.root
    }

    private fun handleCalculations() {
        binding.btnSubmit.setOnClickListener {
            var inGram = 0f

            val voriCount = CalcFragment.getIntFromEd(binding.edVori).toFloat()
            val anaCount = CalcFragment.getIntFromEd(binding.edAna).toFloat()
            val rotiCount = CalcFragment.getIntFromEd(binding.edRoti).toFloat()
            val pointCount = CalcFragment.getIntFromEd(binding.edPoint).toFloat()
            val sell = CalcFragment.getIntFromEd(binding.edPrice).toFloat()

            inGram += voriCount * 11.664f
            inGram += (anaCount / 16f) * 11.664f
            inGram += (rotiCount / (16f * 6f)) * 11.664f
            inGram += (pointCount / (16f * 6f * 10f)) * 11.664f

            Toast.makeText(requireContext(), "$inGram", Toast.LENGTH_SHORT).show()

            if (inGram < 85f) {
                binding.tvOutput.text = "আপনার উপর যাকাত ফরজ হয় নি, যাকাত ফরজ হতে ন্যূনতম ৭.৫ ভরি স্বর্ণ থাকতে হয়"
            } else {
                val toGive = inGram * (2.5f / 100f)
                binding.tvOutput.text = "আপনাকে ${toGive * (sell / 11.664f)} ${CONSTANTS.currency} যাকাত দিতে হবে"
            }
        }
    }
}
