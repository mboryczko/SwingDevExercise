package pl.michalboryczko.swingdev.executor

import io.reactivex.Scheduler


interface PostExecutionThread {

	fun getScheduler(): Scheduler

}