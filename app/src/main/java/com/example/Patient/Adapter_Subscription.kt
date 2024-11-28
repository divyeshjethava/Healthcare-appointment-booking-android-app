package com.example.Patient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R

class Adapter_Subscription(private val subscriptionList:ArrayList<SubscriptionData>) : RecyclerView.Adapter<Adapter_Subscription.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.subscription_card,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return subscriptionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentList=subscriptionList[position]

        holder.subsType.text=currentList.subs_type
        holder.subValidity.text=currentList.subs_validate
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val subsType=itemView.findViewById<TextView>(R.id.subscription_type)
        val subValidity=itemView.findViewById<TextView>(R.id.subscription_validity)
    }


}