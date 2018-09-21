package pl.michalboryczko.swingdev.network

import javax.inject.Inject
import android.content.Context
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import pl.michalboryczko.swingdev.model.responses.IssPositionResponse
import pl.michalboryczko.swingdev.model.responses.PeopleResponse
import pl.michalboryczko.swingdev.network.exception.NetworkException
import retrofit2.Response




class RepositoryImpl @Inject constructor(val service: NetworkService,
										 val context: Context,
										 val networkChecker: NetworkAvailabilityChecker) : Repository {

	override fun getInternationStationposition(): Observable<IssPositionResponse>
		= service.getCurrentIssPosition()
			.compose(unpackResponseAndHandleErrorsObservable())

	override fun getPeopleInSpace(): Observable<PeopleResponse>
		= service.getPeopleInSpace()
			.compose(unpackResponseAndHandleErrorsObservable())

	private fun <T> unpackResponseAndHandleErrorsObservable(): ObservableTransformer<Response<T>, T> {
		return ObservableTransformer { upstream ->
			upstream.flatMap {
				if(it.isSuccessful){
					Observable.just(it.body())
				}

				else{
					if(!networkChecker.isNetworkAvailable())
						Observable.error(NetworkException())

					else
						Observable.error(Exception())
				}
			}
		}
	}


}




