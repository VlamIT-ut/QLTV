package com.example.qltv

data class Book(
    val id:Int,
    val title:String,
    val author:String,
    var isBorrowed:Boolean = false
)
