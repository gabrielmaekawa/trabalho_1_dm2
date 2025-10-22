package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("preco")
    val preco: Double,

    @SerializedName("url_imagem")
    val urlImagem: String,

    @SerializedName("categoria")
    val categoria: String,

    @SerializedName("descricao")
    val descricao: String? = null
)