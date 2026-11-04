package com.example.pdm_roteiro10.ui.screens.tarefas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pdm_roteiro10.data.local.entity.Tarefa
import com.example.pdm_roteiro10.ui.components.LoadingIndicatorScreen
import com.example.pdm_roteiro10.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTarefaScreen(
    viewModel: AppViewModel,
    idTarefa: Int?,
    onNavigateBack: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var isLoadingItem by remember { mutableStateOf(idTarefa != null) }
    val isSaving by viewModel.isSaving.collectAsState()

    LaunchedEffect(idTarefa) {
        if (idTarefa != null) {
            isLoadingItem = true
            val tarefa = viewModel.getTarefa(idTarefa)
            if (tarefa != null) {
                titulo = tarefa.titulo
                status = tarefa.status
            }
            isLoadingItem = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (idTarefa == null) "Nova Tarefa" else "Editar Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, enabled = !isSaving) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (isLoadingItem) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicatorScreen("Carregando dados da tarefa...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título da Tarefa") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text("Status (ex: Em andamento, Concluído)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        enabled = !isSaving
                    ) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            if (titulo.isNotBlank()) {
                                val novaTarefa = Tarefa(
                                    id = idTarefa ?: 0,
                                    titulo = titulo.trim(),
                                    status = status.trim().ifBlank { "Pendente" }
                                )
                                viewModel.salvarTarefa(novaTarefa) {
                                    onNavigateBack()
                                }
                            }
                        },
                        enabled = !isSaving && titulo.isNotBlank()
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Salvando...")
                        } else {
                            Text("Salvar")
                        }
                    }
                }
            }
        }
    }
}
