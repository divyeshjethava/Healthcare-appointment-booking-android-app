package com.example.Admin

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Add_Departments : AppCompatActivity() {


    private lateinit var addDept:ImageView
    private lateinit var backDept:ImageView


    private lateinit var viewLayout: LinearLayout
    private lateinit var viewRecy:RelativeLayout

    private var viewToggle:Boolean=true


    private lateinit var databaseRef1: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference

    private var storageRef= Firebase.storage
    private var uri1: Uri?=null

    private lateinit var addDeptImage:ImageView
    private lateinit var editDeptName:EditText
    private lateinit var editDeptDescription:EditText
    private lateinit var btnAddDept:TextView



    lateinit var deptlist :ArrayList<Department_Data>
    lateinit var deptAdp : AAdapter_Add_Dept


    lateinit var recycleDept: RecyclerView


    lateinit var nodataimage:ImageView
    lateinit var nodataText:TextView


    lateinit var txtTotalDept:TextView
    private var totalDept:Int = 0



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_departments)

        supportActionBar!!.hide()


        addDept=findViewById(R.id.add_department)
        backDept=findViewById(R.id.back_dept)

        viewLayout=findViewById(R.id.view_layout)
        viewRecy=findViewById(R.id.view_recycle)

        addDeptImage=findViewById(R.id.upload_department_img)
        editDeptName=findViewById(R.id.edit_dept_name)
        editDeptDescription=findViewById(R.id.edit_dept_description)
        btnAddDept=findViewById(R.id.btn_add_department)



        nodataimage=findViewById(R.id.noimage)
        nodataText=findViewById(R.id.nodata)

        txtTotalDept=findViewById(R.id.txt_totalDept)


        viewLayout.visibility=View.GONE


        //select doctor Img
        val selectDImg=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri1=it
            addDeptImage.setImageURI(uri1)
        }
        addDeptImage.setOnClickListener {

            selectDImg.launch("image/*")
        }




        backDept.setOnClickListener {

            val intent= Intent(this@Add_Departments,Admin_Home::class.java)
            startActivity(intent)

        }



        addDept.setOnClickListener {


            if(viewToggle)
            {
                viewLayout.visibility=View.VISIBLE
                viewRecy.visibility=View.GONE

                addDept.rotation=45f



                btnAddDept.setOnClickListener {



                    var deptName=editDeptName.text.toString()
                    var deptDes=editDeptDescription.text.toString()




                    if(uri1==null)
                    {
                        Toast.makeText(this,"Select Img",Toast.LENGTH_SHORT).show()
                    }
                    else if (deptName.isEmpty())
                    {
                        editDeptName.setError("Enter Dept Name")
                    }
                    else if(deptDes.isEmpty())
                    {
                        editDeptDescription.setError("Enter Dept Description")
                    }
                    else
                    {

                        //custom alert dialog
                        val view = View.inflate(this@Add_Departments, R.layout.custom_dialog, null)
                        val builder = AlertDialog.Builder(this@Add_Departments)
                        builder.setView(view)
                        val dialog = builder.create()
                        dialog.show()
                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


                        storageRef.getReference("Departments").child(deptName).child("$deptName image")
                            .putFile(uri1!!)
                            .addOnSuccessListener { task ->

                                task.metadata!!.reference!!.downloadUrl
                                    .addOnSuccessListener { uri1 ->

                                        val imageUrl = uri1.toString()
                                        databaseRef1 = FirebaseDatabase.getInstance().getReference("Departments")
                                        databaseRef1.child("All Departments").child(deptName).setValue(Department_Data(imageUrl,deptName,deptDes,1)).addOnCompleteListener {


                                            databaseRef2 = FirebaseDatabase.getInstance().getReference("Departments")
                                            databaseRef2.child("Status 1").child(deptName).setValue(Department_Data(imageUrl,deptName,deptDes,1)).addOnCompleteListener {


                                                if (it.isSuccessful) {

                                                    Toast.makeText(this,"Add Department Successful",Toast.LENGTH_LONG).show()

                                                    editDeptName.setText("")
                                                    editDeptDescription.setText("")
                                                    //addDeptImage.setImageResource(R.drawable.upload_image)


                                                    viewLayout.visibility=View.GONE
                                                    viewRecy.visibility=View.VISIBLE
                                                    viewToggle=true

                                                    addDept.rotation=0f

                                                    dialog.dismiss()
                                                }

                                            }



                                        }
                                    }
                            }


                    }




                }

                viewToggle=false

            }
            else
            {
                viewLayout.visibility=View.GONE
                viewRecy.visibility=View.VISIBLE

                addDept.rotation=0f

                viewToggle=true
            }


        }





        deptlist= ArrayList()
        recycleDept=findViewById(R.id.recycleview_edpt)
        databaseRef1= FirebaseDatabase.getInstance().getReference()


        deptAdp= AAdapter_Add_Dept(this,deptlist)


        recycleDept.layoutManager = LinearLayoutManager(this)
        recycleDept.adapter= deptAdp

        databaseRef1.child("Departments").child("All Departments").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deptlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(Department_Data::class.java)
                    //if (mauth.currentUser?.uid != currentUser?.uid){
                    deptlist.add(currentUser!!)
                    //  }*/

                }
                deptAdp.notifyDataSetChanged()

                if (deptAdp.itemCount == 0) {
                    nodataimage.visibility=View.VISIBLE
                    nodataText.visibility = View.VISIBLE
                    recycleDept.visibility = View.INVISIBLE
                } else {
                    nodataimage.visibility=View.INVISIBLE
                    nodataText.visibility = View.INVISIBLE
                    recycleDept.visibility = View.VISIBLE
                }



                //totalAddDoctor=snapshot.childrenCount.toInt()
                totalDept= deptlist.size
                txtTotalDept.setText("$totalDept")


            }

            override fun onCancelled(error: DatabaseError) {
            }
        })





    }
}