package com.s10plusArtifacts.componets.Dialogs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun SnackBarDemo() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize().zIndex(3f), contentAlignment = Alignment.BottomStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 52.dp)
                .background(Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "¿Es correcto tu número telefonico?",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            text = "SI",
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .clickable {
                            Log.i("", "")
                        }, contentAlignment = Alignment.Center) {
                        Text(
                            text = "NO",
                            fontSize = 16.sp,
                            color = Color.White, textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

            }
        }
    }
    /*  Scaffold(snackbarHost = {
          SnackbarHost(hostState = snackbarHostState)
      }, modifier = Modifier.padding(bottom = 42.dp)) {
          scope.launch {
              val result = snackbarHostState.showSnackbar(
                  message = "ldskjffsdj",
                  actionLabel = "ñdslfjkñl",
                  duration = SnackbarDuration.Indefinite
              )
              when (result) {
                  SnackbarResult.ActionPerformed -> {
                      *//* Handle snackbar action performed *//*
                }
                SnackbarResult.Dismissed -> {
                    *//* Handle snackbar dismissed *//*
                }
            }
        }
    }*/

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SnackPreview() {
    SnackBarDemo()
}