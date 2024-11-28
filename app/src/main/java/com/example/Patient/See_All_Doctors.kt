package com.example.Patient

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Doctor.DUserData
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class See_All_Doctors : AppCompatActivity() {


    private lateinit var database : DatabaseReference
    private lateinit var userlist :ArrayList<DUserData>
    private lateinit var usersAdp : Adapter_Home
    private lateinit var recycleSeeDoc: RecyclerView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_doctors)

        supportActionBar?.hide()



        userlist= ArrayList()
        recycleSeeDoc=findViewById(R.id.recycleView_see_all_doc)
        database= FirebaseDatabase.getInstance().getReference()
        usersAdp= Adapter_Home(this,userlist)
        recycleSeeDoc.layoutManager = LinearLayoutManager(this)
        recycleSeeDoc.adapter= usersAdp

        database.child("Doctor").child("Add Doctor").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(DUserData::class.java)
                    //if (mauth.currentUser?.uid != currentUser?.uid){
                    userlist.add(currentUser!!)
                    //  }*/

                }
                usersAdp.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }
}