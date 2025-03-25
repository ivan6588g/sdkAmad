package com.s10plusArtifacts.componets.Dialogs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s10plusArtifacts.componets.bases.DialogBase

@Composable
fun DialogStates(
    shouldShowDialog: Boolean,
    onClickSelected: (Pair<String,String>) -> Unit
){
     var state:HashMap<String, String> = hashMapOf(
        Pair("AGUASCALIENTES", "AGS."),
        Pair("BAJA CALIFORNIA", "BC."),
        Pair("BAJA CALIFORNIA SUR", "BCS."),
        Pair("CAMPECHE", "CAMP."),
        Pair("COAHUILA", "COAH."),
        Pair("COLIMA", "COL."),
        Pair("CHIAPAS", "CHIS."),
        Pair("CHIHUAHUA", "CHIH."),
        Pair("CIUDAD DE MEXICO", "CDMX."),
        Pair("DURANGO", "DGO."),
        Pair("GUANAJUATO", "GTO."),
        Pair("GUERRERO", "GRO."),
        Pair("HIDALGO", "HGO."),
        Pair("JALISCO", "JAL."),
        Pair("MEXICO", "EDO-MÉX."),
        Pair("MICHOACAN", "MICH."),
        Pair("MORELOS", "MOR."),
        Pair("NAYARIT", "NAY."),
        Pair("NUEVO LEON", "NL."),
        Pair("OAXACA", "OAX."),
        Pair("PUEBLA", "PUE."),
        Pair("QUERETARO", "QRO."),
        Pair("QUINTANA ROO", "QROO."),
        Pair("SAN LUIS POTOSI", "SLP."),
        Pair("SINALOA", "SIN."),
        Pair("SONORA", "SON."),
        Pair("TABASCO", "TAB."),
        Pair("TAMAULIPAS", "TAMPS."),
        Pair("TLAXCALA", "TLAX."),
        Pair("VERACRUZ", "VER."),
        Pair("YUCATAN", "YUC."),
        Pair("ZACATECAS", "ZAC."),

        )

    DialogBase(
        content = {
            Column(modifier = Modifier.fillMaxHeight(0.5f)) {
                Row {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black), contentAlignment = Alignment.Center){
                        Text(modifier = Modifier.padding(vertical = 16.dp), text = "¿De donde nos visita?", fontSize = 24.sp, color = Color.White)
                    }
                }
                LazyVerticalGrid(modifier = Modifier.background(color = Color.White), columns = GridCells.Fixed(1),contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),) {
                   itemsIndexed(state.toSortedMap().map { it.key }.toTypedArray()){
                       index,item ->
                       ItemState(item,onClickItem ={
                          name ->
                      val key: String = state.toSortedMap().keys.toTypedArray()[index]
                      val value: String = state.toSortedMap()[key]!!
                      Log.i("d","")
                           onClickSelected.invoke(Pair(key,value))
                  })
                   }

                }
            }
        },
        onDismissRequest = {  },
        shouldShowDialog = shouldShowDialog ,
        title = "",
        subTitle = ""
    )
}

@Composable
fun ItemState(nameState:String,onClickItem: (String) -> Unit){
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).background(Color.White).clickable { onClickItem.invoke(nameState) }, verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart){
            Text(text = nameState, fontSize = 16.sp, color = Color.Black)
        }
    }
    HorizontalDivider(modifier = Modifier, thickness = 2.dp)


}