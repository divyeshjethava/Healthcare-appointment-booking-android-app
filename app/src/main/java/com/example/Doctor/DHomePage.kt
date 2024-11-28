package com.example.Doctor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthcare.R
import nl.joery.animatedbottombar.AnimatedBottomBar

class DHomePage : AppCompatActivity() {


    private lateinit var bottomBar: AnimatedBottomBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dhome_page)

        supportActionBar?.hide()


        bottomBar=findViewById(R.id.d_anim)
        replacefragment(DHome_fragment())


        bottomBar.setOnTabSelectListener(object :AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {

                when(newTab.id){



                    R.id.D_Home ->replacefragment(DHome_fragment())
                    R.id.D_Chat ->replacefragment(DChat_fragment())
                    R.id.D_Appointment ->replacefragment(DAppoiment_fragment())
                    R.id.D_Profile ->replacefragment(DProfile_fragment())
                }

            }

        })



    }

    fun replacefragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.d_frameLayout, fragment)
        transaction.commit()
    }
}