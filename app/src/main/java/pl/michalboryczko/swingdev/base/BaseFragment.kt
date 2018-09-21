package pl.michalboryczko.swingdev.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import pl.michalboryczko.swingdev.AndroidApplication
import pl.michalboryczko.swingdev.di.components.ApplicationComponent
import pl.michalboryczko.swingdev.di.components.DaggerFragmentComponent
import pl.michalboryczko.swingdev.di.components.FragmentComponent
import pl.michalboryczko.swingdev.di.modules.FragmentModule
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

	@Inject
	lateinit var navigator: Navigator

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		applicationComponent.inject(this)
	}


	/**
	 * Get the Main Application component for dependency injection.
	 * @return [ApplicationComponent]
	 */
	val applicationComponent: ApplicationComponent
		get() = AndroidApplication.getInstance(activity!!).applicationComponent

	val fragmentComponent: FragmentComponent
		get() = DaggerFragmentComponent.builder().applicationComponent(applicationComponent)
				.fragmentModule(FragmentModule(activity!!))
				.build()

	fun hideKeyboard(view: View) = (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
			view.windowToken, 0)

	fun showMessage(msg: String){
		if(context != null)
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
	}


}