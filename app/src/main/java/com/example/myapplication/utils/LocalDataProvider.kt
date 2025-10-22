package com.example.myapplication.utils

import com.example.myapplication.model.Category
import com.example.myapplication.model.Item

object LocalDataProvider {

    fun getLocalCategories(): List<Category> {
        return listOf(
            Category(
                id = "1",
                descricao = "Eletrônicos",
                foto = "https://images.unsplash.com/photo-1498049794561-7780e7231661?w=500&h=500&fit=crop"
            ),
            Category(
                id = "2",
                descricao = "Roupas",
                foto = "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=500&h=500&fit=crop"
            ),
            Category(
                id = "3",
                descricao = "Livros",
                foto = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=500&h=500&fit=crop"
            ),
            Category(
                id = "4",
                descricao = "Acessórios",
                foto = "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500&h=500&fit=crop"
            ),
            Category(
                id = "5",
                descricao = "Beleza",
                foto = "https://images.unsplash.com/photo-1596462502278-27bfdc403348?w=500&h=500&fit=crop"
            ),
            Category(
                id = "6",
                descricao = "Casa",
                foto = "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=500&h=500&fit=crop"
            )
        )
    }

    fun getLocalItems(): List<Item> {
        return listOf(
            Item(
                id = 1,
                nome = "Smartphone Samsung Galaxy S23",
                preco = 2999.99,
                urlImagem = "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400&h=400&fit=crop",
                categoria = "Eletrônicos",
                descricao = "Smartphone premium com câmera de 50MP"
            ),
            Item(
                id = 2,
                nome = "Notebook Dell Inspiron 15",
                preco = 2499.99,
                urlImagem = "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400&h=400&fit=crop",
                categoria = "Eletrônicos",
                descricao = "Notebook com processador Intel i5"
            ),
            Item(
                id = 3,
                nome = "Tênis Nike Air Max 270",
                preco = 599.99,
                urlImagem = "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=400&h=400&fit=crop",
                categoria = "Roupas",
                descricao = "Tênis esportivo com tecnologia Air Max"
            ),
            Item(
                id = 4,
                nome = "Camiseta Polo Lacoste",
                preco = 199.99,
                urlImagem = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&h=400&fit=crop",
                categoria = "Roupas",
                descricao = "Camiseta polo clássica em algodão"
            ),
            Item(
                id = 5,
                nome = "Livro 'O Hobbit' - J.R.R. Tolkien",
                preco = 49.99,
                urlImagem = "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=400&fit=crop",
                categoria = "Livros",
                descricao = "Clássico da literatura fantástica"
            )
        )
    }
}