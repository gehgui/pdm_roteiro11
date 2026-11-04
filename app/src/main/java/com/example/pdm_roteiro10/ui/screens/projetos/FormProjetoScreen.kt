package com.example.pdm_roteiro10.ui.screens.projetos

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pdm_roteiro10.data.local.entity.Projeto
import com.example.pdm_roteiro10.ui.components.LoadingIndicatorScreen
import com.example.pdm_roteiro10.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormProjetoScreen(
    viewModel: AppViewModel,
    idProjeto: Int?,
    onNavigateBack: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var prazo by remember { mutableStateOf("") }
    var isLoadingItem by remember { mutableStateOf(idProjeto != null) }
    val isSaving by viewModel.isSaving.collectAsState()

    LaunchedEffect(idProjeto) {
        if (idProjeto != null) {
            isLoadingItem = true
            val projeto = viewModel.getProjeto(idProjeto)
            if (projeto != null) {
                nome = projeto.nome
                prazo = projeto.prazo
            }
            isLoadingItem = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (idProjeto == null) "Novo Projeto" else "Editar Projeto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, enabled = !isSaving) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
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
                LoadingIndicatorScreen("Carregando dados do projeto...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Projeto") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = prazo,
                    onValueChange = { prazo = it },
                    label = { Text("Prazo (ex: 15/11/2026)") },
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
                            if (nome.isNotBlank()) {
                                val novoProjeto = Projeto(
                                    id = idProjeto ?: 0,
                                    nome = nome.trim(),
                                    prazo = prazo.trim().ifBlank { "Sem prazo" }
                                )
                                viewModel.salvarProjeto(novoProjeto) {
                                    onNavigateBack()
                                }
                            }
                        },
                        enabled = !isSaving && nome.isNotBlank()
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onSecondary
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
