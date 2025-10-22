package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("descricao")
    val descricao: String,
    
    @SerializedName("foto")
    val foto: String
)
