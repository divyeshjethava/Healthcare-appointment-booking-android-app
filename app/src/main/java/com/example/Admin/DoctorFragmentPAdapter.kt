package com.example.Admin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DoctorFragmentPAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0)
            Admin_Add_Doctor_Fragment()
            else if(position == 1)
                Admin_Cancel_Doctor_Fragment()
        else
            Admin_New_Doctor_Fragment()
    }

}