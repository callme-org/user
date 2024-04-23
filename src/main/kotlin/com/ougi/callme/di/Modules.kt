package com.ougi.callme.di

import com.ougi.callme.data.repository.DatabaseRepositoryImpl
import com.ougi.callme.data.repository.UserRepositoryImpl
import com.ougi.callme.domain.repository.DatabaseRepository
import com.ougi.callme.domain.repository.UserRepository
import com.ougi.callme.domain.usecase.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val repositoryModule = module {
    singleOf(::DatabaseRepositoryImpl) { bind<DatabaseRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}

private val useCaseModule = module {
    singleOf(::DatabaseInitializerImpl) {
        bind<DatabaseInitializer>()
    }
    singleOf(::CreateUserUseCaseImpl) { bind<CreateUserUseCase>() }
    singleOf(::GetUserUseCaseImpl) { bind<GetUserUseCase>() }
    singleOf(::UpdateUserUseCaseImpl) { bind<UpdateUserUseCase>() }
}

val appModule = module {
    includes(
        repositoryModule,
        useCaseModule,
    )
}