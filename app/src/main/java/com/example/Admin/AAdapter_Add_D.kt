package com.example.Admin

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Doctor.DUserData
import com.example.healthcare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AAdapter_Add_D(val context: Context, val doctorList: ArrayList<DUserData>) :
    RecyclerView.Adapter<AAdapter_Add_D.doctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): doctorViewHolder {


        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.admin_d_status_list,parent,false)
        return doctorViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: doctorViewHolder, position: Int) {
        val currentList=doctorList[position]





            Glide.with(holder.itemView.context).load(currentList.doctorImg).into(holder.dImg)

            holder.dName.text=currentList.username
            holder.dDepartment.text=currentList.department


        holder.dImg.setOnClickListener {

            lateinit var dialog: Dialog


            val view= View.inflate(context, R.layout.large_image_dialog,null)
            val builder= AlertDialog.Builder(context)
            builder.setView(view)
            dialog=builder.create()

            val largeImage=view.findViewById<ImageView>(R.id.large_picture)
            val docName=view.findViewById<TextView>(R.id.dialog_doc_name)

            Glide.with(context).load(currentList.doctorImg).into(largeImage)
            docName.setText("Dr. "+currentList.username)

            //dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

        }



        if(currentList.status==0)
        {
            holder.dStatus.setText("Add")
            holder.dStatus.setTextColor(ContextCompat.getColor(context,R.color.green))
        }
        else if(currentList.status==1)
        {
            holder.dStatus.setText("cancle")
            holder.dStatus.setTextColor(ContextCompat.getColor(context,R.color.yellow))
        }
        else
        {
            holder.dStatus.setText("Delete")
            holder.dStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
        }



            holder.dStatus.setOnClickListener {

                val dImg=currentList.doctorImg
                val dName=currentList.username
                val dEmail=currentList.email
                val dPhone=currentList.phoneNo
                val dGender=currentList.gender
                val dAge=currentList.age
                val dDob=currentList.birthDate
                val dDepartment=currentList.department
                val dDegree=currentList.degree
                val dHospital=currentList.hospitalName
                val dExperience=currentList.experience
                val dLocation=currentList.location
                val dCity=currentList.city
                val dAboutInfo=currentList.aboutInfo
                val dCertificate=currentList.certificateImg
                val dPass=currentList.password



                val databaseReference:DatabaseReference
                databaseReference=FirebaseDatabase.getInstance().getReference("Doctor")
                databaseReference.child("Cancel Doctor").child(dPhone!!).setValue(DUserData(dImg.toString(),
                    dName.toString(),
                    dEmail.toString(), dPhone,
                    dGender.toString(),
                    dAge.toString(),
                    dDob.toString(),
                    dDepartment.toString(),
                    dDegree.toString(),
                    dHospital.toString(),
                    dExperience.toString(),
                    dLocation.toString(),
                    dCity.toString(),
                    dAboutInfo.toString(),
                    dCertificate.toString(),
                    dPass.toString(),
                    2)).addOnCompleteListener {


                    if(it.isSuccessful)
                    {
                        val databaseRef:DatabaseReference
                        databaseRef=FirebaseDatabase.getInstance().getReference("Doctor")
                        databaseRef.child("Add Doctor").child(dPhone).removeValue().addOnSuccessListener {

                            val databaseRef:DatabaseReference
                            databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                            databaseRef.child("Dept Doctors").child(dDepartment.toString()).child(dPhone).removeValue()

                        }
                    }
                }

            }





    }



    class doctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dImg=itemView.findViewById<ImageView>(R.id.doctorImage)
        val dName=itemView.findViewById<TextView>(R.id.txtDName)
        val dDepartment=itemView.findViewById<TextView>(R.id.txtDDepartment)
        val dStatus=itemView.findViewById<TextView>(R.id.txtDStatus)

        var status:Int = 0


    }

}