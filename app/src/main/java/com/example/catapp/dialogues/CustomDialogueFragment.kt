package com.example.catapp.dialogues

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.catapp.MainActivity
import com.example.catapp.R
import com.example.catapp.model.Comment

class CustomDialogueFragment(comment: Comment, position: Int, context: Context) : DialogFragment() {

    val commentForEdit = comment
    val cont = context
    val index = position

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.edit_comment_dialogue, container, false)
        val comment_id = rootView.findViewById<TextView>(R.id.comment_id)
        comment_id.text = commentForEdit.id.toString()
        val comment_body = rootView.findViewById<TextView>(R.id.body)
        comment_body.text = commentForEdit.body
        val comment_name = rootView.findViewById<TextView>(R.id.name)
        comment_name.text = commentForEdit.name
        val comment_email = rootView.findViewById<TextView>(R.id.email)
        comment_email.text = commentForEdit.email

        val button = rootView.findViewById<Button>(R.id.edit_button)

        button.setOnClickListener {

            val editedBody = rootView.findViewById<EditText?>(R.id.body).text.toString()
            val editedName = rootView.findViewById<EditText?>(R.id.name).text.toString()
            val editedEmail = rootView.findViewById<EditText?>(R.id.email).text.toString()

            val editedComment = Comment(
                commentForEdit.postId,
                commentForEdit.id,
                editedName,
                editedEmail,
                editedBody,
                false
            )

            Log.d("CustomDialogueFragment", "This is edited comment: ${editedComment}")

            (cont as MainActivity).editCommentInDB(editedComment, index)
            dismiss()
        }

        return rootView
    }
}
