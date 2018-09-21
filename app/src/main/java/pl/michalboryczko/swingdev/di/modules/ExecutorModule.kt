package pl.michalboryczko.swingdev.di.modules

import dagger.Module
import dagger.Provides
import pl.michalboryczko.swingdev.executor.PostExecutionThread
import pl.michalboryczko.swingdev.executor.ThreadExecutor
import pl.michalboryczko.swingdev.interactor.GetIssDataUseCase
import pl.michalboryczko.swingdev.network.Repository
import pl.michalboryczko.swingdev.storage.UserPreferences
import javax.inject.Singleton



@Module
class ExecutorModule {

    @Provides
    @Singleton
    internal fun provideGetIssDataUseCase(
            repository: Repository,
            userPreferences: UserPreferences,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ) = GetIssDataUseCase(repository, userPreferences, threadExecutor, postExecutionThread)

}