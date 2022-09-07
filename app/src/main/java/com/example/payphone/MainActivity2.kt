package com.example.payphone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        try{


        val bundle=intent.extras
        var id=bundle?.getInt("transaccion")
        var url="https://pay.payphonetodoesposible.com/api/Sale/$id"
        var Token=bundle?.getString("TOKEN")
        var textEmail=findViewById<TextView>(R.id.txt_email)
        var textCedula=findViewById<TextView>(R.id.txt_Dni)
        var textTelefono=findViewById<TextView>(R.id.txt_telefono)
        var textFecha=findViewById<TextView>(R.id.txt_fecha)
        var textTienda=findViewById<TextView>(R.id.txt_tienda)
        var textValor=findViewById<TextView>(R.id.txt_valor)
        var textEstado=findViewById<TextView>(R.id.txt_etado)
        val cola = Volley.newRequestQueue(this)
        val pagarPayPhone: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener { response ->
                try {
                    Toast.makeText(
                        applicationContext,
                        "orden: " + response.getString("phoneNumber"),
                        Toast.LENGTH_LONG
                    ).show()
                    textEmail.text=response.getString("email")
                    textCedula.text=response.getString("document")
                    textTelefono.text=response.getString("phoneNumber")
                    textFecha.text=response.getString("date")
                    textTienda.text=response.getString("storeName")
                    textValor.text=response.getInt("amount").toString()
                    textEstado.text=response.getString("transactionStatus")
                } catch (e: JSONException) {
                    println(e.toString())
                    Toast.makeText(
                        applicationContext,
                        "uno: " + response.getString("phoneNumber"),
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    "dos: $error",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $Token"
                return params
            }
        }
        cola.add(pagarPayPhone)
        }catch (e:Exception){
            println(e.message.toString())
        }
    }
}