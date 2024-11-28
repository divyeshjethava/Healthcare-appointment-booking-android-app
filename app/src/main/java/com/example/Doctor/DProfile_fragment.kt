package com.example.Doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.Patient.PLoginActivity
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DProfile_fragment : Fragment() {

    private lateinit var dProfileImg:ImageView
    private lateinit var dProfileName:TextView
    private lateinit var dProfileEmail: TextView

    private lateinit var databaseReference: DatabaseReference
    private lateinit var dPhone:String


    private lateinit var dSetting:LinearLayout
    private lateinit var dEditPro:LinearLayout

    private lateinit var dLogout:LinearLayout
    private lateinit var btnYesD:TextView
    private lateinit var btnNoD:TextView



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

        val view:View=inflater.inflate(R.layout.fragment_d_profile_fragment, container, false)

        dProfileImg=view.findViewById(R.id.d_profile_picture)
        dProfileName=view.findViewById(R.id.d_profile_name)
        dProfileEmail=view.findViewById(R.id.d_profile_email)

        dSetting=view.findViewById(R.id.d_list_setting)
        dEditPro=view.findViewById(R.id.d_list_edit_profile)

        dLogout=view.findViewById(R.id.d_list_logout)



        val sharedPreferences:SharedPreferences=requireActivity().getSharedPreferences(PLoginActivity().DPREF,AppCompatActivity.MODE_PRIVATE)
        dPhone=sharedPreferences.getString("dPhoneNoL","error").toString()

        databaseReference=FirebaseDatabase.getInstance().getReference("Doctor")
        databaseReference.child("Add Doctor").child(dPhone).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val image=snapshot.child("doctorImg").value
                val name=snapshot.child("username").value
                val email=snapshot.child("email").value


                if(isAdded)
                {
                    Glide.with(requireContext()).load(image).into(dProfileImg)
                }


                dProfileName.setText("Dr. $name")
                dProfileEmail.setText("$email")

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        dEditPro.setOnClickListener {

            val intent= Intent(requireContext(),DEditProfile::class.java)
            requireActivity().startActivity(intent)

        }



        dLogout.setOnClickListener {

            //custom alert dialog
            val view=View.inflate(requireContext(), R.layout.custom_dialog5,null)
            val builder= AlertDialog.Builder(requireContext())
            builder.setView(view)
            val dialog=builder.create()
            dialog.show()
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


            btnYesD= dialog.findViewById(R.id.yes)!!
            btnNoD=dialog.findViewById(R.id.no)!!

            btnYesD.setOnClickListener {

                val sharedPreferences: SharedPreferences =requireActivity().getSharedPreferences(PLoginActivity().shareD,0)
                val editor: SharedPreferences.Editor=sharedPreferences.edit()
                editor.clear()
                editor.apply()

                val intent=Intent(requireContext(), PLoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()

            }


            btnNoD.setOnClickListener {

                dialog.dismiss()
            }


        }


        return view
    }

}