package pl.michalboryczko.swingdev.ui.main

import com.mapbox.mapboxsdk.geometry.LatLng
import io.reactivex.observers.DisposableObserver
import pl.michalboryczko.swingdev.base.mvp.Presenter
import pl.michalboryczko.swingdev.commons.toPrintableDate
import pl.michalboryczko.swingdev.interactor.GetIssDataUseCase
import pl.michalboryczko.swingdev.model.IssPosiitonWithPeople
import pl.michalboryczko.swingdev.model.responses.Person
import pl.michalboryczko.swingdev.network.exception.NetworkException
import pl.michalboryczko.swingdev.storage.ResourceProvider
import pl.michalboryczko.swingdev.storage.UserPreferences
import timber.log.Timber
import javax.inject.Inject

class MainPresenter @Inject constructor(
		private val getIssPositionUseCase: GetIssDataUseCase,
		private val resourceProvider: ResourceProvider,
		private val pref: UserPreferences
): Presenter<MainView>(){


	private var cachedPeople: List<Person>? = null

	override fun resume(){

	}

	override fun pause(){
		getIssPositionUseCase.clear()
	}

	override fun destroy() {
	}


	fun onMapReady(){
		getDataFromApiPeriodically()
		val time = if(pref.lastSync == 0L) resourceProvider.communicate_no_internet else pref.lastSync.toPrintableDate()
		view.setSyncData("${resourceProvider.last_sync}: $time")
		val latLng = LatLng(pref.lastLatitude, pref.lastLongitude)
		view.setMarker(latLng)
	}

	private fun getDataFromApiPeriodically(){
		getIssPositionUseCase.execute(object: DisposableObserver<IssPosiitonWithPeople>(){
			override fun onComplete() {}

			override fun onNext(t: IssPosiitonWithPeople) {
				val position = t.latLngPosition
				val people = t.people
				cachedPeople = people

				val title = resourceProvider.people_on_iss.replace("X", people.size.toString())
				val latLng = LatLng(position.latitude, position.longitude)

				view.moveCamera(latLng)
				view.setMarker(latLng)
				view.setPeopleList(title, formatPeopleList(people))
				view.setSyncData("${resourceProvider.last_sync}: ${t.timestamp.toPrintableDate()}")
			}

			override fun onError(e: Throwable) {
				if(e is NetworkException)
					view.showMessage(resourceProvider.communicate_no_internet)

				Timber.e(e.toString())
			}
		}, 7L)
	}


	fun onMarkerClicked(){
		val people = cachedPeople
		if(people != null){
			val title = resourceProvider.people_on_iss.replace("X", people.size.toString())
			view.setPeopleList(title, formatPeopleList(people))
		}

		else{
			getIssPositionUseCase.clear()
			getDataFromApiPeriodically()
		}
	}


	fun formatPeopleList(list: List<Person>): String{
		var output = ""
		list.forEach{ output += "${it.name} \n"}
		return output
	}




}