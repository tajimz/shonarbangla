package com.tajim.shonarbangla.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tajim.shonarbangla.frag.CalcFragment
import com.tajim.shonarbangla.frag.PricFragment
import com.tajim.shonarbangla.frag.ZakatFragment

class ViewPager2(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PricFragment()
            1 -> CalcFragment()
            2 -> ZakatFragment()
            else -> PricFragment()
        }
    }

    override fun getItemCount(): Int = 3
}
