package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.Doctor.DSignupActivity
import com.example.healthcare.CheckNetworkConnection
import com.example.Doctor.Doctor_Information
import com.example.healthcare.R

class SelectField : AppCompatActivity() {

    lateinit var checkNetworkConnection: CheckNetworkConnection
    lateinit var dialog:AlertDialog
    lateinit var tryAgain:TextView





    lateinit var doctor:ImageView
    lateinit var patient:ImageView
    lateinit var admin:ImageView
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 101





    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_field)

        supportActionBar?.hide()
        callNetworkConnection()

        //custom alert dialog
        val view= View.inflate(this@SelectField, R.layout.custom_dialog7,null)
        val builder= AlertDialog.Builder(this@SelectField)
        builder.setView(view)
        dialog=builder.create()
        tryAgain=view.findViewById(R.id.btn_try_again)
        //dialog.dismiss()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        //tryAgain=findViewById(R.id.btn_try_again)




        doctor=findViewById(R.id.Doctor)
        patient=findViewById(R.id.Patient)
        //admin=findViewById(R.id.Admin)


        doctor.setOnClickListener{

            val intent=Intent(this@SelectField, DSignupActivity::class.java)
            startActivity(intent)
        }
        patient.setOnClickListener{

            val intent=Intent(this@SelectField, PSignupActivity::class.java)
            startActivity(intent)
        }
        /*admin.setOnClickListener{

            val intent=Intent(this@SelectField,Start::class.java)
            startActivity(intent)
        }*/
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
                    Toast.makeText(this@SelectField,"Please connect your network",Toast.LENGTH_LONG).show()
                }
            }

        })
    }




}