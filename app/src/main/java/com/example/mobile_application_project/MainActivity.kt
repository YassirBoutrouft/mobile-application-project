package com.example.mobile_application_project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application_project.adapter.MyAdapter
import com.example.mobile_application_project.repository.Repository
import com.example.mobile_application_project.ui.BaseUrl.Companion.pageStart
import com.example.mobile_application_project.ui.BaseUrl.Companion.postsNumber
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {
    //init
    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { MyAdapter() }
    lateinit var layoutManager: LinearLayoutManager
    var isLoading = false
    var callouts = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerview()
        createNewPostButton.setOnClickListener {
            val intent = Intent(this, CreatePost::class.java)
            this.startActivity(intent)
        }


        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        forward(pageStart, postsNumber, 0)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        forward(pageStart, postsNumber * (callouts + 1), 3000)

                        callouts++
                    }
                }
            }
        })

    }

    private fun forward(pageNumber: Int, postsNumber: Int, delay: Long) {
        // ini repository.Repository
        isLoading = true
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPosts(pageNumber, postsNumber)
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {

                Handler().postDelayed({
                    isLoading = false
                }, delay)
                    myAdapter.setData(this, R.layout.row, response.body()?.data!!)
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupRecyclerview() {
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}