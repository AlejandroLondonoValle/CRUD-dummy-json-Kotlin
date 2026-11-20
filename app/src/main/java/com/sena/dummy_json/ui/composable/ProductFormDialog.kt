package com.sena.dummy_json.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sena.dummy_json.domain.model.ProductModel

@Composable
fun ProductFormDialog(
    productToEdit: ProductModel? = null,
    isSaving: Boolean = false,
    onDismiss: () -> Unit,
    onSave: (ProductModel) -> Unit
) {
    val isEditing = productToEdit != null

    var title by remember { mutableStateOf(productToEdit?.title.orEmpty()) }
    var description by remember { mutableStateOf(productToEdit?.description.orEmpty()) }
    var category by remember { mutableStateOf(productToEdit?.category.orEmpty()) }
    var brand by remember { mutableStateOf(productToEdit?.brand.orEmpty()) }
    var priceText by remember { mutableStateOf(productToEdit?.price?.let { if (it > 0) it.toString() else "" }.orEmpty()) }
    var stockText by remember { mutableStateOf(productToEdit?.stock?.let { if (it > 0) it.toString() else "" }.orEmpty()) }
    var thumbnail by remember { mutableStateOf(productToEdit?.thumbnail.orEmpty()) }

    var titleError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = if (isEditing) "Editar Producto" else "Crear Producto",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = false
                    },
                    label = { Text("Título *") },
                    isError = titleError,
                    supportingText = { if (titleError) Text("El título es obligatorio") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Categoría") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = brand,
                        onValueChange = { brand = it },
                        label = { Text("Marca") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = priceText,
                        onValueChange = {
                            priceText = it
                            priceError = false
                        },
                        label = { Text("Precio *") },
                        isError = priceError,
                        supportingText = { if (priceError) Text("Precio válido requerido") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = stockText,
                        onValueChange = { stockText = it },
                        label = { Text("Stock") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = thumbnail,
                    onValueChange = { thumbnail = it },
                    label = { Text("URL Imagen (thumbnail)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss, enabled = !isSaving) {
                        Text("Cancelar")
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val priceVal = priceText.toDoubleOrNull()
                            if (title.isBlank()) {
                                titleError = true
                                return@Button
                            }
                            if (priceVal == null || priceVal <= 0) {
                                priceError = true
                                return@Button
                            }

                            val productToSave = (productToEdit ?: ProductModel()).copy(
                                title = title.trim(),
                                description = description.trim(),
                                category = category.trim(),
                                brand = brand.trim().ifBlank { null },
                                price = priceVal,
                                stock = stockText.toIntOrNull() ?: 0,
                                thumbnail = thumbnail.trim()
                            )
                            onSave(productToSave)
                        },
                        enabled = !isSaving
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(if (isEditing) "Guardar" else "Crear")
                        }
                    }
                }
            }
        }
    }
}
