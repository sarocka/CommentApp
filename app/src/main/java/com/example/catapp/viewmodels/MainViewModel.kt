package com.example.catapp.viewmodels


import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.catapp.LogInActivity
import com.example.catapp.MainActivity
import com.example.catapp.api.LoginRequest
import com.example.catapp.database.CommentDB
import com.example.catapp.model.Comment
import com.example.catapp.repositories.CommentRepository
import com.example.catapp.repositories.CommentRepositoryDB
import com.example.catapp.repositories.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response


private lateinit var repositoryDB: CommentRepositoryDB
private lateinit var loginRepository: LoginRepository

class MainViewModel(var repository: CommentRepository, context: Context) : ViewModel() {

    var comments: LiveData<List<Comment>>
    var message = MutableLiveData<String>()

    val comment = MutableLiveData<Comment>()
    val cont = context

    init {
        val commentDao = CommentDB.getDatabase(context).commentDao()
        repositoryDB = CommentRepositoryDB(commentDao)
        repository = CommentRepository()
        comments = repositoryDB.comments
        loginRepository = LoginRepository()
    }

    fun loginUser(loginRequest: LoginRequest) {

        viewModelScope.launch {
            val response = loginRepository.loginUser(loginRequest)

            if (response.isSuccessful) {
                (cont as LogInActivity).mainActRedirect()
                // redirect to mainAct
                Log.d("MainViewModel", "Login Response code: ${response.code()}")
            } else {
                val jObjError = JSONObject(response?.errorBody()?.string())
                Log.d("MainViewModel", " Error body: {${jObjError}")
                message.postValue(jObjError.optString("error"))
                (cont as LogInActivity).displayMsg()
                Log.d("MainViewModel", "Login error: ${jObjError.optString("error")}")
            }
        }

    }

    fun getComments() {
        Log.d("MainViewModel", "entered getComments()")

        val scope = viewModelScope.launch {
            Log.d(
                "MainViewModel",
                "This is scope: ${this.toString()} ${this.coroutineContext} ${Dispatchers}"
            )

            try {
                val response: Response<List<Comment>> = repository.getComments()

                if (response.isSuccessful) {
                    //napuniti bazu responsom
                    //insert all metoda iz dao
                    message.postValue("Data successfully downloaded")
                    (cont as LogInActivity).displayMsg()
                    repositoryDB.deleteAll()
                    repositoryDB.insertAll(repository.getComments().body()!!)
                    Log.d("MainViewModel", "Response: ${response.code()}")
                } else {
                    val jObjError = JSONObject(response?.errorBody()?.string())
                    message.postValue(jObjError.optString("error"))
                    (cont as MainActivity).displayMsg()
                }
            } catch (ex: Exception) {
                Log.d("MainViewModel", "Error: ${ex.message}")
                message.postValue(ex.message)
                (cont as LogInActivity).displayMsg()
                //https://jsonplaceholder.typicode.com/
            }


        }
        Log.d("MainViewModel", "${scope}")
    }

    fun deleteComment(position: Int): Response<Void>? {
        viewModelScope.launch {
            val response = repository.deleteComment(position)

            if (response.isSuccessful) {
                message.postValue("Successfully deleted")
                (cont as MainActivity).displayMsg()
            } else {
                message.postValue("Something went wrong, please try again")
                (cont as MainActivity).displayMsg()
            }

        }
        return null
    }

    fun editComment(comment: Comment, id: Int) {
        viewModelScope.launch {
            val response = repository.editComment(comment, id)

            if (response.isSuccessful) {
                message.postValue("Comment updated")
                (cont as MainActivity).displayMsg()
            } else {
                message.postValue("Something went wrong, please try again")
                (cont as MainActivity).displayMsg()
            }
        }

    }

    fun updateFragment(updatedComment: Comment) {
        viewModelScope.launch {
            comment.postValue(updatedComment)
        }
    }

    fun addCommentToDB(newComment: Comment) {
        viewModelScope.launch {
            repositoryDB.addComment(newComment)
        }

    }

    fun updateCommentFromDB(comment: Comment) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Updated comment: ${comment}")
            repositoryDB.updateComment(comment)
            message.postValue("Comment updated")
            (cont as MainActivity).displayMsg()

        }
    }

    fun apiDataToDb() {
        viewModelScope.launch {
            Log.d("MainViewModel", "This is scope: ${this}")

            Log.d(
                "MainViewModel",
                "Size of the comments list from database: ${repositoryDB.getDataCount()}"
            )

            if (repositoryDB.getDataCount() != 0) {
                Log.d("MainViewModel", "Database is not empty")
            } else {
                //napuniti bazu listom sa neta
                //proci kroz listu sa neta pa ubacivati jedan po jedan
                val response = repository.getComments()
                Log.d("MainViewModel", "database empty")

                if (response.isSuccessful) {
                    comments = repositoryDB.comments
                    Log.d("MainViewModel", "Comments from database: ${comments.value}")

                } else {
                    message.postValue("Something went wrong, please try again")
                    (cont as MainActivity).displayMsg()
                }


            }
        }

    }

    fun deleteCommentFromDb(comment: Comment) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Comment for deletion: ${comment}")
            repositoryDB.deleteComment(comment)
            message.postValue("Comment successfully deleted")
            (cont as MainActivity).displayMsg()
        }
    }

    fun searchDatabase(query: String): LiveData<List<Comment>> {
        return repositoryDB.searchDatabase(query)
    }


}