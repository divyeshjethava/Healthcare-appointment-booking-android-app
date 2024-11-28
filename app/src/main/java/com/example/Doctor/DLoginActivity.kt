package com.example.Doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.Patient.PHomePage
import com.example.Patient.SelectField
import com.example.healthcare.R
import com.google.firebase.auth.FirebaseAuth

class DLoginActivity : AppCompatActivity() {

    lateinit var backPageD:ImageView
    lateinit var txtSignupD:TextView

    lateinit var emailD:EditText
    lateinit var passwordD:EditText

    lateinit var btnLoginD:TextView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dlogin)

        supportActionBar?.hide()

        backPageD=findViewById(R.id.back)

        txtSignupD=findViewById(R.id.textSignupD)
        btnLoginD=findViewById(R.id.btnLoginD)

        emailD=findViewById(R.id.edtEmailD)
        passwordD=findViewById(R.id.edtPasswordD)

        auth=FirebaseAuth.getInstance()


        backPageD.setOnClickListener {

            val intent=Intent(this@DLoginActivity, SelectField::class.java)
            startActivity(intent)
        }



        txtSignupD.setOnClickListener {

           val inten = Intent(this@DLoginActivity, DSignupActivity::class.java)
            startActivity(inten)
        }

        btnLoginD.setOnClickListener {

            val intent=Intent(this@DLoginActivity, PHomePage::class.java)
            startActivity(intent)
        }


        btnLoginD.setOnClickListener {

            val dEmail=emailD.text.toString()
            val dPassword=passwordD.text.toString()

            if(dEmail.isEmpty())
            {
                emailD.setError("Enter the email")
            }
            else if(dPassword.isEmpty())
            {
                passwordD.setError("Enter the password")
            }
            else
            {
                //custom alert dialog
                val view= View.inflate(this@DLoginActivity, R.layout.custom_dialog,null)
                val builder= AlertDialog.Builder(this@DLoginActivity)
                builder.setView(view)
                val dialog=builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


                auth.createUserWithEmailAndPassword(dEmail,dPassword).addOnCompleteListener(this) {

                    if(it.isSuccessful)
                    {

                    }

                }
            }
        }
    }
}