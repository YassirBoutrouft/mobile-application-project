package com.example.mobile_application_project

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application_project.adapter.MyAdapter
import com.example.mobile_application_project.repository.Repository
import com.example.mobile_application_project.ui.BaseUrl.Companion.postsNumber
import kotlinx.android.synthetic.main.tag_activity.*

class TagActivity: AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { MyAdapter() }
    lateinit var layoutManager: LinearLayoutManager
    var isLoading = false
    var callouts = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tag_activity)
        incomingIntent
    }

    private val incomingIntent: Unit
        private get() {
            val button: Button = findViewById<View>(R.id.mainTag) as Button
            if (intent.hasExtra("tag")) {
                val tag = intent.getStringExtra("tag")
                button.setText(tag)
                setupRecyclerview()

                layoutManager = LinearLayoutManager(this)
                tagRecyclerView.layoutManager = layoutManager
                if (tag != null) {
                    forward(tag, postsNumber, 0)
                    tagRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                            super.onScrollStateChanged(recyclerView, newState)
                            if (!recyclerView.canScrollVertically(1)) {
                                if(!isLoading) {
                                    forward(tag,postsNumber*(callouts+1),3000)
                                    callouts++
                                }
                            }
                        }
                    })
                }
            }
        }

    private fun forward(tag: String,postsNumber: Int, delay: Long) {
        isLoading = true
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getPostsByTag(tag, postsNumber)
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                Handler().postDelayed({
                    isLoading = false
                }, delay)
                myAdapter.setData(this,R.layout.row, response.body()?.data!!)
            }else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun setupRecyclerview() {
        tagRecyclerView.adapter = myAdapter
        tagRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}