package com.s10plusArtifacts.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class Configuration(
    @SerializedName("appId")
    val appId: String,
    @SerializedName("views")
    val views: List<View>,
    @SerializedName("status")
    val status:Int,
    @SerializedName("preconfiguration")
    val preconfiguration: Preconfiguration,
    @SerializedName("personalInformation")
    val personalInformation: PersonalInformation
)
@Serializable
@Parcelize
data class PersonalInformation(
    @SerializedName("title")
    val title:String,
    @SerializedName("locationInformation")
    val locationInformation:LocationInformation,
    @SerializedName("urlImage")
    val urlImage:String,
    @SerializedName("showTypesData")
    val showTypesData:List<String>,

): Parcelable
@Serializable
@Parcelize
data class LocationInformation(
    @SerializedName("neighborhoods")
    val neighborhoods: Neighborhoods,
    @SerializedName("state")
    val state: StateConfig,
    @SerializedName("municipality")
    val municipality: Municipality,
): Parcelable
@Serializable
@Parcelize
data class Neighborhoods(
    @SerializedName("id_colonia")
    val id_colonia:Int,
    @SerializedName("colonia")
    val colonia:String
): Parcelable
@Serializable
@Parcelize
data class StateConfig(
    @SerializedName("id_estado")
    val id_estado:Int,
    @SerializedName("estado")
    val estado:String
): Parcelable
@Serializable
@Parcelize
data class Municipality(
    @SerializedName("id_municipio")
    val id_municipio:Int,
    @SerializedName("municipio")
    val municipio:String
): Parcelable
data class Preconfiguration(
    @SerializedName("activeGeoLocalization")
    val activeGeoLocalization:Boolean,
    @SerializedName("interceptorPhone")
    val interceptorPhone:List<String> = emptyList(),
    @SerializedName("offline")
    val offline:Boolean,
    @SerializedName("showState")
    val showState:Boolean,
    @SerializedName("urlAnalytics")
    val urlAnalytics:String,
    @SerializedName("tagAnalyticOpen")
    val tagAnalyticOpen:String,
    @SerializedName("urlSound")
    val urlSound:String,
    @SerializedName("splash_image")
    val splashImage:String,
    @SerializedName("splash_gift")
    val splashGift:String,
    @SerializedName("splash_lottie")
    val splashLottie:String,
    @SerializedName("welcomeVideo")
    val welcomeVideo:String,
)

data class RequestPhone(
    @SerializedName("active")
    val active:Boolean,
    @SerializedName("interceptorsPhone")
    val interceptorsPhone:List<String>,
)

data class State(
    @SerializedName("active")
    val active:Boolean,
)
data class Offline(
    @SerializedName("active")
    val active:Boolean,
    @SerializedName("date_update")
    val dataUpdate: DataUpdate
)

data class DataUpdate(
    @SerializedName("date")
    val date:String,
    @SerializedName("time")
    val time:String,
    @SerializedName("type")
    val type:String,
)

data class View(
    @SerializedName("id")
    val id: String,
    @SerializedName("mainView")
    val mainView: Boolean,
    @SerializedName("nameView")
    val nameView: String,
    @SerializedName("component")
    val component: List<Component>
)

data class Component(
    @SerializedName("type")
    val type: Int,
    @SerializedName("properties")
    val properties: ComponentProperties,
    @SerializedName("actions")
    val actions: Actions,
    @SerializedName("UUID")
    val uuid: String,

    )

data class ComponentProperties(
    @SerializedName("position")
    val position: Position,
    @SerializedName("size")
    val size: Size,
    @SerializedName("cornerRadius")
    val cornerRadius: Int?,
    @SerializedName("itemCarousel")
    val itemCarousel: List<ItemCarousel>,
    @SerializedName("base64Image")
    val base64Image: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("fontSize")
    val fontSize: Int? ,
    @SerializedName("textAlignment")
    val textAlignment: String?,
    @SerializedName("background")
    val background: String? ,
    @SerializedName("colorText")
    val colorText: String? ,
    @SerializedName("margin")
    val margin: Margin?,
    @SerializedName("iconName")
    val iconName: String?,
    @SerializedName("actionAnalytics")
    val actionAnalytics: String?,
    @SerializedName("idAnalytics")
    val idAnalytics: String?,
    @SerializedName("iconColor")
    val iconColor: String?,
    @SerializedName("imageSize")
    val imageSize: ImageSize?,
    @SerializedName("positionImage")
    val positionImage: String?,
    @SerializedName("videoURL")
    val videoURL: String?

)

data class ImageSize(
    @SerializedName("width")
    val width : Float,
    @SerializedName("height")
    val height: Float
)

data class ItemCarousel (
    val id: String,
    val title: String,
    val src: String
)
data class Actions(
    @SerializedName("call")
    val call: String = "",
    @SerializedName("openWebView")
    val openWebView: String = "",
    @SerializedName("openSections")
    val openSections: String = "",
    @SerializedName("showBySchedule")
    val showBySchedule: List<Schedule> = mutableListOf()
)
data class Schedule(
    @SerializedName("dayStart")
    val dayStart:String,
    @SerializedName("dayEnd")
    val dayEnd:String,
    @SerializedName("hourEnd")
    val hourEnd:String,
    @SerializedName("hourStart")
    val hourStart:String,
    val show:Boolean,
)

data class Margin(
    @SerializedName("top")
    val top: Int,
    @SerializedName("bottom")
    val bottom: Int,
    @SerializedName("left")
    val left: Int,
    @SerializedName("right")
    val right: Int
)

data class Size(
    @SerializedName("width")
    val width: Float,
    @SerializedName("height")
    val height: Float
)

data class Position(
    @SerializedName("x")
    val x: Float,
    @SerializedName("y")
    val y: Float
)
