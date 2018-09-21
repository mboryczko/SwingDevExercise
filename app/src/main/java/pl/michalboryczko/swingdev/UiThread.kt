package pl.michalboryczko.swingdev


/**
 * MainThread (UI Thread) implementation based on a scheduler
 * which will execute actions on the Android UI thread
 */


import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.michalboryczko.swingdev.executor.PostExecutionThread

@Singleton
class UiThread @Inject constructor() : PostExecutionThread {

	override fun getScheduler(): Scheduler = AndroidSchedulers.mainThread()

}
