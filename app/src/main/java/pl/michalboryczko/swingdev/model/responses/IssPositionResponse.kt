package pl.michalboryczko.swingdev.model.responses

import pl.michalboryczko.swingdev.model.LatLngPosition

class IssPositionResponse(
        val message: String,
        val iss_position: LatLngPosition,
        val timestamp: Long
): DefaultResponse(message)