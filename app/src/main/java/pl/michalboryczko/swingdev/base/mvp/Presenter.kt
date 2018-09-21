package pl.michalboryczko.swingdev.base.mvp

abstract class Presenter<T : BaseView> {

	lateinit protected var view: T

	fun attachView(view: T) {
		this.view = view
	}

	/**
	 * Method that control the lifecycle of the mView. It should be called in the mView's
	 * (Activity or Fragment) onResume() method.
	 */
	abstract fun resume()

	/**
	 * Method that control the lifecycle of the mView. It should be called in the mView's
	 * (Activity or Fragment) onPause() method.
	 */
	abstract fun pause()

	/**
	 * Method that control the lifecycle of the mView. It should be called in the mView's
	 * (Activity or Fragment) onDestroy() method.
	 */
	abstract fun destroy()


}
