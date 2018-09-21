package pl.michalboryczko.swingdev.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.michalboryczko.swingdev.di.DaggerNames
import pl.michalboryczko.swingdev.network.NetworkAvailabilityChecker
import pl.michalboryczko.swingdev.network.NetworkService
import pl.michalboryczko.swingdev.network.Repository
import pl.michalboryczko.swingdev.network.RepositoryImpl
import pl.michalboryczko.swingdev.storage.UserPreferences
import javax.inject.Named
import javax.inject.Singleton


@Module
class InteractorModule {

	@Provides
	@Singleton
	internal fun provideProductRepository(service: NetworkService,
										  @Named(DaggerNames.APPLICATION_CONTEXT) context: Context,
										  preferences: UserPreferences,
										  networkAvailabilityChecker: NetworkAvailabilityChecker):
			Repository = RepositoryImpl(service, context, networkAvailabilityChecker)

}