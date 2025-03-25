package com.s10plusArtifacts.app.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.s10plusArtifacts.componets.bases.BaseViewModel
import com.s10plusArtifacts.sesion.SessionDelegateCore
import com.s10plusArtifacts.utils.ComponentConvert
import com.s10plusArtifacts.utils.ConvertComponent
import com.s10plusArtifacts.utils.View
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class ChangeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionDelegate: SessionDelegateCore
): BaseViewModel(), SessionDelegateCore by sessionDelegate {
    private val _componentsFromJson = MutableLiveData<MutableList<ComponentConvert>>()
    var componentsFromJson: LiveData<MutableList<ComponentConvert>> = _componentsFromJson
    fun getView(idView:String){
        val gson = Gson()
        var listView = sessionDelegate.views
        if(views.isNotEmpty()){
            var arrayViews = gson.fromJson(views,Array<View>::class.java)
            arrayViews.forEach { view ->
                if(view.id == idView){
                    var convert =  ConvertComponent(view.component)
                    _componentsFromJson.value = convert
                }
            }

        }
    }
}