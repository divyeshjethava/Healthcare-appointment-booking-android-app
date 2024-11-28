package com.example.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.healthcare.R
import com.google.android.material.tabs.TabLayout


class Admin_Doctor_F : Fragment() {


    lateinit var tablayout1: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var adapter1: DoctorFragmentPAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View
        view=inflater.inflate(R.layout.fragment_admin__doctor_, container, false)


        tablayout1=view.findViewById(R.id.tabLayout1)
        viewPager2=view.findViewById(R.id.viewPager1)
        adapter1= DoctorFragmentPAdapter(childFragmentManager,lifecycle)

        tablayout1.addTab(tablayout1.newTab().setText("Add D."))
        tablayout1.addTab(tablayout1.newTab().setText("Cancel D."))
        tablayout1.addTab(tablayout1.newTab().setText("New D."))

        viewPager2.adapter=adapter1

        tablayout1.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null)
                {
                    viewPager2.currentItem=tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tablayout1.selectTab(tablayout1.getTabAt(position))
            }
        })


        return view
    }


}