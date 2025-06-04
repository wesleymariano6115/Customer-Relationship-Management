package com.aurora_technologies.crm3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aurora_technologies.crm3.activities.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start the Java LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // Close this activity so user cannot return here
        finish()
    }
}
