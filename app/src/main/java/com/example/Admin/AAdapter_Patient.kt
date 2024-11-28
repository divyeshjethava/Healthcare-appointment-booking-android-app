package com.example.Admin

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Patient.PUserData
import com.example.healthcare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class AAdapter_Patient(val context: Context,val patientList:ArrayList<PUserData>) : RecyclerView.Adapter<AAdapter_Patient.patientViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): patientViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.admin_patient_list, parent,false)
        return patientViewHolder(itemView)

    }

    override fun getItemCount(): Int {

        return patientList.size
    }

    override fun onBindViewHolder(holder: patientViewHolder, position: Int) {

        val currentList=patientList[position]

        Glide.with(context).load(currentList.image).into(holder.pImage)

        holder.pName.setText(currentList.username)
        holder.pPhone.setText(currentList.phoneNo)


        /*val pImg:String= currentList.image.toString()
        val pName:String=currentList.username.toString()
        val pEmail:String=currentList.email.toString()
        val pPhone:String=currentList.phoneNo.toString()
        val pGender:String=currentList.gender.toString()
        val pAge:String=currentList.age.toString()
        val pDob:String=currentList.birthDate.toString()
        val pHeight:String=currentList.height.toString()
        val pWidth:String=currentList.weight.toString()
        val pCity:String=currentList.city.toString()
        val pPass:String=currentList.password.toString()*/



        /*holder.pStatus.setOnClickListener {

            if(currentList.pStatus==0)
            {
                val databaseReference:DatabaseReference
                databaseReference=FirebaseDatabase.getInstance().getReference("Patient")
                databaseReference.child(currentList.phoneNo.toString()).setValue(PUserData(pImg,pName,pEmail,pPhone,pGender,pAge,pDob,pHeight,pWidth,pCity,pPass,1)).addOnSuccessListener {

                    val databaseReference:DatabaseReference
                    databaseReference=FirebaseDatabase.getInstance().getReference("Patient")
                    databaseReference.child("Status 1").child(currentList.phoneNo.toString()).setValue(PUserData(pImg,pName,pEmail,pPhone,pGender,pAge,pDob,pHeight,pWidth,pCity,pPass,1)).addOnSuccessListener {


                        val databaseReference:DatabaseReference
                        databaseReference=FirebaseDatabase.getInstance().getReference("Patient")
                        databaseReference.child("Status 0").child(currentList.phoneNo.toString()).removeValue().addOnSuccessListener {



                        }

                    }


                }
            }
            else if (currentList.pStatus==1)
            {
                val databaseReference:DatabaseReference
                databaseReference=FirebaseDatabase.getInstance().getReference("Patient")
                databaseReference.child(currentList.phoneNo.toString()).setValue(PUserData(pImg,pName,pEmail,pPhone,pGender,pAge,pDob,pHeight,pWidth,pCity,pPass,0)).addOnSuccessListener {

                    val databaseReference:DatabaseReference
                    databaseReference=FirebaseDatabase.getInstance().getReference("Patient")
                    databaseReference.child("Status 0").child(currentList.phoneNo.toString()).setValue(PUserData(pImg,pName,pEmail,pPhone,pGender,pAge,pDob,pHeight,pWidth,pCity,pPass,0)).addOnSuccessListener {


                        val databaseReference:DatabaseReference
                        databaseReference=FirebaseDatabase.getInstance().getReference("Patient")
                        databaseReference.child("Status 1").child(currentList.phoneNo.toString()).removeValue().addOnSuccessListener {



                        }


                    }


                }
            }

        }*/


        /*if(currentList.pStatus==0)
        {
            holder.pStatus.setText("Add")
            holder.pStatus.setTextColor(ContextCompat.getColor(context,R.color.green))
        }
        else if (currentList.pStatus==1)
        {
            holder.pStatus.setText("Cancel")
            holder.pStatus.setTextColor(ContextCompat.getColor(context,R.color.yellow))
        }
        else
        {
            holder.pStatus.setText("Delete")
            holder.pStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
        }*/




    }

    class patientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pImage=itemView.findViewById<ImageView>(R.id.patient_Image)
        var pName=itemView.findViewById<TextView>(R.id.txt_PName)
        var pPhone=itemView.findViewById<TextView>(R.id.txt_PPhoneNo)
        var pStatus=itemView.findViewById<TextView>(R.id.txt_PStatus)


    }
}