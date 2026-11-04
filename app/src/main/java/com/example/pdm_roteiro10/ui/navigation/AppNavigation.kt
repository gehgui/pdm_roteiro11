package com.example.pdm_roteiro10.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.pdm_roteiro10.ui.screens.projetos.FormProjetoScreen
import com.example.pdm_roteiro10.ui.screens.projetos.ListaProjetosScreen
import com.example.pdm_roteiro10.ui.screens.tarefas.FormTarefaScreen
import com.example.pdm_roteiro10.ui.screens.tarefas.ListaTarefasScreen
import com.example.pdm_roteiro10.ui.viewmodel.AppViewModel

@Composable
fun AppNavigation(
    viewModel: AppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == "listaTarefas" || currentRoute == "listaProjetos") {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "listaTarefas",
                        onClick = {
                            if (currentRoute != "listaTarefas") {
                                navController.navigate("listaTarefas") {
                                    popUpTo("listaTarefas") { inclusive = true }
                                }
                            }
                        },
                        label = { Text("Tarefas") },
                        icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Tarefas") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "listaProjetos",
                        onClick = {
                            if (currentRoute != "listaProjetos") {
                                navController.navigate("listaProjetos") {
                                    popUpTo("listaTarefas")
                                }
                            }
                        },
                        label = { Text("Projetos") },
                        icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Projetos") }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "listaTarefas",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("listaTarefas") {
                ListaTarefasScreen(viewModel) { id ->
                    navController.navigate(if (id == null) "formTarefa" else "formTarefa?id=$id")
                }
            }

            composable("listaProjetos") {
                ListaProjetosScreen(viewModel) { id ->
                    navController.navigate(if (id == null) "formProjeto" else "formProjeto?id=$id")
                }
            }

            composable(
                route = "formTarefa?id={id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                FormTarefaScreen(viewModel, id) {
                    navController.popBackStack()
                }
            }

            composable(
                route = "formTarefa"
            ) {
                FormTarefaScreen(viewModel, null) {
                    navController.popBackStack()
                }
            }

            composable(
                route = "formProjeto?id={id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                FormProjetoScreen(viewModel, id) {
                    navController.popBackStack()
                }
            }

            composable(
                route = "formProjeto"
            ) {
                FormProjetoScreen(viewModel, null) {
                    navController.popBackStack()
                }
            }
        }
    }
}
