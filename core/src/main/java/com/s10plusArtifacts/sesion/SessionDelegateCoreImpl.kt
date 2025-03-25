package com.s10plusArtifacts.sesion

import android.content.Context
import android.text.TextUtils
import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.s10plusArtifacts.utils.PersonalInformation
import com.s10plusArtifacts.utils.Preconfiguration
import com.s10plusArtifacts.utils.View
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionDelegateCoreImpl @Inject constructor(
    @ApplicationContext val context: Context
) : SessionDelegateCore {

    private var  mapToken: Map<String,Any> = mapOf()
    override var activeGeoLocalization: Boolean
        get() = context.ActiveGeoLocalization()
        set(value) {
            context.ActiveGeoLocalization(value)
        }
    override var interceptorPhones: Boolean
        get() = context.InterceptorPhone()
        set(value) {
            context.InterceptorPhone(value)
        }
    override var phone: String
        get() = context.Phone()
        set(value) {
            context.Phone(value)
        }
    override var offline: Boolean
        get() = context.Offline()
        set(value) {
            context.Offline(value)
        }
    override var showState: Boolean
        get() = context.ShowState()
        set(value) {
            context.ShowState(value)
        }
    override var urlAnalytics: String
        get() = context.UrlAnalytics()
        set(value) {
            context.UrlAnalytics(value)
        }
    override var urlSound: String
        get() = context.UrlSound()
        set(value) {
            context.UrlSound(value)
        }
    override var status: Int
        get() = context.Status()
        set(value) {
            context.Status(value)
        }
    override var views: String
        get() = context.Views()
        set(value) {
            context.Views(value)
        }
    override var isFirstSesion: Boolean
        get() = context.isFirstSession()
        set(value) {
            context.isFirstSession(value)
        }
    override var currentPhone: String
        get() = context.CurrentPhone()
        set(value) {
            context.CurrentPhone(value)
        }
    override var state: String
        get() = context.State()
        set(value) {
            context.State(value)
        }
    override var lat: String
        get() = context.Lat()
        set(value) {
            context.Lat(value)
        }
    override var lng: String
        get() = context.Lng()
        set(value) {
            context.Lng(value)
        }
    override var serial: String
        get() = context.Serial()
        set(value){
            context.Serial(value)
        }
    override var tokenClick: String
        get() = context.TokenClick()
        set(value) {
            context.TokenClick(value)
        }
    override var personalInfo: PersonalInformation?
        get() = context.PersonalInfo()
        set(value) {
            if (value != null) {
                context.PersonalInfo(value)
            }
        }
    override var welcomeVideo: String
        get() = context.WelcomeVideo()
        set(value) {
            context.WelcomeVideo(value )
        }
    override var idApp: String
        get() = context.IdApp()
        set(value) {
            context.IdApp(value)
        }
    override var analiticOpen: String
        get() = context.AnaliticOpen()
        set(value) {
            context.AnaliticOpen(value)
        }

    override fun createSession(token: String) {
        decodeJWT(token)
    }

    override fun getId(): String =
        mapToken["id"].toString()


    override fun setPreConfiguration(value: Preconfiguration) {
        this.activeGeoLocalization = value.activeGeoLocalization
        val interceptorPhone = mutableListOf<String>()
        value.interceptorPhone.forEach { phone ->
            interceptorPhone.add(phone.replace("-",""))
        }
        this.phone = if(value.interceptorPhone != null) TextUtils.join(",",interceptorPhone) else ""
        this.offline = value.offline
        this.showState = value.showState
        this.urlAnalytics = value.urlAnalytics
        this.urlSound = value.urlSound
        this.welcomeVideo = if(value.welcomeVideo != null) value.welcomeVideo else ""
        this.analiticOpen = if(value.tagAnalyticOpen!= null) value.tagAnalyticOpen else ""
    }

    override fun setViews(value: List<View>) {
        var viesJson = Gson().toJson(value)
        this.views = viesJson
    }


    override fun setSerialConfig(value: String) {
        this.serial  = value
    }

    override fun updatePersonalInfo(value: PersonalInformation) {
        //var personalInfoJson = Gson().toJson(value)
        this.personalInfo = value
    }

    private fun decodeJWT(token:String): Map<String,Any> {
        if(token.isEmpty()){
            return mapOf()
        }
        mapToken = JWT(token).claims.mapValues {
            entry ->
            entry.value.asString() ?: ""
        }
        return  mapToken
    }

}