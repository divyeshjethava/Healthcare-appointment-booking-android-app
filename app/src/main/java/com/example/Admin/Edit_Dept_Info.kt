package com.example.Admin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.Patient.Departments
import com.example.healthcare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Edit_Dept_Info : AppCompatActivity() {


    private lateinit var backPage:ImageView

    private lateinit var dept_img:ImageView
    private lateinit var dept_name:EditText
    private lateinit var dept_des:EditText


    private lateinit var txtEditDept:TextView

    private lateinit var showSaveBtn:CardView

    private var toggle:Boolean=true


    private lateinit var btnSaveDeptInfo:TextView
    private lateinit var dotLottie:LottieAnimationView


    private lateinit var databaseReference: DatabaseReference

    private var storageRef= Firebase.storage
    private var uri1: Uri?=null


    private var editDeptUrl:String?=null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_dept_info)

        supportActionBar?.hide()


        backPage=findViewById(R.id.back_edit_dept_info)

        dept_img=findViewById(R.id.edit_dept_Img)
        dept_name=findViewById(R.id.edit_txt_dept_name)
        dept_des=findViewById(R.id.edit_txt_Description)

        txtEditDept=findViewById(R.id.txt_edit_dept)
        showSaveBtn=findViewById(R.id.cardView_show_btn)


        btnSaveDeptInfo=findViewById(R.id.btn_save_dept_info)
        dotLottie=findViewById(R.id.edit_dept_dot_loading)



        val selectDeptImg=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri1=it
            dept_img.setImageURI(uri1)
        }


        backPage.setOnClickListener {

            val intent=Intent(this@Edit_Dept_Info,Add_Departments::class.java)
            startActivity(intent)

        }



        var image=intent.getStringExtra("deptImg")
        var name=intent.getStringExtra("deptName")
        var desc=intent.getStringExtra("deptDes")



        Glide.with(this).load(image).into(dept_img)

        dept_name.setText("$name")
        dept_des.setText("$desc")





        txtEditDept.setOnClickListener {

            if (toggle)
            {
                showSaveBtn.visibility= View.VISIBLE
                txtEditDept.setText("Cancel")


                dept_img.isEnabled=true


                dept_name.isEnabled=true
                dept_name.setBackgroundResource(R.drawable.button_shap5)
                dept_name.requestFocus()

                dept_des.isEnabled=true
                dept_des.setBackgroundResource(R.drawable.button_shap5)

//                focus keyboard
//                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

                dept_des.setLines(20)


                dept_img.setOnClickListener {

                    selectDeptImg.launch("image/*")

                }






                btnSaveDeptInfo.setOnClickListener {

                    btnSaveDeptInfo.setText("")
                    dotLottie.visibility=View.VISIBLE

                    val editDeptName=dept_name.text.toString()
                    val editDeptDes=dept_des.text.toString()


                    if(uri1==null)
                    {


                        databaseReference=FirebaseDatabase.getInstance().getReference("Departments")
                        databaseReference.child("All Departments").child(name.toString()).setValue(Department_Data(image,editDeptName,editDeptDes,1)).addOnCompleteListener {

                            if(it.isSuccessful)
                            {


                                databaseReference=FirebaseDatabase.getInstance().getReference("Departments")
                                databaseReference.child("Status 1").child(name.toString()).setValue(Department_Data(image,editDeptName,editDeptDes,1)).addOnCompleteListener {

                                    if(it.isSuccessful)
                                    {

                                        dept_img.isEnabled=false

                                        dept_name.isEnabled=false
                                        dept_name.setBackgroundResource(R.color.gray)

                                        dept_des.isEnabled=false
                                        dept_des.setBackgroundResource(R.color.gray)




                                        txtEditDept.setText("Edit")
                                        dotLottie.visibility=View.GONE
                                        showSaveBtn.visibility= View.GONE


                                        toggle=true
                                        btnSaveDeptInfo.setText("Save")

                                        Toast.makeText(this,"Edit Successful...",Toast.LENGTH_SHORT).show()
                                    }

                                }


                            }

                        }

                    }
                    else
                    {



                        storageRef.getReference("Departments").child(name.toString()).child("$name image")
                            .putFile(uri1!!)
                            .addOnSuccessListener { task ->

                                task.metadata!!.reference!!.downloadUrl
                                    .addOnSuccessListener { uri1 ->

                                        editDeptUrl = uri1.toString()


                                        databaseReference=FirebaseDatabase.getInstance().getReference("Departments")
                                        databaseReference.child("All Departments").child(name.toString()).setValue(Department_Data(editDeptUrl,editDeptName,editDeptDes,1)).addOnCompleteListener {

                                            if(it.isSuccessful)
                                            {



                                                databaseReference=FirebaseDatabase.getInstance().getReference("Departments")
                                                databaseReference.child("Status 1").child(name.toString()).setValue(Department_Data(editDeptUrl,editDeptName,editDeptDes,1)).addOnCompleteListener {

                                                    if(it.isSuccessful)
                                                    {


                                                        dept_img.isEnabled=false

                                                        dept_name.isEnabled=false
                                                        dept_name.setBackgroundResource(R.color.gray)

                                                        dept_des.isEnabled=false
                                                        dept_des.setBackgroundResource(R.color.gray)



                                                        txtEditDept.setText("Edit")
                                                        dotLottie.visibility=View.GONE
                                                        showSaveBtn.visibility= View.GONE

                                                        toggle=true
                                                        btnSaveDeptInfo.setText("Save")


                                                        Toast.makeText(this,"Edit Successful...",Toast.LENGTH_SHORT).show()
                                                    }

                                                }



                                            }

                                        }
                                    }
                            }
                    }





                }




                /*val layoutParams = dept_name.layoutParams
                val index = (dept_name.parent as ViewGroup).indexOfChild(dept_name)


                val editText = EditText(this)
                editText.setBackgroundResource(R.drawable.button_shap5)
                editText.setTextColor(resources.getColor(R.color.black))
                editText.layoutParams = ViewGroup.LayoutParams(550,140) // Use the same layout params as the TextView
                editText.setText(dept_name.text)
                editText.id = dept_name.id


                val parentView = dept_name.parent as ViewGroup
                parentView.removeViewAt(index) // Remove the TextView
                parentView.addView(editText, index)*/



                toggle=false
            }
            else
            {
                showSaveBtn.visibility= View.GONE
                txtEditDept.setText("Edit")


                dept_img.isEnabled=false


                dept_name.isEnabled=false
                dept_name.setBackgroundResource(R.color.gray)

                dept_des.isEnabled=false
                dept_des.setBackgroundResource(R.color.gray)



                dept_name.setText("$name")
                dept_des.setText("$desc")



                toggle=true

            }


        }





    }
}