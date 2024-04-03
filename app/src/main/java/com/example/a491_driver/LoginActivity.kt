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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)

        val createAccount = findViewById<TextView>(R.id.createAccount)
        createAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.putString("username", "test")
            editor.putInt("user id", 1)
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
            // FOR WHEN API IS IMPLEMENTED
//            GlobalScope.launch(Dispatchers.Main){
//                checkAccount(username, password)
//            }
        }
    }

    // FOR WHEN API IS IMPLEMENTED

    // Logs in if username/password is valid
//    suspend fun checkAccount(username: String, password: String) {
//        try {
//            // send input data to checkPassword API
//            val apiService = RetrofitClient.instance.create(ApiService::class.java)
//            val credentials = Account(
//                "",
//                "",
//                username,
//                password,
//                "",
//                "",
//                ""
//            )
//
//            // If valid, returns message and user id, throws error otherwise
//            val returnMessage: ReturnMessage = apiService.checkPassword(credentials)
//
//            if (returnMessage.message == "Password is correct") {
//                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
//                val editor = sharedpreferences.edit()
//                editor.clear()
//                editor.putString("username", username)
//                editor.putInt("user id", returnMessage.user_id)
//                editor.putString("user location", returnMessage.location)
//                editor.apply()
//                startActivity(Intent(this, MainActivity::class.java))
//            } else {
//                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: Exception) {
//            Log.e("RegisterActivity", "Error: ${e.message}", e)
//            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
//        }
//    }

}

class Account (
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String,
    val phone_number: String,
    val location: String,
    val payment_method: String
) {
}

class ReturnMessage(val message: String, val user_id: Int, val location: String){
}
