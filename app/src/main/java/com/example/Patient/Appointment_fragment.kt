package com.example.Patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.healthcare.R
import com.google.android.material.tabs.TabLayout


class Appoiment_fragment : Fragment()  {

    /*lateinit var upcoming:TextView
    lateinit var past:TextView

    private val selectedTabNumber:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View
        view=inflater.inflate(R.layout.fragment_appointment_fragment, container, false)

        upcoming=view.findViewById(R.id.textUpcoming)
        past=view.findViewById(R.id.textPast)

        val upComingFragment=UpComing_fragment()
        val transaction:FragmentTransaction= requireFragmentManager().beginTransaction()
        transaction.replace(R.id.frameLayout1,upComingFragment).commit()

        upcoming.setOnClickListener {

            val upComingFragment=UpComing_fragment()
            val transaction:FragmentTransaction= requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout1,upComingFragment).commit()

        }

        past.setOnClickListener {

            val pastFragment=Past_fragment()
            val transaction:FragmentTransaction= requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout1,pastFragment).commit()

        }

        return view
    }*/

    lateinit var tablayout: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var adapter: FragmentPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View
        view=inflater.inflate(R.layout.fragment_appointment_fragment, container, false)

        tablayout=view.findViewById(R.id.tabLayout)
        viewPager2=view.findViewById(R.id.viewPager2)
        adapter= FragmentPageAdapter(childFragmentManager,lifecycle)

        tablayout.addTab(tablayout.newTab().setText("UpComing"))
        tablayout.addTab(tablayout.newTab().setText("Past"))

        viewPager2.adapter=adapter

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
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
                tablayout.selectTab(tablayout.getTabAt(position))
            }
        })

        return view
    }


}

