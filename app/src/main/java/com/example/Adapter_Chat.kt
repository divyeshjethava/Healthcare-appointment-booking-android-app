package com.example.healthcare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Doctor.DUserData

class Adapter_Chat(val context: Context, var userList: List<DUserData>) :
    RecyclerView.Adapter<Adapter_Chat.userViewHolder>(){

    val PR="str"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_Chat.userViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.doctor_chat_list,parent,false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val currentUser = userList[position]


        holder.nameC.text="Dr. "+currentUser.username
        holder.departmentC.text=currentUser.department

        Glide.with(holder.itemView.context).load(currentUser.doctorImg).into(holder.imageC)



        val sh:SharedPreferences= context.getSharedPreferences(PR,0)
        val ed:SharedPreferences.Editor=sh.edit()
        ed.putString("Dphone",currentUser.phoneNo)

        ed.apply()

       // Glide.with(holder.itemView.context).load(currentUser.image).into(holder.image1)


        holder.itemView.setOnClickListener{

            val intent = Intent(context,Chating::class.java)
            //intent.putExtra("name",currentUser.name)
            //intent.putExtra("uid",currentUser.uid)
            intent.putExtra("pho",currentUser.phoneNo)
            intent.putExtra("name",currentUser.username)
            intent.putExtra("department",currentUser.department)

            intent.putExtra("image",currentUser.doctorImg)
            //intent.putExtra("img",currentUser.image)
            //intent.putExtra("eml",currentUser.email)
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


    class userViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val nameC=itemView.findViewById<TextView>(R.id.chat_doctor_name)
        val departmentC=itemView.findViewById<TextView>(R.id.chat_department)
        val imageC=itemView.findViewById<ImageView>(R.id.chat_doctor_picture)

    }
}