package com.example.pdm_roteiro10.di

import com.example.pdm_roteiro10.data.repository.ProjetoRepository
import com.example.pdm_roteiro10.data.repository.ProjetoRepositoryImpl
import com.example.pdm_roteiro10.data.repository.TarefaRepository
import com.example.pdm_roteiro10.data.repository.TarefaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTarefaRepository(
        tarefaRepositoryImpl: TarefaRepositoryImpl
    ): TarefaRepository

    @Binds
    @Singleton
    abstract fun bindProjetoRepository(
        projetoRepositoryImpl: ProjetoRepositoryImpl
    ): ProjetoRepository
}
