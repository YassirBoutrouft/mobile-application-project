package com.example.mobile_application_project
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_application_project.adapter.MyPostAdapter
import com.example.mobile_application_project.model.main.PostData
import com.example.mobile_application_project.repository.Repository
import kotlinx.android.synthetic.main.post_activity.*

class PostActivity: AppCompatActivity() {
    //ini
    private lateinit var viewModel: MainViewModel
    private val MyPostAdapter by lazy { MyPostAdapter() }
    lateinit var layoutManager: LinearLayoutManager
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_activity)
        incomingIntent
    }

    private val incomingIntent: Unit
        private get() {
            if (intent.hasExtra("post_id")) {
                val postId = intent.getStringExtra("post_id")

                setupRecyclerview()
                layoutManager = LinearLayoutManager(this)
                postRecyclerView.layoutManager = layoutManager
                if (postId != null) {

                    forward(postId, 0)
                }
            }
        }

    private fun forward(postId: String, delay: Long) {

        isLoading = true
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getPost(postId)
        viewModel.myPostResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {

                Handler().postDelayed({
                    isLoading = false
                }, delay)
                val post: List<PostData> = listOf(response.body()) as List<PostData>
                MyPostAdapter.setPostData(this,R.layout.post_layout, post)
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerview() {
        postRecyclerView.adapter = MyPostAdapter
        postRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}