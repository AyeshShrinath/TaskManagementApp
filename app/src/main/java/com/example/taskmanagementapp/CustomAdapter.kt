package com.example.taskmanagementapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val activity: Activity, private val context: Context,
                    private val book_id: ArrayList<*>, private val book_title: ArrayList<*>,
                    private val book_author: ArrayList<*>, private val book_pages: ArrayList<*>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.book_id_txt.text = book_id[position].toString()
        holder.book_title_txt.text = book_title[position].toString()
        holder.book_author_txt.text = book_author[position].toString()
        holder.book_pages_txt.text = book_pages[position].toString()
        // Recyclerview onClickListener
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", book_id[position].toString())
            intent.putExtra("title", book_title[position].toString())
            intent.putExtra("author", book_author[position].toString())
            intent.putExtra("pages", book_pages[position].toString())
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return book_id.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var book_id_txt: TextView = itemView.findViewById(R.id.book_id_txt)
        var book_title_txt: TextView = itemView.findViewById(R.id.book_title_txt)
        var book_author_txt: TextView = itemView.findViewById(R.id.book_author_txt)
        var book_pages_txt: TextView = itemView.findViewById(R.id.book_pages_txt)
        var mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

        init {
            // Animate Recyclerview
            val translate_anim: Animation = AnimationUtils.loadAnimation(context, R.anim.translate_anim)
            mainLayout.animation = translate_anim
        }
    }
}
