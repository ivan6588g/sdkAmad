package com.s10plusArtifacts.componets


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditTextComposable() {

    val inputValue = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        // below line is used to get
        // value of text field,
        value = inputValue.value,

        // below line is used to get value in text field
        // on value change in text field.
        onValueChange = { inputValue.value = it },

        // modifier is used to add padding
        // to our text field.
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),

        // below line is used to specify TextField is enabled, allowing user interaction.
        enabled = true,

        // below line is used to specify TextField is not read-only, allowing text input.
        readOnly = false,

        // below line is used to specify
        // styling for our text field value.
        textStyle = androidx.compose.ui.text.TextStyle(
            color = Color.Black,

            // below line is used to add font
            // size for our text field
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,

            letterSpacing = 0.5.sp,
            textDecoration = TextDecoration.None,
            textAlign = TextAlign.Start
        ),
        //  A composable displaying a label for the TextField.
        label = { Text("Label") },

        // below line is used to add placeholder
        // for our text field.
        placeholder = { Text("Enter user name") },

        // leading icon is used to add icon
        // at the start of text field.
        leadingIcon = {
            // In this method we are specifying
            // our leading icon and its color.
            Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = Color(0xFF6200EE))
        },
        // trailing icons is used to add
        // icon to the end of text field.
        trailingIcon = {
            Icon(Icons.Filled.Info, contentDescription = null, tint = Color(0xFF6200EE))
        },
        // below line is used to indicate the TextField is not in an error state.
        isError = false,

        // below line is used to indicate no visual transformation is applied to the text.
        visualTransformation = VisualTransformation.None,

        // keyboard options is used to modify
        // the keyboard for text field.
        keyboardOptions = KeyboardOptions(
            // below line is used for capitalization
            // inside our text field.
            capitalization = KeyboardCapitalization.None,

            // below line is used to enable auto
            // correct in our keyboard.
            autoCorrectEnabled = true,

            // below line is used to specify our
            // type of keyboard such as text, number, phone.
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ), keyboardActions = KeyboardActions(onDone = {
            // Handle the done action
        }),
        // single line boolean is used to avoid
        // text field entering in multiple lines.
        singleLine = true,

        // below line is used to give
        // max lines for our text field.
        maxLines = 2,

        // below line is used to give
        // max lines for our text field.
        minLines = 1,

        //  below line uses a MutableInteractionSource for handling interaction states.
        interactionSource = remember { MutableInteractionSource() },

        // below line is used for the shape of the TextField is set to the medium shape from the theme.
        shape = MaterialTheme.shapes.medium,

        // below line is used to specify background
        // color for our text field.
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Green,
            disabledTextColor = Color.Green,
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            cursorColor = Color.Blue,
            errorCursorColor = Color.Red,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

