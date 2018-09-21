package pl.michalboryczko.swingdev.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pl.michalboryczko.swingdev.AndroidApplication
import pl.michalboryczko.swingdev.UiThread
import pl.michalboryczko.swingdev.base.Navigator
import pl.michalboryczko.swingdev.di.DaggerNames
import pl.michalboryczko.swingdev.executor.JobExecutor
import pl.michalboryczko.swingdev.executor.PostExecutionThread
import pl.michalboryczko.swingdev.executor.ThreadExecutor
import pl.michalboryczko.swingdev.network.NetworkAvailabilityChecker
import pl.michalboryczko.swingdev.storage.ResourceProvider
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: AndroidApplication) {

	@Provides
	@Named(DaggerNames.APPLICATION_CONTEXT)
	@Singleton
	internal fun provideApplicationContext(): Context = application

	@Provides
	@Singleton
	internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread = uiThread

	@Provides
	@Singleton
	internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor


	@Provides
	@Singleton
	internal fun provideSharedPreferences(@Named(DaggerNames.APPLICATION_CONTEXT) context: Context): SharedPreferences =
			context.getSharedPreferences("swing_dev_pref", Context.MODE_PRIVATE)


	@Provides
	@Singleton
	internal fun provideResourceProvider(@Named(DaggerNames.APPLICATION_CONTEXT) context: Context): ResourceProvider = ResourceProvider(context)


	@Provides
	@Singleton
	internal fun provideNetworkAvailabilityChecker(@Named(DaggerNames.APPLICATION_CONTEXT) context: Context): NetworkAvailabilityChecker = NetworkAvailabilityChecker(context)


	@Provides
	@Singleton
	internal fun provideNavigator(): Navigator = Navigator()




}