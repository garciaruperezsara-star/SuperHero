package com.example.freegames.data

import com.google.gson.annotations.SerializedName
import java.util.concurrent.Flow

data class GameResponse(val results: List<Game>)
data class Game(
    @SerializedName("id") val id: String,
@SerializedName("title") val title: String,
@SerializedName("thumbnail") val thumbnail: String,
@SerializedName("game_url") val gameUrl: String,
@SerializedName("description") val description: String?,
    @SerializedName("short_description") val shortDesc: String?,
@SerializedName("genre")val genre: String?,
@SerializedName("platform")val platform: String?,
@SerializedName("publisher")val publer: String?,
@SerializedName("developer")val developer: String?,
@SerializedName("release_date")val releaseDate: String?
)