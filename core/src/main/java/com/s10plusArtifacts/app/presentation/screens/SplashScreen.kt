package com.s10plusArtifacts.app.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.s10plusArtifacts.app.presentation.navigation.BaseRoutes
import com.s10plusArtifacts.app.presentation.viewmodels.SplashViemodel
import com.s10plusArtifacts.componets.ComposableViewBase
import com.s10plusArtifacts.utils.ComponentConvert
import com.s10plusArtifacts.utils.ComponentProperties
import com.s10plusArtifacts.utils.Margin
import com.s10plusArtifacts.utils.Position
import com.s10plusArtifacts.utils.Size
import com.s10plusArtifacts.coreSdk.R
import timber.log.Timber

@Composable
fun SplashScreen(
    viemodel: SplashViemodel = hiltViewModel(),
    token: String,
    onChange: (screen: BaseRoutes) -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.load))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    val complete: Boolean by viemodel.complete.observeAsState(initial = false)
    val showInformation: Boolean? by viemodel.showInformationPersonal.observeAsState(initial = null)
    val personalInfo by viemodel.personalInformation.observeAsState()
    LaunchedEffect(token) {

        viemodel.setTokenValidate(token)
        if (viemodel.validateSession()) {
            viemodel.stepOne()

        }
    }

    LaunchedEffect(showInformation) {
        if (showInformation == true) {
            Timber.i("ChangeData User ${showInformation}")
            onChange.invoke(BaseRoutes.DataUser)
        }
        if (showInformation == false) {
            Timber.i("ChangeData MainScreen ${showInformation}")

            onChange.invoke(BaseRoutes.MainScreen)
        }
    }


    ComposableViewBase(
        viewModel = viemodel,
        arrayElements = mutableListOf(
            ComponentConvert(
                ComponentProperties(
                    position = Position(0f, 0f),
                    text = "",
                    fontSize = 0,
                    colorText = "",
                    size = Size(height = 100f, width = 100f),
                    cornerRadius = 0,
                    itemCarousel = listOf(),
                    textAlignment = "",
                    background = "",
                    base64Image = "",
                    margin = Margin(top = 0, bottom = 0, left = 0, right = 0),
                    iconColor = "",
                    iconName = "",
                    actionAnalytics = "",
                    idAnalytics = "",
                    positionImage = "",
                    imageSize = null,
                    videoURL = ""


                ), actions = null, component = {

                })
        )
    ) { a, b ->

    }


}