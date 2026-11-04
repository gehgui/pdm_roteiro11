package com.example.pdm_roteiro10.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm_roteiro10.data.local.entity.Projeto
import com.example.pdm_roteiro10.data.local.entity.Tarefa
import com.example.pdm_roteiro10.data.repository.ProjetoRepository
import com.example.pdm_roteiro10.data.repository.TarefaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val tarefaRepository: TarefaRepository,
    private val projetoRepository: ProjetoRepository
) : ViewModel() {

    private val _isLoadingTarefas = MutableStateFlow(true)
    val isLoadingTarefas: StateFlow<Boolean> = _isLoadingTarefas.asStateFlow()

    private val _isLoadingProjetos = MutableStateFlow(true)
    val isLoadingProjetos: StateFlow<Boolean> = _isLoadingProjetos.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    val tarefas: StateFlow<List<Tarefa>> = tarefaRepository.getAllTarefas()
        .onEach {
            // Emite dados iniciais ou atualizações do Room
            delay(300) // Feedback visual de carregamento
            _isLoadingTarefas.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val projetos: StateFlow<List<Projeto>> = projetoRepository.getAllProjetos()
        .onEach {
            delay(300) // Feedback visual de carregamento
            _isLoadingProjetos.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun salvarTarefa(tarefa: Tarefa, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            _isSaving.value = true
            delay(400) // Feedback de carregamento ao salvar
            if (tarefa.id == 0) {
                tarefaRepository.insertTarefa(tarefa)
            } else {
                tarefaRepository.updateTarefa(tarefa)
            }
            _isSaving.value = false
            onComplete()
        }
    }

    suspend fun getTarefa(id: Int): Tarefa? {
        delay(250) // Feedback de carregamento ao buscar item
        return tarefaRepository.getTarefaById(id)
    }

    fun deletarTarefa(tarefa: Tarefa) {
        viewModelScope.launch {
            tarefaRepository.deleteTarefa(tarefa)
        }
    }

    fun salvarProjeto(projeto: Projeto, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            _isSaving.value = true
            delay(400) // Feedback de carregamento ao salvar
            if (projeto.id == 0) {
                projetoRepository.insertProjeto(projeto)
            } else {
                projetoRepository.updateProjeto(projeto)
            }
            _isSaving.value = false
            onComplete()
        }
    }

    suspend fun getProjeto(id: Int): Projeto? {
        delay(250) // Feedback de carregamento ao buscar item
        return projetoRepository.getProjetoById(id)
    }

    fun deletarProjeto(projeto: Projeto) {
        viewModelScope.launch {
            projetoRepository.deleteProjeto(projeto)
        }
    }
}
