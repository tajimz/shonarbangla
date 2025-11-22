package com.tajim.shonarbangla.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tajim.shonarbangla.databinding.HistoryItemBinding;
import com.tajim.shonarbangla.others.CONSTANTS;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    Context context;
    HashMap<String,String> hashMap;

    public HistoryAdapter(Context context, ArrayList<HashMap<String,String>> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        Log.d("appLog1", arrayList.toString());

    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        HistoryItemBinding binding;
        public HistoryViewHolder(HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HistoryItemBinding binding = HistoryItemBinding.inflate(inflater, parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (position == 0){
            holder.binding.date.setText("Date");
            holder.binding.c22Bd.setText("২২ ক্যারেট\nস্বর্ণ BD");
            holder.binding.c21Bd.setText("২১ ক্যারেট\nস্বর্ণ BD");
            holder.binding.c18Bd.setText("১৮ ক্যারেট\nস্বর্ণ BD");
            holder.binding.c22Sa.setText("২২ ক্যারেট\nস্বর্ণ SA");
            holder.binding.c21Sa.setText("২১ ক্যারেট\nস্বর্ণ SA");
            holder.binding.c18Sa.setText("১৮ ক্যারেট\nস্বর্ণ SA");
        }else{
            hashMap = arrayList.get(position-1);
            holder.binding.date.setText(hashMap.get("date"));
            holder.binding.c22Bd.setText(hashMap.get("c22_bd")+ CONSTANTS.currency);
            holder.binding.c21Bd.setText(hashMap.get("c21_bd")+ CONSTANTS.currency);
            holder.binding.c18Bd.setText(hashMap.get("c18_bd")+ CONSTANTS.currency);
            holder.binding.c22Sa.setText(hashMap.get("c22_sa")+ CONSTANTS.currency);
            holder.binding.c21Sa.setText(hashMap.get("c21_sa")+ CONSTANTS.currency);
            holder.binding.c18Sa.setText(hashMap.get("c18_sa")+ CONSTANTS.currency);
        }




    }

    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }


}
