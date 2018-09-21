package pl.michalboryczko.swingdev.interactor

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import pl.michalboryczko.swingdev.executor.PostExecutionThread
import pl.michalboryczko.swingdev.executor.ThreadExecutor
import pl.michalboryczko.swingdev.model.IssPosiitonWithPeople
import pl.michalboryczko.swingdev.model.LatLngPosition
import pl.michalboryczko.swingdev.model.responses.IssPositionResponse
import pl.michalboryczko.swingdev.model.responses.Person
import pl.michalboryczko.swingdev.network.Repository
import pl.michalboryczko.swingdev.network.wrapper.ObservableUseCase
import pl.michalboryczko.swingdev.network.wrapper.ObservableVoidUseCase
import pl.michalboryczko.swingdev.storage.UserPreferences
import javax.inject.Inject
import java.util.concurrent.TimeUnit


class GetIssDataUseCase @Inject constructor(
        private val repository: Repository,
        private val pref: UserPreferences,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread) : ObservableUseCase<Long, IssPosiitonWithPeople>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(secondInterval: Long): Observable<IssPosiitonWithPeople>
        = Observable.interval(0, secondInterval, TimeUnit.SECONDS)
            .flatMap {
                Observable.zip(
                        getPosition(),
                        getPeopleInSpace(),
                        BiFunction<IssPositionResponse, List<Person>, IssPosiitonWithPeople>{ issPosition, people ->
                            IssPosiitonWithPeople(issPosition.iss_position, people, issPosition.timestamp)
                        })
                        .retryWhen { Observable.interval(secondInterval, TimeUnit.SECONDS);}
            }

    private fun getPeopleInSpace(): Observable<List<Person>>{
        return repository
                .getPeopleInSpace()
                .map { it.people }
    }

    private fun getPosition():Observable<IssPositionResponse> {
        return repository.getInternationStationposition()
                .onErrorReturn {
                    IssPositionResponse("error", LatLngPosition(pref.lastLatitude, pref.lastLongitude), pref.lastSync)
                }
                .flatMap {
                    pref.lastLatitude = it.iss_position.latitude
                    pref.lastLongitude = it.iss_position.longitude
                    pref.lastSync = it.timestamp

                    Observable.just(IssPositionResponse("error", LatLngPosition(pref.lastLatitude, pref.lastLongitude), it.timestamp))
                }
    }



}