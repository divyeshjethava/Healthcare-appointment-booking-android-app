package com.example.Admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Patient.PUserData
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Add_New_Admin : AppCompatActivity() {

    lateinit var addAdmin:ImageView
    lateinit var backAdmin:ImageView

    lateinit var viewLinear:LinearLayout
    lateinit var viewRelative:RelativeLayout

    var toggle:Boolean=true


    private lateinit var databaseReference: DatabaseReference
    private lateinit var adminList:ArrayList<Admin_Data>
    private lateinit var adminAdapter:AAdapter_Add_C_Admin
    private lateinit var aRecyclerView: RecyclerView

    private lateinit var txtTotalAdmin:TextView


    private lateinit var editAdminName:EditText
    private lateinit var editAdminEmail:EditText
    private lateinit var editAdminPhone:EditText
    private lateinit var editAdminPass:EditText

    private lateinit var btnAddNewAdmin:TextView


    private lateinit var aNoImage:ImageView
    private lateinit var aNoData:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_admin)


        supportActionBar?.hide()

        addAdmin=findViewById(R.id.add_admin)
        backAdmin=findViewById(R.id.back_add_admin)

        viewLinear=findViewById(R.id.view_layout)
        viewRelative=findViewById(R.id.view_relative)


        editAdminName=findViewById(R.id.edit_admin_name)
        editAdminEmail=findViewById(R.id.edit_admin_email)
        editAdminPhone=findViewById(R.id.edit_admin_phone)
        editAdminPass=findViewById(R.id.edit_admin_password)

        btnAddNewAdmin=findViewById(R.id.btn_add_admin)

        txtTotalAdmin=findViewById(R.id.txt_totalAdmin)


        aNoImage=findViewById(R.id.no_image_a)
        aNoData=findViewById(R.id.no_data_a)


        addAdmin.setOnClickListener {

            if(toggle==true)
            {
                viewLinear.visibility=View.VISIBLE
                viewRelative.visibility=View.GONE

                addAdmin.rotation=45f



                btnAddNewAdmin.setOnClickListener {


                    var aName=editAdminName.text.toString()
                    var aEmail=editAdminEmail.text.toString()
                    var aPhone=editAdminPhone.text.toString()
                    var aPass=editAdminPass.text.toString()


                    if(aName.isEmpty())
                    {
                        editAdminName.setError("Enter admin name")
                    }
                    else if(aEmail.isEmpty())
                    {
                        editAdminEmail.setError("Enter admin email id")
                    }
                    else if(aPhone.isEmpty())
                    {
                        editAdminPhone.setError("Enter admin phone no")
                    }
                    else if(aPass.isEmpty())
                    {
                        editAdminPass.setError("Enter admin Password")
                    }
                    else
                    {

                        databaseReference=FirebaseDatabase.getInstance().getReference("Admin")
                        databaseReference.child("Child Admin").child(aPhone).setValue(Admin_Data(aName,aEmail,aPhone,aPass,7)).addOnCompleteListener {


                            Toast.makeText(this,"Add Admin",Toast.LENGTH_SHORT).show()

                            toggle=false


                            viewLinear.visibility=View.GONE
                            viewRelative.visibility=View.VISIBLE

                            addAdmin.rotation=0f


                        }

                    }



                }



                toggle=false
            }
            else
            {
                viewLinear.visibility=View.GONE
                viewRelative.visibility=View.VISIBLE

                addAdmin.rotation=0f

                toggle=true
            }

        }

        backAdmin.setOnClickListener {

            val intent=Intent(this@Add_New_Admin,Admin_Home::class.java)
            startActivity(intent)

        }



        adminList=ArrayList()
        aRecyclerView=findViewById(R.id.recycleview_admin)
        databaseReference=FirebaseDatabase.getInstance().getReference()
        adminAdapter= AAdapter_Add_C_Admin(this,adminList)

        aRecyclerView.layoutManager = LinearLayoutManager(this)
        aRecyclerView.adapter= adminAdapter


        databaseReference.child("Admin").child("Child Admin").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                adminList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(Admin_Data::class.java)

                    adminList.add(currentUser!!)

                }
                adminAdapter.notifyDataSetChanged()



                if(adminAdapter.itemCount==0)
                {
                    aNoImage.visibility=View.VISIBLE
                    aNoData.visibility=View.VISIBLE
                    aRecyclerView.visibility=View.GONE
                }
                else
                {
                    aNoImage.visibility=View.GONE
                    aNoData.visibility=View.GONE
                    aRecyclerView.visibility=View.VISIBLE
                }


                val totalAdmin=adminList.size
                txtTotalAdmin.setText("$totalAdmin")
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })




    }
}