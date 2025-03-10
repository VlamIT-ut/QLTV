package com.example.qltv

data class User(
    val id:Int,
    var name:String,
    val borrowedBooks:MutableList<Book> = mutableListOf()
)