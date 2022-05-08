package com.example.mobile_application_project.model.main


data class Posts (
    val data : ArrayList<Data>,
    val total: Int,
    val page: Int,
    val limit: Int
)

