package com.example.pdm_roteiro10.data.repository

import com.example.pdm_roteiro10.data.local.entity.Tarefa
import kotlinx.coroutines.flow.Flow

interface TarefaRepository {
    fun getAllTarefas(): Flow<List<Tarefa>>
    suspend fun getTarefaById(id: Int): Tarefa?
    suspend fun insertTarefa(tarefa: Tarefa): Long
    suspend fun updateTarefa(tarefa: Tarefa)
    suspend fun deleteTarefa(tarefa: Tarefa)
}
