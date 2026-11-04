package com.example.pdm_roteiro10

import com.example.pdm_roteiro10.data.local.dao.ProjetoDao
import com.example.pdm_roteiro10.data.local.dao.TarefaDao
import com.example.pdm_roteiro10.data.local.entity.Projeto
import com.example.pdm_roteiro10.data.local.entity.Tarefa
import com.example.pdm_roteiro10.data.repository.ProjetoRepositoryImpl
import com.example.pdm_roteiro10.data.repository.TarefaRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FakeTarefaDao : TarefaDao {
    private val tarefas = mutableListOf<Tarefa>()

    override fun getAllTarefas(): Flow<List<Tarefa>> = flowOf(tarefas.toList())

    override suspend fun getTarefaById(id: Int): Tarefa? = tarefas.find { it.id == id }

    override suspend fun insertTarefa(tarefa: Tarefa): Long {
        val id = if (tarefa.id == 0) (tarefas.size + 1) else tarefa.id
        tarefas.add(tarefa.copy(id = id))
        return id.toLong()
    }

    override suspend fun updateTarefa(tarefa: Tarefa) {
        val index = tarefas.indexOfFirst { it.id == tarefa.id }
        if (index != -1) {
            tarefas[index] = tarefa
        }
    }

    override suspend fun deleteTarefa(tarefa: Tarefa) {
        tarefas.removeAll { it.id == tarefa.id }
    }
}

class FakeProjetoDao : ProjetoDao {
    private val projetos = mutableListOf<Projeto>()

    override fun getAllProjetos(): Flow<List<Projeto>> = flowOf(projetos.toList())

    override suspend fun getProjetoById(id: Int): Projeto? = projetos.find { it.id == id }

    override suspend fun insertProjeto(projeto: Projeto): Long {
        val id = if (projeto.id == 0) (projetos.size + 1) else projeto.id
        projetos.add(projeto.copy(id = id))
        return id.toLong()
    }

    override suspend fun updateProjeto(projeto: Projeto) {
        val index = projetos.indexOfFirst { it.id == projeto.id }
        if (index != -1) {
            projetos[index] = projeto
        }
    }

    override suspend fun deleteProjeto(projeto: Projeto) {
        projetos.removeAll { it.id == projeto.id }
    }
}

class RepositoryTest {

    @Test
    fun testTarefaRepository_insercaoEListagem() = runBlocking {
        val fakeDao = FakeTarefaDao()
        val repository = TarefaRepositoryImpl(fakeDao)

        val tarefa = Tarefa(titulo = "Estudar Hilt", status = "Em andamento")
        val id = repository.insertTarefa(tarefa)

        val item = repository.getTarefaById(id.toInt())
        assertNotNull(item)
        assertEquals("Estudar Hilt", item?.titulo)

        val lista = repository.getAllTarefas().first()
        assertEquals(1, lista.size)
    }

    @Test
    fun testProjetoRepository_insercaoEListagem() = runBlocking {
        val fakeDao = FakeProjetoDao()
        val repository = ProjetoRepositoryImpl(fakeDao)

        val projeto = Projeto(nome = "Projeto PDM", prazo = "20/11/2026")
        val id = repository.insertProjeto(projeto)

        val item = repository.getProjetoById(id.toInt())
        assertNotNull(item)
        assertEquals("Projeto PDM", item?.nome)

        val lista = repository.getAllProjetos().first()
        assertEquals(1, lista.size)
    }
}
