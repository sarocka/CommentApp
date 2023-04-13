package com.example.catapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class HostActivity : AppCompatActivity() {


    private lateinit var globalVarHostAct: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        val sharedPreference = getSharedPreferences("sharedPref", MODE_PRIVATE)
        //private - preferences cannot be shared with other apps
        //append mode - appends new preferences to the existing ones
        val editor = sharedPreference.edit() // omogucava upisivanje u sharedPreference


        val saveButton: Button = findViewById(R.id.saveButton)


        if (sharedPreference != null) {
            var globalVarHostAct = "${sharedPreference.getString("url", null).toString()}"
            LogInActivity.globalVar = globalVarHostAct
            Log.d("HostActivity", "Global var in onCreate: $globalVarHostAct")
            findViewById<EditText>(R.id.url).setText(
                sharedPreference.getString("url", null).toString()
            )

        }

        saveButton.setOnClickListener run@{
            val url = findViewById<EditText>(R.id.url).text.toString()

            if (!isValid(url)) {
                Toast.makeText(this, "Url is not in a valid form", Toast.LENGTH_LONG).show()
                return@run
            }
            globalVarHostAct = url
            LogInActivity.globalVar = globalVarHostAct

            editor.apply {
                putString("url", url)
                apply()
            }

        }

    }

    fun isValid(url: String?): Boolean {
        /* Try creating a valid URL */
        return try {
            URL(url).toURI()
            Log.d("HostActivity", "Url exists")
            true
        } // If there was an Exception
        // while creating URL object
        catch (e: Exception) {
            Toast.makeText(this, "Url not valid or does not exist", Toast.LENGTH_LONG)
            Log.d("HostActivity", "Url does not exist")
            false
        }
    }

}