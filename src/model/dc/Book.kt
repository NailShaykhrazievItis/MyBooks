package com.example.model.dc

data class Book(
    var id: Int,
    var name: String,
    var pages: Int,
    var date: Long,
    var author: Author? = null
)
