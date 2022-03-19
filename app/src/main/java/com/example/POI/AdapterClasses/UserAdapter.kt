package com.example.POI.AdapterClasses

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.POI.Home
import com.example.POI.MessageChatActivity
import com.example.POI.ModelClasses.Users
import com.example.POI.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text



class UserAdapter(mContext:Context, mUsers:List<Users>, 
                  isChatCheck:Boolean):RecyclerView.Adapter<UserAdapter.ViewHolder?>() {

    private val mContext:Context
    private val mUsers:List<Users>
    private var isChatCheck:Boolean

    init {
        this.mUsers=mUsers
        this.mContext=mContext
        this.isChatCheck=isChatCheck

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout, viewGroup,false)
        return UserAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val user:Users=mUsers[i]
        holder.userNammeTxt.text=user.getUsername()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.profile).into(holder.profileImageView)

        holder.itemView.setOnClickListener {
            val options= arrayOf<CharSequence>(
                "Send message",
                "Visit profile"
            )
            val builder:AlertDialog.Builder=AlertDialog.Builder(mContext)
            builder.setTitle("What do you want?")
            builder.setItems(options,DialogInterface.OnClickListener { dialog, position ->
                if (position==0){
                    val intent= Intent(mContext, MessageChatActivity::class.java)
                    intent.putExtra("visit_id",user.getUID())
                    mContext.startActivity(intent)
                }
                if (position==1){

                }
            })
            builder.show()

        }


    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var userNammeTxt:TextView
        var profileImageView:CircleImageView
        var onlineImageView:CircleImageView
        var offlineImageView:CircleImageView
        var lastMessageTxt:TextView

        init {
            userNammeTxt=itemView.findViewById(R.id.username)
            profileImageView=itemView.findViewById(R.id.profileImg)
            onlineImageView=itemView.findViewById(R.id.imageOnline)
            offlineImageView=itemView.findViewById(R.id.imageOffline)
            lastMessageTxt=itemView.findViewById(R.id.messageLast)

        }
    }



}