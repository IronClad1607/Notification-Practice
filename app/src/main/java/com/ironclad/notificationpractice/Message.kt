package com.ironclad.notificationpractice

class Message(val text: CharSequence, val sender: CharSequence?) {
    val timestamp: Long = System.currentTimeMillis()
}