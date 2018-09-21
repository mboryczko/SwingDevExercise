package pl.michalboryczko.swingdev.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.michalboryczko.swingdev.di.DaggerNames
import pl.michalboryczko.swingdev.di.scopes.FragmentScope
import javax.inject.Named



@Module
class FragmentModule(private val fragmentContext: Context) {

	@Provides
	@FragmentScope
	@Named(DaggerNames.FRAGMENT_CONTEXT)
	internal fun provideFragmentContext(): Context = fragmentContext

}