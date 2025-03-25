package com.s10plusArtifacts.componets.bases

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.s10plusArtifacts.componets.Dialogs.DialogModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel:ViewModel() {
    val showDialogSuccess = MutableLiveData<Boolean>()
    val showDialogVideo = MutableLiveData<Boolean>()
    val urlVideoLiveData = MutableLiveData<String>()
    val dialogInformation = MutableLiveData<DialogModel>()
    val onClickDialogSuccess = MutableLiveData<() -> Unit>()
    val showPermission = MutableLiveData<Boolean>()
    val permissionisGranded = MutableLiveData<()->Unit>()
    val showDialogGetPhone = MutableLiveData<Boolean>()
    val onClickGetPhone = MutableLiveData<(String) -> Unit>()
    val showDialogStates = MutableLiveData<Boolean>()
    val onClickSelectedState = MutableLiveData<(Pair<String,String>) -> Unit>()
    val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> get() = _isLoading


    open fun showDialogSuccess(
        title:String,
        subTitle:String,
        btnTitle:String,
        onClick:()->Unit
    ){
        dialogInformation.postValue(DialogModel(title,subTitle,btnTitle))
        onClickDialogSuccess.postValue { onClick.invoke() }
        showDialogSuccess.postValue(true)
    }

    open fun showDialogSuccessModal(
            urlVideo:String
    ){
        showDialogVideo.postValue(true)
        urlVideoLiveData.postValue(urlVideo)
    }


    open fun showDialogGetPhone(onClickGetPhoneResponse:(String) -> Unit){
        showDialogGetPhone.postValue(true)
        onClickGetPhone.postValue(onClickGetPhoneResponse)
    }

    open fun showDialogState(onClickSelected:(Pair<String,String>) -> Unit){
        showDialogStates.postValue(true)
        onClickSelectedState.postValue(onClickSelected)
    }

    open fun closDialogAll(){
        showDialogSuccess.postValue(false)
        showDialogGetPhone.postValue(false)
        showDialogVideo.postValue(false)
        urlVideoLiveData.postValue("")
        _isLoading.value = false

    }

    open fun showPermission(active: Boolean,grandedPermission:()->Unit){
        showPermission.postValue(active)
        permissionisGranded.postValue(grandedPermission)
    }


}