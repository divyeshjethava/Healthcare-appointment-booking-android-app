package com.example.Patient

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R
import com.google.firebase.database.FirebaseDatabase

class Message_Adapter(val context: android.content.Context,val messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val ITEM_RECEIVE = 1
        val ITEM_SENT=2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == 1){
                val view: View = LayoutInflater.from(context).inflate(R.layout.recieve_activity,parent,false)
                return ReceiveViewHolder(view)
            }else{
                val view: View = LayoutInflater.from(this.context).inflate(R.layout.send_activity,parent,false)
                return SentViewHolder(view)
            }
        }

        override fun getItemCount(): Int {
            return messageList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val currentMessage = messageList[position]
            if (holder.javaClass == SentViewHolder::class.java){


                val viewHolder = holder as SentViewHolder
                holder.sentMessage.text=currentMessage.message
            }else{
                val viewHolder = holder as ReceiveViewHolder
                holder.receiveMessage.text=currentMessage.message
            }

        }

        @SuppressLint("SuspiciousIndentation")
        override fun getItemViewType(position: Int): Int {
            val currentMessage = messageList[position]
            val share:SharedPreferences= context.getSharedPreferences(PLoginActivity().share,0)
            var phone=share.getString("phone","1231231230")

            if (FirebaseDatabase.getInstance().getReference("Patient").child(phone!!).equals(currentMessage.senderPhone)){
                return ITEM_SENT
            }else
            {
                return ITEM_RECEIVE
            }
        }
        class SentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val sentMessage = itemView.findViewById<TextView>(R.id.txtSend)
        }
        class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val receiveMessage = itemView.findViewById<TextView>(R.id.txtReceive)
        }
    }
