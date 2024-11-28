package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.ParcelFileDescriptor
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.Doctor.DUserData
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Department_Info : AppCompatActivity() {


    private lateinit var backDeptInfo:ImageView

    private lateinit var deptImg:ImageView
    private lateinit var deptName:TextView
    private lateinit var deptDescr:TextView

    private lateinit var btnFindDoctors:TextView
    private lateinit var dotLoading:LottieAnimationView

    private lateinit var showRelative:RelativeLayout

    private lateinit var btnCard:CardView


    private lateinit var txtFindDoc:TextView

    private lateinit var database : DatabaseReference
    private lateinit var userlist :ArrayList<DUserData>
    private lateinit var usersAdp : Adapter_Home
    private lateinit var recycleFindDoc:RecyclerView

    var name:String?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_info)

        supportActionBar!!.hide()

        backDeptInfo=findViewById(R.id.backHomePage1)

        deptImg=findViewById(R.id.dept_Img)
        deptName=findViewById(R.id.txt_dept_name)
        deptDescr=findViewById(R.id.txtDescription)

        btnFindDoctors=findViewById(R.id.btn_Find_doctor)
        dotLoading=findViewById(R.id.dot_loading)


        showRelative=findViewById(R.id.relative_in_recycle)



        btnCard=findViewById(R.id.card_find_doc)

        txtFindDoc=findViewById(R.id.txt_find_doc)




        backDeptInfo.setOnClickListener {

            val intent=Intent(this@Department_Info,PHomePage::class.java)
            startActivity(intent)
        }


        val img=intent.getStringExtra("deptImg")
        Glide.with(this).load(img).into(deptImg)

        name=intent.getStringExtra("deptName")
        deptName.setText("$name")

        val desc=intent.getStringExtra("deptDesc")
        deptDescr.setText("$desc")




        btnFindDoctors.setOnClickListener {


            dotLoading.visibility= View.VISIBLE
            btnFindDoctors.setText("")

            Handler().postDelayed({



                showRelative.visibility=View.VISIBLE

                txtFindDoc.setText("Top $name Doctors")


                userlist= ArrayList()
                recycleFindDoc=findViewById(R.id.recycleView_department_doc)
                database= FirebaseDatabase.getInstance().getReference()
                usersAdp= Adapter_Home(this,userlist)
                recycleFindDoc.layoutManager = LinearLayoutManager(this)
                recycleFindDoc.adapter= usersAdp

                database.child("Departments").child("Dept Doctors").child(name.toString()).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userlist.clear()
                        for(postSnapshot in snapshot.children){
                            val currentUser = postSnapshot.getValue(DUserData::class.java)
                            //if (mauth.currentUser?.uid != currentUser?.uid){
                            userlist.add(currentUser!!)
                            //  }*/

                        }
                        usersAdp.notifyDataSetChanged()

                        btnCard.visibility=View.GONE
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })



            },1000)

        }

    }
}