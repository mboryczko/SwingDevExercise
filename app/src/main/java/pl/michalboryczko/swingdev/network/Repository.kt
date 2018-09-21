package pl.michalboryczko.swingdev.network

import io.reactivex.Observable
import io.reactivex.Single
import pl.michalboryczko.swingdev.model.responses.IssPositionResponse
import pl.michalboryczko.swingdev.model.responses.PeopleResponse

interface Repository {

	fun getInternationStationposition(): Observable<IssPositionResponse>
	fun getPeopleInSpace(): Observable<PeopleResponse>


}