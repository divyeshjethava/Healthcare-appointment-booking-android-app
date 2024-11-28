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
import com.example.Admin.Admin_Home
import com.example.Doctor.DHomePage
import com.example.healthcare.CheckNetworkConnection
import com.example.healthcare.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import com.google.firebase.database.*

class PLoginActivity : AppCompatActivity() {

    lateinit var checkNetworkConnection: CheckNetworkConnection
    lateinit var dialog:AlertDialog
    lateinit var tryAgain:TextView




    lateinit var phoneP:EditText
    lateinit var passwordP:EditText
    lateinit var forgatePass : TextView
    lateinit var ref1:DatabaseReference
    lateinit var ref2:DatabaseReference
    lateinit var ref3:DatabaseReference
    lateinit var ref4:DatabaseReference
    lateinit var btnLoginP:TextView
    lateinit var phone:String
    lateinit var mauth: FirebaseAuth
    //lateinit var lottie:LottieAnimationView



    lateinit var backPage:ImageView
    lateinit var txtSignup : TextView

    val share="PREF"
    val shareA="PREFF"
    val shareD="PREFFF"
    val shareCA="PREFFFF"

    val PREF ="hello"
    val PREF1 ="hello"

    val DPREF="dHello"

    lateinit var pGoogleLogin:ImageView
    lateinit var client:GoogleSignInClient






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plogin)

        supportActionBar?.hide()


        callNetworkConnection()

        //custom alert dialog
        val view= View.inflate(this@PLoginActivity, R.layout.custom_dialog7,null)
        val builder= AlertDialog.Builder(this@PLoginActivity)
        builder.setView(view)
        dialog=builder.create()
        tryAgain=view.findViewById(R.id.btn_try_again)
        //dialog.dismiss()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)




        mauth= FirebaseAuth.getInstance()





        forgatePass=findViewById(R.id.forgotPasswordP)
        txtSignup=findViewById(R.id.textSignupP)
        phoneP=findViewById(R.id.edtEmailP)
        passwordP=findViewById(R.id.edtPasswordP)
        btnLoginP=findViewById(R.id.btnLoginP)
        //lottie=findViewById(R.id.loading1)

        backPage=findViewById(R.id.back)

        pGoogleLogin=findViewById(R.id.googlePL)







        forgatePass.setOnClickListener {
            val intent = Intent(this@PLoginActivity, ForgotPassword1::class.java)
            startActivity(intent)
        }

        txtSignup.setOnClickListener {
            val intent=Intent(this@PLoginActivity, PSignupActivity::class.java)
            startActivity(intent)
        }



        //verify data
        btnLoginP.setOnClickListener {

            var pPhone =phoneP.text.toString()
            var pPassword = passwordP.text.toString()


            //phone no
            val shareprefrence : SharedPreferences=this.getSharedPreferences(PREF, MODE_PRIVATE)
            val editor1:SharedPreferences.Editor =shareprefrence.edit()
            editor1.putString("phoneNoL",pPhone)
            editor1.apply()


            //password
            val shareprefrence2 : SharedPreferences=this.getSharedPreferences(PREF1, MODE_PRIVATE)
            val editor2:SharedPreferences.Editor =shareprefrence2.edit()
            editor2.putString("password",pPassword)
            editor2.apply()


            //Doctor phone no
            val sharedPreference3:SharedPreferences=this.getSharedPreferences(DPREF, MODE_PRIVATE)
            val editor3:SharedPreferences.Editor=sharedPreference3.edit()
            editor3.putString("dPhoneNoL",pPhone)
            editor3.apply()

            val pPhone1=pPhone.length


           if(pPhone!!.isEmpty())
           {
                phoneP.setError("Enter the Phone No")
           }
           else if(!pPhone1.equals(10))
           {
               phoneP.setError("Invalid Phone No")
           }
            else if(pPassword!!.isEmpty())
            {
                passwordP.setError("Enter the password")
            }else
            {
                //lottie.visibility= View.VISIBLE
                //lottie.playAnimation()

                //custom alert dialog
                val view=View.inflate(this@PLoginActivity, R.layout.custom_dialog,null)
                val builder=AlertDialog.Builder(this@PLoginActivity)
                builder.setView(view)
                val dialog=builder.create()
                dialog.show()
                dialog.setCanceledOnTouchOutside(false)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


                ref1 =FirebaseDatabase.getInstance().getReference("Patient")
                ref1.child(pPhone!!).get().addOnSuccessListener {
                    if (it.exists()){





                        val mob =it.child("phoneNo").value.toString()
                        val ps=it.child("password").value.toString()


                        if(!pPhone.equals(mob))
                        {
                            phoneP.setError("invalid number")
                        }
                        else if(!pPassword.equals(ps))
                        {
                            passwordP.setError("invalid password")
                            dialog.dismiss()
                        }
                        else
                        {
                            dialog.dismiss()




                            //mauth.signInWithEmailAndPassword(pEmail,pPassword).addOnCompleteListener {
                              //  if(it.isSuccessful){
                                //    val sharedPreferences: SharedPreferences =this.getSharedPreferences(share,0)
                                  //  val editor: SharedPreferences.Editor=sharedPreferences.edit()
                                    //editor.putBoolean("HasLogin",true);
                                    //editor.commit();


                                    //lottie.visibility=View.INVISIBLE
                                    //lottie.pauseAnimation()


                            val sharedPreferences: SharedPreferences =this.getSharedPreferences(share,0)
                            val editor: SharedPreferences.Editor=sharedPreferences.edit()
                            editor.putBoolean("HasLogin",true);
                            editor.commit();


                                    val inten = Intent(this@PLoginActivity, PHomePage::class.java)
                                    startActivity(inten)
                                    dialog.dismiss()
                                    finish()


                                //}else{
                                  //  emailP.setError("Email is invalid")
                                  //  dialog.dismiss()
                                  //  Toast.makeText(this@PLoginActivity,"Email is invalid...",Toast.LENGTH_LONG).show()
                                //}
                            //}
                        }
                    }
                    else
                    {



                        ref2 =FirebaseDatabase.getInstance().getReference("Admin")
                        ref2.child(pPhone!!).get().addOnSuccessListener {
                            if (it.exists()) {




                                var mob = it.child("phoneNo").value.toString()
                                var ps = it.child("password").value.toString()
                                var status=it.child("status").value.toString()



                                if (!pPhone.equals(mob))
                                {
                                    phoneP.setError("invalid number")
                                }
                                else if(!pPassword.equals(ps))
                                {
                                    passwordP.setError("invalid password")
                                }
                                else if (status.equals("8"))
                                {

                                    val sharedPreferences: SharedPreferences = this.getSharedPreferences(shareA, 0)
                                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                    editor.putBoolean("HasLoginA", true)
                                    editor.commit()



                                    val inten = Intent(this@PLoginActivity, Admin_Home::class.java)
                                    startActivity(inten)
                                    dialog.dismiss()
                                    finish()

                                }
                            }
                            else
                            {




                                ref3 =FirebaseDatabase.getInstance().getReference("Doctor")
                                ref3.child("Add Doctor").child(pPhone!!).get().addOnSuccessListener {
                                    if (it.exists()) {




                                        val mob = it.child("phoneNo").value.toString()
                                        val ps = it.child("password").value.toString()


                                        if (!pPhone.equals(mob))
                                        {
                                            phoneP.setError("invalid number")
                                        }
                                        else if (!pPassword.equals(ps))
                                        {
                                            passwordP.setError("invalid password")
                                        }
                                        else {
                                            dialog.dismiss()


                                            //mauth.signInWithEmailAndPassword(pEmail,pPassword).addOnCompleteListener {
                                            //  if(it.isSuccessful){
                                            //    val sharedPreferences: SharedPreferences =this.getSharedPreferences(share,0)
                                            //  val editor: SharedPreferences.Editor=sharedPreferences.edit()
                                            //editor.putBoolean("HasLogin",true);
                                            //editor.commit();


                                            //lottie.visibility=View.INVISIBLE
                                            //lottie.pauseAnimation()


                                            val sharedPreferences: SharedPreferences = this.getSharedPreferences(shareD, 0)
                                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                            editor.putBoolean("HasLoginD", true)
                                            editor.commit()


                                            val inten = Intent(this@PLoginActivity, DHomePage::class.java)
                                            startActivity(inten)
                                            dialog.dismiss()
                                            finish()


                                            //}else{
                                            //  emailP.setError("Email is invalid")
                                            //  dialog.dismiss()
                                            //  Toast.makeText(this@PLoginActivity,"Email is invalid...",Toast.LENGTH_LONG).show()
                                            //}
                                            //}
                                        }
                                    }
                                    else
                                    {



                                        ref4=FirebaseDatabase.getInstance().getReference("Admin")
                                        ref4.child("Child Admin").child(pPhone).get().addOnSuccessListener {


                                            if (it.exists())
                                            {

                                                Toast.makeText(this@PLoginActivity,"User Not Exists...",Toast.LENGTH_LONG).show()


                                                val mob = it.child("aphone").value.toString()
                                                val ps = it.child("apassword").value.toString()
                                                val status = it.child("astatus").value.toString()




                                                if (!pPhone.equals(mob))
                                                {
                                                    phoneP.setError("invalid number")
                                                }
                                                else if (!pPassword.equals(ps))
                                                {
                                                    passwordP.setError("invalid password")
                                                }
                                                else if(status.equals("7"))
                                                {
                                                    dialog.dismiss()


                                                    val sharedPreferences: SharedPreferences = this.getSharedPreferences(shareCA, 0)
                                                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                                    editor.putBoolean("HasLoginCA", true)
                                                    editor.commit()


                                                    val inten = Intent(this@PLoginActivity, Admin_Home::class.java)
                                                    startActivity(inten)
                                                    dialog.dismiss()
                                                    finish()

                                                }





                                            }
                                            else
                                            {
                                                dialog.dismiss()

                                                Toast.makeText(this@PLoginActivity,"User Not Exists...",Toast.LENGTH_LONG).show()
                                            }

                                        }
                                    }

                                }
                            }
                        }



//                        Toast.makeText(this@PLoginActivity,"User are not exists...",Toast.LENGTH_LONG).show()
//                        dialog.dismiss()
                    }
                }






            }



        }


        backPage.setOnClickListener{
            val intent=Intent(this@PLoginActivity, SelectField::class.java)
            startActivity(intent)
        }




        //google login
        val options=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client=GoogleSignIn.getClient(this,options)
        pGoogleLogin.setOnClickListener {

            val intent=client.signInIntent
            startActivityForResult(intent,10001)
        }

    }

    private fun alertDialog() {
        TODO("Not yet implemented")

        val view=View.inflate(this@PLoginActivity, R.layout.custom_dialog,null)

        val builder=AlertDialog.Builder(this@PLoginActivity)
        builder.setView(view)

        val dialog=builder.create()
        dialog.show()

    }

    //google login
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode==10001)
        {
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            val account=task.getResult(ApiException::class.java)
            val credential=GoogleAuthProvider.getCredential(account.idToken,null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {task->
                    if(task.isSuccessful)
                    {
                        val i=Intent(this, PHomePage::class.java)
                        startActivity(i)
                    }
                    else
                    {
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                    }
                }
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
                    Toast.makeText(this@PLoginActivity,"Please connect your network", Toast.LENGTH_LONG).show()
                }
            }

        })
    }

}