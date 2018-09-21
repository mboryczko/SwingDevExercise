package pl.michalboryczko.swingdev.base

import android.app.Activity
import pl.michalboryczko.swingdev.ui.main.impl.MainActivity
import javax.inject.Singleton


@Singleton
class Navigator {

	fun navigateToMainActivity(activity: Activity) = activity.apply { startActivity(MainActivity.prepareIntent(activity)) }

}
