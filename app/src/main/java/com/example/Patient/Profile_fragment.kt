package com.example.Patient

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.healthcare.R
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class Profile_fragment : Fragment() {

    lateinit var editProfileP:TextView
    lateinit var paymentP:TextView
    lateinit var subscription:TextView
    lateinit var logoutP:TextView
    lateinit var btnYesP:TextView
    lateinit var btnNoP:TextView
    lateinit var stRef:StorageReference
    var pn :String?=null

    var phoneLog :String?=null
    var phoneSign :String?=null
    var conPhone:String?=null

    lateinit var profilePicture:ImageView
    lateinit var profileNmae:TextView
    lateinit var profileEmail:TextView
    lateinit var dataReference:DatabaseReference


    lateinit var settings:ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View
        view=inflater.inflate(R.layout.fragment_profile_fragment, container, false)



        editProfileP=view.findViewById(R.id.txtEditProfileP)
        paymentP=view.findViewById(R.id.txtPaymentsP)
        subscription=view.findViewById(R.id.txtSubscriptionP)
        logoutP=view.findViewById(R.id.txtLogoutP)

        profilePicture=view.findViewById(R.id.profile_picture)

        profileNmae=view.findViewById(R.id.profileName1)
        profileEmail=view.findViewById(R.id.profileEmail1)


        settings=view.findViewById(R.id.profile_setting)


        settings.setOnClickListener {

            val intent=Intent(requireContext(), Settings::class.java)
            startActivity(intent)
        }




        //edit profile
       editProfileP.setOnClickListener {
            val intent=Intent(requireContext(), EditProfile::class.java)
            startActivity(intent)
        }



        paymentP.setOnClickListener {
            val intent=Intent(requireContext(), PaymentMethod::class.java)
            startActivity(intent)
        }


        subscription.setOnClickListener {

            val intent=Intent(requireContext(), MySubscription::class.java)
            startActivity(intent)
        }



        //logout app
        logoutP.setOnClickListener {

            //custom alert dialog
            val view=View.inflate(requireContext(), R.layout.custom_dialog5,null)
            val builder= AlertDialog.Builder(requireContext())
            builder.setView(view)
            val dialog=builder.create()
            dialog.show()
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


            btnYesP= dialog.findViewById(R.id.yes)!!
            btnNoP=dialog.findViewById(R.id.no)!!

            btnYesP.setOnClickListener {

                val sharedPreferences: SharedPreferences =requireActivity().getSharedPreferences(
                    PLoginActivity().share,0)
                val editor: SharedPreferences.Editor=sharedPreferences.edit()
                editor.clear()
                editor.apply()

                val intent=Intent(requireContext(), PLoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()

            }


            btnNoP.setOnClickListener {

                dialog.dismiss()
            }

        }


        //val sh :SharedPreferences=requireActivity().getSharedPreferences(PLoginActivity().PREF,Context.MODE_PRIVATE)
        //pn=sh.getString("phoneNo","error").toString()

        //get phone no in login activity
        var shareprefrence1 : SharedPreferences = this.requireActivity().getSharedPreferences(
            PLoginActivity().PREF, AppCompatActivity.MODE_PRIVATE)
        phoneLog= shareprefrence1.getString("phoneNoL","error").toString()


        var shareprefrence2 : SharedPreferences =this.requireActivity().getSharedPreferences(
            PSignupActivity().PREFF,AppCompatActivity.MODE_PRIVATE)
        phoneSign= shareprefrence2.getString("phoneNoS","error").toString()



        if(!phoneLog.equals("error"))
        {
            conPhone=phoneLog
        }
        else
        {
            conPhone=phoneSign
        }



            dataReference=FirebaseDatabase.getInstance().getReference("Patient")
            dataReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val n=snapshot.child(conPhone!!).child("username").value
                    val e=snapshot.child(conPhone!!).child("email").value

                    profileNmae.setText(""+n)
                    profileEmail.setText(""+e)


                    //set image
                    val pPhoto=snapshot.child(conPhone!!).child("image").value

                    if (isAdded) {
                        Glide.with(requireContext()).load(pPhoto).into(profilePicture)
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }


            })




        return view
    }


    /*private fun setProfileImg() {

        stRef = FirebaseStorage.getInstance().getReference(pn!!).child("image")
        val localFile = File.createTempFile("temp","jpg")
        stRef.getFile(localFile).addOnSuccessListener {


            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            profile_pic.setImageBitmap(bitmap)
            //Glide.with(requireContext()).load(bitmap).into(profile_pic)


        }.addOnFailureListener{
            Toast.makeText(requireContext(),"some error in image",Toast.LENGTH_SHORT).show()
        }
    }*/





}