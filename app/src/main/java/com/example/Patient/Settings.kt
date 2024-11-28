package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide.with
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference

class Settings : AppCompatActivity() {


    lateinit var editProfileP: TextView
    lateinit var paymentP: TextView
    lateinit var subscription: TextView
    lateinit var logoutP: TextView
    lateinit var btnYesP: TextView
    lateinit var btnNoP: TextView
    lateinit var stRef: StorageReference

    var phoneSetting :String?=null

    lateinit var settingPicture: ImageView
    lateinit var profileNmae: TextView
    lateinit var profileEmail: TextView
    lateinit var dataReference: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.hide()

        editProfileP=findViewById(R.id.txtSEditProfileP)
        paymentP=findViewById(R.id.txtSPaymentsP)
        subscription=findViewById(R.id.txtSSubscriptionP)
        logoutP=findViewById(R.id.txtSLogoutP)

        settingPicture=findViewById(R.id.setting_picture)

        profileNmae=findViewById(R.id.txtSettingName1)
        profileEmail=findViewById(R.id.txtSettingEmail1)


        //edit profile
        editProfileP.setOnClickListener {
            val intent= Intent(this, EditProfile::class.java)
            startActivity(intent)
        }



        paymentP.setOnClickListener {
            val intent= Intent(this, PaymentMethod::class.java)
            startActivity(intent)
        }


        subscription.setOnClickListener {

            val intent= Intent(this, MySubscription::class.java)
            startActivity(intent)
        }


        //logout app
        logoutP.setOnClickListener {

            //custom alert dialog
            val view= View.inflate(this, R.layout.custom_dialog5,null)
            val builder= AlertDialog.Builder(this)
            builder.setView(view)
            val dialog=builder.create()
            dialog.show()
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


            btnYesP= dialog.findViewById(R.id.yes)!!
            btnNoP=dialog.findViewById(R.id.no)!!

            btnYesP.setOnClickListener {

                val sharedPreferences: SharedPreferences =this.getSharedPreferences(PLoginActivity().share,0)
                val editor: SharedPreferences.Editor=sharedPreferences.edit()
                editor.clear()
                editor.apply()

                val intent=Intent(this, PLoginActivity::class.java)
                startActivity(intent)
                finish()

            }


            btnNoP.setOnClickListener {

                dialog.dismiss()
            }

        }


        var shareprefrence1 : SharedPreferences =this.getSharedPreferences(PSignupActivity().PREFF,AppCompatActivity.MODE_PRIVATE)
        var phoneSign= shareprefrence1.getString("phoneNoS","error").toString()

        //get phone no in login activity
        var shareprefrence2 : SharedPreferences = this.getSharedPreferences(PLoginActivity().PREF, AppCompatActivity.MODE_PRIVATE)
        var phoneLog= shareprefrence2.getString("phoneNoL","error").toString()



        if(!phoneSign.equals("error"))
        {
            phoneSetting=phoneSign
        }
        else
        {
            phoneSetting=phoneLog
        }



            dataReference= FirebaseDatabase.getInstance().getReference("Patient")
            dataReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val n=snapshot.child(phoneSetting!!).child("username").value
                    val e=snapshot.child(phoneSetting!!).child("email").value

                    profileNmae.setText(""+n)
                    profileEmail.setText(""+e)

                    //set image
                    val img=snapshot.child(phoneSetting!!).child("image").value
                    with(this@Settings).load(img).into(settingPicture)

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })






    }

}