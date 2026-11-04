package com.example.pdm_roteiro10.data.repository

import com.example.pdm_roteiro10.data.local.dao.TarefaDao
import com.example.pdm_roteiro10.data.local.entity.Tarefa
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TarefaRepositoryImpl @Inject constructor(
    private val tarefaDao: TarefaDao
) : TarefaRepository {

    override fun getAllTarefas(): Flow<List<Tarefa>> = tarefaDao.getAllTarefas()

    override suspend fun getTarefaById(id: Int): Tarefa? = tarefaDao.getTarefaById(id)

    override suspend fun insertTarefa(tarefa: Tarefa): Long = tarefaDao.insertTarefa(tarefa)

    override suspend fun updateTarefa(tarefa: Tarefa) = tarefaDao.updateTarefa(tarefa)

    override suspend fun deleteTarefa(tarefa: Tarefa) = tarefaDao.deleteTarefa(tarefa)
}
