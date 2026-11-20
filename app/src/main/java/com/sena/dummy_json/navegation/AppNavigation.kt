package com.sena.dummy_json.navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sena.dummy_json.ui.screen.ProductDetailScreen
import com.sena.dummy_json.ui.screen.ProductFormScreen
import com.sena.dummy_json.ui.screen.ProductListScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ProductList,
        modifier = modifier
    ) {
        composable<Routes.ProductList> {
            ProductListScreen(
                onProductClick = { productId ->
                    navController.navigate(Routes.ProductDetail(productId))
                },
                onAddProductClick = {
                    navController.navigate(Routes.ProductForm(null))
                },
                onEditProductClick = { productId ->
                    navController.navigate(Routes.ProductForm(productId))
                }
            )
        }

        composable<Routes.ProductDetail> { backStackEntry ->
            val route: Routes.ProductDetail = backStackEntry.toRoute()
            ProductDetailScreen(
                productId = route.productId,
                onBack = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate(Routes.ProductForm(id))
                }
            )
        }

        composable<Routes.ProductForm> { backStackEntry ->
            val route: Routes.ProductForm = backStackEntry.toRoute()
            ProductFormScreen(
                productId = route.productId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
