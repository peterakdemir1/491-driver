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
        val licenseInput = findViewById<EditText>(R.id.licenseInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val button = findViewById<Button>(R.id.registerButton)

        button.setOnClickListener {
            val newDriver = Driver(
                fNameInput.text.toString(),
                lNameInput.text.toString(),
                usernameInput.text.toString(),
                passwordInput.text.toString(),
                phoneInput.text.toString(),
                licenseInput.text.toString()
            )

            GlobalScope.launch(Dispatchers.Main) {
                postDriverData(newDriver)
            }
            startActivity(Intent(this, LoginActivity::class.java))


        }

    }

    suspend fun postDriverData (driver: Driver) {
        try {
            val apiService = RetrofitClient.instance.create(APIService::class.java)
            val returnDriver = apiService.createDriver(driver)


            // store username in sharedpreferences and login
            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.putString(getString(R.string.username_key), driver.username)
            editor.putInt(getString(R.string.driver_id_key), returnDriver.driver_id)
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
        } catch (e: Exception) {
            Log.e("RegisterActivity", "Error: ${e.message}", e)
        }
    }
}

class ReturnDriver (
    val driver_id: Int,
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String,
    val phone_number: String,
    val license_id: String
)