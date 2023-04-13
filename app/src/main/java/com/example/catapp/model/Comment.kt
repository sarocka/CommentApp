package com.example.catapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Comment(

    @SerializedName("postId")
    @PrimaryKey(autoGenerate = true)
    val postId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("body")
    val body: String,
    var isClicked: Boolean,

    ) {
    override fun toString(): String {
        return "Comment(postId=$postId, id=$id, name='$name', email='$email', body='$body')"
    }
}
