package com.example.Patient

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Admin.Department_Data
import com.example.healthcare.R

class Adapter_All_Dept(val context: Context, val deptList:ArrayList<Department_Data>) : RecyclerView.Adapter<Adapter_All_Dept.DeptViewHolder>()
{


    class DeptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageDept=itemView.findViewById<ImageView>(R.id.dept_img)
        var txtDeptName=itemView.findViewById<TextView>(R.id.txt_dept_name)
        var txtDeptDes=itemView.findViewById<TextView>(R.id.txt_dept_des)

        var btnViewDept=itemView.findViewById<Button>(R.id.btn_view_dept)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeptViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.departments_shape2,parent,false)
        return Adapter_All_Dept.DeptViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return deptList.size
    }

    override fun onBindViewHolder(holder: DeptViewHolder, position: Int) {

        val currantList=deptList[position]

        Glide.with(holder.itemView.context).load(currantList.deptImg).into(holder.imageDept)

        holder.txtDeptName.text=currantList.deptName

        var t:String=currantList.deptDescription.toString()
        var trimString=t.trim()
        holder.txtDeptDes.text=trimString

        holder.btnViewDept.setOnClickListener {

            val intent=Intent(context,Department_Info::class.java)

            intent.putExtra("deptImg",currantList.deptImg)
            intent.putExtra("deptName",currantList.deptName)
            intent.putExtra("deptDesc",currantList.deptDescription)

            context.startActivity(intent)

        }

    }
}