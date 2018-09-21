package pl.michalboryczko.swingdev.executor

/**
 * Created on 16.08.2018.
 */
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Decorated [ThreadPoolExecutor]
 */
@Singleton
class JobExecutor @Inject constructor() : ThreadExecutor {

	private val workQueue: BlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>()
	private val threadFactory: ThreadFactory = JobThreadFactory()
	private val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
			KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, workQueue, threadFactory)

	override fun execute(runnable: Runnable) = threadPoolExecutor.execute(runnable)

	companion object {

		private val INITIAL_POOL_SIZE = 2
		private val MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2
		// Sets the amount of time an idle thread waits before terminating
		private val KEEP_ALIVE_TIME = 10
		// Sets the Time Unit to seconds
		private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
	}

	private class JobThreadFactory : ThreadFactory {

		private val counter: AtomicInteger = AtomicInteger(0)

		override fun newThread(runnable: Runnable): Thread = Thread(runnable, THREAD_NAME + counter.get())

		companion object {

			private val THREAD_NAME = "android_"

		}
	}
}