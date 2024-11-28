package com.example.Admin

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.example.Patient.PLoginActivity
import com.example.healthcare.R
import com.google.android.material.navigation.NavigationView

class Admin_Home : AppCompatActivity() {


    private lateinit var fragmentManager: FragmentManager

    private lateinit var linearLayout: LinearLayout
    private lateinit var uImg:ImageView
    private lateinit var uType:TextView
    private var bolval:Boolean=true



    private lateinit var openDrawer:ImageView
    private lateinit var toggle:ActionBarDrawerToggle


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        supportActionBar!!.hide()
        window.statusBarColor=getColor(R.color.white)

        newFragment(Admin_Doctor_F())


        linearLayout=findViewById(R.id.sites)
        uImg=findViewById(R.id.DoctorImg)
        uType=findViewById(R.id.user_type)



        val drawerLayout:DrawerLayout=findViewById(R.id.drawer_layout)
        val naveView:NavigationView=findViewById(R.id.nav_view)

        val headerView=naveView.getHeaderView(0)
        val drawerLinearLay=headerView.findViewById<LinearLayout>(R.id.change_user)
        val drawerUImg=headerView.findViewById<ImageView>(R.id.drawer_doctor_img)
        val drawerUType=headerView.findViewById<TextView>(R.id.drawer_user_type)

        openDrawer=findViewById(R.id.img_plus)
        openDrawer.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }



        drawerLinearLay.setOnLongClickListener {


            if (bolval==true)
            {
                uImg.setImageResource(R.drawable.patient)
                uType.setText("Patient")

                drawerUImg.setImageResource(R.drawable.patient)
                drawerUType.setText("Patient")

                newFragment(Admin_Patient_F())

                bolval=false
            }
            else
            {
                uImg.setImageResource(R.drawable.doctor)
                uType.setText("Doctor")

                drawerUImg.setImageResource(R.drawable.doctor)
                drawerUType.setText("Doctor")

                newFragment(Admin_Doctor_F())


                bolval=true
            }


            true
        }



        naveView.setNavigationItemSelectedListener {


            when(it.itemId)
            {
                R.id.nav_department -> {

                    val intent=Intent(this@Admin_Home,Add_Departments::class.java)
                    startActivity(intent)
                }
                R.id.nav_add_admin -> {

                    val intent=Intent(this@Admin_Home,Add_New_Admin::class.java)
                    startActivity(intent)

                }
                R.id.nav_subscription -> Toast.makeText(applicationContext,"subscription",Toast.LENGTH_LONG).show()
                R.id.nav_offers ->{

                    val intent=Intent(this@Admin_Home,Add_New_Offers::class.java)
                    startActivity(intent)

                }
                R.id.nav_payment -> Toast.makeText(applicationContext,"payment",Toast.LENGTH_LONG).show()
                R.id.nav_share -> Toast.makeText(applicationContext,"share",Toast.LENGTH_LONG).show()

                R.id.nav_Logout -> {

                    val sharedPreferences: SharedPreferences =this.getSharedPreferences(PLoginActivity().shareA,0)
                    val editor: SharedPreferences.Editor=sharedPreferences.edit()
                    editor.clear()
                    editor.apply()

                    val intent= Intent(this, PLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }


            true
        }










        linearLayout.setOnLongClickListener {

            if (bolval==true)
            {
                uImg.setImageResource(R.drawable.patient)
                uType.setText("Patient")

                drawerUImg.setImageResource(R.drawable.patient)
                drawerUType.setText("Patient")

                newFragment(Admin_Patient_F())

                bolval=false
            }
            else
            {
                uImg.setImageResource(R.drawable.doctor)
                uType.setText("Doctor")

                drawerUImg.setImageResource(R.drawable.doctor)
                drawerUType.setText("Doctor")

                newFragment(Admin_Doctor_F())


                bolval=true
            }

            true
        }


    }


    private fun newFragment(fragment: Fragment)
    {
        fragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragment_con,fragment).commit()

    }
}