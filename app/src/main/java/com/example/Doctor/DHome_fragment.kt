package com.example.Doctor

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Picture
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.DialogCompat
import com.bumptech.glide.Glide
import com.example.Patient.PLoginActivity
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DHome_fragment : Fragment() {


    private lateinit var dHomeName:TextView
    private lateinit var dHomeImage: ImageView

    private lateinit var databaseReference:DatabaseReference
    private lateinit var dPhoneNo:String

    private lateinit var dialog:Dialog
    private lateinit var image:String
    private lateinit var name:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View=inflater.inflate(R.layout.fragment_d_home_fragment, container, false)


        dHomeName=view.findViewById(R.id.d_user_nameHome)
        dHomeImage=view.findViewById(R.id.d_home_picture)


        var sharedPreferences:SharedPreferences=this.requireActivity().getSharedPreferences(PLoginActivity().DPREF,AppCompatActivity.MODE_PRIVATE)
        dPhoneNo=sharedPreferences.getString("dPhoneNoL","error").toString()




        databaseReference=FirebaseDatabase.getInstance().getReference("Doctor")
        databaseReference.child("Add Doctor").child(dPhoneNo).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                name=snapshot.child("username").value.toString()
                dHomeName.setText("Dr. $name")

                image=snapshot.child("doctorImg").value.toString()

                if (isAdded)
                {
                    Glide.with(requireContext()).load(image).into(dHomeImage)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        dHomeImage.setOnClickListener {

            val view= View.inflate(requireContext(), R.layout.large_image_dialog,null)
            val builder= AlertDialog.Builder(requireContext())
            builder.setView(view)
            dialog=builder.create()

            val largeImage=view.findViewById<ImageView>(R.id.large_picture)
            val docName=view.findViewById<TextView>(R.id.dialog_doc_name)

            Glide.with(requireContext()).load(image).into(largeImage)
            docName.setText("Dr. $name")

            //dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

        }




        return view
    }

}