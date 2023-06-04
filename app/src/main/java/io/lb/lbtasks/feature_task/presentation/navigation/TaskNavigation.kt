package io.lb.lbtasks.feature_task.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.lb.lbtasks.feature_task.presentation.screens.TasksScreen
import io.lb.lbtasks.feature_task.presentation.screens.TaskDetailsScreen

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TaskScreens.TaskScreen.name
    ) {
        composable(TaskScreens.TaskScreen.name){
            TasksScreen(navController = navController)
        }

        composable(
            route = TaskScreens.TaskDetailsScreen.name + "/{task}",
            arguments = listOf(
                navArgument(name = "task") {
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("task")?.let {
                TaskDetailsScreen(
                    navController = navController,
                    it
                )
            }
        }
    }
}
