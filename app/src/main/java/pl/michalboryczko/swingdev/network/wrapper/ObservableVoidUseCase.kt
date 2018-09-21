package pl.michalboryczko.swingdev.network.wrapper


import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import pl.michalboryczko.swingdev.executor.ThreadExecutor
import pl.michalboryczko.swingdev.executor.PostExecutionThread

/**
 * Simple wrapper for [ObservableUseCase] to omit input type parameter
 *
 * @param R the output value type
 */
abstract class ObservableVoidUseCase<R> protected constructor(
		threadExecutor: ThreadExecutor,
		postExecutionThread: PostExecutionThread) : ObservableUseCase<Unit, R>(threadExecutor, postExecutionThread) {

	fun execute(observer: DisposableObserver<R>) = super.execute(observer, Unit)

	override fun buildUseCaseObservable(item: Unit): Observable<R> = buildUseCaseObservable()

	protected abstract fun buildUseCaseObservable(): Observable<R>

	internal fun observable(): Observable<R> = buildUseCaseObservable()
}