package com.example.POI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tarea_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea_main)

        val TareaVer = findViewById<Button>(R.id.btn_tareaver)
        val TareaCrear = findViewById<Button>(R.id.btn_tareacrear)


        TareaVer.setOnClickListener{
            val VentanaTareaVer: Intent = Intent(applicationContext,TareaVer_main::class.java)
            startActivity(VentanaTareaVer)

        }

        TareaCrear.setOnClickListener{
            val VentanaTareaCrear: Intent = Intent(applicationContext,TareaCrear_main::class.java)
            startActivity(VentanaTareaCrear)

        }



    }
}