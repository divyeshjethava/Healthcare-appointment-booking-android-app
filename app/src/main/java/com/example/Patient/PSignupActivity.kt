package com.example.Patient

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.healthcare.CheckNetworkConnection
import com.example.healthcare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PSignupActivity : AppCompatActivity() {


    lateinit var checkNetworkConnection: CheckNetworkConnection
    lateinit var dialog:AlertDialog
    lateinit var tryAgain:TextView


    lateinit var references :DatabaseReference
    lateinit var edtUserNameP:EditText
    lateinit var edtEmailP:EditText
    lateinit var edtPhoneNoP:EditText
    lateinit var edtPasswordP:EditText
    lateinit var edtCPasswordP:EditText
    val PREFF="helloo"
    val PREFF1="helloo"


    lateinit var btnSignupP:TextView
    lateinit var mAuth:FirebaseAuth
    lateinit var backPage:ImageView
    lateinit var txtLogin:TextView
    //lateinit var loading:LottieAnimationView

    var emailPattern="^[a-zA-Z0-9]{1,20}@[a-z0-9]{1,20}.[a-zA-Z]{2,3}\$\""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psignup)
        supportActionBar?.hide()


        callNetworkConnection()

        //custom alert dialog
        val view= View.inflate(this@PSignupActivity, R.layout.custom_dialog7,null)
        val builder= AlertDialog.Builder(this@PSignupActivity)
        builder.setView(view)
        dialog=builder.create()
        tryAgain=view.findViewById(R.id.btn_try_again)
        //dialog.dismiss()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



        edtUserNameP=findViewById(R.id.edtUserNameP)
        edtEmailP=findViewById(R.id.edtEmailP)
        edtPhoneNoP=findViewById(R.id.edtPhoneNoP)
        edtPasswordP=findViewById(R.id.edtPasswordP)
        edtCPasswordP=findViewById(R.id.edtConPasswordP)
        //loading=findViewById(R.id.loading)

        btnSignupP=findViewById(R.id.btnSignupP)

        backPage=findViewById(R.id.back)
        txtLogin=findViewById(R.id.textLoginP)

        mAuth= FirebaseAuth.getInstance()






        btnSignupP.setOnClickListener {

            val userName=edtUserNameP.text.toString()
            val email=edtEmailP.text.toString()
            val phone=edtPhoneNoP.text.toString()
            val password=edtPasswordP.text.toString()
            var conPassword=edtCPasswordP.text.toString()





            if(userName.isEmpty())
            {
                edtUserNameP.setError("Enter Username")
            }
            else if(email.isEmpty())
            {
                edtEmailP.setError("Enter Email")
            }
            else if(phone.isEmpty())
            {
                edtPhoneNoP.setError("Enter phone no")
            }
            else if(password.isEmpty())
            {
                edtPasswordP.setError("Enter password")
            }
            else if(conPassword.isEmpty())
            {
                edtCPasswordP.setError("Enter Conform password")
            }
            //else if(!email.matches(emailPattern))
            //{
            //    edtEmail.setError("Invalid Email")
            //}
            else if(!phone.length.equals(10))
            {
                edtPhoneNoP.setError("Invalid Phone No")
            }
            //else if(!password.length.equals(6))
            //{
            //    edtPasswordP.setError("6 letter is complsory")
            //}
            else if(!password.equals(conPassword))
            {
                edtCPasswordP.setError("Invalid Conform password")
            }
            else
            {
                //loading.visibility=View.VISIBLE
                //loading.playAnimation()


                //custom alert dialog
                val view=View.inflate(this@PSignupActivity, R.layout.custom_dialog,null)
                val builder= AlertDialog.Builder(this@PSignupActivity)
                builder.setView(view)
                val dialog=builder.create()
                dialog.show()
                dialog.setCanceledOnTouchOutside(false)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



                references=FirebaseDatabase.getInstance().getReference("Patient")
                references.child(phone).setValue(PUserData(userName,email,phone,password)).addOnCompleteListener {


                    val sharedPreferences:SharedPreferences=this.getSharedPreferences(PREFF, 0)
                    val editor:SharedPreferences.Editor=sharedPreferences.edit()
                    editor.putString("phoneNoS",phone)
                    editor.apply()


                    val sharedPreferences1:SharedPreferences=this.getSharedPreferences(PREFF1, MODE_PRIVATE)
                    val editor1:SharedPreferences.Editor=sharedPreferences1.edit()
                    editor1.putString("passwordS",password)
                    editor1.apply()


                    val intent = Intent(this@PSignupActivity, EditProfile::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                    finish()


                }
            }







        }

        backPage.setOnClickListener{

            val intent =Intent(this@PSignupActivity, PLoginActivity::class.java)
            startActivity(intent)
        }
        txtLogin.setOnClickListener{

            val intent=Intent(this@PSignupActivity, PLoginActivity::class.java)
            startActivity(intent)
        }


    }




    private fun callNetworkConnection() {
        checkNetworkConnection= CheckNetworkConnection(application)
        checkNetworkConnection.observe(this,{ isConnected ->

            if(isConnected)
            {
                dialog.dismiss()
            }
            else
            {
                dialog.show()
                tryAgain.setOnClickListener {
                    Toast.makeText(this@PSignupActivity,"Please connect your network", Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}
