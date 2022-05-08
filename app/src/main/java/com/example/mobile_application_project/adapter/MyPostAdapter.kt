package com.example.mobile_application_project.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application_project.R
import com.example.mobile_application_project.TagActivity
import com.example.mobile_application_project.model.main.PostData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*

class MyPostAdapter: RecyclerView.Adapter<MyPostAdapter.MyViewHolder>() {
    private val TAG = "PostRecyclerViewAdapter"
    private var mContext: Context? = null
    private var mLayout = R.layout.row
    private var mPost= emptyList<PostData>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(mLayout, parent, false))
    }

    override fun getItemCount(): Int {
        return mPost.size
    }



    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        holder.itemView.authName.text = String.format("%s. %s %s", mPost[i].owner.title, mPost[i].owner.firstName, mPost[i].owner.lastName )

        holder.itemView.Date.text = mPost[i].publishDate

        Picasso.get().load(mPost[i].owner.picture).into(holder.itemView.avatar);

        Picasso.get().load(mPost[i].image).into(holder.itemView.mainImage);

        holder.itemView.content.text = mPost[i].text

        holder.itemView.tag1.text = mPost[i].tags[0]
        holder.itemView.tag2.text = mPost[i].tags[1]
        holder.itemView.tag3.text = mPost[i].tags[2]

        holder.itemView.tag1.setOnClickListener {
            val tag = it.tag1.text
            val intent = Intent(mContext, TagActivity::class.java)
            intent.putExtra("tag", tag)
            mContext?.startActivity(intent)
        }
        holder.itemView.tag2.setOnClickListener {
            val tag = it.tag2.text
            val intent = Intent(mContext, TagActivity::class.java)
            intent.putExtra("tag", tag)
            mContext?.startActivity(intent)
        }
        holder.itemView.tag3.setOnClickListener {
            val tag = it.tag3.text
            val intent = Intent(mContext, TagActivity::class.java)
            intent.putExtra("tag", tag)
            mContext?.startActivity(intent)
        }
    }

    fun setPostData(context: Context, layout: Int, Post: List<PostData>){
        mPost = Post
        mContext = context
        mLayout = layout
        notifyDataSetChanged()
    }

}