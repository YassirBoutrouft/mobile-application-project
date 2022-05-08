package com.example.mobile_application_project

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_application_project.repository.Repository

class DeleteClass : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_post)
        incomingIntent
    }
    private val incomingIntent: Unit
        private get() {
            if (intent.hasExtra("post_id")) {

                val postId = intent.getStringExtra("post_id")
                val warning: TextView = findViewById<View>(R.id.successText) as TextView
                val warningText = "Post with id : $postId has been deleted"

                warning.setText(warningText)

                if (postId != null) {
                    delete(postId)
                }
            }
        }



    private fun delete(postId: String) {
        // forward with deletePost
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.deletePost(postId)
    }
}