package pl.michalboryczko.swingdev.network.wrapper


import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pl.michalboryczko.swingdev.executor.ThreadExecutor
import pl.michalboryczko.swingdev.executor.PostExecutionThread

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a [DisposableSingleObserver]
 * that will execute its job in a background thread and will post the result in the UI thread.
 *
 * Class is parametrized with two types, where:
 * @param T the input value type
 * @param R the output value type
 */

abstract class SingleUseCase<in T, R> protected constructor(
		private val threadExecutor: ThreadExecutor,
		private val postExecutionThread: PostExecutionThread) {

	private val disposables: CompositeDisposable = CompositeDisposable()

	/**
	 * Builds an [Single] which will be used when executing the current [SingleUseCase].
	 */
	protected abstract fun buildUseCaseSingle(item: T): Single<R>

	/**
	 * Returns built [Single]. This method is for composing and reusing existed UseCases
	 */
	internal fun single(params: T): Single<R> = buildUseCaseSingle(params)

	/**
	 * Executes the current use case.

	 * @param observer [DisposableSingleObserver] which will be listening to the observable build
	 * * by [.buildUseCaseSingle] ()} method.
	 * @param params Parameters (Optional) used to build/execute this use case.
	 */
	open fun execute(observer: DisposableSingleObserver<R>, params: T) {
		val single = this.buildUseCaseSingle(params)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler())
		addDisposable(single.subscribeWith(observer))
	}

	/**
	 * Dispose from current [CompositeDisposable].
	 */
	open fun dispose() {
		if (!disposables.isDisposed) {
			disposables.dispose()
		}
	}

	open fun checkStatus() =
			"UseCase is disposed: ${disposables.isDisposed}, size: ${disposables.size()}"


	/**
	 * Clears all disposables
	 */
	open fun clear() {
		if (!disposables.isDisposed) {
			disposables.clear()
		}
	}

	/**
	 * Dispose from current [CompositeDisposable].
	 */
	private fun addDisposable(disposable: Disposable) {
		disposables.add(disposable)
	}
}