package pl.michalboryczko.swingdev.di.components

import dagger.Component
import pl.michalboryczko.swingdev.di.modules.FragmentModule
import pl.michalboryczko.swingdev.di.scopes.FragmentScope


@FragmentScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

}