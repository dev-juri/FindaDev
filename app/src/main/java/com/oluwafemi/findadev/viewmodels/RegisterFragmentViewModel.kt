package com.oluwafemi.findadev.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.oluwafemi.findadev.model.Dev

enum class UploadStatus {
    SUCCESS, FAILURE, USER_EXISTS, NO_RECORD
}

class RegisterFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var firestoreInstance: FirebaseFirestore

    private val _status = MutableLiveData<UploadStatus>()
    val status: LiveData<UploadStatus>
        get() = _status

    init {
        firestoreInstance = FirebaseFirestore.getInstance()
    }

    fun addDev(dev: Dev, email: String) {
        val firestoreCollection = firestoreInstance.collection("devs").document(email)
        Log.i("FireStoreLog", firestoreCollection.id)
        try {
            firestoreCollection.set(dev)
                .addOnCompleteListener { _status.value = UploadStatus.SUCCESS }
                .addOnFailureListener { _status.value = UploadStatus.FAILURE }
        } catch (e: Exception) {
            _status.value = UploadStatus.FAILURE
            Log.i("FireStoreLog", e.toString())
        }
    }


    fun checkUser(dev: Dev, email: String) {
        val firestoreCollection = firestoreInstance.collection("devs").document(email)

        firestoreCollection.get()
            .addOnSuccessListener {
                if (it.exists() && it != null) {
                    _status.value = UploadStatus.USER_EXISTS
                } else {
                    addDev(dev, email)
                }
            }
            .addOnFailureListener {
                _status.value = UploadStatus.FAILURE
            }
    }
}

