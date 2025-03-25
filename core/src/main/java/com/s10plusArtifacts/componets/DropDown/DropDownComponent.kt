package com.s10plusArtifacts.componets.DropDown
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownComponent(
    modifier: Modifier = Modifier,
    options: List<String>, // Lista de opciones
    label: String = "Selecciona una opción", // Etiqueta del menú
    onOptionSelected: (String) -> Unit // Callback para devolver la opción seleccionada
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") } // Opción seleccionada

    // Contenedor del Dropdown
    ExposedDropdownMenuBox(modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // Campo de texto con estilo OutlinedTextField
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true, // Deshabilitamos la edición
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor() // Necesario para el menú desplegable
                .fillMaxWidth()
        )

        // Menú desplegable
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option // Actualiza la selección local
                        expanded = false // Cierra el menú
                        onOptionSelected(option) // Devuelve la selección al callback
                    }
                )
            }
        }
    }
}

