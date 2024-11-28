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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.Patient.PLoginActivity
import com.example.healthcare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class Doctor_Information : AppCompatActivity() {


    lateinit var databaseRef: DatabaseReference
    var storageRef= Firebase.storage
    lateinit var doctorImg:ImageView
    lateinit var editImg:ImageView
    var uri1:Uri?=null


    lateinit var editNameD:EditText
    lateinit var editEmailD:EditText
    lateinit var editPhoneD:EditText

    //gender
    lateinit var txtGender: TextView
    var gender: String? =null
    lateinit var maleD: TextView
    lateinit var femaleD: TextView
    lateinit var otherD: TextView


    lateinit var editAgeD:EditText
    lateinit var editBirthDateD:EditText


    lateinit var editDepartmentD:Spinner
    val departments= arrayOf("Cardiologist","Neurologist","Pulmonologist","Urologist","Dentist","Pediatrician","Trichologist","Ophthalmologist")
    var selectDepartment:String?=null

    lateinit var editDegreeD:EditText
    lateinit var editHospitalND:EditText
    lateinit var editExperienceD:EditText
    lateinit var editLocationD:EditText
    lateinit var editCityD:EditText
    lateinit var editAboutInfoD:EditText
    lateinit var uploadDegreeImg:ImageView
    var uri2:Uri?=null


    lateinit var btnSave:TextView

    val PREE="SHARE"


    //signup information
    lateinit var d_name:String
    lateinit var d_phone:String
    lateinit var d_email:String
    lateinit var d_pass:String



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_information)

        supportActionBar?.hide()

        storageRef=FirebaseStorage.getInstance()

        doctorImg=findViewById(R.id.doctor_img)
        editImg=findViewById(R.id.edit_img)
        editNameD=findViewById(R.id.editNameD1)
        editEmailD=findViewById(R.id.editEmailD1)
        editPhoneD=findViewById(R.id.editPhoneNoD1)


        //gender
        txtGender=findViewById(R.id.txt_gender)
        maleD=findViewById(R.id.txt_male)
        femaleD=findViewById(R.id.txt_female)
        otherD=findViewById(R.id.txt_other)


        editAgeD=findViewById(R.id.editAgeD1)
        editBirthDateD=findViewById(R.id.editBirthDateD1)
        editDepartmentD=findViewById(R.id.editDepartmentD1)
        editDegreeD=findViewById(R.id.editDegreeD1)
        editHospitalND=findViewById(R.id.editHospitalD1)
        editExperienceD=findViewById(R.id.editExperienceD1)
        editLocationD=findViewById(R.id.editLocationD1)
        editCityD=findViewById(R.id.editCityD1)
        editAboutInfoD=findViewById(R.id.editAboutInfoD1)
        uploadDegreeImg=findViewById(R.id.upload_image)


        btnSave=findViewById(R.id.btn_save)




        var pho=intent.getStringExtra("pho")

        Toast.makeText(this,"$pho",Toast.LENGTH_LONG).show()

        //select doctor Img
        val selectDImg=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri1=it
            doctorImg.setImageURI(uri1)
        }
        editImg.setOnClickListener {

            selectDImg.launch("image/*")
        }


        //spinner
        val arrayAdapter=ArrayAdapter(this@Doctor_Information,android.R.layout.simple_spinner_dropdown_item,departments)
        editDepartmentD.adapter=arrayAdapter
        editDepartmentD.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
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
        }


        //gender
        maleD.setOnClickListener {

            maleD.setBackgroundResource(R.drawable.button_shap5)
            femaleD.setBackgroundResource(R.drawable.button_shap3)
            otherD.setBackgroundResource(R.drawable.button_shap3)
            maleD.setTextColor(Color.BLACK)
            femaleD.setTextColor(Color.rgb(130, 217, 243))
            otherD.setTextColor(Color.rgb(130, 217, 243))

            gender="Male"
        }
        femaleD.setOnClickListener {

            maleD.setBackgroundResource(R.drawable.button_shap3)
            femaleD.setBackgroundResource(R.drawable.button_shap5)
            otherD.setBackgroundResource(R.drawable.button_shap3)
            femaleD.setTextColor(Color.BLACK)
            maleD.setTextColor(Color.rgb(130, 217, 243))
            otherD.setTextColor(Color.rgb(130, 217, 243))

            gender="Female"
        }
        otherD.setOnClickListener {

            maleD.setBackgroundResource(R.drawable.button_shap3)
            femaleD.setBackgroundResource(R.drawable.button_shap3)
            otherD.setBackgroundResource(R.drawable.button_shap5)
            otherD.setTextColor(Color.BLACK)
            maleD.setTextColor(Color.rgb(130, 217, 243))
            femaleD.setTextColor(Color.rgb(130, 217, 243))

            gender="Other"
        }


        //select certificate img
        val selectCImg=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri2=it
            uploadDegreeImg.setImageURI(uri2)
        }
        uploadDegreeImg.setOnClickListener {

            selectCImg.launch("image/*")
        }





        val reference=FirebaseDatabase.getInstance().getReference("Doctor")
        reference.child("Signup Doctor").child(pho!!).get().addOnSuccessListener {


            if(it.exists())
            {
                d_name= it.child("username").value.toString()
                d_phone= it.child("phoneNo").value.toString()
                d_email= it.child("email").value.toString()
                d_pass= it.child("password").value.toString()

                editNameD.setText(d_name)
                editPhoneD.setText(d_phone)
                editEmailD.setText(d_email)

                editPhoneD.isEnabled=false
                editEmailD.isEnabled=false


            }

        }





       btnSave.setOnClickListener {

           val nameD=editNameD.text.toString()
           val emailD=editEmailD.text.toString()
           val phoneD=editPhoneD.text.toString()
           val genderD=gender
           val ageD=editAgeD.text.toString()
           val dobD=editBirthDateD.text.toString()

           val degreeD=editDegreeD.text.toString()
           val HospitalND=editHospitalND.text.toString()
           val experienceD=editExperienceD.text.toString()
           val locationD=editLocationD.text.toString()
           val cityD=editCityD.text.toString()
           val aboutInfoD=editAboutInfoD.text.toString()

           val emailSend="yashranderi8@gmail.com";
           val emailSubject="Join in health care app"



           //validation fields
           if (uri1==null)
           {
               Toast.makeText(this,"Please Upload Image",Toast.LENGTH_SHORT).show()
           }
           else if (nameD.isEmpty())
           {
               editNameD.setError("Enter Name")
           }
           else if (emailD.isEmpty())
           {
               editEmailD.setError("Enter Email Id")
           }
           else if (phoneD.isEmpty())
           {
               editPhoneD.setError("Enter Phone No")
           }
           else if (genderD==null)
           {
               Toast.makeText(this,"Select your Gender",Toast.LENGTH_SHORT).show()
           }
           else if (ageD.isEmpty())
           {
               editAgeD.setError("Enter your Age")
           }
           else if (dobD.isEmpty())
           {
               editBirthDateD.setError("Enter your Birth Date")
           }
           else if(selectDepartment==null)
           {
               Toast.makeText(this,"Select your Department",Toast.LENGTH_SHORT).show()
           }
           else if (degreeD.isEmpty())
           {
               editDegreeD.setError("Enter your Degree")
           }
           else if(experienceD.isEmpty())
           {
               editExperienceD.setError("Enter your Experience Year")
           }
           else if (locationD.isEmpty())
           {
               editLocationD.setError("Enter your Location")
           }
           else if (cityD.isEmpty())
           {
               editCityD.setError("Enter your City")
           }
           else if(aboutInfoD.isEmpty())
           {
               editAboutInfoD.setError("Enter your About Info")
           }
           else if(uri2==null)
           {
               Toast.makeText(this,"Please Upload Certificate Image",Toast.LENGTH_SHORT).show()
           }
           else if (!phoneD.length.equals(10))
           {
               editPhoneD.setError("Enter Correct Phone No")
           }
           else {
               //custom alert dialog
               val view = View.inflate(this@Doctor_Information, R.layout.custom_dialog, null)
               val builder = AlertDialog.Builder(this@Doctor_Information)
               builder.setView(view)
               val dialog = builder.create()
               dialog.show()
               dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


               //val storage = FirebaseStorage.getInstance()

               //val imageRef = storage.reference.child("Doctor").child(phoneD!!).child("image")

               storageRef.getReference("Doctor").child("Add Doctor").child(phoneD).child("image")
                   .putFile(uri1!!)
                   .addOnSuccessListener { task ->

                       task.metadata!!.reference!!.downloadUrl
                           .addOnSuccessListener { uri1 ->

                               val imageUrl = uri1.toString()
                               databaseRef = FirebaseDatabase.getInstance().getReference("Doctor")
                               databaseRef.child("New Doctor").child(phoneD).setValue(
                                   DUserData(imageUrl, nameD, emailD, phoneD, genderD, ageD, dobD, selectDepartment!!, degreeD, HospitalND, experienceD, locationD, cityD,aboutInfoD, uri2.toString(),d_pass,0)
                               ).addOnCompleteListener {


                                   if (it.isSuccessful) {

                                       //saveImageStorage()
                                       //setEditImage()

                                       val sharedPreferences: SharedPreferences = this.getSharedPreferences(PREE, MODE_PRIVATE)
                                       val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                       editor.putString("phoneData", phoneD)
                                       editor.commit()


                                       val mail1 = "yashranderi8@gmail.com"
                                       val subject1 = "join to health care app"
                                       val message1 = "i am " + selectDepartment + " specialist "


                                       /*  val intentMail = Intent(Intent.ACTION_SEND)
                                         intentMail.data = Uri.parse("mailto:")
                                         intentMail.type = "text/plain"
                                         intentMail.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail1))
                                         intentMail.putExtra(Intent.EXTRA_SUBJECT, subject1)
                                         intentMail.putExtra(Intent.EXTRA_TEXT, message1)


                                         Toast.makeText(this, "Send the mail to health care.", Toast.LENGTH_LONG)
                                             .show()


                                         try {
                                             startActivity(Intent.createChooser(intentMail, "Send Email"))
                                         } catch (e: Exception) {
                                             Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                                         }*/


                                       val intent = Intent(this@Doctor_Information, PLoginActivity::class.java)
                                       startActivity(intent)


                                       /* Handler().postDelayed({

                                            val intent=Intent(this@Doctor_Information,DLoginActivity::class.java)
                                            startActivity(intent)

                                        },7000)*/


                                   }
                               }


                           }

                   }


           }



       }

    }



    /*private fun saveImageStorage()
    {
        val p=editPhoneD.text.toString()

        storageRef =  FirebaseStorage.getInstance().getReference("Doctor").child(p!!)
            .child("image")



        storageRef.putFile(uri1!!).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Toast.makeText(this,"hiiiiii",Toast.LENGTH_LONG).show()




                Glide.with(this@Doctor_Information).load(uri1).into(doctorImg)




            }.addOnFailureListener{
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
        }

    }



    private fun setEditImage() {
        val p=editPhoneD.text.toString()
        Toast.makeText(this@Doctor_Information,"hi "+p,Toast.LENGTH_LONG).show()
        storageRef= FirebaseStorage.getInstance().getReference("Doctor").child(p!!).child("image")
        val localFile= File.createTempFile("temp","jpg")
        storageRef.getFile(localFile).addOnCompleteListener{

            val bitmap= BitmapFactory.decodeFile(localFile.absolutePath)
            doctorImg.setImageBitmap(bitmap)

        }
    }*/
}