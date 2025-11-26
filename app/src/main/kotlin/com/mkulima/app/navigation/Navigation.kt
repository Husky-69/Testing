package com.mkulima.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mkulima.app.ui.screens.OnboardingScreen
import com.mkulima.app.ui.screens.HomeScreen
import com.mkulima.app.ui.screens.DiagnosisScreen
import com.mkulima.app.ui.screens.CropGuidesScreen
import com.mkulima.app.ui.screens.MyFarmScreen

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Home : Screen("home")
    data object Diagnosis : Screen("diagnosis")
    data object CropGuides : Screen("crop_guides")
    data object MyFarm : Screen("my_farm")
}

@Composable
fun MKulimaNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onGetStarted = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onDiagnosisClick = { navController.navigate(Screen.Diagnosis.route) },
                onCropGuidesClick = { navController.navigate(Screen.CropGuides.route) },
                onMyFarmClick = { navController.navigate(Screen.MyFarm.route) }
            )
        }
        composable(Screen.Diagnosis.route) {
            DiagnosisScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.CropGuides.route) {
            CropGuidesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.MyFarm.route) {
            MyFarmScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
