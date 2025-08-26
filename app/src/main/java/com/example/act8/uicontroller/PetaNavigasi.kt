package com.example.act8.uicontroller

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.act8.R
import com.example.act8.view.DetailSiswaScreen
import com.example.act8.view.EditSiswaScreen
import com.example.act8.view.EntrySiswaScreen
import com.example.act8.view.HomeScreen
import androidx.compose.foundation.layout.padding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(id = findTitleByRoute(currentRoute)),
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }, modifier = Modifier
    ) { innerPadding ->
        HostNavigasi(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Composable for the top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}

/**
 * Composable for handling navigation between screens.
 */
@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = {
                    navController.navigate("${DestinasiDetailSiswa.route}/$it")
                }
            )
        }
        composable(
            route = DestinasiDetailSiswa.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailSiswa.itemIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idSiswa = backStackEntry.arguments?.getInt(DestinasiDetailSiswa.itemIdArg)
            DetailSiswaScreen(
                navigateToEditItem = {
                    navController.navigate("${DestinasiEditSiswa.route}/$it")
                },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiEditSiswa.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditSiswa.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditSiswaScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

/**
 * Helper function to find the title resource based on the current navigation route.
 */
@Composable
private fun findTitleByRoute(route: String?): Int {
    return when (route) {
        DestinasiHome.route -> DestinasiHome.titleRes
        DestinasiEntry.route -> DestinasiEntry.titleRes
        DestinasiDetailSiswa.routeWithArgs -> DestinasiDetailSiswa.titleRes
        DestinasiEditSiswa.routeWithArgs -> DestinasiEditSiswa.titleRes
        else -> R.string.app_name // Default title
    }
}


object DestinasiHome : DestinasiNavigasi {
    override val route = "home_siswa"
    override val titleRes = R.string.app_name
}
object DestinasiEntry : DestinasiNavigasi {
    override val route = "entry_siswa"
    override val titleRes = R.string.entry_siswa
}
object DestinasiDetailSiswa : DestinasiNavigasi {
    override val route = "detail_siswa"
    override val titleRes = R.string.detail_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}
object DestinasiEditSiswa : DestinasiNavigasi {
    override val route = "edit_siswa"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}