package pl.michalboryczko.swingdev.network


import io.reactivex.Observable
import io.reactivex.Single
import pl.michalboryczko.swingdev.model.responses.IssPositionResponse
import pl.michalboryczko.swingdev.model.responses.PeopleResponse
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

	@GET("iss-now.json")
	fun getCurrentIssPosition(
	): Observable<Response<IssPositionResponse>>


	@GET("astros.json")
	fun getPeopleInSpace(
	): Observable<Response<PeopleResponse>>



}