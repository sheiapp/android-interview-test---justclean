package com.example.interviewtest.utils.adapter.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.interviewtest.view.fragments.FavoritesTab
import com.example.interviewtest.view.fragments.PostTab

class ViewPagerAdapter(f: Fragment, ) : FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> PostTab()
            1-> FavoritesTab()
            else->
                PostTab()
        }
    }
}