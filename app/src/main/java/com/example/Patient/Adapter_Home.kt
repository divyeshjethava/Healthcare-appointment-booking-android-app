package com.example.Patient

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Doctor.DUserData
import com.example.Doctor.DoctorProfile
import com.example.healthcare.R

class Adapter_Home(val context: Context, var userList: List<DUserData>) :
    RecyclerView.Adapter<Adapter_Home.userViewHolder>(){

    val PR="str"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.see_doctor_list,parent,false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val currentUser = userList[position]


        holder.name.text="Dr. "+currentUser.username
        holder.department.text=currentUser.department
        holder.dInfo.text=currentUser.aboutInfo

        Glide.with(holder.itemView.context).load(currentUser.doctorImg).into(holder.dImg)




        val sh:SharedPreferences= context.getSharedPreferences(PR,0)
        val ed:SharedPreferences.Editor=sh.edit()
        ed.putString("Dphone",currentUser.phoneNo)
        ed.apply()

       //Glide.with(holder.itemView.context).load(currentUser.image).into(holder.image1)
        holder.dImg.setOnClickListener {

            lateinit var dialog: Dialog


            val view= View.inflate(context, R.layout.large_image_dialog,null)
            val builder= AlertDialog.Builder(context)
            builder.setView(view)
            dialog=builder.create()

            val largeImage=view.findViewById<ImageView>(R.id.large_picture)
            val docName=view.findViewById<TextView>(R.id.dialog_doc_name)

            Glide.with(context).load(currentUser.doctorImg).into(largeImage)
            docName.setText("Dr. "+currentUser.username)

            //dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

        }


        holder.itemView.setOnClickListener{

            val intent = Intent(context, DoctorProfile::class.java)
            intent.putExtra("dName",currentUser.username)
            intent.putExtra("department",currentUser.department)
            intent.putExtra("dImg",currentUser.doctorImg)
            intent.putExtra("dNumber",currentUser.phoneNo)
            intent.putExtra("dExperience",currentUser.experience)
            intent.putExtra("dAboutInfo",currentUser.aboutInfo)
            var per="name"




            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return userList.size

    }


    fun searchDataList(searchList : List<DUserData>){

        userList=searchList
        notifyDataSetChanged()

    }




    class userViewHolder(itemView: View):RecyclerView
        .ViewHolder(itemView){

        val name=itemView.findViewById<TextView>(R.id.name1)
        val department=itemView.findViewById<TextView>(R.id.department1)
        val dInfo=itemView.findViewById<TextView>(R.id.txt_doctor_info)
        val dImg=itemView.findViewById<ImageView>(R.id.doctorImg1)

    }
}