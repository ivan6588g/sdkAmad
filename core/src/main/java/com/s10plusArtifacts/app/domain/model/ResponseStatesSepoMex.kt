package com.s10plusArtifacts.app.domain.model

import com.google.gson.annotations.SerializedName

data class ResponseStatesSepoMex(
    @SerializedName("id_estado")
    var id_estado:Int,
    @SerializedName("estado")
    var estado:String,

)
