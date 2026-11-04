package com.example.pdm_roteiro10.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pdm_roteiro10.data.local.dao.ProjetoDao
import com.example.pdm_roteiro10.data.local.dao.TarefaDao
import com.example.pdm_roteiro10.data.local.entity.Projeto
import com.example.pdm_roteiro10.data.local.entity.Tarefa

@Database(entities = [Tarefa::class, Projeto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao
    abstract fun projetoDao(): ProjetoDao
}
