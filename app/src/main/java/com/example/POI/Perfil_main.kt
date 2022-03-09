package com.example.POI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Perfil_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_main)

        val Subgrupo = this.findViewById<ImageButton>(R.id.btn_sgrupo)
        val Chat = this.findViewById<ImageButton>(R.id.btn_chats)

        val Recompensa = this.findViewById<ImageButton>(R.id.btn_recompensa)
        val Tarea = this.findViewById<ImageButton>(R.id.btn_tarea)

/////Boton para mandar a llamar ventana de subgrupos/////

        Subgrupo.setOnClickListener{
            val VentanaSG = Intent(applicationContext,SubG_main::class.java)
            startActivity(VentanaSG)
        }
/////Boton para mandar a llamar ventana de chats/////

        Chat.setOnClickListener{
            val VentanaChat: Intent = Intent(applicationContext,Chat_main::class.java)
            startActivity(VentanaChat)
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