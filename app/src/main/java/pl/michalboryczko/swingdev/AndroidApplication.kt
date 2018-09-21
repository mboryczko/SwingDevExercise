package pl.michalboryczko.swingdev

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.mapbox.mapboxsdk.Mapbox
import pl.michalboryczko.swingdev.di.components.ApplicationComponent
import pl.michalboryczko.swingdev.di.components.DaggerApplicationComponent
import pl.michalboryczko.swingdev.di.modules.ApplicationModule
import pl.michalboryczko.swingdev.di.modules.NetModule


class AndroidApplication : MultiDexApplication() {

	lateinit var applicationComponent: ApplicationComponent
		private set

	override fun onCreate() {
		super.onCreate()

		initializeInjector()
		initializeMap()
	}

	private fun initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(ApplicationModule(this))
				.netModule(NetModule(this))
				.build()
		applicationComponent.inject(this)
	}

	fun initializeMap(){
		Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
	}

	companion object {
		fun getInstance(activity: Activity) = activity.application as AndroidApplication
	}
}