package com.s10plusArtifacts.componets.Carousel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.s10plusArtifacts.utils.ComponentProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun CarouselComponent(propertiesCarrouselViewComposable: ComponentProperties) {
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            if(propertiesCarrouselViewComposable.itemCarousel.isNotEmpty()) {
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                )
            }
        }
    }

    Column() {
        HorizontalPager(
            count = propertiesCarrouselViewComposable.itemCarousel.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .height(propertiesCarrouselViewComposable.size.height.dp)
                .width(propertiesCarrouselViewComposable.size.width.dp)

        ) { page ->
            Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.graphicsLayer {

                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }) {

                GlideImage(model = propertiesCarrouselViewComposable.itemCarousel[page].src , contentDescription = "", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            }
        }
        HorizontalPagerIndicator(pagerState = pagerState, modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),)
    }
}
