package com.example.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.healthcare.R
import com.google.android.material.tabs.TabLayout


class DAppoiment_fragment : Fragment() {


    private lateinit var dTabLayout: TabLayout
    private lateinit var dViewPager2: ViewPager2
    private lateinit var dAdapter: DFragmentPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View=inflater.inflate(R.layout.fragment_d_appoiment_fragment, container, false)

        dTabLayout=view.findViewById(R.id.d_tab_Layout)
        dViewPager2=view.findViewById(R.id.d_view_Pager2)
        dAdapter= DFragmentPageAdapter(childFragmentManager,lifecycle)


        dTabLayout.addTab(dTabLayout.newTab().setText("UpComing"))
        dTabLayout.addTab(dTabLayout.newTab().setText("Past"))

        dViewPager2.adapter=dAdapter

        dTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null)
                {
                    dViewPager2.currentItem=tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


        dViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dTabLayout.selectTab(dTabLayout.getTabAt(position))
            }
        })

        return view
    }

}