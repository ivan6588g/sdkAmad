package com.s10plusArtifacts.componets.bases

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.s10plusArtifacts.coreSdk.R

@Composable
fun DialogBase(
    content: @Composable() () -> Unit,
    onDismissRequest: () -> Unit,
    shouldShowDialog: Boolean,
    modifier: Modifier = Modifier,
    showCloseIcon:Boolean = false,
    title: String,
    subTitle: String,
    icon: Int? = null
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            icon ?: R.raw.success_lottie
        )
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    if (shouldShowDialog) {
        Dialog(onDismissRequest = {
            onDismissRequest()
        }) {
            Card(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {
                    if(showCloseIcon) {
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.End) {
                            Icon(
                                modifier = Modifier.clickable {
                                    onDismissRequest.invoke()
                                },
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "close dialog"
                            )
                        }
                    }
                    if (icon != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.18f)
                                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LottieAnimation(composition = composition, progress = { progress })
                        }
                    }
                    if(title.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if(subTitle.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = subTitle, textAlign = TextAlign.Center, fontSize = 15.sp)
                        }
                    }
                }
                content.invoke()

            }

        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewDialog() {
    DialogBase(
        content = {
            Button(onClick = { /*TODO*/ }) {

            }
        },
        onDismissRequest = { /*TODO*/ },
        shouldShowDialog = true,
        modifier = Modifier,
        title = "Holaaaa",
        subTitle = "Holaaa"
    )
}