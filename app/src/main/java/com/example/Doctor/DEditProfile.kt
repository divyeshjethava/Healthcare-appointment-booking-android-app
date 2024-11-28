package com.example.Doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.Patient.PLoginActivity
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DEditProfile : AppCompatActivity() {


    private lateinit var backDocEditPro:ImageView

    private lateinit var databaseRef: DatabaseReference
    private var storageRef= Firebase.storage
    private lateinit var doctorImg: ImageView
    private lateinit var editDocImg: ImageView
    private var uri1: Uri?=null
    private lateinit var img:String


    private lateinit var editDocName: EditText
    private lateinit var editDocEmail: EditText
    private lateinit var editDocPhone: EditText

    //gender
    private lateinit var txtDocGender: TextView
    private var gender: String? =null
    private lateinit var maleDoc: TextView
    private lateinit var femaleDoc: TextView
    private lateinit var otherDoc: TextView


    private lateinit var editDocDOB: EditText
    private lateinit var editDocAge: EditText


    private lateinit var editDocDepartment: EditText
    private lateinit var department:String
    //private val departments= arrayOf("Cardiologist","Neurologist","Pulmonologist","Urologist","Dentist","Pediatrician","Trichologist","Ophthalmologist")
    //private var selectDepartment:String?=null

    private lateinit var editDocDegree: EditText
    private lateinit var editDocHospitalN: EditText
    private lateinit var editDocExperience: EditText
    private lateinit var editDocLocation: EditText
    private lateinit var editDocCity: EditText
    private lateinit var editDocAboutInfo: EditText
    private lateinit var uploadDocDegreeImg: ImageView
    private var uri2: String?=null


    private lateinit var btnDocInfoSave: TextView

    private lateinit var phoneNo:String


    private lateinit var dPassword:String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dedit_profile)
        supportActionBar?.hide()


        backDocEditPro=findViewById(R.id.back_Doc_edit_info)

        doctorImg=findViewById(R.id.edit_doctor_img)
        editDocImg=findViewById(R.id.d_edit_img)

        editDocName=findViewById(R.id.edit_doc_name)
        editDocEmail=findViewById(R.id.edit_doc_email)
        editDocPhone=findViewById(R.id.edit_doc_phone)

        maleDoc=findViewById(R.id.txt_male_d)
        femaleDoc=findViewById(R.id.txt_female_d)
        otherDoc=findViewById(R.id.txt_other_d)

        editDocDOB=findViewById(R.id.edit_doc_dob)
        editDocAge=findViewById(R.id.edit_doc_age)

        editDocDepartment=findViewById(R.id.edit_doc_dept)

        editDocDegree=findViewById(R.id.edit_doc_degree)
        editDocHospitalN=findViewById(R.id.edit_doc_hospital)
        editDocExperience=findViewById(R.id.edit_doc_experience)
        editDocLocation=findViewById(R.id.edit_doc_location)
        editDocCity=findViewById(R.id.edit_doc_city)
        editDocAboutInfo=findViewById(R.id.edit_doc_about_info)
        uploadDocDegreeImg=findViewById(R.id.edit_dco_certificate_img)

        btnDocInfoSave=findViewById(R.id.btn_save_edit_info)





        backDocEditPro.setOnClickListener {

            val intent=Intent(this@DEditProfile,DHomePage::class.java)
            startActivity(intent)

        }




        val sharedPreferences:SharedPreferences=this.getSharedPreferences(PLoginActivity().DPREF, MODE_PRIVATE)
        phoneNo=sharedPreferences.getString("dPhoneNoL","error").toString()


        databaseRef=FirebaseDatabase.getInstance().getReference("Doctor")
        databaseRef.child("Add Doctor").child(phoneNo).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                val dImage=snapshot.child("doctorImg").value

                img=dImage.toString()

                val dName=snapshot.child("username").value
                val dEmail=snapshot.child("email").value
                val dPhone=snapshot.child("phoneNo").value
                val dGender=snapshot.child("gender").value
                val dAge=snapshot.child("age").value
                val dDOB=snapshot.child("birthDate").value
                val dDept=snapshot.child("department").value

                val dDegree=snapshot.child("degree").value
                val dHospitalN=snapshot.child("hospitalName").value
                val dExperience=snapshot.child("experience").value
                val dLocation=snapshot.child("location").value
                val dCity=snapshot.child("location").value
                val dAboutInfo=snapshot.child("aboutInfo").value
                val dCerImg=snapshot.child("certificateImg").value

                uri2=dCerImg.toString()

                dPassword=snapshot.child("password").value.toString()



                Glide.with(this@DEditProfile).load(dImage).into(doctorImg)

                editDocName.setText("$dName")
                editDocEmail.setText("$dEmail")
                editDocPhone.setText("$dPhone")


                if(dGender=="Male")
                {
                    maleDoc.setBackgroundResource(R.drawable.button_shap5)
                    maleDoc.setTextColor(Color.BLACK)

                    gender=dGender.toString()
                }
                else if(dGender=="Female")
                {
                    femaleDoc.setBackgroundResource(R.drawable.button_shap5)
                    femaleDoc.setTextColor(Color.BLACK)

                    gender=dGender.toString()

                }
                else
                {
                    otherDoc.setBackgroundResource(R.drawable.button_shap5)
                    otherDoc.setTextColor(Color.BLACK)

                    gender=dGender.toString()

                }


                editDocDOB.setText("$dDOB")
                editDocAge.setText("$dAge")


                //spinner
                /*val arrayAdapter= ArrayAdapter(this@DEditProfile,android.R.layout.simple_spinner_dropdown_item,departments)
                editDocDepartment.adapter=arrayAdapter
                editDocDepartment.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        selectDepartment=departments[position]

                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }*/


                editDocDepartment.setText("$dDept")
                department=editDocDepartment.toString()

                editDocDegree.setText("$dDegree")
                editDocHospitalN.setText("$dHospitalN")
                editDocExperience.setText("$dExperience")
                editDocLocation.setText("$dLocation")
                editDocCity.setText("$dCity")
                editDocAboutInfo.setText("$dAboutInfo")

                Glide.with(this@DEditProfile).load(dCerImg).into(uploadDocDegreeImg)



            }

            override fun onCancelled(error: DatabaseError) {
            }


        })






        val selectDImg=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri1=it
            doctorImg.setImageURI(uri1)
        }

        editDocImg.setOnClickListener {

            selectDImg.launch("image/*")


        }





        btnDocInfoSave.setOnClickListener {


            val nameD=editDocName.text.toString()
            val emailD=editDocEmail.text.toString()
            val phoneD=editDocPhone.text.toString()
            val genderD=gender
            val departmentD=editDocDepartment.text.toString()
            val ageD=editDocAge.text.toString()
            val dobD=editDocDOB.text.toString()
            val degreeD=editDocDegree.text.toString()
            val HospitalND=editDocHospitalN.text.toString()
            val experienceD=editDocExperience.text.toString()
            val locationD=editDocLocation.text.toString()
            val cityD=editDocCity.text.toString()
            val aboutInfoD=editDocAboutInfo.text.toString()




            //validation fields

            if (nameD.isEmpty())
            {
                editDocName.setError("Enter Name")
            }
            else if (emailD.isEmpty())
            {
                editDocEmail.setError("Enter Email Id")
            }
            else if (phoneD.isEmpty())
            {
                editDocPhone.setError("Enter Phone No")
            }
            else if (genderD==null)
            {
                Toast.makeText(this,"Select your Gender",Toast.LENGTH_SHORT).show()
            }
            else if (ageD.isEmpty())
            {
                editDocAge.setError("Enter your Age")
            }
            else if (dobD.isEmpty())
            {
                editDocDOB.setError("Enter your Birth Date")
            }
            else if(departmentD==null)
            {
                Toast.makeText(this,"Select your Department",Toast.LENGTH_SHORT).show()
            }
            else if (degreeD.isEmpty())
            {
                editDocDegree.setError("Enter your Degree")
            }
            else if(experienceD.isEmpty())
            {
                editDocExperience.setError("Enter your Experience Year")
            }
            else if (locationD.isEmpty())
            {
                editDocLocation.setError("Enter your Location")
            }
            else if (cityD.isEmpty())
            {
                editDocCity.setError("Enter your City")
            }
            else if(aboutInfoD.isEmpty())
            {
                editDocAboutInfo.setError("Enter your About Info")
            }
            else if(uri2==null)
            {
                Toast.makeText(this,"Please Upload Certificate Image",Toast.LENGTH_SHORT).show()
            }
            else if (!phoneD.length.equals(10))
            {
                editDocPhone.setError("Enter Correct Phone No")
            }
            else {
                //custom alert dialog
                val view = View.inflate(this@DEditProfile, R.layout.custom_dialog, null)
                val builder = AlertDialog.Builder(this@DEditProfile)
                builder.setView(view)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



                if (uri1==null)
                {
                    Toast.makeText(this@DEditProfile,"hi",Toast.LENGTH_LONG).show()

                    databaseRef = FirebaseDatabase.getInstance().getReference("Doctor")
                    databaseRef.child("Add Doctor").child(phoneD).setValue(
                        DUserData(img, nameD, emailD, phoneD, genderD, ageD, dobD, departmentD, degreeD, HospitalND, experienceD, locationD, cityD,aboutInfoD, uri2.toString(),dPassword,1)
                    ).addOnCompleteListener {


                        if (it.isSuccessful) {

                            dialog.dismiss()

                        }
                    }
                }
                else
                {
                    storageRef.getReference("Doctor").child("Add Doctor").child(phoneD).child("image")
                        .putFile(uri1!!)
                        .addOnSuccessListener { task ->

                            task.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener { uri1 ->

                                    val imageUrl = uri1.toString()
                                    databaseRef = FirebaseDatabase.getInstance().getReference("Doctor")
                                    databaseRef.child("Add Doctor").child(phoneD).setValue(
                                        DUserData(imageUrl, nameD, emailD, phoneD, genderD, ageD, dobD, departmentD, degreeD, HospitalND, experienceD, locationD, cityD,aboutInfoD, uri2.toString(),dPassword,1)
                                    ).addOnCompleteListener {


                                        if (it.isSuccessful) {

                                           dialog.dismiss()

                                        }
                                    }


                                }

                        }
                }



                //val storage = FirebaseStorage.getInstance()

                //val imageRef = storage.reference.child("Doctor").child(phoneD!!).child("image")




            }



        }


    }
}