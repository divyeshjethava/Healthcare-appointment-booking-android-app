package com.example.Doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.Patient.Book_Apointment
import com.example.Patient.PHomePage
import com.example.Patient.PLoginActivity
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorProfile : AppCompatActivity() {

    private lateinit var backPageDoctorPro:ImageView

    private lateinit var btnBookAppointmnet:TextView

    private lateinit var doctorPImg:ImageView
    private lateinit var txtDoctorName:TextView
    private lateinit var txtDoctorDepartment:TextView

    private lateinit var txtExperience:TextView
    private lateinit var txtAboutInfo:TextView

    private lateinit var databaseReference: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)

        supportActionBar?.hide()


        backPageDoctorPro=findViewById(R.id.back_page_doctor_profile)

        btnBookAppointmnet=findViewById(R.id.btn_book_Appointment)

        doctorPImg=findViewById(R.id.d_Profile_Img)
        txtDoctorName=findViewById(R.id.txt_doctor_name)
        txtDoctorDepartment=findViewById(R.id.txt_doctor_department)

        txtExperience=findViewById(R.id.txt_Experience)
        txtAboutInfo=findViewById(R.id.txt_About_Info)






        backPageDoctorPro.setOnClickListener {

            val intent=Intent(this@DoctorProfile,PHomePage::class.java)
            startActivity(intent)

        }




        val dImg=intent.getStringExtra("dImg")
        Glide.with(this@DoctorProfile).load(dImg).into(doctorPImg)

        val dName=intent.getStringExtra("dName")
        txtDoctorName.setText(dName)

        val dDepartment=intent.getStringExtra("department")
        txtDoctorDepartment.setText(dDepartment)

        val dNumber=intent.getStringExtra("dNumber")

        val experience=intent.getStringExtra("dExperience")
        txtExperience.setText(experience)

        val aboutInfo=intent.getStringExtra("dAboutInfo")
        txtAboutInfo.setText(aboutInfo)



        val sharedPreferences:SharedPreferences=this.getSharedPreferences(PLoginActivity().PREF,0)
        val phoneNo=sharedPreferences.getString("phoneNoL","error").toString()



        btnBookAppointmnet.setOnClickListener {


            databaseReference=FirebaseDatabase.getInstance().getReference("Subscriptions")
            databaseReference.child(phoneNo).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if(snapshot.exists())
                    {
                        val intent= Intent(this@DoctorProfile, Book_Apointment::class.java)
                        intent.putExtra("dImg",dImg)
                        intent.putExtra("dName",dName)
                        intent.putExtra("dDepartment",dDepartment)
                        intent.putExtra("dNumber",dNumber)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this@DoctorProfile,"Please Add Subscription",Toast.LENGTH_LONG).show()
                    }

                }

                override fun onCancelled(error: DatabaseError) {


                }


            })




        }

    }
}