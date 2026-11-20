package com.sena.dummy_json.ui.composable

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.sena.dummy_json.domain.model.ProductModel

@Composable
fun ConfirmDeleteDialog(
    product: ProductModel,
    onConfirm: (ProductModel) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar producto") },
        text = { Text("¿Estás seguro de que deseas eliminar \"${product.title}\"? Esta acción no se puede deshacer.") },
        confirmButton = {
            Button(
                onClick = { onConfirm(product) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
