package com.example.Patient

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.Admin.Admin_Home
import com.example.Doctor.DHomePage
import com.example.healthcare.R

class splash_screen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()





        Handler().postDelayed({
            val sharedPreferencesP:SharedPreferences=this.getSharedPreferences(PLoginActivity().share,0)
            val LogInP:Boolean= sharedPreferencesP.getBoolean("HasLogin",false)

            val sharedPreferencesA:SharedPreferences=this.getSharedPreferences(PLoginActivity().shareA,0)
            val LogInA:Boolean= sharedPreferencesA.getBoolean("HasLoginA",false)

            val sharedPreferencesD:SharedPreferences=this.getSharedPreferences(PLoginActivity().shareD,0)
            val LogInD:Boolean= sharedPreferencesD.getBoolean("HasLoginD",false)

            val sharedPreferencesCA:SharedPreferences=this.getSharedPreferences(PLoginActivity().shareCA,0)
            val LogInCA:Boolean= sharedPreferencesCA.getBoolean("HasLoginCA",false)

            if(LogInP){
                val intent=Intent(this@splash_screen, PHomePage::class.java)
                startActivity(intent)
                finish()

            }
            else if(LogInA){
                val intent=Intent(this@splash_screen, Admin_Home::class.java)
                startActivity(intent)
                finish()

            }
            else if(LogInD)
            {
                val intent=Intent(this@splash_screen, DHomePage::class.java)
                startActivity(intent)
                finish()
            }
            else if(LogInCA)
            {
                val intent=Intent(this@splash_screen, Admin_Home::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this@splash_screen, SelectField::class.java)
                startActivity(intent)
                finish()

            }


        },2000)
    }


}