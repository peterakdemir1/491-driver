package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameInput = findViewById<EditText>(R.id.usernameInput)
        passwordInput = findViewById<EditText>(R.id.passwordInput)

        val createAccount = findViewById<TextView>(R.id.createAccount)
        createAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            GlobalScope.launch(Dispatchers.Main){
                checkAccount(username, password)
                usernameInput.text.clear()
                passwordInput.text.clear()
            }
        }
    }


    // Logs in if username/password is valid
    suspend fun checkAccount(username: String, password: String) {
        try {
            // send input data to checkPassword API
            val apiService = RetrofitClient.instance.create(APIService::class.java)
            val credentials = DriverCredentials(username, password)

            // If valid, returns message and user id, throws error otherwise
            val returnMessage: ReturnMessage = apiService.checkDriverCredentials(credentials)

            if (returnMessage.message == "Credentials are correct") {
                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val editor = sharedpreferences.edit()
                editor.clear()
                editor.putString(getString(R.string.username_key), username)
                editor.putInt(getString(R.string.driver_id_key), returnMessage.driver_id)
                editor.apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error: ${e.message}", e)
            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
        }
    }

}

class DriverCredentials (val username: String, val password: String)

class ReturnMessage(
    val message: String,
    val driver_id: Int,
    val phone_number: String,
    val license_id: String,
    val verified: Boolean,
    val direct_deposit: Boolean
){
}

