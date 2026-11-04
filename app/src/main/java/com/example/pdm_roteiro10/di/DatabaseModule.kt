package com.example.pdm_roteiro10.di

import android.content.Context
import androidx.room.Room
import com.example.pdm_roteiro10.data.local.dao.ProjetoDao
import com.example.pdm_roteiro10.data.local.dao.TarefaDao
import com.example.pdm_roteiro10.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pdm_roteiro10_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTarefaDao(database: AppDatabase): TarefaDao {
        return database.tarefaDao()
    }

    @Provides
    fun provideProjetoDao(database: AppDatabase): ProjetoDao {
        return database.projetoDao()
    }
}
