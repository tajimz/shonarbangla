package com.tajim.shonarbangla.frag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tajim.shonarbangla.databinding.FragmentZakatBinding;
import com.tajim.shonarbangla.others.CONSTANTS;


public class ZakatFragment extends Fragment {
    FragmentZakatBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentZakatBinding.inflate(inflater, container, false);

        handleCalculations();

        return binding.getRoot();



    }

    private void handleCalculations(){

        binding.btnSubmit.setOnClickListener(v->{
            float inGram = 0 ;
            float voriCount = CalcFragment.getIntFromEd(binding.edVori);
            float anaCount = CalcFragment.getIntFromEd(binding.edAna);
            float rotiCount = CalcFragment.getIntFromEd(binding.edRoti);
            float pointCount = CalcFragment.getIntFromEd(binding.edPoint);
            float sell = CalcFragment.getIntFromEd(binding.edPrice);
            inGram += voriCount* 11.664;
            inGram += (anaCount/16)* 11.664;
            inGram += (rotiCount/(16*6))* 11.664;
            inGram += (pointCount/(16*6*10))* 11.664;

            Toast.makeText(getContext(), ""+inGram, Toast.LENGTH_SHORT).show();

            if (inGram < 85){
                binding.tvOutput.setText("আপনার উপর যাকাত ফরজ হয় নি, যাকাত ফরজ হতে ন্যূনতম ৭.৫ ভরি স্বর্ণ থাকতে হয়");
            }else {
                float toGive = (float) (inGram * (2.5/100));
                binding.tvOutput.setText("আপনাকে "+toGive * (sell/11.664f) + CONSTANTS.currency+" যাকাত দিতে হবে");




            }






        });

    }


}