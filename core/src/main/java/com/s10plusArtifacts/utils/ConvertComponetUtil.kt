package com.s10plusArtifacts.utils

fun ConvertComponent(value:List<Component>) : MutableList<ComponentConvert>{
    var listComponent:MutableList<ComponentConvert> = mutableListOf()
    value.forEach { component ->
        when(component.type){
            TypeComponent.IMAGE.value ->{
                listComponent.add(TransformImage(component.properties, component.actions))
            }
            TypeComponent.TEXT.value ->{
                listComponent.add(TransformTextFromJson(component.properties,component.actions))
            }
            TypeComponent.CARROUSEL.value -> {
                listComponent.add(TransformCarouselComponent(component.properties,component.actions))
            }
            TypeComponent.IMAGE_BUTTON.value -> {
                listComponent.add(TransformButtonImageComponent(component.properties,component.actions))
            }
            TypeComponent.BUTTON.value -> {
                listComponent.add(TransformButtonComponent(component.properties,component.actions))
            }
            TypeComponent.VIDEO.value -> {
                listComponent.add(TransformVideoComponent(component.properties,component.actions))
            }

        }
    }
    return listComponent
}


fun TransformTextFromJson(properties: ComponentProperties, actions: Actions? = null):  ComponentConvert {

    return  convertTextView(properties,actions)
}

fun TransformImage(properties: ComponentProperties, actions: Actions? = null):ComponentConvert {
    return convertImageView(properties,actions)
}

fun TransformCarouselComponent(properties: ComponentProperties,actions: Actions):  ComponentConvert {

    return convertCorouselView(properties,actions)
}


fun TransformButtonComponent(properties: ComponentProperties,actions: Actions? = null):ComponentConvert{
    return convertButtonView(properties,actions)
}

fun TransformVideoComponent(properties: ComponentProperties,actions: Actions? = null):ComponentConvert{
    return convertVideoView(properties,actions)
}

fun TransformButtonImageComponent(properties: ComponentProperties,actions: Actions? = null):ComponentConvert{
    return convertButtonImage(properties,actions)
}
