package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("itens")
    val itens: List<Item>,
    
    @SerializedName("categorias")
    val categorias: List<Category>
)
