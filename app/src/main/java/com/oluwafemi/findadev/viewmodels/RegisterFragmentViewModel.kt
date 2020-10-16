package com.oluwafemi.findadev.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.oluwafemi.findadev.model.Dev
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

enum class UploadStatus {
    LOADING, SUCCESS, FAILURE
}

class RegisterFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private lateinit var firestoreInstance: FirebaseFirestore

    private val _status = MutableLiveData<UploadStatus>()
    val status: LiveData<UploadStatus>
        get() = _status

    init {
        firestoreInstance = FirebaseFirestore.getInstance()
    }

    fun addDev(dev: Dev) {
        viewModelScope.launch {
            val firestoreCollection = firestoreInstance.collection("devs")
            try {
                _status.value = UploadStatus.LOADING
                firestoreCollection.add(dev)
                    .addOnCompleteListener {_status.value = UploadStatus.SUCCESS}
                    .addOnFailureListener {_status.value = UploadStatus.FAILURE}
            } catch (e: Exception) {
                _status.value = UploadStatus.FAILURE
                Log.i("FireStoreLog", e.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

