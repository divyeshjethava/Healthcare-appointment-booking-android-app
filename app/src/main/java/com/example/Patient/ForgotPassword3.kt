package com.example.Patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.healthcare.R

class ForgotPassword3 : AppCompatActivity() {

    lateinit var clear:TextView
    lateinit var newPassword:EditText
    lateinit var conNewPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password3)

        supportActionBar?.hide()

        clear=findViewById(R.id.clearPassword)
        newPassword=findViewById(R.id.edtFNewPassword)
        conNewPassword=findViewById(R.id.edtFConPassword)

        clear.setOnClickListener {

            newPassword.setText("")
            conNewPassword.setText("")
        }
    }
}