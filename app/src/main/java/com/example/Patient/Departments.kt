package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Admin.Department_Data
import com.example.Doctor.DUserData
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Departments : AppCompatActivity() {


    private lateinit var backHomePage: ImageView

    private lateinit var recyclerAllDept: RecyclerView
    private lateinit var deptList:ArrayList<Department_Data>
    private lateinit var adapterAllDept:Adapter_All_Dept

    private lateinit var databaseReference: DatabaseReference



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContentView(R.layout.activity_departments)

        /*if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)
        }*/

        backHomePage=findViewById(R.id.backHomePage1)



        backHomePage.setOnClickListener {

            val intent=Intent(this@Departments, PHomePage::class.java)
            startActivity(intent)
        }


        deptList= ArrayList()
        recyclerAllDept=findViewById(R.id.recycleView_all_dept)
        databaseReference= FirebaseDatabase.getInstance().getReference("Departments")


        adapterAllDept= Adapter_All_Dept(this@Departments,deptList)


        recyclerAllDept.layoutManager = GridLayoutManager(this@Departments,2)
        recyclerAllDept.adapter= adapterAllDept

        databaseReference.child("Status 1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deptList.clear()
                for(postSnapshot in snapshot.children){
                    val currentDept = postSnapshot.getValue(Department_Data::class.java)
                    //if (mauth.currentUser?.uid != currentUser?.uid){
                    deptList.add(currentDept!!)
                    //  }*/

                }
                adapterAllDept.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })



        databaseReference=FirebaseDatabase.getInstance().getReference("Departments")
        databaseReference.child("Status 1")



    }
}