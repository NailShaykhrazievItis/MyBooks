package com.example.model.dc

data class BookDTO(
    var id: Int,
    var name: String,
    var pages: Int,
    var date: Long,
    var author: AuthorDTO? = null
)
