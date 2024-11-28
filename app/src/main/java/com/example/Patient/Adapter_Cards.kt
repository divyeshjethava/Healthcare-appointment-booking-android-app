package com.example.healthcare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.Patient.CardData
import com.example.Patient.PaymentMethod

class Adapter_Cards(private val context: PaymentMethod, private val dataList:List<CardData>): RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.dabit_cradit_card_shape,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Toast.makeText(context,"hi",Toast.LENGTH_SHORT).show()
        val currentUser = dataList[position]

        holder.recCardNo.text=currentUser.cardNo
        holder.recExDate.text=currentUser.expiryDate
        holder.recHoName.text=currentUser.cardHolderName

    }

    override fun getItemCount(): Int {

        return dataList.size

    }


}

class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    var recCardNo:TextView
    var recExDate:TextView
    var recHoName:TextView

    init {
        recCardNo=itemView.findViewById(R.id.txt_card_last_no)
        recExDate=itemView.findViewById(R.id.txt_card_expiry_date)
        recHoName=itemView.findViewById(R.id.txt_card_holder_name)
    }

}