package com.example.interviewtest.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.interviewtest.R
import com.example.interviewtest.utils.adapter.viewPagerAdapter.ViewPagerAdapter
import com.example.interviewtest.databinding.FragmentTabContainerBinding
import com.example.interviewtest.utils.Constants.tab1
import com.example.interviewtest.utils.Constants.tab2
import com.google.android.material.tabs.TabLayout.GRAVITY_FILL
import com.google.android.material.tabs.TabLayout.MODE_FIXED
import com.google.android.material.tabs.TabLayoutMediator


class TabContainerFragment : Fragment(R.layout.fragment_tab_container) {
    private var _binding: FragmentTabContainerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: ViewPagerAdapter

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTabContainerBinding.bind(view)
        setupTabs()
    }

    private fun setupTabs() {
        mAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = mAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()
        binding.tabLayout.getTabAt(0)?.text = tab1
        binding.tabLayout.getTabAt(1)?.text = tab2
        binding.tabLayout.tabMode=MODE_FIXED
        binding.tabLayout.tabGravity=GRAVITY_FILL
    }
}