package com.example.Doctor

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Patient.AppointmentData
import com.example.Patient.PLoginActivity
import com.example.Patient.PSignupActivity
import com.example.healthcare.R
import com.google.firebase.database.FirebaseDatabase

class Adapter_DUpComing(val context: Context, val appoList:ArrayList<AppointmentData>) : RecyclerView.Adapter<Adapter_DUpComing.appoViewHolder>()
{


    var conformPhone:String?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): appoViewHolder {
        val view : View=LayoutInflater.from(context).inflate(R.layout.appointment_booked_shape_p,parent,false)
        return appoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appoList.size
    }

    override fun onBindViewHolder(holder: appoViewHolder, position: Int) {
        val currentAppo=appoList[position]


        Glide.with(holder.itemView.context).load(currentAppo.image).into(holder.patientImg)
        holder.txtPatientName.text=currentAppo.doctorName
        holder.txtDate.text=currentAppo.appoDate

        holder.txtTime.text=currentAppo.appoTime+"  -  PM      "


        //get phone no in login activity
        var shareprefrence1 : SharedPreferences = this.context.getSharedPreferences(PLoginActivity().PREF, AppCompatActivity.MODE_PRIVATE)
        var phoneLog= shareprefrence1.getString("phoneNoL","error").toString()

        var shareprefrence2 : SharedPreferences =this.context.getSharedPreferences(PSignupActivity().PREFF, AppCompatActivity.MODE_PRIVATE)
        var phoneSign= shareprefrence2.getString("phoneNoS","error").toString()



        if(!phoneSign.equals("error"))
        {
            conformPhone=phoneSign
        }
        else
        {
            conformPhone=phoneLog
        }



        holder.btnCancleAppo.setOnClickListener {



            val view=View.inflate(context, R.layout.custom_dialog8,null)
            val builder= AlertDialog.Builder(context)
            builder.setView(view)
            val dialog=builder.create()
            dialog.show()
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



            val btnCancle=dialog.findViewById<TextView>(R.id.btn_cancle)
            val btnYes=dialog.findViewById<TextView>(R.id.btn_yes)

            btnCancle!!.setOnClickListener {

                dialog.dismiss()

            }
            btnYes!!.setOnClickListener {

                val databaseReference=FirebaseDatabase.getInstance().getReference("Appointments")
                databaseReference.child(conformPhone.toString()).child(currentAppo.pNumber!!).removeValue()
                dialog.dismiss()

            }


        }




        holder.btnReschedulAppo.setOnClickListener {




        }



    }






    class appoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val patientImg=itemView.findViewById<ImageView>(R.id.patient_img)
        val txtPatientName=itemView.findViewById<TextView>(R.id.txt_patient_name)
        val txtDate=itemView.findViewById<TextView>(R.id.txt_d_date)
        val txtTime=itemView.findViewById<TextView>(R.id.txt_d_time)

        val btnCancleAppo=itemView.findViewById<TextView>(R.id.btn_cancel_appo)
        val btnReschedulAppo=itemView.findViewById<TextView>(R.id.btn_reschedule_appo)

    }
}
