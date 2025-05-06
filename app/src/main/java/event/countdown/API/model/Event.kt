package event.countdown.API.model

data class Event(
    val title: String,
    val date: Date,
    val link: String // Optional: URL to event details
) {
    data class Date(
        val start_date: String // Event start date
    )
}