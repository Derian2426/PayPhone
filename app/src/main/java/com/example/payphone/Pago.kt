package com.example.payphone

import com.google.gson.Gson
import org.json.JSONObject

data class Pago (val phoneNumber:String
                ,val countryCode:String
                ,val clientUserId:String
                 ,val reference:String
                 ,val responseUrl:String
                 ,val amount:Int
                 ,val amountWithTax:Int
                 ,val amountWithoutTax:Int
                 ,val tax:Int
                 ,val clientTransactionId:String
                 ){
    fun getJson():JSONObject{
        val json= Gson()
        return JSONObject(json.toJson(this))
    }
}

