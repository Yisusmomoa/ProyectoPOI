package com.example.POI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SubG_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_gmain)
        val Subgrupo = this.findViewById<ImageButton>(R.id.btn_sgrupo)
        val Chat = this.findViewById<ImageButton>(R.id.btn_chats)
        val Perfil = this.findViewById<ImageButton>(R.id.btn_perfil)
        val Recompensa = this.findViewById<ImageButton>(R.id.btn_recompensa)
        val Tarea = this.findViewById<ImageButton>(R.id.btn_tarea)


/////Boton para mandar a llamar ventana de chats/////

        Chat.setOnClickListener{
            val VentanaChat: Intent = Intent(applicationContext,Chat_main::class.java)
            startActivity(VentanaChat)
        }
/////Boton para mandar a llamar ventana de perfil/////

        Perfil.setOnClickListener{
            val VentanaPerfil: Intent = Intent(applicationContext,Perfil_main::class.java)
            startActivity(VentanaPerfil)
        }
/////Boton para mandar a llamar ventana de perfil/////

        Recompensa.setOnClickListener{
            val VentanaRecompensa: Intent = Intent(applicationContext,Recompensa_main::class.java)
            startActivity(VentanaRecompensa)
        }
/////Boton para mandar a llamar ventana de perfil/////

        Tarea.setOnClickListener{
            val VentanaTarea: Intent = Intent(applicationContext,Tarea_main::class.java)
            startActivity(VentanaTarea)
        }


    }
}