package pl.michalboryczko.swingdev.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import pl.michalboryczko.swingdev.AndroidApplication
import pl.michalboryczko.swingdev.di.components.ActivityComponent
import pl.michalboryczko.swingdev.di.components.ApplicationComponent
import pl.michalboryczko.swingdev.di.components.DaggerActivityComponent
import pl.michalboryczko.swingdev.di.modules.ActivityModule
import pl.michalboryczko.swingdev.network.Repository
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

	@Inject
	lateinit var navigator: Navigator

	@Inject
	lateinit var repository: Repository

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		applicationComponent.inject(this)

	}

	val applicationComponent: ApplicationComponent
		get() = (application as AndroidApplication).applicationComponent

	val activityComponent: ActivityComponent
		get() = DaggerActivityComponent.builder().applicationComponent(applicationComponent)
				.activityModule(ActivityModule(this))
				.build()


	fun showMessage(msg: String){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
	}

	override fun onSupportNavigateUp(): Boolean {
		onBackPressed()
		return true
	}
}