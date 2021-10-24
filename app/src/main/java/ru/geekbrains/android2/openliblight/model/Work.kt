package ru.geekbrains.android2.openliblight.model

import com.google.gson.annotations.SerializedName

data class Work(
@SerializedName("key")
val key: String,
@SerializedName("title")
val title: String,
@SerializedName("cover_id")
val coverId: Int,
@SerializedName("authors")
val authors: List<Author>,
var subjKey: String
)
