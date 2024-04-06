package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var sharedpreferences: SharedPreferences
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fNameInput = findViewById<EditText>(R.id.firstNameInput)
        val lNameInput = findViewById<EditText>(R.id.lastNameInput)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        //val emailInput = findViewById<EditText>(R.id.emailInput)
        val locationInput = findViewById<EditText>(R.id.locationInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val paymentInput = findViewById<EditText>(R.id.paymentInput)
        val button = findViewById<Button>(R.id.registerButton)

        button.setOnClickListener {
            val newAccount = Account(
                fNameInput.text.toString(),
                lNameInput.text.toString(),
                usernameInput.text.toString(),
                passwordInput.text.toString(),
                phoneInput.text.toString(),
                locationInput.text.toString(),
                paymentInput.text.toString()
            )

            startActivity(Intent(this, LoginActivity::class.java))
            // FOR WHEN API IS IMPLEMENTED
//            GlobalScope.launch(Dispatchers.Main) {
//                postUserData(newAccount)
//            }

        }

    }

    // FOR WHEN API IS IMPLEMENTED
//    suspend fun postUserData (user: Account) {
//        try {
//            val apiService = RetrofitClient.instance.create(ApiService::class.java)
//            apiService.postUser(user)
//
//            // authenticate user to receive user id
//            val returnMessage: ReturnMessage = apiService.checkPassword(user)
//
//            // store username in sharedpreferences and login
//            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
//            val editor = sharedpreferences.edit()
//            editor.clear()
//            editor.putString("username", user.username)
//            editor.putInt("user id", returnMessage.user_id)
//            editor.putString("user location", user.location)
//            editor.apply()
//            startActivity(Intent(this, MainActivity::class.java))
//        } catch (e: Exception) {
//            Log.e("RegisterActivity", "Error: ${e.message}", e)
//        }
//    }
}