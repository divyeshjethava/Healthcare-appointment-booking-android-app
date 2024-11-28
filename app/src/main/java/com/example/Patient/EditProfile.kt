package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.healthcare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class EditProfile : AppCompatActivity() {

    lateinit var back1:ImageView
    lateinit var reference: DatabaseReference

    var storageReference= Firebase.storage

    lateinit var editNameP:EditText
    lateinit var editEmailP:EditText
    lateinit var editPhoneP:EditText

    //gender
    lateinit var txtGender:TextView
    var gender: String? =null
    lateinit var maleP:TextView
    lateinit var femaleP:TextView
    lateinit var otherP:TextView


    lateinit var editAgeP:EditText
    lateinit var editBirthDateP:EditText
    lateinit var editHeightP:EditText
    lateinit var editWeightP:EditText
    lateinit var editCityP:EditText

    lateinit var btnSaveP:TextView
    lateinit var btnCancleP:TextView

    var loginPhoneno1:String?=null
    var signupPhoneno1:String?=null
    var password1:String?=null

    lateinit var image:ImageView
    lateinit var imageP:String
    lateinit var addImg:ImageView
    var uri : Uri?=null
    lateinit var storage:FirebaseStorage
   // lateinit var storageReference:StorageReference



    var conformPhoneNo:String?=null



    private val selectImg =registerForActivityResult(ActivityResultContracts.GetContent()){
        uri=it
        image.setImageURI(uri)
    }


    @SuppressLint("MissingInflatedId", "ResourceAsColor", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        supportActionBar?.hide()

        storageReference=FirebaseStorage.getInstance()

        back1=findViewById(R.id.backEditProfile)

        editNameP=findViewById(R.id.editNameP1)
        editEmailP=findViewById(R.id.editEmailP1)
        editPhoneP=findViewById(R.id.editPhoneNoP1)

        //gender
        txtGender=findViewById(R.id.txt_gender)
        maleP=findViewById(R.id.txt_male)
        femaleP=findViewById(R.id.txt_female)
        otherP=findViewById(R.id.txt_other)


        editAgeP=findViewById(R.id.editAgeP1)
        editBirthDateP=findViewById(R.id.editBirthDateP1)
        editHeightP=findViewById(R.id.editHeightP1)
        editWeightP=findViewById(R.id.editWeightP1)
        editCityP=findViewById(R.id.editCityP1)


        btnSaveP=findViewById(R.id.Save)
        btnCancleP=findViewById(R.id.Cancle)


        image=findViewById(R.id.proImg)
        addImg=findViewById(R.id.editPicture)



        //add Image gallery
        addImg.setOnClickListener {

            selectImg.launch("image/*")

        }


        maleP.setOnClickListener {

            maleP.setBackgroundResource(R.drawable.button_shap5)
            femaleP.setBackgroundResource(R.drawable.button_shap3)
            otherP.setBackgroundResource(R.drawable.button_shap3)
            maleP.setTextColor(Color.BLACK)
            femaleP.setTextColor(Color.rgb(130, 217, 243))
            otherP.setTextColor(Color.rgb(130, 217, 243))

            gender="Male"
        }
        femaleP.setOnClickListener {

            maleP.setBackgroundResource(R.drawable.button_shap3)
            femaleP.setBackgroundResource(R.drawable.button_shap5)
            otherP.setBackgroundResource(R.drawable.button_shap3)
            femaleP.setTextColor(Color.BLACK)
            maleP.setTextColor(Color.rgb(130, 217, 243))
            otherP.setTextColor(Color.rgb(130, 217, 243))

            gender="Female"
        }
        otherP.setOnClickListener {

            maleP.setBackgroundResource(R.drawable.button_shap3)
            femaleP.setBackgroundResource(R.drawable.button_shap3)
            otherP.setBackgroundResource(R.drawable.button_shap5)
            otherP.setTextColor(Color.BLACK)
            maleP.setTextColor(Color.rgb(130, 217, 243))
            femaleP.setTextColor(Color.rgb(130, 217, 243))

            gender="Other"
        }


        back1.setOnClickListener {

            var intent=Intent(this@EditProfile, PHomePage::class.java)
            startActivity(intent)
        }





        //get phone no in signup page
        var sharedPreferences1:SharedPreferences=this.getSharedPreferences(PSignupActivity().PREFF, MODE_PRIVATE)
        signupPhoneno1=sharedPreferences1.getString("phoneNoS","error").toString()


        //get phone no in login page
        var sharedPreferences2:SharedPreferences=this.getSharedPreferences(PLoginActivity().PREF, MODE_PRIVATE)
        loginPhoneno1=sharedPreferences2.getString("phoneNoL","error").toString()


        //get password in login page
        var sharedPreferences3:SharedPreferences=this.getSharedPreferences(PSignupActivity().PREFF1, MODE_PRIVATE)
        password1=sharedPreferences3.getString("passwordS","error").toString()




            if(uri==null)
            {
                val drawable=ContextCompat.getDrawable(this, R.drawable.profile_picture2)
                image.setImageDrawable(drawable)
            }





        if(!loginPhoneno1.equals("error"))
        {
            conformPhoneNo=loginPhoneno1
        }
        else
        {
            conformPhoneNo=signupPhoneno1
        }




            reference=FirebaseDatabase.getInstance().getReference("Patient")
            reference.child(conformPhoneNo!!).get().addOnSuccessListener {

                if(it.exists())
                {
                    var name1=it.child("username").value
                    var email1=it.child("email").value
                    var phoneno1=it.child("phoneNo").value
                    var gender1=it.child("gender").value
                    var age1=it.child("age").value
                    var dob1=it.child("birthDate").value
                    var height1=it.child("height").value
                    var weight1=it.child("weight").value
                    var city1=it.child("city").value
                    imageP=it.child("image").value.toString()

                    editNameP.setText(""+name1)
                    editEmailP.setText(""+email1)
                    editPhoneP.setText(""+phoneno1)


                    gender=gender1.toString()

                    //gender
                    if(gender1=="Male")
                    {
                        maleP.setBackgroundResource(R.drawable.button_shap5)
                        maleP.setTextColor(Color.BLACK)
                    }
                    else if (gender1=="Female")
                    {
                        femaleP.setBackgroundResource(R.drawable.button_shap5)
                        femaleP.setTextColor(Color.BLACK)
                    }
                    else if (gender1=="Other")
                    {
                        otherP.setBackgroundResource(R.drawable.button_shap5)
                        otherP.setTextColor(Color.BLACK)
                    }

                    if(age1!=null && dob1!=null && height1!=null && weight1!=null && city1!=null)
                    {


                        editAgeP.setText(""+age1)
                        editBirthDateP.setText(""+dob1)
                        editHeightP.setText(""+height1)
                        editWeightP.setText(""+weight1)
                        editCityP.setText(""+city1)
                        //set Image
                        Glide.with(this).load(imageP).into(image)
                    }

                }
            }





        btnCancleP.setOnClickListener {

            val intent=Intent(this@EditProfile, PHomePage::class.java)
            startActivity(intent)
        }



        btnSaveP.setOnClickListener {


            val imageP=uri
            val nameP=editNameP.text.toString()
            val EmailP=editEmailP.text.toString()
            val PhoneP=editPhoneP.text.toString()

            val GenderP=gender
            val AgeP=editAgeP.text.toString()
            val DobP=editBirthDateP.text.toString()
            val HeightP=editHeightP.text.toString()
            val WeightP=editWeightP.text.toString()
            val CityP=editCityP.text.toString()
            val passP=password1


            if(nameP.isEmpty())
            {
                editNameP.setError("Enter Name")
            }
            else if(EmailP.isEmpty())
            {
                editEmailP.setError("Enter Email")
            }
            else if(PhoneP.isEmpty())
            {
                editPhoneP.setError("Enter phone no")
            }
            /*else if(GenderP==null)
            {
                txtGender.setError("Selece gender")
                Toast.makeText(this,"Select gender",Toast.LENGTH_SHORT).show()
            }*/
            else if(AgeP.isEmpty())
            {
                editAgeP.setError("Enter Age")
            }
            else if(DobP.isEmpty())
            {
                editBirthDateP.setError("Enter Birth Date")
            }
            else if(HeightP.isEmpty())
            {
                editHeightP.setError("Enter Height")
            }
            else if(WeightP.isEmpty())
            {
                editWeightP.setError("Enter Weight")
            }
            else if(CityP.isEmpty())
            {
                editCityP.setError("Enter City")
            }
            /*else if (uri==null){

                Toast.makeText(this@EditProfile,"please upload image",Toast.LENGTH_SHORT).show()
            }
            else if(PhoneP.length==10)
            {
                editPhoneP.setError("Enter Email")
            }*/
            else
            {
                //custom alert dialog
                val view= View.inflate(this@EditProfile, R.layout.custom_dialog,null)
                val builder= AlertDialog.Builder(this@EditProfile)
                builder.setView(view)
                val dialog=builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



                    /*val sref =  FirebaseStorage.getInstance().getReference(conformPhoneNo!!)
                    .child("image")


                sref.putFile(uri!!).addOnSuccessListener {
                    sref.downloadUrl.addOnSuccessListener {


                        //saveImage(it)
                        Glide.with(this@EditProfile).load(uri).into(image)


                    }.addOnFailureListener{
                        Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }*/

                storageReference.getReference("Patient").child(conformPhoneNo!!).child("image")
                    .putFile(uri!!)
                    .addOnSuccessListener { task ->

                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {uri ->




                                reference=FirebaseDatabase.getInstance().getReference("Patient")
                                reference.child(conformPhoneNo!!).setValue(PUserData(uri.toString(),nameP,EmailP,PhoneP, GenderP.toString(),AgeP,DobP,HeightP,WeightP,CityP,passP!!,1)).addOnCompleteListener {

                                    if(it.isSuccessful)
                                    {
                                        val inten = Intent(this@EditProfile, PHomePage::class.java)
                                        startActivity(inten)
                                        dialog.dismiss()
                                    }
                                }




                            }

                    }




            }





        }


    }

    /*private fun saveImage(it:Uri) {


        val storage = FirebaseStorage.getInstance()
        val imageRef = storage.reference.child(conformPhoneNo!!).child("image")


        imageRef.downloadUrl.addOnSuccessListener { uri ->

            val imageUrl = uri.toString()

            FirebaseDatabase.getInstance().getReference("Patient").child(conformPhoneNo!!).child("image").setValue(imageUrl).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"upload successfull",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"some error",Toast.LENGTH_SHORT).show()
                }
            }

        }.addOnFailureListener { exception ->
            // Handle error
            println("Error getting download URL: $exception")
        }



    }*/



}
