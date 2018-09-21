package pl.michalboryczko.swingdev.model.responses

class PeopleResponse(
        val message: String,
        val people: List<Person>,
        val number: Int
):DefaultResponse(message)