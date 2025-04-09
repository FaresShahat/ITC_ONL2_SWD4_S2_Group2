package event.countdown.API

import event.countdown.API.model.Event
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.json")
    suspend fun getEvents(
        @Query("engine") engine: String = "google_events",
        @Query("q") query: String = "events in Dubai", // Search query
        @Query("api_key") apiKey: String = "57ae2e9e0a6995bc7ff3e2f3706b8fb7c97dff1adc023f8547a4c04bb1e687db" // Replace with your key
    ): SerpApiResponse
}

data class SerpApiResponse(
    val events_results: List<Event>
)