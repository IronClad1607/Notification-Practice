package com.ironclad.notificationpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}