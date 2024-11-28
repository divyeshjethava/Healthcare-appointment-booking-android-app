package com.example.Patient

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.healthcare.CheckNetworkConnection
import com.example.healthcare.R
import com.example.healthcare.Setting_fragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class PHomePage : AppCompatActivity() {


    lateinit var checkNetworkConnection: CheckNetworkConnection
    lateinit var dialog:AlertDialog
    lateinit var tryAgain:TextView



    lateinit var fram:FrameLayout

    //bottom navigation
    lateinit var bottomBar:AnimatedBottomBar
    lateinit var sharedPOne: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phome_page)

        supportActionBar?.hide()
        window.statusBarColor=getColor(R.color.white)

        callNetworkConnection()

        //custom alert dialog
        val view= View.inflate(this@PHomePage, R.layout.custom_dialog7,null)
        val builder= AlertDialog.Builder(this@PHomePage)
        builder.setView(view)
        dialog=builder.create()
        tryAgain=view.findViewById(R.id.btn_try_again)
        //dialog.dismiss()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



        /*val view: View = View.inflate(this@HomePage,R.layout.activity_edit_profile,null)
        val bsDialog=BottomSheetDialog(this@HomePage)
        bsDialog.setContentView(view)
        bsDialog.show()*/

        sharedPOne=getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val hasRunBefore = sharedPOne.getBoolean("hasRunBefore", true)

        if (hasRunBefore)
        {
            //dialog
            val view=View.inflate(this, R.layout.custom_dialog6,null)
            val builder=AlertDialog.Builder(this)
            builder.setView(view)
            val dialog=builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()


            val btnCancel=dialog.findViewById<TextView>(R.id.btn_cancel)
            val btnEdit=dialog.findViewById<TextView>(R.id.btn_edit)

            btnCancel?.setOnClickListener {

                dialog.dismiss()
            }

            btnEdit?.setOnClickListener {

                val intent=Intent(this@PHomePage, EditProfile::class.java)
                startActivity(intent)
            }


            val editor=sharedPOne.edit()
            editor.putBoolean("hasRunBefore", false)
            editor.apply()
        }


        bottomBar=findViewById(R.id.anim)
        replacefra(Home_fragment())


        bottomBar.setOnTabSelectListener(object :AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                    lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                val fragment :Fragment?=null

                when(newTab.id){
                    R.id.Home ->replacefra(Home_fragment())
                    R.id.Chat ->replacefra(Setting_fragment())
                    R.id.Appointment ->replacefra(Appoiment_fragment())
                    R.id.Profile ->replacefra(Profile_fragment())
                }

            }

        })





        /*logout.setOnClickListener {
            val sharedPreferences: SharedPreferences =this.getSharedPreferences(PLoginActivity().share,0)
            val editor: SharedPreferences.Editor=sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent =Intent(this@HomePage,SelectField::class.java)
            startActivity(intent)
            finish()
        }*/

    }

    private fun replacefra(fragment:Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }




    private fun callNetworkConnection() {
        checkNetworkConnection= CheckNetworkConnection(application)
        checkNetworkConnection.observe(this,{ isConnected ->

            if(isConnected)
            {
                dialog.dismiss()
            }
            else
            {
                dialog.show()
                tryAgain.setOnClickListener {
                    Toast.makeText(this@PHomePage,"Please connect your network", Toast.LENGTH_LONG).show()
                }
            }

        })
    }


}