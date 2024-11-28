package com.example.Patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.healthcare.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class ForgotPassword1 : AppCompatActivity() {

    lateinit var backPageF:ImageView

    var temp=true
    val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    lateinit var another_way:TextView
    lateinit var verify_no_email:EditText
    lateinit var change:TextView
    lateinit var ref:DatabaseReference
    lateinit var auth: FirebaseAuth

    lateinit var BtnSendP:TextView
    lateinit var phoneno1:String

    lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()

        backPageF=findViewById(R.id.imageView3)

        another_way=findViewById(R.id.try_another_way)
        verify_no_email=findViewById(R.id.edtForgotMobNo)
        change=findViewById(R.id.textChange)
        auth=FirebaseAuth.getInstance()



        BtnSendP=findViewById(R.id.btnSendP)


        //back page intent
        backPageF.setOnClickListener {

            val  intent=Intent(this@ForgotPassword1, PLoginActivity::class.java)
            startActivity(intent)
        }


        another_way.setOnClickListener {

            if(temp==true)
            {
                change.setText("Enter Your Email Id")
                verify_no_email.hint="Example@gmail.com"

                temp=false
            }
            else
            {
                change.setText("Enter Your Mobile Number")
                verify_no_email.hint="+91 0000000000"

                temp=true
            }


        }







        BtnSendP.setOnClickListener {

          phoneno1 = verify_no_email.text.toString()


                if(phoneno1.isEmpty())
                {
                    verify_no_email.setError("Please Enter PhoneNo")
                }
                else if (!phoneno1.length.equals(10)){

                    verify_no_email.setError("enter the correct number")
                }
                else{

                    //custom alert dialog
                    val view=View.inflate(this@ForgotPassword1, R.layout.custom_dialog4,null)
                    val builder= AlertDialog.Builder(this@ForgotPassword1)
                    builder.setView(view)
                    dialog=builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



                    ref=FirebaseDatabase.getInstance().getReference("Patient")
                    ref.child(phoneno1).get().addOnSuccessListener {
                        if(it.exists())
                        {
                            var pn = it.child("phoneNo").value


                            if (phoneno1.equals(pn))
                            {
                                phoneno1="+91"+phoneno1
                                val options= PhoneAuthOptions.newBuilder(auth)
                                    .setPhoneNumber(phoneno1)
                                    .setTimeout(60L, TimeUnit.SECONDS)
                                    .setActivity(this@ForgotPassword1)
                                    .setCallbacks(callback)
                                    .build()
                                PhoneAuthProvider.verifyPhoneNumber(options)


                            }


                        }
                        else{
                            dialog.dismiss()
                            verify_no_email.setError("mobile no invalid")
                        }
                    }





                }
        }





        /*btnSendP.setOnClickListener {




            if(temp==true)
            {


                ref.child(phoneno1).get().addOnSuccessListener {
                    if(it.exists())
                    {
                        pn = it.child("phoneNo").value as String?
                    }
                }




                if(phoneno1.isEmpty())
                {
                    verify_no_email.setError("Please Enter PhoneNo")
                }
                else if (!phoneno1.length.equals(10)){

                    verify_no_email.setError("enter the correct number")
                }
                else if(!phoneno1.equals(pn)){

                    verify_no_email.setError("this Mobile number is not valid")
                }
                else
                {

                    //custom alert dialog
                    val view= View.inflate(this@ForgotPassword1,R.layout.custom_dialog,null)
                    val builder= AlertDialog.Builder(this@ForgotPassword1)
                    builder.setView(view)
                    val dialog=builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                    phoneno1="+91"+phoneno1
                    val options= PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneno1)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this@ForgotPassword1)
                        .setCallbacks(callback)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)

                    dialog.dismiss()

                }
                temp=false
            }
            else
            {
                var email1=verify_no_email.text.toString()

                if(email1.isEmpty())
                {
                    verify_no_email.setError("Please Enter Email")
                }
                else
                {
                    val intent=Intent(this@ForgotPassword1,ForgotPassword2::class.java)
                    intent.putExtra("phoneNo1",email1)
                    startActivity(intent)
                }
                temp=true
            }

        }*/





    }
    private val callback =object :PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWith(credential)
        }


        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(this@ForgotPassword1,"some error",Toast.LENGTH_SHORT).show()

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {

            val intent = Intent(this@ForgotPassword1, ForgotPassword2::class.java)
            intent.putExtra("otp", verificationId)
            intent.putExtra("token", token)

            intent.putExtra("pn",phoneno1 )
            startActivity(intent)
            dialog.dismiss()
        }
    }


    private fun signInWith(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this@ForgotPassword1,"Authentication successfuly", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@ForgotPassword1,"some error",Toast.LENGTH_SHORT).show()
            }
        }
    }


    }

