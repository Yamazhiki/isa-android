package com.isa.library.ext

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.isa.library.ui.IntentReason

fun Activity.replaceFragment(containerViewId: Int, fragment: Fragment, tag: String = "") {
    val manager = fragmentManager
    val transition = manager.beginTransaction()
    transition.replace(containerViewId, fragment)
    transition.commit()
}

fun Activity.toast(context: Context, text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}

fun Activity.toast(context: Context, resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, resId, duration).show()
}

fun Activity.prepareIntent(reason: IntentReason) {
    val intent = if (reason.condition) {
        Intent(this, reason.target)
    } else {
        Intent(this, reason.block)
    }
    startActivity(intent)
}