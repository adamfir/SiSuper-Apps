package com.example.vitorizkiimanda.sisuper_apps.database

data class AgendaEvent(
        val id: Int?,
        val eventId: String?,
        val eventImage: String?,
        val eventDate: String?,
        val eventName: String?,
        val eventPlace: String?,
        val eventOrganizer: String?,
        val eventDescription: String?) {

    companion object {
        const val TABLE_AGENDA_EVENT: String = "TABLE_AGENDA_EVENT"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_IMAGE: String = "EVENT_IMAGE"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val EVENT_PLACE: String = "EVENT_PLACE"
        const val EVENT_ORGANIZER: String = "EVENT_ORGANIZER"
        const val EVENT_DESCRIPTION: String = "EVENT_DESCRIPTION"
    }
}