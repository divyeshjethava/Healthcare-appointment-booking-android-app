package com.example.Admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R

class AAdapter_Add_C_Admin(val context: Context,val adminList:ArrayList<Admin_Data>) : RecyclerView.Adapter<AAdapter_Add_C_Admin.adminViewHolder>() {


    class adminViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var adminName=itemView.findViewById<TextView>(R.id.txt_AName)
        var adminPhone=itemView.findViewById<TextView>(R.id.txt_APhoneNo)
        var adminFLetter=itemView.findViewById<TextView>(R.id.admin_Name_first_l)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adminViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.admin_child_a_list, parent,false)
        return AAdapter_Add_C_Admin.adminViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return adminList.size
    }

    override fun onBindViewHolder(holder: adminViewHolder, position: Int) {

        val currentAdmin=adminList[position]

        holder.adminName.setText(currentAdmin.aName)
        holder.adminPhone.setText(currentAdmin.aPhone)

        val name:String=currentAdmin.aName.toString()
        val fc=name[0]

        holder.adminFLetter.setText("$fc")


    }


}

