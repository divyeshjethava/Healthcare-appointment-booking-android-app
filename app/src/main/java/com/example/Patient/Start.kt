package com.example.Patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.healthcare.R

class Start : AppCompatActivity() {

    lateinit var btnlog:TextView
    lateinit var btnsig:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        btnsig=findViewById(R.id.btnSignup)
        btnsig.setOnClickListener {
            val intent = Intent(this@Start, PSignupActivity::class.java)
            startActivity(intent)
        }
        btnlog=findViewById(R.id.btnLogin)
        btnlog.setOnClickListener {
            val intent = Intent(this@Start, PLoginActivity::class.java)
            startActivity(intent)
        }
    }
}