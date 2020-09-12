package com.ironclad.notificationpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFirebase.setOnClickListener {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d("PUI", "GetInstance Id Failed", task.exception)
                        return@addOnCompleteListener
                    }

                    val token = task.result?.token

                    Log.d("PUI", "Token: $token")
                }
        }

        btnInternalNotification.setOnClickListener {
            val notificationIntent = Intent(this, InternalNotificationActivity::class.java)
            startActivity(notificationIntent)
        }
    }
}