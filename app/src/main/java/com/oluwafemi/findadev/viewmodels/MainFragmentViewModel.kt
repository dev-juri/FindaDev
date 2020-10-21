package com.oluwafemi.findadev.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.oluwafemi.findadev.model.Dev
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var firestoreInstance: FirebaseFirestore
    private var _devList = MutableLiveData<ArrayList<Dev>>()
    val devList: LiveData<ArrayList<Dev>>
        get() = _devList
    private val _status = MutableLiveData<UploadStatus>()
    val status: LiveData<UploadStatus>
        get() = _status

    private val _navigateToSelectedProperty = MutableLiveData<Dev>()
    val navigateToSelectedProperty : LiveData<Dev>
        get() = _navigateToSelectedProperty

    init {
        firestoreInstance = FirebaseFirestore.getInstance()
        getAllDevs()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getAllDevs() {
        viewModelScope.launch {
            firestoreInstance.collection("devs").addSnapshotListener { value, error ->
                if (error != null) {
                    _status.value = UploadStatus.FAILURE
                    return@addSnapshotListener
                }
                if (value != null) {
                    val allUsers = ArrayList<Dev>()
                    val documents = value.documents
                    documents.forEach{
                        val devFromDB = it.toObject(Dev::class.java)
                        if (devFromDB != null) {
                            allUsers.add(devFromDB)
                        }
                    }
                    _devList.value = allUsers
                }
            }
        }
    }
    fun displayPropertyDetails(dev: Dev){
        _navigateToSelectedProperty.value = dev
    }
    fun displayPropertyDetailsCompleted(){
        _navigateToSelectedProperty.value = null
    }
}