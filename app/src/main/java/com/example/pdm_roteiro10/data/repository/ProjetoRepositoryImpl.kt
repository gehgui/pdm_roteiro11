package com.example.pdm_roteiro10.data.repository

import com.example.pdm_roteiro10.data.local.dao.ProjetoDao
import com.example.pdm_roteiro10.data.local.entity.Projeto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjetoRepositoryImpl @Inject constructor(
    private val projetoDao: ProjetoDao
) : ProjetoRepository {

    override fun getAllProjetos(): Flow<List<Projeto>> = projetoDao.getAllProjetos()

    override suspend fun getProjetoById(id: Int): Projeto? = projetoDao.getProjetoById(id)

    override suspend fun insertProjeto(projeto: Projeto): Long = projetoDao.insertProjeto(projeto)

    override suspend fun updateProjeto(projeto: Projeto) = projetoDao.updateProjeto(projeto)

    override suspend fun deleteProjeto(projeto: Projeto) = projetoDao.deleteProjeto(projeto)
}
