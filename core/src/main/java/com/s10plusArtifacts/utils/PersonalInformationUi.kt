package com.s10plusArtifacts.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PersonalInformationUi (
    val title:String,
    val locationInformation:LocationInformationUi,
    val urlImage:String,
    val showTypesData:List<String>,

)

@Parcelize
data class LocationInformationUi(
    val neighborhoods: NeighborhoodsUi,
    val state: StateConfigUi,
    val municipality: MunicipalityUi,
): Parcelable

@Parcelize
data class NeighborhoodsUi(
    val id_colonia:Int,
    val colonia:String
): Parcelable

@Parcelize
data class StateConfigUi(
    val id_estado:Int,
    val estado:String
): Parcelable

@Parcelize
data class MunicipalityUi(
    val id_municipio:Int,
    val municipio:String
): Parcelable
