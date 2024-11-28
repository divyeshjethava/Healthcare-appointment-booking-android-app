package com.example.Admin

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.healthcare.R
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Add_New_Offers : AppCompatActivity() {


    private lateinit var offersImg:ImageView
    private lateinit var editTitle:EditText

    private lateinit var btnAssOffers:TextView

    private lateinit var databaseReference: DatabaseReference
    private var storageRef= Firebase.storage
    private var uri:Uri?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_offers)

        supportActionBar?.hide()

        offersImg=findViewById(R.id.offers_img)
        editTitle=findViewById(R.id.edt_title)

        btnAssOffers=findViewById(R.id.btn_add_offers)


        var selectOfferImg=registerForActivityResult(ActivityResultContracts.GetContent()){

            uri= it
            offersImg.setImageURI(uri)

        }

        offersImg.setOnClickListener {

            selectOfferImg.launch("image/*")

        }


        btnAssOffers.setOnClickListener {

            var offerTitle=editTitle.text.toString()


            storageRef.getReference("Offer Image").child(offerTitle)
                .putFile(uri!!)
                .addOnSuccessListener { task ->

                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { uri ->

                            var offerUri=uri.toString()

                            databaseReference=FirebaseDatabase.getInstance().getReference("Offers")
                            databaseReference.child(offerTitle).setValue(Offers_Data(offerUri,offerTitle,"1")).addOnCompleteListener {


                                if (it.isSuccessful)
                                {
                                    Toast.makeText(this,"Add Department Successful", Toast.LENGTH_LONG).show()

                                }


                            }


                        }


                }


        }




    }
}