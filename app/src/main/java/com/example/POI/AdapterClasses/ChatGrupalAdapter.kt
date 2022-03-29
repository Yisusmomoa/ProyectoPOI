package com.example.POI.AdapterClasses

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.POI.ModelClasses.Chat
import com.example.POI.ModelClasses.MensajeGrupal
import com.example.POI.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ChatGrupalAdapter(mContext: Context,
                        mChatList:List<MensajeGrupal>):
    RecyclerView.Adapter<ChatGrupalAdapter.ViewHolder?>() {
    private val mContext:Context
    private val mChatList:List<MensajeGrupal>
    var firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    init {
        this.mChatList=mChatList
        this.mContext=mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return if(position==1){
            val view:View= LayoutInflater.from(mContext)
                .inflate(com.example.POI.R.layout.message_item_right,
                    parent,false)
            ViewHolder(view)
        }
        else{
            val view:View= LayoutInflater.from(mContext)
                .inflate(com.example.POI.R.layout.message_item_left,
                    parent,false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat:MensajeGrupal=mChatList[position]
        //aquí es donde se coloca el texto de firebase a los elementos
        holder.show_text_message!!.text=chat.contenido
        holder.DateMessage!!.text=chat.timeStamp.toString()
        //holder.DateMessage!!.text= chat.timeStamp as CharSequence?

    }

    override fun getItemCount(): Int {
        return mChatList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(mChatList[position].esMio){
            1
        }
        else{
            0
        }
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var profile_image: CircleImageView?=null
        var show_text_message: TextView?=null
        var left_image_view: ImageView?=null
        var text_seen: TextView?=null
        var right_image_view: ImageView?=null
        var DateMessage: TextView?=null

        init {
            profile_image=itemView.findViewById(R.id.profile_image)
            show_text_message=itemView.findViewById(R.id.show_text_message)
            left_image_view=itemView.findViewById(R.id.left_image_view)
            text_seen=itemView.findViewById(R.id.text_seen)
            right_image_view=itemView.findViewById(R.id.right_image_view)
            DateMessage=itemView.findViewById(R.id.DateMessage)
        }
    }
}