package pl.michalboryczko.swingdev.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.michalboryczko.swingdev.di.DaggerNames
import pl.michalboryczko.swingdev.di.scopes.ActivityScope
import javax.inject.Named

@Module
class ActivityModule(private val activityContext: Context) {

	@Provides
	@Named(DaggerNames.ACTIVITY_CONTEXT)
	@ActivityScope
	internal fun provideActivityContext(): Context = activityContext

}