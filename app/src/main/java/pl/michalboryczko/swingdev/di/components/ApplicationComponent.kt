package pl.michalboryczko.swingdev.di.components

import dagger.Component
import pl.michalboryczko.swingdev.AndroidApplication
import pl.michalboryczko.swingdev.base.BaseActivity
import pl.michalboryczko.swingdev.base.BaseFragment
import pl.michalboryczko.swingdev.base.Navigator
import pl.michalboryczko.swingdev.di.modules.ApplicationModule
import pl.michalboryczko.swingdev.di.modules.ExecutorModule
import pl.michalboryczko.swingdev.di.modules.InteractorModule
import pl.michalboryczko.swingdev.di.modules.NetModule
import pl.michalboryczko.swingdev.executor.PostExecutionThread
import pl.michalboryczko.swingdev.executor.ThreadExecutor
import pl.michalboryczko.swingdev.interactor.GetIssDataUseCase
import pl.michalboryczko.swingdev.network.NetworkAvailabilityChecker
import pl.michalboryczko.swingdev.network.Repository
import pl.michalboryczko.swingdev.storage.ResourceProvider
import pl.michalboryczko.swingdev.storage.UserPreferences
import javax.inject.Singleton

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(
		modules = arrayOf(ApplicationModule::class, InteractorModule::class, ExecutorModule::class, NetModule::class))
interface ApplicationComponent {


	fun inject(baseActivity: BaseActivity)
	fun inject(baseFragment: BaseFragment)
	fun inject(androidApplication: AndroidApplication)


	fun provideGetIssPositionUseCase(): GetIssDataUseCase


	fun navigator(): Navigator
	fun repository(): Repository
	fun threadExecutor(): ThreadExecutor
	fun postThreadExecutor(): PostExecutionThread
	fun sharedPreferences(): UserPreferences
	fun resourceProvider(): ResourceProvider
	fun networkAvailabilityChecker(): NetworkAvailabilityChecker

}