package pl.michalboryczko.swingdev.di.components

import dagger.Component
import pl.michalboryczko.swingdev.ui.main.impl.MainActivity
import pl.michalboryczko.swingdev.di.modules.ActivityModule
import pl.michalboryczko.swingdev.di.scopes.ActivityScope

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

	fun inject(mainActivity: MainActivity)


}