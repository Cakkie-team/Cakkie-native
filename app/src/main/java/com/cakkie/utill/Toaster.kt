package com.cakkie.utill

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cakkie.R


class Toaster(
    context: Context,
    message: String,
    image: Int
) : Toast(context) {

    init {
        // Use the context to access the LayoutInflater
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom layout for your toast
        val toastLayout = inflater.inflate(R.layout.custom_toast_layout, null)

        // Set the message and image in your custom layout
        val messageTextView = toastLayout.findViewById<TextView>(R.id.toast_message)
        val imageView = toastLayout.findViewById<ImageView>(R.id.toast_image)

        messageTextView.text = message
        imageView.setImageResource(image)

        // Set your custom layout on the toast
        view = toastLayout

        //set gravity to top of screen
        setGravity(Gravity.TOP, 0, 60)

        // Set the duration for your toast
        duration = LENGTH_LONG
    }
}