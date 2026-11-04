package com.example.pdm_roteiro10.data.repository

import com.example.pdm_roteiro10.data.local.entity.Projeto
import kotlinx.coroutines.flow.Flow

interface ProjetoRepository {
    fun getAllProjetos(): Flow<List<Projeto>>
    suspend fun getProjetoById(id: Int): Projeto?
    suspend fun insertProjeto(projeto: Projeto): Long
    suspend fun updateProjeto(projeto: Projeto)
    suspend fun deleteProjeto(projeto: Projeto)
}
