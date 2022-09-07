package com.example.payphone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun pagoOrden(view: View) {
        var TOKEN =
            "xJuo3lJPGJWM-dwA9z5nYHQeRNkar6zBrhTYXZyBX2g6dXJq_42Euns3FhaihRm1DMA19yFBUUKXIl2QSzRZzF62e2QoswUoPONU-gO2VDGOjt2G4JBwabhxTdNigbNk-oo-K5NL40Knhs_gG0cVzbUQRzsK_9D78rXYPjp85w1dq9lA-qh357aPynQNzMhaqc0lDRpzWY8-_VetVwWWzy-aKoLYGPt2wJp2-klq0z67xXwJvSe58CyZoxz3x2YYWIJG8tdZ8WH3sYqECkHrZaH3gICHbG3UiiKGbc_pegx1LU3vxY7IW3z5HZEtQj2b69Wv7A"
        var txt_cedula: TextView = findViewById(R.id.txt_cedula)
        var txt_telefono: TextView = findViewById(R.id.txt_telefono)
        var txt_valor: TextView = findViewById(R.id.txt_valor)
        var valor:Float=txt_valor.text.toString().toFloat()
        valor=valor*100
        var intValor=valor.toInt()
        if (txt_cedula.text.toString().isEmpty() && txt_telefono.text.toString()
                .isEmpty() && txt_valor.text.toString().isEmpty()
        ) {
            Toast.makeText(
                this,
                "Algunos campos no se han rellenado",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            var pago: Pago = Pago(
                txt_telefono.text.toString(),
                "593",
                txt_cedula.text.toString(),
                "none",
                "http://paystoreCZ.com/confirm.php",
                txt_valor.text.toString().toInt(),
                90,
                0,
                10,
                UUID.randomUUID().toString()
            )
            val url = "https://pay.payphonetodoesposible.com/api/Sale"
            var pagoJson: JSONObject = JSONObject()
            pagoJson = pago.getJson()

            val cola = Volley.newRequestQueue(this)
            val intent= Intent(this,MainActivity2::class.java)
            val pagarPayPhone: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                url,
                pagoJson,
                Response.Listener { response ->
                    try {
                        Toast.makeText(
                            applicationContext,
                            "Solicitud enviada con exito: " + response.getInt("transactionId"),
                            Toast.LENGTH_LONG
                        ).show()
                        var transaccion: Transaccion = Transaccion(response.getInt("transactionId"))
                        intent.putExtra("transaccion",transaccion.transactionId)
                        intent.putExtra("TOKEN",TOKEN)
                        startActivity(intent)
                    } catch (e: JSONException) {
                        println(e.toString())
                        Toast.makeText(
                            applicationContext,
                            "uno: " + response.getInt("transactionId"),
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
                    params["Authorization"] = "Bearer $TOKEN"
                    return params
                }
            }
            cola.add(pagarPayPhone)
        }
    }
}