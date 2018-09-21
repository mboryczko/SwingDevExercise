package pl.michalboryczko.swingdev.model

import pl.michalboryczko.swingdev.model.responses.IssPositionResponse
import pl.michalboryczko.swingdev.model.responses.Person


class IssPosiitonWithPeople(
		val latLngPosition: LatLngPosition,
		val people: List<Person>,
		val timestamp: Long
)