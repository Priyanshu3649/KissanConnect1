package com.example.kisaanconnect.utils

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.example.kisaanconnect.R
import com.example.kisaanconnect.ui.registration.SignUpFragment


fun sendVerificationEmail(view: View) {
    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnSuccessListener {

        Snackbar.make(
            view,
            view.context.getString(R.string.check_email), Snackbar.LENGTH_LONG
        ).show()

    }?.addOnFailureListener { exception ->

        Log.d(SignUpFragment.TAG, "signUpNewUser: ${exception.message}")
        Snackbar.make(
            view,
            view.context.getString(R.string.resend_verification_mail), Snackbar.LENGTH_LONG
        ).show()

    }

    // sign out user
    FirebaseAuth.getInstance().signOut()
}