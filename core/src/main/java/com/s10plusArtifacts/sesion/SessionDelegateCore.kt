package com.s10plusArtifacts.sesion

import com.s10plusArtifacts.utils.PersonalInformation
import com.s10plusArtifacts.utils.Preconfiguration
import com.s10plusArtifacts.utils.View

interface SessionDelegateCore {

   var activeGeoLocalization:Boolean
   var interceptorPhones: Boolean
   var phone:String
   var offline:Boolean
   var showState:Boolean
   var urlAnalytics:String
   var urlSound:String
   var status:Int
   var views:String
   var isFirstSesion:Boolean
   var currentPhone:String
   var state:String
   var lat:String
   var lng:String
   var serial:String
   var tokenClick:String
   var personalInfo:PersonalInformation?
   var welcomeVideo:String
   var idApp:String
   var analiticOpen:String
   fun createSession(token:String)
   fun getId():String
   fun setPreConfiguration(value: Preconfiguration)
   fun setViews(value: List<View>)
   fun setSerialConfig(value:String)
   fun updatePersonalInfo(value:  PersonalInformation )
   /* var token:String
    var phoneNumber:String
    var audioUrl:String
    var geolocation:Boolean
    var isFirstSession:Boolean
    var lastUpdate:String




    fun setPhoneNumber(value: String)
    fun setAudioUrl(value:String)
    fun setGeolocation(value:Boolean)
    fun setFirstSession(value: String)
    fun setLastUpdate(value:String)
    fun setSession(json:String)
    fun getPhoneNumber():String
    fun getAudioUrl():String
    fun getGeolocation():String
    fun getFirstSession():String
    fun getLastUpdate():String*/

}