package com.example.Doctor

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.Patient.PLoginActivity
import com.example.Patient.SelectField
import com.example.healthcare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DSignupActivity : AppCompatActivity() {

    lateinit var backPage:ImageView
    lateinit var txtLogin:TextView

    lateinit var edtUserNameD:EditText
    lateinit var edtEmailD:EditText
    lateinit var edtPhonenoD:EditText
    lateinit var edtPasswordD:EditText
    lateinit var edtConPasswordD:EditText
    lateinit var btnSignupD:TextView

    lateinit var reference:DatabaseReference

    lateinit var auth:FirebaseAuth

    //validate
    val userNameValidate:String="[a-zA-z]+"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsignup)

        supportActionBar?.hide()


        backPage=findViewById(R.id.back)
        txtLogin=findViewById(R.id.textLogin)

        edtUserNameD=findViewById(R.id.edtUserNameD)
        edtEmailD=findViewById(R.id.edtEmailD)
        edtPhonenoD=findViewById(R.id.edtPhoneNoD)
        edtPasswordD=findViewById(R.id.edtPasswordD)
        edtConPasswordD=findViewById(R.id.edtConPasswordD)

        btnSignupD=findViewById(R.id.btnSignupD)

        auth=FirebaseAuth.getInstance()





        backPage.setOnClickListener{

            val intent=Intent(this@DSignupActivity, SelectField::class.java)
            startActivity(intent)
        }

        txtLogin.setOnClickListener{

            val intent=Intent(this@DSignupActivity, PLoginActivity::class.java)
            startActivity(intent)

        }









        btnSignupD.setOnClickListener {

            var userName=edtUserNameD.text.toString()
            var email=edtEmailD.text.toString()
            var phoneNo=edtPhonenoD.text.toString()
            var password=edtPasswordD.text.toString()
            var conPassword=edtConPasswordD.text.toString()


            if(userName.isEmpty() && email.isEmpty() && phoneNo.isEmpty() && password.isEmpty() && conPassword.isEmpty())
            {
                edtUserNameD.setError("Please Enter Username")
                edtEmailD.setError("Please Enter Email")
                edtPhonenoD.setError("Please Enter Phone No")
                edtPasswordD.setError("Please Enter Password")
                edtConPasswordD.setError("Please Enter Conform Password")

                /*Toast.makeText(this,"hi "+d_doctor_img.toString()+" "+userName+" "+email+" "+phoneNo+" "+
                    d_gender.toString()+" "+ d_age.toString()+" "+ d_birthDate.toString()+" "+ d_department.toString()+" "+
                    d_degree.toString()+" "+ d_location.toString()+" "+
                    d_city.toString()+" "+ d_certificate_img.toString()+"  "+password,Toast.LENGTH_LONG).show()*/

            }
            else if(!phoneNo.length.equals(10))
            {
                edtPhonenoD.setError("Enter Correct Phone no")
            }
            else if (!password.equals(conPassword))
            {
                edtConPasswordD.setError("Password are not match")
            }
            else
            {
                //custom alert dialog
                val view= View.inflate(this@DSignupActivity, R.layout.custom_dialog,null)
                val builder= AlertDialog.Builder(this@DSignupActivity)
                builder.setView(view)
                val dialog=builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


                reference=FirebaseDatabase.getInstance().getReference("Doctor")
                reference.child("Signup Doctor").child(phoneNo).setValue(
                    DUserData(userName,email,phoneNo,password,0)).addOnCompleteListener {


                    //auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {

                        if(it.isSuccessful)
                        {
                            val intent=Intent(this@DSignupActivity, Doctor_Information::class.java)
                            intent.putExtra("pho",phoneNo)
                            startActivity(intent)
                            dialog.dismiss()
                        }

                    //}



                }
            }



        }

    }
}

