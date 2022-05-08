package com.example.mobile_application_project.model.main


data class Data (
    val id: String,
    val image: String,
    val likes: Int,
    val tags: ArrayList<String>,
    val text: String,
    val publishDate: String,
    val owner: Owner
)