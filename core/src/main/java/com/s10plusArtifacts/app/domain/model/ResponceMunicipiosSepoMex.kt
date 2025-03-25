package com.s10plusArtifacts.app.domain.model

import com.google.gson.annotations.SerializedName

data class ResponceMunicipiosSepoMex(
    @SerializedName("id_municipio")
    var id_municipio:Int,
    @SerializedName("municipio")
    var municipio:String,
)