package com.example.catapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.catapp.viewmodels.MainViewModel
import com.example.catapp.api.LoginRequest
import com.example.catapp.repositories.CommentRepository
import com.example.catapp.viewmodels.ViewModelFactory

class LogInActivity() : AppCompatActivity() {

    companion object {
        var globalVar = ""
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //sharedPref
        val sharedPreference = getSharedPreferences("sharedPref", MODE_PRIVATE)
        globalVar="${sharedPreference.getString("url", null).toString()}"

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val getDataBtn = findViewById<Button>(R.id.getDataBtn)

        loginBtn.setOnClickListener {
            initializeViewModel()
            val email = findViewById<EditText?>(R.id.email).text.toString()
            val password = findViewById<EditText?>(R.id.password).text.toString()
            val loginRequest = LoginRequest(email, password)
            loginUser(loginRequest)
        }

        getDataBtn.setOnClickListener {
            initializeViewModel()
            //povuci sa apija i sacuvaj u bazi
            try {
                viewModel.getComments()
            } catch (ex: Exception) {
                Log.d("LogInActivity", "An error has occurred: ${ex.message.toString()}")
            }
        }

    }

    fun initializeViewModel() {
        val repository = CommentRepository()
        val viewModelFactory = ViewModelFactory(repository, this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    fun loginUser(loginRequest: LoginRequest) {
        viewModel.loginUser(loginRequest)
    }

    fun settingsRedirect() {
        val intent = Intent(this@LogInActivity, HostActivity::class.java)
        startActivity(intent)
    }

    fun mainActRedirect() {
        val intent = Intent(this@LogInActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun displayMsg() {
        viewModel.message.observe({ lifecycle }) {
            it?.let {
                Toast.makeText(this, "${it}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_header, menu)
        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsBtn -> settingsRedirect()
        }
return true


    }

}
