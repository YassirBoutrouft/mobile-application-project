package com.example.mobile_application_project.repository
import com.example.mobile_application_project.api.RetrofitInstance
import com.example.mobile_application_project.model.delete.Delete
import com.example.mobile_application_project.model.main.Data
import com.example.mobile_application_project.model.main.PostData
import com.example.mobile_application_project.model.main.Posts
import retrofit2.Response

class Repository {
    suspend fun getPosts(pageNumber: Int, postsNumber: Int): Response<Posts> {
        return RetrofitInstance.api.getPosts(pageNumber, postsNumber)
    }

    suspend fun getPost(postId: String): Response<PostData> {
        return RetrofitInstance.api.getPost(postId)
    }

    suspend fun getPostsByTag(tag: String, postsNumber: Int): Response<Posts> {
        return RetrofitInstance.api.getPostsByTag(tag, postsNumber)
    }

    suspend fun deletePost(postId: String): Response<Delete> {
        return RetrofitInstance.api.deletePost(postId)
    }

    suspend fun createPost(text: String, image: String,likes: Int, tags: ArrayList<String>, owner: String): Response<Data> {
        return RetrofitInstance.api.createPost(text, image,likes, tags ,owner)
    }
}