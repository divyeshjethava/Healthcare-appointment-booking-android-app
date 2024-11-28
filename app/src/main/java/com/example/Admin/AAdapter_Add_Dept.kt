package com.example.Admin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthcare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AAdapter_Add_Dept(val context: Context,val deptList:ArrayList<Department_Data>) : RecyclerView.Adapter<AAdapter_Add_Dept.deptViewHolder>() {



    class deptViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

        val deptImage=itemView.findViewById<ImageView>(R.id.dept_image)
        val deptName=itemView.findViewById<TextView>(R.id.txt_dept_name)
        val deptStatus=itemView.findViewById<TextView>(R.id.dept_status)

        val showDept=itemView.findViewById<CardView>(R.id.cardView_show_dept)

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): deptViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.admin_department_list,parent,false)
        return deptViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return deptList.size
    }

    override fun onBindViewHolder(holder: deptViewHolder, position: Int) {

        val currentList=deptList[position]

        Glide.with(holder.itemView.context).load(currentList.deptImg).into(holder.deptImage)

        holder.deptName.text=currentList.deptName




        val dept_img=currentList.deptImg
        val dept_name=currentList.deptName
        val dept_des=currentList.deptDescription




        holder.deptStatus.setOnClickListener {



            if(currentList.deptStatus==0)
            {

                val databaseRef:DatabaseReference
                databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                databaseRef.child("All Departments").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,1)).addOnSuccessListener {


                    val databaseRef:DatabaseReference
                    databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                    databaseRef.child("Status 1").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,1)).addOnSuccessListener {


                        val databaseRef:DatabaseReference
                        databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                        databaseRef.child("Status 0").child(dept_name.toString()).removeValue()

                    }

                }


            }
            else if (currentList.deptStatus==1)
            {

                val databaseRef:DatabaseReference
                databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                databaseRef.child("All Departments").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,0)).addOnSuccessListener {

                    val databaseRef:DatabaseReference
                    databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                    databaseRef.child("Status 0").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,0)).addOnSuccessListener {


                        val databaseRef:DatabaseReference
                        databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                        databaseRef.child("Status 1").child(dept_name.toString()).removeValue()

                    }
                }



            }
            else if (currentList.deptStatus==2)
            {


                val databaseRef:DatabaseReference
                databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                databaseRef.child("All Departments").child(dept_name.toString()).removeValue().addOnSuccessListener {


                    val databaseRef:DatabaseReference
                    databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                    databaseRef.child("Status 1").child(dept_name.toString()).removeValue().addOnSuccessListener {

                        val databaseRef:DatabaseReference
                        databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                        databaseRef.child("Status 0").child(dept_name.toString()).removeValue().addOnSuccessListener {


                            Toast.makeText(context,"Delete : ${currentList.deptName}",Toast.LENGTH_LONG).show()


                        }

                    }

                }


            }



        }



        holder.deptStatus.setOnLongClickListener {




            if(currentList.deptStatus==2)
            {

                val databaseRef:DatabaseReference
                databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                databaseRef.child("All Departments").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,1)).addOnSuccessListener {


                    val databaseRef:DatabaseReference
                    databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                    databaseRef.child("Status 1").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,1)).addOnSuccessListener {


                        val databaseRef:DatabaseReference
                        databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                        databaseRef.child("Status 0").child(dept_name.toString()).removeValue()

                    }
                }

            }
            else
            {

                val databaseRef:DatabaseReference
                databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                databaseRef.child("All Departments").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,2)).addOnSuccessListener {


                    val databaseRef:DatabaseReference
                    databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                    databaseRef.child("Status 1").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,2)).addOnSuccessListener {


                        val databaseRef:DatabaseReference
                        databaseRef=FirebaseDatabase.getInstance().getReference("Departments")
                        databaseRef.child("Status 0").child(dept_name.toString()).setValue(Department_Data(dept_img,dept_name,dept_des,2))

                    }

                }


            }

            true
        }



        holder.showDept.setOnClickListener {

            val intent=Intent(context,Edit_Dept_Info::class.java)
            intent.putExtra("deptImg",dept_img)
            intent.putExtra("deptName",dept_name)
            intent.putExtra("deptDes",dept_des)
            context.startActivity(intent)

        }




        if(currentList.deptStatus==0)
        {
            holder.deptStatus.setText("Add")
            holder.deptStatus.setTextColor(ContextCompat.getColor(context,R.color.green))
        }
        else if(currentList.deptStatus==1)
        {
            holder.deptStatus.setText("Cancel")
            holder.deptStatus.setTextColor(ContextCompat.getColor(context,R.color.yellow))
        }
        else
        {
            holder.deptStatus.setText("Delete")
            holder.deptStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
        }



    }

}