package com.example.POI.AdapterClasses

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.POI.Home
import com.example.POI.ModelClasses.Chat
import com.example.POI.R
import com.example.POI.ViewFullImageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ChatsAdapter(
    mContext:Context,
    mChatList:List<Chat>,
    imageUrl:String):RecyclerView.Adapter<ChatsAdapter.ViewHolder?>() {

    private val mContext:Context
    private val mChatList:List<Chat>
    private val imageUrl:String
    var firebaseUser:FirebaseUser=FirebaseAuth.getInstance().currentUser!!

    init {
        this.mChatList=mChatList
        this.mContext=mContext
        this.imageUrl=imageUrl

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return if(position==1){
            val view:View= LayoutInflater.from(mContext)
                .inflate(com.example.POI.R.layout.message_item_right, parent,false)
            ViewHolder(view)
        }
        else{
            val view:View= LayoutInflater.from(mContext)
                .inflate(com.example.POI.R.layout.message_item_left, parent,false)
            ViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return mChatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val chat:Chat=mChatList[position]
        Picasso.get().load(imageUrl).into(holder.profile_image)

        //images message
        if (chat.getMessage().equals("sent you an image")
            && !chat.getUrl().equals("")){
            //image message  right side
            if(chat.getSender().equals(firebaseUser!!.uid)){
                //aqu?? es donde se coloca el texto de firebase a los elementos
                holder.show_text_message!!.visibility=View.GONE
                holder.right_image_view!!.visibility=View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.right_image_view)
                holder.DateMessage!!.text= chat.getTimeStamp() as CharSequence?

                holder.right_image_view!!.setOnClickListener {
                    val options= arrayOf<CharSequence>(
                        "Ver imagen completa",
                        "Eliminar imagen",
                        "Cancelar"
                    )
                    var builder:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("??Qu?? quieres hacer?")
                    builder.setItems(options,DialogInterface.OnClickListener {
                            dialog, which ->
                        if (which==0){
                            val intent= Intent(mContext, ViewFullImageActivity::class.java)
                            intent.putExtra("url",chat.getUrl())
                            mContext.startActivity(intent)
                        }
                        else if (which==1){
                            deleteSentMessage(position,holder)
                        }
                    })
                    builder.show()
                }
            }
            //image message  left side
            else if(!chat.getSender().equals(firebaseUser!!.uid)){
                //aqu?? es donde se coloca el texto de firebase a los elementos
                holder.show_text_message!!.visibility=View.GONE
                holder.left_image_view!!.visibility=View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.left_image_view)
                holder.DateMessage!!.text= chat.getTimeStamp() as CharSequence?

                holder.left_image_view!!.setOnClickListener {
                    val options= arrayOf<CharSequence>(
                        "Ver imagen completa",
                        "Cancelar"
                    )
                    var builder:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("??Qu?? quieres hacer?")
                    builder.setItems(options,DialogInterface.OnClickListener {
                            dialog, which ->
                        if (which==0){
                            val intent= Intent(mContext, ViewFullImageActivity::class.java)
                            intent.putExtra("url",chat.getUrl())
                            mContext.startActivity(intent)
                        }
                    })
                    builder.show()
                }

            }
        }
        //text message
        else{
            //aqu?? es donde se coloca el texto de firebase a los elementos
            holder.show_text_message!!.text=chat.getMessage()
            holder.DateMessage!!.text= chat.getTimeStamp() as CharSequence?
            //verificar que tu eres quein mando el memnsaje para eliminarlo
            if (firebaseUser!!.uid==chat.getSender()){
                holder.show_text_message!!.setOnClickListener {
                    val options= arrayOf<CharSequence>(
                        "Eliminar mensaje",
                        "Cancelar"
                    )
                    var builder:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    builder.setTitle("??Qu?? quieres hacer?")
                    builder.setItems(options,DialogInterface.OnClickListener {
                            dialog, which ->
                        if (which==0){
                            deleteSentMessage(position,holder)
                        }
                    })
                    builder.show()
                }
            }
        }

        //sent and seen message
        if(position==mChatList.size-1){
            if (chat.getIsseen()){
                holder.text_seen!!.text="Seen"
                if (chat.getMessage().equals("sent you an image") && !chat.getUrl().equals("")){
                    val lp:RelativeLayout.LayoutParams?=holder.text_seen!!.layoutParams as RelativeLayout.LayoutParams?//*************************************************
                    lp!!.setMargins(0,245,10,0)
                    holder.text_seen!!.layoutParams=lp
                }
            }
            else{
                holder.text_seen!!.text="Sent"
                if (chat.getMessage().equals("sent you an image") && !chat.getUrl().equals("")){
                    val lp:RelativeLayout.LayoutParams?=holder.text_seen!!.layoutParams as RelativeLayout.LayoutParams//*************************************************
                    lp!!.setMargins(0,245,10,0)
                    holder.text_seen!!.layoutParams=lp
                }
            }
        }
        else{
            holder.text_seen!!.visibility=View.GONE
        }

    }

        inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            var profile_image:CircleImageView?=null
            var show_text_message:TextView?=null
            var left_image_view:ImageView?=null
            var text_seen:TextView?=null
            var right_image_view:ImageView?=null
            var DateMessage:TextView?=null

            init {
                profile_image=itemView.findViewById(R.id.profile_image)
                show_text_message=itemView.findViewById(R.id.show_text_message)
                left_image_view=itemView.findViewById(R.id.left_image_view)
                text_seen=itemView.findViewById(R.id.text_seen)
                right_image_view=itemView.findViewById(R.id.right_image_view)
                DateMessage=itemView.findViewById(R.id.DateMessage)
            }
        }

    override fun getItemViewType(position: Int): Int {
        return if(mChatList[position].getSender().equals(firebaseUser!!.uid)){
            1
        }
        else{
            0
        }
    }

    private fun deleteSentMessage(position: Int, holder: ChatsAdapter.ViewHolder){
        val ref=FirebaseDatabase.getInstance().reference.child("Chats")
            .child(mChatList.get(position).getMessageId()!!)
            .removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(holder.itemView.context, "Eliminado", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(holder.itemView.context, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
                }
            }
    }

}