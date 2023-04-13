package com.example.catapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.MainActivity
import com.example.catapp.R
import com.example.catapp.dialogues.CustomDialogueFragment
import com.example.catapp.model.Comment

class CommentAdapter(context: Context) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments = mutableListOf<Comment>()
    var cont = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        holder.body.text = comments[position].body
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var body: TextView = itemView.findViewById(R.id.body)
        var editButton: ImageButton = itemView.findViewById(R.id.editButton)
        var deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        var starButton: ImageButton = itemView.findViewById(R.id.starButton)

        init {
            deleteButton.setOnClickListener {
                openDeleteDialogue()
            }
            editButton.setOnClickListener {
                val dialogFragment =
                    CustomDialogueFragment(comments[adapterPosition], adapterPosition, cont)
                dialogFragment.show(
                    (cont as AppCompatActivity).supportFragmentManager,
                    "customDialogue"
                )

            }
            starButton.setOnClickListener {
                if (comments[adapterPosition].isClicked) {
                    starButton.setColorFilter(Color.BLACK)
                    comments[adapterPosition].isClicked = false
                } else {
                    starButton.setColorFilter(Color.YELLOW)
                    comments[adapterPosition].isClicked = true
                }
            }


            itemView.setOnClickListener {
                (cont as MainActivity).currentComment(comments[adapterPosition])

            }

        }

        private fun openDeleteDialogue() {
            val builder = AlertDialog.Builder(cont)
            builder.setMessage("Are you sure you want to delete this comment?")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

                (cont as MainActivity).deleteCommentFromDb(comments[adapterPosition])

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }

    }

    fun setData(newList: MutableList<Comment>) {
        comments = newList
        notifyDataSetChanged()
    }
}