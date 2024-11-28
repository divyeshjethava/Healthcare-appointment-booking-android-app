package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.healthcare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Book_Appointment_successful : AppCompatActivity() {

    lateinit var txtDName:TextView
    lateinit var txtPName:TextView
    lateinit var txtDate:TextView
    lateinit var txtTime:TextView

    lateinit var btnGoToHome:TextView
    var pNumber:String?=null

    lateinit var databaseReference:DatabaseReference



    val calender=Calendar.getInstance().time
    val systemDate=SimpleDateFormat("dd MMMM YYYY", Locale.US).format(calender)



    private lateinit var dName:String
    private lateinit var pName:String
    private lateinit var tDate:String
    private lateinit var tTime:String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment_successful)

        supportActionBar?.hide()

        txtDName=findViewById(R.id.txt_d_name)
        txtPName=findViewById(R.id.txt_p_name)
        txtDate=findViewById(R.id.txt_date)
        txtTime=findViewById(R.id.txt_time)

        btnGoToHome=findViewById(R.id.btn_goto_home)



        dName=intent.getStringExtra("docName").toString()
        pName=intent.getStringExtra("patientName").toString()
        tDate=intent.getStringExtra("apDate").toString()
        tTime=intent.getStringExtra("apTime").toString()




        txtDName.setText("Dr. $dName")
        txtPName.setText("$pName")
        txtDate.setText("$tDate")
        txtTime.setText("$tTime")


       /* var sharedPreferences1: SharedPreferences =this.getSharedPreferences(PSignupActivity().PREFF1, MODE_PRIVATE)
        val phoneS=sharedPreferences1.getString("phoneNoS","error").toString()

        var sharedPreferences2: SharedPreferences =this.getSharedPreferences(PLoginActivity().PREF, MODE_PRIVATE)
        val phoneL=sharedPreferences2.getString("phoneNoL","error").toString()



        if(!phoneS.equals("error"))
        {
            pNumber=phoneS
        }
        else
        {
            pNumber=phoneL
        }

        val intentDate=intent.getStringExtra("bNumber")

        databaseReference=FirebaseDatabase.getInstance().getReference("Appointments")
        databaseReference.child(pNumber!!).child(intentDate!!).get().addOnSuccessListener {


            if(it.exists())
            {
                dName=it.child("doctorName").value.toString()
                pName=it.child("patientName").value.toString()
                tDate=it.child("appoDate").value.toString()
                tTime=it.child("appoTime").value.toString()


                txtDName.setText("Dr. "+dName)
                txtPName.setText(pName)
                txtDate.setText(tDate)
                txtTime.setText(tTime)

            }
        }*/




        btnGoToHome.setOnClickListener {

            val intent=Intent(this@Book_Appointment_successful, PHomePage::class.java)
            startActivity(intent)
            finish()
        }


    }
}