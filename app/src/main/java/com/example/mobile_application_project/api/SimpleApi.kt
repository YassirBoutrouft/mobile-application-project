package com.example.mobile_application_project.api
import com.example.mobile_application_project.model.main.PostData
import com.example.mobile_application_project.model.main.Posts
import com.example.mobile_application_project.model.delete.Delete
import com.example.mobile_application_project.model.main.Data
import retrofit2.Response
import retrofit2.http.*
interface SimpleApi {
    @Headers("app-id: 6273cf6d2cd1e855d23b1726")
    @GET("post")

    suspend fun getPosts(
        @Query("page") pageNumber: Int,
        @Query("limit") postsNumber: Int
    ): Response<Posts>

    @Headers("app-id:6273cf6d2cd1e855d23b1726 ")
    @GET("post/{post}")
    suspend fun getPost(
        @Path("post") postId: String
    ): Response<PostData>

    @Headers("app-id:6273cf6d2cd1e855d23b1726 ")
    @GET("tag/{tag}/post")
    suspend fun getPostsByTag(
        @Path("tag") tag: String,
        @Query("limit") postsNumber: Int
    ): Response<Posts>

    @Headers("app-id: 6273cf6d2cd1e855d23b1726")
    @DELETE("post/{post}")
    suspend fun deletePost(
        @Path("post") postId: String
    ): Response<Delete>

    @FormUrlEncoded
    @Headers("app-id: 6273cf6d2cd1e855d23b1726")
    @POST("post/create")
    suspend fun createPost(
        @Field("text") text: String,
        @Field("image") image: String,
        @Field("likes") likes: Int,
        @Field("tags") tags: ArrayList<String>,
        @Field("owner") owner: String,
    ): Response<Data>
}